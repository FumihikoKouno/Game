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
		g.fillRect(x,y,colSize*Data.MESSAGE_X_SIZE,rowSize*Data.MESSAGE_Y_SIZE+8);
		g.setColor(Color.BLACK);
		g.fillRect(x+3,y+3,colSize*Data.MESSAGE_X_SIZE-6,rowSize*Data.MESSAGE_Y_SIZE+2);
		Data.setFont(g,Data.MESSAGE_FONT);
		int start = 0;
		int end = 0;
		int drawX;
		int drawY;
		for(int i = 0; i < rowSize; i++){
			start = i * colSize;
			end = (i+1) * colSize;
			drawX = x + Data.MESSAGE_X_SIZE/2;
			drawY = y + i * Data.MESSAGE_Y_SIZE + Data.MESSAGE_Y_SIZE;
			if(start > messageSize) break;
			if(end > messageSize){
				g.drawString(message.substring(i*colSize,messageSize),drawX,drawY);
			}else{
				g.drawString(message.substring(i*colSize,(i+1)*colSize),drawX,drawY);
			}
		}
	}
	
}