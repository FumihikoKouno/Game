/**
 * エンドレスモード
 */
import java.awt.Graphics;

public class Endless extends Field{
	
	public Endless(){}
	// ゲームオーバーフラグを立てる関数
	protected void gameOver(){
		if(topExist() && (Data.scrollOffset != 0)){	
			if(gameOverFrame == 0){
				gameOverFrame = Data.frame + 60;
				Data.keyCansel = true;
				Data.mouseCansel = true;
			}
			if(gameOverFrame != 0 && gameOverFrame <= Data.frame){
				new ScoreIO().output();
				Data.gameStatus = Data.TITLE;
				Data.keyCansel = false;
				Data.mouseCansel = false;
			}
		}
	}
	// 初期化
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
	// 描画
	public void draw(Graphics g){
		super.draw(g);
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
