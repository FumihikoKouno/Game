package Game;

import Game.Common.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

class MainPanel extends JPanel{
	
	private Image dbImage;
	private Graphics dbg;

	private Key key;
	private Mouse mouse;
	private Map map;
	private Title title;
	private Menu menu;
	
	public MainPanel(){
		// mapIDと主人公の初期位置(x,y)
		map = new Map(0,0,28*Data.CHIP_SIZE);
		menu = new Menu();
		title = new Title();
		/* keyイベントの作成 */
		key = new Key();
		this.setFocusable(true);
		this.addKeyListener(key);
		/** 
		 * mouseイベントの作成
		 * …今回多分マウス使わないよね?
		 */
		mouse = new Mouse();
		this.addMouseListener(mouse);
		
		setPreferredSize(new Dimension(Data.WIDTH, Data.HEIGHT));
	}
	
	/**
	 * ゲームループ
	 */
    public void run(){
	while(dbImage == null){
	    dbImage = createImage(Data.WIDTH,Data.HEIGHT);
	    if(dbImage != null){
		dbg = dbImage.getGraphics();
	    }
	}
	long time;
	// ゲーム開始時にフレーム数をリセット
	Data.frame = 0;
	// MilliSecondPerFrameで1フレームあたり何ミリ秒かの変数
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
	/**
	 * プログラム全体のupdate
	 */
	public void update() {
		/*
		if(dbImage == null){
			dbImage = createImage(WIDTH,HEIGHT);
			if(dbImage == null){
				System.out.println("dbImage is null 2");
				return;
			}else{
				dbg = dbImage.getGraphics();
			}
		}
		*/
		dbg.setColor(Color.WHITE);
		dbg.fillRect(0,0,WIDTH,HEIGHT);
		switch(Data.gameStatus){
		case Data.TITLE:
			/**
			 * ここにタイトル画面の処理
			 * …というかタイトル用のクラスを作ってもいいと思う
			 */
			title.update();
			title.draw(dbg);
			break;
		case Data.MENU:
			/**
			 * ここにメニューの処理
			 * タイトル同様クラス作ってもいいと思う
			 */
			menu.update();
			menu.draw(dbg);
			break;
		case Data.PLAYING:
			// プレイ中
			// 各クラスのupdate + draw
			map.update();
			map.draw(dbg);
			break;
		}
		
		
		dbg.drawString("frame : " + Data.frame, Data.WIDTH - 80, 15);
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
