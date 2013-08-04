/**
 * スコアアタックモード
 */
import java.awt.Graphics;
import java.awt.Image;

public class ScoreAttack extends Field{
	
	public ScoreAttack(){}

	// ゲームオーバー
	protected void gameOver(){
		// 制限時間が0になったら
		if(startFrame + Data.TIME_LIMIT <= Data.frame){
			// ゲームオーバーの文字表示用に猶予時間とキー受付のキャンセルをセット
			if(gameOverFrame == 0){
				gameOverFrame = Data.frame + 60;
				Data.keyCansel = true;
				Data.mouseCansel = true;
			}
			// 時間が経ったらスコアを記録し、タイトルに戻る。その際キー受付のキャンセルはリセットしておく
			if(gameOverFrame != 0 && gameOverFrame <= Data.frame){
				new ScoreIO().output();
				Data.gameStatus = Data.TITLE;
				Data.keyCansel = false;
				Data.mouseCansel = false;
			}
		}
		// 上にパネルが達してしまった場合
		if(topExist() && (Data.scrollOffset != 0)){
			// ゲームオーバー表示用猶予時間
			if(gameOverFrame == 0){
				gameOverFrame = Data.frame + 60;
				Data.keyCansel = true;
				Data.mouseCansel = true;
			}
			// 時間が経ったらタイトルに戻る
			if(gameOverFrame != 0 && gameOverFrame <= Data.frame){
				Data.gameStatus = Data.TITLE;
				Data.keyCansel = false;
				Data.mouseCansel = false;
			}
		}
	}

	// 初期化関数
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
			if(gameOverFrame == 0) Data.time = (startFrame+Data.TIME_LIMIT-Data.frame)/Data.fps;
		 	g.drawString((Data.time/60)+":"+String.format("%1$02d",(Data.time%60)),(Data.REST_X+space_x)*Data.zoom,(Data.REST_Y-space_y)*Data.zoom);
		}else{
			g.drawString(((startFrame-Data.frame)/Data.fps+1)+"",Data.WIDTH/2*Data.zoom,Data.HEIGHT/2*Data.zoom);
			Data.time = Data.TIME_LIMIT/Data.fps;
		 	g.drawString((Data.time/60)+":"+String.format("%1$02d",(Data.time%60)),(Data.REST_X+space_x)*Data.zoom,(Data.REST_Y-space_y)*Data.zoom);
		}
	}
}
