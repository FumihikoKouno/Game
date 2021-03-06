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
			return "敵のあらゆるスキル効果を受けない";
		case 2:
			return "カードを通り越して移動できる";
		case 3:
			return "斜めに移動できる";
		case 4:
			return "斜めに攻撃できる";
		case 5:
			return "あらゆるスキル効果を受けない";
		case 6:
			return "自分の属性と同じ属性の攻撃を吸収する";
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
