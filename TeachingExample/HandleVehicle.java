package TeachingExample;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.lang.Math;
import TeachingExample.Exceptions.*;

public class HandleVehicle{
	private static HashMap<String, Car> inventory = new HashMap<String, Car>();

	/**
	* We need a good way to get the values in the fields so we can create our Car Object
	* In this function we will parse each Field and do some simple Error Checks
	* Once we are sure that the values are proper and we can create our Car Object we will
	* create the Car Object and then return it to whoever needs it. Or else, NULL
	**/
	protected static Car checkCarValues(String cbrandField, String cmodelField, String cyearField, String ccostField){
		Exceptions myException = new Exceptions();
		String[] brand, model;
		int year;
		double cost;
		Car userCar;
		try{
			brand = cbrandField.split("\\s+");
			if (brand.length > 1 || brand[0].equals("")){	//Check if the word is less than one or empty
				throw myException.new SingleWordException("ERROR: Please Enter a Single Word for your Brand\n");
			}
			model = cmodelField.split("\\s+");
			if (model.length > 1 || model[0].equals("")){	//Check if the word is less than one or empty
				throw myException.new SingleWordException("ERROR: Please Enter a Single Word for your Model\n");
			}
			year = Integer.parseInt(cyearField.toLowerCase());
			if (ccostField.toLowerCase().equals("")){		//Looks for the Default Price Possibility
				userCar = new Car(brand[0].toLowerCase(), model[0].toLowerCase(), year);
			} else {
				cost = Double.parseDouble(ccostField.toLowerCase());
				userCar = new Car(brand[0].toLowerCase(), model[0].toLowerCase(), year, cost);
			}
			return userCar;
		} catch (NumberFormatException nfe){
			InventoryGUI.appendToTextPanel("ERROR: Please double check your entry for proper input.\n");
		} catch (YearToLowException ytle){
			InventoryGUI.appendToTextPanel(ytle.getMessage());
		} catch (PriceToLowException ptle){
			InventoryGUI.appendToTextPanel(ptle.getMessage());
		} catch (SingleWordException swe){
			InventoryGUI.appendToTextPanel(swe.getMessage());
		}
		return null;
	}

	/**
	* We need a good way to get the values in the fields so we can create our SUV Object
	* In this function we will parse each Field and do some simple Error Checks
	* Once we are sure that the values are proper and we can create our SUV Object we will
	* create the SUV Object and then return it to whoever needs it. Or else, NULL
	**/
	protected static SUV checkSUVValues(String sbrandField, String smodelField, String syearField, String seatsField, String tireBrandField, String buttonSelection, String scostField){
		Exceptions myException = new Exceptions();
		String[] brand, model, tireBrand;
		int year, seats;
		double cost;
		boolean isAllTerran;
		SUV userSUV;
		try{
			brand = sbrandField.split("\\s+");
			if (brand.length > 1 || brand[0].equals("")){	//Check if the word is less than one or empty
				throw myException.new SingleWordException("ERROR: Please Enter a Single Word for your Brand\n");
			}
			model = smodelField.split("\\s+");
			if (model.length > 1 || model[0].equals("")){	//Check if the word is less than one or empty
				throw myException.new SingleWordException("ERROR: Please Enter a Single Word for your Model\n");
			}
			year = Integer.parseInt(syearField.toLowerCase());
			seats = Integer.parseInt(seatsField.toLowerCase());
			tireBrand = tireBrandField.split("\\s+");
			if (tireBrand.length > 1 || tireBrand[0].equals("")){
				throw myException.new SingleWordException("ERROR: Please Enter a Single Word for your TireBrand\n");
			}
			if (buttonSelection.equals("true")){			//Needs to know about the Boolean Value
				isAllTerran = true;
			} else {
				isAllTerran = false;
			}
			if (scostField.toLowerCase().equals("")){		//Looks for the Default Price Possibility
				userSUV = new SUV(brand[0].toLowerCase(), model[0].toLowerCase(), year, seats, isAllTerran, tireBrand[0].toLowerCase());
			} else {
				cost = Double.parseDouble(scostField.toLowerCase());
				userSUV = new SUV(brand[0].toLowerCase(), model[0].toLowerCase(), year, cost, seats, isAllTerran, tireBrand[0].toLowerCase());
			}
			return userSUV;
		} catch (NumberFormatException nfe){
			InventoryGUI.appendToTextPanel("ERROR: Please double check your entry for proper input.\n");
		} catch (YearToLowException ytle){
			InventoryGUI.appendToTextPanel(ytle.getMessage());
		} catch (PriceToLowException ptle){
			InventoryGUI.appendToTextPanel(ptle.getMessage());
		} catch (NegativeSeatsException nse){
			InventoryGUI.appendToTextPanel(nse.getMessage());
		} catch (SingleWordException swe){
			InventoryGUI.appendToTextPanel(swe.getMessage());
		}
		return null;
	}

