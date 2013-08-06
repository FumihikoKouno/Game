package Game.Body;

public class Body{
	protected int defencePower;
	protected int jumpMax;
	protected int jumpSpeed;
	protected int movingSpeed;
	protected int froat;
	protected int nockBackTime;
	protected int invisibleTime;
	public int getInvisibleTime(){ return invisibleTime; }
	public int getNockBackTime(){ return nockBackTime; }
	public int getDefence(){ return defencePower; }
	public int getFroat(){ return froat; }
	public int getJumpSpeed(){return jumpSpeed;}
	public int getJumpMax(){return jumpMax;}
	public int getMovingSpeed(){return movingSpeed;}
}