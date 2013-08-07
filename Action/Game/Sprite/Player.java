/**
 * �v���C���[��\�킷�N���X
 */

package Game.Sprite;

import Game.Common.*;
import Game.Sprite.Weapon.*;
import Game.Body.*;

import java.awt.Graphics;
import java.awt.Color;

public class Player extends Sprite{
	// ���̃t���[�����ɉ����ɒ��n������true
	private boolean landing = false;
	/**
	 * �v���C���[�̃W�����v�{�^������x�����ꂽ���ǂ���
	 * ���ꂪ�Ȃ��ƃW�����v�{�^���������ςȂ��ŉ��x���W�����v���Ă��܂�
	 */
	public boolean jumpReleased = true;
	// ��̃W�����v�������ꂽ���ǂ����̍U���{�^����
	public boolean attackReleased = true;
	// �R�C������
	public int coin;
	// �ǂ̕���𑕔����Ă��邩
	public int weaponID;
	// �ǂ̑����𑕔����Ă��邩
	public int element;
	// ����
	public Weapon weapon;
	// �̂̑���
	public int bodyID;
	
	/**
	 * �U�����󂯂��u�Ԃ̃t���[����
	 * ���t���[�����G���Ƃ��m�b�N�o�b�N�ŉ��t���[�������Ȃ��Ƃ�
	 * ���̕ӂ̔��f�ɗp����
	 */
	private int attackedFrame;
	
	// ���C�t(���͂������l��ێ����Ă邾���A�}�C�i�X�ɂȂ낤���֌W�Ȃ�)
	private int life;
	//
	private int lifeMax;
	
	/**
	 * �ړ��X�s�[�h
	 * (���͉��ɒ�`����Ă���)���x�̒l��p����
	 * ���ۂɎ�l�����ǂ̂��炢�̃X�s�[�h�œ����̂���\�킷
	 */
	private int speed;
	
	/**
	 * �ړ����x�֌W
	 * ���͂����ɒ�`���Ă��邪
	 * �����I�ɂ͑S�g�����̃N���X���Q�Ƃ���
	 */
	private int invisibleTime;
	private int nockBackTime;
	private int defence;
	private int movingSpeed;
	private int jumpSpeed;
	private int jumpMax;
	private int froat;
	
	public void equipBody(int id){
		bodyID = id;
		equipBody();
	}
	
	public void equipBody(){
		Body body = null;
		switch(bodyID){
		case 0:
			body = new Body0();
			break;
		case 1:
			body = new Body1();
			break;
		default:
			break;
		}
		froat = body.getFroat();
		invisibleTime = body.getInvisibleTime();
		nockBackTime = body.getNockBackTime();
		defence = body.getDefence();
		movingSpeed = body.getMovingSpeed();
		jumpMax = body.getJumpMax();
		jumpSpeed = body.getJumpSpeed();
	}
	
	public Player clone(){
		Player newPlayer = new Player();
		newPlayer.x = this.x;
		newPlayer.y = this.y;
		newPlayer.image = this.image;
		newPlayer.vx = this.vx;
		newPlayer.vy = this.vy;
		newPlayer.width = this.width;
		newPlayer.height = this.height;
		newPlayer.life = this.life;
		newPlayer.weaponID = this.weaponID;
		newPlayer.element = this.element;
		newPlayer.bodyID = this.bodyID;
		return newPlayer;
	}


	/**
	 * �R���X�g���N�^
	 * �Ȃ�ƂȂ������Ȃ��o�[�W�����������Ă邯��
	 * ���͎g���ĂȂ����A���������I�ɂ��g��Ȃ�
	 */
	public Player(){
		this(0,0);
	}
	/**
	 * �R���X�g���N�^
	 * ��l���̏����ʒu�������ɂƂ�
	 * �ʒu�Ɗe�ϐ��̏�����
	 */
	public Player(int x, int y){
		super(x,y);
		image = Data.image.playerImage;
		vx = 0; vy = 0;
		width = 32;
		height = 32;
		lifeMax = 10;
		life = 10;
		weaponID = 0;
		element = 0;
		equipBody(0);
	}
	public void land(){
		super.land();
		landing = true;
	}
	
	public boolean landing(){
		return landing;
	}
	
	/**
	 * �����p�֐�
	 * �c�Ƃ����Ă��d�͂Ƃ��̘b�ł͂Ȃ�
	 * �����ĊR���痎�����ꍇ�Ȃǂɕ����Ȃ����߂̊֐�
	 */
	public void fall(){
		if(jumpCount == 0) jumpCount = 1;
	}
	
