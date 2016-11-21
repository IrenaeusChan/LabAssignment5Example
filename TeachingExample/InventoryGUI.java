package TeachingExample;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;
import javax.imageio.ImageIO;

public class InventoryGUI extends JFrame{

	//For ALL Event Handling
	GUIListeners listener = new GUIListeners();

	//Image Variables
	private static BufferedImage carPic = null, suvPic = null, openPic = null, saveAsPic = null, searchPic = null;
	private static JButton carP, suvP, openP, saveAsP, searchP;

	//The Main Window
	private static JFrame mainWindow;
	private static JPanel mainPanel1, mainPanel2, mainPanel3, iconPanel, textPanel, cardPanel;
	private static JTextArea textText;
	private static TitledBorder titled;
	private static JLabel mainLabel;

	//Card Components
	private static JPanel addCarPanel, addSUVPanel, printCarInfoPanel, printCarCostPanel, searchPanel;
	//For Car
	private static JLabel cbrandLabel, cmodelLabel, cyearLabel, ccostLabel;
	private static JTextField cbrandField, cmodelField, cyearField, ccostField;
	private static TextPrompt cbrandPrompt, cmodelPrompt, cyearPrompt, ccostPrompt;
	private static JButton csaveButton, ccancelButton;
	private static JPanel saveAndCancelPanelForCar;
	//For SUV
	private static JLabel sbrandLabel, smodelLabel, syearLabel, scostLabel, seatsLabel, isAllTerrainLabel, tireBrandLabel;
	private static JTextField sbrandField, smodelField, syearField, scostField, seatsField, tireBrandField;
	private static TextPrompt sbrandPrompt, smodelPrompt, syearPrompt, scostPrompt, seatsPrompt, tireBrandPrompt;
	private static JButton ssaveButton, scancelButton;
	private static JPanel saveAndCancelPanelForSUV, terrainPanel;
	private static ButtonGroup yesOrNo;
	private static JRadioButton yesTerrain, noTerrain;
	//For Search
	private static JLabel searchBrand, searchModel, searchYear;
	private static JTextField searchBrandField, searchModelField, searchYearField;
	private static TextPrompt searchBrandPrompt, searchModelPrompt, searchYearPrompt;
	private static JButton searchButton, searchCancelButton;
	private static JPanel searchAndCancelPanel;

	//Menu Variables
	private static JMenu inventoryMenu, printMenu, fileMenu, searchMenu;
	private static JMenuBar menuBar;
	private static JMenuItem addCarItem, addSUVItem, printCarInfoItem, printCarCostItem, printDataDumpItem, readFileItem, fileDataDumpItem, searchItem, saveFileItem;

	//Main Constructor
	protected InventoryGUI(){
		super();
		
		getPictures();
		iconScreen();
		menuBar();
		textScreen();
		
		cardScreen();
		mainWindow();
	}

	/**
     * Since the pictures obtained are not to given size, this function will resize the BufferedImage
     * into a smaller compressed scaled version and dispose of the original version. It will then return
     * the new compressed version for further use.
     **/
	private BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmpImage = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage userImgage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = userImgage.createGraphics();
        g2d.drawImage(tmpImage, 0, 0, null);
        g2d.dispose();

