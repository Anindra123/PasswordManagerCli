package interfaces;
import java.io.IOException;
import java.io.FileNotFoundException;


public interface FileHandlingOperations {
	
	public void setPasswithAccName(String fileName) throws IOException;
	public void getPasswithAccName(String fileName,String content) throws FileNotFoundException;
	public void modifyPasswords(String fileName) throws IOException,FileNotFoundException;
	public void removePasswords(String fileName) throws IOException,FileNotFoundException;
	public void getIndex(String fileName) throws FileNotFoundException;
	
	
}