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
	private final int messageX = 5;
	private final int messageY = 300;
	private final int messageCOL = 15;
	private final int messageROW = 2;
    public Title(){
    	cursor = new Cursor(0,0);
    }
	public void init(){
		cursor.setLoopAble(true);
		endless = new MessageWindow("���Ԑ����Ȃ��ŃQ�[���I�[�o�[�ɂȂ�܂ő������[�h�ł��B",messageX,messageY,messageCOL,messageROW);
		scoreAttack = new MessageWindow("2���Ԃłł��邾���X�R�A���҂����[�h�ł��B",messageX,messageY,messageCOL,messageROW);
		stageClear = new MessageWindow("���ׂẴp�l������胉�C���������ƃN���A�ɂȂ郂�[�h�ł��B",messageX,messageY,messageCOL,messageROW);
		demo = new MessageWindow("�f���ł��B��{�I�ȑ�����������܂��B",messageX,messageY,messageCOL,messageROW);
		ranking = new MessageWindow("�����L���O��\�����܂��B",messageX,messageY,messageCOL,messageROW);
		config = new MessageWindow("�ݒ��ύX���܂�",messageX,messageY,messageCOL,messageROW);
		exit = new MessageWindow("�v���O�������I�����܂��B",messageX,messageY,messageCOL,messageROW);
		cursor.set(0,0);
		Data.cursorMaxX = 0;
		Data.cursorMaxY = Data.EXIT-1;
	}
	
	private void enter(){
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
		case Data.CONFIG:
			mw = config;
			break;
		case Data.EXIT:
			mw = exit;
			break;
		}
	}
	
	public void update(){
		cursor.move();
		messageUpdate();
		if(KeyStatus.enter) enter();
    }
    
    public void draw(Graphics g){
	    g.drawImage(Data.image.titleImage,0,0,Data.WIDTH*Data.zoom,Data.HEIGHT*Data.zoom,
	    	0,0,Data.WIDTH,Data.HEIGHT,
	    	null);
    	cursor.draw(g,Data.TITLE);
    	if(mw != null) mw.draw(g);
    }
}
