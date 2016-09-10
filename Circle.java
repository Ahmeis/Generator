import java.awt.*;
public class Circle{
	private int radius =30;
	private double thickness =0.92;
	private String choice;
	private int xOffset, yOffset;
	private Font cFont = new Font("Times new roman",Font.BOLD,35);
	private Color fColor = Color.LIGHT_GRAY;
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
		g2 = Old.getGraphics();
		choice = S;
	}
	Circle(Circle Old, String S, int xOffset, int yOffset){
		radius = Old.getRadius();
		thickness = Old.getThickness();
		cFont = Old.getFont();
		fColor = Old.getFontColor();
		g2 = Old.getGraphics();
		choice = S;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
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
	void setFont(Font f){
		cFont = f;
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
		cFont = new Font(cFont.getFontName(),cFont.getStyle(),size);
	}
	void setFontName(String name){
		cFont = new Font(name, cFont.getStyle(),cFont.getSize());
	}
	void setFontStyle(int style){
		cFont = new Font(cFont.getFontName(), style, cFont.getSize());
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
