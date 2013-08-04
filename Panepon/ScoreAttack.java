/**
 * �X�R�A�A�^�b�N���[�h
 */
import java.awt.Graphics;
import java.awt.Image;

public class ScoreAttack extends Field{
	
	public ScoreAttack(){}

	// �Q�[���I�[�o�[
	protected void gameOver(){
		// �������Ԃ�0�ɂȂ�����
		if(startFrame + Data.TIME_LIMIT <= Data.frame){
			// �Q�[���I�[�o�[�̕����\���p�ɗP�\���ԂƃL�[��t�̃L�����Z�����Z�b�g
			if(gameOverFrame == 0){
				gameOverFrame = Data.frame + 60;
				Data.keyCansel = true;
				Data.mouseCansel = true;
			}
			// ���Ԃ��o������X�R�A���L�^���A�^�C�g���ɖ߂�B���̍ۃL�[��t�̃L�����Z���̓��Z�b�g���Ă���
			if(gameOverFrame != 0 && gameOverFrame <= Data.frame){
				new ScoreIO().output();
				Data.gameStatus = Data.TITLE;
				Data.keyCansel = false;
				Data.mouseCansel = false;
			}
		}
		// ��Ƀp�l�����B���Ă��܂����ꍇ
		if(topExist() && (Data.scrollOffset != 0)){
			// �Q�[���I�[�o�[�\���p�P�\����
			if(gameOverFrame == 0){
				gameOverFrame = Data.frame + 60;
				Data.keyCansel = true;
				Data.mouseCansel = true;
			}
			// ���Ԃ��o������^�C�g���ɖ߂�
			if(gameOverFrame != 0 && gameOverFrame <= Data.frame){
				Data.gameStatus = Data.TITLE;
				Data.keyCansel = false;
				Data.mouseCansel = false;
			}
		}
	}

	// �������֐�
	public void init(){
		super.init();
		Data.seed = System.currentTimeMillis();
		random.setSeed(Data.seed);
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
		int space_x = 20;
		int space_y = 7;
		Data.setFont(g,Data.SCORE_FONT);
		if(startFrame <= Data.frame){
			if(gameOverFrame == 0) Data.time = (startFrame+Data.TIME_LIMIT-Data.frame)/Data.fps;
		 	g.drawString((Data.time/60)+":"+String.format("%1$02d",(Data.time%60)),(Data.REST_X+space_x)*Data.zoom,(Data.REST_Y-space_y)*Data.zoom);
		}else{
			g.drawString(((startFrame-Data.frame)/Data.fps+1)+"",Data.WIDTH/2*Data.zoom,Data.HEIGHT/2*Data.zoom);
			Data.time = Data.TIME_LIMIT/Data.fps;
		 	g.drawString((Data.time/60)+":"+String.format("%1$02d",(Data.time%60)),(Data.REST_X+space_x)*Data.zoom,(Data.REST_Y-space_y)*Data.zoom);
		}
	}
}
