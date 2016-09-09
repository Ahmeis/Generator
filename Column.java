import java.awt.*;

public class Column {
	private int chNum, qNum, nOffset;
	private Circle circles[];
	private boolean heading=true, Direction = false,switchSide =false;
	private int xSep, ySep;
	private Font nFont;
	private int radius, fStyle, fSize, headingHeight =0;
	private Graphics2D g2;
	private String fName,choices[];
	private FontMetrics fm;
	Column(Circle A[]){
		circles = A;
		chNum = A.length;
		g2 = A[0].getGraphics();
		radius = A[0].getRadius();
		nFont = new Font(A[0].getFontName(),Font.BOLD,(A[0].getFontSize()*11)/9);
		getChoices();
	}
	Column(Column Old){
		circles = Old.getCircles();
		chNum = circles.length;
		g2 = Old.getGraphics();
		nFont = Old.getFont();
		fName = Old.getFontName();
		fStyle = Old.getFontStyle();
		fSize = Old.getFontSize();
		heading = Old.getHeading();
		Direction = Old.getDirection();
		switchSide = Old.getSwitchSide();
		xSep = Old.getXSep();
		ySep = Old.getYSep();
		radius = circles[0].getRadius();
		getChoices();
	}
	
	private void getChoices(){
		choices = new String[chNum];
		for(int i=0;i<chNum;i++){
			choices[i]=circles[i].getChoice();
		}
	}
	int getXSep(){
		return xSep;
	}
	int getYSep(){
		return ySep;
	}
	boolean getSwitchSide(){
		return switchSide;
	}
	Graphics2D getGraphics(){
		return g2;
	}
	void setGraphics(Graphics2D g2){
		this.g2 = g2;
	}
	int getColumnWidth(){
		return xSep*(1+chNum)+fm.stringWidth(Integer.toString(qNum+nOffset))-radius; 
	}
	void setDirection(boolean R2L){
		Direction = R2L;
	}
	boolean getDirection(){
		return Direction;
	}
	void setHeading(){
		heading = true;
	}
	void removeHeading(){
		heading = false;
		headingHeight=0;
	}
	boolean getHeading(){
		return heading;
	}
	void setQuestionsNumber(int questions){
		qNum = questions;
	}
	void setNumberingOffset(int offset){
		nOffset = offset;
	}
	void setHorizontalSeparation(int xDis){
		xSep = xDis;
	}
	void setVerticalSeparation(int yDis){
		ySep = yDis;
	}
	int getHorizontalSeparation(){
		return xSep;
	}
	int getVerticalSeparation(){
		return ySep;
	}
	int getNumberingEnd(){
		return nOffset+qNum;
	}
	int getQuestionsNumber(){
		return qNum;
	}
	Circle[] getCircles(){
		return circles;
	}
	void setFont(Font f){
		nFont = f;
		fName = f.getFontName();
		fStyle = f.getStyle();
		fSize = f.getSize();
	}
	void setFont(String f, int style, int size){
		nFont = new Font(f,style,size);
		fName = f;
		fStyle = style;
		fSize = size;
	}
	Font getFont(){
		return nFont;
	}
	String getFontName(){
		return nFont.getFontName();
	}
	int getFontStyle(){
		return nFont.getStyle();
	}
	
	int getFontSize(){
		return nFont.getSize();
	}
	void setFontSize(int size){
		nFont = new Font(fName,fStyle,size);
		fSize = size;
	}
	void setFontStyle(int style){
		nFont = new Font(fName,style,fSize);
		fStyle = style;
	}
	void setFontName(String name){
		nFont = new Font(name,fStyle,fSize);
		fName = name;
	}
	private void setGraphicsFont(){
		g2.setFont(nFont);
		fm = g2.getFontMetrics();
		g2.setColor(Color.black);
	}
	private void writeHeading(int Cx, int Ty){
		int mid = chNum/2,Ox=0;
		setGraphicsFont();
		int Hy = fm.getHeight();
		headingHeight = Hy+ySep;
		if(chNum%2==1){
			Ox = xSep/2;
			if(!Direction){
				Cx +=xSep;
			}
			else{
				Cx-=xSep;
			}
		}
		else{
			mid--;
		}
		if(!Direction){
			for(int i=0;i<chNum;i++){
				g2.drawString(choices[i],Cx-(mid-i)*xSep-Ox-fm.stringWidth(choices[i])/2, Ty + Hy);
			}
		}
		else{
			for(int i=0;i<chNum;i++){
				g2.drawString(choices[i],Cx+(mid-i)*xSep+Ox-fm.stringWidth(choices[i])/2, Ty + Hy);
			}
		}
	}
	private void writeNumbering(int Cx, int Cy){
		String number;
		int Hy,Ay;
		setGraphicsFont();
		Hy = fm.getHeight();
		Ay = fm.getAscent();
		if(!Direction){
			for(int i=1;i<=qNum;i++){
				number = Integer.toString(i+nOffset);
				g2.drawString(number+'-', Cx-fm.stringWidth(number), Cy+(i-1)*ySep+Hy-Ay);
			}
		}
		else{
			if(switchSide){
				for(int i=1;i<=qNum;i++){
					number = Integer.toString(i+nOffset);
					g2.drawString(number+'-', Cx+fm.stringWidth("-"), Cy+(i-1)*ySep+Hy-Ay);
					}
			}
			else{
				for(int i=1;i<=qNum;i++){
					number = Integer.toString(i+nOffset);
					g2.drawString('-'+number, Cx+fm.stringWidth("-"), Cy+(i-1)*ySep+Hy-Ay);
					}
			}
		}
	}
	void writeColumn(int Cx, int Ty){
		if(heading){
			writeHeading(Cx,Ty);
		}
		int Ox,mid;
		mid = chNum/2;
		Ox=0;
		if(chNum%2!=0){
			Ox = xSep/2;
		}
		if(!Direction){
			writeNumbering(Cx-mid*xSep-Ox, Ty+ headingHeight);
			for(int i=1;i<=chNum;i++){
				for(int j=0;j<qNum;j++){
					circles[i-1].drawCircle(Cx-(mid-i)*xSep-Ox, Ty + headingHeight +j*ySep);
				}
			}
		}
		else{
			writeNumbering(Cx+mid*xSep+Ox,Ty+headingHeight);
			for(int i=1;i<=chNum;i++){
				for(int j=0;j<qNum;j++){
					circles[i-1].drawCircle(Cx+(mid-i)*xSep+Ox, Ty + headingHeight +j*ySep);
				}
			}
		}
	}
	
}
