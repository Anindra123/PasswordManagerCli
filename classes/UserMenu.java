package classes;
import interfaces.DisplayOperations;
import exceptions.IndexNotMatchingException;
import exceptions.FileisEmptyException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.FileNotFoundException;

//This class handles all the display.It implements the interface DisplayOperations
public class UserMenu implements DisplayOperations{
	private String name;
	private String pass;
	private StorePassAcc storePass;
	private GeneratePass generatePass;
	private Scanner cin;
	private static String currentUser = null;
	private int numOfAcc;
	
	public UserMenu(){
		this.name = null;
		this.pass = null;
		cin = new Scanner(System.in);
		storePass = new StorePassAcc();
		generatePass = new GeneratePass();
	}
	
	//Display Menu for registration
	public void registerMenu(){
		System.out.println("\n\t\t\t\t\t-------------------------------");
		System.out.println("\t\t\t\t\t|       ACCOUNT REGISTER      |");
		System.out.println("\t\t\t\t\t-------------------------------");
		System.out.print("\n\t\t\t\t\tEnter a user name :");
		name = cin.nextLine();
		String userName = name.replaceAll(" ","_");
		System.out.print("\n\t\t\t\t\tEnter a strong master password :");
		pass = cin.nextLine();
		String epass= storePass.encryption(pass);
		if(!storePass.verify("Users",userName,epass) && !storePass.isNull(name,pass) && !pass.contains(" ")){
			storePass.setUserName(userName);
			storePass.setMasterPass(epass);
			try{
				
				storePass.setPasswithAccName("Users");
			}
			catch(IOException e){
				System.out.println("\n\t\t\t\t\tError Occured.Please Try Again");
			}
			System.out.println("\n\t\t\t\t\tAccount Registered Sucessfully");
		}
		else if(storePass.isNull(name,pass)){
			System.out.println("\n\t\t\t\t\tPlease enter a valid input");
		}
		else if(storePass.verify("Users",userName,epass)){
			System.out.println("\n\t\t\t\t\tUsername or masterpassword already exist");
		}
		else if(pass.contains(" ")){
			System.out.println("\n\t\t\t\t\tAvoid using spaces in passwords");
		}
			
		
	}
	
	//Display menu for login
	public boolean loginMenu(){		
	    System.out.println("\n\t\t\t\t\t-------------------------------");
		System.out.println("\t\t\t\t\t|         LOGIN MENU          |");
		System.out.println("\t\t\t\t\t-------------------------------");
		System.out.print("\n\t\t\t\t\tEnter your user name :");
		name = cin.nextLine();
		String userName = name.replaceAll(" ","_");
		System.out.print("\n\t\t\t\t\tEnter your master password :");
		pass = cin.nextLine();
		String epass=storePass.encryption(pass);
		String content = userName+" "+epass;
		try{
			storePass.getPasswithAccName("Users",content);
		}
		catch(FileNotFoundException e){
			System.out.println("\n\t\t\t\t\tAccount not found or created");
			return false;
		}	
		if(storePass.verify(userName,epass)){
			currentUser = storePass.getUserName();
			System.out.println("\n\t\t\t\t\tLogged in Sucessfully. Welcome,"+currentUser);
			return true;
		}else{
			System.out.println("\n\t\t\t\tUser name or Master Password doesn't match or not saved, please try again");		
		}
		return false;
	}
	
	//Display menu for storing passwords
	public void storePassMenu(){
		
		try{
			try{
				storePass.getIndex(currentUser+"Stored Passwords");
			}
			catch(FileNotFoundException | FileisEmptyException e){
			StorePassAcc.currentIndex = 0;
			System.out.println(e.getMessage());
			}
			System.out.print("\n\t\t\t\tEnter the number of accounts to store password for :");
			numOfAcc = cin.nextInt();
			cin.nextLine();
			storePass.setPasswithAccName(currentUser+"Stored Passwords",numOfAcc);
		}catch(IOException e){
			System.out.println(e.getMessage());
		}catch(InputMismatchException e){
			System.out.println("\n\t\t\t\t\tPlease Enter a number");
			return;
		}
		
			
	}
	
