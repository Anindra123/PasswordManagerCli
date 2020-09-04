package classes;

import interfaces.FileHandlingOperations;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;



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
	public MasterPassAcc(String userName,String masterPass){
		this.userName = userName;
		this.masterPass = masterPass;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
	public void setMasterPass(String masterPass){
		this.masterPass = masterPass;
	}
	
	public String getUserName(){
		return userName.replaceAll("_"," ");
	}
	
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
	public String getMasterPass(String fileName,String userName){
		File readFile = new File(fileName+".txt");
		try{
			reader = new Scanner(readFile);
		    while(reader.hasNext()){
            String line = reader.nextLine();
			if(line.contains(userName)){
				String words[] = line.split(" ");
				String masterpass = words[1];
                  return masterpass;				
				}
			}
			reader.close();
		}
		catch(FileNotFoundException e){
		System.out.println("Account not found or created");	
		}
		return null;
	}
	
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
		}
		return false;
	}
	public abstract String encryption(String passwords);
	public abstract String decryption(String passwords);
	
	public void setPasswithAccName(String fileName) throws IOException{
		writeFile = new FileWriter(fileName+".txt",true);
		String outputLine =userName+" "+masterPass+"\n";
		writeFile.write(outputLine);
		writeFile.close();
	}
	
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
			reader.close();
		}
		catch(FileNotFoundException e){
			System.out.println("File Doesn't Exist");
		}
		catch(NullPointerException e){
			System.out.println("User Name or Password Doesn't Match.Please Try Again");
		}
	}
		
	
}