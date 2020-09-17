package interfaces;

//Interface containing all display functionality implemented by UserMenu
public interface DisplayOperations{
	public void registerMenu();
	public boolean loginMenu();
	public void storePassMenu();
	public void getPassMenu();
	public void modifyPassMenu();
	public void removePassMenu();
	public void generatePassMenu();
	public void mainMenu();
	public void startingMenu();
}