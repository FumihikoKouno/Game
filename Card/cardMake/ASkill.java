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
			return "�G�̂�����X�L�����ʂ��󂯂Ȃ�";
		case 2:
			return "�J�[�h��ʂ�z���Ĉړ��ł���";
		case 3:
			return "�΂߂Ɉړ��ł���";
		case 4:
			return "�΂߂ɍU���ł���";
		case 5:
			return "������X�L�����ʂ��󂯂Ȃ�";
		case 6:
			return "�����̑����Ɠ��������̍U�����z������";
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