	public void mapHit(int dir, int dest){
		switch(dir){
		case UP:
			vy = dest - y;
			break;
		case DOWN:
			vy = dest - y;
			land();
			break;
		case LEFT:
		case RIGHT:
			vx = dest - x;
			break;
		}
	}
	/**
	 * �W�����v���Ă���� true
	 */
	public boolean jumping(){
		return jump;
	}
	/**
	 * �e�t���[���ł�update�p�֐�
	 */
	public void update(){
		landing = false;
		/**
		 * ���G��Ԃ���уm�b�N�o�b�N�̏���
		 */
		if(invisible){
			/**
			 * �m�b�N�o�b�N���A����s�\
			 */
			if(Data.frame - attackedFrame < nockBackTime){
				return;
			}
			/**
			 * ���G���؂�鏈��
			 * �_���[�W������Ă���45�t���[��(�����������Ȃ����1.5�b)�������疳�G����
			 */
			if(Data.frame - attackedFrame > invisibleTime){
				invisible = false;
			}
		}
		/**
		 * 15�t���[�����ƂɃO���t�B�b�N�̃X�e�[�^�X��ς���
		 * ����̕ω��Ńv�j�v�j������A�������������
		 */
		animationUpdate(15);
		/**
		 * �v���C���[�̉������̑��x����
		 * �_�b�V���{�^����������Ă���� 2 �{�ɂ��Ă���
		 * ���̂Ƃ��돑���Ă��Ȃ����Aspeed�����������
		 * �ǂ����蔲���錻�ۂ��N����̂Œ���
		 * �Ⴆ��Data.CHIP_SIZE���傫���ƕǂ𔲂���悤�ɂȂ�
		 */
		if(KeyStatus.dash){
			if(jumpCount == 0) speed = movingSpeed<<1;
		}else{
			if(jumpCount == 0) speed = movingSpeed;
		}
		/**
		 * �d�͕������Z
		 * �A��Data.CHIP_SIZE�𒴂���Ə������蔲���錻�ۂ���������̂�
		 * �������ő�l�ɂȂ�悤�ɂ��Ă���
		 * �󒆂�Data.CHIP_SIZE�����������X�N���v�g������ꍇ�A�������K�v����
		 */
		if(vy == 0) vy = 1;
		if((Data.frame%froat)==0) vy += Data.gravity;
		if(vy > Data.CHIP_SIZE) vy = Data.CHIP_SIZE-1;
		/**
		 * �U���{�^���������ꂽ�Ƃ��̏���
		 */
		if(KeyStatus.attack){
			if(weapon == null){
				if(/*jumpCount == 0 && */attackReleased){
					attackReleased = false;
					attack();
				}
			}
		}else{
			attackReleased = true;
		}
		/**
		 * �\�[�h�Ő؂��Ă���Œ��͓����Ȃ��悤�ɂ��Ă���
		 * �\�[�h���o�����ʒu�ŌŒ肵�Ă���̂ŁA������Ƃ�����Ƃ߂荞��
		 */
		/*
		if(weapon != null && weapon instanceof Sword){
			vx = 0;
			vy = 0;
		}else{
			*/
			/**
			 * �ړ���W�����v�̏���
			 * �ǂ�X�v���C�g�ɓ����锻���Map�N���X�ōs��
			 * �����ł͉�����Q���Ȃ������ꍇ�ɂǂꂾ���ړ����邩��ݒ肷��
			 */
			setMove();
			setJump();
//		}
	}
	
	/**
	 * �W�����v�̏���
	 */
	public void setJump(){
		if(KeyStatus.jump){
			if(jumpReleased && jumpCount < jumpMax){
				// �W�����v�{�^���������� ���� �W�����v�ł���ꍇ
				jumpReleased = false;
				jump = true;
				jumpCount++;
				// �W�����v��
				vy = -jumpSpeed;
			}
		}else{
			jumpReleased = true;
		}
		if((jumpReleased || !jump) && vy < 0){
			jump = false;
			vy = 0;
		}
	}
	/**
	 * ��l���̈ړ��ʂƂ������Ƃ����L�[�ɉ����ăZ�b�g����֐�
	 */
	public void setMove(){
		if(KeyStatus.up){
			direction = UP;
		}
		if(KeyStatus.down){
			direction = DOWN;
		}
		if(KeyStatus.left){
			if(KeyStatus.right){
				vx = 0;
			}else{
				vx = -speed;
				direction = LEFT;
			}
		}
		if(!KeyStatus.left){
			if(KeyStatus.right){
				vx = speed;
				direction = RIGHT;
			}else{
				vx = 0;
			}
		}
	}
	
	/**
	 * �����U�鏈��
	 */
	public void attack(){
		switch(weaponID){
		case Weapon.SWORD:
			weapon = new Sword();
			break;
		case Weapon.ARROW:
			weapon = new Arrow();
			break;
		}
		switch(direction){
		case UP:
			weapon.setDirection(Weapon.UP);
			break;
		case DOWN:
			weapon.setDirection(Weapon.DOWN);
			break;
		case LEFT:
			weapon.setDirection(Weapon.LEFT);
			break;
		case RIGHT:
			weapon.setDirection(Weapon.RIGHT);
			break;
		}
		weapon.element = element;
		weapon.appear();
	}
	
	/**
	 * d ���_���[�W���󂯂��Ƃ��̏���
	 * ���̓m�b�N�o�b�N�Ƃ��̑��x�͐��l�œK���ɂ���Ă�
	 */
	public void damage(int d){
		if(invisible) return;
		if(d>defence){
			life -= (d-defence);
		}else{
			life -= 1;
		}
		if(weapon != null && weapon instanceof Sword){
			weapon = null;
		}
		switch(direction){
		case UP:
			vx = 0;
			vy = 3;
			break;
		case DOWN:
			vx = 0;
			vy = -3;
			break;
		case LEFT:
			vx = 3;
			vy = 0;
			break;
		case RIGHT:
			vx = -3;
			vy = 0;
			break;
		}
		invisible = true;
		attackedFrame = Data.frame;
	}

	/**
	 * �`�揈��
	 */
	public void draw(Graphics g, int screenX, int screenY){
		if(!invisible || Data.frame % 2 != 0){
		    super.draw(g,screenX,screenY);
		}
		if(weapon != null) weapon.draw(g, screenX, screenY);
		g.setColor(Color.WHITE);

		LifeGauge.draw(g, x-screenX-5, y-15-screenY, life, lifeMax);
		g.drawString("life : " + life, 15,15);
		g.drawString("player : " + x + ", " + y, 15, 30);
		g.drawString("coin : " + coin, 15, 45);
	}
}