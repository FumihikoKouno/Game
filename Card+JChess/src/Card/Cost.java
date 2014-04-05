package Card;

public class Cost{
	public int attack;
	public int move;
	public int evolution;
	public int activeSkill;
	public int summon;
	
	public Cost(int id, boolean evolved){
		switch(id){
		case 0:
			attack = 1;
			move = 2;
			evolution = 3;
			activeSkill = 4;
			summon = 5;
			break;
		default:
			break;
		}
	}
}
