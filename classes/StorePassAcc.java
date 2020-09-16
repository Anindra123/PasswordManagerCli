package classes;

import interfaces.FileHandlingOperations; 
import exceptions.IndexNotMatchingException;
import exceptions.FileisEmptyException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;



public class StorePassAcc extends MasterPassAcc implements FileHandlingOperations{
	protected String passwords;
	protected String accName;
	protected String outputline;
	protected String index;
	protected static int currentIndex =0;
	protected static String tempAccName ="";
	
	
	public StorePassAcc(){
		super();
		this.passwords = null;
		this.accName = null;
		this.outputline = null;
		this.index = null;
	}
	
	public String encryption(String passwords)
	{
		StringBuilder encryptedPass = new StringBuilder();
		String s1 = passwords;
		for(char c : s1.toCharArray()){
			c += 5;
			encryptedPass.append(c);
        
		}
 	return encryptedPass.toString();
	}
	public String decryption(String passwords)
	{
		StringBuilder decryptedPass = new StringBuilder();
		String s1 = passwords;
		for(char c : s1.toCharArray()){
			c -= 5;
			decryptedPass.append(c);
         
		}	
		return decryptedPass.toString();
	}
	public void renameFile(String oldFileName,String newFileName){
		File oldFile = new File(oldFileName); 
		File newFile = new File(newFileName+".txt");
		oldFile.renameTo(newFile);	
	}
	
	public boolean accNameExist(String fileName,String accName) {
		readFile = new File(fileName+".txt");
		try{
			reader = new Scanner(readFile);
			while(reader.hasNext()){
				String line = reader.nextLine();
				if(line.contains(accName)){
					return true;
				}
			}
		}
		catch(FileNotFoundException e){
			return false;
		}
		catch(NoSuchElementException e){
			System.out.println(e.getMessage());
			return false;
		}
		return false;
	}
		
	
	public void setPasswithAccName(String fileName,int numOfAcc) throws IOException{
		Scanner scan = new Scanner(System.in);
		int lineindex=0;
		writeFile = new FileWriter(fileName+".txt",true);
		try{
			while(lineindex < numOfAcc){
				currentIndex++;
				System.out.print("\n\t\t\t\tEnter a name of site/account you want to store password for :");
				accName = scan.nextLine();
				if(tempAccName.length() == 0){
					tempAccName = accName;
				}
				else{
					while(tempAccName.equals(accName)){
						System.out.println("\n\t\t\t\tAccount with "+accName+" already exists");
						System.out.print("\n\t\t\t\tPlease enter  a different account name :");
						accName = scan.nextLine();
					}
					tempAccName = accName;
				}
				System.out.print("\n\t\t\t\tEnter the password to store :");
				passwords = scan.nextLine();
						
				
				if(!isNull(accName,passwords) && !passwords.contains(" ") ){
					String epass=this.encryption(passwords);
					outputline = currentIndex+" "+accName.replaceAll(" ","_")+" "+epass+"\n";
					writeFile.write(outputline);
					lineindex++;
				}else if(isNull(accName,passwords)){
					System.out.println("\n\t\t\t\t\tPlease enter a valid input");
					currentIndex = 0;
					return;
				}
				else if(passwords.contains(" ")){
					System.out.println("\n\t\t\t\t\tAvoid using spaces in password");
					currentIndex = 0;
					return;
				}	
			}
			System.out.println("\n\t\t\t\t\tAll Passwords encrypted and stored sucessfully");
		}
		finally{
			writeFile.close();
		}
	}		
	public void getIndex(String fileName) throws FileNotFoundException,FileisEmptyException{
		readFile = new File(fileName+".txt");
		if(readFile.length() == 0){
			throw new FileisEmptyException();
		}
		try{
			reader = new Scanner(readFile);
			System.out.println("\n\t\t\t\t\t------------------------------");
			System.out.println("\t\t\t\t\t|Index   |    Account-Title  ");
			System.out.println("\t\t\t\t\t------------------------------");
			while(reader.hasNext()){
				String line = reader.nextLine();
				String[] words = line.split(" ");
				index = words[0];
				accName = words[1].replaceAll("_"," ");
				passwords = words[2];
				System.out.println("\t\t\t\t\t| "+index+"      |      "+accName);
				currentIndex = Integer.parseInt(index);
			System.out.println("\t\t\t\t\t------------------------------");
			}
		}
		catch(NumberFormatException e){
			System.out.println("\n\t\t\t\t\tNot a number");
		}finally{
			reader.close();
		}
	}
	
