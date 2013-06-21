import java.awt.Graphics;

class Ranking{
	int mode;
	int offset;
	Cursor cursor;
	Record[] endless = new Record[10];
	Record[] score = new Record[10];
	Record[] stage = new Record[10];
	ScoreIO sIO = new ScoreIO();
	public Ranking(){
		cursor = new Cursor(0,0);
	}
	
	public void init(){
		sIO.makeRanking(endless,score,stage);
		cursor.setLoopAble(true);
		Data.keyCansel = false;
		Data.mouseCansel = false;
		mode = Data.ENDLESS;
		cursor.set(0,0);
		Data.cursorMaxX = Data.STAGE_CLEAR - 1;
		Data.cursorMaxY = 0;
	}
	
	public void update(){
		cursor.move();
		mode = cursor.getX() + 1;
		if(KeyStatus.change) Data.gameStatus = Data.TITLE;
	}
	public void draw(Graphics g){
		Data.setFont(g,Data.SCORE_FONT);
		int drawX = 60;
		int drawY = 100;
		int diff = 30;
		switch(mode){
		case Data.ENDLESS:
			g.drawImage(Data.image.endlessImage,0,0,null);
			for(int i = 0; i < endless.length; i++){
				if(endless[i] == null) break;
				g.drawString(endless[i].getName(),Data.RANKING_NAME_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
				g.drawString(endless[i].getScore()+"",Data.RANKING_SCORE_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
				g.drawString(endless[i].getTime()+"",Data.RANKING_TIME_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
				g.drawString(endless[i].getMaxChain()+"",Data.RANKING_MAX_CHAIN_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
				g.drawString(endless[i].getMaxDelete()+"",Data.RANKING_MAX_DELETE_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
			}
			if((Data.frame % 60) < 30){
				g.drawString("Stage Clear",5,470);
				g.drawString("Score Attack",500,470);
				g.drawImage(Data.image.scoreChangeImage,0,400,null);
			}
			break;
		case Data.SCORE_ATTACK:
			g.drawImage(Data.image.scoreAttackImage,0,0,null);
			for(int i = 0; i < score.length; i++){
				if(score[i] == null) break;
				g.drawString(score[i].getName(),Data.RANKING_NAME_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
				g.drawString(score[i].getScore()+"",Data.RANKING_SCORE_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
				g.drawString(score[i].getTime()+"",Data.RANKING_TIME_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
				g.drawString(score[i].getMaxChain()+"",Data.RANKING_MAX_CHAIN_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
				g.drawString(score[i].getMaxDelete()+"",Data.RANKING_MAX_DELETE_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
			}
			if((Data.frame % 60) < 30){
				g.drawString("Endless",5,470);
				g.drawString("Stage Clear",500,470);
				g.drawImage(Data.image.scoreChangeImage,0,400,null);
			}
			break;
		case Data.STAGE_CLEAR:
			g.drawImage(Data.image.stageClearImage,0,0,null);
			for(int i = 0; i < endless.length; i++){
				if(stage[i] == null) break;
				g.drawString(stage[i].getName(),Data.RANKING_NAME_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
				g.drawString(stage[i].getScore()+"",Data.RANKING_STAGE_SCORE_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
				g.drawString(stage[i].getTime()+"",Data.RANKING_STAGE_TIME_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
				g.drawString(stage[i].getMaxChain()+"",Data.RANKING_MAX_CHAIN_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
				g.drawString(stage[i].getMaxDelete()+"",Data.RANKING_MAX_DELETE_X,Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y);
			}
			if((Data.frame % 60) < 30){
				g.drawString("Score Attack",5,470);
				g.drawString("Endless",500,470);
				g.drawImage(Data.image.scoreChangeImage,0,400,null);
			}
			break;
		default:
			break;
		}
	}
}