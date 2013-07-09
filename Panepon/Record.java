class Record{
	private String name;
	private int score;
	private int time;
	private int maxChain;
	private int maxDelete;
	private boolean replay;

	public Record(String name, int score, int time, int maxChain, int maxDelete, boolean replay){
		this.name = name;
		this.score = score;
		this.time = time;
		this.maxChain = maxChain;
		this.maxDelete = maxDelete;
		this.replay = replay;
	}
	
	public boolean getReplay(){ return replay; }
	public String getName(){ return name; }
	public int getScore(){ return score; }
	public int getTime(){ return time; }
	public int getMaxChain(){ return maxChain; }
	public int getMaxDelete(){ return maxDelete; }
	
	public boolean haveHighScoreThan(Record against){
		return score > against.getScore();
	}
	public boolean haveShorterTimeThan(Record against){
		return time < against.getTime();
	}
	public String toString(){
		return (name+":: Score:"+score+", Time:"+time+", maxChain:"+maxChain+", maxDelete"+maxDelete);
	}
}