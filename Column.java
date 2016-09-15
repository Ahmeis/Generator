import java.awt.*;

public class Column {
	private int chNum, qNum, nOffset;
	private Circle circles[];
	private boolean heading=true, Direction = false,switchSide =false;
	private int xSep, ySep;
	private Font nFont;
	private int radius, headingHeight =0;
	private Graphics2D g2;
	private String choices[];
	private FontMetrics fm;
	private Location locations[];
	private int lOffset, pageNumber;
	
	Column(Circle A[], Location[] loc){
		circles = A;
		chNum = A.length;
		g2 = A[0].getGraphics();
		radius = A[0].getRadius();
		nFont = new Font(A[0].getFont().getFontName(),Font.BOLD,(A[0].getFont().getSize()*11)/9);
		getChoices();
		locations = loc;
	}
	Column(Column Old, int QN, int numOffset){
		circles = Old.getCircles();
		chNum = circles.length;
		g2 = Old.getGraphics();
		nFont = Old.getFont();
		heading = Old.getHeading();
		Direction = Old.getDirection();
		switchSide = Old.getSwitchSide();
		xSep = Old.getHorizontalSeparation();
		ySep = Old.getVerticalSeparation();
		radius = circles[0].getRadius();
		locations = Old.getLocation();
		qNum = QN;
		nOffset = numOffset;
		getChoices();
	}

	private void getChoices(){
		choices = new String[chNum];
		for(int i=0;i<chNum;i++){
			choices[i]=circles[i].getChoice();
		}
	}
	boolean getSwitchSide(){
		return switchSide;
	}
	Graphics2D getGraphics(){
		return g2;
	}
	void setGraphics(Graphics2D g2){
		this.g2 = g2;
		for(int i=0;i<circles.length;i++)
			circles[i].setGraphics(g2);
	}
	int getColumnWidth(){
		setGraphicsFont();
		return xSep*(1+chNum)+fm.stringWidth(Integer.toString(qNum+nOffset))-radius; 
	}
	void setSwitchSide(boolean swtch){
		switchSide = swtch;
	}
	void setDirection(boolean R2L){
		Direction = R2L;
	}
	boolean getDirection(){
		return Direction;
	}
	void setHeading(boolean set){
		heading = set;
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
	int getQuestionsNumber(){
		return qNum;
	}
	Circle[] getCircles(){
		return circles;
	}
	void setFont(Font f){
		nFont = f;
	}
	Font getFont(){
		return nFont;
	}
	private void setGraphicsFont(){
		g2.setFont(nFont);
		fm = g2.getFontMetrics();
		g2.setColor(Color.black);
	}
	private void writeHeading(int Cx, int Ty){
		int mid = chNum/2,Ox=0;
		setGraphicsFont();
		int Hy = 3*ySep/2 + fm.getHeight() -fm.getAscent();
		headingHeight =ySep*5/2;
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
	
	void setLocationOffset(int O){
		lOffset = O;
	}
	int getLocationOffset(){
		return lOffset;
	}
	void setPageNumber(int num){
		pageNumber = num;
	}
	Location[] getLocation(){
		return locations;
	}
	
	void writeColumn(int Cx, int Ty){
		locations[0].setType(false);
		if(heading){
			writeHeading(Cx,Ty);
		}else{
			headingHeight = 3*ySep/2;
		}
		int Ox,mid,Dx,Dy;
		mid = chNum/2;
		Ox=0;
		if(chNum%2!=0){
			Ox = xSep/2;
		}
		if(!Direction){
			writeNumbering(Cx-mid*xSep-Ox, Ty+ headingHeight);
			for(int j=0;j<qNum;j++){
				for(int i=1;i<=chNum;i++){
					Dx = Cx-(mid-i)*xSep-Ox;
					Dy = Ty + headingHeight +j*ySep;
					circles[i-1].drawCircle(Dx, Dy);
					locations[lOffset] = new Location(locations[0],Dx,Dy,choices[i-1],j+1+nOffset);
					locations[lOffset].setPageNumber(pageNumber);
					lOffset++;
				}
			}
		}
		else{
			writeNumbering(Cx+mid*xSep+Ox,Ty+headingHeight);
			for(int j=0;j<qNum;j++){
				for(int i=1;i<=chNum;i++){
					Dx = Cx+(mid-i)*xSep+Ox;
					Dy = Ty + headingHeight +j*ySep;
					circles[i-1].drawCircle(Dx, Dy);
					locations[lOffset] = new Location(locations[0],Dx,Dy,choices[i-1],j+1+nOffset);
					locations[lOffset].setPageNumber(pageNumber);
					lOffset++;
				}
			}
		}
	}
	
}
