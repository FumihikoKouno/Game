/**
 * スコアデータのクラス
 */
import java.util.ArrayList;

class Record{
	// 名前
	private String name;
	private int score;
	private int time;
	private int maxChain;
	private int maxDelete;
	private boolean replay;
	private int sumTime;
	private int count;
	
	インスタンス。各データを設定
	public Record(String name, int score, int time, int maxChain, int maxDelete, boolean replay, int sumTime, int count){
		this.name = name;
		this.score = score;
		this.time = time;
		this.maxChain = maxChain;
		this.maxDelete = maxDelete;
		this.replay = replay;
		this.sumTime = sumTime;
		this.count = count;
	}
	
	public int getCount(){ return count; }
	public int getSumTime(){ return sumTime; }
	public boolean getReplay(){ return replay; }
	public String getName(){ return name; }
	public int getScore(){ return score; }
	public int getTime(){ return time; }
	public int getMaxChain(){ return maxChain; }
	public int getMaxDelete(){ return maxDelete; }
	
	// スコアが上回っていればtrue
	public boolean haveHighScoreThan(Record against){
		return score > against.getScore();
	}
	// タイムが短ければtrue
	public boolean haveShorterTimeThan(Record against){
		return time < against.getTime();
	}
	public String toString(){
		return (name+"::\n  Score:"+score+"\n  Time:"+time+"\n  maxChain:"+maxChain+"\n  maxDelete:"+maxDelete+"\n  replay:"+replay+"\n  sumTime:"+sumTime+"\n  count:"+count);
	}
}