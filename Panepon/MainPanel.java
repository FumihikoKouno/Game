import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

class MainPanel extends JPanel{
	
	private Image dbImage;
	private Graphics dbg;

	private Title title;
	private Ranking ranking;
	private Key key;
	private Field field;
	private Mouse mouse;
	private MouseMotion mouseMotion;
	
	private int prevGameStatus;
	
	public MainPanel(){
		prevGameStatus = Data.TITLE;
		title = new Title();
		field = new Field();
		ranking = new Ranking();
		title.init();
		key = new Key();
		mouse = new Mouse();
		mouseMotion = new MouseMotion();
		this.setFocusable(true);
		this.addKeyListener(key);
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouseMotion);
		setPreferredSize(new Dimension(Data.WIDTH, Data.HEIGHT));
	}
	
	public void start(){
		while(dbImage == null){
			dbImage = createImage(Data.WIDTH,Data.HEIGHT);
			if(dbImage != null){
				dbg = dbImage.getGraphics();
			}
		}
		long time;
		Data.frame = 0;
		int mspf = 1000 / Data.fps;
		while(true){
			time = System.currentTimeMillis();
			update();
			time = System.currentTimeMillis() - time;
			if(time > mspf) Data.debugPrint("slowdown");
			try{
				if(time < mspf) Thread.sleep(mspf-time);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			Data.frame++;
		}
	}

	public void update() {
		dbg.setColor(Color.BLACK);
		dbg.fillRect(0,0,Data.WIDTH,Data.HEIGHT);
		switch(Data.gameStatus){
		case Data.TITLE:
			if(prevGameStatus != Data.gameStatus){
				title.init();
				prevGameStatus = Data.gameStatus;
				KeyStatus.setAll(false);
				Data.mousePressed = false;
			}
			title.update();
			title.draw(dbg);
			break;
		case Data.ENDLESS:
			if(prevGameStatus != Data.gameStatus){
				field = new Endless();
				field.init();
				prevGameStatus = Data.gameStatus;
				KeyStatus.setAll(false);
				Data.mousePressed = false;
			}
		case Data.SCORE_ATTACK:
			if(prevGameStatus != Data.gameStatus){
				field = new ScoreAttack();
				field.init();
				prevGameStatus = Data.gameStatus;
				KeyStatus.setAll(false);
				Data.mousePressed = false;
			}
		case Data.STAGE_CLEAR:
			if(prevGameStatus != Data.gameStatus){
				field = new StageClear();
				field.init();
				prevGameStatus = Data.gameStatus;
				KeyStatus.setAll(false);
				Data.mousePressed = false;
			}
		case Data.DEMO:
			if(prevGameStatus != Data.gameStatus){
				field = new Demo();
				field.init();
				prevGameStatus = Data.gameStatus;
				KeyStatus.setAll(false);
				Data.mousePressed = false;
			}
			field.update();
			field.draw(dbg);
			break;
		case Data.RANKING:
			if(prevGameStatus != Data.gameStatus){
				ranking.init();
				prevGameStatus = Data.gameStatus;
				KeyStatus.setAll(false);
				Data.mousePressed = false;
			}
			ranking.update();
			ranking.draw(dbg);
			break;
		case Data.EXIT:
			System.exit(0);
			break;
		}
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
