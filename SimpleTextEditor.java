package cs181_TextEditor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SimpleTextEditor extends JFrame{
	
	//Declarations
	private int fontSize;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JMenuBar menuBar;
	private JMenu fileMenu, fontMenu, aboutMenu, sizeMenu;
	private JMenuItem saveItem, exitItem, smallerItem, largerItem, aboutItem;
	
	//Constructor
	public SimpleTextEditor() {
		
		//Frame set-up
		JFrame frame = new JFrame();
		frame.setTitle("Simple Text Editor");
		frame.setSize(500, 400);
		fontSize = 12;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//TextArea set-up
		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
		scrollPane = new JScrollPane(textArea);		
		add(scrollPane, BorderLayout.CENTER);
		
		//JMenu components 
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		fontMenu = new JMenu("Font");
		aboutMenu = new JMenu("About");
		sizeMenu = new JMenu("Size");
		
		saveItem = new JMenuItem("Save As");
		exitItem = new JMenuItem("Exit");
		smallerItem = new JMenuItem("Smaller");
		largerItem = new JMenuItem("Larger");
		aboutItem = new JMenuItem("About");
		
		//Adds an Action Listener to saveItem and overrides the Action Listener to call saveToFile method
		saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });
		
		//Adds an Action Listener to exitItem and overrides the Action Listener to call the exit method
		exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
		
		//Adds an Action Listener to smallerItem and overrides the Action Listener to call changeFontSize method
		smallerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeFontSize(-2);
            }
        });
		
		//Adds an Action Listener to largerItem and overrides the Action Listener to call changeFontSize method
		largerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	changeFontSize(2);
            }
        });
		
		//Adds an Action Listener to aboutItem and overrides the Action Listener to call showboutDialog method
			aboutItem.addActionListener(new ActionListener() { 
				@Override
		        public void actionPerformed(ActionEvent e) {
		        showAboutDialog();
		        }
		    });
				
		//Adds the menu items to the according menu 
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		sizeMenu.add(smallerItem);
		sizeMenu.add(largerItem);
		fontMenu.add(sizeMenu);
		aboutMenu.add(aboutItem);
		
		//Adds the menus to the menu bar
		menuBar.add(fileMenu);
		menuBar.add(fontMenu);
		menuBar.add(aboutMenu);
		setJMenuBar(menuBar);
		}
	
	//Method: changes the font size with a minimum size of 6
	private void changeFontSize(int change) {
		fontSize += change;
		
		if(fontSize < 6) {
			fontSize = 6; 
		}
			
		textArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
		textArea.repaint();
	}
	
	//Method: opens a new frame that displays text about the coder(me)
	private void showAboutDialog() {
		JFrame aboutFrame = new JFrame();
		aboutFrame.setSize(400, 400);
		
		JTextArea aboutText = new JTextArea("My name is Diana, I am a 1st year student. \nThis is my first Windows-based Java application.");
		aboutText.setEditable(false);
		aboutText.setFont(new Font("Arial", Font.BOLD, 12));
		
		aboutFrame.add(aboutText);
		aboutFrame.setVisible(true);
	}
	
	//Method: saves the text file to the user's computer
	private void saveToFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save As");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setSelectedFile(new File("output.txt"));
		
		int userSelection = fileChooser.showSaveDialog(this);
		if(userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			if(!fileToSave.getName().endsWith(".txt")) {
				fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
			}
			try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
				writer.write(textArea.getText());
				JOptionPane.showMessageDialog(this, "File saved successfully!");
			}catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
