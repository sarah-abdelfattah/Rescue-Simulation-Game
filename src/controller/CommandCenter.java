package controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;

//import com.sun.javafx.css.CalculatedValue;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import exceptions.SimulationException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Rescuable;
import simulation.Simulator;
import view.trial;

public class CommandCenter  implements SOSListener,ActionListener
{

	private Simulator engine;

	private trial view;

	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<Citizen> dead;
	private ArrayList<JButton> worlds;
	private ArrayList<JButton> unitsButtons;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Disaster> disasters;

	private Unit currentUnit;
	private Rescuable currentTarget;

	JButton button;
	JButton currentCycle;
	JButton target;
	JButton unit;
	JButton respond;
	JButton casualties;
	JButton b;

	private int counter1 = 0;
	int prevBuildings = 0;
	int prevCitizens = 0;
	int prevDead = 0;   //kont 3amel dool 3ashan akaren el info panel

	ImageIcon icon, newicon;
	Image img, newimg;

	Color aliceBlue = new Color(240,248,255);
	Color lightGray = new Color(245,245,245);

	JTextArea textArea;


	public CommandCenter() throws Exception {
		engine = new Simulator(this);

		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		disasters = new ArrayList<Disaster>();
		worlds = new ArrayList<>();
		unitsButtons = new ArrayList<>();
		dead = new ArrayList<Citizen>();
		
		emergencyUnits = engine.getEmergencyUnits();

		view=new trial();
		view.setTitle("The Disaster");

		target=new JButton("Current Target");
		target.setActionCommand("target");
		target.addActionListener(this);
		unit=new JButton("Current Unit");

		unit.setActionCommand("unit");
		unit.addActionListener(this);
		currentCycle = new JButton("Current cycle 0");
		casualties = new JButton("Casualties: 0");

		respond=new JButton("Respond");
		respond.setActionCommand("respond");
		respond.addActionListener(this);



		loadUnits();    //adds a button for each idle unit
		loadWorld();		//adds the array list of world buttons and adds them to grid
		loadVisible();  //n


		JButton nextCycle=new JButton("Next Cycle");
		nextCycle.addActionListener(this);
		view.getDownPart().add(nextCycle);
		view.getDownPart().add(target);
		view.getDownPart().add(unit);
		view.getDownPart().add(currentCycle);
		view.getDownPart().add(respond);
		view.getDownPart().add(casualties);

		worlds.get(0).setText("BASE");

		view.validate();
		//view.pack();

	}

	public void loadVisible(){
		for(int i=0;i<engine.getBuildings().size();i++){
			ResidentialBuilding b=engine.getBuildings().get(i);
			if(b.getDisaster()!=null)
				receiveSOSCall(b);;
		}

		for(int i=0;i<engine.getCitizens().size();i++){
			Citizen c=engine.getCitizens().get(i);
			if(c.getDisaster()!=null)
				receiveSOSCall(c);
		}


	}

