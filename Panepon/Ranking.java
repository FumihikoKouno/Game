import java.awt.Graphics;
import java.awt.Color;

class Ranking{
	Replay replay;
	int mode;
	int offset;
	int endlessLen;
	int scoreLen;
	int stageLen;
	Cursor cursor;
	Record[] endless = new Record[10];
	Record[] score = new Record[10];
	Record[] stage = new Record[10];
	boolean[] replayStatus = new boolean[3];
	ScoreIO sIO = new ScoreIO();
	public Ranking(){
		cursor = new Cursor(0,0);
	}
	
	public void init(){
		sIO.makeRanking(endless,score,stage,replayStatus);
		replay = null;
		cursor.setLoopAble(true);
		Data.keyCansel = false;
		Data.mouseCansel = false;
		mode = Data.ENDLESS;
		cursor.set(0,0);
		for(int i = 0; i < endless.length; i++){
			if(endless[i] == null){
				endlessLen = i;
				break;
			}
		}
		if(endlessLen == 0 && endless[0] != null) endlessLen = stage.length;

		for(int i = 0; i < score.length; i++){
			if(score[i] == null){
				scoreLen = i;
				break;
			}
		}
		if(scoreLen == 0 && score[0] != null) scoreLen = stage.length;

		for(int i = 0; i < stage.length; i++){
			if(stage[i] == null){
				stageLen = i;
				break;
			}
		}
		if(stageLen == 0 && stage[0] != null) stageLen = stage.length;
		Data.cursorMaxX = 2;
		Data.cursorMaxY = endlessLen-1;
	}
	
	public void update(){
	if(replay != null){
		replay.update();
		if(replay.end()){
			cursor.set(0,0);
			Data.cursorMaxX = 2;
			Data.cursorMaxY = endlessLen-1;
			replay = null;
		}
		return;
	}
		cursor.move();
		switch(cursor.getX()){
		case 0:
			mode = Data.ENDLESS;
			Data.cursorMaxY = endlessLen-1;
			break;
		case 1:
			mode = Data.SCORE_ATTACK;
			Data.cursorMaxY = scoreLen-1;
			break;
		case 2:
			mode = Data.STAGE_CLEAR;
			Data.cursorMaxY = stageLen-1;
			break;
		}
		if(Data.cursorMaxY < 0) Data.cursorMaxY = 0;
		cursor.setY(cursor.getY());
		if(KeyStatus.change) Data.gameStatus = Data.TITLE;
		if(KeyStatus.enter){
			String name = null;
			int ty = cursor.getY();
			KeyStatus.enter = false;
			switch(mode){
			case Data.ENDLESS:
				if(endless[ty] != null && endless[ty].getReplay()) name = endless[ty].getName();
				break;
			case Data.SCORE_ATTACK:
				if(score[ty] != null && score[ty].getReplay()) name = score[ty].getName();
				break;
			case Data.STAGE_CLEAR:
				if(stage[ty] != null && stage[ty].getReplay()) name = stage[ty].getName();
				break;
			}
			if(name != null){
				replay = new Replay();
				replay.setStatus(name, mode);
				replay.init();
			}
		}
	}
	public void draw(Graphics g){
		if(replay != null){
			replay.draw(g);
			return;
		}
		Data.setFont(g,Data.SCORE_FONT);
		int drawX = 60*Data.zoom;
		int drawY = 100*Data.zoom;
		int diff = 30*Data.zoom;
		switch(mode){
		case Data.ENDLESS:
			g.drawImage(Data.image.endlessImage,0,0,Data.WIDTH*Data.zoom,Data.HEIGHT*Data.zoom,
				0,0,Data.WIDTH,Data.HEIGHT,null);
			for(int i = 0; i < endless.length; i++){
				if(endless[i] == null) break;
				if(endless[i].getReplay()) g.setColor(Color.RED);
				else g.setColor(Color.WHITE);
				g.drawString(endless[i].getName(),Data.RANKING_NAME_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
				g.drawString(endless[i].getScore()+"",Data.RANKING_SCORE_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
				g.drawString(endless[i].getTime()+"",Data.RANKING_TIME_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
				g.drawString(endless[i].getMaxChain()+"",Data.RANKING_MAX_CHAIN_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
				g.drawString(endless[i].getMaxDelete()+"",Data.RANKING_MAX_DELETE_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
			}
			if((Data.frame % 60) < 30){
				g.setColor(Color.WHITE);
				g.drawString("Stage Clear",5*Data.zoom,470*Data.zoom);
				g.drawString("Score Attack",500*Data.zoom,470*Data.zoom);
				g.drawImage(Data.image.scoreChangeImage,0,400*Data.zoom,640*Data.zoom,427*Data.zoom,
					0,0,640,27,null);
			}
			break;
		case Data.SCORE_ATTACK:
			g.drawImage(Data.image.scoreAttackImage,0,0,Data.WIDTH*Data.zoom,Data.HEIGHT*Data.zoom,
				0,0,Data.WIDTH,Data.HEIGHT,null);
			for(int i = 0; i < score.length; i++){
				if(score[i] == null) break;
				if(score[i].getReplay()) g.setColor(Color.RED);
				else g.setColor(Color.WHITE);
				g.drawString(score[i].getName(),Data.RANKING_NAME_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
				g.drawString(score[i].getScore()+"",Data.RANKING_SCORE_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
				g.drawString(score[i].getTime()+"",Data.RANKING_TIME_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
				g.drawString(score[i].getMaxChain()+"",Data.RANKING_MAX_CHAIN_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
				g.drawString(score[i].getMaxDelete()+"",Data.RANKING_MAX_DELETE_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
			}
			if((Data.frame % 60) < 30){
				g.setColor(Color.WHITE);
				g.drawString("Endless",5*Data.zoom,470*Data.zoom);
				g.drawString("Stage Clear",500*Data.zoom,470*Data.zoom);
				g.drawImage(Data.image.scoreChangeImage,0,400*Data.zoom,640*Data.zoom,427*Data.zoom,
					0,0,640,27,null);
			}
			break;
		case Data.STAGE_CLEAR:
			g.drawImage(Data.image.stageClearImage,0,0,Data.WIDTH*Data.zoom,Data.HEIGHT*Data.zoom,
				0,0,Data.WIDTH,Data.HEIGHT,null);
			for(int i = 0; i < endless.length; i++){
				if(stage[i] == null) break;
				if(stage[i].getReplay()) g.setColor(Color.RED);
				else g.setColor(Color.WHITE);
				g.drawString(stage[i].getName(),Data.RANKING_NAME_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
				g.drawString(stage[i].getScore()+"",Data.RANKING_STAGE_SCORE_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
				g.drawString(stage[i].getTime()+"",Data.RANKING_STAGE_TIME_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
				g.drawString(stage[i].getMaxChain()+"",Data.RANKING_MAX_CHAIN_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
				g.drawString(stage[i].getMaxDelete()+"",Data.RANKING_MAX_DELETE_X*Data.zoom,(Data.RANKING_TOP_Y+i*Data.RANKING_DIFF_Y)*Data.zoom);
			}
			if((Data.frame % 60) < 30){
				g.setColor(Color.WHITE);
				g.drawString("Score Attack",5*Data.zoom,470*Data.zoom);
				g.drawString("Endless",500*Data.zoom,470*Data.zoom);
				g.drawImage(Data.image.scoreChangeImage,0,400*Data.zoom,640*Data.zoom,427*Data.zoom,
					0,0,640,27,null);
			}
			break;
		default:
			break;
		}
		cursor.draw(g,Data.RANKING);
	}
}