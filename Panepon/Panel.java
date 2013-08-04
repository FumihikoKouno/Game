/**
 * �p�l�����̃N���X
 */
import java.awt.Graphics;

class Panel{
	// �ړ��̃I�t�Z�b�g
	private int offset_x, offset_y;
	// �p�l���̎��
	private int kind;
	// �p�l���̕\����������Ƃ��̃t���[����
	private int delete_limit;
	// ������t���O
	private boolean falling;
	// �p�l�������S�ɏ�������t���[��
	private int end_frame;
	// �_�ŃA�j���[�V�����I���t���[��
	private int d_animation_time;
	// �A���p�ϐ�
	private int connected;
	
	public Panel(int k){
		connected = 0;
		kind = k;
		delete_limit = 0;
		d_animation_time = 0;
		offset_x = 0;
		offset_y = 0;
		end_frame = 0;
		falling = false;
	}
	
	// update�֐��B�I�t�Z�b�g�ɉ����ăp�l���𓮂���
	public void update(){
		if(offset_x < 0){
			offset_x = Math.min(offset_x+Data.MPF,0);
		}
		if(offset_x > 0){
			offset_x = Math.max(offset_x-Data.MPF,0);
		}
		if(offset_y < 0){
			offset_y = Math.min(offset_y+Data.GRAVITY*Data.hard,0);
		}
		if(offset_y > 0){
			offset_y = Math.max(offset_y-Data.GRAVITY*Data.hard,0);
		}
	}
	
	public void setConnected(int i){ connected = i; }
	public int getConnected(){ return connected; }
	public int getDeleteLimit(){ return delete_limit; }
	// �^����ꂽ�����ɉ����Ċe�I���t���[�����Z�b�g
	// count�͉��Ԗڂɏ����邩��max�͉�����������������
	public void setDeleteFrame(int count, int max){
		d_animation_time = Data.frame+Data.DELETE_TIME/Data.hard;
		delete_limit = d_animation_time+count*Data.DELETE_DIFFERENCE_TIME/Data.hard;
		end_frame = d_animation_time+max*Data.DELETE_DIFFERENCE_TIME/Data.hard; 
	}
	
	public boolean cMoving(){ return offset_x != 0; }
	public boolean rMoving(){ return offset_y != 0; }
	public void setKind(int k){ kind = k; }
	public int getKind(){ return kind; }
	public void setOffset(int x, int y){ offset_x = x; offset_y = y; }
	public void setFalling(boolean b){ falling = b; }
	public boolean isFalling(){ return falling; }
	// �����Ă���Œ��Ȃ�true
	public boolean isDeleting(){ return delete_limit>0; }
	// �\����������t���[���ɒB������true
	public boolean isDeleted(){ return delete_limit != 0 && delete_limit <= Data.frame; }
	// �p�l�������S�ɏ�����t���[���ɒB������true
	public boolean end(){ return end_frame != 0 && end_frame < Data.frame; }

	// �`��
	public void draw(Graphics g, int x, int y){
		int drawX = (Data.FIELD_START_X + x * Data.PANEL_SIZE + offset_x)*Data.zoom;
		int drawY = (-Data.scrollOffset + Data.FIELD_START_Y + y * Data.PANEL_SIZE + offset_y)*Data.zoom;
		int imageX = kind*Data.PANEL_SIZE;
		int imageY = 0;
		// �܂��o�����Ă��Ȃ���ԉ��̂��炢�p�l��
		if(y == Data.ROW){
			g.drawImage(Data.image.nextPanelImage,
				drawX, drawY, drawX+Data.PANEL_SIZE*Data.zoom, drawY+Data.PANEL_SIZE*Data.zoom,
				imageX, imageY, imageX + Data.PANEL_SIZE, imageY + Data.PANEL_SIZE,
				null
				);
		}else{
			// �p�l���̕\��
			if(kind >= 0){
				// �����Ă���Ƃ��Ɍ��镔���̕\��
				if((isDeleting() && d_animation_time-1 < Data.frame) || (isDeleting() && (d_animation_time > Data.frame) && ((Data.frame%(Data.DELETE_TIME/3))>=(Data.DELETE_TIME/6)))){
					g.drawImage(Data.image.deletingPanelImage,
						drawX, drawY, drawX+Data.PANEL_SIZE*Data.zoom, drawY+Data.PANEL_SIZE*Data.zoom,
						imageX, imageY, imageX + Data.PANEL_SIZE, imageY + Data.PANEL_SIZE,
						null
						);
				}else{
					// �ʏ�̕\��
					g.drawImage(Data.image.panelImage,
						drawX, drawY, drawX+Data.PANEL_SIZE*Data.zoom, drawY+Data.PANEL_SIZE*Data.zoom,
						imageX, imageY, imageX + Data.PANEL_SIZE, imageY + Data.PANEL_SIZE,
						null
						);
				}
			}
		}
	}
}
