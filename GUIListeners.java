package TeachingExample;

import java.awt.event.*;

//All the ActionListeners for the GUI
public class GUIListeners{
	protected class AddCar implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("AddCar");
			//Call on the AddCar Method inside HandleVehicle
		}
	}
	
	protected class AddSUV implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("AddSUV");
			//Call on the AddSUV Method inside HandleVehicle
		}
	}
	
	protected class PrintCarInformation implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("PrintCarInformation");
			//Call on the PrintCarInformation Method inside HandleVehicle
		}
	}
	
	protected class PrintCarCost implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("PrintCarCost");
			//Call on the PrintCarCost Method inside HandleVehicle
		}
	}

	protected class PrintDataDump implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("PrintDataDump");
			//Call on the PrintDataDump Method inside HandleVehicle
		}
	}

	protected class ReadFile implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("ReadFile");
			//Call on the ReadFile Method inside HandleVehicle
		}
	}
	
	protected class DataDumpToFile implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("DataDumpToFile");
			//Call on the DataDumpToFile Method inside HandleVehicle
		}
	}

	protected class SearchHashMap implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("SearchHashMap");
			//Call on the SearchHashMap Method inside HandleVehicle
		}
	}
}