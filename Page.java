import java.awt.*;
public class Page {
	private Column[] column;
	private Special special;
	private boolean orientation,sExist;
	private int D[],cOffset, cLength;
	private final int width =0, height =1, mTop =2, mBottom =3 ,tHeight =4, mSide =5;
	private int y1, y2 ,y3;
	private Graphics2D g2;
	
	Page(Graphics2D g2,Column[] C, int columnOffset, int columnLength){
		sExist = false;
		column = C;
		cOffset = columnOffset;
		cLength = columnLength;
		for(int i=cOffset;i<cOffset+cLength;i++){
			column[i].setGraphics(g2);
		}
		this.g2 = g2;
	}
	Page(Graphics2D g2,Column[] C, Special S,int columnLength){
		column = C;
		special = S;
		sExist = true;
		cLength = columnLength;
		for(int i=0;i<cLength;i++){
			column[i].setGraphics(g2);
		}
		special.setGraphics(g2);
		this.g2 = g2;
	}
	Page(Page Old,Graphics2D g2, int columnLength){
		sExist = false;
		column = Old.getColumns();
		orientation = Old.getOrientation();
		cOffset = Old.getNextColumnNumber();
		D = Old.getDimensions();
		cLength = columnLength;
		for(int i=cOffset;i<cOffset+cLength;i++){
			column[i].setGraphics(g2);
		}
		this.g2 = g2;
	}
	
	int getNextColumnNumber(){
		return cOffset+cLength;
		
	}
	int[] getDimensions(){
		return D;
	}
	Column[] getColumns(){
		return column;
	}
	boolean getOrientation(){
		return orientation;
	}
	void setOrientation(boolean R2L){
		orientation = R2L;
	}
	void setDimensions(int[] A){
		D = A;
	}
	
	private void setLength(){
		y1 =D[mTop]*5/2;
		y2 = D[tHeight]+D[mTop]*5/2;
		y3 =D[height]-D[mBottom]*3/2;
	}
	
