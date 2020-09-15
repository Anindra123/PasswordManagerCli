package classes;
import java.util.Random;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
		symbols = new String[]{"!","@","#","%","^","&","*","(",")","~",";",":","'","_","+","-"};
		alphabet = null;
		random = new Random();
	}
	
	public String genRandStr(){
		alphabet = "AbCdeFgHiJkLMnOPqRsTUvWxYz";
		char char1 = alphabet.charAt(random.nextInt(alphabet.length()));
		char char2 = alphabet.charAt(random.nextInt(alphabet.length()));
		String randStr = Character.toString(char1)+Character.toString(char2);
		return randStr;
	}
	public String genRandSym(){
		return symbols[random.nextInt(symbols.length)];
	}
	public int genRandNum(){
		return numbers[random.nextInt(numbers.length)];
	}
	
	public String getGeneratedPass(){
		generatedPass = this.genRandStr()+this.genRandSym()+this.genRandNum()+this.genRandStr()+
		this.genRandNum()+this.genRandSym()+this.genRandStr();
		return generatedPass;
	}
	
	public void getIndex(String fileName) throws FileNotFoundException{
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
	
	public void setPasswithAccName(String fileName,String accName,String pass) throws IOException{
		currentIndex++;
		String outputline = currentIndex+" "+accName+" "+pass;
		writeFile = new FileWriter(fileName+".txt",true);
		writeFile.write(outputline);
		writeFile.close();
		System.out.println("\n\t\t\t\t\tPassword Stored sucessfully");
	}
	
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