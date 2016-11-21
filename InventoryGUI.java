package TeachingExample;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class InventoryGUI extends JFrame{

	//For ALL Event Handling
	GUIListeners listener = new GUIListeners();

	//Image Variables
	private static BufferedImage carPic = null, suvPic = null, openPic = null, savePic = null, saveAsPic = null, searchPic = null;
	private static JButton carP, suvP, openP, saveP, saveAsP, searchP;

	//The main window
	private static JFrame mainWindow;
	private static JPanel mainPanel1, mainPanel2, mainPanel3, iconPanel, textPanel, cardPanel;
	private static JPanel addCarPanel, addSUVPanel, printCarInfoPanel, printCarCostPanel, searchPanel;
	private static JTextArea textText;
	private static TitledBorder titled;

	//Menu Variables
	private static JMenu inventoryMenu, printMenu, fileMenu, searchMenu;
	private static JMenuBar menuBar;
	private static JMenuItem addCarItem, addSUVItem, printCarInfoItem, printCarCostItem, printDataDumpItem, readFileItem, fileDataDumpItem, searchItem;

	//Individual Components
	private static JLabel mainLabel, brandLabel, modelLabel, yearLabel, costLabel, seatsLabel, isAllTerrainLabel, tireBrandLabel;

	//Main Constructor
	protected InventoryGUI(){
		super();
		
		getPictures();
		iconScreen();
		menuBar();
		textScreen();
		
		cardScreen();	//Incomplete
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
            carPic = ImageIO.read(new File("Pictures/car.png"));
            suvPic = ImageIO.read(new File("Pictures/suv.jpg"));
            openPic = ImageIO.read(new File("Pictures/open.gif"));
            savePic = ImageIO.read(new File("Pictures/save.png"));
            saveAsPic = ImageIO.read(new File("Pictures/saveAs.png"));
            searchPic = ImageIO.read(new File("Pictures/search.png"));
            System.out.println("Images have been loaded...");
        } catch (IOException e) {
            System.out.println("Error: Images are either missing or cannot be accessed.");
            System.exit(0);
        }

        //Resize
        carPic = resize(carPic, 20, 20);
        suvPic = resize(suvPic, 20, 20);
        openPic = resize(openPic, 20, 20);
        savePic = resize(savePic, 20, 20);
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
        addCarItem.addActionListener(listener.new AddCar());
        addSUVItem.addActionListener(listener.new AddSUV());

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
        fileMenu.add(readFileItem);
        fileMenu.add(fileDataDumpItem);
        readFileItem.addActionListener(listener.new ReadFile());
        fileDataDumpItem.addActionListener(listener.new DataDumpToFile());

        searchItem = new JMenuItem("Search via Hashmap");
        searchMenu.add(searchItem);
        searchItem.addActionListener(listener.new SearchHashMap());
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
		carP.setPreferredSize(new Dimension(20, 20));
		carP.addActionListener(listener.new AddCar());

		suvP = new JButton(new ImageIcon(suvPic));
		suvP.setPreferredSize(new Dimension(20, 20));
		suvP.addActionListener(listener.new AddSUV());

		openP = new JButton(new ImageIcon(openPic));
		openP.setPreferredSize(new Dimension(20, 20));
		openP.addActionListener(listener.new ReadFile());

		saveP = new JButton(new ImageIcon(savePic));
		saveP.setPreferredSize(new Dimension(20, 20));
		saveP.addActionListener(listener.new DataDumpToFile());

		saveAsP = new JButton(new ImageIcon(saveAsPic));
		saveAsP.setPreferredSize(new Dimension(20, 20));
		saveAsP.addActionListener(listener.new DataDumpToFile());

		searchP = new JButton(new ImageIcon(searchPic));
		searchP.setPreferredSize(new Dimension(20, 20));
		searchP.addActionListener(listener.new SearchHashMap());

		//Formatting
		g.fill = GridBagConstraints.BOTH;
		g.gridx = 0;
		g.gridy = 0;
		iconPanel.add(carP, g);
		g.fill = GridBagConstraints.BOTH;
		g.gridx = 1;
		g.gridy = 0;
		iconPanel.add(suvP, g);
		g.fill = GridBagConstraints.BOTH;
		g.gridx = 2;
		g.gridy = 0;
		iconPanel.add(openP, g);
		g.fill = GridBagConstraints.BOTH;
		g.gridx = 3;
		g.gridy = 0;
		iconPanel.add(saveP, g);
		g.fill = GridBagConstraints.BOTH;
		g.gridx = 4;
		g.gridy = 0;
		iconPanel.add(saveAsP, g);
		g.fill = GridBagConstraints.BOTH;
		g.gridx = 5;
		g.gridy = 0;
		iconPanel.add(searchP, g);
	}

	/**
	* The output screen for where the information will be displayed
	* This text screen allows scrolling on both sides in case information is too large
	**/
	private void textScreen(){
		textPanel = new JPanel();
		textText = new JTextArea(15, 50);
		titled = new TitledBorder("Direct Output");
		textText.setLineWrap(true);
		JScrollPane scrollText = new JScrollPane(textText);
		scrollText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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

		createCarPanel();
		createSUVPanel();
		createPrintCarInforPanel();
		createPrintCarCostPanel();
		createSearchPanel();

		cardPanel.add(addCarPanel, "Car Panel");
		cardPanel.add(addSUVPanel, "SUV Panel");
		//cardPanel.add(printCarInfoPanel, "Car Information");
	}

	//Creates the Car Panel
	private void createCarPanel(){
		addCarPanel = new JPanel();
		addCarPanel.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints(); //Define Constraints

		brandLabel = new JLabel("Brand");
		brandLabel.setSize(70, 40);
		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridwidth = 1;
		g.gridx = 0;
		g.gridy = 0;
		addCarPanel.add(brandLabel, g);

		modelLabel = new JLabel("Model");
		modelLabel.setSize(70, 40);
		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridwidth = 1;
		g.gridx = 2;
		g.gridy = 0;
		addCarPanel.add(modelLabel, g);
	}

	//Creates the SUV Panel
	private void createSUVPanel(){
		addSUVPanel = new JPanel();
		addSUVPanel.setLayout(new GridBagLayout());

		brandLabel = new JLabel("Brand");
		brandLabel.setLocation(0, 0);
		brandLabel.setSize(15, 15);
		brandLabel.setHorizontalAlignment(4);
		addSUVPanel.add(brandLabel);

		modelLabel = new JLabel("Model");
		modelLabel.setLocation(0, 10);
		modelLabel.setSize(15, 15);
		modelLabel.setHorizontalAlignment(4);
		addSUVPanel.add(modelLabel);
	}

	//Creates the Information Panel
	private void createPrintCarInforPanel(){
		printCarInfoPanel = new JPanel();
	}

	//Creates the Print Panel
	private void createPrintCarCostPanel(){
		printCarCostPanel = new JPanel();
	}

	//Creates the Search Function Panel
	private void createSearchPanel(){
		searchPanel = new JPanel();
	}

	//Put all our components together
	private void mainWindow(){
		mainPanel1 = new JPanel(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		mainPanel2 = new JPanel();
		mainPanel2.setLayout(new BoxLayout(mainPanel2, BoxLayout.Y_AXIS));
		mainPanel3 = new JPanel();
		mainLabel = new JLabel("Made by Irenaeus Chan", SwingConstants.CENTER);

		g.gridx = 0;
		g.gridy = 0;
		g.anchor = GridBagConstraints.NORTHWEST;
		mainPanel1.add(iconPanel, g);
		mainPanel3.add(mainPanel1, BorderLayout.LINE_START);

		mainPanel2.add(cardPanel);
        mainPanel2.add(textPanel);

		mainWindow = new JFrame("Inventory");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(580, 680);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setResizable(false);
        mainWindow.setJMenuBar(menuBar);

        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, "Car Panel");

        mainWindow.add(mainPanel3, BorderLayout.LINE_START);
        mainWindow.add(mainPanel2, BorderLayout.CENTER);
        mainWindow.add(mainLabel, BorderLayout.PAGE_END);

        mainWindow.pack();
        mainWindow.setVisible(true);	
	}
}