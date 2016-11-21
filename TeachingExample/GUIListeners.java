package TeachingExample;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

//All the ActionListeners for the GUI
public class GUIListeners{

	protected class AddCar implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("AddCar");
			InventoryGUI.setTextPanel("");
			HandleVehicle.addCar(InventoryGUI.getCarFields());
		}
	}
	
	protected class AddSUV implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("AddSUV");
			InventoryGUI.setTextPanel("");
			HandleVehicle.addSUV(InventoryGUI.getSUVFields());
		}
	}
	
	protected class PrintCarInformation implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("PrintCarInformation");
			HandleVehicle.printCarInformation();
		}
	}
	
	protected class PrintCarCost implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("PrintCarCost");
			HandleVehicle.printCarCost();
		}
	}

	protected class PrintDataDump implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("PrintDataDump");
			HandleVehicle.printDataDump();
		}
	}

	protected class ReadFile implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("ReadFile");
			HandleVehicle.readFile();
		}
	}
	
	protected class DataDumpToFile implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("DataDumpToFile");
			HandleVehicle.dataDumpToFile();
		}
	}

	protected class SearchHashMap implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("SearchHashMap");
			HandleVehicle.searchHashMap(InventoryGUI.getSearchFields());
		}
	}

	//This will be where the Save Method works
	protected class SaveAsToFile implements ActionListener{
		JFileChooser fc = new JFileChooser();
		public void actionPerformed(ActionEvent e){
			System.out.println("SaveAs");
			//Start at our current working directory
			File workingDirectory = new File(System.getProperty("user.dir"));
			fc.setCurrentDirectory(workingDirectory);

			//Create the Save Window interface
			JFrame saveWindow = new JFrame();
	        saveWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        saveWindow.setSize(280, 100);
	        saveWindow.setLocationRelativeTo(null);
	        saveWindow.setResizable(false);
	        saveWindow.setVisible(true);

	       	JPanel savePanel = new JPanel();
	        savePanel.setLayout(new FlowLayout());
	        JLabel saveMessage = new JLabel("Do you want to save your Inventory?", SwingConstants.CENTER);

	        //We will be using this type of ActionListener because it is a pop-up window
	        JButton saveButton = new JButton("Save");
	        saveButton.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent e){
	        		//fc is the fileChooser that will use the "Save Dialog" function in the JFileChooser
	        		int returnVal = fc.showSaveDialog(null);
	        		if(returnVal == JFileChooser.APPROVE_OPTION){		//Basically if the user presses Save instead of cancels in the Dialog
	        			File f = fc.getSelectedFile();					//We look for the file they have chosen to select
	        			try{
							PrintWriter output = new PrintWriter(f);	//Generic File Printing Statement
							output.write(HandleVehicle.saveFile());
							output.close();
							saveWindow.dispose();
						} catch (IOException er){
							JOptionPane.showMessageDialog(null, "ERROR: IOException - Cannot Write to File", "Save Error", JOptionPane.INFORMATION_MESSAGE);
						}
	        		}
	        	}
	        });
	        JButton cancelSaveButton = new JButton ("Cancel");
	        cancelSaveButton.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent e){
	        		saveWindow.dispose();
	        	}
	        });
	        savePanel.add(saveButton);
	        savePanel.add(cancelSaveButton);
	        saveWindow.add(saveMessage, BorderLayout.CENTER);
	        saveWindow.add(savePanel, BorderLayout.SOUTH);
		}
	}

	protected class UpdateLabel implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Label Updated");
			InventoryGUI.setTextPanel("Please use EXCEPTION: StackCopyOutWorkException");
		}
	}

	//Used to Switch the CardLayout to the Car Page
	protected class ShowCar implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Go to Car Page");
			InventoryGUI.resetFields();
        	InventoryGUI.showCard("Car Panel");
		}
	}

	//Used to Switch the CardLayout to the SUV Page
	protected class ShowSUV implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Go to SUV Page");
			InventoryGUI.resetFields();
			InventoryGUI.showCard("SUV Panel");
		}
	}

	//Used to Switch the CardLayout to the Search Page
	protected class ShowSearch implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Go to Search Page");
			InventoryGUI.resetFields();
			InventoryGUI.showCard("Search");
		}
	}

	protected class CancelFields implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Reset All Fields");
			InventoryGUI.resetFields();
		}
	}
}