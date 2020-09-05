package interfaces;
import classes.*;
import exceptions.*;
import java.io.IOException;
import java.io.FileNotFoundException;



public interface FileHandlingOperations {
	
	public void setPasswithAccName(String fileName) throws IOException;
	public void getPasswithAccName(String fileName,String content) throws FileNotFoundException,IndexNotMatchingException;
	public void modifyPasswords(String fileName) throws IOException,FileNotFoundException,IndexNotMatchingException;
	public void removePasswords(String fileName) throws IOException,FileNotFoundException,IndexNotMatchingException;
	public void getIndex(String fileName) throws FileNotFoundException,FileisEmptyException;
	public void renameFile(String oldFileName,String newFileName);
	
	
}