	//Display menu for modifying passwords
	public void modifyPassMenu(){
		try{
			storePass.getIndex(currentUser+"Stored Passwords");
			storePass.modifyPasswords(currentUser+"Stored Passwords");
		}
		catch(FileNotFoundException  | FileisEmptyException e){
			System.out.println("\n\t\t\t\t\tNo password currently stored");
		}
		catch(IOException e){
			System.out.println("\n\t\t\t\t\tError Occured.Please Try Again");
		}
		catch(IndexNotMatchingException e){
			System.out.println(e.getMessage());			
		}
	}
	
	//Display menu for removing passwords
	public void removePassMenu(){
		try{
			storePass.getIndex(currentUser+"Stored Passwords");
			storePass.removePasswords(currentUser+"Stored Passwords");
		}
		catch(FileNotFoundException | FileisEmptyException e){
			System.out.println("\n\t\t\t\t\tNo password currently stored");
		}
		catch(IOException e){
			System.out.println("\n\t\t\t\t\tError Occured.Please Try Again");
		}
		catch(IndexNotMatchingException e){
			System.out.println(e.getMessage());
		}
	}
	//Menu for showing all passwords stored for a user
	public void getPassMenu(){
		try{
			storePass.getIndex(currentUser+"Stored Passwords");
			storePass.getPasswithAccName(currentUser+"Stored Passwords");
		}
		catch(FileNotFoundException | FileisEmptyException e){
			StorePassAcc.currentIndex = 0;
			System.out.println("\n\t\t\t\t\tNo password currently stored");
		}	
		catch(IndexNotMatchingException e){
			System.out.println(e.getMessage());
		}	

	}
	//Display menu for generating a password
	public void generatePassMenu(){
		System.out.print("\n\t\t\t\tEnter a title of the account you want to generate a password for :");
		String accName = cin.nextLine();
		String pass = generatePass.getGeneratedPass();
		System.out.println("\n\t\t\t\tYour generated password :"+pass);
		System.out.println("\n\t\t\t\tWhat do you want to do ?");
		System.out.println("\n\t\t\t\t1.Store encrypted pass only");
		System.out.println("\n\t\t\t\t2.Store encrypted pass and save the decrypted pass in seperate textfile");
		System.out.print("\n\t\t\t\tEnter a option (1-2):");
		try{
			
			int selectIndex = cin.nextInt();
			cin.nextLine();
			if(selectIndex == 1){
				try{
					generatePass.getIndex(currentUser+"Stored Passwords");
				}
				catch(FileNotFoundException e){
					storePass.currentIndex =0;
				}
				String epass = storePass.encryption(pass);
				generatePass.setPasswithAccName(currentUser+"Stored Passwords",accName.replaceAll(" ","_"),epass);
			}
			else if(selectIndex == 2){
				try{
					generatePass.getIndex(currentUser+"Stored Passwords");
				}
				catch(FileNotFoundException e){
					storePass.currentIndex =0;
				}
				String epass = storePass.encryption(pass);
				generatePass.setPasswithAccName(currentUser+"Stored Passwords",accName.replaceAll(" ","_"),epass);
				System.out.print("\n\t\t\t\tEnter a file name you want to store the password to :");
				String fileName = cin.nextLine();
				generatePass.savePasswithAccName(fileName,accName,pass);
			}
		}
		catch(InputMismatchException e){
			System.out.println("\n\t\t\t\t\tPlease Enter a valid input");
			return;
		}
		catch(IOException e){
			System.out.println("\n\t\t\t\t\tError Occured.Please Try Again");
		}
	}		

		
	
