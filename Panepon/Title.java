/**
 * �^�C�g���N���X
 */
import java.awt.Graphics;

class Title{
	// �J�[�\��
	private Cursor cursor;
	// �e���[�h�̃��b�Z�[�W
	private MessageWindow mw;
	private MessageWindow endless;
	private MessageWindow scoreAttack;
	private MessageWindow stageClear;
	private MessageWindow demo;
	private MessageWindow ranking;
	private MessageWindow exit;
	private MessageWindow config;
	// �n�[�h���[�h�p�ϐ�
	private boolean hardReleased;
	private int hardCount;
	// ���b�Z�[�W�̕\���ʒu
	private final int messageX = 5;
	private final int messageY = 300;
	private final int messageCOL = 15;
	private final int messageROW = 2;
	public Title(){
		cursor = new Cursor(0,0);
	}
	// ������
	public void init(){
		// �J�[�\�������[�v����悤��
		cursor.setLoopAble(true);
		// �e���b�Z�[�W�̃C���X�^���X����
		endless = new MessageWindow("���Ԑ����Ȃ��ŃQ�[���I�[�o�[�ɂȂ�܂ő������[�h�ł��B",messageX,messageY,messageCOL,messageROW);
		scoreAttack = new MessageWindow("2���Ԃłł��邾���X�R�A���҂����[�h�ł��B",messageX,messageY,messageCOL,messageROW);
		stageClear = new MessageWindow("���ׂẴp�l������胉�C���������ƃN���A�ɂȂ郂�[�h�ł��B",messageX,messageY,messageCOL,messageROW);
		demo = new MessageWindow("�f���ł��B��{�I�ȑ�����������܂��B",messageX,messageY,messageCOL,messageROW);
		ranking = new MessageWindow("�����L���O��\�����܂��B",messageX,messageY,messageCOL,messageROW);
		config = new MessageWindow("�ݒ��ύX���܂�",messageX,messageY,messageCOL,messageROW);
		exit = new MessageWindow("�v���O�������I�����܂��B",messageX,messageY,messageCOL,messageROW);
		// �n�[�h���[�h�p�J�E���g������
		hardCount = 0;
		hardReleased = true;
		// �J�[�\���ő�l��ݒ�
		Data.cursorMaxX = 0;
		Data.cursorMaxY = Data.EXIT-1;
	}
	// �J�[�\���ʒu���Z�b�g
	public void cursorReset(){
		cursor.set(0,0);
	}
	
	// �G���^�[�������ꂽ�Ƃ��̏���
	private void enter(){
		if(cursor.getY()+1 == Data.DEMO && Data.hard == 2) return;
		Data.gameStatus = cursor.getY()+1;
	}

	// �\�����郁�b�Z�[�W�̎�ނ�ݒ肷��
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
		case Data.EXIT:
			mw = exit;
			break;
		}
	}
	
	// update�֐�
	public void update(){
		// hard�̃L�[��������Ă�����t���O�𗧂Ă�
		if(!KeyStatus.hard) hardReleased = true;
		// �O�t���[����hard�̃L�[��������Ă��āA���̃t���[����hard�̃L�[��������Ă����
		else if(hardReleased && KeyStatus.hard){
			// �t���O���Z�b�g�Ɖ������������Z
			hardReleased = false;
			hardCount++;
		}
		// hard��5�񉟂��ꂽ��n�[�h���[�h��
		if(hardCount == 5){
			Data.hard = 3 - Data.hard;
			hardCount = 0;
		}
		// �J�[�\���ړ�
		cursor.move();
		// ���b�Z�[�Wupdate
		messageUpdate();
		// �G���^�[����
		if(KeyStatus.enter) enter();
	}
	
	// �`��
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