	public boolean checkIndex(String fileName,char index) throws FileNotFoundException{
		readFile = new File(fileName+".txt");
		int flag = 0;
		reader = new Scanner(readFile);
		while(reader.hasNext()){
			String line = reader.nextLine();
			if(line.charAt(0) == index){
				flag = 1;
				break;
			}
			
		}
		reader.close();
		
		if(flag == 1){
			return true;
		}
		return false;
	}
	
	
	public void getPasswithAccName(String fileName) throws FileNotFoundException,IndexNotMatchingException{
		readFile = new File(fileName+".txt");
		
		System.out.print("\n\t\t\t\tEnter the index no of the password to decrypt and view :");
		char selectIndex = cin.next().charAt(0);
		if(!checkIndex(userName.replaceAll("_"," ")+"Stored Passwords",selectIndex)){
			throw new IndexNotMatchingException(selectIndex);
		}
		reader = new Scanner(readFile);
		try{
			while(reader.hasNext()){
				String line = reader.nextLine();
				if(line.charAt(0) == selectIndex){	
					String[] words = line.split(" ");
					accName = words[1].replaceAll("_"," ");
					passwords = words[2];
					String dpass=this.decryption(passwords);
					System.out.println("\n\t\t\t\t\tAccount for :"+accName);
					System.out.println("\n\t\t\t\t\tPassword :"+dpass);
					break;
				}
			}
		}finally{
			reader.close();
		}
	}

		
	
	
	public void modifyPasswords(String fileName) throws IOException,FileNotFoundException,IndexNotMatchingException{
		readFile = new File(fileName+".txt");
		System.out.print("\n\t\t\t\tEnter the Index no you want to modify the password for: ");
		char selectIndex = cin.next().charAt(0);
		cin.nextLine();
		if(!checkIndex(userName.replaceAll("_"," ")+"Stored Passwords",selectIndex)){
			throw new IndexNotMatchingException(selectIndex);
		}
		writeFile = new FileWriter(fileName+" temp.txt");
		try{
			reader = new Scanner(readFile);
			while(reader.hasNext()){
				String line = reader.nextLine();
				if(line.charAt(0) == selectIndex){
					String[] words = line.split(" ");
					String indexNo = words[0];
					accName = words[1].replaceAll("_"," ");
					System.out.println("\n\t\t\t\t\tFor "+accName);
					System.out.print("\n\t\t\t\t\tEnter a new password :");
					String modPass = cin.nextLine();
					String pass = this.encryption(modPass);
					if(!isNull(accName,modPass) && !modPass.contains(" ")){
						String outputline = indexNo+" "+accName+" "+pass+"\n";
						writeFile.write(outputline);
						System.out.println("\n\t\t\t\t\tPassword modify successfully");
						continue;
					}	
					else if(isNull(accName,modPass)){
						System.out.println("\n\t\t\t\t\tPlease enter a valid input");
					}
					else if(modPass.contains(" ")){
						System.out.println("\n\t\t\t\t\tAvoid using spaces in passwords");
					}	
				}
				writeFile.write(line+"\n");
			}	
		}
		finally{
			writeFile.close();
			reader.close();
			readFile.delete();
			this.renameFile(fileName+" temp.txt",fileName);
		}
	
	}
	
	
	public void removePasswords(String fileName) throws IOException,FileNotFoundException,IndexNotMatchingException{
		readFile = new File(fileName+".txt");
		System.out.print("\n\t\t\t\tEnter the Index no you want to remove the password for: ");
		char selectIndex = cin.next().charAt(0);
		cin.nextLine();
		if(!checkIndex(userName.replaceAll("_"," ")+"Stored Passwords",selectIndex)){
			throw new IndexNotMatchingException(selectIndex);
		}
		writeFile = new FileWriter(fileName+" temp.txt");
		try{
			reader = new Scanner(readFile);
			while(reader.hasNext()){
				String line = reader.nextLine();
				if(line.charAt(0) == selectIndex){
					String[] words = line.split(" ");
					String indexNo = words[0];
					accName = words[1].replaceAll("_"," ");
					passwords = this.decryption(words[2]);
					System.out.println("\n\t\t\t\tAccount name: "+accName);
					System.out.println("\n\t\t\t\tPassword: "+passwords);
					System.out.println("\n\t\t\t\tDo you want to permanently remove this password?");
					System.out.println("\n\t\t\t\t1. Yes");
					System.out.println("\n\t\t\t\t2. No");
					System.out.print("\n\t\t\t\tEnter your option(1-2): ");
					try{
						int select = cin.nextInt();
						cin.nextLine();
						if(select==1){
						System.out.println("\n\t\t\t\t\tPassword removed successfully");
							continue;
						}
						else if(select==2){
						System.out.println("\n\t\t\t\t\tPassword not removed ");
						}
					}
					catch(InputMismatchException e){
						System.out.println("\n\t\t\t\tPlease enter a valid input"); 
						writeFile.write(line+"\n");
					}	
			}
			writeFile.write(line+"\n");
			}	
		}finally{
			writeFile.close();
			reader.close();
			readFile.delete();
			this.renameFile(fileName+" temp.txt",fileName);
		}
	}
	
	
}