	//Menu for showing master password of the user	
	public void forgotPassMenu(){
		System.out.print("\n\t\t\t\t\tEnter Your Name : ");
		String name = cin.nextLine();
		try{
			String pass = storePass.getMasterPass("Users",name.replaceAll(" ","_"));
			String depass = storePass.decryption(pass);
			System.out.println("\n\t\t\t\t\tFor User: "+name);
			System.out.println("\n\t\t\t\t\tYour Master Password :"+depass);
		}
		catch(NullPointerException e){
			System.out.println("\n\t\t\t\t\tUser name doesn't match or doesn't exists.Try Again");
		}
		
	
	}
	//Main menu, the menu shown after a user sucessfully logs in
	public void mainMenu(){
		System.out.println("\n\t\t\t\t\tUser :"+storePass.getUserName());
		int selectIndex = 0;
		do{
			System.out.println("\n\t\t\t\t\t-------------------------------");
		    System.out.println("\t\t\t\t\t|         MAIN MENU           |");
		    System.out.println("\t\t\t\t\t-------------------------------");
			System.out.println("\t\t\t\t\t-------------------------------");
			System.out.println("\t\t\t\t\t|      1.Store passwords      |");
			System.out.println("\t\t\t\t\t-------------------------------");
			System.out.println("\t\t\t\t\t|     2.Show saved passwords  |");
			System.out.println("\t\t\t\t\t-------------------------------");
			System.out.println("\t\t\t\t\t|      3.Modify password      |");
			System.out.println("\t\t\t\t\t-------------------------------");
			System.out.println("\t\t\t\t\t|      4.Remove password      |");
			System.out.println("\t\t\t\t\t-------------------------------");
			System.out.println("\t\t\t\t\t| 5.Suggest a strong password |");
			System.out.println("\t\t\t\t\t-------------------------------");
			System.out.println("\t\t\t\t\t|         6.Logout            |");
			System.out.println("\t\t\t\t\t-------------------------------");
			System.out.print("\n\t\t\t\t\tEnter any one(1-6):");
			try{
				selectIndex = cin.nextInt();
				cin.nextLine();
			}
			catch(InputMismatchException e){
				System.out.println("\n\t\t\t\t\tPlease enter a number");
				cin.nextLine();
				return;
			}
			if(selectIndex == 1){
				this.storePassMenu();
			}
			else if(selectIndex == 2){
				this.getPassMenu();
			}
			else if(selectIndex == 3){
				this.modifyPassMenu();
			}
			else if(selectIndex == 4){
				this.removePassMenu();
			}
			else if(selectIndex == 5){
				this.generatePassMenu();
			}
		}while(selectIndex != 6);
	}
		
				
	//Starting menu where user can register and login
	public void startingMenu(){
		int selectIndex = 0;
		do{	System.out.println("\n\t\t\t\t\t-------------------------------");
		    System.out.println("\t\t\t\t\t-------------------------------");
     		System.out.println("\t\t\t\t\t| WELCOME TO PASSWORD MANAGER |");
			System.out.println("\t\t\t\t\t-------------------------------");
			System.out.println("\t\t\t\t\t-------------------------------");
			System.out.println("\n\t\t\t\t\t-------------------------------");
			System.out.println("\t\t\t\t\t|         1.REGISTER          |");
			System.out.println("\t\t\t\t\t-------------------------------");
			System.out.println("\t\t\t\t\t|          2.LOGIN            |");
			System.out.println("\t\t\t\t\t-------------------------------");
			System.out.println("\t\t\t\t\t|   3.FORGOT MASTERPASSWORD   |");
			System.out.println("\t\t\t\t\t-------------------------------");
			System.out.println("\t\t\t\t\t|          4.EXIT             |");
			System.out.println("\t\t\t\t\t-------------------------------");
			System.out.print("\n\t\t\t\t\tChoose your Option(1-4):");
			try{
				selectIndex = cin.nextInt();
				cin.nextLine();
			}
			catch(InputMismatchException e){
				System.out.println("\n\t\t\t\t\tPlease enter a number");
				cin.nextLine();
				return;
			}
			if(selectIndex == 1){
				this.registerMenu();
			}
			else if(selectIndex == 2){
				if(this.loginMenu()){
					this.mainMenu();
				}	
			}
			else if(selectIndex == 3){
				this.forgotPassMenu();
			}
		}
		while(selectIndex !=4);
	}
}
					
		
	