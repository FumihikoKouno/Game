import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

class MainPanel extends JPanel implements Common{
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	private Image dbImage;
	private Graphics dbg;
	
	private Key key;
	private Field field;
	private Title title;
	
	public MainPanel() {
		title = new Title();
		field = new Field();
		key = new Key(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		new ScreenUpdate(30).start();
	}
	public void keyPressed(int key){
		switch(Flag.status){
		case STATUS_TITLE:
			title.keyPressed(key);
			field.init();
			break;
		case STATUS_GAME:
			field.keyPressed(key);
			break;
		}
	}
	
	private class ScreenUpdate extends Thread{
		int interval;
		public ScreenUpdate(int interval){
			this.interval = interval;
		}
		public void run(){
			while(true){
				if(Flag.gameover){
					/*
					field = new Field();
					Flag.status = STATUS_TITLE;
					Flag.gameover = false;
					*/
				}
				try{
					Thread.sleep(interval);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				update();
			}
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
		dbg.setColor(Color.BLACK);
		dbg.fillRect(0,0,WIDTH,HEIGHT);
		switch(Flag.status){
		case STATUS_TITLE:
			title.draw(dbg);
			break;
		case STATUS_GAME:
			field.draw(dbg);
			break;
		}
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
