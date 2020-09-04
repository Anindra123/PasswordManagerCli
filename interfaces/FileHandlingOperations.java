package interfaces;
import java.io.IOException;


public interface FileHandlingOperations {
	
	public void setPasswithAccName(String fileName) throws IOException;
	public void getPasswithAccName(String fileName,String content);
	public void modifyPasswords(String fileName) throws IOException;
	public void removePasswords(String fileName) throws IOException;
	public void getIndex(String fileName);
	
	
}