	/**
	* A method that will take any valid car that has been already checked by the checkCarValues method
	* and will add that care to the HashMap only if another car with the same attributes does not exist
	**/
	protected static void addCar(Car userCar){
		Exceptions myException = new Exceptions();
		String key;
		if (userCar == null){
			InventoryGUI.appendToTextPanel("Car was NOT entered.\n");
		} else {
			try{
				key = userCar.getBrand() + userCar.getModel() + userCar.getYear();
				if (inventory.get(key) != null){
					throw myException.new RepeatKeyException("ERROR: This Car you are trying to enter already exists.\n");
				} else {
					inventory.put(key, userCar);
					InventoryGUI.appendToTextPanel("Car was SAVED successfully.\n");
				}
			} catch (RepeatKeyException rke){
				InventoryGUI.appendToTextPanel(rke.getMessage());
			}
		}
	}

	/**
	* A method that will take any valid car that has been already checked by the checkSUVValues method
	* and will add that care to the HashMap only if another SUV with the same attributes does not exist
	**/
	protected static void addSUV(SUV userSUV){
		Exceptions myException = new Exceptions();
		String key;
		if (userSUV == null){
			InventoryGUI.appendToTextPanel("SUV was NOT entered.\n");
		} else {
			try{
				key = userSUV.getBrand() + userSUV.getModel() + userSUV.getYear();
				if (inventory.get(key) != null){
					throw myException.new RepeatKeyException("ERROR: This SUV you are trying to enter already exists.\n");
				} else {
					inventory.put(key, userSUV);
					InventoryGUI.appendToTextPanel("SUV was SAVED successfully.\n");
				}
			} catch (RepeatKeyException rke){
				InventoryGUI.appendToTextPanel(rke.getMessage());
			}
		}
	}

	//Generic Print Car Information Method
	protected static void printCarInformation(){
		InventoryGUI.setTextPanel("");
		for (Car value : inventory.values()){
			InventoryGUI.appendToTextPanel(value.getBrand() + " " + value.getModel() + " " + value.getYear() + "\n");
		}
	}

	//Generic Print Cost Method
	protected static void printCarCost(){
		double total = 0, average;
		InventoryGUI.setTextPanel("");
		for (Car value : inventory.values()){
			total += value.getPrice();
		}
		average = Math.round(total/inventory.size() * 100D)/100D;		//This will ensure that the double value is rounded to 2 decimal places
		InventoryGUI.appendToTextPanel("There are " + inventory.size() + " vehicles in your Inventory.\nIt is worth $" + average + " on average.\n");
	}

	//Generic DataDump Method
	protected static void printDataDump(){
		InventoryGUI.setTextPanel("");
		for (Car value : inventory.values()){
			InventoryGUI.appendToTextPanel(value.dataDump() + "\n");
		}
	}

