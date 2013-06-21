import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class ASkill implements Common{

	private int skillNumber;
	
	public ASkill(int skillNumber){
		this.skillNumber = skillNumber;
	}
	
	public String getExplanation(){
		switch(skillNumber){
		case 0:
			return "";
		case 1:
			return "“G‚Ì‚ ‚ç‚ä‚éƒXƒLƒ‹Œø‰Ê‚ğó‚¯‚È‚¢";
		case 2:
			return "ƒJ[ƒh‚ğ’Ê‚è‰z‚µ‚ÄˆÚ“®‚Å‚«‚é";
		case 3:
			return "Î‚ß‚ÉˆÚ“®‚Å‚«‚é";
		case 4:
			return "Î‚ß‚ÉUŒ‚‚Å‚«‚é";
		case 5:
			return "‚ ‚ç‚ä‚éƒXƒLƒ‹Œø‰Ê‚ğó‚¯‚È‚¢";
		case 6:
			return "©•ª‚Ì‘®«‚Æ“¯‚¶‘®«‚ÌUŒ‚‚ğ‹zû‚·‚é";
		}
		return "Error";
	}
	
	private int abs(int x){
		if(x < 0) return -x;
		else return x;
	}
	
	public void exe(Point p, Field field){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		int turn = field.getTurn();
		int[][] range = field.getRange();
		switch(skillNumber){
		default:
			break;
		}
	}
	public double defenceMul(int element, int power, Point defender, Field field){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		int turn = field.getTurn();
		int[][] range = field.getRange();
		double mul = 1;
		switch(skillNumber){
		case 6:
			if(card[defender.y][defender.x].getElement() == element) mul = -1;
			break;
		default:
			mul = 1;
			break;
		}
		return mul;
	}
	
	public double defenceMul(Point attacker, Point defender, Field field){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		int turn = field.getTurn();
		int[][] range = field.getRange();
		double mul = 1;
		switch(skillNumber){
		case 6:
			if(card[defender.y][defender.x].getElement() == card[attacker.y][attacker.x].getElement()) mul = -1;
			break;
		default:
			mul = 1;
			break;
		}
		return mul;
	}
	public int defenceAdd(int element, int power, Point defender, Field field){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		int turn = field.getTurn();
		int[][] range = field.getRange();
		int add;
		switch(skillNumber){
		default:
			add = 0;
			break;
		}
		return add;
	}
	
	public int defenceAdd(Point attacker, Point defencer, Field field){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		int turn = field.getTurn();
		int[][] range = field.getRange();
		int add;
		switch(skillNumber){
		default:
			add = 0;
			break;
		}
		return add;
	}
	public void offence(Point p, Field field){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		int turn = field.getTurn();
		int[][] range = field.getRange();
		switch(skillNumber){
		default:
			break;
		}
	}
	
	public void draw(int turn, int field, Graphics g){
		if((field * turn > 0) || (abs(field) == 2 || abs(field) == 4)){
			g.setFont(font);
			g.setColor(Color.white);
			for(int i = 0; i < WORD_ROW; i++){
				String aExplanation = getExplanation();
				if(aExplanation.length() < i * WORD_COUNT + WORD_COUNT){
					g.drawString(aExplanation.substring(i * WORD_COUNT , aExplanation.length()),
						A_SKILL_X, A_SKILL_Y + i * STRING_Y_SIZE);
					break;
				}else{
					g.drawString(aExplanation.substring(i * WORD_COUNT , i * WORD_COUNT + WORD_COUNT),
						A_SKILL_X, A_SKILL_Y + i * STRING_Y_SIZE);
				}
			}
		}
	}
}