package TeachingExample;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

//All the ActionListeners for the GUI
// Generally speaking, there are many ways to implement GUI Listeners. Many people
// will implement them as a large inner class within their GUI, other people will
// implement them as attachments to their buttons. However, I prefer to modularize
// my code and create an entire new public class that contains all the ActionListeners
// as inner classes
public class GUIListeners{

	//By using inner classes, we don't have to worry about figuring out which button
	// is being pressed like we would if we implemented these inside our GUI
	// the implementation of these ActionListeners will actually be generic and
	// can be used for any button, as long as the buttons implement these Listeners
	// properly. See example in InventoryGUI for proper ActionListener implementation
	protected class AddCar implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("AddCar");		//This allows us to see if button is pressed
			InventoryGUI.setTextPanel("");		//Call on the method setTextPanel() found in my InventoryGUI class
			HandleVehicle.addCar(InventoryGUI.getCarFields());	//Uses the addCar() method in my HandleVehicle class
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
		JFileChooser fc = new JFileChooser();		//Instantiate a new instance of the JFileChooser class
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
	        savePanel.setLayout(new FlowLayout());	//Formatting for the JPanel which will be in the JFrame
	        JLabel saveMessage = new JLabel("Do you want to save your Inventory?", SwingConstants.CENTER);

	        //We will be using this type of ActionListener because it is a pop-up window.
	        // As mentioned before, this is the third type of ActionListener implementation.
	        JButton saveButton = new JButton("Save");
	        saveButton.addActionListener(new ActionListener(){	//We implement the ActionListener as a direct
	        	public void actionPerformed(ActionEvent e){		// attachment to the button itself rather than as an inner class
	        		int returnVal = fc.showSaveDialog(null);	//fc is the fileChooser that will use the "Save Dialog" function in the JFileChooser
	        		if(returnVal == JFileChooser.APPROVE_OPTION){		//Basically if the user presses Save instead of cancels in the Dialog
	        			File f = fc.getSelectedFile();					//We look for the file they have chosen to select
	        			try{
							PrintWriter output = new PrintWriter(f);	//Generic File Printing Statement
							output.write(HandleVehicle.saveFile());
							output.close();
							saveWindow.dispose();						//Once we have finished with the Pop-Up, we want to get rid of it
						} catch (IOException er){
							//Pop-Up for when they try to Save the file but there is a problem with it
							JOptionPane.showMessageDialog(null, "ERROR: IOException - Cannot Write to File", "Save Error", JOptionPane.INFORMATION_MESSAGE);
						}
	        		}
	        	}
	        });
			//Again, we will implement the third type of ActionListener to the Cancel Button due to it being a pop-up
	        JButton cancelSaveButton = new JButton ("Cancel");
	        cancelSaveButton.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent e){
	        		saveWindow.dispose();								//Nothing special here, just closes the entire interface
	        	}
	        });

	        //Add the components
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