/**
 * �}�b�v�N���X
 * �c�Ƃ������v���C���̑S�̂������ǂ�N���X�ɂȂ��Ă�
 */

package Game;

import Game.Common.*;
import Game.Sprite.*;
import Game.Sprite.Weapon.*;
import Game.MapData.MapData;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Map{
	// �}�b�v��ID(���̓}�b�v����Ȃ̂Ŏg���ĂȂ�)
	private int id;
	/**
	 * ��ʕ\���p�̍��W�ϐ��A�����ɂ���ĉ�ʂ���肢��ɃX�N���[������
	 * ��ʂɉf���Ă����ԍ���̍��W���w��
	 */
	private int x, y;
	// �v���C���[
	private Player player;
	/**
	 * �}�b�v�f�[�^
	 * �}�b�v�Ɋւ������ێ�����A�}�b�v�ړ�����ۂ�
	 * mapData.load(int id)���g���}�b�v�����X�V����
	 * �A��load�֐��͍��̂Ƃ��떢����
	 */
	private MapData mapData;
	/**
	 * �|�[�Y�{�^���A���j���[�{�^���������ꂽ���ǂ����̕ϐ�
	 */
	private boolean pauseReleased;
	private boolean menuReleased;
	// �|�[�Y��
	private boolean pausing;

	/**
	 * �R���X�g���N�^
	 * �����ݒ肵�Ă�
	 */
	public Map(int mapId, int x, int y){
		pauseReleased = false;
		pausing = false;
		id = mapId;
		player = new Player(x, y);
		mapData = new MapData(id);
	}
	/**
	 * �}�b�v�̃X�N���[���֐�
	 * �v���C���[�����傤�ǂ����ʒu�Ɏʂ�悤�ɉ�ʂ��X�N���[������
	 * �����X�N���[���ʂȂǂ������������ꍇ��
	 * ����ł͑Ή��ł��Ȃ�
	 */
	public void scroll(){
		int npx = player.x + player.vx;
		int npy = player.y + player.vy;
		int nx = npx - Data.WIDTH/2;
		int ny = npy - Data.HEIGHT/2;
		if(nx < 0) nx = 0;
		if(ny < 0) ny = 0;
		if(nx > mapData.col*Data.CHIP_SIZE-Data.WIDTH) nx = mapData.col*Data.CHIP_SIZE-Data.WIDTH;
		if(ny > mapData.row*Data.CHIP_SIZE-Data.HEIGHT) ny = mapData.row*Data.CHIP_SIZE-Data.HEIGHT;
		x = nx;
		y = ny;
	}
	/**
	 * �`��p�֐�
	 * �}�b�v�̕`��Ɠ�����
	 * �}�b�v�̎����Ă���I�u�W�F�N�g�̕`��֐����Ăяo��
	 * ��̓I�ɂ̓v���C���[(���������)�ƃX�v���C�g�̕`��֐����Ăяo��
	 */
	public void draw(Graphics g){
		/* �w�i�`�� */
		g.drawImage(Data.image.backgroundImage,0,0,null);
		/* �}�b�v�̕`��J�n */
		int row = Data.HEIGHT / Data.CHIP_SIZE;
		int col = Data.WIDTH / Data.CHIP_SIZE;
		for(int i = 0; i <= row; i++){
			for(int j = 0; j <= col; j++){ 
				int chipX = j + x / Data.CHIP_SIZE;
				if(chipX < 0) chipX = 0;
				if(chipX >= mapData.col) chipX = mapData.col-1;
				int chipY = i + y / Data.CHIP_SIZE;
				if(chipY < 0) chipY = 0;
				if(chipY >= mapData.row) chipY = mapData.row-1;
				if(mapData.data[chipY][chipX] == 0) continue;
				int imageX = (mapData.data[chipY][chipX] % 16) * Data.CHIP_SIZE;
				int imageY = (mapData.data[chipY][chipX] / 16) * Data.CHIP_SIZE;
				g.drawImage(Data.image.mapImage,
					j * Data.CHIP_SIZE - (x%Data.CHIP_SIZE), i * Data.CHIP_SIZE -(y%Data.CHIP_SIZE), 
					j * Data.CHIP_SIZE -(x%Data.CHIP_SIZE)+Data.CHIP_SIZE, i * Data.CHIP_SIZE -(y%Data.CHIP_SIZE)+Data.CHIP_SIZE,
					imageX, imageY, imageX + Data.CHIP_SIZE, imageY + Data.CHIP_SIZE,
					null
				);
			}
		}
		/* �}�b�v�̕`��I�� */
		/* �X�v���C�g�̕`�� */
		for(int i = 0; i < mapData.spriteList.size(); i++){
			mapData.spriteList.get(i).draw(g,x,y);
		}
		/* �v���C���[�̕`�� */
		player.draw(g,x,y);
		// �|�[�Y���̏ꍇ�A��ʂɃ|�[�Y�̕�����_�ł�����
		if(pausing && (Data.frame/30)%2 == 0) g.drawString("Pause",Data.WIDTH/2, Data.HEIGHT/2);
	}
	/**
	 * mapHitUp����mapHitRight��
	 * �e�����̃}�b�v�Ƃ̏Փ˔�����s���֐�
	 * �}�b�v�Ƃ̏Փ˔����������ꍇ�͂�����������
	 */
	public int mapHitUp(int px, int py, int width, int height){
		int from = (px+Data.CD_DIFF)/Data.CHIP_SIZE;
		int to = (px+width-1-Data.CD_DIFF)/Data.CHIP_SIZE;
		for(int i = from; i <= to; i++){
			if(py+Data.CD_DIFF < 0) return -Data.CD_DIFF;
			int newChipY = (py+Data.CD_DIFF)/Data.CHIP_SIZE;
			if(mapData.pass[newChipY][i] == 1){
				return newChipY*Data.CHIP_SIZE + Data.CHIP_SIZE - Data.CD_DIFF;
			}
		}
		return Integer.MIN_VALUE;
	}
	public int mapHitDown(int px, int py, int width, int height){
		int from = (px+Data.CD_DIFF)/Data.CHIP_SIZE;
		int to = (px+width-1-Data.CD_DIFF)/Data.CHIP_SIZE;
		for(int i = from; i <= to; i++){
			int newChipY = (py+height-Data.CD_DIFF)/Data.CHIP_SIZE;
			if(newChipY >= mapData.row) return mapData.row * Data.CHIP_SIZE - height + Data.CD_DIFF;
			if(mapData.pass[newChipY][i] == 1){
				return newChipY*Data.CHIP_SIZE - height + Data.CD_DIFF;
			}
		}
		return Integer.MIN_VALUE;
	}
	public int mapHitLeft(int px, int py, int width, int height){
		int from = (py+Data.CD_DIFF)/Data.CHIP_SIZE;
		int to = (py+height-1-Data.CD_DIFF)/Data.CHIP_SIZE;
		for(int i = from; i <= to; i++){
			if(px+Data.CD_DIFF < 0) return -Data.CD_DIFF;
			int newChipX = (px+Data.CD_DIFF)/Data.CHIP_SIZE;
			if(mapData.pass[i][newChipX] == 1){
				return newChipX*Data.CHIP_SIZE + Data.CHIP_SIZE - Data.CD_DIFF;
			}
		}
		return Integer.MIN_VALUE;
	}
	public int mapHitRight(int px, int py, int width, int height){
		int from = (py+Data.CD_DIFF)/Data.CHIP_SIZE;
		int to = (py+height-1-Data.CD_DIFF)/Data.CHIP_SIZE;
		for(int i = from; i <= to; i++){
			int newChipX = (px+width-Data.CD_DIFF)/Data.CHIP_SIZE;
			if(newChipX >= mapData.col) return mapData.col * Data.CHIP_SIZE - width + Data.CD_DIFF;
			if(mapData.pass[i][newChipX] == 1){
				return newChipX*Data.CHIP_SIZE - width + Data.CD_DIFF;
			}
		}
		return Integer.MIN_VALUE;
	}
	/**
	 * �X�v���C�g�ƃ}�b�v�̏Փ˔���
	 * ��{��playerAndMapHit�Ɠ���
	 * �ǂ̎�ނ̃X�v���C�g���}�b�v�ɏՓ˂������Ƃ��̓����
	 * �e�N���X��mapHit�֐����g��
	 * �K�v�Ȃ�� if(tmp instanceof ****) �œ��ʂȓ��������
	 * �Ⴆ�Εǂ��󂷂悤�ȃX�v���C�g�Ȃ�
	 */
	public boolean spriteAndMapHit(Sprite tmp){
		boolean ret = false;
		if(tmp.vy < 0){
			int d = mapHitUp(tmp.x,tmp.y+tmp.vy,tmp.width,tmp.height);
			if(d != Integer.MIN_VALUE){
				tmp.mapHit(Sprite.UP,d);
				ret = true;
			}
		}
		if(tmp.vy > 0){
			int d = mapHitDown(tmp.x,tmp.y+tmp.vy,tmp.width,tmp.height);
			if(d != Integer.MIN_VALUE){
				tmp.mapHit(Sprite.DOWN,d);
				ret = true;
			}else{
				/**
				 * ���ɐڂ��Ȃ���΃v���C���[�͋󒆂ɂ��邱�ƂɂȂ�
				 * mapHit�ɂ܂Ƃ߂����������ǁA�v�����Ȃ�����
				 * ���ꂪ�Ȃ��Ă������͂���񂾂��ǁA
				 * �󒆂ɂ���Ƃ�������̂��߂ɕK�v
				 */
				if(tmp instanceof Player) player.fall();
			}
		}
		if(tmp.vx < 0){
			int d = mapHitLeft(tmp.x+tmp.vx,tmp.y+tmp.vy,tmp.width,tmp.height);
			if(d != Integer.MIN_VALUE){
				tmp.mapHit(Sprite.LEFT,d);
				ret = true;
			}
		}
		if(tmp.vx > 0){
			int d = mapHitRight(tmp.x+tmp.vx,tmp.y+tmp.vy,tmp.width,tmp.height);
			if(d != Integer.MIN_VALUE){
				tmp.mapHit(Sprite.RIGHT,d);
				ret = true;
			}
		}
		return ret;
	}
	/**
	 * hitUp����hitRight�͕��̓��m�̊e�����ɑ΂���Փ˔���
	 * �U���̏Փ˔���Ƃ����������g��
	 * �U���̔���Ƃ��A�G�Ƃ̐ڐG����Ƃ���
	 * �����蔻���������ꍇ�͂�����
	 * "1����ɓ�����"��hitUp
	 */
	public int hitUp(int px1, int py1, int width1, int height1, int px2, int py2, int width2, int height2){
		if(px1+Data.CD_DIFF > px2+width2-1-Data.CD_DIFF || px1+width1-1-Data.CD_DIFF < px2+Data.CD_DIFF) return Integer.MIN_VALUE;
		if(py1+Data.CD_DIFF <= py2+height2-1-Data.CD_DIFF && py1+height1-1 > py2+height2-1){
			return py2+height2-Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
	public int hitDown(int px1, int py1, int width1, int height1, int px2, int py2, int width2, int height2){
		if(px1+Data.CD_DIFF > px2+width2-1-Data.CD_DIFF || px1+width1-1-Data.CD_DIFF < px2+Data.CD_DIFF) return Integer.MIN_VALUE;
		if(py1 < py2 && py1+height1-1-Data.CD_DIFF >= py2+Data.CD_DIFF){
			return py2-height1+2*Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
	public int hitLeft(int px1, int py1, int width1, int height1, int px2, int py2, int width2, int height2){
		if(py1+Data.CD_DIFF > py2+height2-1-Data.CD_DIFF || py1+height1-1-Data.CD_DIFF < py2+Data.CD_DIFF) return Integer.MIN_VALUE;
		if(px1+Data.CD_DIFF <= px2+width2-1-Data.CD_DIFF && px1+width1-1 > px2+width2-1){
			return px2+width2-Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
	public int hitRight(int px1, int py1, int width1, int height1, int px2, int py2, int width2, int height2){
		if(py1+Data.CD_DIFF > py2+height2-1-Data.CD_DIFF || py1+height1-1-Data.CD_DIFF < py2+Data.CD_DIFF) return Integer.MIN_VALUE;
		if(px1 < px2 && px1+width1-1-Data.CD_DIFF >= px2+Data.CD_DIFF){
			return px2-width1+2*Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
	/**
	 * �X�v���C�g���m�̃X�v���C�g�̏Փ˔���
	 * ���̂Ƃ���v���C���[�ƓG�A����ƓG�̏Փ˔���ɂ̂ݎg���Ă���
	 */
	public boolean spriteAndSpriteHit(Sprite s1, Sprite s2){
		int dUp = hitUp(s1.x,s1.y+s1.vy,s1.width,s1.height,s2.x,s2.y+s2.vy,s2.width,s2.height);
		int dDown = hitDown(s1.x,s1.y+s1.vy,s1.width,s1.height,s2.x,s2.y+s2.vy,s2.width,s2.height);
		int dLeft = hitLeft(s1.x+s1.vx,s1.y,s1.width,s1.height,s2.x+s2.vx,s2.y,s2.width,s2.height);
		int dRight = hitRight(s1.x+s1.vx,s1.y,s1.width,s1.height,s2.x+s2.vx,s2.y,s2.width,s2.height);
		if(dUp != Integer.MIN_VALUE){
			if(s1 instanceof Player) s2.touch(s1,Sprite.DOWN,dUp);
			if(s1 instanceof Weapon) s2.attacked(s1);
			return true;
		}
		if(dDown != Integer.MIN_VALUE){
			if(s1 instanceof Player) s2.touch(s1,Sprite.UP,dDown);
			if(s1 instanceof Weapon) s2.attacked(s1);
			return true;
		}
		if(dLeft != Integer.MIN_VALUE){
			if(s1 instanceof Player) s2.touch(s1,Sprite.RIGHT,dLeft);
			if(s1 instanceof Weapon) s2.attacked(s1);
			return true;
		}
		if(dRight != Integer.MIN_VALUE){
			if(s1 instanceof Player) s2.touch(s1,Sprite.LEFT,dRight);
			if(s1 instanceof Weapon) s2.attacked(s1);
			return true;
		}
		return false;
	}

	/**
	 * �t���[�����Ƃ̍X�V�p�֐�
	 * �����ȃC���X�^���X��update���Ăяo��
	 * �����̏��Ԃ͌��\�d�v�������肷��
	 * �㔼�̕����D��x������
	 * �Ⴆ�ΓG�ɓ������ċ��������
	 * �ǂɂ߂荞�܂Ȃ������厖�Ȃ�
	 */
	public void update(){
		/**
		 * ���j���[�Ăяo���p�̑���
		 * ���̓��j���[�{�^���ɑΉ�����{�^����ݒ肵�Ă��Ȃ����A
		 * �����������j���[������Ă��Ȃ�
		 */
		if(!KeyStatus.menu) menuReleased = true;
		if(menuReleased && KeyStatus.menu){
			Data.gameStatus = Data.MENU;
			menuReleased = false;
			return;
		}
		/**
		 * �|�[�Y�p����
		 */
		if(!KeyStatus.pause) pauseReleased = true;
		if(pausing){
			if(pauseReleased && KeyStatus.pause){
				pausing = false;
				pauseReleased = false;
			}
			return;
		}else{
			if(pauseReleased && KeyStatus.pause){
				pausing = true;
				pauseReleased = false;
				return;
			}
		}
		// �v���C���[�̏�ԍX�V
		player.update();
		/**
		 * ����ƕǂƂ̏Փ˔���
		 * ��Ƃ��͎h���邩�����邩�����ˁH
		 */
		if(player.weapon != null){
			player.weapon.update(mapData);
			if(player.weapon.end) player.weapon = null;
			else spriteAndMapHit(player.weapon);
		}
		// �X�v���C�g�̏Փ˔���
		// �ǁE�v���C���[�E����Ƃ̏Փ˔�����s��
		for(int i = 0; i < mapData.spriteList.size(); i++){
			Sprite tmp = mapData.spriteList.get(i);
			tmp.update(mapData);
			spriteAndSpriteHit(player,tmp);
			if(player.weapon != null){
				spriteAndSpriteHit(player.weapon,tmp);
			}
			if(tmp.end){
				mapData.spriteList.remove(tmp);
				continue;
			}
			spriteAndMapHit(tmp);
			tmp.move();
		}
		// �v���C���[�̃}�b�v�Ƃ̏Փ˔���
		spriteAndMapHit(player);
		/**
		 * �����܂łŁA�}�b�v�Ƃ��G�Ƃ��ɂԂ��邱�Ƃɂ��
		 * ���x�ϓ��̏������S���I������̂ŁA
		 * ��l���̈ړ��ƁA���̈ړ���ɍ��킹����ʃX�N���[��
		 */
		scroll();
		player.move();
	}
}
