/**
 * �X�e�[�W�N���A�p�N���X
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;

public class StageClear extends Field{
	
	public StageClear(){}
	// �N���A���C���̏ꏊ�������ϐ�
	private int clearLine;
	// �N���A���C���\���̂��߂ɂ���オ�������C�������o���Ă����ϐ�
	private int upNo;
	
	// �Q�[���I�[�o�[�֐�
	protected void gameOver(){
		boolean tmp = true;
		// �N���A���C�����o�����Ă�����
		if(clearLine >= 0){
			for(int i = 0; i < clearLine; i++){
				for(int j = 0; j < Data.COL; j++){
					if(panel[i][j] != null) tmp = false;
				}
			}
			// �S�Ẵp�l�����N���A���C���ȉ��ŁA�����Ă���p�l�����Ȃ����
			if(tmp && !deletePanelExist()){
				for(int i = 0; i < Data.ROW; i++){
					for(int j = 0; j < Data.COL; j++){
						if(panel[i][j] == null) continue;
						if(isFallable(j,i)) tmp = false;
					}
				}
				// �X�ɗ����Ă���p�l�����Ȃ����
				if(tmp){
					// �Q�[���I�[�o�[�\���p�̗P�\���Ԃ����A�L�[�ƃ}�E�X���L�����Z������悤�ɂ���
					if(gameOverFrame == 0){
						gameOverFrame = Data.frame + 60;
						Data.keyCansel = true;
						Data.mouseCansel = true;
					}
					// ���Ԃ����Ă΃X�R�A���L�^���ă^�C�g���ɖ߂�
					if(gameOverFrame != 0 && gameOverFrame <= Data.frame){
						new ScoreIO().output();
						Data.gameStatus = Data.TITLE;
						Data.keyCansel = false;
						Data.mouseCansel = false;
					}
				}
			}
		}
		// �p�l������ɂ����ăQ�[���I�[�o�[�̏ꍇ
		if(topExist() && Data.scrollOffset != 0){
			// �P�\���ԍ쐬
			if(gameOverFrame == 0){
				gameOverFrame = Data.frame + 60;
				Data.keyCansel = true;
				Data.mouseCansel = true;
			}
			// �^�C�g���ɖ߂�
			if(gameOverFrame != 0 && gameOverFrame <= Data.frame){
				Data.gameStatus = Data.TITLE;
				Data.keyCansel = false;
				Data.mouseCansel = false;
			}
		}
	}
	// �N���A���C����\�����邽�߂ɃI�[�o�[���C�h
	protected void appearNewLine(){
		super.appearNewLine();
		// ����オ�������C����
		upNo++;
		// ����オ�肪��ꃉ�C�����𒴂�����
		if(upNo == Data.CLEAR_LINE){
			// �N���A���C�����Z�b�g
			clearLine = Data.ROW;
		}else{
			// �N���A���C�����o�����Ă����烉�C���o���̂��тɈ��ɂ��炷
			if(clearLine > 0) clearLine--;
		}
	}
	
	// ������
	public void init(){
		super.init();
		Data.seed = System.currentTimeMillis();
		random.setSeed(Data.seed);
		upNo = 0;
		clearLine = -1;
		Data.keyCansel = false;
		Data.mouseCansel = false;
		startFrame = Data.frame + Data.fps * 3;
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				panel[i][j] = null;
			}
		}
		for(int i = Data.ROW-1; i >= 0; i--){
			for(int j = 0; j < Data.COL; j++){
				if(i < Data.ROW/2+2) continue;
				else{
					do{
						panel[i][j] = new Panel(random.nextInt(Data.PANEL_NUMBER*Data.hard));
					}while(isDeletable(j,i));
				}
			}
		}
		createNewLine();
	}
	
	// �`��
	public void draw(Graphics g){
		super.draw(g);
		if(clearLine > 0){
			g.setColor(Color.RED);
			g.fillRect(Data.FIELD_START_X*Data.zoom,(Data.FIELD_START_Y+clearLine*Data.PANEL_SIZE-Data.scrollOffset-2)*Data.zoom,(Data.PANEL_SIZE*Data.COL)*Data.zoom,5*Data.zoom);
		}
		int space_x = 20;
		int space_y = 7;
		Data.setFont(g,Data.SCORE_FONT);
		if(startFrame <= Data.frame){
			if(gameOverFrame == 0) Data.time = (Data.frame-startFrame)/Data.fps;
			g.drawString((Data.time/60)+":"+String.format("%1$02d",(Data.time%60)),(Data.REST_X+space_x)*Data.zoom,(Data.REST_Y-space_y)*Data.zoom);
		}else{
			g.drawString(((startFrame-Data.frame)/Data.fps+1)+"",Data.WIDTH/2*Data.zoom,Data.HEIGHT/2*Data.zoom);
			g.drawString("0:00",(Data.REST_X+space_x)*Data.zoom,(Data.REST_Y-space_y)*Data.zoom);
		}
	}
}
