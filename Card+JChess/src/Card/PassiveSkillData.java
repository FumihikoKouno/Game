package Card;

import Card.Card.ATTACK_KIND;
import Common.Data;

public class PassiveSkillData {
	public static String getNormalString(int id, boolean evolved){
		switch(id){
		case 0:
			break;
		default:
			break;
		}
		return "NULL";
	}
	
	public static void passiveTurnStart(int id, boolean evolved){
		switch(id){
		case 0:
			break;
		default:
			break;
		}
		return;
	}
	
	public static int passiveDiffence(int id, boolean evolved, ATTACK_KIND ak, Data.ELEMENT element, int damage){
		switch(id){
		case 0:
			if(ak==ATTACK_KIND.SKILL) return 0;
			else return damage;
		default:
			return damage;
		}
	}
}
