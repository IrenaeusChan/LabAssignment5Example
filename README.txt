Description:
A simple GUI made for the CIS 2430 Introduction to Object Orientated Programming Course
The GUI is meant to replicate an Inventory system that will be used by a Vehicle Store
to store their inventory. It has basic functions such as opening and loading pre-existing
inventory files, entering new vehicles, and searching for certain vehicles. Users can save
the file for future use or porting. There are multiple functionalities as described by
the specification created for this specific Lab Assignment which will not be covered here.

Files:
Car.java            - Super class holding the vehicles and their attributes
SUV.java            - Subclass of Car.java which specializes in the SUV vehicle
input.txt           - An example Inventory input document
GUIListeners.java   - The collection of listeners used for the GUI, interacts with everything else
Exceptions.java     - A personalized exception library with limited functionality
HandleVehicle.java  - Methods that manipulate the data as well as performing basic functionality
InventoryGUI.java   - The code used for the generation of the GUI as well as some methods
TextPrompt.java     - An external library used for this Demonstration, reference inside
Inventory.java      - The Main Program

Compilation:
In order to use this Program, simply fork this repository and then navigate to the repository.
After which, a simple compilation using "javac TeachingExample\*.java" should compile everything.
There have been known to be some errors, but a fresh compile should not yield in any trouble.
Once the compilation has been completed, run the program by using "java TeachingExample.Inventory"

Limitations:
The current program cannot be resized due to the static components. For future improvements, utilizing
a dynamic setting would probably be better in order to maximize resolution and user preference.
Other limitations of this program include no error checking for input files which are assumed
to be perfect and there is no ability to edit or delete Vehicles once they are entered without
going directly into the file itself and removing it manually. Other limitations include the inability
to see which Vehicles get rejected during the parsing process and limited save functionality (always
prompting the user to select which file they would like to save to rather than remembering).