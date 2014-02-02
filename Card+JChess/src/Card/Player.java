package Card;

import Common.Data;

public class Player extends Card {

	public Player(Card.ELEMENT e){super(e);}
	
	public int passiveDiffence(Card.ATTACK_KIND ak, int damage){
		if(ak==Card.ATTACK_KIND.SKILL){
			return 0;
		}
		return damage;
	}
	
	@Override
	public void init() {
		power = 8000;
		boolean[][] activeSkillTmp =
			{
				{true,true,false},
				{true,false,true},
				{true,true,true}
			};
		boolean[][] attackTmp =
			{
				{true,false,true},
				{false,false,false},
				{true,false,true},
				{true,true,true},
				{true,false,true}
			};
		boolean[][] moveTmp =
			{
				{false,true,true},
				{true,false,true},
				{false,true,true}
			};
		normalStatus.activeSkillRange = activeSkillTmp;
		normalStatus.attackRange = attackTmp;
		normalStatus.moveRange = moveTmp;
		normalStatus.element = Data.ELEMENT.NONE;
		normalStatus.aSkillDescription = "Nothing";
		normalStatus.enemiesForASkill = 0;
		normalStatus.friendsForASkill = 0;
		normalStatus.pSkillDescription = "Unaffected by all skills";
		normalCost.summon = 1;
		normalCost.attack = 2;
		normalCost.activeSkill = 3;
		normalCost.evolution = 4;
		normalCost.move = 5;
	}

	@Override
	public int doActiveSkill(Card[] friends, Card[] enemies) {
		return 0;
	}

}
