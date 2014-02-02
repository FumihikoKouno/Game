package Card;

import java.awt.Point;

import Common.Data;

public abstract class Card {
	
	public enum ELEMENT{
		SOUTH,
		NORTH,
	}
	
	public enum ATTACK_KIND{
		ATTACK,
		SKILL,
	}
	
	protected class Status{
		public int friendsForASkill;
		public int enemiesForASkill;
		public String aSkillDescription;
		public String pSkillDescription;
		public Data.ELEMENT element;
		public boolean[][] attackRange;
		public boolean[][] activeSkillRange;
		public boolean[][] moveRange;
	}
	protected class Cost{
		public int attack;
		public int move;
		public int evolution;
		public int activeSkill;
		public int summon;
	}
	
	protected ELEMENT user;
	protected int ID;
	protected Cost normalCost = new Cost();
	protected Cost evolvedCost = new Cost();
	protected Status normalStatus = new Status();
	protected Status evolvedStatus = new Status();
	protected int power;
	protected boolean evolved = false;
	protected String activeSkillDescription;
	protected String passiveSkillDescription;
	
	public Card(){
		init();
	}
	public Card(ELEMENT uid){
		user = uid; 
		init();
	}
	public abstract void init();
	public abstract int doActiveSkill(Card[] friends, Card[] enemies);
	public abstract int passiveDiffence(ATTACK_KIND ak, int damage);

	public int getAttackCost(){
		if(evolved) return evolvedCost.attack;
		else return normalCost.attack;
	}
	public int getSummonCost(){
		if(evolved) return evolvedCost.summon;
		else return normalCost.summon;
	}
	public int getActiveSkillCost(){
		if(evolved) return evolvedCost.activeSkill;
		else return normalCost.activeSkill;
	}
	public int getEvolveCost(){
		if(evolved) return evolvedCost.evolution;
		else return normalCost.evolution;
	}
	public int getMoveCost(){
		if(evolved) return evolvedCost.move;
		else return normalCost.move;
	}
	
	public ELEMENT getUser(){
		return user;
	}
	
	public boolean isEvolved(){
		return evolved;
	}
	
	public void evolve(){
		evolved = true;
	}
	
	public int getFriendsForActiveSkill(){
		if(evolved) return evolvedStatus.friendsForASkill;
		else return normalStatus.friendsForASkill;
	}
	public int getEnemiesForActiveSkill(){
		if(evolved) return evolvedStatus.enemiesForASkill;
		else return normalStatus.enemiesForASkill;
	}
	
	public String getActiveSkillDescription(){
		if(evolved) return evolvedStatus.aSkillDescription;
		else return normalStatus.aSkillDescription;
	}
	
	public String getPassiveSkillDescription(){
		if(evolved) return evolvedStatus.pSkillDescription;
		else return normalStatus.pSkillDescription;
	}
	
	public boolean isDead(){
		return (power<=0);
	}	
	
	public void damage(ATTACK_KIND ak, Data.ELEMENT element, int d){
		Status status;
		int damage = d;
		if(evolved) status = evolvedStatus;
		else status = normalStatus;
		if(Data.WEAKER.get(status.element) == element) damage = (damage << 1);
		if(Data.STRONGER.get(status.element) == element) damage = (damage >> 1);
		damage = passiveDiffence(ak,damage);
		power -= damage;
		if(power < 0) power = 0;
	}
	
	public int getPower(){
		return power;
	}
	
	public int doAttack(Card enemy){
		Status status;
		int ret;
		if(evolved){
			status = evolvedStatus;
			ret = evolvedCost.attack;
		}
		else{
			status = normalStatus;
			ret = normalCost.attack;
		}
		enemy.damage(ATTACK_KIND.ATTACK,status.element, power);
		return ret;
	}

	public boolean[][] getAttackableRange(Point self){
		boolean[][] range;
		if(evolved)	range = evolvedStatus.attackRange;
		else range = normalStatus.attackRange;
		return getRange(self,range);
	}

	public boolean[][] getActiveSkillRange(Point self){
		boolean[][] range;
		if(evolved)	range = evolvedStatus.activeSkillRange;
		else range = normalStatus.activeSkillRange;
		return getRange(self,range);
	}
	
	public boolean[][] getMoveRange(Point self){
		boolean[][] range;
		if(evolved)	range = evolvedStatus.moveRange;
		else range = normalStatus.moveRange;
		return getRange(self,range);
	}
	
	private boolean[][] getRange(Point self, boolean[][] range){
		if((range.length&1)==0 || (range[0].length&1)==0){
			System.out.println("Invalid range length : ["+range.length+"]["+range[0].length+"]" );
			return null;
		}
		boolean[][] ret;
		ret = new boolean[Data.FIELD_Y][Data.FIELD_X];
		
		for(int i = 0; i < range.length; i++){
			for(int j = 0; j < range[i].length; j++){
				int y = i+self.y-range.length/2;
				int x = j+self.x-range[i].length/2;
				if(x < 0 || x >= Data.FIELD_X || y < 0 || y >= Data.FIELD_Y) continue;
				ret[y][x] = range[i][j];
			}
		}
		return ret;
	}
}