	protected static void readFile(){
		JFileChooser fc = new JFileChooser();
		String[] fileValues;
		//Make sure we look in the current Directory
		File workingDirectory = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(workingDirectory);
		//fc is the fileChooser that will use the "Open Dialog" function in the JFileChooser
		int returnVal = fc.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION){		//If their selection was Open
			File f = fc.getSelectedFile();					//Obtain the actual file that was desired to be opened
			try{
				//Reading the File
				Scanner scanner = new Scanner(f);
				InventoryGUI.setTextPanel("UPDATE: File has been read successfully.\n");
				while(scanner.hasNextLine()){				//Normal File Reading
					fileValues = scanner.nextLine().toLowerCase().split("\\s+");	//Split the Input File, we assume perfect format
					//We need to remember that [0] = Brand, [1] = Model, [2] = Year, [3] = Cost, [4] = isSUV, [5] = seats, [6] = terrain, [7] = tireBrand
					if (fileValues[4].equals("1")){
						//Check the SUV Values
						SUV userSUV = checkSUVValues(fileValues[0], fileValues[1], fileValues[2], fileValues[5], fileValues[7], fileValues[6], fileValues[3]);
						addSUV(userSUV);	//Add to our HashMap
					} else {
						//Check the Car Values
						Car userCar = checkCarValues(fileValues[0], fileValues[1], fileValues[2], fileValues[3]);
						addCar(userCar);	//Add to our HashMap
					}
				}
			} catch (FileNotFoundException er){
                JOptionPane.showMessageDialog(null, "ERROR: File does not exist", "File Not Found", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	protected static void dataDumpToFile(){
		try{
			PrintWriter output = new PrintWriter(new File("output.txt"));	//Open a PrintWriter to write to
			for (Car value : inventory.values()){		//For every value inside our HashMap
				output.write(value.dataDump() + "\n");	//Write it
			}
			output.close();
			InventoryGUI.setTextPanel("UPDATE: Your Inventory has SUCCESSFULLY been dumped to a file.\n");
		} catch (FileNotFoundException fne){
			InventoryGUI.setTextPanel("UPDATE: An error occurred writing to the file. Your Inventory was not dumped correctly.\n");
		}
	}

	//Organizes the Inventory so we can save it for future use
	protected static String saveFile(){
		String toFile = "";
		for (Car value : inventory.values()){
			toFile += value.toString() + "\n";
		}
		return toFile;
	}

	//Generic HashMap Search Method
	protected static void searchHashMap(String[] searchTerms){
		Exceptions myException = new Exceptions();
		String[] brand, model;
		int year;
		InventoryGUI.setTextPanel("");
		try{
			brand = searchTerms[0].split("\\s+");
			if (brand.length > 1 || brand[0].equals("")){	//Check if the word is less than one or empty
				throw myException.new SingleWordException("ERROR: Please Enter a Single Word for your Brand\n");
			}
			model = searchTerms[1].split("\\s+");
			if (model.length > 1 || model[0].equals("")){	//Check if the word is less than one or empty
				throw myException.new SingleWordException("ERROR: Please Enter a Single Word for your Model\n");
			}
			year = Integer.parseInt(searchTerms[2].toLowerCase());
			String key = brand[0].toLowerCase() + model[0].toLowerCase() + year;	//Makes the Key
			Car tempVehicle = inventory.get(key);	//Puts the Value returned from the Hash search into a temporary variable
			if (tempVehicle != null){
				InventoryGUI.appendToTextPanel("The Vehicle that was found:\n" + tempVehicle.getBrand() + " " + tempVehicle.getModel() 
												+ " " + tempVehicle.getYear() + " " + tempVehicle.getPrice());
			} else {
				InventoryGUI.appendToTextPanel("There was no vehicles found that matched your description.\n");
			}
			
		} catch (SingleWordException swe){
			InventoryGUI.appendToTextPanel(swe.getMessage());
		} catch (NumberFormatException nfe){
			InventoryGUI.appendToTextPanel("ERROR: Please double check your entry for Year.\n");
		}
	}
}