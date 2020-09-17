package classes;

import interfaces.FileHandlingOperations;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


//This class will be inherited by StorePassAcc.It implements interface FileHandlingOperations 
public abstract class MasterPassAcc implements FileHandlingOperations{
	protected String userName;
	protected String masterPass;
	protected Scanner cin;
	protected FileWriter writeFile;
	protected Scanner reader;
	protected File readFile;
	
	
	public MasterPassAcc(){
		this.userName = null;
		this.masterPass = null;
		cin = new Scanner(System.in);
	}
	//Set the user name for the user account
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	//Set the master password for the user account
	public void setMasterPass(String masterPass){
		this.masterPass = masterPass;
	}
	
	//Get the user name for the user account
	public String getUserName(){
		return userName.replaceAll("_"," ");
	}
	
	//Check whether user name and master password are valid
	public boolean verify(String user,String masterpass){
		try{
			if(userName.equals(user) && masterPass.equals(masterpass)){
				return true;
			}
		}catch(NullPointerException e){
			return false;
		}
		return false;
	}
	
	//Check whether user entered invalid user name and master password
	public boolean isNull(String user,String pass){
		if(user.length() == 0 || pass.length()==0){
			return true;
		}
		return false;
	}
	
	//Check whether a user name already exist,overloading the verify function
	public boolean verify(String fileName,String user,String pass){
		String content = user+" "+pass;
		readFile = new File(fileName+".txt");
		try{
			reader = new Scanner(readFile);
			while(reader.hasNext()){
				String line = reader.nextLine();
				if(line.contains(content)){
					return true;
				}
			}
		}catch(FileNotFoundException e){
			return false;
		}finally{
			reader.close();
		}
		return false;
	}
	
	//Get the master password for the user account
	public String getMasterPass(String fileName,String userName){
		readFile = new File(fileName+".txt");
		try{
			reader = new Scanner(readFile);
		    while(reader.hasNext()){
            String line = reader.nextLine();
			String words[] = line.split(" ");
			if(words[0].equals(userName)){
				String masterpass = words[1];
                  return masterpass;				
				}
			}
		}
		catch(FileNotFoundException e){
		System.out.println("\n\t\t\t\tAccount not found or created");	
		}
		finally{
			reader.close();
		}			
		return null;
	}
	
	//This will be implemented on StorePassAcc
	public abstract String encryption(String passwords);
	public abstract String decryption(String passwords);
	
	//This will store user name and master pass on a file
	public void setPasswithAccName(String fileName) throws IOException{
		writeFile = new FileWriter(fileName+".txt",true);
		String outputLine =userName+" "+masterPass+"\n";
		writeFile.write(outputLine);
		writeFile.close();
	}
	
	//This stores the value of user name and master pass from the file to userName and masterPass variable
	public void getPasswithAccName(String fileName,String content){
		readFile = new File(fileName+".txt");
		try{
			reader = new Scanner(readFile);
			while(reader.hasNext()){
				String line = reader.nextLine();
				if(line.equals(content)){
					
					String[] words = line.split(" ");
					userName = words[0];
					masterPass = words[1];
				}
			}
		}
		catch(FileNotFoundException e){
			System.out.println("\n\t\t\t\tAccount not found or created");	
		}
		catch(NullPointerException e){
			System.out.println("\n\t\t\tser Name or Password Doesn't Match.Please Try Again");
		}
		finally{
			reader.close();
		}	
	}
		
	
}