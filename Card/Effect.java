import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Effect implements Common{
	
	private int effectType;
	private Point effectPoint;
	private int damage;
	private int count;
	private boolean end;
	private Image effectImage;
	private ImageIcon icon;
	
	public Effect(){
		reset();
	}
	
	public boolean getEnd(){
		return end;
	}
	
	public void setPoint(Point p){
		effectPoint = p;
	}
	
	public void setEffectType(int e){
		effectType = e;
	}
	
	public void setDamage(int d){
		damage = d;
	}
	
	private void reset(){
		effectPoint = null;
		effectType = 0;
		damage = 0;
		count = 0;
		end = false;
		icon = null;
		effectImage = null;
	}
	
	public void draw(Graphics g){
		g.setFont(font);
		switch(effectType){
		case DAMAGE:
			if(damage < 0) g.setColor(Color.red);
			if(damage > 0) g.setColor(Color.green);
			count++;
			g.drawString(damage +"",
				FIELD_X + effectPoint.x * CS, FIELD_Y + effectPoint.y * CS - count);
			g.setColor(Color.white);
			if(count > 10){
				end = true;
				return;
			}
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			break;
		case ATTACK:
			icon = new ImageIcon(getClass().getResource("image/effect/attack.gif"));
			effectImage = icon.getImage();
			g.drawImage(effectImage, FIELD_X + effectPoint.x * CS, FIELD_Y + effectPoint.y * CS, null);
			try{
				Thread.sleep(150);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			end = true;
			break;
		case FRIEND_SUPPORT:
			icon = new ImageIcon(getClass().getResource("image/effect/friendSupport.gif"));
			effectImage = icon.getImage();
			g.drawImage(effectImage,
				FIELD_X + effectPoint.x * CS, FIELD_Y + effectPoint.y * CS,
				FIELD_X + effectPoint.x * CS + CS, FIELD_Y + effectPoint.y * CS + CS,
				count * CS, 0, count * CS + CS, CS,
				null);
			count++;
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			if(count == 5){
				end = true;
			}
			break;
		case TARGET_SUPPORT:
			icon = new ImageIcon(getClass().getResource("image/effect/targetSupport.gif"));
			effectImage = icon.getImage();
			g.drawImage(effectImage,
				FIELD_X + effectPoint.x * CS, FIELD_Y + effectPoint.y * CS,
				FIELD_X + effectPoint.x * CS + CS, FIELD_Y + effectPoint.y * CS + CS,
				count * CS, 0, count * CS + CS, CS,
				null);
			count++;
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			if(count == 5){
				end = true;
			}
			break;
		case RED_TURN:
			icon = new ImageIcon(getClass().getResource("image/effect/turnChange.gif"));
			effectImage = icon.getImage();
			count++;
			g.drawImage(effectImage,
				640-count*50, 295, 640-count*50+250,345,
				0,0,250,50,
				null);
			if(count == 10){
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			if(count == 19){
				end = true;
			}
			break;
		case BLUE_TURN:
			icon = new ImageIcon(getClass().getResource("image/effect/turnChange.gif"));
			effectImage = icon.getImage();
			count++;
			g.drawImage(effectImage,
				640-count*50, 295, 640-count*50+250,345,
				0,50,250,100,
				null);
			if(count == 10){
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			if(count == 19){
				end = true;
			}
			break;
		case BATTLE_START:
			icon = new ImageIcon(getClass().getResource("image/effect/battleStart.gif"));
			effectImage = icon.getImage();
			count++;
			g.drawImage(effectImage,
				640-count*50, 295, 640-count*50+350,345,
				0,0,350,50,
				null);
			if(count == 10){
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			if(count == 19){
				end = true;
			}
			break;
		case RED_WIN:
			icon = new ImageIcon(getClass().getResource("image/effect/winner.gif"));
			effectImage = icon.getImage();
			count++;
			g.drawImage(effectImage,
				640-count*50, 295, 640-count*50+350,345,
				0,0,350,50,
				null);
			if(count == 10){
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			if(count == 19){
				end = true;
			}
			break;
		case BLUE_WIN:
			icon = new ImageIcon(getClass().getResource("image/effect/winner.gif"));
			effectImage = icon.getImage();
			count++;
			g.drawImage(effectImage,
				640-count*50, 295, 640-count*50+350,345,
				0,50,350,100,
				null);
			if(count == 10){
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			if(count == 19){
				end = true;
			}
			break;
		default:
			end = true;
			break;
		}
	}
	
}