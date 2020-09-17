package exceptions;

//User defined exception,checks whether a file doesn't have any content
public class FileisEmptyException extends Exception{
	
	public FileisEmptyException(){
		super();
	}
	
	public String getMessage(){
		return "\n\t\t\t\t\tNo password currently stored";
	}
	
}