	void writePage(){
		setLength();
		if(sExist){
			if(orientation){
				writeSpecialPageR2L();
			}else{
				writeSpecialPageL2R();
			}
		}else{
			if(orientation){
				writeNormalPageR2L();
			}else{
				writeNormalPageL2R();
			}
		}
	}
	private void writeSpecialPageL2R(){
		int xSep,Ty, cW, sW;
		int Dx;
		cW = column[0].getColumnWidth();
		sW = special.getWidth();
		Ty = D[mTop]+D[tHeight];
		xSep = D[width]-sW -cLength*cW;
		xSep/=(cLength+2);
		if(xSep>D[mSide]){
			special.writeSpecial(xSep+sW/2, Ty);
			g2.setColor(Color.BLACK);
			g2.drawLine(xSep*3/2 + sW, y2, xSep*3/2 + sW, y3);
			for(int i=0;i<cLength;i++){
				Dx = (i+2)*xSep + sW + i*cW + cW/2;
				column[i].writeColumn(Dx, Ty);
				if(i!=cLength-1){
					g2.setColor(Color.BLACK);
					g2.drawLine(Dx + cW/2 +xSep/2, y2, Dx + cW/2 +xSep/2, y3);
				}
			}
		}else{
			xSep = D[width] - sW - cLength*cW -D[mSide]*2;
			xSep /=cLength;
			special.writeSpecial(D[mSide]+sW/2, Ty);
			g2.setColor(Color.BLACK);
			g2.drawLine(xSep/2+ D[mSide]+ sW, y2, xSep/2+ D[mSide]+ sW, y3);
			for(int i=0;i<cLength;i++){
				Dx = D[mSide] + sW + i*cW + (i+2)*xSep + cW/2;
				column[i].writeColumn(Dx, Ty);
				if(i!=cLength-1){
					g2.setColor(Color.BLACK);
					g2.drawLine(Dx + cW/2 +xSep/2, y2, Dx + cW/2 +xSep/2, y3);
				}
			}
		}
	}
	private void writeSpecialPageR2L(){
		int xSep,Ty, cW, sW,W;
		int Dx;
		W = D[width];
		cW = column[0].getColumnWidth();
		sW = special.getWidth();
		Ty = D[mTop]+D[tHeight];
		xSep = D[width]-sW -cLength*cW;
		xSep/=(cLength+2);
		if(xSep>D[mSide]){
			special.writeSpecial(W-xSep-sW/2, Ty);
			g2.setColor(Color.black);
			g2.drawLine(W -xSep*3/2 -sW, y2, W -xSep*3/2 -sW, y3);
			for(int i=0;i<cLength;i++){
				Dx = W - ((i+2)*xSep + sW + i*cW + cW/2);
				column[i].writeColumn(Dx, Ty);
				if(i!=cLength-1){
					g2.setColor(Color.BLACK);
					g2.drawLine(Dx -cW/2 -xSep/2, y2, Dx -cW/2 -xSep/2, y3);
				}
			}
		}else{
			xSep = D[width] - sW - cLength*cW -D[mSide]*2;
			xSep /=cLength;
			special.writeSpecial(W -D[mSide]-sW/2, Ty);
			g2.setColor(Color.black);
			g2.drawLine(W- D[mSide]- sW - xSep/2, y2, W- D[mSide]- sW - xSep/2, y3);
			for(int i=0;i<cLength;i++){
				Dx = W-( D[mSide] + sW + i*cW + (i+2)*xSep + cW/2);
				column[i].writeColumn(Dx, Ty);
				if(i!=cLength-1){
					g2.setColor(Color.BLACK);
					g2.drawLine(Dx -cW/2 -xSep/2, y2, Dx -cW/2 -xSep/2, y3);
				}
			}
		}
	}
	private void writeNormalPageR2L(){
		int xSep, Ty, cW, Dx, W;
		W = D[width];
		cW = column[0].getColumnWidth();
		Ty = D[mTop];
		xSep = D[width] - cLength*cW;
		xSep/=(cLength+1);
		if(xSep>D[mSide]){
			for(int i=0;i<cLength;i++){
				Dx = W -( (i+1)*xSep + i*cW + cW/2);
				column[i+cOffset].writeColumn(Dx, Ty);
				if(i!=cLength-1){
					g2.setColor(Color.BLACK);
					g2.drawLine(Dx -cW/2 -xSep/2, y1, Dx -cW/2 -xSep/2, y3);
				}
			}
		}else{
			for(int i=0;i<cLength;i++){
				Dx = W - (D[mSide] + i*xSep +i*cW +cW/2);
				column[i+cOffset].writeColumn(Dx, Ty);
				if(i!=cLength-1){
					g2.setColor(Color.BLACK);
					g2.drawLine(Dx -cW/2 -xSep/2, y1, Dx -cW/2 -xSep/2, y3);
				}
			}
		}
	}
	private void writeNormalPageL2R(){
		int xSep, Ty, cW, Dx;
		cW = column[0].getColumnWidth();
		Ty = D[mTop];
		xSep = D[width] - cLength*cW;
		xSep/=(cLength+1);
		if(xSep>D[mSide]){
			for(int i=0;i<cLength;i++){
				Dx = (i+1)*xSep +i*cW + cW/2;
				column[i+cOffset].writeColumn(Dx, Ty);
				if(i!=cLength-1){
					g2.setColor(Color.BLACK);
					g2.drawLine(Dx +cW/2 +xSep/2, y1, Dx +cW/2 +xSep/2, y3);
				}
			}
		}else{
			for(int i=0;i<cLength;i++){
				Dx = D[mSide] + i*xSep +i*cW +cW/2;	
				column[i+cOffset].writeColumn(Dx, Ty);
				if(i!=cLength-1){
					g2.setColor(Color.BLACK);
					g2.drawLine(Dx +cW/2 +xSep/2, y1, Dx +cW/2 +xSep/2, y3);
				}
			}
		}
	}
}