	public void refresh(){
		currentCycle.setText("Current Cycle:"+(++counter1));
		casualties.setText("Casualties: "+engine.calculateCasualties());

		for(int i=0;i<visibleCitizens.size();i++){
			Citizen c=visibleCitizens.get(i);
			String loc=""+c.getLocation();

			for(int j=0;j<worlds.size();j++){

				if(loc.equals(worlds.get(j).getActionCommand())){
					if(c.getState()==CitizenState.DECEASED)
					{
						icon = new ImageIcon(getClass().getResource("dead.gif"));
						img = icon.getImage();
						newimg = img.getScaledInstance((int)worlds.get(j).getSize().getWidth()-5, (int)worlds.get(j).getSize().getHeight()-1-5, java.awt.Image.SCALE_SMOOTH);
						newicon = new ImageIcon(newimg);
						worlds.get(j).setIcon(newicon);
						worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
						worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
					}
					//worlds.get(j).setText("Dead ");
					else {
						Boolean found=false;
						for(int k = 0 ; k<emergencyUnits.size() && found == false; k++ ) {

							if(emergencyUnits.get(k).getLocation().equals(c.getLocation())) {
								icon = new ImageIcon(getClass().getResource("citizen.gif"));
								img = icon.getImage();
								newimg = img.getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH);
								newicon = new ImageIcon(newimg);
								worlds.get(j).setIcon(newicon);
								worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
								worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
								found =true;


								ImageIcon icon2,newicon2;
								Image img2,newimg2;
								if(Integer.parseInt(emergencyUnits.get(k).getUnitID()) == 1) {
									icon2 = new ImageIcon(getClass().getResource("ambulance.gif"));
									img2 = icon2.getImage();
									newimg2 = img2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
									newicon2 = new ImageIcon(newimg2);
									worlds.get(j).setRolloverEnabled(true);
									worlds.get(j).setRolloverIcon(newicon2);
									worlds.get(j).setPressedIcon(newicon2);
									worlds.get(j).setText("AMB");
									worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
									worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
								}
								else if(Integer.parseInt(emergencyUnits.get(k).getUnitID()) == 2){
									icon2 = new ImageIcon(getClass().getResource("dcu.gif"));
									img2 = icon2.getImage();
									newimg2 = img2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
									newicon2 = new ImageIcon(newimg2);
									worlds.get(j).setRolloverEnabled(true);
									worlds.get(j).setRolloverIcon(newicon2);
									worlds.get(j).setPressedIcon(newicon2);
									worlds.get(j).setText("DCU");
									worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
									worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
								}
								else if(Integer.parseInt(emergencyUnits.get(k).getUnitID()) == 3){
									icon2 = new ImageIcon(getClass().getResource("policecar.gif"));
									img2 = icon2.getImage();
									newimg2 = img2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
									newicon2 = new ImageIcon(newimg2);
									worlds.get(j).setRolloverEnabled(true);
									worlds.get(j).setRolloverIcon(newicon2);
									worlds.get(j).setPressedIcon(newicon2);
									worlds.get(j).setText("EVC");
									worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
									worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
								}
								else if(Integer.parseInt(emergencyUnits.get(k).getUnitID()) == 4){
									icon2 = new ImageIcon(getClass().getResource("firetruck.gif"));
									img2 = icon2.getImage();
									newimg2 = img2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
									newicon2 = new ImageIcon(newimg2);
									worlds.get(j).setRolloverEnabled(true);
									worlds.get(j).setRolloverIcon(newicon2);
									worlds.get(j).setPressedIcon(newicon2);
									worlds.get(j).setText("FTK");
									worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
									worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
								}
								else if(Integer.parseInt(emergencyUnits.get(k).getUnitID()) == 5){
									icon2 = new ImageIcon(getClass().getResource("gcu.gif"));
									img2 = icon2.getImage();
									newimg2 = img2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
									newicon2 = new ImageIcon(newimg2);
									worlds.get(j).setRolloverEnabled(true);
									worlds.get(j).setRolloverIcon(newicon2);
									worlds.get(j).setPressedIcon(newicon2);
									worlds.get(j).setText("GCU");
									worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
									worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
								}


							}

							else {
								icon = new ImageIcon(getClass().getResource("citizen.gif"));
								img = icon.getImage();
								newimg = img.getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH);
								newicon = new ImageIcon(newimg);
								worlds.get(j).setIcon(newicon);
								worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
								worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
								worlds.get(j).setRolloverEnabled(false);
								worlds.get(j).setText("");
							}
						}
					}
				}
			}

		}


		for(int i=0 ;i<visibleBuildings.size(); i++){
			ResidentialBuilding b=visibleBuildings.get(i);
			String loc=""+b.getLocation();

			for(int j=0;j<worlds.size();j++){
				//System.out.println(b.getDisaster());
				String d = disasterType(b.getDisaster());

				if(loc.equals(worlds.get(j).getActionCommand())){
					if(b.getStructuralIntegrity()==0){
						icon = new ImageIcon(getClass().getResource("collapsed.gif"));
						img = icon.getImage();
						newimg = img.getScaledInstance((int)worlds.get(j).getSize().getWidth()-5, (int)worlds.get(j).getSize().getHeight()-5, java.awt.Image.SCALE_SMOOTH);
						newicon = new ImageIcon(newimg);
						worlds.get(j).setIcon(newicon);
						worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
						worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
					}
					//worlds.get(j).setText("Collapsed");
					else {
						Boolean found=false;
						for(int k = 0 ; k<emergencyUnits.size() && found == false; k++ ) {

							if(emergencyUnits.get(k).getLocation().equals(b.getLocation())) {
								icon = new ImageIcon(getClass().getResource("building.gif"));
								img = icon.getImage();
								newimg = img.getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH);
								newicon = new ImageIcon(newimg);
								worlds.get(j).setIcon(newicon);
								worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
								worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
								found =true;


								ImageIcon icon2,newicon2;
								Image img2,newimg2;
								if(Integer.parseInt(emergencyUnits.get(k).getUnitID()) == 1) {
									icon2 = new ImageIcon(getClass().getResource("ambulance.gif"));
									img2 = icon2.getImage();
									newimg2 = img2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
									newicon2 = new ImageIcon(newimg2);
									worlds.get(j).setRolloverEnabled(true);
									worlds.get(j).setRolloverIcon(newicon2);
									worlds.get(j).setPressedIcon(newicon2);
									worlds.get(j).setText("AMB");
									worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
									worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
								}
								else if(Integer.parseInt(emergencyUnits.get(k).getUnitID()) == 2){
									icon2 = new ImageIcon(getClass().getResource("dcu.gif"));
									img2 = icon2.getImage();
									newimg2 = img2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
									newicon2 = new ImageIcon(newimg2);
									worlds.get(j).setRolloverEnabled(true);
									worlds.get(j).setRolloverIcon(newicon2);
									worlds.get(j).setPressedIcon(newicon2);
									worlds.get(j).setText("DCU");
									worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
									worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
								}
								else if(Integer.parseInt(emergencyUnits.get(k).getUnitID()) == 3){
									icon2 = new ImageIcon(getClass().getResource("policecar.gif"));
									img2 = icon2.getImage();
									newimg2 = img2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
									newicon2 = new ImageIcon(newimg2);
									worlds.get(j).setRolloverEnabled(true);
									worlds.get(j).setRolloverIcon(newicon2);
									worlds.get(j).setPressedIcon(newicon2);
									worlds.get(j).setText("EVC");
									worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
									worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
								}
								else if(Integer.parseInt(emergencyUnits.get(k).getUnitID()) == 4){
									icon2 = new ImageIcon(getClass().getResource("firetruck.gif"));
									img2 = icon2.getImage();
									newimg2 = img2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
									newicon2 = new ImageIcon(newimg2);
									worlds.get(j).setRolloverEnabled(true);
									worlds.get(j).setRolloverIcon(newicon2);
									worlds.get(j).setPressedIcon(newicon2);
									worlds.get(j).setText("FTK");
									worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
									worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
								}
								else if(Integer.parseInt(emergencyUnits.get(k).getUnitID()) == 5){
									icon2 = new ImageIcon(getClass().getResource("gcu.gif"));
									img2 = icon2.getImage();
									newimg2 = img2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
									newicon2 = new ImageIcon(newimg2);
									worlds.get(j).setRolloverEnabled(true);
									worlds.get(j).setRolloverIcon(newicon2);
									worlds.get(j).setPressedIcon(newicon2);
									worlds.get(j).setText("GCU");
									worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
									worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
								}


							}

							else {
								icon = new ImageIcon(getClass().getResource("building.gif"));
								img = icon.getImage();
								newimg = img.getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH);
								newicon = new ImageIcon(newimg);
								worlds.get(j).setIcon(newicon);
								worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
								worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);
								worlds.get(j).setRolloverEnabled(false);
								worlds.get(j).setText("");
							}
						}
					}
				}

			}

		}





		for(int i=0;i<emergencyUnits.size();i++){

			Unit u=emergencyUnits.get(i);
			String loc=""+u.getLocation();
			for(int j=0;j<worlds.size();j++){
				if(loc.equals(worlds.get(j).getActionCommand())){
					for(int k=0;k<visibleCitizens.size();k++) {
						//						if(visibleCitizens.get(k).getLocation().equals(loc)) {
						//							
						//						}
						//							worlds.get(j).setText("Citizen + Unit");
					}
					for(int k=0;k<visibleBuildings.size();k++){
						//						if(visibleBuildings.get(k).getLocation().equals(loc))
						//						//	worlds.get(j).setText("Building+ Unit");
					}

				}
			}

		}


		//di el method el btshoof law fi zitizen w building f nfs el location 
		//ht8yr el icon
		//5ltha a5r wa7da ,, 3lshan already yb2a 3ndohom icons mn el methods el foo2
		//f in case di 7slt el icon et8yr 
		//in case la2 ,, howa already 3ndo
		//rage3 3ala el method di 

		for(int i = 0; i< visibleBuildings.size(); i++) {

			ResidentialBuilding b = visibleBuildings.get(i);

			for(int j = 0; j<visibleCitizens.size(); j++) {
				Citizen c = visibleCitizens.get(j);
				if(b.getLocation().equals(c.getLocation())) {
					icon = new ImageIcon(getClass().getResource("buildinwcitizen.gif"));
					img = icon.getImage();
					newimg = img.getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH);
					newicon = new ImageIcon(newimg);
					worlds.get(j).setIcon(newicon);
					worlds.get(j).setHorizontalTextPosition(JButton.CENTER);
					worlds.get(j).setVerticalTextPosition(JButton.BOTTOM);

				}
			}
		}

		
		
		
		
		//view.pack();
		//view.validate();
		view.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public void loadWorld(){

		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				button = new JButton();
				if((i+j)%2!=0 ) {
					button.setBackground(new Color(25,25,112));
					button.setOpaque(true);

				}
				else {
					button.setBackground(new Color(139,0,0));
					button.setOpaque(true);
				}

				button.setActionCommand(i+" "+j);
				button.addActionListener(this);
				worlds.add(button);
				view.getGrid().add(button);

			}
		}

	}

	public void loadUnits(){
		for(int i = engine.getEmergencyUnits().size()-1;i>-1;i--){
			Unit a = engine.getEmergencyUnits().get(i);
			//JButton b = new JButton();
			if(a.getState() == UnitState.IDLE){


				if(a instanceof Ambulance){
					//b.setText("Ambulance "+a.getState());
					icon = new ImageIcon(getClass().getResource("ambulance.gif"));
					img = icon.getImage();
					newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
					newicon = new ImageIcon(newimg);
					b = new JButton(""+a.getState(),newicon);
					b.setHorizontalTextPosition(JButton.CENTER);
					b.setVerticalTextPosition(JButton.BOTTOM);
					b.setActionCommand("AMB");


				}

				if(a instanceof FireTruck){
					//b.setText("Fire Truck "+a.getState());
					icon = new ImageIcon(getClass().getResource("firetruck.gif"));
					img = icon.getImage();
					newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
					newicon = new ImageIcon(newimg);
					b = new JButton(""+a.getState(),newicon);
					b.setHorizontalTextPosition(JButton.CENTER);
					b.setVerticalTextPosition(JButton.BOTTOM);
					b.setActionCommand("FTK");
				}

				if(a instanceof DiseaseControlUnit){
					//b.setText("Disease Control "+a.getState());
					icon = new ImageIcon(getClass().getResource("DCU.gif"));
					img = icon.getImage();
					newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
					newicon = new ImageIcon(newimg);
					b = new JButton(""+a.getState(),newicon);
					b.setHorizontalTextPosition(JButton.CENTER);
					b.setVerticalTextPosition(JButton.BOTTOM);
					b.setActionCommand("DCU");
				}

				if(a instanceof GasControlUnit){
					//b.setText("Gas Control \n"+a.getState());
					icon = new ImageIcon(getClass().getResource("gcu.gif"));
					img = icon.getImage();
					newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
					newicon = new ImageIcon(newimg);
					b = new JButton(""+a.getState(),newicon);
					b.setHorizontalTextPosition(JButton.CENTER);
					b.setVerticalTextPosition(JButton.BOTTOM);
					b.setActionCommand("GCU");
				}

				if(a instanceof Evacuator){
					//b.setText("Evacuator \n"+a.getState());
					icon = new ImageIcon(getClass().getResource("policecar.gif"));
					img = icon.getImage();
					newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
					newicon = new ImageIcon(newimg);
					b = new JButton(""+a.getState(),newicon);
					b.setHorizontalTextPosition(JButton.CENTER);
					b.setVerticalTextPosition(JButton.BOTTOM);
					b.setActionCommand("EVC");
				}

				b.addActionListener(this);

				view.getAvailableUnitPanel().add(b);
				unitsButtons.add(b);

			}
		}
	}

	public void printl(Object s){
		System.out.println(s);
	}

	@Override
	public void receiveSOSCall(Rescuable r) {

		if (r instanceof ResidentialBuilding) {

			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);

		} else {

			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}

	}

	public void checkUnit(String command){

		if (command.equals("AMB")){
			for(int i=0;i<engine.getEmergencyUnits().size();i++){
				Unit u=engine.getEmergencyUnits().get(i);
				int id=Integer.parseInt(u.getUnitID());
				if(id==1){
					unit.setText("Current unit: "+command);
					currentUnit=u;

				}
			}
		}

		if (command.equals("DCU")){
			for(int i=0;i<engine.getEmergencyUnits().size();i++){

				Unit u=engine.getEmergencyUnits().get(i);
				int id=Integer.parseInt(u.getUnitID());
				if(id==2){
					unit.setText("current unit: "+command);
					currentUnit=u;
				}
			}
		}
		if (command.equals("EVC")){
			for(int i=0;i<engine.getEmergencyUnits().size();i++){

				Unit u=engine.getEmergencyUnits().get(i);
				int id=Integer.parseInt(u.getUnitID());
				if(id==3){
					unit.setText("current unit: "+command);
					currentUnit=u;

				}
			}
		}

		if (command.equals("FTK")){
			for(int i=0;i<engine.getEmergencyUnits().size();i++){

				Unit u=engine.getEmergencyUnits().get(i);
				int id=Integer.parseInt(u.getUnitID());
				if(id==4){
					unit.setText("current unit: "+command);
					currentUnit=u;

				}
			}
		}
		if (command.equals("GCU")){
			for(int i=0;i<engine.getEmergencyUnits().size();i++){

				Unit u=engine.getEmergencyUnits().get(i);
				int id=Integer.parseInt(u.getUnitID());
				if(id==5){
					unit.setText("current unit: "+command);
					currentUnit=u;

				}
			}
		}


	}

	public void checkTarget(String command){

		for(int i=0;i<visibleCitizens.size();i++){

			Citizen c=visibleCitizens.get(i);
			String loc=""+c.getLocation();

			if(command.equals(loc)){
				view.getInfo().setText(""+c.citizenInfo());
				//JOptionPane.showMessageDialog(view, c.citizenInfo());
				currentTarget=c;
				target.setText("Current Target: Citizen "+c.getLocation());
			}
		}

		for(int i=0;i<visibleBuildings.size();i++){

			ResidentialBuilding b=visibleBuildings.get(i);

			String loc=""+b.getLocation();

			if(command.equals(loc)){
				view.getInfo().setText(""+b.buildingInfo());
				//JOptionPane.showMessageDialog(view, b.buildingInfo());
				currentTarget=b;
				target.setText("Current Target: Building "+b.getLocation());
			}
		}



	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command=arg0.getActionCommand();
		//the next cycle part
		if(command.equals("Next Cycle")){
			try {
				engine.nextCycle();
				refresh();
				refreshUnits();

				if(engine.checkGameOver()){{
					//					JOptionPane.showMessageDialog(view, "balabizo game over \n "
					//							+ "Deaths: "+engine.calculateCasualties());
					gameOver over = new gameOver(engine.calculateCasualties());
					over.setVisible(true);
					view.dispose();
				}

				}


			} catch (SimulationException e) {
				e.printStackTrace();
			}
		}
		//end

		if(command.equals("unit")){
			unit.setText("Current Unit: None");
			currentUnit=null;}
		if(command.equals("target")){
			target.setText("Current Target: None");
			currentTarget=null;}



		checkUnit(command); //if he pressed on a unit it makes the current unit this unit
		checkTarget(command);//if he presses on a target it makes it the current target
		checkRespond(command);
		showUnitInfo(command);//if he presses on a unit it will show its info 
		infoPanel();
		System.out.println("sarah");
		updateDisaster();
		if(command.equalsIgnoreCase("0 0"))
			showBase();

	}

	public void checkRespond(String command){

		if(command.equals("respond")){

			if(currentUnit!=null&&currentTarget!=null){
				try {
					currentUnit.respond(currentTarget);
					JButton b = getUnitButton(currentUnit);
					b.setText(""+currentUnit.getState());


					currentUnit=null;
					currentTarget=null;
					unit.setText("CurrentUnit: None");
					target.setText("CurrentTarget: None");

				} catch (SimulationException e) {
					
					if(e instanceof IncompatibleTargetException){
						
						JOptionPane.showMessageDialog(view, "This target needs another unit");
					}
					else
					if(e instanceof CitizenAlreadyDeadException)
						JOptionPane.showMessageDialog(view, "The citizen is already dead");
					else
					if(e instanceof BuildingAlreadyCollapsedException)
						JOptionPane.showMessageDialog(view, "The building already collapsed");
					else
					if(e instanceof CannotTreatException)
						JOptionPane.showMessageDialog(view, "This unit is unable to treat this target");
					
					
					
				}
			}
		}



	}

	public JButton getUnitButton(Unit u){
		if(u instanceof Ambulance){
			for(int i=0;i<unitsButtons.size();i++){
				if(unitsButtons.get(i).getActionCommand().equals("AMB"))
					return unitsButtons.get(i);
			}
		}
		if(u instanceof Evacuator){
			for(int i=0;i<unitsButtons.size();i++){
				if(unitsButtons.get(i).getActionCommand().equals("EVC"))
					return unitsButtons.get(i);
			}
		}
		if(u instanceof DiseaseControlUnit){
			for(int i=0;i<unitsButtons.size();i++){
				if(unitsButtons.get(i).getActionCommand().equals("DCU"))
					return unitsButtons.get(i);
			}
		}
		if(u instanceof FireTruck){
			for(int i=0;i<unitsButtons.size();i++){
				if(unitsButtons.get(i).getActionCommand().equals("FTK"))
					return unitsButtons.get(i);
			}
		}
		if(u instanceof GasControlUnit){
			for(int i=0;i<unitsButtons.size();i++){
				if(unitsButtons.get(i).getActionCommand().equals("GCU"))
					return unitsButtons.get(i);
			}
		}



		return new JButton("None");
	}

	public void refreshUnits(){
		for(int i=0;i<emergencyUnits.size();i++){
			Unit u=emergencyUnits.get(i);
			button=getUnitButton(u);
			button.setText("\n"+u.getState());

		}
	}

	public void showUnitInfo(String command){
		for(int i=0;i<emergencyUnits.size();i++){
			Unit u=emergencyUnits.get(i);
			button=getUnitButton(u);
			if(button.getActionCommand().equals(command)){
				//JOptionPane.showMessageDialog(view, u.unitInfo());
				view.getInfo().setText("" + u.unitInfo() );
			}
		}

	}

	public void showBase(){
		String s="";

		for(int i=0;i<emergencyUnits.size();i++){
			Unit u=emergencyUnits.get(i);
			String loc=""+u.getLocation();
			

				s+=u.unitInfo();
		}

		for(int i=0;i<visibleCitizens.size();i++){
			Citizen c=visibleCitizens.get(i);
			String loc=""+c.getLocation();
			if(loc.equals("0 0"))
				s+=c.citizenInfo();
		}

		//JOptionPane.showMessageDialog(view,s+"\n");
		view.getInfo().setText(s);

	}

	public static void main(String[]args) throws Exception{
		new CommandCenter();
	}

	public void infoPanel(){
		if(prevBuildings<visibleBuildings.size()){

			for(int i=prevBuildings;i<visibleBuildings.size();i++){

				ResidentialBuilding b=visibleBuildings.get(i);
				disasters.add(b.getDisaster());
				view.getLog().append("The Disaster "+b.disasterType(b.getDisaster())+"\n"+
						"has struck building at "+b.getLocation()+"\n");

				prevBuildings++;
			}
		}

		if(prevCitizens<visibleCitizens.size()){

			for(int i=prevCitizens;i<visibleCitizens.size();i++){

				Citizen c=visibleCitizens.get(i);
				disasters.add(c.getDisaster());
				view.getLog().append("The Disaster "+c.disasterType(c.getDisaster())+"\n"+
						"has struck Citizen: "+c.getNationalID()+"\n");

				prevCitizens++;
			}
		}
		updateDead();

		//LEFT FOR BASSEL
		//		if(prevDead<engine.calculateCasualties()){
		//			for(int i=prevDead;i<engine.getCitizens().size();i++){
		//				Citizen c = engine.getCitizens().get(i);
		//				for(int j=0; j< dead.size();j++) {
		//					if(dead.get(j).getNationalID().equals(c.getNationalID())) {
		//					view.getLog().append("A citizen at location: ("+c.getLocation() + ") has died"+"\n");
		//					dead.add(c);
		//					prevDead++;
		//				}
		//				}
		//			}
		//		}
		
	}
	public void updateDead(){
		for(int i=0;i<engine.getCitizens().size();i++){
			Citizen c=engine.getCitizens().get(i);
			if(c.getState()==CitizenState.DECEASED){
				if(doesntContain(c)){
					view.getLog().append("A citizen at location: ("+c.getLocation() + ") has died"+"\n");
					dead.add(c);
					}
				}
		}
		
		
		
		
	}
	public boolean doesntContain(Citizen c){
		for(int i=0;i<dead.size();i++){
			if(c.getNationalID().equals(dead.get(i).getNationalID()))
				return false;
		}
		
		return true;
	}

	public void updateDisaster(){
		String s="";
		for(int i=0;i<disasters.size();i++){
			if(disasters.get(i).isActive()==true){
				s+=disasterType(disasters.get(i))+"\n";
			}
		}
		view.getActiveDisastersText().setText(s);


	}

	public String disasterType(Disaster d){
		if(d instanceof Injury)
			return "Injury ";
		if(d instanceof Infection)
			return "Infection ";
		if(d instanceof Collapse)
			return "Collapse";
		if(d instanceof Fire)
			return "Fire ";
		if(d instanceof GasLeak)
			return "Gas Leak ";

		return "None";
	}



}
