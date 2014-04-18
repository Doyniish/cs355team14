package edu.uwec.cs355.group14.rulegeneration;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class gui extends JFrame{
	private double minConfidenceLevel;
	private double minSupportLevel;
	private String filepath;

	private JPanel panel;
	private JTextArea result;
	public gui() {

		initUI();

	}

	public final void initUI(){

		panel = new JPanel();
		setTitle("Apriori");
		setSize (600,600);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);





		final JTextField mcltxt = new JTextField();
		mcltxt.setBounds(150, 30, 60, 25);
		JButton mcl = new JButton("Set MCL");
		mcl.setBounds(20, 30, 120, 25);

		mcl.addActionListener(new ActionListener()
		   {
		   		public void actionPerformed(ActionEvent ae)
		   		{
		   			final String MinConfLvl = mcltxt.getText();
		   			minConfidenceLevel = Double.parseDouble(MinConfLvl);
		   			System.out.println(minConfidenceLevel);
	   			}
			});






		final JTextField msltxt = new JTextField();
		msltxt.setBounds(150, 60, 60, 25);
		JButton msl = new JButton("Set MSL");
		msl.setBounds(20, 60, 120, 25);
		msl.addActionListener(new ActionListener()
		   {
	   		public void actionPerformed(ActionEvent ae)
	   		{
	   			final String MinSupLvl = msltxt.getText();
	   			minSupportLevel = Double.parseDouble(MinSupLvl);
	   			System.out.println(minSupportLevel);
   			}
		});


		final JTextField filetxt = new JTextField();
		filetxt.setBounds(190,100,200,25);
		JButton filenamer = new JButton("Set filename");
		filenamer.setBounds(20, 100, 140, 25);
		filenamer.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
            	filepath = filetxt.getText();
	   			System.out.println(filepath);
            }
        });

		JButton run = new JButton("Submit Information");
		run.setBounds(20,140, 240, 25);
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	           try {
				Result result = APriori.algorithm(filepath, minConfidenceLevel, minSupportLevel);
				if(result.getErrorLog() != null) {
					// display transaction
				} else {
					// display error log
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}//variables need to be returned outside of local scope?
	            System.out.println("data sent to algorithm");
	      //      try (FileReader fr = new FileReader(APriori.algorithm(result))){;
	       //     		result.read(fr, null);
	      //      } //this comment block is attempting to read in the result file from 
	            //the apriori algorithm and display it to the result textarea on button click.

		}
	});
		JTextArea result = new JTextArea();
		result.setBounds(10,200 ,570 , 350);
		result.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JScrollPane pane = new JScrollPane();
        pane.getViewport().add(result);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(pane);
        add(panel);	

		add(mcl);
		add(mcltxt);
        add(msl);
        add(msltxt);
        add(filenamer);
        add(filetxt);
        add(run);
		add(result);

       add(panel);

	}
	//submit button run Apriori.algorithm(filepath, minConfidenceLevel, minSupportLevel)



	public String readFile(File file) {

        StringBuffer fileBuffer = null;
        String fileString = null;
        String line = null;

        try {
            FileReader in = new FileReader(file);
            BufferedReader brd = new BufferedReader(in);
            fileBuffer = new StringBuffer();

            while ((line = brd.readLine()) != null) {
                fileBuffer.append(line).append(
                        System.getProperty("line.separator"));
            }

            in.close();
            fileString = fileBuffer.toString();
        } catch (IOException e) {
            return null;
        }
        return fileString;
    }
	public static void main (String[] args){

		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				gui test = new gui();
				test.setVisible(true);

			}

		});
<<<<<<< HEAD

	}}
=======
		
	}}
>>>>>>> 95fc4c430f18d0317a788a0c84ce06e611059184
