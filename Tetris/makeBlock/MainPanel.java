import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

class MainPanel extends JPanel implements Runnable{
	// パネルサイズ
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	
	private Graphics dbg;
	private Image dbImage;
	
	private Field field;
	private Mouse mouse;
	
	public MainPanel() {
		// パネルの推奨サイズを設定
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		field = new Field();
		mouse = new Mouse(field,this);
		
		new Thread(this).start();
	}
	
	public void run(){
		while(true){
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			update();
		}
	}
	
	public void update() {
		while(dbImage == null){
			dbImage = createImage(WIDTH,HEIGHT);
			if(dbImage == null){
				System.out.println("dbImage is null");
				return;
			}else{
				dbg = dbImage.getGraphics();
			}
		}
		dbg.setColor(Color.WHITE);
		dbg.fillRect(0,0,WIDTH,HEIGHT);
		field.draw(dbg);
		paintScreen();
	}
	
	public void paintScreen(){
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
