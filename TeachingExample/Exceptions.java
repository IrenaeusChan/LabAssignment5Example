package TeachingExample;

//We create a normal public class that everyone is used to seeing here
public class Exceptions {

	//Inner Classes
	//From the normal public class we create what is called Inner Classes inside the public
	// class. Each of these classes will be protected because we only want to use them in
	// our current implementation of the GUI. These exceptions should not be available
	// anywhere else which means you don't have to worry about signature conflicts
	//When you create an inner class, you treat it the same as a regular class
	protected class YearToLowException extends Exception{
		//Main Exception Throw
		protected YearToLowException(){	//Like a regular class we need constructors
			super();					// this is a generic constructor for your exceptions
		}
		//String Exception Throw
		protected YearToLowException(String s){		//You can also create a constructor
			super(s);								// that accepts Strings so you can 
		}											// display and return to the user
	}

	protected class PriceToLowException extends Exception{
		//Main Exception Throw
		protected PriceToLowException(){
			super();
		}
		//String Exception Throw
		protected PriceToLowException(String s){
			super(s);
		}
	}

	protected class NegativeSeatsException extends Exception{
		//Main Exception Throw
		protected NegativeSeatsException(){
			super();
		}
		//String Exception Throw
		protected NegativeSeatsException(String s){
			super(s);
		}
	}

	protected class SingleWordException extends Exception{
		//Main Exception Throw
		protected SingleWordException(){
			super();
		}
		//String Exception Throw
		protected SingleWordException(String s){
			super(s);
		}
	}

	protected class RepeatKeyException extends Exception{
		//Main Exception Throw
		protected RepeatKeyException(){
			super();
		}
		//String Exception Throw
		protected RepeatKeyException(String s){
			super(s);
		}
	}
}