        return userImgage;
    }

    /**
    * We need to obtain the pictures that we will be using for our GUI from our Pictures folder.
    * This function will attempt to open the pictures, if the picture doesn't exist or it is in the wrong
    * format then it will through an IOException, otherwise, the picture will be obtained and resized.
    **/
	private void getPictures() {
        try {
        	//Loads in the pictures from the Pictures Folder
            carPic = ImageIO.read(new File("Pictures/car.png"));
            suvPic = ImageIO.read(new File("Pictures/suv.jpg"));
            openPic = ImageIO.read(new File("Pictures/open.gif"));
            saveAsPic = ImageIO.read(new File("Pictures/saveAs.png"));
            searchPic = ImageIO.read(new File("Pictures/search.png"));
            System.out.println("Images have been loaded...");
        } catch (IOException e) {
            System.out.println("Error: Images are either missing or cannot be accessed.");
            System.exit(0);
        }

        //Resizes the pictures to 20 by 20, which is a nice small size
        carPic = resize(carPic, 20, 20);
        suvPic = resize(suvPic, 20, 20);
        openPic = resize(openPic, 20, 20);
        saveAsPic = resize(saveAsPic, 20, 20);
        searchPic = resize(searchPic, 20, 20);
    }

    /**
	* Creates the MenuBar that will be used for the GUI
	* Due to the complexity of our program, there isn't much to do in the case of Menu Items
	* However, grouping them into four distinct categories makes it more intuitive
    **/
    private void menuBar(){
		//MenuBar
        menuBar = new JMenuBar();

        //Create the Menu Options
        inventoryMenu = new JMenu("Invetory");
        printMenu = new JMenu("Print");
        fileMenu = new JMenu("File");
        searchMenu = new JMenu("Search");
        
        //Add them to the Menu Bar
        menuBar.add(inventoryMenu);
        menuBar.add(printMenu);
        menuBar.add(fileMenu);
        menuBar.add(searchMenu);

        //Give each Menu Option a dropdown list
        addCarItem = new JMenuItem("Add Car...");
        addSUVItem = new JMenuItem("Add SUV...");
        inventoryMenu.add(addCarItem);
        inventoryMenu.add(addSUVItem);
        addCarItem.addActionListener(listener.new ShowCar());
        addSUVItem.addActionListener(listener.new ShowSUV());

        printCarInfoItem = new JMenuItem("Car Info");
        printCarCostItem = new JMenuItem("Average Cost");
        printDataDumpItem = new JMenuItem("Standard Data Dump");
        printMenu.add(printCarInfoItem);
        printMenu.add(printCarCostItem);
        printMenu.add(printDataDumpItem);
        printCarInfoItem.addActionListener(listener.new PrintCarInformation());
        printCarCostItem.addActionListener(listener.new PrintCarCost());
        printDataDumpItem.addActionListener(listener.new PrintDataDump());

        readFileItem = new JMenuItem("Read From File");
        fileDataDumpItem = new JMenuItem("File Data Dump");
        saveFileItem = new JMenuItem("Save Inventory to File...");
        fileMenu.add(readFileItem);
        fileMenu.add(fileDataDumpItem);
        fileMenu.add(saveFileItem);
        readFileItem.addActionListener(listener.new ReadFile());
        fileDataDumpItem.addActionListener(listener.new DataDumpToFile());
        saveFileItem.addActionListener(listener.new SaveAsToFile());

        searchItem = new JMenuItem("Search via Hashmap");
        searchMenu.add(searchItem);
        searchItem.addActionListener(listener.new ShowSearch());
	}

	/**
	* We will use a panel in order to hold all the pictures that we want to use as our
	* Pseudo Menu Bar. It not only looks nicer, but allows the user shortcuts to use the commands
	* rather than just having to have them navigate the menu
	**/
	private void iconScreen(){
		//A container to hold the picture icons
		iconPanel = new JPanel();
		iconPanel.setLayout(new GridBagLayout());
		iconPanel.setSize(15, 50);
		GridBagConstraints g = new GridBagConstraints(); //Define Constraints

		//Image Creation And Listeners
		carP = new JButton(new ImageIcon(carPic));
		carP.setPreferredSize(new Dimension(20, 20));	//We want to make sure the size is 20 by 20
		carP.addActionListener(listener.new ShowCar());	// this way it won't interfere with future spacing

		suvP = new JButton(new ImageIcon(suvPic));
		suvP.setPreferredSize(new Dimension(20, 20));
		suvP.addActionListener(listener.new ShowSUV());

		openP = new JButton(new ImageIcon(openPic));
		openP.setPreferredSize(new Dimension(20, 20));
		openP.addActionListener(listener.new ReadFile());

		saveAsP = new JButton(new ImageIcon(saveAsPic));
		saveAsP.setPreferredSize(new Dimension(20, 20));
		saveAsP.addActionListener(listener.new SaveAsToFile());

		searchP = new JButton(new ImageIcon(searchPic));
		searchP.setPreferredSize(new Dimension(20, 20));
		searchP.addActionListener(listener.new ShowSearch());

		//Formatting
		g.fill = GridBagConstraints.BOTH;
		g.gridx = 0;
		g.gridy = 0;
		iconPanel.add(carP, g);
		g.gridx = 1;
		g.gridy = 0;
		iconPanel.add(suvP, g);
		g.gridx = 2;
		g.gridy = 0;
		iconPanel.add(openP, g);
		g.gridx = 3;
		g.gridy = 0;
		iconPanel.add(saveAsP, g);
		g.gridx = 4;
		g.gridy = 0;
		iconPanel.add(searchP, g);
	}

	/**
	* The output screen for where the information will be displayed
	* This text screen allows scrolling on both sides in case information is too large
	**/
	private static void textScreen(){
		textPanel = new JPanel();
		textText = new JTextArea(15, 50);
		titled = new TitledBorder("Direct Output");
		textText.setLineWrap(true);		//This is for if the line is far too long for the screen
		textText.setEditable(false);	//We don't want them to be able to edit our direct output
		JScrollPane scrollText = new JScrollPane(textText);
		scrollText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);	//Scroll
        scrollText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);	//Scroll Vertical
        textPanel.add(scrollText);
        textPanel.setBorder(titled);	//Makes it look nicer
	}

	/**
	* Creation of the CardLayout that allows us to switch between the different options
	* that the user may want to use. By creating a stack of panels that we can switch,
	* it prevents pop-ups and awkward input fields that go empty.
	**/
	private void cardScreen(){
		cardPanel = new JPanel();
		cardPanel.setLayout(new CardLayout());

		//Create the Cards that we will be adding to the CardLayout Panel
		createCarPanel();
		createSUVPanel();
		createSearchPanel();

		//Add the Cards that we created
		cardPanel.add(addCarPanel, "Car Panel");
		cardPanel.add(addSUVPanel, "SUV Panel");
		cardPanel.add(searchPanel, "Search");
	}

	//Creates the Car Panel
	private void createCarPanel(){
		addCarPanel = new JPanel();
		saveAndCancelPanelForCar = new JPanel();
		addCarPanel.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints(); //Define Constraints

		//Brand
		cbrandLabel = new JLabel("Brand");
		cbrandLabel.setBorder(new EmptyBorder(0,0,0,5));	//Adds a slight space between the JLabel and JTextField
		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridx = 0;
		g.gridy = 0;
		addCarPanel.add(cbrandLabel, g);
		cbrandField = new JTextField(20);
		cbrandPrompt = new TextPrompt("Toyota", cbrandField);
		cbrandPrompt.changeAlpha(0.7f);
		cbrandField.setMargin(new Insets(2, 2, 2, 2)); //Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 0;
		addCarPanel.add(cbrandField, g);

		//Model
		cmodelLabel = new JLabel("Model");
		cmodelLabel.setBorder(new EmptyBorder(0,0,0,5)); //Adds a slight space between the JLabel and JTextField
		g.gridx = 0;
		g.gridy = 1;
		addCarPanel.add(cmodelLabel, g);
		cmodelField = new JTextField(20);
		cmodelPrompt = new TextPrompt("Camry", cmodelField);
		cmodelPrompt.changeAlpha(0.7f);
		cmodelField.setMargin(new Insets(2, 2, 2, 2)); //Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 1;
		addCarPanel.add(cmodelField, g);

		//Year
		cyearLabel = new JLabel("Year");
		cyearLabel.setBorder(new EmptyBorder(0,0,0,5));	//Adds a slight space between the JLabel and JTextField
		g.gridx = 0;
		g.gridy = 2;
		addCarPanel.add(cyearLabel, g);
		cyearField = new JTextField(20);
		cyearPrompt = new TextPrompt("2004", cyearField);
		cyearPrompt.changeAlpha(0.7f);
		cyearField.setMargin(new Insets(2, 2, 2, 2)); //Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 2;
		addCarPanel.add(cyearField, g);

		//Cost
		ccostLabel = new JLabel("Cost");
		ccostLabel.setBorder(new EmptyBorder(0,0,0,5));	//Adds a slight space between the JLabel and JTextField
		g.gridx = 0;
		g.gridy = 3;
		addCarPanel.add(ccostLabel, g);
		ccostField = new JTextField(20);
		ccostPrompt = new TextPrompt("50000", ccostField);
		ccostPrompt.changeAlpha(0.7f);
		ccostField.setMargin(new Insets(2, 2, 2, 2)); //Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 3;
		addCarPanel.add(ccostField, g);

		//Save and Cancel Buttons
		csaveButton = new JButton("Save");
		csaveButton.setPreferredSize(new Dimension(100, 20));
		csaveButton.addActionListener(listener.new AddCar());
		saveAndCancelPanelForCar.add(csaveButton);
		ccancelButton = new JButton("Cancel");
		ccancelButton.setPreferredSize(new Dimension(100, 20));
		ccancelButton.addActionListener(listener.new CancelFields());
		saveAndCancelPanelForCar.add(ccancelButton);
		
		g.insets = new Insets(10, 0, 0, 0); //Add a bit of spacing between the Buttons
		g.gridwidth = 2;
		g.gridx = 0;
		g.gridy = 4;
		addCarPanel.add(saveAndCancelPanelForCar, g);
	}

	//Creates the SUV Panel
	//Unfortunately, because each Object can only belong to one Panel... we have to create them all over again
	private void createSUVPanel(){
		addSUVPanel = new JPanel();
		saveAndCancelPanelForSUV = new JPanel();
		terrainPanel = new JPanel();
		addSUVPanel.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints(); //Define Constraints

		//Brand
		sbrandLabel = new JLabel("Brand");
		sbrandLabel.setBorder(new EmptyBorder(0,0,0,5));	//Adds a slight space between the JLabel and JTextField
		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridx = 0;
		g.gridy = 0;
		addSUVPanel.add(sbrandLabel, g);
		sbrandField = new JTextField(20);
		sbrandPrompt = new TextPrompt("Toyota", sbrandField);
		sbrandPrompt.changeAlpha(0.7f);
		sbrandField.setMargin(new Insets(2, 2, 2, 2)); //Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 0;
		addSUVPanel.add(sbrandField, g);

		//Model
		smodelLabel = new JLabel("Model");
		smodelLabel.setBorder(new EmptyBorder(0,0,0,5)); //Adds a slight space between the JLabel and JTextField
		g.gridx = 0;
		g.gridy = 1;
		addSUVPanel.add(smodelLabel, g);
		smodelField = new JTextField(20);
		smodelPrompt = new TextPrompt("Camry", smodelField);
		smodelPrompt.changeAlpha(0.7f);
		smodelField.setMargin(new Insets(2, 2, 2, 2)); //Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 1;
		addSUVPanel.add(smodelField, g);

		//Year
		syearLabel = new JLabel("Year");
		syearLabel.setBorder(new EmptyBorder(0,0,0,5));	//Adds a slight space between the JLabel and JTextField
		g.gridx = 0;
		g.gridy = 2;
		addSUVPanel.add(syearLabel, g);
		syearField = new JTextField(20);
		syearPrompt = new TextPrompt("2004", syearField);
		syearPrompt.changeAlpha(0.7f);
		syearField.setMargin(new Insets(2, 2, 2, 2)); //Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 2;
		addSUVPanel.add(syearField, g);

		//Cost
		scostLabel = new JLabel("Cost");
		scostLabel.setBorder(new EmptyBorder(0,0,0,5));	//Adds a slight space between the JLabel and JTextField
		g.gridx = 0;
		g.gridy = 3;
		addSUVPanel.add(scostLabel, g);
		scostField = new JTextField(20);
		scostPrompt = new TextPrompt("50000", scostField);
		scostPrompt.changeAlpha(0.7f);
		scostField.setMargin(new Insets(2, 2, 2, 2)); //Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 3;
		addSUVPanel.add(scostField, g);

		seatsLabel = new JLabel("Seats");
		seatsLabel.setBorder(new EmptyBorder(0,0,0,5));
		g.gridx = 0;
		g.gridy = 4;
		addSUVPanel.add(seatsLabel, g);

		seatsField = new JTextField(2);
		seatsPrompt = new TextPrompt("4", seatsField);
		seatsPrompt.changeAlpha(0.7f);
		seatsField.setMargin(new Insets(2, 2, 2, 2)); //Makes a slight inset around the JTextField
		//Because this Object is smaller than the other ones, we need to change a few rules
		g.anchor = GridBagConstraints.FIRST_LINE_START;
		g.fill = GridBagConstraints.NONE;
		g.gridx = 1;
		g.gridy = 4;
		addSUVPanel.add(seatsField, g);

		isAllTerrainLabel = new JLabel("All-Terrain:");
		isAllTerrainLabel.setBorder(new EmptyBorder(0,0,0,5));
		terrainPanel.add(isAllTerrainLabel);
		
		//Radio Buttons for the Yes or No Options
		yesTerrain = new JRadioButton("Yes");
		yesTerrain.setSelected(true);
		noTerrain = new JRadioButton("No");
		//We have to group them so they listen to each other
		yesOrNo = new ButtonGroup();
		yesOrNo.add(yesTerrain);
		yesOrNo.add(noTerrain);
		//Make sure to add the buttons, not the group
		terrainPanel.add(yesTerrain);
		terrainPanel.add(noTerrain);
		g.anchor = GridBagConstraints.LINE_END;
		g.gridx = 1;
		g.gridy = 4;
		addSUVPanel.add(terrainPanel, g);

		//Don't forget to change the rules back once we've finished with it
		g.fill = GridBagConstraints.HORIZONTAL;
		g.anchor = GridBagConstraints.CENTER;

		tireBrandLabel = new JLabel("Tire Brand");
		tireBrandLabel.setBorder(new EmptyBorder(0,0,0,5));
		//g.gridwidth = 1;
		g.gridx = 0;
		g.gridy = 5;
		addSUVPanel.add(tireBrandLabel, g);
		tireBrandField = new JTextField(20);
		tireBrandPrompt = new TextPrompt("Michelin", tireBrandField);
		tireBrandPrompt.changeAlpha(0.7f);
		tireBrandField.setMargin(new Insets(2, 2, 2, 2)); //Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 5;
		addSUVPanel.add(tireBrandField, g);

		//Save and Cancel Buttons
		ssaveButton = new JButton("Save");
		ssaveButton.setPreferredSize(new Dimension(100, 20));
		ssaveButton.addActionListener(listener.new AddSUV());
		saveAndCancelPanelForSUV.add(ssaveButton);
		scancelButton = new JButton("Cancel");
		scancelButton.setPreferredSize(new Dimension(100, 20));
		scancelButton.addActionListener(listener.new CancelFields());
		saveAndCancelPanelForSUV.add(scancelButton);
		
		g.insets = new Insets(10, 0, 0, 0); //Add a bit of spacing between the Buttons
		g.gridwidth = 2;
		g.gridx = 0;
		g.gridy = 6;
		addSUVPanel.add(saveAndCancelPanelForSUV, g);
	}

	//Creates the Search Function Panel
	private void createSearchPanel(){
		searchPanel = new JPanel();
		searchAndCancelPanel = new JPanel();
		searchPanel.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints(); //Define Constraints

		searchBrand = new JLabel("Brand");
		searchBrand.setBorder(new EmptyBorder(0,0,0,5));	//Adds a slight space between the JLabel and JTextField
		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridx = 0;
		g.gridy = 0;
		searchPanel.add(searchBrand, g);
		searchBrandField = new JTextField(20);
		searchBrandPrompt = new TextPrompt("Honda", searchBrandField);
		searchBrandPrompt.changeAlpha(0.7f);
		searchBrandField.setMargin(new Insets(2, 2, 2, 2)); //Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 0;
		searchPanel.add(searchBrandField, g);

		searchModel = new JLabel("Model");
		searchModel.setBorder(new EmptyBorder(0,0,0,5));	//Adds a slight space between the JLabel and JTextField
		g.gridx = 0;
		g.gridy = 1;
		searchPanel.add(searchModel, g);
		searchModelField = new JTextField(20);
		searchModelPrompt = new TextPrompt("Civic", searchModelField);
		searchModelPrompt.changeAlpha(0.7f);
		searchModelField.setMargin(new Insets(2, 2, 2, 2)); //Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 1;
		searchPanel.add(searchModelField, g);
		
		searchYear = new JLabel("Year");
		searchYear.setBorder(new EmptyBorder(0,0,0,5));	//Adds a slight space between the JLabel and JTextField
		g.gridx = 0;
		g.gridy = 3;
		searchPanel.add(searchYear, g);
		searchYearField = new JTextField(20);
		searchYearPrompt = new TextPrompt("2012", searchYearField);
		searchYearPrompt.changeAlpha(0.7f);
		searchYearField.setMargin(new Insets(2, 2, 2, 2)); //Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 3;
		searchPanel.add(searchYearField, g);

		//Save and Cancel Buttons
		searchButton = new JButton("Search");
		searchButton.setPreferredSize(new Dimension(100, 20));
		searchButton.addActionListener(listener.new SearchHashMap());
		searchAndCancelPanel.add(searchButton);
		searchCancelButton = new JButton("Cancel");
		searchCancelButton.setPreferredSize(new Dimension(100, 20));
		searchCancelButton.addActionListener(listener.new CancelFields());
		searchAndCancelPanel.add(searchCancelButton);
		
		g.insets = new Insets(10, 0, 0, 0); //Add a bit of spacing between the Buttons
		g.gridwidth = 2;
		g.gridx = 0;
		g.gridy = 4;
		searchPanel.add(searchAndCancelPanel, g);
	}

	//Put all our components together
	private void mainWindow(){
		mainPanel1 = new JPanel(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		mainPanel2 = new JPanel();
		mainPanel2.setLayout(new BoxLayout(mainPanel2, BoxLayout.Y_AXIS));
		mainPanel3 = new JPanel();
		mainLabel = new JLabel("Made by Irenaeus Chan", SwingConstants.CENTER);

		g.anchor = GridBagConstraints.NORTHWEST;
        g.fill = GridBagConstraints.NONE;
		g.gridx = 0;
		g.gridy = 0;
		g.anchor = GridBagConstraints.NORTHWEST;
		mainPanel1.add(iconPanel, g);

		mainPanel2.add(cardPanel);
        mainPanel2.add(textPanel);

        g.gridx = 0;
        g.gridy = 1;
        mainPanel1.add(mainPanel2, g);
        mainPanel3.add(mainPanel1, BorderLayout.PAGE_START);

		mainWindow = new JFrame("Inventory");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(580, 680);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setResizable(false);
        mainWindow.setJMenuBar(menuBar);

        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, "Car Panel");

        mainWindow.add(mainPanel3, BorderLayout.LINE_START);
        mainWindow.add(mainLabel, BorderLayout.PAGE_END);

        mainWindow.pack();
        mainWindow.setVisible(true);	
	}

	/**
	*===============================================================================================
	* These will be the functions that allow another Program to access and manipulate our Variables
	*===============================================================================================
	**/
	protected static void setTextPanel(String textToSet) {
		textText.setText(textToSet);
	}

	protected static void appendToTextPanel(String textToAppend){
		textText.append(textToAppend);
	}

	/**
	* We will use this function to switch between the CardLayouts Cards from OTHER classes
	**/
	protected static void showCard(String key) {
		CardLayout cl = (CardLayout)(cardPanel.getLayout());
		cl.show(cardPanel, key);
	}

	//Pass the CarField Values after checking if they are correct
	protected static Car getCarFields(){
		Car userCar = HandleVehicle.checkCarValues(cbrandField.getText(), cmodelField.getText(), cyearField.getText(), ccostField.getText());
		return userCar;
	}

	//Pass the SUVField Values after checking if they are correct
	protected static SUV getSUVFields(){
		String isAllTerrain;
		if (yesTerrain.isSelected()){
			isAllTerrain = "true";
		} else {
			isAllTerrain = "false";
		}
		SUV userSUV = HandleVehicle.checkSUVValues(sbrandField.getText(), smodelField.getText(), syearField.getText(), seatsField.getText(), tireBrandField.getText(), isAllTerrain, scostField.getText());
		return userSUV;
	}

	//Passes in the values that the user wants to search for
	protected static String[] getSearchFields(){
		String []searchTerms = {searchBrandField.getText(), searchModelField.getText(), searchYearField.getText()};
		return searchTerms;
	}

	protected static void resetFields(){
		cbrandField.setText("");
		cmodelField.setText("");
		cyearField.setText("");
		ccostField.setText("");
		sbrandField.setText("");
		smodelField.setText("");
		syearField.setText("");
		scostField.setText("");
		seatsField.setText("");
		tireBrandField.setText("");
		searchBrandField.setText("");
		searchModelField.setText("");
		searchYearField.setText("");
	}
}