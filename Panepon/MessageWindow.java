import java.awt.Graphics;
import java.awt.Color;

class MessageWindow{
	private int colSize;
	private int rowSize;
	private int x,y;
	private String message;
	private int messageSize;
	
	public MessageWindow(String s,int x,int y,int c, int r){
		message = s;
		messageSize = message.length();
		colSize = c;
		rowSize = r;
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(x*Data.zoom,y*Data.zoom,colSize*Data.MESSAGE_X_SIZE*Data.zoom,(rowSize*Data.MESSAGE_Y_SIZE+8)*Data.zoom);
		g.setColor(Color.BLACK);
		g.fillRect((x+3)*Data.zoom,(y+3)*Data.zoom,(colSize*Data.MESSAGE_X_SIZE-6)*Data.zoom,(rowSize*Data.MESSAGE_Y_SIZE+2)*Data.zoom);
		Data.setFont(g,Data.MESSAGE_FONT);
		int start = 0;
		int end = 0;
		int drawX;
		int drawY;
		for(int i = 0; i < rowSize; i++){
			start = i * colSize;
			end = (i+1) * colSize;
			drawX = (x + Data.MESSAGE_X_SIZE/2)*Data.zoom;
			drawY = (y + i * Data.MESSAGE_Y_SIZE + Data.MESSAGE_Y_SIZE)*Data.zoom;
			if(start > messageSize) break;
			if(end > messageSize){
				g.drawString(message.substring(i*colSize,messageSize),drawX,drawY);
			}else{
				g.drawString(message.substring(i*colSize,(i+1)*colSize),drawX,drawY);
			}
		}
	}
	
}