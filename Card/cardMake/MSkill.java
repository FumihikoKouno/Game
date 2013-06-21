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
			return "なし";
		case 1:
			return "範囲内の敵にダメージ";
		case 2:
			return "自分のすべてのステータスを初期状態に戻す";
		case 3:
			return "任意の位置に移動";
		case 4:
			return "敵のカードの内プレイヤーを除くカードで最もパワーが高いカードにダメージ";
		case 5:
			return "直線上の敵にダメージ";
		case 6:
			return "フィールド上の敵の移動力－１";
		case 7:
			return "フィールド上の味方の移動力＋１";
		case 8:
			return "カードを１枚ドロー";
		case 9:
			return "範囲内の敵のパワーを２００吸収";
		case 10:
			return "範囲内の味方のパワーを１０００回復";
		case 11:
			return "味方１体を任意の位置に移動";
		case 12:
			return "選択した味方と同じ属性になる";
		case 13:
			return "任意の位置の敵に攻撃できる";
		case 14:
			return "フィールド上のすべてのカードにダメージ";
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
