package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class gameOver extends JFrame implements ActionListener {
	JLabel text,text2;
	JButton playAgain;
	JButton pic;

	public gameOver(int d) {
		this.setTitle("the end");
		this.setSize(1000, 1000);
		//this.setVisible(true);
		this.setBackground(Color.white);
		this.setLocation(25, 25);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(5,0));

		

		pic = new JButton();
		ImageIcon icon = new ImageIcon(this.getClass().getResource("done.gif"));
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(400, 200, java.awt.Image.SCALE_SMOOTH);
		ImageIcon newicon = new ImageIcon(newimg);
		pic.setIcon(newicon);
		this.add(pic);
		
		
		
		text = new JLabel();
		text.setVisible(true);
		text.setText("													GAME OVER");
		text.setFont(new Font(Font.SANS_SERIF, Font.BOLD,28));
		this.add(text);
		
		
		text2 = new JLabel();
		text2.setVisible(true);
		text2.setText("										  "+d + " citizens died");
		text2.setFont(new Font(Font.SANS_SERIF, Font.BOLD,28));

		this.add(text2);


		playAgain = new JButton("Play Again");
		playAgain.setSize(200, 50);
		playAgain.setBorder(BorderFactory.createLineBorder(Color.red));
		playAgain.setFont(new Font("Times New Roman", Font.ITALIC, 28));
		playAgain.setForeground(Color.black);
		playAgain.addActionListener(this);
		this.add(playAgain);
		
		
		JButton close = new JButton("Close");
		close.setSize(200, 50);
		close.setBorder(BorderFactory.createLineBorder(Color.red));
		close.setFont(new Font("Times New Roman", Font.ITALIC, 28));
		close.setForeground(Color.black);
		close.addActionListener(this);
		this.add(close);
		
		//System.exit(0);
		this.validate();
		this.pack();		


	}

	public void actionPerformed(ActionEvent e) {
		if(playAgain == e.getSource()) {
			try {
				CommandCenter again = new CommandCenter();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		else
			System.exit(0);
	}




	public static void main(String[]args) {
		new gameOver(23);
	}
}
