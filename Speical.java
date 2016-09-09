import java.awt.*;
public class Speical {
	private int width;
	private int chNum, IDL, xSep, ySep, fNum, tHeight, IDH;
	private int align; // -1 left, 1 right else center
	private boolean ID=false,direction = false;
	private Circle circles[];
	private String text,field[];
	private Font tFont, fFont;
	private FontMetrics fm;
	private Graphics2D  g2;
	Speical(Circle A[], int IDlength){
		circles = A;
		chNum = A.length;
		ID = true;
		IDL = IDlength;
	}
	Speical(){
		
	}
	private void setGraphicsTextFont(){
		g2.setFont(tFont);
		g2.setColor(Color.black);
		fm = g2.getFontMetrics();
	}
	private void setGraphicsFieldFont(){
		g2.setFont(fFont);
		g2.setColor(Color.black);
		fm = g2.getFontMetrics();
	}
	void setAlignment(int A){
		if(A==-1)
			align = -1;
		else if(A==1)
			align = 1;
		else align =0;
	}
	void setText(String T){
		text = T;
	}
	void setTextFont(Font f){
		tFont = f;
	}

	void setField(String[] F){
		field = F;
		fNum = F.length;
	}
	void setFieldFont(Font f){
		fFont = f;
	}
	
	void setSpeicalWidth(int W){
		width = W;
	}
	int getMinimumWidth(){
		if(ID){
			return xSep*(IDL);
		}
		return 0;
	}
	void setHorizontalSeparation(int Sep){
		xSep = Sep;
	}
	void setVerticalSeparation(int Sep){
		ySep = Sep;
	}
	void setDirection(boolean R2L){
		direction = R2L;
	}
	void setGraphics(Graphics2D g2){
		this.g2 = g2;
	}
	private void writeText(int Cx, int Ty){
		setGraphicsTextFont();
		String temp=text;
		int Dx,lineCount=1,Hy,curLen;
		Hy = fm.getHeight();
		switch(align){
		case -1: //Left Aligned
			{
				Dx = Cx-width/2;
				while(temp.length()>0){
					curLen = temp.length();
					if(fm.stringWidth(temp)<width){
						g2.drawString(temp, Dx, Ty+lineCount*Hy);
						lineCount++;
						break;
					}
					for(int i=curLen-1;i>0;i--){
						if(temp.charAt(i)==' '&&fm.stringWidth(temp.substring(0, i))<width){
							g2.drawString(temp.substring(0, i), Dx, Ty+lineCount*Hy);
							lineCount++;
							temp = temp.substring(i+1);
							break;
						}
					}
					if(curLen==temp.length()){
						int a = 2;
						while(fm.stringWidth(temp.substring(0, a))<width){
							a++;
						}
						g2.drawString(temp.substring(0, a-1), Dx, Ty+lineCount*Hy);
						lineCount++;
						temp = temp.substring(a-1);
					}
				}
			}
			break;
		case 1: //Right Aligned
			{
				while(temp.length()>0){
					curLen = temp.length();
					if(fm.stringWidth(temp)<width){
						Dx = Cx + width/2 - fm.stringWidth(temp);
						g2.drawString(temp, Dx, Ty+lineCount*Hy);
						lineCount++;
						break;
					}
					for(int i=curLen-1;i>0;i--){
						if(temp.charAt(i)==' '&&fm.stringWidth(temp.substring(0, i))<width){
							Dx = Cx +width/2 -fm.stringWidth(temp.substring(0, i));
							g2.drawString(temp.substring(0, i), Dx, Ty+lineCount*Hy);
							lineCount++;
							temp = temp.substring(i+1);
							break;
						}
					}
					if(curLen==temp.length()){
						int i = 2;
						while(fm.stringWidth(temp.substring(0, i))<width){
							i++;
						}
						Dx = Cx +width/2 -fm.stringWidth(temp.substring(0, i-1));
						g2.drawString(temp.substring(0, i-1), Dx, Ty+lineCount*Hy);
						lineCount++;
						temp = temp.substring(i-1);
					}
				}
			}	
			break;
		default: //Center Aligned
			{
				while(temp.length()>0){
					curLen = temp.length();
					if(fm.stringWidth(temp)<width){
						Dx = Cx - fm.stringWidth(temp)/2;
						g2.drawString(temp, Dx, Ty+lineCount*Hy);
						lineCount++;
						break;
					}
					for(int i=curLen-1;i>0;i--){
						if(temp.charAt(i)==' '&&fm.stringWidth(temp.substring(0, i))<width){
							Dx = Cx -fm.stringWidth(temp.substring(0, i))/2;
							g2.drawString(temp.substring(0, i), Dx, Ty+lineCount*Hy);
							lineCount++;
							temp = temp.substring(i+1);
							break;
						}
					}
					if(curLen==temp.length()){
						int i = 2;
						while(i<curLen&&fm.stringWidth(temp.substring(0, i))<width){
							i++;
						}
						Dx = Cx -fm.stringWidth(temp.substring(0, i-1))/2;
						g2.drawString(temp.substring(0, i-1), Dx, Ty+lineCount*Hy);
						lineCount++;
						temp = temp.substring(i-1);
					}
				}
			}
			break;
		}
		tHeight = (lineCount-1)*Hy;
	}
	private void writeID(int Cx, int Ty){
		int mid = IDL/2, Ox=0;
		if(IDL%2==1){
			
				Cx +=xSep;
		}else{
			mid++;
			Ox = xSep/2;
		}
		for(int i=1;i<=IDL;i++){
			for(int j=0;j<chNum;j++){
				circles[j].drawCircle(Cx+(mid-i)*xSep-Ox, Ty +(j+1)*ySep);
			}
		}
		IDH = (chNum+1)*ySep;
	}
	private void writeField(int Cx, int Ty){
		setGraphicsFieldFont();
		int Dx = Cx -width/2;
		int Sl,Hy,lineCount=1;
		Hy = fm.getHeight();
		String H = "_",temp;
		int Hl = fm.stringWidth(H);
		for(int i=0;i<fNum;i++){
			temp = field[i];
			temp+=":";
			Sl = fm.stringWidth(temp);
			while(Sl+Hl<width){
				temp+=H;
				Sl+=Hl;
			}
			if(direction){
				Dx = Cx+ width/2 -Sl;
			}
			g2.drawString(temp, Dx, Ty+lineCount*Hy);
			lineCount++;
		}
	}
	void writeSpecial(int Cx, int Ty){
		writeText(Cx,Ty);
		if(ID){
			writeID(Cx, Ty+tHeight);
		}
		writeField(Cx,Ty+tHeight+IDH);
	}
}
