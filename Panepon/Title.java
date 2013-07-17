import java.awt.Graphics;

class Title{
	private Cursor cursor;
	private MessageWindow mw;
	private MessageWindow endless;
	private MessageWindow scoreAttack;
	private MessageWindow stageClear;
	private MessageWindow demo;
	private MessageWindow ranking;
	private MessageWindow exit;
	private MessageWindow config;
    private boolean hardReleased;
    private int hardCount;
	private final int messageX = 5;
	private final int messageY = 300;
	private final int messageCOL = 15;
	private final int messageROW = 2;
    public Title(){
    	cursor = new Cursor(0,0);
    }
	public void init(){
		cursor.setLoopAble(true);
		endless = new MessageWindow("時間制限なしでゲームオーバーになるまで続くモードです。",messageX,messageY,messageCOL,messageROW);
		scoreAttack = new MessageWindow("2分間でできるだけスコアを稼ぐモードです。",messageX,messageY,messageCOL,messageROW);
		stageClear = new MessageWindow("すべてのパネルが一定ラインを下回るとクリアになるモードです。",messageX,messageY,messageCOL,messageROW);
		demo = new MessageWindow("デモです。基本的な操作説明をします。",messageX,messageY,messageCOL,messageROW);
		ranking = new MessageWindow("ランキングを表示します。",messageX,messageY,messageCOL,messageROW);
		config = new MessageWindow("設定を変更します",messageX,messageY,messageCOL,messageROW);
		exit = new MessageWindow("プログラムを終了します。",messageX,messageY,messageCOL,messageROW);
		hardCount = 0;
		hardReleased = true;
		Data.cursorMaxX = 0;
		Data.cursorMaxY = Data.EXIT-1;
	}
	
	public void cursorReset(){
		cursor.set(0,0);
	}
	
	private void enter(){
	    if(cursor.getY()+1 == Data.DEMO && Data.hard == 2) return;
		Data.gameStatus = cursor.getY()+1;
	}

	private void messageUpdate(){
		switch(cursor.getY()+1){
		case Data.ENDLESS:
			mw = endless;
			break;
		case Data.SCORE_ATTACK:
			mw = scoreAttack;
			break;
		case Data.STAGE_CLEAR:
			mw = stageClear;
			break;
		case Data.DEMO:
			mw = demo;
			break;
		case Data.RANKING:
			mw = ranking;
			break;
			/*
		case Data.CONFIG:
			mw = config;
			break;
			*/
		case Data.EXIT:
			mw = exit;
			break;
		}
	}
	
	public void update(){
	    if(!KeyStatus.hard) hardReleased = true;
	    else if(hardReleased && KeyStatus.hard){
		hardReleased = false;
		hardCount++;
	    }
	    if(hardCount == 5){
		Data.hard = 3 - Data.hard;
		hardCount = 0;
	    }
		cursor.move();
		messageUpdate();
		if(KeyStatus.enter) enter();
	}
	public void draw(Graphics g){
	    if(Data.hard == 1){
		g.drawImage(Data.image.titleImage,0,0,Data.WIDTH*Data.zoom,Data.HEIGHT*Data.zoom,
		0,0,Data.WIDTH,Data.HEIGHT,
		null);
	    }else if(Data.hard == 2){
		g.drawImage(Data.image.hardTitleImage,0,0,Data.WIDTH*Data.zoom,Data.HEIGHT*Data.zoom,
		0,0,Data.WIDTH,Data.HEIGHT,
		null);
	    }
		cursor.draw(g,Data.TITLE);
		if(mw != null) mw.draw(g);
	}
}
