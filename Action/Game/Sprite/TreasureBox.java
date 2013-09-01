/**
 * �Ŕ̃N���X
 */

package Game.Sprite;

import Game.Sprite.Player;
import Game.Common.Data;
import Game.Common.StateData;
import Game.Common.KeyStatus;
import Game.MessageWindow;

import java.awt.Graphics;
import java.awt.Image;

public class TreasureBox extends Sprite{
	
	private String[] message;
	private boolean touching;
	private int kind;
	
	// �󔠂̒��g�p�̋�ʗp�萔( kind�ɓ���� )
	private static final int SWORD = 0;
	private static final int ARROW = 1;
	private static final int FIRE = 2;
	private static final int WATER = 3;
	private static final int THUNDER = 4;
	
	/**
	 * �R���X�g���N�^
	 * ���W�����߂Ă���
	 */
	public TreasureBox(int x, int y, int kind){
		this.kind = kind;
		IMAGE_X = 160;
		IMAGE_Y = 0;
		width = 32;
		height = 32;
		this.x = x;
		this.y = y;
		init();
	}
	
	private void init(){
		switch(kind){
		case SWORD:
			message = new String[4];
			message[0] = "�\�[�h����ɓ��ꂽ�I";
			message[1] = "";
			message[2] = "���j���[���J����EQUIPMENT����I�����đ����ł��܂�";
			message[3] = "����������G���^�[���������ƂōU�����ł��܂�";
			break;
		case ARROW:
			message = new String[4];
			message[0] = "�A���[����ɓ��ꂽ�I";
			message[1] = "";
			message[2] = "���j���[���J����EQUIPMENT����I�����đ����ł��܂�";
			message[3] = "����������G���^�[���������ƂōU�����ł��܂�";
			break;
		case FIRE:
			break;
		case WATER:
			break;
		case THUNDER:
			break;
		default:
			break;
		}
	}

	// �X�v���C�g��update
	public void update(){}
	
	// �v���C���[���X�v���C�g�ɐG�ꂽ�Ƃ��̊֐�
	public void touch(Sprite s, int dir, int[] dest){
		if((dir & (1 << HIT_DIRECT)) > 0){
			if(KeyStatus.up){
				Data.mw = new MessageWindow(message,MessageWindow.CENTER);
				switch(kind){
				case SWORD:
					StateData.flag.gotWeapon |= 1;
					break;
				case ARROW:
					StateData.flag.gotWeapon |= 2;
					break;
				case FIRE:
					break;
				case WATER:
					break;
				case THUNDER:
					break;
				default:
					break;
				}
			}
		}
	}
	
	// �`�揈��
	/*
	public void draw(Graphics g, int screenX, int screenY){
		g.drawImage(Data.image.signboardImage,
			x-screenX, y-screenY, x-screenX+width, y-screenY+height,
			0, 0, width, height,
			null
		);
	}
	*/
}