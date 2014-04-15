package gui_rough_draft;

import java.io.FileReader;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;



public class gui extends JFrame{
	
	
	

	public gui() {
		
		initUI();
		
	}
	
	public final void initUI(){
		setTitle("Apriori");
		setSize (600,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JButton mcl = new JButton("Set MCL");
		mcl.setBounds(40, 30, 80, 25);
		mcl.addActionListener(new ButtonListener());
		
		JButton msl = new JButton("Set MSL");
		msl.setBounds(40, 30, 80, 25);
		msl.addActionListener(new ButtonListener());
		
		JButton fileselector = new JButton("Select Transaction file");
		fileselector.setBounds(40, 30, 80, 25);
		fileselector.addActionListener(new ButtonListener());
		
	
		
		
		
		
		
		
		
	}
	class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JButton o = (JButton) e.getSource();
            String label = o.getText();
            //statusbar.setText(" " + label + " button clicked");
        }
    }
	
	public static void main (String[] args){
		
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				gui test = new gui();
				test.setVisible(true);
				
			}
			
		});
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}


//400 by 400
/**
 * @author johnskie
 */

