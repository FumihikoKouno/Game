package Card;

public class PassiveSkill {
	public String pSkillDescription;
	
	public PassiveSkill(int id, boolean evolved){
		switch(id){
		case 0:
			pSkillDescription = "Cannot";
			break;
		default:
			break;
		}
	}
}
