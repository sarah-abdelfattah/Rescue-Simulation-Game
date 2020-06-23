package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
//the program uses the Swing library.
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import simulation.Simulator;

public class gameView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel allUnitsPanel,grid,infoPanel; // Panel is a container
	private JButton button1; // Button is a component
	private JTextArea info;

	private JPanel availableUnitPanel,respondingUnitPanel,treatingUnitsPanel;

	public gameView(){

		super();

		this.validate();
		this.setVisible(true);
		this.setSize(1200, 500);
		this.setLayout(new BorderLayout());
		this.setTitle("النجدة !!");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setAlwaysOnTop(true); 
		this.getContentPane().setBackground(Color.WHITE);

		//-----MENU BAR-------

		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem = new JMenuItem("Welcome",
				KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"rvefdgrvef ");
		menu.add(menuItem);

		menuItem = new JMenuItem("this really doesn't do anything :P ",
				new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_B);
		menu.add(menuItem);

		menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_D);
		menu.add(menuItem);

		//a group of radio button menu items
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("Not Balabizo");
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Balabizo");
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		//a group of check box menu items
		menu.addSeparator();
		cbMenuItem = new JCheckBoxMenuItem("Yes");
		cbMenuItem.setMnemonic(KeyEvent.VK_C);
		menu.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("No");
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
		menu.add(cbMenuItem);

		//a submenu
		menu.addSeparator();
		submenu = new JMenu("Come to me");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("See below");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_2, ActionEvent.ALT_MASK));
		submenu.add(menuItem);

		submenu.addSeparator();
		JMenu subsubmenu = new JMenu("I have nothing :D ");
		menuItem = new JMenuItem("Nothing :)");

		subsubmenu.add(menuItem);
		submenu.add(subsubmenu);
		menu.add(submenu);

		//Build second menu in the menu bar.
		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
				"This menu does nothing");
		menuBar.add(menu);

		this.add(menuBar, BorderLayout.PAGE_START);


		//-----------

		//---- most left panel ----//
		JPanel allUnitsPanel = new JPanel(); 
		allUnitsPanel.setVisible(true);
		allUnitsPanel.setName("units");
		allUnitsPanel.setLayout(new GridLayout(3,0));	//asmtha three parts .. available .. responding .. treating
		this.add(allUnitsPanel, BorderLayout.EAST );	//added to the general container 
		allUnitsPanel.setBackground(Color.WHITE);

		TitledBorder border = new TitledBorder("The Units");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		border.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,18));

		Color brown = new Color(102,51,0);
		Color ivory = new Color(25,25,112);
		Color nicePurple = new Color(245,245,255);
		Color seaShell = new Color(255,255,240);
		Color lightGray = new Color(245,245,245);

		border.setTitleColor(ivory);

		allUnitsPanel.setBorder(border);


		//the first part of the unit panel : displaying the available units
		JPanel availableUnitPanel = new JPanel(); 
		availableUnitPanel.setVisible(true);
		availableUnitPanel.setName("available units");
		availableUnitPanel.setLayout(new GridLayout(0,5));
		//this.add(unitPanel, BorderLayout.EAST );
		availableUnitPanel.setBackground(lightGray);
		allUnitsPanel.add(availableUnitPanel);


		TitledBorder borderAU = new TitledBorder("Available Units");
		borderAU.setTitleJustification(TitledBorder.CENTER);
		borderAU.setTitlePosition(TitledBorder.TOP);

		Color darkRed = new Color(204,0,0); 
		borderAU.setTitleColor(darkRed);
		borderAU.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,15));
		availableUnitPanel.setBorder(borderAU);


		//the second part of the unit panel : displaying the responding units
		JPanel respondingUnitPanel = new JPanel(); 
		respondingUnitPanel.setVisible(true);
		respondingUnitPanel.setName("responding units");
		respondingUnitPanel.setLayout(new GridLayout(0,5));
		respondingUnitPanel.setBackground(lightGray);
		allUnitsPanel.add(respondingUnitPanel);


		TitledBorder borderRU = new TitledBorder("Responding Units");
		borderRU.setTitleJustification(TitledBorder.CENTER);
		borderRU.setTitlePosition(TitledBorder.TOP);

		borderRU.setTitleColor(darkRed);
		borderRU.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,15));
		respondingUnitPanel.setBorder(borderRU);


		//the third part of the unit panel : displaying the treating units
		JPanel treatingUnitsPanel = new JPanel(); 
		treatingUnitsPanel.setVisible(true);
		treatingUnitsPanel.setName("units");
		treatingUnitsPanel.setLayout(new GridLayout(0,5));
		treatingUnitsPanel.setBackground(lightGray);
		allUnitsPanel.add(treatingUnitsPanel);

		TitledBorder borderTU = new TitledBorder("Treating Units");
		borderTU.setTitleJustification(TitledBorder.CENTER);
		borderTU.setTitlePosition(TitledBorder.TOP);

		borderTU.setTitleColor(darkRed);
		borderTU.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,15));
		treatingUnitsPanel.setBorder(borderTU);




		// adding buttons of the units with icons //and numbers

		ImageIcon icon, newicon = new ImageIcon();
		Image img,newimg;

		//ambulance
		JPanel ambulance = new JPanel();
		availableUnitPanel.add(ambulance);
		ambulance.setLayout(new BorderLayout());

		JButton ambNum = new JButton("23");
		ambulance.add(ambNum, BorderLayout.AFTER_LAST_LINE);

		JButton amb = new JButton();
		icon = new ImageIcon(this.getClass().getResource("ambulance.gif"));
		img = icon.getImage();
		newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		newicon = new ImageIcon(newimg);
		amb.setIcon(newicon);
		ambulance.add(amb, BorderLayout.PAGE_START);



		//disease control unit
		JPanel disCont = new JPanel();
		availableUnitPanel.add(disCont);
		disCont.setLayout(new BorderLayout());

		JButton dcuNum = new JButton("22");
		disCont.add(dcuNum, BorderLayout.AFTER_LAST_LINE);

		JButton dcu = new JButton();
		icon = new ImageIcon(this.getClass().getResource("dcu.gif"));
		img = icon.getImage();
		newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		newicon = new ImageIcon(newimg);
		dcu.setIcon(newicon);
		disCont.add(dcu, BorderLayout.PAGE_START);

		//evacuator

		JPanel evacuator = new JPanel();
		availableUnitPanel.add(evacuator);
		evacuator.setLayout(new BorderLayout());

		JButton evcNum = new JButton("21");
		evacuator.add(evcNum, BorderLayout.AFTER_LAST_LINE);

		JButton evc = new JButton();
		icon = new ImageIcon(this.getClass().getResource("policecar.gif"));
		img = icon.getImage();
		newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		newicon = new ImageIcon(newimg);
		evc.setIcon(newicon);
		evacuator.add(evc, BorderLayout.PAGE_START);

		//fire truck

		JPanel fireTruck = new JPanel();
		availableUnitPanel.add(fireTruck);
		fireTruck.setLayout(new BorderLayout());

		JButton ftkNum = new JButton("20");
		fireTruck.add(ftkNum, BorderLayout.AFTER_LAST_LINE);

		JButton ftk = new JButton();
		icon = new ImageIcon(this.getClass().getResource("firetruck.gif"));
		img = icon.getImage();
		newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		newicon = new ImageIcon(newimg);
		ftk.setIcon(newicon);
		fireTruck.add(ftk, BorderLayout.PAGE_START);

		//gas control unit
		JPanel gasCU = new JPanel();
		availableUnitPanel.add(gasCU);
		gasCU.setLayout(new BorderLayout());

		JButton gcuNum = new JButton("0");
		gasCU.add(gcuNum, BorderLayout.AFTER_LAST_LINE);


		JButton gcu = new JButton();
		icon = new ImageIcon(this.getClass().getResource("gcu.gif"));
		img = icon.getImage();
		newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		newicon = new ImageIcon(newimg);
		gcu.setIcon(newicon);
		gasCU.add(gcu, BorderLayout.PAGE_START);


		//---- grid panel ----//
		JPanel grid = new JPanel(new GridLayout(10,10));
		grid.setVisible(true);
		this.add(grid,BorderLayout.CENTER);
		grid.setBackground(ivory);


		TitledBorder borderGrid = new TitledBorder("SBS");
		borderGrid.setTitleJustification(TitledBorder.CENTER);
		borderGrid.setTitlePosition(TitledBorder.TOP);
		borderGrid.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,18));
		borderGrid.setTitleColor(Color.WHITE);
		grid.setBorder(borderGrid);



		//		for(int i = 0 ; i<10 ; i++) {
		//			for(int j =0; j<10 ;j++) {
		//				grid.add(new JButton());
		//				
		//			}
		//	}







		//---- info panel ----//

		JPanel infoPanel = new JPanel();
		infoPanel.setVisible(true);
		this.add(infoPanel,BorderLayout.WEST);
		infoPanel.setBackground(lightGray);


		TitledBorder borderInfo = new TitledBorder("The Information");
		borderInfo.setTitleJustification(TitledBorder.CENTER);
		borderInfo.setTitlePosition(TitledBorder.TOP);
		borderInfo.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,18));
		borderInfo.setTitleColor(ivory);
		infoPanel.setBorder(borderInfo);

		infoPanel.setPreferredSize(new Dimension(200,(int) this.getSize().getHeight()));

		JTextArea info = new JTextArea();
		info.setFont(new Font(Font.MONOSPACED, Font.PLAIN,12));
		info.setSize(200,(int)this.getSize().getHeight());
		info.setEditable(false);
		infoPanel.add(info);




		//t7t ba2a
		JPanel downPart = new JPanel();
		downPart.setVisible(true);
		this.add(downPart,BorderLayout.PAGE_END);

		downPart.setBackground(lightGray);

		TitledBorder borderDown = new TitledBorder("Progress");
		borderDown.setTitleJustification(TitledBorder.CENTER);
		borderDown.setTitlePosition(TitledBorder.TOP);
		borderDown.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,18));
		borderDown.setTitleColor(ivory);		
		downPart.setBorder(borderDown);
		downPart.setPreferredSize(new Dimension((int)this.getSize().getWidth(),100));

		JProgressBar progressBar = new JProgressBar();
		//progressBar = new JProgressBar(0, task.getLengthOfTask());
		progressBar.setValue(23);
		progressBar.setStringPainted(true);

		downPart.add(progressBar);




		this.pack(); //by3dl el screen ,, 3lshan kol 7aga tban kwais without maximize wla minimize
	}
	

	public void addBuilding(JButton building, boolean test) {
		if(test == true) {
			ImageIcon icon = new ImageIcon(this.getClass().getResource("gcu.gif"));
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
			ImageIcon newicon = new ImageIcon(newimg);
			building.setIcon(newicon);
			this.grid.add(building);
		}
		else {
			this.grid.add(building);
		}

	}

	public static void main(String[]args) {

		gameView game = new gameView();

	}
}
