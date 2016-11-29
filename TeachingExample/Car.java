package TeachingExample;

import TeachingExample.Exceptions.*;	//Be sure to import the Exceptions class like you would any other library

//Car Super Class
public class Car{

	//Initialized Attributes
	String brand;
	String model;
	int year, isSUV;
	double price;

	//Constructor for General Car
	public Car(String brand, String model, int year, double price) throws YearToLowException, PriceToLowException{
		Exceptions myException = new Exceptions();	//Instanstiation of the Exceptions class
		this.isSUV = 0;
		this.brand = brand;
		this.model = model;
		if (year < 1986 || year > 9999) {
			//After instantiating the Exceptions class, I am able to create instances of the inner class found
			// inside the Exceptions class, in this example, I am throwing the inner class YearToLowException
			throw myException.new YearToLowException("ERROR: Year must be greater than 1986\n");
		} else {
			this.year = year;
		}
		if (price < 5000) {
			//Due to the beauty of inner classes, I can create any inner class I want as long as I have the
			// initial Exceptions class instantiated. Here I am throwing another inner class called PriceToLowException
			throw myException.new PriceToLowException("ERROR: Price must be greater than $5,000\n");
		} else {
			this.price = price;
		}
	}

	//Constructor for Optional Price of $50,000
	public Car(String brand, String model, int year) throws YearToLowException{
		//You must instantiate this class if you are going to throw your own exception
		Exceptions myException = new Exceptions();
		this.isSUV = 0;
		this.brand = brand;
		this.model = model;
		if (year < 1986 || year > 9999) {
			throw myException.new YearToLowException("ERROR: Year must be greater than 1986\n");
		} else {
			this.year = year;
		}
		this.price = 50000;
	}

	//Accessors
	public String getBrand(){
		return this.brand;
	}

	public String getModel(){
		return this.model;
	}

	public int getYear(){
		return this.year;
	}

	public double getPrice(){
		return this.price;
	}

	//Mutators
	public void setBrand(String brand){
		this.brand = brand;
	}

	public void setModel(String model){
		this.model = model;
	}

	public void setYear(int year) throws YearToLowException{
		Exceptions myException = new Exceptions();
		if (year < 1986 || year > 9999) {
			throw myException.new YearToLowException("ERROR: Year must be greater than 1986 and less than 9999");
		} else {
			this.year = year;
		}
	}

	public void setPrice(int price) throws PriceToLowException{
		Exceptions myException = new Exceptions();
		if (price < 5000) {
			throw myException.new PriceToLowException("ERROR: Price must be greater than $5,000");
		} else {
			this.price = price;
		}
	}

	//ToString
	@Override
	public String toString(){
		return this.brand + " " + this.model + " " + this.year + " " + this.price + " " + this.isSUV;
	}	

	//Equals Method
	public boolean isEquals(Object other){
		if (other == null){
			return false;
		} else if (getClass() != other.getClass()){
			return false;
		} else {
			Car otherCar = (Car)other;
			return this.brand.equals(otherCar.brand) && this.model.equals(otherCar.model) 
					&& this.year == otherCar.year && this.price == otherCar.price;
		}
	}

	//dataDump
	public String dataDump(){
		return this.brand + " " + this.model + " " + this.year + " " + this.price;
	}
}