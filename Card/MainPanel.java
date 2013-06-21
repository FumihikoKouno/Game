import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

class MainPanel extends JPanel implements Runnable{
	// パネルサイズ
	private static final int WIDTH = 640;
	private static final int HEIGHT = 640;
	
	private Graphics dbg;
	private Image dbImage;

	private Cursor cursor;
	private Key key;
	private Field field;
	private Title title;
	private Config config;
	
	public MainPanel() {
		// パネルの推奨サイズを設定
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		Flag.keyInputable = true;
		Flag.title = true;
		
		title = new Title();
		config = new Config();
		field = new Field();
		cursor = new Cursor(title,config,field);
		key = new Key(cursor,this);
		new Thread(this).start();
	}
	
	public void run(){
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
		if(Flag.gameOver){
			Flag.gameSet = false;
			Flag.gameOver = false;
			Flag.game = false;
			Flag.title = true;
			Flag.initialize = false;
			Flag.keyInputable = true;
		}
		while(dbImage == null){
			dbImage = createImage(WIDTH,HEIGHT);
			if(dbImage == null){
				System.out.println("dbImage is null");
				return;
			}else{
				dbg = dbImage.getGraphics();
			}
		}
		if(Flag.title){
			if(!Flag.initialize){
				title.init();
				Flag.initialize = true;
			}
			title.draw(dbg);
		}
		if(Flag.config){
			if(!Flag.initialize){
				config.init();
				Flag.initialize = true;
			}
			config.draw(dbg);
		}
		if(Flag.game){
			if(!Flag.initialize){
				field.init();
				Flag.initialize = true;
			}
			field.draw(dbg);
			cursor.draw(dbg);
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
