import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

class MainPanel extends JPanel{
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	private Image dbImage;
	private Graphics dbg;
	
	private int[] test = new int[3];
	
	private int frame;
	
	public MainPanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	public void start(){
		test[0] = 255;
		test[1] = 0;
		test[2] = 0;
		frame = 0;
		while(true){
			try{
				Thread.sleep(30);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			update();
		}
	}
	
	public void update() {
		if(dbImage == null){
			dbImage = createImage(WIDTH,HEIGHT);
			if(dbImage == null){
				System.out.println("dbImage is null");
				return;
			}else{
				dbg = dbImage.getGraphics();
			}
		}
		
		// ê¬Å`ê‘
		frame++;
		if((frame & 0x300) == 0x300){
			test[0] = 255;
			test[1] = 255-(frame & 0xff);
			test[2] = 0;
		}else if((frame & 0x300) == 0x200){
			test[0] = (frame & 0xff);
			test[1] = 255;
			test[2] = 0;
		}else if((frame & 0x300) == 0x100){
			test[0] = 0;
			test[1] = 255;
			test[2] = 255-(frame & 0xff);
		}else{
			test[0] = 0;
			test[1] = (frame & 0xff);
			test[2] = 255;
		}
		dbg.setColor(new Color(test[0],test[1],test[2]));
		dbg.fillRect(0,0,WIDTH,HEIGHT);
		draw();
	}
	
	public void draw(){
		try{
			Graphics g = getGraphics();
			if((g != null) && (dbImage != null)){
				g.drawImage(dbImage,0,0,null);
			}
			Toolkit.getDefaultToolkit().sync();
			if(g != null){
				g.dispose();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
