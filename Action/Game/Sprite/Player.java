/**
 * �v���C���[��\�킷�N���X
 */

package Game.Sprite;

import Game.Common.*;
import Game.Sprite.Weapon.*;
import Game.Body.*;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Player extends Sprite{
	// ���̃t���[�����ɉ����ɒ��n������true
	private boolean landing = false;
	
	private boolean shiftReleased = true;
	
	private final int END_TIME = 30;
	
	// ���ꂽ�Ƃ��̃��C�t(���ꂽ�G�t�F�N�g�p��)
	private int endedLife;
	
	/**
	 * �v���C���[�̃W�����v�{�^������x�����ꂽ���ǂ���
	 * ���ꂪ�Ȃ��ƃW�����v�{�^���������ςȂ��ŉ��x���W�����v���Ă��܂�
	 */
	public boolean jumpReleased = true;
	// ��̃W�����v�������ꂽ���ǂ����̍U���{�^����
	public boolean attackReleased = true;
	// �R�C������
	public byte coin;
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
	private byte speed;
	
	/**
	 * �ړ����x�֌W
	 * �S�g�����̃N���X���Q�Ƃ���
	 */
	private byte invisibleTime;
	private byte nockBackTime;
	private byte defence;
	private byte movingSpeed;
	private byte jumpSpeed;
	private byte jumpMax;
	private byte froat;
	
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
		endedLife = 1;
		vx = 0; vy = 0;
		width = 32;
		height = 32;
		direction = RIGHT;
		lifeMax = 10;
		life = 10;
		element = 0;
		equipBody(0);
	}
	public void land(){
		super.land();
		landing = true;
	}
	
	public void testAtShift(){
		if(KeyStatus.pause){
			if(shiftReleased){
				StateData.gotElement[1]=5;
				StateData.gotElement[2]=5;
				StateData.gotElement[3]=5;
				lifeMax += 5;
				life += 5;
				StateData.flag.gotWeapon=3;
				StateData.flag.gotElement=7;
				StateData.flag.gotBody=3;
				shiftReleased = false;
				StateData.mapData.passSpriteList.add(new BrokenChip(x,y,Weapon.SWORD,Element.NONE));
			}
		}else{
			shiftReleased = true;
		}
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
	
	public void screenOut(){
		if(life > 0) life -= 1000;
	}
	
	/**
	 * �e�t���[���ł�update�p�֐�
	 */
	public void update(){
		if(life <= 0){
			dead();
			return;
		}
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
			* �_���[�W������Ă���invisibleFrame���̃t���[��(�����������Ȃ����1.5�b)�������疳�G����
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
			if(jumpCount == 0) speed = (byte)(movingSpeed<<1);
		}else{
			if(jumpCount == 0) speed = (byte)movingSpeed;
		}
		
		// shift�{�^�����������Ƃŉ������e�X�g����
		testAtShift();
		
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
		default:
			return;
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
		if(element > 0){
			StateData.gotElement[element]--;
			if(StateData.gotElement[element] == 0){
				element = Element.NONE;
			}
		}
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

	private void dead(){
		if(endedLife > 0){
			vx = vy = 0;
			endedLife = life;
		}else if(life <= endedLife-END_TIME){
			x = StateData.mapData.getFirstX();
			y = StateData.mapData.getFirstY();
			StateData.mapData.setFit(false);
			life = lifeMax;
			endedLife = 1;
		}else{
			life--;
		}
	}
	
	/**
	 * �`�揈��
	 */
	public void draw(Graphics g, int screenX, int screenY){
		g.setColor(Color.BLACK);
		g.fillRect(5,5,(Data.CHIP_SIZE<<1)+10,Data.CHIP_SIZE+10);
		
		g.setColor(Color.WHITE);
		g.drawRect(5,5,(Data.CHIP_SIZE<<1)+10,Data.CHIP_SIZE+10);
		
		g.drawRect(10,10,Data.CHIP_SIZE,Data.CHIP_SIZE);
		g.drawImage(Data.image.weaponIconImage,
			10,10,10+Data.CHIP_SIZE,10+Data.CHIP_SIZE,
			(weaponID<<Data.CHIP_BIT),0,(weaponID<<Data.CHIP_BIT)+Data.CHIP_SIZE,Data.CHIP_SIZE,
			null);
		
		g.drawRect(10+Data.CHIP_SIZE,10,Data.CHIP_SIZE,Data.CHIP_SIZE);
		g.drawImage(Data.image.elementIconImage,
			10+Data.CHIP_SIZE,10,10+(Data.CHIP_SIZE<<1),10+Data.CHIP_SIZE,
			(element<<Data.CHIP_BIT),0,(element<<Data.CHIP_BIT)+Data.CHIP_SIZE,Data.CHIP_SIZE,
			null);
		
		if(endedLife > 0){
			if(!invisible || Data.frame % 2 != 0){
				super.draw(g,screenX,screenY);
			}
			if(weapon != null) weapon.draw(g, screenX, screenY);
		}else{
			int endFrame = ((endedLife - life) << 2);
			int dx = x - screenX;
			int dy = y - screenY;
			int size;
			if(Data.frame % 8 < 4){
				size = 8;
				dx += 4;
				dy += 4;
			}else{
				size = 16;
			}
			g.setColor(Color.WHITE);
			g.fillOval(dx,dy-endFrame*3/2,size,size);
			g.fillOval(dx-endFrame,dy-endFrame,size,size);
			g.fillOval(dx-endFrame*3/2,dy,size,size);
			g.fillOval(dx-endFrame,dy+endFrame,size,size);
			g.fillOval(dx,dy+endFrame*3/2,size,size);
			g.fillOval(dx+endFrame,dy+endFrame,size,size);
			g.fillOval(dx+endFrame*3/2,dy,size,size);
			g.fillOval(dx+endFrame,dy-endFrame,size,size);
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("dialog", Font.BOLD, 10));
		g.drawString("Player",5,Data.HEIGHT-20);
// ���C�t�Q�[�W���v���C���[�̏�ɕ\������ꍇ
//		LifeGauge.draw(g, x-screenX+width/2, y-screenY, life, lifeMax);
		LifeGauge.draw(g, lifeMax+15, Data.HEIGHT, life, lifeMax);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("dialog", Font.PLAIN, 12));
		
		g.drawString("life : " + life, 10,60);
		g.drawString("player : " + x + ", " + y, 10, 75);
		g.drawString("coin : " + coin, 10, 90);
	}
}