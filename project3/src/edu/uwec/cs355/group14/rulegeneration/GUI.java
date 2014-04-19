package edu.uwec.cs355.group14.rulegeneration;

import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextArea display;
	
	public GUI() {
		initUI();
	}

	public final void initUI() {
		panel = new JPanel();
		setTitle("PaulMart Rule Generator - A Priori Algorithm");
		setSize(600,600);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Labels
		JLabel mcl = new JLabel("Min. Confidence Level");
		mcl.setBounds(20, 30, 160, 25);
		add(mcl);
		
		JLabel msl = new JLabel("Min. Support Level");
		msl.setBounds(20, 60, 160, 25);
		add(msl);
		
		JLabel filename = new JLabel("File Path");
		filename.setBounds(20, 100, 140, 25);
		add(filename);
		
		// Text Fields		
		final JTextField mcltxt = new JTextField();
		mcltxt.setBounds(190, 30, 60, 25);
		add(mcltxt);
	
		final JTextField msltxt = new JTextField();
		msltxt.setBounds(190, 60, 60, 25);
		add(msltxt);
		
		final JTextField filetxt = new JTextField();
		filetxt.setBounds(190, 100, 200, 25);
		add(filetxt);
		
		final JButton run = new JButton("Submit Information");
		run.setBounds(20, 140, 240, 25);
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double minConfidenceLevel = Double.parseDouble(mcltxt.getText());
					double minSupportLevel = Double.parseDouble(msltxt.getText());
	            	String filePath = filetxt.getText();
					
//		   			System.out.println("minConfidenceLevel: " + minConfidenceLevel);
//		   			System.out.println("minSupportLevel: " + minSupportLevel);
//		   			System.out.println("filePath: " + filePath);
		   			
		   			Result result = APriori.algorithm(filePath, minConfidenceLevel, minSupportLevel);
		   			System.out.println(result.getAssociationRuleSet());

		   			if(result.getErrorLog() == null) {
		   				display.setText(result.getAssociationRuleSet().toString());
		   			} else {
		   				display.setText(result.getErrorLog().toString());
		   			}
				} finally {
				}
//				System.out.println("run completed");
			}
		});
		add(run);
		
		display = new JTextArea();
		display.setBounds(10, 200, 570, 350);
		display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JScrollPane pane = new JScrollPane();
        pane.getViewport().add(display);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(pane);
        add(panel);
		add(display);
	}

	public static void main (String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				GUI test = new GUI();
				test.setVisible(true);
			}
		});
	}		
}