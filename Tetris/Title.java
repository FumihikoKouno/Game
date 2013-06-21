import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

class Title implements Common{
	public void keyPressed(int key){
		Flag.status = STATUS_GAME;
	}
	public void draw(Graphics g){
		g.drawImage(image.titleImage,0,0,null);
	}
	
}