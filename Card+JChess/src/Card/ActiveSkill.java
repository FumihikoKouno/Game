package Card;

public class ActiveSkill {
	public boolean selectForASkill;
	public int fieldsForASkill;
	public int friendsForASkill;
	public int enemiesForASkill;
	public String aSkillDescription;
	public boolean[][] activeSkillRange;

	public ActiveSkill(int id, boolean evolved){
		switch(id){
		case 0:
			aSkillDescription = "Nothing";
			activeSkillRange = new boolean[1][1];
			break;
		default:
			break;
		}
	}
}
