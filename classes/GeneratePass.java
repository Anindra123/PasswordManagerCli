package classes;
import java.util.Random;
import java.util.Scanner;


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
}