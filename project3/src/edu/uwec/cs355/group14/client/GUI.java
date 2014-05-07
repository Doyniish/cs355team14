package edu.uwec.cs355.group14.client;

import java.io.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;

import org.restlet.resource.ClientResource;

import edu.uwec.cs355.group14.common.Result;
import edu.uwec.cs355.group14.common.RuleSet;

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
		JLabel msl = new JLabel("Min. Support Level");
		msl.setBounds(20, 30, 160, 25);
		add(msl);
		
		JLabel mcl = new JLabel("Min. Confidence Level");
		mcl.setBounds(20, 60, 160, 25);
		add(mcl);
		
		JLabel filename = new JLabel("Filepath");
		filename.setBounds(20, 100, 140, 25);
		add(filename);
		
		// Text Fields
		final JTextField msltxt = new JTextField();
		msltxt.setBounds(190, 30, 60, 25);
		add(msltxt);
	
		final JTextField mcltxt = new JTextField();
		mcltxt.setBounds(190, 60, 60, 25);
		add(mcltxt);
		
		final JLabel savedfiletxt = new JLabel();
		savedfiletxt.setBounds(20, 170, 500, 25);
		add(savedfiletxt);
		
		final JTextField filetxt = new JTextField();
		filetxt.setBounds(190, 100, 200, 25);
		add(filetxt);
		
		final JButton openFile = new JButton("Select File");
		openFile.setBounds(80, 100, 100, 25);
		add(openFile);
		
		openFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				JFileChooser fileopen = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("Text Files", "txt");
				fileopen.addChoosableFileFilter(filter);
	                
				int ret = fileopen.showDialog(panel, "Open file");
				if (ret == JFileChooser.APPROVE_OPTION) {
					filetxt.setText(fileopen.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		final JButton run = new JButton("Submit Information");
		run.setBounds(20, 140, 240, 25);
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mclString = mcltxt.getText();
				String mslString = msltxt.getText();
				String resultString = "";
				if(!isValidNumber(mslString)) {
					resultString = "Minimum support level must be a positive number between 0 and 1, inclusive.\n";
				}
				if(!isValidNumber(mclString)) {
					resultString += "Minimum confidence level must be a positive number between 0 and 1, inclusive.\n";
				}
				if(resultString.equals("")) {
					double minConfidenceLevel = Double.parseDouble(mclString);
					double minSupportLevel = Double.parseDouble(mslString);
					String filepath = filetxt.getText();
					
					Result fileTransactions = new Result(filepath, minSupportLevel, minConfidenceLevel);
										
					if(fileTransactions.getErrorLog() == null) {
						ClientResource clientResource = new ClientResource("http://localhost:8111/");
						RuleResource proxy = clientResource.wrap(RuleResource.class);
						
						proxy.store(fileTransactions);
						RuleSet ruleSet = proxy.retrieve();
						resultString += ruleSet.toString();
						savedfiletxt.setText(saveToFile(filepath, resultString));
					} else {
						resultString += fileTransactions.printErrorLog();
					}
				}
				display.setText(resultString);
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
	
	public static boolean isValidNumber(String number) {
		try {
			double input = Double.parseDouble(number);
			return (input >= 0.0 && input <= 1.0);
		} catch (NumberFormatException e) {
			return false; 
		}
	}
	
	public static String saveToFile(String originalFilepath, String ruleSet) {
		String filepath = originalFilepath.substring(0, originalFilepath.length() - 3) + "output.txt";
		FileWriter writer = null;
		
		try {
			writer = new FileWriter(filepath);
			writer.write(ruleSet);
			writer.close();
			filepath = "Output file saved to " + filepath;
			if(ruleSet.equals("")) {
				filepath = "Generated 0 rules. " + filepath;
			}
		} catch (IOException e) {
			e.printStackTrace();
			filepath = "File did not save correctly. " + e;
		}
		return filepath;
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