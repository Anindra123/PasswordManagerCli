package exceptions;
public class IndexNotMatchingException extends Exception{
	
	private char detail;
	public IndexNotMatchingException(char c){
		detail = c;
	}
	
}
	