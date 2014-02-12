package Card;

import java.util.ArrayList;

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
		boolean[][] eMoveTmp =
			{
				{true,true,true,true,true},
				{true,true,true,true,true},
				{true,true,false,true,true},
				{true,true,true,true,true},
				{true,true,true,true,true}
			};
		normalStatus.activeSkillRange = activeSkillTmp;
		normalStatus.attackRange = attackTmp;
		normalStatus.moveRange = moveTmp;
		normalStatus.element = Data.ELEMENT.NONE;
		normalStatus.aSkillDescription = "Nothing";
		normalStatus.fieldsForASkill = 0;
		normalStatus.enemiesForASkill = 0;
		normalStatus.friendsForASkill = 0;
		normalStatus.pSkillDescription = "Unaffected by all skills";
		normalCost.summon = 1;
		normalCost.attack = 2;
		normalCost.activeSkill = 3;
		normalCost.evolution = 4;
		normalCost.move = 5;		normalStatus.activeSkillRange = activeSkillTmp;
		evolvedStatus.attackRange = attackTmp;
		evolvedStatus.moveRange = eMoveTmp;
		evolvedStatus.element = Data.ELEMENT.NONE;
		evolvedStatus.aSkillDescription = "Nothing";
		evolvedStatus.fieldsForASkill = 0;
		evolvedStatus.enemiesForASkill = 0;
		evolvedStatus.friendsForASkill = 0;
		evolvedStatus.pSkillDescription = "Unaffected by all skills";
		evolvedCost.summon = 1;
		evolvedCost.attack = 2;
		evolvedCost.activeSkill = 3;
		evolvedCost.evolution = 0;
		evolvedCost.move = 5;
	}

	@Override
	public int doActiveSkill(ArrayList<Box> fields, ArrayList<Card> friends, ArrayList<Card> enemies) {
		return 0;
	}

}
