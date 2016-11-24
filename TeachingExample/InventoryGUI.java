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
	//Much like how we had to create an instantiation of the Exceptions class to utilize the inner classes found inside the
	// Exceptions class, we need to instantiate the GUIListeners class so we can use the inner class ActionListeners found inside
	GUIListeners listener = new GUIListeners();

	//Image Variables
	private static BufferedImage carPic = null, suvPic = null, openPic = null, saveAsPic = null, searchPic = null;
	private static JButton carP, suvP, openP, saveAsP, searchP, labelP;

	//Components of the Main Window
	private static JFrame mainWindow;
	private static JPanel mainPanel1, mainPanel2, mainPanel3, labelPanel, iconPanel, textPanel, cardPanel;
	private static JTextArea textText;
	private static TitledBorder titled;
	private static JLabel mainLabel;

	//Components of the Card Layout
	private static JPanel addCarPanel, addSUVPanel, printCarInfoPanel, printCarCostPanel, searchPanel;
	//Components for the Car Panel inside the Card Panel
	private static JLabel cbrandLabel, cmodelLabel, cyearLabel, ccostLabel;				//There can only be 1 Component per Panel
	private static JTextField cbrandField, cmodelField, cyearField, ccostField;			// this is why you see the same type of Labels and
	private static TextPrompt cbrandPrompt, cmodelPrompt, cyearPrompt, ccostPrompt;		// TextFields for both SUV Panel and Card Panel
	private static JButton csaveButton, ccancelButton;									// It is because we have to create a whole new Component
	private static JPanel saveAndCancelPanelForCar;										// for an entirely new Panel
	//Components for the SUV Panel inside the Card Panel
	private static JLabel sbrandLabel, smodelLabel, syearLabel, scostLabel, seatsLabel, isAllTerrainLabel, tireBrandLabel;
	private static JTextField sbrandField, smodelField, syearField, scostField, seatsField, tireBrandField;
	private static TextPrompt sbrandPrompt, smodelPrompt, syearPrompt, scostPrompt, seatsPrompt, tireBrandPrompt;
	private static JButton ssaveButton, scancelButton;
	private static JPanel saveAndCancelPanelForSUV, terrainPanel;
	private static ButtonGroup yesOrNo;
	private static JRadioButton yesTerrain, noTerrain;
	//Components for the Search Panel inside the Card Panel
	private static JLabel searchBrand, searchModel, searchYear;
	private static JTextField searchBrandField, searchModelField, searchYearField;
	private static TextPrompt searchBrandPrompt, searchModelPrompt, searchYearPrompt;
	private static JButton searchButton, searchCancelButton;
	private static JPanel searchAndCancelPanel;

	//Components for the Menu Bar
	private static JMenu inventoryMenu, printMenu, fileMenu, searchMenu;
	private static JMenuBar menuBar;
	private static JMenuItem addCarItem, addSUVItem, printCarInfoItem, printCarCostItem, printDataDumpItem, readFileItem, fileDataDumpItem, searchItem, saveFileItem;

	//Main Constructor
	//I generally like to modularize everything which is why you see only methods found inside the constructor.
	// You don't necessarily have to do it this way, but for readability and flow sake, I prefer having methods
	// handle all the work and having my constructor call on each method individually
	protected InventoryGUI(){
		super();
		
		getPictures();		//Loads the pictures from my Pictures folder and prepares them for use
		iconScreen();		//Takes the pictures loaded from the getPictures() method and turns them into clickable icons
		menuBar();			//Generates and creates the Menu Bar with the Menu Items inside
		textScreen();		//Creates the TextArea that will be used to display the Users results or any messages
		cardScreen();		//Creates the CardPanel that will contain my CarPanel, SUVPanel, and SearchPanel
		mainWindow();		//Takes all of the components created in the above methods and packs them into one JFrame
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

        //Resizes the pictures using our resize() method to 20 by 20, which is a nice small size
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
        //Adds it to the Inventory Option
        inventoryMenu.add(addCarItem);
        inventoryMenu.add(addSUVItem);
        addCarItem.addActionListener(listener.new ShowCar());
        addSUVItem.addActionListener(listener.new ShowSUV());

        printCarInfoItem = new JMenuItem("Car Info");
        printCarCostItem = new JMenuItem("Average Cost");
        printDataDumpItem = new JMenuItem("Standard Data Dump");
        //Adds it to the Print Option
        printMenu.add(printCarInfoItem);
        printMenu.add(printCarCostItem);
        printMenu.add(printDataDumpItem);
        printCarInfoItem.addActionListener(listener.new PrintCarInformation());
        printCarCostItem.addActionListener(listener.new PrintCarCost());
        printDataDumpItem.addActionListener(listener.new PrintDataDump());

        readFileItem = new JMenuItem("Read From File");
        fileDataDumpItem = new JMenuItem("File Data Dump");
        saveFileItem = new JMenuItem("Save Inventory to File...");
        //Adds it to the File Option
        fileMenu.add(readFileItem);
        fileMenu.add(fileDataDumpItem);
        fileMenu.add(saveFileItem);
        readFileItem.addActionListener(listener.new ReadFile());
        fileDataDumpItem.addActionListener(listener.new DataDumpToFile());
        saveFileItem.addActionListener(listener.new SaveAsToFile());

        searchItem = new JMenuItem("Search via Hashmap");
        //Adds it to the Search Option
        searchMenu.add(searchItem);
        searchItem.addActionListener(listener.new ShowSearch());
	}

	/**
	* We will use a panel in order to hold all the pictures that we want to use as our
	* Pseudo Menu Bar. It not only looks nicer, but allows the user shortcuts to use the commands
	* rather than having them navigate the menu
	**/
	private void iconScreen(){
		//A container to hold the picture icons
		iconPanel = new JPanel();
		iconPanel.setLayout(new GridBagLayout());		//We will be using GridBagLayout
		iconPanel.setSize(15, 50);						//I want to make sure the size of the Panel is small
														// when I add it to the final JFrame
		/**
		* This is an important part of using GridBagLayout. Unlike the other layouts covered in class, GridBagLayout
		* gives you a lot of control of where you want things to be. But in order to utilize this control we have to
		* use the GridBagConstaints class, which is just a supporting class for GridBagLayout that contains the precompiled
		* options of control methods you can use to orientate your components. More about this can be found here:
		* https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
		**/
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
		/**
		* When we are trying to format GridBagLayout, we need to understand that there are already default Constraints
		* We can normally utilize these constraints without ever worrying about them. However, for more finer control
		* options, manipulating the constraints is more ideal
		**/
		//This "fill" constraints is used to determine what to do with with the component if the display area (iconPanel)
		// is larger than the requested size of the component, 20 by 20. In this case, I use BOTH, which basically means,
		// "If the display area is larger than my image component, just fill it up as much as you can"
		g.fill = GridBagConstraints.BOTH;
		//Similar to GridLayout, GridBagLayout utilizes a grid system. The default for the grid system is 1 row and 1 column.
		// However, you can always make this wider by specifying the rows and columns, or just add more components to the panel.
		// In this case, I am adding the Car Picture into the 0th column and 0th row
		g.gridx = 0;
		g.gridy = 0;
		iconPanel.add(carP, g);
		//I will then add the second picture, the SUV picture, into the 1st column, and the 0th row
		g.gridx = 1;
		g.gridy = 0;
		iconPanel.add(suvP, g);
		//This will continue for the remainder of the pictures
		g.gridx = 2;
		g.gridy = 0;
		iconPanel.add(openP, g);
		g.gridx = 3;
		g.gridy = 0;				//NOTE: You cannot add a component to a row or column, if the preceding row and column
		iconPanel.add(saveAsP, g);	// is empty. For example, if you have 3 rows and 3 columns, and only the 2nd row and 2 column
		g.gridx = 4;				// is filled out, you cannot randomly add a component to the 5th row and 5th column because
		g.gridy = 0;				// the 3rd and 4th row/columns are still empty. GridBagLayout will take our component and fill it into
		iconPanel.add(searchP, g);	// the 3rd or 4th row/column respectively.
	}

	/**
	* The output screen for where the information will be displayed
	* This text screen allows scrolling on both sides in case information is too large
	**/
	private static void textScreen(){
		textPanel = new JPanel();
		textText = new JTextArea(15, 50);				//Our TextArea that will display our messages and user output
		titled = new TitledBorder("Direct Output");		//I want to make it look nicer than a generic TextArea so I give it a border
		textText.setLineWrap(true);						//This is for if the line is far too long for the screen
		textText.setEditable(false);					//We don't want them to be able to edit our direct output
		JScrollPane scrollText = new JScrollPane(textText);		//Allows the user to scroll on the TextArea
		scrollText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);	//Scroll
        scrollText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		//Scroll Vertical
        textPanel.add(scrollText);
        textPanel.setBorder(titled);					//Makes it look nicer
	}

	/**
	* Creation of the CardLayout that allows us to switch between the different options
	* that the user may want to use. By creating a stack of panels that we can switch,
	* it prevents pop-ups and awkward input fields that go empty.
	**/
	private void cardScreen(){
		cardPanel = new JPanel();
		cardPanel.setLayout(new CardLayout());		//We define the cardPanel as a CardLayout

		//Create the Cards that we will be adding to the CardLayout Panel
		createCarPanel();
		createSUVPanel();
		createSearchPanel();

		//Add the Cards that we created
		/**
		* Once the cards have been created using the above methods, we have CarPanel, SUVPanel, and searchPanel
		* that have been properly initialized and their components added respectively. So now we need to take the
		* Panels that have all the components and add it to our CardPanel.
		* In order to display which Panel you want to be shown, we have to make sure to give the Panels a unique
		* String identifier as follows cardPanel.add(panelToBeAdded, "UniqueIdentifier");
		**/
		cardPanel.add(addCarPanel, "Car Panel");
		cardPanel.add(addSUVPanel, "SUV Panel");
		cardPanel.add(searchPanel, "Search");
	}

	//Creates the Car Panel
	private void createCarPanel(){
		addCarPanel = new JPanel();
		saveAndCancelPanelForCar = new JPanel();
		addCarPanel.setLayout(new GridBagLayout());				//Once again, we are using the GridBagLayout
		GridBagConstraints g = new GridBagConstraints(); 		// therefore we need the GridBagConstraints again

		//Brand
		cbrandLabel = new JLabel("Brand");
		cbrandLabel.setBorder(new EmptyBorder(0,0,0,5));		//Adds a slight space between the JLabel and JTextField
		g.fill = GridBagConstraints.HORIZONTAL;					//Previously we used BOTH as our "fill" however, we don't
		g.gridx = 0;											// want out components looking square, so instead we use HORIZONTAL
		g.gridy = 0;
		addCarPanel.add(cbrandLabel, g);						//Add the Brand to the 0th column and 0th row
		cbrandField = new JTextField(20);
		cbrandPrompt = new TextPrompt("Toyota", cbrandField);	//Using the TextPrompt class to have the suggested fields
		cbrandPrompt.changeAlpha(0.7f);							//This changes the gradient of the text to make it lighter and distinguishable
		cbrandField.setMargin(new Insets(2, 2, 2, 2)); 			//Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 0;
		addCarPanel.add(cbrandField, g);						//We don't need to set "fill" every time because it is already HORIZONTAL
																// we just need to add the TextField to the 1st column and 0th row
		//Model
		cmodelLabel = new JLabel("Model");
		cmodelLabel.setBorder(new EmptyBorder(0,0,0,5)); 		//Adds a slight space between the JLabel and JTextField
		g.gridx = 0;
		g.gridy = 1;
		addCarPanel.add(cmodelLabel, g);
		cmodelField = new JTextField(20);
		cmodelPrompt = new TextPrompt("Camry", cmodelField);
		cmodelPrompt.changeAlpha(0.7f);
		cmodelField.setMargin(new Insets(2, 2, 2, 2)); 			//Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 1;
		addCarPanel.add(cmodelField, g);

		//Year
		cyearLabel = new JLabel("Year");
		cyearLabel.setBorder(new EmptyBorder(0,0,0,5));
		g.gridx = 0;
		g.gridy = 2;
		addCarPanel.add(cyearLabel, g);
		cyearField = new JTextField(20);
		cyearPrompt = new TextPrompt("2004", cyearField);
		cyearPrompt.changeAlpha(0.7f);
		cyearField.setMargin(new Insets(2, 2, 2, 2));
		g.gridx = 1;
		g.gridy = 2;
		addCarPanel.add(cyearField, g);

		//Cost
		ccostLabel = new JLabel("Cost");
		ccostLabel.setBorder(new EmptyBorder(0,0,0,5));
		g.gridx = 0;
		g.gridy = 3;
		addCarPanel.add(ccostLabel, g);
		ccostField = new JTextField(20);
		ccostPrompt = new TextPrompt("50000", ccostField);
		ccostPrompt.changeAlpha(0.7f);
		ccostField.setMargin(new Insets(2, 2, 2, 2));
		g.gridx = 1;
		g.gridy = 3;
		addCarPanel.add(ccostField, g);

		//Save and Cancel Buttons
		/**
		* My save and cancel buttons are a little different. So far, all our fields have been pretty straightforward, we have two columns
		* one will be for the Label and the other for the corresponding TextField. However, there's a problem here because we don't want the
		* save button to be in one column and the cancel button to be on the other.
		* In order to get around this, I first add the save and cancel button to a NEW Panel called saveAndCancelPanelForCar. Then I will add
		* this panel to the CarPanel. This way, I won't be adding the buttons, but the entire Panel itself to make formatting easier.
		**/
		csaveButton = new JButton("Save");
		csaveButton.setPreferredSize(new Dimension(100, 20));
		csaveButton.addActionListener(listener.new AddCar());
		saveAndCancelPanelForCar.add(csaveButton);
		ccancelButton = new JButton("Cancel");
		ccancelButton.setPreferredSize(new Dimension(100, 20));
		ccancelButton.addActionListener(listener.new CancelFields());
		saveAndCancelPanelForCar.add(ccancelButton);
		
		/**
		* To add the entire panel, I first set gridwidth to be 2. Gridwidth is default set at 1, which means the component you are trying to add
		* will only take up a single column at a time. However, because we don't want both buttons to be squished into 1 column, we tell the
		* layout that this specific panel can be stretched across 2 columns. We could tell it to stretch across 3 columns, or even 0.5 of a column
		* if we want, but for now, stretching across both columns makes our final Save and Cancel Buttons fit nicely
		**/
		g.insets = new Insets(10, 0, 0, 0); 					//Add a bit of spacing between the Buttons
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
		sbrandLabel.setBorder(new EmptyBorder(0,0,0,5));		//Adds a slight space between the JLabel and JTextField
		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridx = 0;
		g.gridy = 0;
		addSUVPanel.add(sbrandLabel, g);
		sbrandField = new JTextField(20);
		sbrandPrompt = new TextPrompt("Toyota", sbrandField);
		sbrandPrompt.changeAlpha(0.7f);
		sbrandField.setMargin(new Insets(2, 2, 2, 2)); 			//Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 0;
		addSUVPanel.add(sbrandField, g);

		//Model
		smodelLabel = new JLabel("Model");
		smodelLabel.setBorder(new EmptyBorder(0,0,0,5));
		g.gridx = 0;
		g.gridy = 1;
		addSUVPanel.add(smodelLabel, g);
		smodelField = new JTextField(20);
		smodelPrompt = new TextPrompt("Camry", smodelField);
		smodelPrompt.changeAlpha(0.7f);
		smodelField.setMargin(new Insets(2, 2, 2, 2));
		g.gridx = 1;
		g.gridy = 1;
		addSUVPanel.add(smodelField, g);

		//Year
		syearLabel = new JLabel("Year");
		syearLabel.setBorder(new EmptyBorder(0,0,0,5));
		g.gridx = 0;
		g.gridy = 2;
		addSUVPanel.add(syearLabel, g);
		syearField = new JTextField(20);
		syearPrompt = new TextPrompt("2004", syearField);
		syearPrompt.changeAlpha(0.7f);
		syearField.setMargin(new Insets(2, 2, 2, 2));
		g.gridx = 1;
		g.gridy = 2;
		addSUVPanel.add(syearField, g);

		//Cost
		scostLabel = new JLabel("Cost");
		scostLabel.setBorder(new EmptyBorder(0,0,0,5));
		g.gridx = 0;
		g.gridy = 3;
		addSUVPanel.add(scostLabel, g);
		scostField = new JTextField(20);
		scostPrompt = new TextPrompt("50000", scostField);
		scostPrompt.changeAlpha(0.7f);
		scostField.setMargin(new Insets(2, 2, 2, 2));
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
		seatsField.setMargin(new Insets(2, 2, 2, 2));
		/**
		* Because this Object is smaller than the other ones, we need to change a few rules. The first thing we need to do is account
		* for certain defaults that come with GridBagLayout. As I said before, GridBagLayout has a lot of defaults and we need to change
		* them if something special arises. The default value for "anchor" is CENTER, which means every component is put into the middle
		* the column or row that it is in. Because THIS specific seat TextField is only meant for a single character, it seems unintuitive
		* to place a large 20 character wide TextField for it, so instead we make it a 2 character textfield earlier and then we want
		* to put it on the VERY LEFT of our column so it doesn't end up in the center. To do this we set it to LINE_START. Furthermore,
		* we don't want it to go filling up the entire column like we did before if the display area size is larger than the component, so
		* in this specific case we will turn off the "fill" and set it to NONE.
		**/
		g.anchor = GridBagConstraints.LINE_START;
		g.fill = GridBagConstraints.NONE;
		g.gridx = 1;
		g.gridy = 4;
		addSUVPanel.add(seatsField, g);

		isAllTerrainLabel = new JLabel("All-Terrain:");
		isAllTerrainLabel.setBorder(new EmptyBorder(0,0,0,5));
		terrainPanel.add(isAllTerrainLabel);
		
		yesTerrain = new JRadioButton("Yes");					//Radio Buttons for the Yes or No Options
		yesTerrain.setSelected(true);
		noTerrain = new JRadioButton("No");		
		yesOrNo = new ButtonGroup();							//We have to group them so they listen to each other
		yesOrNo.add(yesTerrain);
		yesOrNo.add(noTerrain);
		terrainPanel.add(yesTerrain);							//Make sure to add the buttons, not the group
		terrainPanel.add(noTerrain);
		/**
		* Again, we are no longer dealing with just a label and its corresponding TextField. We are now dealing with radio buttons
		* because of this, we need to make sure they again aren't randomly placed in the middle of the column or row, so we set the
		* anchor to the LINE_END which is the VERY RIGHT of the column. We don't need to set "fill" here because "fill" was previously
		* set when we had to deal with the number of seats.
		**/
		g.anchor = GridBagConstraints.LINE_END;
		g.gridx = 1;
		g.gridy = 4;
		addSUVPanel.add(terrainPanel, g);

		//Don't forget to change the rules back once we've finished with it
		g.fill = GridBagConstraints.HORIZONTAL;
		g.anchor = GridBagConstraints.CENTER;

		tireBrandLabel = new JLabel("Tire Brand");
		tireBrandLabel.setBorder(new EmptyBorder(0,0,0,5));
		g.gridx = 0;
		g.gridy = 5;
		addSUVPanel.add(tireBrandLabel, g);
		tireBrandField = new JTextField(20);
		tireBrandPrompt = new TextPrompt("Michelin", tireBrandField);
		tireBrandPrompt.changeAlpha(0.7f);
		tireBrandField.setMargin(new Insets(2, 2, 2, 2));
		g.gridx = 1;
		g.gridy = 5;
		addSUVPanel.add(tireBrandField, g);

		//Save and Cancel Buttons
		ssaveButton = new JButton("Save");
		ssaveButton.setPreferredSize(new Dimension(100, 20));
		ssaveButton.addActionListener(listener.new AddSUV());
		saveAndCancelPanelForSUV.add(ssaveButton);				//Same situation here as with the Car Panel, our Save and Cancel
		scancelButton = new JButton("Cancel");					// buttons won't fit nicely in our two column system, so we just
		scancelButton.setPreferredSize(new Dimension(100, 20));	// create an entire Panel to put them both in and then add the Panel
		scancelButton.addActionListener(listener.new CancelFields());
		saveAndCancelPanelForSUV.add(scancelButton);
		
		g.insets = new Insets(10, 0, 0, 0); 					//Add a bit of spacing between the Buttons
		g.gridwidth = 2;										//Discussed earlier in the Car Panel example
		g.gridx = 0;
		g.gridy = 6;
		addSUVPanel.add(saveAndCancelPanelForSUV, g);
	}

	//Creates the Search Function Panel
	private void createSearchPanel(){
		searchPanel = new JPanel();
		searchAndCancelPanel = new JPanel();
		searchPanel.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints(); 		//Define Constraints

		searchBrand = new JLabel("Brand");
		searchBrand.setBorder(new EmptyBorder(0,0,0,5));		//Adds a slight space between the JLabel and JTextField
		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridx = 0;
		g.gridy = 0;
		searchPanel.add(searchBrand, g);
		searchBrandField = new JTextField(20);
		searchBrandPrompt = new TextPrompt("Honda", searchBrandField);
		searchBrandPrompt.changeAlpha(0.7f);
		searchBrandField.setMargin(new Insets(2, 2, 2, 2)); 	//Makes a slight inset around the JTextField
		g.gridx = 1;
		g.gridy = 0;
		searchPanel.add(searchBrandField, g);

		searchModel = new JLabel("Model");
		searchModel.setBorder(new EmptyBorder(0,0,0,5));
		g.gridx = 0;
		g.gridy = 1;
		searchPanel.add(searchModel, g);
		searchModelField = new JTextField(20);
		searchModelPrompt = new TextPrompt("Civic", searchModelField);
		searchModelPrompt.changeAlpha(0.7f);
		searchModelField.setMargin(new Insets(2, 2, 2, 2));
		g.gridx = 1;
		g.gridy = 1;
		searchPanel.add(searchModelField, g);
		
		searchYear = new JLabel("Year");
		searchYear.setBorder(new EmptyBorder(0,0,0,5));
		g.gridx = 0;
		g.gridy = 3;
		searchPanel.add(searchYear, g);
		searchYearField = new JTextField(20);
		searchYearPrompt = new TextPrompt("2012", searchYearField);
		searchYearPrompt.changeAlpha(0.7f);
		searchYearField.setMargin(new Insets(2, 2, 2, 2));
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
		
		g.insets = new Insets(10, 0, 0, 0);
		g.gridwidth = 2;
		g.gridx = 0;
		g.gridy = 4;
		searchPanel.add(searchAndCancelPanel, g);
	}

	//Put all our components together
	private void mainWindow(){
		mainPanel1 = new JPanel(new GridBagLayout());			//This will be used to hold the Picture Menu, and 
		GridBagConstraints g = new GridBagConstraints();		// will orientate the entire PicturePanel to the TOP
		mainPanel2 = new JPanel();											//We use Y_AXIS here to have all the items
		mainPanel2.setLayout(new BoxLayout(mainPanel2, BoxLayout.Y_AXIS));	// going from Top to Bottom and not Left to Right
		mainPanel3 = new JPanel();								//Used to hold MainPanel1 and move it to the LEFT
		mainLabel = new JLabel("Made by Irenaeus Chan", SwingConstants.CENTER);
		labelPanel = new JPanel();
		labelP = new JButton();

		g.anchor = GridBagConstraints.NORTHWEST;				//Because we want the Picture Menu on the TOP LEFT, we
        g.fill = GridBagConstraints.NONE;						// need to make sure the anchor is on the TOP LEFT side
		g.gridx = 0;											// and set "fill" to NONE to stop it from stretching
		g.gridy = 0;
		mainPanel1.add(iconPanel, g);							//We add the Picture Menu to the 0th row

		mainPanel2.add(cardPanel);								//Puts the two panels together so it is easier to
        mainPanel2.add(textPanel);								// format the entire panel as a whole

        g.gridx = 0;
        g.gridy = 1;
        mainPanel1.add(mainPanel2, g);							//We add both cardPanel and TextPanel to the 1st row
        mainPanel3.add(mainPanel1, BorderLayout.PAGE_START);	//Then we add all of the components to MainPanel3 and have them
        														// all as LEFT justified. This ensures the Picture Menu is on
		mainWindow = new JFrame("Inventory");					// the TOP LEFT and it ensures that the CardPanel and TextPanel
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// are underneath the Picture Menu
        mainWindow.setSize(580, 680);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setResizable(false);							//I don't want user to be able to resize to prevent awkward
        mainWindow.setJMenuBar(menuBar);						// component spacing

        showCard("Car Panel");									//Calls on the showCard() method to show the Car Panel initially

        mainWindow.add(mainPanel3, BorderLayout.LINE_START);	//Add all of the components to the entire JFrame
        labelPanel.add(mainLabel);
        labelP.setOpaque(false);								//Used to add the "Made by Irenaeus Chan"
        labelP.setContentAreaFilled(false);
        labelP.setBorderPainted(false);
        labelP.addActionListener(listener.new UpdateLabel());	//Updates everything and ensures that the components fit together
        labelPanel.add(labelP);
        mainWindow.add(labelPanel, BorderLayout.PAGE_END);
        mainWindow.pack();										//Packs everything together to make sure sizing is normalized
        mainWindow.setVisible(true);							//Set it to be visible. Done!
	}

	/**
	*===============================================================================================
	* These will be the functions that allow another Program to access and manipulate our Variables
	*===============================================================================================
	*
	* These functions are not only helpful for when we want to manipulate GUI component information such as resetting all the
	* the TextFields to empty, but it is also useful to have them accessible outside of the GUI class so other classes such as
	* the GUIListeners class can have access to them. This is why these are set as protected AND static. Static so that we can
	* use these functions without instantiating a whole new GUI, and protected so only the related classes can use the functions
	**/
	protected static void setTextPanel(String textToSet) {
		textText.setText(textToSet);
	}

	protected static void appendToTextPanel(String textToAppend){
		textText.append(textToAppend);
	}

	/**
	* We will use this function to switch between the CardLayouts Cards from OTHER classes
	* I'm not really sure why we have to define it this way, but https://docs.oracle.com/javase/tutorial/uiswing/layout/card.html
	* specifies that in order to show the panel we need to have this type of formatting
	**/
	protected static void showCard(String key) {
		CardLayout cl = (CardLayout)(cardPanel.getLayout());
		cl.show(cardPanel, key);
	}

	//Pass the CarField Values after checking if they are correct
	protected static Car getCarFields(){
		//We get each TextField from the GUI and them passes them into the HandleVehicle checkCarValues() method which returns a car
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

	//A useful function to have and use when you want to basically reset everything back to its initial state
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