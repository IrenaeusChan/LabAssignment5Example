package TeachingExample;

import TeachingExample.Exceptions.*;

//SUV Subclass
public class SUV extends Car{

	//Initialized Attributes
	int numSeats;
	boolean isAllTerrain;
	String tireBrand;

	//Constructor for SUV
	public SUV(String brand, String model, int year, double price, int numSeats, boolean isAllTerrain, String tireBrand) throws YearToLowException, PriceToLowException, NegativeSeatsException{
		super(brand, model, year, price);
		this.isSUV = 1;
		Exceptions myException = new Exceptions();
		if (numSeats < 0) {
			throw myException.new NegativeSeatsException("ERROR: There cannot be negative seats\n");
		} else {
			this.numSeats = numSeats;
		}
		this.isAllTerrain = isAllTerrain;
		this.tireBrand = tireBrand;
	}

	//Constructor for Optional Price of $50,000
	public SUV(String brand, String model, int year, int numSeats, boolean isAllTerrain, String tireBrand) throws YearToLowException, NegativeSeatsException{
		super(brand, model, year);
		this.isSUV = 1;
		Exceptions myException = new Exceptions();
		if (numSeats < 0) {
			throw myException.new NegativeSeatsException("ERROR: There cannot be negative seats\n");
		} else {
			this.numSeats = numSeats;
		}
		this.isAllTerrain = isAllTerrain;
		this.tireBrand = tireBrand;
	}

	//Accessors
	public int getNumSeats(){
		return this.numSeats;
	}

	public boolean getIsAllTerrain(){
		return this.isAllTerrain;
	}

	public String getTireBrand(){
		return this.tireBrand;
	}

	//Mutators
	public void setNumSeats(int numSeats){
		this.numSeats = numSeats;
	}

	public void setIsAllTerrain(boolean isAllTerrain){
		this.isAllTerrain = isAllTerrain;
	}

	public void setTireBrand(String tireBrand){
		this.tireBrand = this.tireBrand;
	}

	//ToString
	@Override
	public String toString(){
		return super.toString() + " " + this.numSeats + " " + this.isAllTerrain + " " + this.tireBrand;
	}	

	//Equals Method
	@Override
	public boolean isEquals(Object other){
		if (other == null){
			return false;
		} else if (getClass() != other.getClass()){
			return false;
		} else {
			SUV otherSUV = (SUV)other;
			return this.brand.equals(otherSUV.brand) && this.model.equals(otherSUV.model) 
					&& this.year == otherSUV.year && this.price == otherSUV.price && this.numSeats == otherSUV.numSeats
					&& this.isAllTerrain == otherSUV.isAllTerrain && this.tireBrand.equals(otherSUV.tireBrand);
		}
	}

	//DataDump
	@Override
	public String dataDump(){
		return "SUV: " + this.model + ", " + this.tireBrand;
	}
	
}