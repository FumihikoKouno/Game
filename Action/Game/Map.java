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
		int lx = s.getX()+Data.CD_DIFF;
		int rx = s.getX()+s.getWidth()-1-Data.CD_DIFF;
		int uy = s.getY()+Data.CD_DIFF;
		int dy = s.getY()+s.getHeight()-1-Data.CD_DIFF;
		int vy = s.getVy();
		int fromX = lx/Data.CHIP_SIZE;
		int toX = rx/Data.CHIP_SIZE;
		int fromY = (uy+vy)/Data.CHIP_SIZE;
		int toY = dy/Data.CHIP_SIZE;
		for(int i = toY; i >= fromY; i--){
			for(int j = fromX; j <= toX; j++){
				if(j<0||j>=mapData.col||i<0||i>=mapData.row) continue;
				if(mapData.pass[i][j] == 1){
					return (i+1)*Data.CHIP_SIZE-Data.CD_DIFF;
				}
			}
		}
		if(uy+vy < 0) return -Data.CD_DIFF;
		return Integer.MIN_VALUE;
	}
	public int mapHitDown(Sprite s){
		int lx = s.getX()+Data.CD_DIFF;
		int rx = s.getX()+s.getWidth()-1-Data.CD_DIFF;
		int uy = s.getY()+Data.CD_DIFF;
		int dy = s.getY()+s.getHeight()-1-Data.CD_DIFF;
		int vy = s.getVy();
		int fromX = lx/Data.CHIP_SIZE;
		int toX = rx/Data.CHIP_SIZE;
		int fromY = uy/Data.CHIP_SIZE;
		int toY = (dy+vy)/Data.CHIP_SIZE;
		for(int i = fromY; i <= toY; i++){
			if(i >= mapData.row) return mapData.row*Data.CHIP_SIZE-s.getHeight()+Data.CD_DIFF;
			for(int j = fromX; j <= toX; j++){
				if(j<0||j>=mapData.col||i<0) continue;
				if(mapData.pass[i][j] == 1){
					return i*Data.CHIP_SIZE-s.getHeight()+Data.CD_DIFF;
				}
			}
		}
		return Integer.MIN_VALUE;
	}
	public int mapHitLeft(Sprite s){
		int vx = s.getVx();
		int vy = s.getVy();
		int lx = s.getX()+Data.CD_DIFF;
		int rx = s.getX()+s.getWidth()-1-Data.CD_DIFF;
		int uy = s.getY()+Data.CD_DIFF+vy;
		int dy = s.getY()+s.getHeight()-1-Data.CD_DIFF+vy;
		int fromX = (lx+vx)/Data.CHIP_SIZE;
		int toX = rx/Data.CHIP_SIZE;
		int fromY = uy/Data.CHIP_SIZE;
		int toY = dy/Data.CHIP_SIZE;
		for(int i = toX; i >= fromX; i--){
			for(int j = fromY; j <= toY; j++){
				if(i<0||i>=mapData.col||j<0||j>=mapData.row) continue;
				if(mapData.pass[j][i] == 1){
					return (i+1)*Data.CHIP_SIZE-Data.CD_DIFF;
				}
			}
		}
		if(lx+vx < 0) return -Data.CD_DIFF;
		return Integer.MIN_VALUE;
	}
	public int mapHitRight(Sprite s){
		int vx = s.getVx();
		int vy = s.getVy();
		int lx = s.getX()+Data.CD_DIFF;
		int rx = s.getX()+s.getWidth()-1-Data.CD_DIFF;
		int uy = s.getY()+Data.CD_DIFF+vy;
		int dy = s.getY()+s.getHeight()-1-Data.CD_DIFF+vy;
		int fromX = lx/Data.CHIP_SIZE;
		int toX = (rx+vx)/Data.CHIP_SIZE;
		int fromY = uy/Data.CHIP_SIZE;
		int toY = dy/Data.CHIP_SIZE;
		for(int i = fromX; i <= toX; i++){
			if(i >= mapData.col) return mapData.col*Data.CHIP_SIZE-s.getWidth()+Data.CD_DIFF;
			for(int j = fromY; j <= toY; j++){
				if(i<0||j<0||j>=mapData.row) continue;
				if(mapData.pass[j][i] == 1){
					return i*Data.CHIP_SIZE-s.getWidth()+Data.CD_DIFF;
				}
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
				if(tmp instanceof Player && !Data.player.landing()){
					Data.player.fall();
				}
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
	public int spriteHit(Sprite s1, Sprite s2){
		int x1l = s1.getX();
		int x2l = s2.getX();
		int x1r = x1l + s1.getWidth();
		int x2r = x2l + s2.getWidth();
		int y1u = s1.getY();
		int y2u = s2.getY();
		int y1d = y1u + s1.getHeight();
		int y2d = y2u + s2.getHeight();
		x1l += Data.CD_DIFF;
		x1r -= Data.CD_DIFF;
		y1u += Data.CD_DIFF;
		y1d -= Data.CD_DIFF;
		if(x1l <= x2r && x1r >= x2l && y1u <= y2d && y1d >= y2u){
			return 16;
		}
		return 0;
	}
	public int hitUp(Sprite s1, Sprite s2){
		int v1 = s1.getVy();
		int v2 = s2.getVy();
		int y1b = s1.getY()+Data.CD_DIFF;
		int y2b = s2.getY()+s2.getHeight();
		int y1a = y1b+v1;
		int y2a = y2b+v2;
		if(v1-v2 >= 0) return Integer.MIN_VALUE;
		if((y1b-y2b)*(y1a-y2a) > 0) return Integer.MIN_VALUE;
		double touch = (double)(v1*y2b-v2*y1b)/(double)(v1-v2);
		double ratio;
		if(v1!=0){
			ratio = (double)(touch-y1b)/(double)v1;
		}else{
			ratio = (double)(touch-y2b)/(double)v2;
		}
		double x1l = s1.getX();
		double x1r = x1l+s1.getWidth();
		double vx1 = s1.getVx();
		double x2l = s2.getX();
		double x2r = x2l+s2.getWidth();
		double vx2 = s2.getVx();
		x1l += vx1 * ratio;
		x1r += vx1 * ratio;
		x2l += vx2 * ratio;
		x2r += vx2 * ratio;
		if(x1l+Data.CD_DIFF < x2r && x1r-Data.CD_DIFF > x2l){
			return (int)(touch);
		}else{
			return Integer.MIN_VALUE;
		}
	}
	public int hitDown(Sprite s1, Sprite s2){
		int v1 = s1.getVy();
		int v2 = s2.getVy();
		int y1b = s1.getY()+s1.getHeight()-Data.CD_DIFF-1;
		int y2b = s2.getY();
		int y1a = y1b+v1;
		int y2a = y2b+v2;
		if(v1-v2 <= 0) return Integer.MIN_VALUE;
		if((y1b-y2b)*(y1a-y2a) > 0) return Integer.MIN_VALUE;
		double touch = (double)(v1*y2b-v2*y1b)/(double)(v1-v2);
		double ratio;
		if(v1!=0){
			ratio = (double)(touch-y1b)/(double)v1;
		}else{
			ratio = (double)(touch-y2b)/(double)v2;
		}
		double x1l = s1.getX();
		double x1r = x1l+s1.getWidth();
		double vx1 = s1.getVx();
		double x2l = s2.getX();
		double x2r = x2l+s2.getWidth();
		double vx2 = s2.getVx();
		x1l += vx1 * ratio;
		x1r += vx1 * ratio;
		x2l += vx2 * ratio;
		x2r += vx2 * ratio;
		if(x1l+Data.CD_DIFF < x2r && x1r-Data.CD_DIFF > x2l){
			return (int)(touch);
		}else{
			return Integer.MIN_VALUE;
		}
	}
	public int hitLeft(Sprite s1, Sprite s2){
		int v1 = s1.getVx();
		int v2 = s2.getVx();
		int x1b = s1.getX()+Data.CD_DIFF;
		int x2b = s2.getX()+s2.getWidth();
		int x1a = x1b+v1;
		int x2a = x2b+v2;
		if(v1-v2 >= 0) return Integer.MIN_VALUE;
		if((x1b-x2b)*(x1a-x2a) > 0) return Integer.MIN_VALUE;
		double touch = (double)(v1*x2b-v2*x1b)/(double)(v1-v2);
		double ratio;
		if(v1!=0){
			ratio = (double)(touch-x1b)/(double)v1;
		}else{
			ratio = (double)(touch-x2b)/(double)v2;
		}
		double y1u = s1.getY();
		double y1d = y1u+s1.getHeight();
		double vy1 = s1.getVy();
		double y2u = s2.getY();
		double y2d = y2u+s2.getHeight();
		double vy2 = s2.getVy();
		y1u += vy1 * ratio;
		y1d += vy1 * ratio;
		y2u += vy2 * ratio;
		y2d += vy2 * ratio;
		if(y1u+Data.CD_DIFF < y2d && y1d-Data.CD_DIFF > y2u){
			return (int)(touch);
		}else{
			return Integer.MIN_VALUE;
		}
	}
	public int hitRight(Sprite s1, Sprite s2){
		int v1 = s1.getVx();
		int v2 = s2.getVx();
		int x1b = s1.getX()+s1.getWidth()-Data.CD_DIFF-1;
		int x2b = s2.getX();
		int x1a = x1b+v1;
		int x2a = x2b+v2;
		if(v1-v2 <= 0) return Integer.MIN_VALUE;
		if((x1b-x2b)*(x1a-x2a) > 0) return Integer.MIN_VALUE;
		double touch = (double)(v1*x2b-v2*x1b)/(double)(v1-v2);
		double ratio;
		if(v1!=0){
			ratio = (double)(touch-x1b)/(double)v1;
		}else{
			ratio = (double)(touch-x2b)/(double)v2;
		}
		double y1u = s1.getY();
		double y1d = y1u+s1.getHeight();
		double vy1 = s1.getVy();
		double y2u = s2.getY();
		double y2d = y2u+s2.getHeight();
		double vy2 = s2.getVy();
		y1u += vy1 * ratio;
		y1d += vy1 * ratio;
		y2u += vy2 * ratio;
		y2d += vy2 * ratio;
		if(y1u+Data.CD_DIFF < y2d && y1d-Data.CD_DIFF > y2u){
			return (int)(touch);
		}else{
			return Integer.MIN_VALUE;
		}
	}
	/**
	 * �X�v���C�g���m�̃X�v���C�g�̏Փ˔���
	 */
	public boolean spriteAndSpriteHit(Sprite s1, Sprite s2){
		int[] hitPosition = new int[4];
		int[] hitPosition1 = new int[4];
		int[] hitPosition2 = new int[4];
		int[] diff = new int[4];
		hitPosition[Sprite.UP] = hitUp(s1,s2);
		hitPosition[Sprite.DOWN]  = hitDown(s1,s2);
		hitPosition[Sprite.LEFT]  = hitLeft(s1,s2);
		hitPosition[Sprite.RIGHT]  = hitRight(s1,s2);
		int hitDir = spriteHit(s1,s2);
		for(int i = 0; i < 4; i++){
			if(hitPosition[i] != Integer.MIN_VALUE){
				hitDir = hitDir | (1 << i);
				hitPosition1[i] = hitPosition[i];
			}else{
				hitPosition1[i] = Integer.MIN_VALUE;
			}
		}
		if(hitDir > 0){
			hitPosition2[Sprite.UP] = hitPosition[Sprite.DOWN];
			hitPosition2[Sprite.DOWN] = hitPosition[Sprite.UP];
			hitPosition2[Sprite.LEFT] = hitPosition[Sprite.RIGHT];
			hitPosition2[Sprite.RIGHT] = hitPosition[Sprite.LEFT];
			hitPosition1[Sprite.UP] -= Data.CD_DIFF;
			hitPosition1[Sprite.LEFT] -= Data.CD_DIFF;
			hitPosition1[Sprite.DOWN] -= s1.getHeight()-Data.CD_DIFF;
			hitPosition1[Sprite.RIGHT] -= s1.getWidth()-Data.CD_DIFF;
			hitPosition2[Sprite.UP] -= Data.CD_DIFF;
			hitPosition2[Sprite.LEFT] -= Data.CD_DIFF;
			hitPosition2[Sprite.DOWN] -= s2.getHeight()-Data.CD_DIFF;
			hitPosition2[Sprite.RIGHT] -= s2.getWidth()-Data.CD_DIFF;
			if(s1 instanceof Player) s2.touch(s1,hitDir,hitPosition1);
			else if(s1 instanceof Weapon) s2.attacked(s1);
			else{
				s2.touch(s1,hitDir,hitPosition1);
				hitDir = hitDir & 16;
				for(int i = 0; i < 4; i++){
					if(hitPosition2[i] != Integer.MIN_VALUE) hitDir = hitDir | (1 << i);
				}
				s1.touch(s2,hitDir,hitPosition2);
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
