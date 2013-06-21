import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class MSkill implements Common{
	private int skillNumber;
	
	public MSkill(int skillNumber){
		this.skillNumber = skillNumber;
	}
	
	public String getExplanation(){
		switch(skillNumber){
		case 0:
			return "�Ȃ�";
		case 1:
			return "�͈͓��̓G�Ƀ_���[�W";
		case 2:
			return "�����̂��ׂẴX�e�[�^�X��������Ԃɖ߂�";
		case 3:
			return "�C�ӂ̈ʒu�Ɉړ�";
		case 4:
			return "�G�̃J�[�h�̓��v���C���[�������J�[�h�ōł��p���[�������J�[�h�Ƀ_���[�W";
		case 5:
			return "������̓G�Ƀ_���[�W";
		case 6:
			return "�t�B�[���h��̓G�̈ړ��́|�P";
		case 7:
			return "�t�B�[���h��̖����̈ړ��́{�P";
		case 8:
			return "�J�[�h���P���h���[";
		case 9:
			return "�͈͓��̓G�̃p���[���Q�O�O�z��";
		case 10:
			return "�͈͓��̖����̃p���[���P�O�O�O��";
		case 11:
			return "�����P�̂�C�ӂ̈ʒu�Ɉړ�";
		case 12:
			return "�I�����������Ɠ��������ɂȂ�";
		case 13:
			return "�C�ӂ̈ʒu�̓G�ɍU���ł���";
		case 14:
			return "�t�B�[���h��̂��ׂẴJ�[�h�Ƀ_���[�W";
		}
		return "Error";
	}
	
	public void draw(Graphics g){
		g.setFont(font);
		g.setColor(Color.white);
		for(int i = 0; i < wordROW; i++){
			String mExplanation = getExplanation();
			if(mExplanation.length() < i * wordCount + wordCount){
				g.drawString(mExplanation.substring(i * wordCount , mExplanation.length()),
					mSkillX, mSkillY + i * stringYSize);
				break;
			}else{
				g.drawString(mExplanation.substring(i * wordCount , i * wordCount + wordCount),
					mSkillX, mSkillY + i * stringYSize);
			}
		}
	}
}
