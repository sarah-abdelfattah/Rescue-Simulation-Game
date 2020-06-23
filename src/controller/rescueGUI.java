package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import exceptions.SimulationException;
import model.events.SOSListener;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;
import simulation.Simulator;
import view.gameView;




public class rescueGUI implements ActionListener, SOSListener, SOSResponder, WorldListener{
	private Simulator sim;
	private ArrayList<JButton> btnsElementst;
	private gameView game;
	
	
	
	public rescueGUI() throws Exception {
		sim = new Simulator(this);
		
		game = new gameView();
		
		btnsElementst = new ArrayList<JButton>();
		
		Address address;
		for (int i = 0 ; i<10 ; i++) {
			for(int j = 0 ; j<10 ; j++) {
				address = new Address(i,j);
				
				ResidentialBuilding r = sim.getBuildings().get(i);
				if(r.getLocation().getX() == i && r.getLocation().getY() == j) {
					JButton button = new JButton();
					//button.addActionListener(this);
					game.addBuilding(button, true);
					
					this.btnsElementst.add(button);

				}
				else {
					JButton button = new JButton();
					//button.addActionListener(this);
					game.addBuilding(button,false);
					
					this.btnsElementst.add(button);
					System.out.println("none");
				}
//				if(sim.getBuildingByLocation(address) != null) { //I changed visibility of it to public
//					JButton button = new JButton();
//					button.setText("Building's location" + address.toString());
//					button.addActionListener(this);
//					game.addBuilding(button, false);
//					
//					this.btnsElementst.add(button);
//					System.out.println(address.toString());
//					
//				}
			}
		}
		
		
	}
	
	
	
	@Override
	public void assignAddress(Simulatable s, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void respond(Rescuable r) throws SimulationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveSOSCall(Rescuable r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		
	}


	public static void main(String[] args) throws Exception {
		System.out.println("yes");
		new rescueGUI();
	}


	

}
