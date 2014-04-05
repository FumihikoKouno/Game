package Card;

import Common.Data;
import Common.Data.ELEMENT;

public class StatusData {
	public int power;
	public int life;
	public ActiveSkill aSkill;
	public PassiveSkill pSkill;
	public Data.ELEMENT element;
	public boolean[][] attackRange;
	public boolean[][] moveRange;

	public StatusData(int id, boolean evolved){
		switch(id){
		case 0:
			if(evolved){
				attackRange = new boolean[3][3];
				for(int i = 0; i < 3; i++) attackRange[0][i] = true;
				moveRange = new boolean[5][5];
				for(int i = 0; i < 5; i++){
					for(int j = 0; j < 5; j++){
						moveRange[i][j] = true;
					}
				}
				element = ELEMENT.NONE;
				life = 10000;
				power = 500;
				aSkill = new ActiveSkill(id,evolved);
				pSkill = new PassiveSkill(id,evolved);
			}else{
				attackRange = new boolean[3][3];
				attackRange[0][1] = true;
				moveRange = new boolean[3][3];
				for(int i = 0; i < 3; i++){
					for(int j = 0; j < 3; j++){
						moveRange[i][j] = true;
					}
				}
				element = ELEMENT.NONE;
				life = 10000;
				power = 500;
				aSkill = new ActiveSkill(id,evolved);
				pSkill = new PassiveSkill(id,evolved);
			}
			break;
		default:
			break;
		}
	}
}
