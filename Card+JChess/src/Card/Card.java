package Card;

import java.awt.Point;
import java.util.ArrayList;

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
	
	protected ELEMENT user;
	protected int ID;
	protected Cost normalCost;
	protected Cost evolvedCost;
	protected StatusData normalStatus;
	protected StatusData evolvedStatus;
	protected boolean evolved = false;
	
	public Card(ELEMENT uid){
		user = uid; 
		init();
		normalStatus = new StatusData(ID,false);
		evolvedStatus = new StatusData(ID,true);
		normalCost = new Cost(ID,false);
		evolvedCost = new Cost(ID,true);
	}
	public abstract void init();
	public void doActiveSkill(ArrayList<Box> fields, ArrayList<Card> friends, ArrayList<Card> enemies){
		ActiveSkillData.doActiveSkill(ID, evolved, fields, friends, enemies);
	}

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
	
	public boolean getSelectForActiveSkill(){
		if(evolved) return evolvedStatus.aSkill.selectForASkill;
		else return normalStatus.aSkill.selectForASkill;
	}
	
	public int getFieldsForActiveSkill(){
		if(evolved) return evolvedStatus.aSkill.fieldsForASkill;
		else return normalStatus.aSkill.fieldsForASkill;
	}

	public int getFriendsForActiveSkill(){
		if(evolved) return evolvedStatus.aSkill.friendsForASkill;
		else return normalStatus.aSkill.friendsForASkill;
	}
	public int getEnemiesForActiveSkill(){
		if(evolved) return evolvedStatus.aSkill.enemiesForASkill;
		else return normalStatus.aSkill.enemiesForASkill;
	}
	
	public String getActiveSkillDescription(){
		if(evolved) return evolvedStatus.aSkill.aSkillDescription;
		else return normalStatus.aSkill.aSkillDescription;
	}
	
	public String getPassiveSkillDescription(){
		if(evolved) return evolvedStatus.pSkill.pSkillDescription;
		else return normalStatus.pSkill.pSkillDescription;
	}
	
	public int getLife(){
		if(evolved) return evolvedStatus.life;
		else return normalStatus.life;
	}
	
	public void setLife(int l){
		if(evolved) evolvedStatus.life = l;
		else normalStatus.life = l;
	}
	
	public boolean isDead(){
		return (getLife()<=0);
	}	
	
	public void damage(ATTACK_KIND ak, Data.ELEMENT element, int d){
		StatusData status;
		int damage = d;
		if(evolved) status = evolvedStatus;
		else status = normalStatus;
		if(Data.WEAKER.get(status.element) == element) damage = (damage << 1);
		if(Data.STRONGER.get(status.element) == element) damage = (damage >> 1);
		damage = PassiveSkillData.passiveDiffence(ID,evolved,ak,element,damage);
		setLife(getLife()-damage);
		if(getLife() < 0) setLife(0);
	}
	
	public int getPower(){
		if(evolved) return evolvedStatus.power;
		else return normalStatus.power;
	}
	
	public int doAttack(Card enemy){
		StatusData status;
		int ret;
		if(evolved){
			status = evolvedStatus;
			ret = evolvedCost.attack;
		}
		else{
			status = normalStatus;
			ret = normalCost.attack;
		}
		enemy.damage(ATTACK_KIND.ATTACK,status.element, getPower());
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
		if(evolved)	range = evolvedStatus.aSkill.activeSkillRange;
		else range = normalStatus.aSkill.activeSkillRange;
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
		boolean[][] tmpRange;
		tmpRange = new boolean[range.length][range[0].length];

		switch(user){
		case NORTH:
			for(int i = 0; i < range.length; i++){
				tmpRange[i] = range[range.length-i-1];
			}
			break;
		case SOUTH:
			tmpRange = range;
			break;
		default:
			break;
		}
		
		for(int i = 0; i < tmpRange.length; i++){
			for(int j = 0; j < tmpRange[i].length; j++){
				int y = i+self.y-tmpRange.length/2;
				int x = j+self.x-tmpRange[i].length/2;
				if(x < 0 || x >= Data.FIELD_X || y < 0 || y >= Data.FIELD_Y) continue;
				ret[y][x] = tmpRange[i][j];
			}
		}
		
		return ret;
	}
}
