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
		// mapID�Ǝ�l���̏����ʒu(x,y)
		map = new Map(0,0,28*Data.CHIP_SIZE);
		menu = new Menu();
		title = new Title();
		/* key�C�x���g�̍쐬 */
		key = new Key();
		this.setFocusable(true);
		this.addKeyListener(key);
		/** 
		 * mouse�C�x���g�̍쐬
		 * �c���񑽕��}�E�X�g��Ȃ����?
		 */
		mouse = new Mouse();
		this.addMouseListener(mouse);
		
		setPreferredSize(new Dimension(Data.WIDTH, Data.HEIGHT));
	}
	
	/**
	 * �Q�[�����[�v
	 */
    public void run(){
	while(dbImage == null){
	    dbImage = createImage(Data.WIDTH,Data.HEIGHT);
	    if(dbImage != null){
		dbg = dbImage.getGraphics();
	    }
	}
	long time;
	// �Q�[���J�n���Ƀt���[���������Z�b�g
	Data.frame = 0;
	// MilliSecondPerFrame��1�t���[�������艽�~���b���̕ϐ�
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
	 * �v���O�����S�̂�update
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
			 * �����Ƀ^�C�g����ʂ̏���
			 * �c�Ƃ������^�C�g���p�̃N���X������Ă������Ǝv��
			 */
			title.update();
			title.draw(dbg);
			break;
		case Data.MENU:
			/**
			 * �����Ƀ��j���[�̏���
			 * �^�C�g�����l�N���X����Ă������Ǝv��
			 */
			menu.update();
			menu.draw(dbg);
			break;
		case Data.PLAYING:
			// �v���C��
			// �e�N���X��update + draw
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
