/**
 * メッセージウィンドウのクラス
 */
package Game;

import Game.Common.*;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;


public class MessageWindow{
	private boolean skip;
	private String[] message;
	private int showSize;
	private int width;
	private int messageSize;
	
	private int showFrame = 0;
	
	private final int MESSAGE_Y_SIZE = 24;
	
	private int dir;
	public static final int CENTER = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	
	// 表示する文字列と横文字数と縦文字数
	public MessageWindow(String[] s, int dir){
		this.dir = dir;
		message = s;
		messageSize = 0;
		showFrame = Data.frame;
		for(int i = 0; i < message.length; i++){
			messageSize += message[i].length();
		}
	}
	
	// 下向き三角形を描画する1,2,3が三角形の各頂点の座標
	public void drawTriangle(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3){
		g.setColor(Color.WHITE);
		g.drawLine(x1,y1,x2,y2);
		g.drawLine(x2,y2,x3,y3);
		g.drawLine(x3,y3,x1,y1);
	}
	
	public void update(){
		if((Data.frame&1)==0){
			showSize++;
			if(showSize > messageSize) showSize = messageSize;
		}
		if(KeyStatus.attack){
			if(showSize < messageSize){
				skip = true;
				showSize = messageSize;
			}
			if(skip){
				skip = true;
			}
		}else{
			skip = false;
		}
	}
	
	public boolean end(){
		if(showSize == messageSize){
			if(KeyStatus.attack && !skip){
				Data.frame = showFrame;
				return true;
			}
		}
		return false;
	}
	
	// 描画
	public void draw(Graphics g){
		int width = 0;
		int height = (message.length+1)*MESSAGE_Y_SIZE+3;
		FontMetrics fm = g.getFontMetrics();
		for(int i = 0; i < message.length; i++){
			int tmp = fm.stringWidth(message[i]);
			if(tmp > width){ width = tmp; }
		}
		width += 12;
		
		int x = (Data.WIDTH-width)/2;
		int y = (Data.HEIGHT-height)/2;
		
		g.setColor(Color.WHITE);
		g.fillRect(x,y,width+12,height+12);
		g.setColor(Color.BLUE);
		g.fillRect(x+2,y+2,width+8,height+8);
		g.setColor(Color.WHITE);
		g.fillRect(x+4,y+4,width+4,height+4);
		g.setColor(Color.BLACK);
		g.fillRect(x+6,y+6,width,height);
		g.setColor(Color.WHITE);
		g.setFont(new Font("ＭＳ Ｐゴシック",0, 18));
		
		if(showSize == messageSize){
			int x1 = x+width-12;
			int y1 = y+height-12;
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
		int tmpCharSize = showSize;
		
		for(int i = 0; i < message.length; i++){
			int tmpWidth = fm.stringWidth(message[i]);
			switch(dir){
			case CENTER:
				drawX = (Data.WIDTH-tmpWidth)/2+6;
				break;
			case LEFT:
				drawX = x + 12;
				break;
			case RIGHT:
				drawX = x + width - tmpWidth;
				break;
			default:
				drawX = (Data.WIDTH-tmpWidth)/2+6;
				break;
			}
			drawY = (y+i*MESSAGE_Y_SIZE+28);
			if(message[i].length() < tmpCharSize){
				g.drawString(message[i],drawX,drawY);
				tmpCharSize -= message[i].length();
			}else if(tmpCharSize > 0){
				g.drawString(message[i].substring(0,tmpCharSize),drawX,drawY);
				break;
			}
		}
	}
	
}