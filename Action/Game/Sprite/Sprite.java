/**
 * ���ׂẴC�x���g�̊�ՃN���X
 * �G�A�d�|��(�X�C�b�`�Ƃ��H)�A���Ƃ�
 * �v���C���[�̍s���ɂ���ĉ����ω�����������̂�
 * ���̃N���X���p�����č��
 */

package Game.Sprite;

import Game.Sprite.Player;
import Game.MapData.MapData;
import Game.Common.Data;
import Game.Sprite.Weapon.Weapon;

import java.awt.Graphics;
import java.awt.Image;

public class Sprite{
	/**
	 * static�ȕϐ�
	 */
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static int nextSpriteID = 0;
	/**
	 * �����o�ϐ�
	 */
	// true�ɂ���ƃX�v���C�g�����ł�����ϐ�
	public boolean end = false;
	/**
	 * �X�v���C�g�̈ʒu
	 * �X�v���C�g�̈�ԍ���̕����̍��W���w��
	 */
	protected int x,y;
	// �X�v���C�g�̑��x
	protected int vx, vy;
	// �X�v���C�g�̌���
	protected int direction;
	// �X�v���C�g�̑傫��
	protected int width, height;
	// �X�v���C�g��ID
	protected int spriteID;
	// �X�v���C�g�̃A�j���[�V�����p�ϐ�
	protected int animationStatus;
	protected int animationFrame;
	// �X�v���C�g�̃C���[�W
	protected Image image;
	/**
	 * �������烁�\�b�h
	 */
	/**
	 * �R���X�g���N�^
	 * �e�X�v���C�g�ɌŗL��ID��U�蕪���āA
	 * �����ƃA�j���[�V�����p�ϐ���������
	 */
	public Sprite(){
		spriteID = nextSpriteID;
		nextSpriteID++;
		animationStatus = 0;
		animationFrame = Data.frame;
		direction = 0;
	}
	/**
	 * �R���X�g���N�^
	 * ���W�����߂Ă���
	 */
	public Sprite(int x, int y){
		this();
		this.x = x;
		this.y = y;
	}

    public int getX(){ return x; }
    public int getY(){ return y; }
    public int getVx(){ return vx; }
    public int getVy(){ return vy; }
    public int getWidth(){ return width; }
    public int getHeight(){ return height; }
    public int getDirection(){ return direction; }
    public int getSpriteID(){ return spriteID; }

	public void setVx(int vx){
		setV(vx,this.vy);
	}
	public void setVy(int vy){
		setV(this.vx,vy);
	}
	
    public void setV(int vx, int vy){
	this.vx = vx;
	this.vy = vy;
    }

	// �X�v���C�g��update
	public void update(MapData mapData){}
	/**
	 * �X�v���C�g�̃A�j���[�V������update
	 * f �t���[�����Ƃɉ摜���؂�ւ��
	 */
	public void animationUpdate(int f){
		if(Data.frame - animationFrame >= f){
			animationStatus = 1 - animationStatus;
			animationFrame = Data.frame;
		}
	}
	// �X�v���C�g�̈ړ�����
	public void move(){
		x += vx;
		y += vy;
	}
	// �X�v���C�g���}�b�v�ɓ��������ꍇ�̏���
	// �����͂ǂ̕����ɓ����������Ƃ��̐��O�̈ʒu
	public void mapHit(int dir, int dest){}
	// �v���C���[���X�v���C�g�ɐG�ꂽ�Ƃ��̊֐�
	public void touch(Sprite s, int dir, int[] dest){}
	// �v���C���[���X�v���C�g�ɂ����Ȃ����Ƃ��̊֐�
	public void over(){}
	// ���̃X�v���C�g�ɑ΂���sprite���U�������Ƃ��̊֐�
	public void attacked(Sprite sprite){}
	// �`�揈��
	public void draw(Graphics g, int screenX, int screenY){
		int ix = animationStatus * width;
		int iy = direction * height;
		g.drawImage(image,
			x-screenX, y-screenY, x-screenX+width, y-screenY+height,
			ix, iy, ix+width, iy+height,
			null
		);
	}
}