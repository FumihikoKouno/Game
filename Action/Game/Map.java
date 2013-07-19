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
		Data.player = new Player(x, y);
		mapData = new MapData(id);
	}
	/**
	 * �}�b�v�̃X�N���[���֐�
	 * �v���C���[�����傤�ǂ����ʒu�Ɏʂ�悤�ɉ�ʂ��X�N���[������
	 * �����X�N���[���ʂȂǂ������������ꍇ��
	 * ����ł͑Ή��ł��Ȃ�
	 */
	public void scroll(){
		int npx = Data.player.getX() + Data.player.getVx();
		int npy = Data.player.getY() + Data.player.getVy();
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
		Data.player.draw(g,x,y);
		// �|�[�Y���̏ꍇ�A��ʂɃ|�[�Y�̕�����_�ł�����
		if(pausing && (Data.frame/30)%2 == 0) g.drawString("Pause",Data.WIDTH/2, Data.HEIGHT/2);
	}
	/**
	 * mapHitUp����mapHitRight��
	 * �e�����̃}�b�v�Ƃ̏Փ˔�����s���֐�
	 * �}�b�v�Ƃ̏Փ˔����������ꍇ�͂�����������
	 */
	public int mapHitUp(Sprite s){
	    int px = s.getX();
	    int py = s.getY() + s.getVy();
	    int width = s.getWidth();
	    int height = s.getHeight();
	    int from = (px+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int to = (px+width-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int f = (py+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int t = (py+height-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int ret = Integer.MIN_VALUE;
	    for(int i = from; i <= to; i++){
		for(int j = f; j <= t ; j++){
		    if(i<0||i>=mapData.col||j<0||j>=mapData.row) continue;
		    if(mapData.pass[j][i] == 1){
			ret = Math.max(ret, j*Data.CHIP_SIZE + Data.CHIP_SIZE - Data.CD_DIFF);
		    }
		}
		if(py+Data.CD_DIFF < 0) ret = Math.max(ret,-Data.CD_DIFF);
	    }
	    return ret;
	}
	public int mapHitDown(Sprite s){
	    int px = s.getX();
	    int py = s.getY() + s.getVy();
	    int width = s.getWidth();
	    int height = s.getHeight();
	    int from = (px+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int to = (px+width-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int f = (py+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int t = (py+height-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int ret = Integer.MAX_VALUE;
	    for(int i = from; i <= to; i++){
		for(int j = f; j <= t; j++){
		    if(i<0||i>=mapData.col||j<0) continue;
		    if(j >= mapData.row) ret = Math.min(ret,mapData.row * Data.CHIP_SIZE - height + Data.CD_DIFF);
		    else if(mapData.pass[j][i] == 1){
			ret = Math.min(ret,j*Data.CHIP_SIZE - height + Data.CD_DIFF);
		    }
		}
	    }
	    if(ret == Integer.MAX_VALUE) return Integer.MIN_VALUE;
	    return ret;
	}
	public int mapHitLeft(Sprite s){
	    int px = s.getX() + s.getVx();
	    int py = s.getY() + s.getVy();
	    int width = s.getWidth();
	    int height = s.getHeight();
	    int from = (py+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int to = (py+height-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int f = (px+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int t = (px+width-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int ret = Integer.MIN_VALUE;
	    for(int i = from; i <= to; i++){
		for(int j = f; j <= t; j++){
		    if(j<0||j>=mapData.col||i<0||i>=mapData.row) continue;
		    if(mapData.pass[i][j] == 1){
			ret = Math.max(ret,j*Data.CHIP_SIZE + Data.CHIP_SIZE - Data.CD_DIFF);
		    }
		}
		if(px+Data.CD_DIFF < 0) ret = Math.max(ret,-Data.CD_DIFF);
	    }
	    return ret;
	}
	public int mapHitRight(Sprite s){
	    int px = s.getX() + s.getVx();
	    int py = s.getY() + s.getVy();
	    int width = s.getWidth();
	    int height = s.getHeight();
	    int from = (py+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int to = (py+height-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int f = (px+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int t = (px+width-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int ret = Integer.MAX_VALUE;
	    for(int i = from; i <= to; i++){
		for(int j = f; j <= t; j++){
		    if(j<0||i<0||i>=mapData.row) continue;
		    if(j >= mapData.col) ret = Math.min(ret,mapData.col * Data.CHIP_SIZE - width + Data.CD_DIFF);
		    else if(mapData.pass[i][j] == 1){
			ret = Math.min(ret,j*Data.CHIP_SIZE - width + Data.CD_DIFF);
		    }  
		}
	    }
	    if(ret == Integer.MAX_VALUE) return Integer.MIN_VALUE;
	    return ret;
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
		if(tmp.getVy() < 0){
			int d = mapHitUp(tmp);
			if(d != Integer.MIN_VALUE){
				tmp.mapHit(Sprite.UP,d);
				ret = true;
			}
		}
		if(tmp.getVy() > 0){
			int d = mapHitDown(tmp);
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
				if(tmp instanceof Player) Data.player.fall();
			}
		}
		if(tmp.getVx() < 0){
			int d = mapHitLeft(tmp);
			if(d != Integer.MIN_VALUE){
				tmp.mapHit(Sprite.LEFT,d);
				ret = true;
			}
		}
		if(tmp.getVx() > 0){
			int d = mapHitRight(tmp);
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
	/**
	 * ��������Sprite���m�̏Փ˔��肪����ŗǂ��̂��s��
	 */
    public int hitUp(Sprite s1, Sprite s2){
	    int px1 = s1.getX();
	    int py1 = s1.getY()+s1.getVy();
	    int width1 = s1.getWidth();
	    int height1 = s1.getHeight();
	    int px2 = s2.getX();
	    int py2 = s2.getY()+s2.getVy();
	    int width2 = s2.getWidth();
	    int height2 = s2.getHeight();
		if(px1+Data.CD_DIFF > px2+width2-1 || px1+width1-1-Data.CD_DIFF < px2) return Integer.MIN_VALUE;
		if(py1+Data.CD_DIFF <= py2+height2-1 && py1+height1-1-Data.CD_DIFF > py2+height2-1){
			return py2+height2-Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
	public int hitDown(Sprite s1, Sprite s2){
		int px1 = s1.getX();
		int py1 = s1.getY()+s1.getVy();
		int width1 = s1.getWidth();
		int height1 = s1.getHeight();
		int px2 = s2.getX();
		int py2 = s2.getY()+s2.getVy();
		int width2 = s2.getWidth();
		int height2 = s2.getHeight();
		if(px1+Data.CD_DIFF > px2+width2-1 || px1+width1-1-Data.CD_DIFF < px2) return Integer.MIN_VALUE;
		if(py1+Data.CD_DIFF < py2 && py1+height1-1-Data.CD_DIFF >= py2){
			return py2-height1+Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
    public int hitLeft(Sprite s1, Sprite s2){
	    int px1 = s1.getX()+s1.getVx();
	    int py1 = s1.getY();
	    int width1 = s1.getWidth();
	    int height1 = s1.getHeight();
	    int px2 = s2.getX()+s2.getVx();
	    int py2 = s2.getY();
	    int width2 = s2.getWidth();
	    int height2 = s2.getHeight();
		if(py1+Data.CD_DIFF > py2+height2-1 || py1+height1-1-Data.CD_DIFF < py2) return Integer.MIN_VALUE;
		if(px1+Data.CD_DIFF <= px2+width2-1 && px1+width1-1-Data.CD_DIFF > px2+width2-1){
			return px2+width2-Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
    public int hitRight(Sprite s1, Sprite s2){
	    int px1 = s1.getX()+s1.getVx();
	    int py1 = s1.getY();
	    int width1 = s1.getWidth();
	    int height1 = s1.getHeight();
	    int px2 = s2.getX()+s2.getVx();
	    int py2 = s2.getY();
	    int width2 = s2.getWidth();
	    int height2 = s2.getHeight();
		if(py1+Data.CD_DIFF > py2+height2-1 || py1+height1-1-Data.CD_DIFF < py2) return Integer.MIN_VALUE;
		if(px1+Data.CD_DIFF < px2 && px1+width1-1-Data.CD_DIFF >= px2){
			return px2-width1+Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
	/**
	 * �X�v���C�g���m�̃X�v���C�g�̏Փ˔���
	 */
	public boolean spriteAndSpriteHit(Sprite s1, Sprite s2){
		int[] hitPosition = new int[4];
		hitPosition[Sprite.UP] = hitUp(s1,s2);
		hitPosition[Sprite.DOWN]  = hitDown(s1,s2);
		hitPosition[Sprite.LEFT]  = hitLeft(s1,s2);
		hitPosition[Sprite.RIGHT]  = hitRight(s1,s2);
		int hitDir = 0;
		for(int i = 0; i < 4; i++){
			if(hitPosition[i] != Integer.MIN_VALUE) hitDir = hitDir | (1 << i);
		}
		if(hitDir > 0){
			if(s1 instanceof Player) s2.touch(s1,hitDir,hitPosition);
			else if(s1 instanceof Weapon) s2.attacked(s1);
			else{
				s2.touch(s1,hitDir,hitPosition);
				int tmp;
				tmp = hitPosition[Sprite.UP];
				hitPosition[Sprite.UP] = hitPosition[Sprite.DOWN];
				hitPosition[Sprite.DOWN] = tmp;
				tmp = hitPosition[Sprite.LEFT];
				hitPosition[Sprite.LEFT] = hitPosition[Sprite.RIGHT];
				hitPosition[Sprite.RIGHT] = tmp;
				hitDir = 0;
				for(int i = 0; i < 4; i++){
					if(hitPosition[i] != Integer.MIN_VALUE) hitDir = hitDir | (1 << i);
				}
				s1.touch(s2,hitDir,hitPosition);
			}
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
		Data.player.update();
		/**
		 * ����ƕǂƂ̏Փ˔���
		 * ��Ƃ��͎h���邩�����邩�����ˁH
		 */
		if(Data.player.weapon != null){
			Data.player.weapon.update(mapData);
			if(Data.player.weapon.end) Data.player.weapon = null;
			else spriteAndMapHit(Data.player.weapon);
		}
		// �X�v���C�g�̏Փ˔���
		// �ǁE�v���C���[�E����Ƃ̏Փ˔�����s��
		for(int i = 0; i < mapData.spriteList.size(); i++){
			Sprite tmp = mapData.spriteList.get(i);
			// ��ʊO�̃X�v���C�g�ɂ��Ă̌v�Z�͍s��Ȃ�
			if(tmp.getX() < x - Data.SCREEN_OUT || tmp.getX() > x + Data.WIDTH + Data.SCREEN_OUT || tmp.getY() < y - Data.SCREEN_OUT || tmp.getY() > y + Data.WIDTH + Data.SCREEN_OUT) continue;
			tmp.update(mapData);
			spriteAndSpriteHit(Data.player,tmp);
			if(Data.player.weapon != null){
				spriteAndSpriteHit(Data.player.weapon,tmp);
			}
			for(int j = i+1; j < mapData.spriteList.size(); j++){
				Sprite tmp2 = mapData.spriteList.get(j);
				if(tmp2.getX() < x - Data.SCREEN_OUT || tmp2.getX() > x + Data.WIDTH + Data.SCREEN_OUT || tmp2.getY() < y - Data.SCREEN_OUT || tmp2.getY() > y + Data.WIDTH + Data.SCREEN_OUT) continue;
				spriteAndSpriteHit(tmp,tmp2);
			}
			if(tmp.end){
				mapData.spriteList.remove(tmp);
				continue;
			}
			spriteAndMapHit(tmp);
			tmp.move();
		}
		// �v���C���[�̃}�b�v�Ƃ̏Փ˔���
		spriteAndMapHit(Data.player);
		/**
		 * �����܂łŁA�}�b�v�Ƃ��G�Ƃ��ɂԂ��邱�Ƃɂ��
		 * ���x�ϓ��̏������S���I������̂ŁA
		 * ��l���̈ړ��ƁA���̈ړ���ɍ��킹����ʃX�N���[��
		 */
		scroll();
		Data.player.move();
	}
}
