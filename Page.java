import java.awt.*;
public class Page {
	private Column[] column;
	private Special special;
	private boolean orientation,sExist;
	private int D[],cOffset, cLength;
	private final int width =0,mTop =1 ,tHeight =2, mSide =3;
	
	Page(Graphics2D g2,Column[] C, int columnOffset, int columnLength){
		sExist = false;
		column = C;
		cOffset = columnOffset;
		cLength = columnLength;
		for(int i=cOffset;i<cOffset+cLength;i++){
			column[i].setGraphics(g2);
		}
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
	void writePage(){
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
			for(int i=0;i<cLength;i++){
				Dx = (i+2)*xSep + sW + i*cW + cW/2;
				column[i].writeColumn(Dx, Ty);
			}
		}else{
			xSep = D[width] - sW - cLength*cW -D[mSide]*2;
			xSep /=cLength;
			special.writeSpecial(D[mSide]+sW/2, Ty);
			for(int i=0;i<cLength;i++){
				Dx = D[mSide] + sW + i*cW + (i+2)*xSep + cW/2;
				column[i].writeColumn(Dx, Ty);
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
			for(int i=0;i<cLength;i++){
				Dx = W - ((i+2)*xSep + sW + i*cW + cW/2);
				column[i].writeColumn(Dx, Ty);
			}
		}else{
			xSep = D[width] - sW - cLength*cW -D[mSide]*2;
			xSep /=cLength;
			special.writeSpecial(W -D[mSide]-sW/2, Ty);
			for(int i=0;i<cLength;i++){
				Dx = W-( D[mSide] + sW + i*cW + (i+2)*xSep + cW/2);
				column[i].writeColumn(Dx, Ty);
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
			}
		}else{
			for(int i=0;i<cLength;i++){
				Dx = W - (D[mSide] + i*xSep +i*cW +cW/2);
				column[i+cOffset].writeColumn(Dx, Ty);
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
			}
		}else{
			for(int i=0;i<cLength;i++){
				Dx = D[mSide] + i*xSep +i*cW +cW/2;	
				column[i+cOffset].writeColumn(Dx, Ty);
			}
		}
	}
}
