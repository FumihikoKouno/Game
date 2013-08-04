/**
 * �Q�[���v���C��ʂ̊��N���X
 * �Q�[���v���C�̊�{����̊֐��Ƃ�
 * �����ȃ��[�h�ɋ��ʂ̕ϐ����`���Ă���
 * ���̃N���X���p�����邱�ƂŔ�r�I�ȒP�ɂ����ȃ��[�h���ł���
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import java.util.Random;

import javax.swing.ImageIcon;

public class Field{
	
	public Field(){}
	/**
	 * �Q�[���I�[�o�[�ɂȂ����u�Ԃɐݒ肳���ϐ�
	 * ���O���͂Ƃ��^�C�g���ɖ߂�Ƃ��̃t���[�����ɂȂ�
	 */
	protected int gameOverFrame;
	// �G�t�F�N�g���X�g
	protected ArrayList<Effect> effect = new ArrayList<Effect>();
	// �Q�[���J�n�̏u�Ԃ̃t���[��
	protected int startFrame;
	// �X�N���[�������u�Ԃ̃t���[��
	protected int scrollFrame;
	// �}�E�X����ł���ł���p�l���A�����x�Ay���W
	protected int pressingPanelX;
	protected int pressingPanelY;
	private Panel pressingPanel;
	// �p�l������ւ��L�[��space��������Ă��邩�ǂ���
	private boolean cursorReleased;
	// �����p�ϐ�
	protected Random random = new Random();
	// �t�B�[���h�̃p�l���z��
	protected Panel[][] panel = new Panel[Data.ROW][Data.COL];
	// �J�[�\��
	protected Cursor cursor;
	// �V���ɏo�Ă�����̃p�l��
	protected Panel[] newLine = new Panel[Data.COL];
	// �}�E�X�̃{�^����������Ă��邩�ǂ���
	private boolean mouseReleased;
	// ���g���C�{�^����������Ă��邩�ǂ���
	private boolean retryReleased;
	// �^�C�g���ɖ߂�{�^����������Ă��邩�ǂ���
	private boolean toTitleReleased;
	// ���g���C�֐�
	public void retry(){
		KeyStatus.setAll(false);
		init();
	}
	// ���t���[�����Ƃ�update�֐�
	public void update(){
		// �Q�[���J�n�O�������牽�����Ȃ�
		if(startFrame > Data.frame) return;
		// ���g���C�L�[��������Ă���΃t���O�Z�b�g
		if(!KeyStatus.retry) retryReleased = true;
		// ���g���C�L�[���O�t���[���ł͗�����Ă��āA���t���[���ŉ����ꂽ�ꍇ���g���C����
		if(retryReleased && KeyStatus.retry){
			retryReleased = false;
			retry();
			return;
		}
		// �^�C�g���ɂ��Ă����g���C���l
		if(!KeyStatus.toTitle) toTitleReleased = true;
		if(toTitleReleased && KeyStatus.toTitle){
			toTitleReleased = false;
			Data.gameStatus = Data.TITLE;
			Data.keyCansel = false;
			Data.mouseCansel = false;
			return;
		}
		// �Q�[���I�[�o�[���茓�Q�[���I�[�o�[�����֐����Ă�
		gameOver();
		// �Q�[���I�[�o�[�t���[����0�o�Ȃ��ꍇ�Q�[���I�[�o�[�������ƂɂȂ�̂ňȍ~�̏����͂��Ȃ�
		if(gameOverFrame != 0) return;
		// �J�[�\����x�Ay���W��ێ����Ă���
		int ty = cursor.getY();
		int tx = cursor.getX();
		// ����グ���x���̓X�R�A/1000(�����ȉ��؂�̂�)�Ƃ���
		Data.lv = Data.score/1000;
		// ����グ���x���̍ő��9
		if(Data.lv > 9) Data.lv = 9;
		if(!Data.mousePressed){
			// �}�E�X�̃{�^����������Ă��Ȃ���΃L�[�{�[�h�ɂ��J�[�\�����쏈��
			
			// �����}�E�X�������Ă���p�l��������΂���𗣂�
			if(pressingPanel != null) releasePanel();
			// �}�E�X�{�^�������������Ƃ������Ă���
			mouseReleased = true;
			// �J�[�\����������Ă���L�[�ɑΉ����ē�����
			cursor.move();
			// ����ւ��L�[�𗣂����t���O����
			if(!KeyStatus.change) cursorReleased = true;
			// �O�t���[���ɓ���ւ��L�[��������Ă��č��t���[���œ���ւ��L�[��������Ă���΃p�l�������ւ���
			if(KeyStatus.change && cursorReleased) swapping();
		}else{
			// �}�E�X�̃{�^����������Ă����ꍇ�̓}�E�X�ɂ��J�[�\�����쏈��
			
			// �O�t���[���Ƀ}�E�X�{�^����������Ă�����p�l�������ޏ���
			if(mouseReleased){
				pressPanel();
				mouseReleased = false;
				cursor.set(Data.pressedX,Data.pressedY);
			}
			cursor.setX(Data.pressedX);
			// ����ł���p�l��������A���ꂪ�����Ă��邩�A������p�l���������炻��𗣂�
			if(pressingPanel != null && (pressingPanel.isDeleting() || pressingPanel != panel[pressingPanelY][pressingPanelX])) releasePanel();
			// ����ȊO�̓p�l���𓮂�������
			else panelMove();
		}
		// ����グ
		scroll();
		// �t�B�[���h�ɂ��邷�ׂẴp�l����update
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] != null) panel[i][j].update();
			}
		}
		// �G�t�F�N�g��update
		for(int i = 0; i < effect.size(); i++){
			effect.get(i).update();
		}
		// ������p�l���Ə�����p�l���̃t���O���Z�b�g
		setFallingFlag();
		setDeleteFlag();
		// ���ۂɃp�l���𗎂Ƃ�����A�������肷�鏈��
		fallPanels();
		deletePanels();
		// �p�l�������S�ɏ���
		endPanels();
		// �G�t�F�N�g������
		deleteEffect();
		// �S�Ă̏�����̃J�[�\���Ɉʒu�𓾂�
		int cx = cursor.getX();
		int cy = cursor.getY();
		// �J�[�\���̈ʒu�������Ă�����replay�p�ɋL�^
		if(ty != cy || tx != cx){
			Data.replayCursorFrame.add(new Integer(Data.frame-startFrame));
			Data.replayCursorX.add(new Integer(cx));
			Data.replayCursorY.add(new Integer(cy));
		}
		// �ő�A�����X�V
		if(Data.maxChain < Data.chain) Data.maxChain = Data.chain;
		// �A�����؂ꂽ��A������0�ɂ���
		if(chainResetable()) Data.chain = 0;
	}
	
	// �}�E�X�ɂ���ăp�l���𓮂����֐�
	private void panelMove(){
		// �������ǂ����̃t���O�Ƃǂ����ɓ������̃t���O
		boolean movable = true;
		boolean left = true;
		int x = -1;
		int y = -1;
		int newOffset = 0;
		// ����ł���p�l�����Ȃ�������A�ړ�����������A�}�E�X�̎w��x�ɕω����Ȃ������珈�����I��
		if(pressingPanel == null) return;
		if(pressingPanel.cMoving()) return;
		if(pressingPanelX == Data.pressedX) return;
		// ����ł���p�l�����E�ɓ����ꍇ
		if(pressingPanelX < Data.pressedX){
			left = false;
			x = pressingPanelX + 1;
			y = pressingPanelY;
			// newOffset�͈ړ�����p�l���ɐݒ肷�鉡�ړ��̃I�t�Z�b�g
			newOffset = -Data.PANEL_SIZE;
			// �ړ���p�l�������݂��A���̃p�l�������ړ���������Ă���Œ��Ȃ�ړ��s�Ƃ���
			if(panel[y][x] != null && (panel[y][x].cMoving() || panel[y][x].isDeleting())) movable = false;
		}
		// ����ł���p�l�������ɓ����ꍇ
		if(pressingPanelX > Data.pressedX){
			left = true;
			x = pressingPanelX - 1;
			y = pressingPanelY;
			// newOffset�͈ړ�����p�l���ɐݒ肷�鉡�ړ��̃I�t�Z�b�g
			newOffset = Data.PANEL_SIZE;
			// �ړ���p�l�������݂��A���̃p�l�������ړ���������Ă���Œ��Ȃ�ړ��s�Ƃ���
			if(panel[y][x] != null && (panel[y][x].cMoving() || panel[y][x].isDeleting())) movable = false;
		}
		// �ړ��\�Ȃ�ړ�����
		if(movable){
			// ���ړ��ƉE�ړ��ŕς���
			if(left) swapPanel(x,y);
			else swapPanel(pressingPanelX,pressingPanelY);
			// ����ł���p�l����x�Ay���W���X�V
			pressingPanelX = x;
			pressingPanelY = y;
		}
	}
	
	// �}�E�X�Ńp�l���𗣂��֐�
	private void releasePanel(){
		pressingPanel = null;
		pressingPanelX = -1;
		pressingPanelY = -1;
	}
	
	// �}�E�X�Ńp�l�������ފ֐�
	private void pressPanel(){
		int x = Data.pressedX;
		int y = Data.pressedY;
		if(x < 0 || x >= Data.COL) return;
		if(y < 0 || y >= Data.ROW) return;
		// ���������p�l�����Ȃ�������A���ړ�����������A�����Ă���Œ��Ȃ���߂Ȃ�
		if(panel[y][x] == null || panel[y][x].cMoving() || panel[y][x].isDeleting()) return;
		pressingPanel = panel[Data.pressedY][Data.pressedX];
		pressingPanelX = x;
		pressingPanelY = y;		
	}

	// �G�t�F�N�g�폜�֐�
	private void deleteEffect(){
		int i = 0;
		while(i < effect.size()){
			if(effect.get(i).ended()) effect.remove(i);
			else i++;
		}
	}
	
	// gameover�p�֐��A�e���[�h�Ŏ���
	protected void gameOver(){assert(false);}
	
	// �A���������Z�b�g����邩�ǂ���
	private boolean chainResetable(){
		// �����Ă���p�l��������A�A����1�ȉ��Ȃ�A������1�Ƃ���
		if(deletePanelExist()){
			if(Data.chain <= 1){
				Data.chain = 1;
				return false;
			}
		}
		// �A�����̃p�l�����Ȃ���ΘA���̓��Z�b�g�����
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				if(panel[i][j].getConnected() != 0) return false;
			}
		}
		return true;
	}

	// �V���ɏo������p�l���̃��C�������
	protected void createNewLine(){
		int tmp;
		int count;
		for(int i = 0; i < Data.COL; i++){
			while(true){
				count = 1;
				tmp = random.nextInt(Data.PANEL_NUMBER*Data.hard);
				for(int j = i-1; j >= 0; j--){
					if(tmp == newLine[j].getKind()) count++;
					else break;
				}
				for(int j = Data.ROW-1; j >= 0; j--){
					if(panel[j][i] != null && panel[j][i].getKind() == tmp) count++;
					else break;
				}
				if(count < 3) break;
			}
			newLine[i] = new Panel(tmp);
		}
	}

	// �t�B�[���h�̈�ԏ�Ƀp�l�������邩
	protected boolean topExist(){
		for(int i = 0; i < Data.COL; i++){
			if(panel[0][i] != null) return true;
		}
		return false;
	}
	
	// �p�l���𗎂Ƃ��֐�
	private void fallPanels(){
		for(int i = Data.ROW-1; i >= 0; i--){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				// ������t���O�������Ă��āA���̃p�l�����c�ړ��̃I�t�Z�b�g��0�ŏ����Ă��Ȃ��A�����Ȃ���Η�����(�I�t�Z�b�g��ݒ�)
				if(panel[i][j].isFalling()){
					if(!panel[i][j].rMoving() && !panel[i][j].isDeleting() && panel[i+1][j] == null){
						panel[i+1][j] = panel[i][j];
						panel[i][j].setOffset(0,-Data.PANEL_SIZE);
						panel[i][j] = null;
					}
				}
			}
		}
	}

	// �J�[�\���̈ʒu�̃p�l�������ւ���֐�
	private void swapping(){
		cursorReleased = false;
		int x = cursor.getX();
		int y = cursor.getY();
		boolean leftMovable = true;
		boolean rightMovable = true;
		// �J�[�\���̍��������݂����ړ����Ă�����A�����Ă����肵���瓮�����Ȃ�
		if(panel[y][x] != null && (panel[y][x].cMoving() || panel[y][x].isDeleting())) leftMovable = false;
		// �J�[�\���̉E�������݂����ړ����Ă�����A�����Ă����肵���瓮�����Ȃ�
		if(panel[y][x+1] != null && (panel[y][x+1].cMoving() || panel[y][x+1].isDeleting())) rightMovable = false;
		// �J�[�\���̍��A�E�̗����𓮂�����ꍇ������
		if(leftMovable && rightMovable){
			swapPanel(x,y);
		}
	}
	
	// ����ւ���p�l���̍����̍��W�������ɂƂ�p�l�������ۂɓ���ւ���֐�
	protected void swapPanel(int x, int y){
		// ����ւ������Ƃ��L��
		Data.replaySwapFrame.add(new Integer(Data.frame-startFrame));
		Data.replaySwapX.add(new Integer(x));
		Data.replaySwapY.add(new Integer(y));
		// �����Ƀp�l�����Ȃ���Ώ����I��
		if(panel[y][x] == null && panel[y][x+1] == null) return;
		// �������Ȃ���ΉE�𓮂���
		if(panel[y][x] == null){
			panel[y][x+1].setOffset(Data.PANEL_SIZE,0);
			panel[y][x] = panel[y][x+1];
			panel[y][x+1] = null;
			return;
		}
		// �E�����Ȃ���΍��𓮂���
		if(panel[y][x+1] == null){
			panel[y][x].setOffset(-Data.PANEL_SIZE,0);
			panel[y][x+1] = panel[y][x];
			panel[y][x] = null;
			return;
		}
		// ����ȊO�͓���ւ���
		panel[y][x].setOffset(-Data.PANEL_SIZE,0);
		panel[y][x+1].setOffset(Data.PANEL_SIZE,0);
		Panel tmp = panel[y][x];
		panel[y][x] = panel[y][x+1];
		panel[y][x+1] = tmp;
	}

	// �������������͗�������t���O�̗����Ă���p�l�������邩�ǂ���
	private boolean fallingPanelExist(){
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				if(panel[i][j].isFalling()) return true;
			}
		}
	return false;
	}

	// �A�����̃p�l�������邩�ǂ���
	private boolean connectPanelExist(){
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				if(panel[i][j].getConnected() != 0) return true;
			}
		}
		return false;
	}
	
	// ���ړ����̃p�l�������邩�ǂ���
	private boolean movingPanelExist(){
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				if(panel[i][j].cMoving()) return true;
			}
		}
		return false;
	}
	
	// �X�N���[�����~�܂��true
	private boolean scrollStop(){
		// �Q�[���I�[�o�[�Ȃ�~�܂�
		if(gameOverFrame != 0) return true;
		// �ŏ�̃p�l��������X�N���[���I�t�Z�b�g��0�łȂ��Ȃ�~�܂�(������Q�[���I�[�o�[�ɂȂ���)
		if(topExist() && Data.scrollOffset != 0) return true;
		// �X�N���[���{�^����������Ă���ꍇ
		if(KeyStatus.scroll){
			// �����Ă���p�l����������������Ă���p�l��������Ƃ��ɍŏ�̃p�l�������݂���Ύ~�܂�
			if((fallingPanelExist() || connectPanelExist() || deletePanelExist()) && topExist()) return true;
			// ����ȊO�͓���
			else return false;
		}
		//�X�N���[���{�^����������Ă��Ȃ���
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				// �����Ă���p�l�����������藎����p�l��������Ǝ~�܂�
				if(panel[i][j].isDeleting()) return true;
				if(isFallable(j,i)) return true;
				if(panel[i][j].isFalling()) return true;
			}
		}
		return false;
	}
	
	
	// �����Ă���p�l�������邩�ǂ���
	protected boolean deletePanelExist(){
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				if(panel[i][j].isDeleting()) return true;
			}
		}
		return false;
	}
	
	// �V�������C�����t�B�[���h�ɏo��������֐�(�܂�ʏ�̐F�ɂ���֐�)
	protected void appearNewLine(){
		// �X�N���[���ɍ��킹�ăJ�[�\�����グ��
		cursor.moveUp();
		pressingPanelY -= 1;
		for(int i = 1; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
			panel[i-1][j] = panel[i][j];
			}
		}
		for(int i = 0; i < Data.COL; i++){
			panel[Data.ROW-1][i] = newLine[i];
		}
		// �V���ȃ��C�������
		createNewLine();
	}

	// �X�N���[���֐�
	protected void scroll(){
		// �X�N���[�����~�܂�Ȃ炱�̃t���[���ŃX�N���[�������������Ƃɂ��A���Ԃ����Z�b�g
		if(scrollStop()){
			scrollFrame = Data.frame;
			return;
		}
		// �O�̃X�N���[�������莞�Ԍo�����班����ɂ�����
		if(scrollFrame + (10 - Data.lv) * 2 <= Data.frame || KeyStatus.scroll){
			// ���v���C�p�L�^
			Data.replayScrollFrame.add(new Integer(Data.frame-startFrame));
			// �I�t�Z�b�g����
			Data.scrollOffset = (Data.scrollOffset + Data.SCROLL_UNIT*Data.hard) % Data.PANEL_SIZE;
			// ��񕪋���������V���ȃ��C�����o��������
			if(Data.scrollOffset == 0){
				appearNewLine();
			}
			// �X�N���[�������t���[����ۑ�
			scrollFrame = Data.frame;
		}
	}
	
	// �p�l�������S�ɏ���
	private boolean endPanels(){
		boolean connection;
		for(int j = 0; j < Data.COL;j++){
			connection = false;
			for(int i = Data.ROW-1; i >= 0; i--){
				if(panel[i][j] == null){
					connection = false;
					continue;
				}
				// �p�l������������A���t���O�𗧂Ă��̃p�l��������
				if(panel[i][j].end()){
					connection = true;
					panel[i][j] = null;
				}else{
					// �p�l���������ĘA���t���O������ΘA���ݒ������
					if(connection) panel[i][j].setConnected(Data.frame);
				}
			}
		}
		// �Ȃ�true��Ԃ��Ă��邩�o���Ă��Ȃ��A�����d�l�ύX�ɂ����́B���͎g���Ă��Ȃ��͂�
		return true;
	}
	
	// �p�l���̕\��������(���ۂ͈ړ��ł��Ȃ�����̃p�l���͗����Ȃ���Ԃɂ���)
	private void deletePanels(){
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				if(panel[i][j].isDeleted()){
					if(panel[i][j].getKind() != -1){
						panel[i][j].setKind(-1);
						Data.score += 10;
					}
				}
			}
		}
	}
	
	// ������
	public void init(){
		Data.replayScrollFrame.clear();
		Data.replaySwapFrame.clear();
		Data.replaySwapX.clear();
		Data.replaySwapY.clear();
		Data.replayCursorFrame.clear();
		Data.replayCursorX.clear();
		Data.replayCursorY.clear();
		gameOverFrame = 0;
		cursor = new Cursor(Data.INIT_CURSOR_X,Data.INIT_CURSOR_Y);
		cursor.setLoopAble(false);
		Data.cursorMaxX = Data.COL-2;
		Data.cursorMaxY = Data.ROW-1;
		pressingPanel = null;
		pressingPanelX = -1;
		pressingPanelY = -1;
		mouseReleased = true;
		Data.score = 0;
		Data.chain = 0;
		Data.maxDelete = 0;
		Data.maxChain = 0;
		scrollFrame = Data.frame;
		cursorReleased = true;
		toTitleReleased = false;
		retryReleased = false;
		Data.scrollOffset = 0;
	}
	
	// �Ή�����x�Ay���W�̃p�l����������p�l�����ǂ���
	protected boolean isDeletable(int x, int y){
		if(panel[y][x].isFalling() || isFallable(x,y) || panel[y][x].isDeleting() || panel[y][x].cMoving()) return false;
		int count_row = 1;
		int count_col = 1;
		int myKind = panel[y][x].getKind();
		for(int u = y-1; u >= 0; u--){
			if(panel[u][x] == null) break;
			if(panel[u][x].isFalling() || isFallable(x,u) || panel[u][x].isDeleting() || panel[u][x].cMoving()) break;
			if(panel[u][x].getKind() != myKind) break;
			else count_row++;
		}
		for(int d = y+1; d < Data.ROW; d++){
			if(panel[d][x] == null) break;
			if(panel[d][x].isFalling() || isFallable(x,d) || panel[d][x].isDeleting() || panel[d][x].cMoving()) break;
			if(panel[d][x].getKind() != myKind) break;
			else count_row++;
		}
		for(int l = x-1; l >= 0; l--){
			if(panel[y][l] == null) break;
			if(panel[y][l].isFalling() || isFallable(l,y) || panel[y][l].isDeleting() || panel[y][l].cMoving()) break;
			if(panel[y][l].getKind() != myKind) break;
			else count_col++;
		}
		for(int r = x+1; r < Data.COL; r++){
			if(panel[y][r] == null) break;
			if(panel[y][r].isFalling() || isFallable(r,y) || panel[y][r].isDeleting() || panel[y][r].cMoving()) break;
			if(panel[y][r].getKind() != myKind) break;
			else count_col++;
		}
		// 3�ȏ�Ȃ����Ă�Ώ�����p�l���Ȃ̂�true
		if(count_row>=3 || count_col>=3) return true;
		else return false;
	}
	
	// ������t���O�𗧂Ă�
	private void setDeleteFlag(){
		boolean first = true;
		boolean firstChain = true;
		int minI = Data.ROW;
		int minJ = Data.COL;
		int count = 0;
		int max = 0;
		int tmp = 0;
		boolean[][] flag = new boolean[Data.ROW][Data.COL];
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null){
					flag[i][j] = false;
					continue;
				}
				if(isDeletable(j,i)){
					if(minI > i) minI = i;
					if(minJ > j) minJ = j;
					flag[i][j] = true;
					max++;
				}else{
					flag[i][j] = false;
				}
			}
		}
		// �����������̍X�V
		if(Data.maxDelete < max) Data.maxDelete = max;
		count = max-1;
		for(int i = Data.ROW-1; i >= 0; i--){
			for(int j = Data.COL-1; j >= 0; j--){
				// ������z�t���O����������
				if(flag[i][j]){
					// ������G�t�F�N�g���Z�b�g
					effect.add(new Effect(Data.DELETE_EFFECT,j,i,panel[i][j].getKind()));
					// ���������̃G�t�F�N�g�ƃ|�C���g�ǉ�
					if(first && max > 3){
						effect.add(new Effect(Data.SAME_EFFECT,minJ,minI,max));
						Data.score += max*max*5;
					}
					// �A���̃G�t�F�N�g�ƃ|�C���g�ǉ�
					if(firstChain && panel[i][j].getConnected() != 0 && Data.chain > 0){
						firstChain = false;
						Data.chain++;
						effect.add(new Effect(Data.CHAIN_EFFECT,minJ,minI,Data.chain));
						Data.score += Data.chain*Data.chain*10;
					}
					// �ŏ��̃t���O������(�|�C���g�ǉ��Ƃ��G�t�F�N�g���d�������Ȃ�����)
					first = false;
					if(Data.chain == 0) Data.chain = 1;
					// �������t���[�����Z�b�g(���Ԃɏ�����悤�ɂ��邽��count�Ƃ�max�Ƃ����g��)
					panel[i][j].setDeleteFrame(count,max-1);
					count--;
				}else{
					// �����Ȃ��p�l���̏ꍇ�ŘA���t���O�������Ă����ꍇ
					if(panel[i][j] != null && panel[i][j].getConnected() < 0){
						// �ړ����Ă��Ȃ��p�l���ł���
						if(!panel[i][j].cMoving() && !panel[i][j].rMoving()){
							// �ŉ��ɂ������艺�̃p�l���̘A���t���O���Ȃ�������A�����Ă���Œ������������ĂȂ��������������Ă���Œ��łȂ���ΘA���t���O�����Z�b�g
							if((i+1==Data.ROW || (panel[i+1][j] != null && (panel[i+1][j].getConnected() == 0 || panel[i+1][j].isDeleting()))) && !panel[i][j].isDeleting()){
								panel[i][j].setConnected(0);
							}
						}
					}
				}
			}
		}
	}
	
	// x�Ay���W����������p�l�����ǂ���
	protected boolean isFallable(int x, int y){
		int tmp = panel[y][x].getConnected();
		// ��ԉ��̃p�l���ɂ���
		if(y == Data.ROW-1){
			// �������������Ă���Œ��Ȃ�true
			if(panel[Data.ROW-1][x].rMoving()) return true;
			else{
				// �A���t���O�������Ă���Β��n�����u�Ԃ̃t���[�����}�C�i�X�ŃZ�b�g
				if(tmp != 0) panel[y][x].setConnected(-Data.frame);
				return false;
			}
		}else{
			// ���̃Q�[���ł̓p�l���͎΂߂ɓ������Ƃ͖����̂ŁA���ɓ����Ă���Η����Ă��Ȃ�
			if(panel[y][x].cMoving()){
				// �A���t���O�������Ă���Β��n�����u�Ԃ̃t���[�����}�C�i�X�ŃZ�b�g
				if(tmp != 0) panel[y][x].setConnected(-Data.frame);
				return false;
			}
			// �������������Ă���Œ��Ȃ�true
			if(panel[y][x].rMoving()){
				return true;
			}
			// ���̃p�l�������݂��Ȃ���Η�����̂�true
			if(panel[y+1][x] == null){
				return true;
			}
			boolean ret = panel[y+1][x].isFalling();
			// ���̃p�l����������p�l���ŘA���t���O�������Ă���Β��n�����u�Ԃ̃t���[�����}�C�i�X�ŃZ�b�g
			if(!ret && tmp != 0) panel[y][x].setConnected(-Data.frame);
			// ���̃p�l���̗����锻��Ɠ����ɂȂ�
			return ret;
		}
	}
	
	// ������t���O���Z�b�g
	public void setFallingFlag(){
		int tmp = 0;
		for(int i = Data.ROW-1; i>=0; i--){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j]==null) continue;
				// �����ɓ���tmp�͉��̃p�l�������S�ɏ������Ƃ��̃t���[����(�����ĂȂ����0)
				tmp = panel[i][j].getConnected();
				// ������p�l���ł����̃p�l�������S�ɏ����Ă����莞�Ԍo�߂��Ă���Η�����t���O�𗧂Ă�
				panel[i][j].setFalling(isFallable(j,i) && (tmp == 0 || (tmp+Data.DELETE_RAG/Data.hard<=Data.frame)));
			}
		}
	}
	
	// ���낢��`��
	public void draw(Graphics g){
		if(gameOverFrame == 0){
			for(int i = 0; i < Data.ROW; i++){
				for(int j = 0; j < Data.COL; j++){
					if(panel[i][j] == null) continue;
					panel[i][j].draw(g,j,i);
				}
			}
			for(int i = 0; i < Data.COL; i++){
				newLine[i].draw(g,i,Data.ROW);
			}
			cursor.draw(g,Data.ENDLESS);
		}
		if(Data.hard==1){
			g.drawImage(Data.image.fieldImage,0,0,Data.WIDTH*Data.zoom,Data.HEIGHT*Data.zoom,0,0,Data.WIDTH,Data.HEIGHT,null);
		}else if(Data.hard==2){
			g.drawImage(Data.image.hardFieldImage,0,0,Data.WIDTH*Data.zoom,Data.HEIGHT*Data.zoom,0,0,Data.WIDTH,Data.HEIGHT,null);
		}
		for(int i = 0; i < effect.size(); i++){
			effect.get(i).draw(g);
		}
		int space_x = 20;
		int space_y = 7;
		Data.setFont(g,Data.SCORE_FONT);
		g.drawString(Data.score+"",(Data.SCORE_X+space_x)*Data.zoom,(Data.SCORE_Y-space_y)*Data.zoom);
		g.drawString(Data.lv+"",(Data.LV_X+space_x)*Data.zoom,(Data.LV_Y-space_y)*Data.zoom);
		g.drawString(Data.maxChain+"",(Data.MAX_CHAIN_X+space_x)*Data.zoom,(Data.MAX_CHAIN_Y-space_y)*Data.zoom);
		g.drawString(Data.maxDelete+"",(Data.MAX_DELETE_X+space_x)*Data.zoom,(Data.MAX_DELETE_Y-space_y)*Data.zoom);
		if(gameOverFrame != 0){
			g.drawString("Game Over",(Data.WIDTH/2-100)*Data.zoom,(Data.HEIGHT/2)*Data.zoom);
		}
	}
}
