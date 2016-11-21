package TeachingExample;

import TeachingExample.Exceptions.*;

//Car Super Class
public class Car{

	//Initialized Attributes
	String brand;
	String model;
	int year;
	double price;

	//This is bad... I only did it because i'm lazy
	Exceptions myException = new Exceptions();

	//Constructor for General Car
	public Car(String brand, String model, int year, double price) throws YearToLowException, PriceToLowException{
		this.brand = brand;
		this.model = model;
		if (year < 1986 || year > 9999) {
			throw myException.new YearToLowException("ERROR: Year must be greater than 1986");
		} else {
			this.year = year;
		}
		if (price < 5000) {
			throw myException.new PriceToLowException("ERROR: Price must be greater than $5,000");
		} else {
			this.price = price;
		}
	}

	//Constructor for Optional Price of $50,000
	public Car(String brand, String model, int year) throws YearToLowException{
		this.brand = brand;
		this.model = model;
		if (year < 1986 || year > 9999) {
			throw myException.new YearToLowException("ERROR: Year must be greater than 1986");
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
		if (year < 1986 || year > 9999) {
			throw myException.new YearToLowException("ERROR: Year must be greater than 1986");
		} else {
			this.year = year;
		}
	}

	public void setPrice(int price) throws PriceToLowException{
		if (price < 5000) {
			throw myException.new PriceToLowException("ERROR: Price must be greater than $5,000");
		} else {
			this.price = price;
		}
	}

	//ToString
	@Override
	public String toString(){
		return this.brand + " " + this.model + " " + this.year + " " + this.price;
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