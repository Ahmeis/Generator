import java.awt.*;
public class Circle{
	private int radius =30;
	private double thickness =0.92;
	private String choice;
	private int xOffset, yOffset;
	private Font cFont = new Font("Times new roman",Font.BOLD,35);
	private Color fColor = Color.LIGHT_GRAY;
	private String fName;
	private int fSize =35, fStyle =1;
	private Graphics2D g2;
	private FontMetrics fm;
	Circle(int radius){
		this.radius = radius;
	}
	Circle(Circle Old, String S){
		radius = Old.getRadius();
		thickness = Old.getThickness();
		cFont = Old.getFont();
		fColor = Old.getFontColor();
		fName = Old.getFontName();
		fStyle = Old.getFontStyle();
		fSize = Old.getFontSize();
		g2 = Old.getGraphics();
		fm = Old.getFontMetrics();
		choice = S;
	}
	Circle(Circle Old, String S, int xOffset, int yOffset){
		radius = Old.getRadius();
		thickness = Old.getThickness();
		cFont = Old.getFont();
		fColor = Old.getFontColor();
		fName = Old.getFontName();
		fStyle = Old.getFontStyle();
		fSize = Old.getFontSize();
		g2 = Old.getGraphics();
		fm = Old.getFontMetrics();
		choice = S;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	FontMetrics getFontMetrics(){
		return fm;
	}
	int getRadius(){
		return radius;
	}
	void setXOffset(int X){
		xOffset = X;
	}
	void setYOffset(int Y){
		yOffset = Y;
	}
	void setThickness(double T){
		thickness = T;
	}
	double getThickness(){
		return thickness;
	}
	void setChoice(String C){
		choice = C;
	}
	void setFont(String S, int style, int size){
		cFont = new Font(S,style,size);
		fName = S;
		fSize = size;
		fStyle = style;
	}
	void setFont(Font f){
		cFont = f;
		fName = f.getFontName();
		fStyle = f.getStyle();
		fSize = f.getSize();
	}
	String getFontName(){
		return fName;
	}
	int getFontStyle(){
		return fStyle;
	}
	int getFontSize(){
		return fSize;
	}
	Font getFont(){
		return cFont;
	}
	void setFontColor(Color C){
		fColor = C; 
	}
	void setFontColor(int C){
		fColor = new Color(C);
	}
	Color getFontColor(){
		return fColor;	
	}
	void setGraphics(Graphics2D g2){
		this.g2 = g2;
	}
	Graphics2D getGraphics(){
		return g2;
	}
	void drawCircle(int Cx, int Cy){
		g2.setColor(Color.black);
		g2.fillOval(Cx-radius, Cy-radius, radius*2, radius*2);
		int iRadius = (int)(radius*thickness);
		g2.setColor(Color.white);
		g2.fillOval(Cx-iRadius, Cy-iRadius, iRadius*2, iRadius*2);
		setGraphicsFont();
		g2.drawString(choice, Cx-fm.stringWidth(choice)/2+xOffset,
						Cy + fm.getHeight() -fm.getAscent()*14/15 +yOffset);
	}
	void setFontSize(int size){
		cFont = new Font(fName,fStyle,size);
		fSize = size;
	}
	void setFontName(String name){
		cFont = new Font(name, fStyle, fSize);
		fName = name;
	}
	void setFontStyle(int style){
		cFont = new Font(fName, style, fSize);
		fStyle = style;
	}
	private void setGraphicsFont(){
		g2.setFont(cFont);
		fm = g2.getFontMetrics();
		g2.setColor(fColor);
	}
	String getChoice(){
		return choice;
	}
}
