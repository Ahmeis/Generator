import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class Generator{
	private BufferedImage image, images[];
	private Graphics2D g2;
	private int width, height; // Page Dimensions
	private int mSide, mTop, mBottom; // Margin Dimensions
	private double thickness; // Circle Thickness
	private String title, text, field[], choices[], IDC[]; 
	private Font fText, fTitle, fCircle, fField, nFont;
	private int align; // Text alignment
	private boolean ID, heading;// Special Exist, ID Exist, Heading Exist
	private boolean orientation, SS; // Orientation ,Numbering flip
	private int IDL, radius; // ID length, circle radius
	private int xCS,yCS; // Horizontal and Vertical Circle Separation
	private int xCoS; // Minimum Horizontal Column Separation
	private int maxHeight, maxWidth; // Maximum usable height and width (Paper Dim. - margin)
	private int qNum, colNum; // Questions Number, Number of normal columns
	private int CQN, colPP; // Number of questions in a column, columns per page
	private int SCQN; // Number of questions in a column in special page
	private int sColNum; // Number of special page columns
	private int cWidth; // Column width
	private int specialWidth, tHeight; // width of special column, title height
	private int pagesNumber;
	private Color choiceColor; 
	private Circle cCircle[],IDCircle[],base;
	private Column columnBase, column[];
	private Special special;
	private FontMetrics fm;
	private Page pages[];
	private String ProjectName;
	private int Dimensions[];
	private final int dWidth =0, dHeight =1, dmTop =2, dmBottom =3, dtHeight =4, dmSide =5;
	private Location locations[];
	Generator(BufferedImage A){
		image = A;
		g2 = image.createGraphics();
		width = image.getWidth();
		height = image.getHeight();
	}
	Generator(int width, int height){
		image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		g2 = image.createGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, width, height);
		this.width = width;
		this.height = height;
	}
	
	void setMargin(int side, int top, int bottom){
		mSide = side;
		mTop = top;
		mBottom = bottom;
		setMaximums();
	}
	private void setMaximums(){
		maxHeight = height - mTop - mBottom;
		maxWidth = width - 2*mSide;
	}
	void setOrientation(boolean R2L){
		orientation = R2L;
	}
	void setQuestionNumber(int num){
		qNum = num;
	}
	void setTextFont(Font f){
		fText = f;
	}
	void setText(String t){
		text = t;
	}
	void setTextAlignment(int A){
		align = A;
	}
	
	void setFieldFont(Font f){
		fField = f;
	}
	void setFields(String[] F){
		field = F;
	}
	
	void setTitleFont(Font f){
		fTitle = f;
	}
	void setTitle(String t){
		title = t;
	}
	
	void setChoices(String[] C){
		choices = C;
	}
	
	void setID(boolean set){
		ID = set;
	}
	void setIDChoices(String[] I){
		IDC = I;
	}
	void setIDLength(int l){
		IDL = l;
	}

	void setHeading(boolean set){
		heading = set;
	}
	void setChoiceHS(int xS){ // Choices Horizontal Separation
		xCS = xS;
	}
	void setChoiceVS(int yS){ // Choices Vertical Separation
		yCS = yS;
	}
	
	private void setCQN(){ // Get Column only page maximum questions number
		if(heading){
			CQN= (maxHeight)/yCS -2;
		}
		else{
			CQN = maxHeight/yCS -1;
		}
	}
	private void setColumnPerPage(){
		int s = maxWidth;
		colPP =-1;
		while(s>0){
			s-=(xCoS + cWidth);
			colPP++;
		}
	}
	private void setColumnNumber(){ 
		setCQN();
		colNum = Ciel((qNum-sColNum*SCQN),CQN);
		setColumnPerPage();
	}
	private void setSCQN(){
		if(heading){
			SCQN = (maxHeight-tHeight)/yCS -2;
		}else{
			SCQN = (maxHeight -tHeight)/yCS -1;
		}
	}
	private void setColumnSNumber(){
		int s = maxWidth - specialWidth;
		sColNum =-1;
		while(s>0){
			s-=(xCoS + cWidth);
			sColNum++;
		}
		setSCQN();
		int d = qNum -sColNum*SCQN;
		if(d<=0){
			sColNum = Ciel(qNum,SCQN);
			colNum = 0;
		}else{
			setColumnNumber();
		}
	}
	
	void setChoiceColor(Color c){
		choiceColor = c;
	}
	void setChoicesFont(Font f){
		fCircle = f;
	}
	void setCircleRadius(int i){
		radius =i;
	}
	void setCircleThickness(double t){
		thickness = t;
	}
	private void setBaseCircle(){
		base = new Circle(radius);
		base.setFont(fCircle);
		base.setGraphics(g2);
		base.setThickness(thickness);
		base.setFontColor(choiceColor);
	}
	private void setChoiceCircles(){
		setBaseCircle();
		int cL = choices.length;
		cCircle = new Circle[cL];
		for(int i=0;i<cL;i++){
			cCircle[i] = new Circle(base,choices[i]);
		}
	}
	private void setIDCircles(){
		setBaseCircle();
		int IL = IDC.length;
		IDCircle = new Circle[IL];
		for(int i=0;i<IL;i++){
			IDCircle[i] = new Circle(base,IDC[i]);
		}
	}

	void setSwitchSide(boolean swtch){
		SS = swtch;
	}
	void setNumberingFont(Font f){
		nFont = f;
	}
	private void setColumnBase(){
		columnBase = new Column(cCircle, locations);
		columnBase.setHorizontalSeparation(xCS);
		columnBase.setVerticalSeparation(yCS);
		columnBase.setDirection(orientation);
		columnBase.setHeading(heading);
		columnBase.setFont(nFont);
		columnBase.setGraphics(g2);
		columnBase.setSwitchSide(SS);
		cWidth = columnBase.getColumnWidth();
	}
	private void setColumns(){ 
		setChoiceCircles();
		setColumnBase();
		setColumnSNumber();
		column = new Column[colNum+sColNum];
		if(colNum==0){
			for(int i=0;i<sColNum-1;i++){
				column[i] = new Column(columnBase,SCQN,SCQN*i);
			}
			column[sColNum-1]= new Column(columnBase,Remainder(qNum,SCQN),SCQN*(sColNum-1));
		}else{
			for(int i=0;i<sColNum;i++){
				column[i] = new Column(columnBase,SCQN,SCQN*i);
			}
			int Of = SCQN * sColNum;
			for(int i=0;i<colNum-1;i++){
				column[i+sColNum]= new Column(columnBase,CQN,Of + CQN*i);
			}
			column[colNum+sColNum-1]= new Column(columnBase,Remainder((qNum-Of),CQN),Of + CQN*(colNum-1));
		}
	}
	void setMinimumColumnSeparation(int s){
		xCoS = s;
	}
	
	void setSpecialWidth(int width){
		specialWidth = width;
	}
	private void setSepcial(){
		if(ID){
			setIDCircles();
			special = new Special(IDCircle, IDL, locations);
			special.setVerticalSeparation(yCS);
			special.setHorizontalSeparation(xCS);
		}
		special.setAlignment(align);
		special.setDirection(orientation);
		special.setField(field);
		special.setFieldFont(fField);
		special.setGraphics(g2);
		special.setText(text);
		special.setTextFont(fText);
		special.setSpeicalWidth(specialWidth);
	}
	
	private void insertTitle(){
		g2.setColor(Color.black);
		g2.setFont(fTitle);
		fm = g2.getFontMetrics();
		int Dx, Hy, Ty,lineCount,curLen,Mx;
		String temp = title;
		Hy = fm.getHeight();
		Ty = mTop +Hy;
		Mx = width - 2*mSide;
		lineCount =0;
		while(temp.length()>0){
			curLen = temp.length();
			if(fm.stringWidth(temp)<Mx){
				Dx = width/2 - fm.stringWidth(temp)/2;
				g2.drawString(temp, Dx, Ty+lineCount*Hy);
				lineCount++;
				break;
			}
			for(int i=curLen-1;i>0;i--){
				if(temp.charAt(i)==' '&&fm.stringWidth(temp.substring(0, i))<Mx){
					Dx = width/2 -fm.stringWidth(temp.substring(0, i))/2;
					g2.drawString(temp.substring(0, i), Dx, Ty+lineCount*Hy);
					lineCount++;
					temp = temp.substring(i+1);
					break;
				}
			}
			if(curLen==temp.length()){
				int i = 2;
				while(i<curLen&&fm.stringWidth(temp.substring(0, i))<Mx){
					i++;
				}
				Dx = width/2 -fm.stringWidth(temp.substring(0, i-1))/2;
				g2.drawString(temp.substring(0, i-1), Dx, Ty+lineCount*Hy);
				lineCount++;
				temp = temp.substring(i-1);
			}
		}
		tHeight = Hy*lineCount;
	}
	private void setNewGraphics(int i){
		images[i] = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		g2 = images[i].createGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, width, height);
	}
	private void setImages(){
		images = new BufferedImage[pagesNumber];
	}
	private void setPages(){
		setDimensions();
		insertTitle();
		setSepcial();
		setColumns();
		setLocationNumber();
		if(colNum==0){
			pagesNumber =1;
		}else{
			pagesNumber = Ciel(colNum,colPP)+1;
		}
		pages = new Page[pagesNumber];
		setImages();
		setNewGraphics(0);
		setDimensions();
		setSepcial();
		setColumns();
		insertTitle();
		int lOffset =1;
		setLocationBase(1);
		pages[0] = new Page(g2, column,special,sColNum ,locations);
		pages[0].setDimensions(Dimensions);
		pages[0].writePage();
		lOffset = pages[0].getLocationOffset();
		int counter = colNum;
		for(int i=0;i<pagesNumber-1;i++){
			setNewGraphics(i+1);
			setLocationBase(i+1);
			pages[i+1] = new Page(pages[i],g2,Divide(counter,colPP),i+2);
			pages[i+1].setLocationOffset(lOffset);
			pages[i+1].writePage();
			lOffset = pages[i+1].getLocationOffset();
			counter -=colPP;
		}
	}
	BufferedImage[] getImages(){
		return images;
	}
	void GenerateImages() throws IOException{
		setPages();
		File output;
		for(int i=0;i<pagesNumber;i++){
			output = new File(ProjectName + i +".jpg");
			ImageIO.write(images[i],"jpg",output);
		}
	}
	void GenerateModelFile() throws IOException{
		File output = new File(ProjectName + ".txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		for(int i=1;i<locations.length;i++){
			writer.write(locations[i].getLocation());
			writer.newLine();
		}
		writer.close();
	}
	
	private void setLocationNumber(){
		int x = qNum * choices.length + IDL * IDC.length +1;
		locations = new Location[x];
	}
	private void setLocationBase(int pageNumber){
		locations[0] = new Location(mSide/2,mTop/2, pageNumber);
	}	
	
	private void setDimensions(){
		Dimensions =new int[6];
		Dimensions[dWidth] = width;
		Dimensions[dHeight] = height;
		Dimensions[dmTop] = mTop;
		Dimensions[dmBottom] = mBottom;
		Dimensions[dtHeight] = tHeight;
		Dimensions[dmSide] = mSide;
	}
	void setProjectName(String name){
		ProjectName = name;
	}
	private int Ciel(int A, int B){
		return A%B>0?A/B+1:A/B;
	}
	private int Remainder(int A, int B){
		return A%B>0?A%B:B;
	}
	private int Divide(int A, int B){
		return A>B?B:A;
	}
}