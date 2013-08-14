package Game.Body;

public class Body{
	protected byte defencePower;
	protected byte jumpMax;
	protected byte jumpSpeed;
	protected byte movingSpeed;
	protected byte froat;
	protected byte nockBackTime;
	protected byte invisibleTime;
	public byte getInvisibleTime(){ return invisibleTime; }
	public byte getNockBackTime(){ return nockBackTime; }
	public byte getDefence(){ return defencePower; }
	public byte getFroat(){ return froat; }
	public byte getJumpSpeed(){return jumpSpeed;}
	public byte getJumpMax(){return jumpMax;}
	public byte getMovingSpeed(){return movingSpeed;}
}