package classes;
import java.util.Random;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//This class inherits StorePassAcc
public class GeneratePass extends StorePassAcc{
	private String generatedPass;
	private int[] numbers;
	private String[] symbols;
	private String alphabet;
	private Random random;
	
	
	public GeneratePass(){
		super();
		this.generatedPass = null;
		numbers = new int[]{0,1,2,3,4,5,6,7,8,9};
		symbols = new String[]{"!","@","#","%","^","&","*","(",")",";","'","_","+","-"};
		alphabet = null;
		random = new Random();
	}
	
	//This function returns a random string containing two characters
	public String genRandStr(){
		alphabet = "AbCdeFgHiJkLMnOPqRsTUvWxYz";
		char char1 = alphabet.charAt(random.nextInt(alphabet.length()));
		char char2 = alphabet.charAt(random.nextInt(alphabet.length()));
		String randStr = Character.toString(char1)+Character.toString(char2);
		return randStr;
	}
	//This function will return a random symbol from the symbols array
	public String genRandSym(){
		return symbols[random.nextInt(symbols.length)];
	}
	//This function will return a random number from the numbers array
	public int genRandNum(){
		return numbers[random.nextInt(numbers.length)];
	}
	
	//This function will return a generated pseudo random password that the user can save
	public String getGeneratedPass(){
		generatedPass = this.genRandStr()+this.genRandSym()+this.genRandNum()+this.genRandStr()+
		this.genRandNum()+this.genRandSym()+this.genRandStr();
		return generatedPass;
	}
	
	//Gets the current index for storing password.Overloaded from StorePassAcc
	public void getIndex(String fileName) throws FileNotFoundException{
		currentIndex = 0;
		readFile = new File(fileName+".txt");
		try{
			reader = new Scanner(readFile);
			while(reader.hasNext()){
				String line = reader.nextLine();
				String[] words = line.split(" ");
				index = words[0];
				currentIndex = Integer.parseInt(index);
			}
			
			reader.close();
		}
		catch(NumberFormatException e){
			System.out.println("\n\t\t\t\t\tNot a number");
		}
	}
	
	//Replace the pass with the generated pass if same account name is found
	public void replacePass(String fileName,String accName,String pass) throws IOException{
		File readfile = new File(fileName+".txt");
		FileWriter writeFiletemp = new FileWriter(fileName+"temp.txt",true);
		try{
			reader = new Scanner(readfile);
			while(reader.hasNext()){
				String line = reader.nextLine();
				String[] words = line.split(" ");
				if(line.contains(accName.replaceAll(" ","_"))){
					String reOutputline = words[0]+" "+words[1]+" "+pass+"\n";
					writeFiletemp.write(reOutputline);
					continue;
				}
				writeFiletemp.write(line+"\n");		
			}
			System.out.println("\n\t\t\t\t\tPassword replaced sucessfully");
		}
		catch(FileNotFoundException | NoSuchElementException e){
			System.out.println(e.getMessage());
		}
		finally{
			reader.close();
			writeFiletemp.close();
			readFile.delete();
			renameFile(fileName+"temp.txt",fileName);
		}
	}
					
					
					
	//Store all passwords and account name in a file.Overloaded from StorePassAcc
	public void setPasswithAccName(String fileName,String accName,String pass) throws IOException{
		Scanner scan = new Scanner(System.in);
		currentIndex++;
		if(accNameExist(fileName,accName)){
			System.out.println("\n\t\t\t\t\tPassword for account "+accName+" already stored");
			System.out.println("\n\t\t\t\t\tDo you want to replace it ?");
			System.out.println("\n\t\t\t\t\t1.Yes");
			System.out.println("\n\t\t\t\t\t2.No");
			System.out.print("\n\t\t\t\t\tEnter any one(1/2):");
			try{
				int select = scan.nextInt();
				scan.nextLine();
				if(select == 1){
					try{
						this.replacePass(fileName,accName,pass);
					}
					catch(IOException e){
						System.out.println(e.getMessage());
					}
				}else if(select == 2){
					writeFile = new FileWriter(fileName+".txt",true);
					System.out.print("\n\t\t\t\t\tEnter a different account name:");
					String name = scan.nextLine();
					while(accNameExist(fileName,name)){
						System.out.println("\n\t\t\t\t\tAccount name already exist");
						System.out.print("\n\t\t\t\t\tEnter a different account name:");
						name = scan.nextLine();
					}
					String reOutputline = currentIndex+" "+name.replaceAll(" ","_")+" "+pass+"\n";
					writeFile.write(reOutputline);
					writeFile.close();
					System.out.println("\n\t\t\t\t\tPassword Stored sucessfully");
				}
						
			}catch(InputMismatchException e){
					System.out.print("\n\t\t\t\t\tPlease enter a number");
			}	
			
		}
		else{
			writeFile = new FileWriter(fileName+".txt",true);
			String outputline = currentIndex+" "+accName.replaceAll(" ","_")+" "+pass+"\n";
			writeFile.write(outputline);
			writeFile.close();
			System.out.println("\n\t\t\t\t\tPassword Stored sucessfully");
		}
	}
	
	//Save the decrypted pass in a seperate file so the user can copy and use it
	public void savePasswithAccName(String fileName,String accName,String pass) throws IOException{
		String outputline = "For "+accName+"\n"+"Generated Pass :"+pass+"\n";
		writeFile = new FileWriter(fileName+".txt",true);
		File filepath = new File(fileName+".txt");
		writeFile.write(outputline);
		writeFile.close();
		if(filepath.exists()){
			System.out.println("File "+filepath.getName()+" created at "+filepath.getAbsolutePath());
			System.out.println("\n\t\t\tPlease delete the file after copying the password");
		}
	}
}