package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import java.awt.Toolkit;


public class trial extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel allUnitsPanel,grid,infoPanel; // Panel is a container
	JPanel infoPart, logPart;

	JTextArea info;
	JTextArea log;
	private JTextArea activeDisastersText;
	
	JPanel treatingUnitsPanel;
	JPanel availableUnitPanel;
	JPanel respondingUnitPanel;
	JPanel activeDisasters;
	JPanel downPart;


	JScrollPane scrollLog, scrollInfo, scrollDis;

	ImageIcon icon, newicon;
	Image img, newimg;

	Color darkBlue = new Color(25,25,112);
	Color lightGray = new Color(245,245,245);
	Color darkRed = new Color(204,0,0); 
	Color seaShell = new Color(255,245,238);

	public trial() {


		this.validate();
		this.setVisible(true);
		this.validate();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		//this.setSize(MAXIMIZED_BOTH, MAXIMIZED_BOTH);
		//this.setSize(BoxLayout.PAGE_AXIS, BoxLayout.PAGE_AXIS);
		this.setLayout(new BorderLayout());
		this.setTitle("trial");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//this.setAlwaysOnTop(true); 
		this.getContentPane().setBackground(Color.WHITE);

		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	      setBounds(0,0,screenSize.width, screenSize.height);
	      setVisible(true);

		//-----MENU BAR-------
		{
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

		}
		//-----------




		//---- most right panel ----//
		allUnitsPanel = new JPanel(); 
		allUnitsPanel.setVisible(true);
		allUnitsPanel.setName("units");
		allUnitsPanel.setLayout(new GridLayout(2,0));	//asmtha three parts .. available .. responding .. treating
		this.add(allUnitsPanel, BorderLayout.EAST );	//added to the general container 
		allUnitsPanel.setBackground(Color.WHITE);
		//allUnitsPanel.setPreferredSize(new Dimension(200, (int) this.getSize().getHeight()));

		TitledBorder border = new TitledBorder("The Units");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		border.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,18));


		Color ivory = new Color(25,25,112);

		Color lightGray = new Color(245,245,245);

		border.setTitleColor(ivory);

		allUnitsPanel.setBorder(border);
		
		allUnitsPanel.setPreferredSize(new Dimension(350,(int) this.getSize().getHeight()));


		//the first part of the unit panel : displaying the available units
		availableUnitPanel = new JPanel(); 
		availableUnitPanel.setVisible(true);
		availableUnitPanel.setName("available units");
		availableUnitPanel.setLayout(new GridLayout(0,5));
		//this.add(unitPanel, BorderLayout.EAST );
		availableUnitPanel.setBackground(lightGray);
		availableUnitPanel.setPreferredSize(new Dimension(300,(int) this.getSize().getHeight()));
		allUnitsPanel.add(availableUnitPanel);


		TitledBorder borderAU = new TitledBorder("Available Units");
		borderAU.setTitleJustification(TitledBorder.CENTER);
		borderAU.setTitlePosition(TitledBorder.TOP);

		Color darkRed = new Color(204,0,0); 
		borderAU.setTitleColor(darkRed);
		borderAU.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,15));
		availableUnitPanel.setBorder(borderAU);

		//the part 
		
		activeDisasters = new JPanel(); 
		activeDisasters.setVisible(true);
		activeDisasters.setName("Active Disasters");
		activeDisasters.setBackground(lightGray);
		//activeDisasters.setPreferredSize(new Dimension(300,(int) this.getSize().getHeight()));
		allUnitsPanel.add(activeDisasters);



		activeDisastersText= new JTextArea();
		activeDisastersText.setFont(new Font(Font.MONOSPACED, Font.PLAIN,12));
		activeDisastersText.setPreferredSize(new Dimension((int)activeDisasters.getSize().getWidth()-20,120000));
		activeDisastersText.setEditable(false);
		activeDisastersText.setText("");
		activeDisastersText.setBackground(lightGray);
		activeDisasters.add(activeDisastersText, BorderLayout.CENTER);

		TitledBorder borderAD = new TitledBorder("Active Disasters");
		borderAD.setTitleJustification(TitledBorder.CENTER);
		borderAD.setTitlePosition(TitledBorder.TOP);

		borderAD.setTitleColor(darkRed);
		borderAD.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,15));
		activeDisastersText.setBorder(borderAD);


		scrollDis = new JScrollPane(activeDisastersText);
		scrollDis.setPreferredSize(new Dimension((int) activeDisasters.getSize().getWidth(),(int) activeDisasters.getSize().getHeight()));
		activeDisasters.add(scrollDis, BorderLayout.CENTER);
		
		
		
		
		//the second part of the unit panel : displaying the responding units
		respondingUnitPanel = new JPanel(); 
		respondingUnitPanel.setVisible(true);
		respondingUnitPanel.setName("responding units");
		respondingUnitPanel.setLayout(new GridLayout(0,5));
		respondingUnitPanel.setBackground(lightGray);
		//allUnitsPanel.add(respondingUnitPanel);


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
		//allUnitsPanel.add(treatingUnitsPanel);

		TitledBorder borderTU = new TitledBorder("Treating Units");
		borderTU.setTitleJustification(TitledBorder.CENTER);
		borderTU.setTitlePosition(TitledBorder.TOP);

		borderTU.setTitleColor(darkRed);
		borderTU.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,15));
		treatingUnitsPanel.setBorder(borderTU);








		//---- grid panel ----//
		grid = new JPanel(new GridLayout(10,10));
		grid.setVisible(true);
		this.add(grid,BorderLayout.CENTER);
		grid.setBackground(ivory);
		grid.setPreferredSize(new Dimension(600,(int) this.getSize().getHeight()));


		TitledBorder borderGrid = new TitledBorder("SBS");
		borderGrid.setTitleJustification(TitledBorder.CENTER);
		borderGrid.setTitlePosition(TitledBorder.TOP);
		borderGrid.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,18));
		borderGrid.setTitleColor(Color.WHITE);
		grid.setBorder(borderGrid);











		//---- info panel ----//

		infoPanel = new JPanel();
		infoPanel.setVisible(true);
		this.add(infoPanel,BorderLayout.WEST);
		infoPanel.setBackground(lightGray);
		infoPanel.setLayout(new GridLayout(2,0));
		infoPanel.setPreferredSize(new Dimension(350,(int) this.getSize().getHeight()));



		logPart = new JPanel();
		logPart.setVisible(true);
		logPart.setBackground(lightGray);
		logPart.setSize(new Dimension(350,12000));
		infoPanel.add(logPart);

		log = new JTextArea();
		log.setFont(new Font(Font.MONOSPACED, Font.PLAIN,12));
		log.setPreferredSize(new Dimension((int)logPart.getSize().getWidth()-20,120000));
		log.setEditable(false);
		log.setText("");
		log.setBackground(lightGray);
		logPart.add(log, BorderLayout.CENTER);

		TitledBorder borderLog = new TitledBorder("Log");
		borderLog.setTitleJustification(TitledBorder.CENTER);
		borderLog.setTitlePosition(TitledBorder.TOP);
		borderLog.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,18));
		borderLog.setTitleColor(ivory);
		log.setBorder(borderLog);


		scrollLog = new JScrollPane(log);
		scrollLog.setPreferredSize(new Dimension((int) logPart.getSize().getWidth(),(int) logPart.getSize().getHeight()));
		logPart.add(scrollLog, BorderLayout.CENTER);






		infoPart = new JPanel();
		infoPart.setVisible(true);
		infoPart.setBackground(lightGray);
		infoPart.setSize(new Dimension(350,1500));
		infoPanel.add(infoPart);

		info = new JTextArea();
		info.setFont(new Font(Font.MONOSPACED, Font.PLAIN,12));
		info.setPreferredSize(new Dimension((int)logPart.getSize().getWidth()-20,120000));
		info.setEditable(false);
		info.setText("");
		info.setBackground(lightGray);
		infoPart.add(info);

		TitledBorder borderInfo = new TitledBorder("Information");
		borderInfo.setTitleJustification(TitledBorder.CENTER);
		borderInfo.setTitlePosition(TitledBorder.TOP);
		borderInfo.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,18));
		borderInfo.setTitleColor(ivory);
		info.setBorder(borderInfo);


		scrollInfo = new JScrollPane(info);
		scrollInfo.setPreferredSize(new Dimension((int) infoPart.getSize().getWidth(),(int) infoPart.getSize().getHeight()));
		infoPart.add(scrollInfo, BorderLayout.CENTER);



		//t7t ba2a
		downPart = new JPanel();
		downPart.setVisible(true);
		this.add(downPart,BorderLayout.PAGE_END);
		downPart.setBackground(lightGray);


		TitledBorder borderDown = new TitledBorder("Progress");
		borderDown.setTitleJustification(TitledBorder.CENTER);
		borderDown.setTitlePosition(TitledBorder.TOP);
		borderDown.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD,18));
		borderDown.setTitleColor(ivory);		
		downPart.setBorder(borderDown);
	//	downPart.setPreferredSize(new Dimension((int)this.getSize().getWidth(),200));


		this.pack();
		this.validate();

		this.setLocationRelativeTo(null); 
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}



	public JPanel getAvailableUnitPanel() {
		return availableUnitPanel;
	}

	public JPanel getRespondingUnitPanel() {
		return respondingUnitPanel;
	}




	public JPanel getGrid() {
		return grid;
	}




	public JPanel getDownPart() {
		return downPart;
	}




	public void addUnitIdle(JButton unit) {

		availableUnitPanel.add(unit, FlowLayout.LEFT);

	}

	public JTextArea getInfo() {
		return info;
	}

	public JTextArea getLog() {
		return log;
	}



	public void updateLog(ArrayList<String> data) {
		String p = "";
		for(String string : data)
			p = p + string  + "\n";
		log.setText(p);
	}

//	public void updateInfo(ArrayList<String> info) {
//		String p = "";
//		for(String string : info)
//			p = p + string  + "\n";
//		this.info.setText(p);
//	}

	




	public static void main(String[] args) {

		new trial();
	}



	public JTextArea getActiveDisastersText() {
		return activeDisastersText;
	}



	public void setActiveDisastersText(JTextArea activeDisastersText) {
		this.activeDisastersText = activeDisastersText;
	}

}
