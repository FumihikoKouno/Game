/**
 * �J�[�\���֌W�̃N���X
 * �Q�[���v���C��ʂł̓p�l���ړ��̃J�[�\��
 * �^�C�g���⃉���L���O�ł͍��ڑI���̃J�[�\��
 */

import java.awt.Graphics;

class Cursor{
	// �J�[�\����x, y���W
	private int x, y;
	
	/**
	 * �O�ɃJ�[�\�����������Ƃ�����̃t���[����
	 * 0���ƑO�̃t���[���ɂ̓J�[�\���𓮂����Ă��Ȃ�
	 * �ړ��L�[���������ςȂ��ɂ����Ƃ��ɂ����Ȃ�啝�ɓ����Ȃ��悤�ɂ��邽��
	 */
	private int beforeMove;
	
	/**
	 * true�Ȃ�J�[�\�����ŏ�̂Ƃ��ɏ����������ŉ���
	 * �J�[�\�����ŉ��̂Ƃ��ɉ����������Ƃ��ɍŏ�ɂ���
	 */
	private boolean loop;
	
	// �R���X�g���N�^ ����x, ����y ������
	public Cursor(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	// x���W�𓾂�
	public int getX(){ return x; }
	// y���W�𓾂�
	public int getY(){ return y; }
	// �J�[�\�������Ɉړ�����(y���W��-1����)
	public void moveUp(){ y = Math.max(y-1,0); }
	// ������x,y���W�ɃJ�[�\�����ڂ�
	public void set(int x, int y){
		if(x > Data.cursorMaxX) this.x = Data.cursorMaxX;
		else if(x < 0) this.x = 0;
		else this.x = x;
		if(y > Data.cursorMaxY) this.y = Data.cursorMaxY;
		else if(y < 0) this.y = 0;
		else this.y = y;
	}
	// x���W��ݒ肷��
	public void setX(int x){
		if(x > Data.cursorMaxX) this.x = Data.cursorMaxX;
		else if(x < 0) this.x = 0;
		else this.x = x;
	}
	// y���W��ݒ肷��
	public void setY(int y){
		if(y > Data.cursorMaxY) this.y = Data.cursorMaxY;
		else if(y < 0) this.y = 0;
		else this.y = y;
	}
	// �J�[�\�������[�v���邩�ǂ����̃t���O���Z�b�g
	public void setLoopAble(boolean b){ loop = b; }
	
	// ���͂���Ă���L�[�ɉ����ăJ�[�\���𓮂����֐�
	public void move(){
		boolean moved = false;
		moved = KeyStatus.up || KeyStatus.down || KeyStatus.left || KeyStatus.right;
		// ���̃t���[���ŃL�[��������Ă���Γ����Ă��Ȃ����Ƃ�\�킷0�Ƃ���
		if(!moved){
			beforeMove = 0;
			return;
		}
		// �O����J�[�\���𓮂����Ă���ړ��L�[���������ςȂ��ɂ��Ă���ꍇ5�t���[�������Ȃ��Ǝ��������Ȃ�
		if(beforeMove>0 && beforeMove <5){
			beforeMove++;
			return;
		}
		if(KeyStatus.up){
			if(loop){
				if(y == 0) y = Data.cursorMaxY;
				else y = y - 1;
			}else{
				y = Math.max(y-1,0);
			}
		}
		if(KeyStatus.down){
			if(loop){
				if(y == Data.cursorMaxY) y = 0;
				else y = y + 1;
			}else{
				y = Math.min(y+1,Data.cursorMaxY);
			}
		}
		if(KeyStatus.left){
			if(loop){
				if(x == 0) x = Data.cursorMaxX;
				else x = x - 1;
			}else{
				x = Math.max(x-1,0);
			}
		}
		if(KeyStatus.right){
			if(loop){
				if(x == Data.cursorMaxX) x = 0;
				else x = x + 1;
			}else{
				x = Math.min(x+1,Data.cursorMaxX);
			}
		}
		if(beforeMove == 0 && moved) beforeMove = 1;
	}

	// �`��֐� status�̓Q�[���̏�Ԃ�\�킷
	public void draw(Graphics g, int status){
		int drawX = 0;
		int drawY = 0;
		int width = 0;
		int height = 0;
		int imageX = 0;
		int imageY = 0;
		switch(status){
		case Data.TITLE:
			drawX = (Data.TITLE_CURSOR_X + 5 * ((Data.frame%30<15)?0:1))*Data.zoom;
			drawY = (Data.TITLE_CURSOR_Y + Data.TITLE_DIFFERENCE * y)*Data.zoom;
			width = Data.PANEL_SIZE;
			height = Data.PANEL_SIZE;
			imageX = 0;
			imageY = Data.PANEL_SIZE;
			break;
		case Data.RANKING:
			drawX = (Data.RANKING_CURSOR_X + 5 * ((Data.frame%30<15)?0:1))*Data.zoom;
			drawY = (Data.RANKING_CURSOR_Y + Data.RANKING_DIFFERENCE * y)*Data.zoom;
			width = Data.PANEL_SIZE;
			height = Data.PANEL_SIZE;
			imageX = 0;
			imageY = Data.PANEL_SIZE;
			break;
		case Data.ENDLESS:
		case Data.SCORE_ATTACK:
		case Data.STAGE_CLEAR:
		case Data.DEMO:
			drawX = (Data.FIELD_START_X + x * Data.PANEL_SIZE)*Data.zoom;
			drawY = (-Data.scrollOffset + Data.FIELD_START_Y + y * Data.PANEL_SIZE)*Data.zoom;
			width = (Data.PANEL_SIZE << 1);
			height = Data.PANEL_SIZE;
			imageX = 0;
			imageY = 0;
			break;
		default:
			break;
		}
		g.drawImage(Data.image.cursorImage,drawX,drawY,drawX+width*Data.zoom,drawY+height*Data.zoom,
			imageX,imageY,imageX+width,imageY+height,
			null);
	}
	
}
