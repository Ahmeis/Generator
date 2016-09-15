
public class Location {
	private int xPos, yPos; // x Position, y Position
	private String choice; // choice
	private boolean type; // True for ID false for choice
	private int number; // Question number/Digit number in ID
	private int page=1; // Page number
	private int xRef, yRef; // Reference point
	Location(int X, int Y, int pageNumber){
		xRef = X;
		yRef = Y;
		page = pageNumber;
	}
	Location(Location Ref, int X, int Y, String Choice, int Number){
		xRef = Ref.getXRef();
		yRef = Ref.getYRef();
		page = Ref.getPageNumber();
		type = Ref.getType();
		xPos = X;
		yPos = Y;
		choice = Choice;
		number = Number;
	}
	
	int getPageNumber(){
		return page;
	}
	boolean getType(){
		return type;
	}
	void setPageNumber(int P){
		page = P;
	}
	int getXRef(){
		return xRef;
	}
	int getYRef(){
		return yRef;
	}
	void setLocation(int X, int Y){
		xPos = X;
		yPos = Y;
	}
	void setType(boolean ID){
		type = ID;
	}
	void setChoice(String C){
		choice = C;
	}
	void setNumber(int N){
		number = N;
	}
	String getLocation(){
		String S;
		if(type){
			S="ID ";
		}else{
			S="Q ";
		}
		S+=number + " ";
		S+= choice + " ";
		int x,y;
		x = xPos - xRef;
		y = yPos - yRef;
		S+= x + " ";
		S+= y + " ";
		S+= page;
		return S;
	}
}
