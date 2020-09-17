package exceptions;


//User defined exception,checks whether user entered a valid index
public class IndexNotMatchingException extends Exception{
	
	private char detail;
	public IndexNotMatchingException(char c){
		detail = c;
	}
	public String getMessage(){
		return "\n\t\t\t\t\tPlease Enter a valid input";
	}
}
	