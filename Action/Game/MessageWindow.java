/**
 * メッセージウィンドウのクラス
 */
package Game;

import Game.Common.*;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;


public class MessageWindow{
	private int colSize;
	private int rowSize;
	private String message;
	private int messageSize;
	private int showSize;
	
	private int showFrame = 0;
	
	private final int MESSAGE_X_SIZE = 18;
	private final int MESSAGE_Y_SIZE = 24;

	// 表示する文字列と横文字数と縦文字数
	public MessageWindow(String s,int c, int r){
		message = s;
		messageSize = message.length();
		colSize = c;
		rowSize = r+1;
		showFrame = Data.frame;
	}
	
	// 下向き三角形を描画する1,2,3が三角形の各頂点の座標
	public void drawTriangle(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3){
		g.setColor(Color.WHITE);
		g.drawLine(x1,y1,x2,y2);
		g.drawLine(x2,y2,x3,y3);
		g.drawLine(x3,y3,x1,y1);
	}
	
	public void update(){
		if((Data.frame&1)==0) return;
		showSize++;
		if(showSize > messageSize) showSize = messageSize;
	}
	
	public boolean end(){
		if(showSize == messageSize){
			if(KeyStatus.attack){
				Data.frame = showFrame;
				return true;
			}
		}
		return false;
	}
	
	// 描画
	public void draw(Graphics g, int x, int y){
		g.setColor(Color.WHITE);
		g.fillRect(x,y,colSize*MESSAGE_X_SIZE+12,rowSize*MESSAGE_Y_SIZE+12);
		g.setColor(Color.BLUE);
		g.fillRect(x+2,y+2,colSize*MESSAGE_X_SIZE+8,rowSize*MESSAGE_Y_SIZE+8);
		g.setColor(Color.WHITE);
		g.fillRect(x+4,y+4,colSize*MESSAGE_X_SIZE+4,rowSize*MESSAGE_Y_SIZE+4);
		g.setColor(Color.BLACK);
		g.fillRect(x+6,y+6,colSize*MESSAGE_X_SIZE,rowSize*MESSAGE_Y_SIZE);
		g.setColor(Color.WHITE);
		g.setFont(new Font("ＭＳ Ｐゴシック",0, 18));
		
		if(showSize == messageSize){
			int x1 = x+colSize*MESSAGE_X_SIZE-12;
			int y1 = y+rowSize*MESSAGE_Y_SIZE-12;
			int x2 = x1+12;
			int y2 = y1;
			int x3 = x1+6;
			int y3 = y1+10;
			if(Data.frame%30 < 15){
				y1 += 2;
				y2 += 2;
				y3 += 2;
			}
			drawTriangle(g,x1,y1,x2,y2,x3,y3);
		}
		
		int start = 0;
		int end = 0;
		int drawX;
		int drawY;
		
		for(int i = 0; i < rowSize; i++){
			start = i * colSize;
			end = (i+1) * colSize;
			drawX = (x+16);
			drawY = (y+i*MESSAGE_Y_SIZE+28);
			if(start > showSize) break;
			if(end > showSize){
				g.drawString(message.substring(i*colSize,showSize),drawX,drawY);
			}else{
				g.drawString(message.substring(i*colSize,(i+1)*colSize),drawX,drawY);
			}
		}
	}
	
}