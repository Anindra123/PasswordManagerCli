package classes;

import interfaces.FileHandlingOperations; 
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;



public class StorePassAcc extends MasterPassAcc implements FileHandlingOperations{
	private String passwords;
	private String accName;
	private String outputline;
	private String index;
	private static int currentIndex =0;
	
	
	public StorePassAcc(){
		super();
		this.passwords = null;
		this.accName = null;
	}
	
	public StorePassAcc(String userName, String masterpass, String passwords,String accName){
		super(userName,masterpass);
		this.passwords=passwords;
		this.accName=accName;
		
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
	
	public void setPasswithAccName(String fileName,int numOfAcc) throws IOException{
		
		int lineindex=0;
		writeFile = new FileWriter(fileName+".txt",true);
		
			while(lineindex < numOfAcc){
				currentIndex++;
				System.out.print("\n\t\t\t\tEnter a name of site/account you want to store password for :");
				accName = cin.nextLine();
				System.out.print("\n\t\t\t\tEnter the password to store :");
				passwords = cin.nextLine();
				String epass=this.encryption(passwords);
				outputline = currentIndex+" "+accName.replaceAll(" ","_")+" "+epass+"\n";
				writeFile.write(outputline);
				lineindex++;
			
			}
		writeFile.close();
		System.out.println("\n\t\t\t\t\tAll Passwords encrypted and stored sucessfully");
		
	}		
	public void getIndex(String fileName) throws FileNotFoundException{
		readFile = new File(fileName+".txt");
		try{
			reader = new Scanner(readFile);
			System.out.println("Index        Account-Title");
			while(reader.hasNext()){
				String line = reader.nextLine();
				String[] words = line.split(" ");
				index = words[0];
				accName = words[1].replaceAll("_"," ");
				passwords = words[2];
				System.out.println(index+"            "+accName);
				currentIndex = Integer.parseInt(index);
			}
			reader.close();
		}
		catch(NumberFormatException e){
			System.out.println("\n\t\t\t\t\tNot a number");
		}
	}
	public void renameFile(String oldFileName,String newFileName){
		File oldFile = new File(oldFileName); 
		File newFile = new File(newFileName+".txt");
		oldFile.renameTo(newFile);	
	}
	
	
	
	public void getPasswithAccName(String fileName) throws FileNotFoundException{
		readFile = new File(fileName+".txt");
		
		reader = new Scanner(readFile);
		System.out.print("\n\t\t\t\t\tEnter the index no of the password to decrypt and view :");
		char selectIndex = cin.next().charAt(0);
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
		reader.close();
		
	}
					
	public void modifyPasswords(String fileName) throws IOException,FileNotFoundException{
		readFile = new File(fileName+".txt");
		writeFile = new FileWriter(fileName+" temp.txt");
		System.out.print("Enter the Index no you want to modify the password for: ");
		char selectIndex = cin.next().charAt(0);
		cin.nextLine();
	
		reader = new Scanner(readFile);
		while(reader.hasNext()){
			String line = reader.nextLine();
			if(line.charAt(0) == selectIndex){
				String[] words = line.split(" ");
				String indexNo = words[0];
				accName = words[1].replaceAll("_"," ");
				System.out.println("For "+accName);
				System.out.print("Enter a new password :");
				String modPass = cin.nextLine();
				String pass = this.encryption(modPass);
				String outputline = indexNo+" "+accName+" "+pass+"\n";
				writeFile.write(outputline);
				continue;
			}
			writeFile.write(line+"\n");
		}	
		System.out.println("Password modify successfully");
		
		
		writeFile.close();
		reader.close();
		readFile.delete();
		this.renameFile(fileName+" temp.txt",fileName);
            
	
	}
	
	
	public void removePasswords(String fileName) throws IOException,FileNotFoundException{
		readFile = new File(fileName+".txt");
		writeFile = new FileWriter(fileName+" temp.txt");
		System.out.print("\n\t\t\t\tEnter the Index no you want to remove the password for: ");
		char selectIndex = cin.next().charAt(0);
		cin.nextLine();
		
		reader = new Scanner(readFile);
		while(reader.hasNext()){
			String line = reader.nextLine();
		    if(line.charAt(0) == selectIndex){
				String[] words = line.split(" ");
				String indexNo = words[0];
				accName = words[1].replaceAll("_"," ");
				passwords = this.decryption(words[2]);
				System.out.println("Account name: "+accName);
				System.out.println("Password: "+passwords);
				System.out.println("Do you want to permanently remove this password?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				System.out.println("Enter your option(1-2): ");
				try{
					int select = cin.nextInt();
					cin.nextLine();
					if(select==1){
						continue;
					}
					else if(select==2){
						writeFile.write(line+"\n");	
					}
				}
				catch(InputMismatchException e){
					System.out.println("Please enter a valid input"); 
					writeFile.write(line+"\n");
				}	
		}
		writeFile.write(line+"\n");
		}	
		System.out.println("\n\t\t\t\t\tPassword removed successfully");
		writeFile.close();
		reader.close();
		readFile.delete();
		this.renameFile(fileName+" temp.txt",fileName);
	}
	
	
}
