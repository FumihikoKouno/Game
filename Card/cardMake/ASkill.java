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
	
	public void draw(Graphics g){
		g.setFont(font);
		g.setColor(Color.white);
		for(int i = 0; i < wordROW; i++){
			String aExplanation = getExplanation();
			if(aExplanation.length() < i * wordCount + wordCount){
				g.drawString(aExplanation.substring(i * wordCount , aExplanation.length()),
					aSkillX, aSkillY + i * stringYSize);
				break;
			}else{
				g.drawString(aExplanation.substring(i * wordCount , i * wordCount + wordCount),
					aSkillX, aSkillY + i * stringYSize);
			}
		}
	}
}
