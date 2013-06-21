import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class MSkill implements Common{
	
	private static final int TEST = 99;
	private int sumTarget;
	private int sumFriend;
	private int sumCard;
	private int sumField;
	private int skillNumber;
	
	public MSkill(int skillNumber){
		this.skillNumber = skillNumber;
	}
	
	private void setRangeAllField(int[][] range, Field field){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		int turn = field.getTurn();
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(cardPlayer[i][j] == 0){
					range[i][j] = 1;
				}
			}
		}
	}
	
	private void setRangeAllEnemy(int[][] range, Field field){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		int turn = field.getTurn();
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(card[i][j] != null && cardPlayer[i][j] * turn < 0 && card[i][j].getASkill() != 1 && card[i][j].getASkill() != 5){
					range[i][j] = 1;
				}
			}
		}
	}
	
	private void setRangeAllFriend(int[][] range, Field field){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		int turn = field.getTurn();
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(card[i][j] != null && cardPlayer[i][j] * turn > 0 && card[i][j].getASkill() != 5){
					range[i][j] = 1;
				}
			}
		}
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
		case TEST:
			return "味方を３枚犠牲にし敵のカードを２枚倒す";
		}
		return "Error";
	}

	public void exe(Point p, Field field){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		int turn = field.getTurn();
		int[][] range = field.getRange();
		Point[] target = field.getTargetCard();
		Point[] friend = field.getFriendCard();
		Point[] sumCard = field.getSumCard();
		Point[] sumField = field.getSumField();
		int attackElement = card[p.y][p.x].getElement();
		int attackPower = card[p.y][p.x].getPower();
		switch(skillNumber){
		case 0:
			break;
		case 1:
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(range[i][j] == 1 && cardPlayer[i][j] != 0){
						field.skillDamage(attackElement,attackPower,new Point(j,i));
					}
				}
			}
			break;
		case 2:
			field.setEffect(p,FRIEND_SUPPORT);
			card[p.y][p.x].loadCardData(card[p.y][p.x].getCardNumber());
			break;
		case 3:
			field.setEffect(p,FRIEND_SUPPORT);
			field.move(sumField[0], p);
			break;
		case 4:
			int maxPower = 0;
			Point maxCard = null;
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(range[i][j] == 1){
						if(card[i][j].getPower() > maxPower){
							maxPower = card[i][j].getPower();
							maxCard = new Point(j,i);
						}
					}
				}
			}
			attackElement = card[p.y][p.x].getElement();
			attackPower = card[p.y][p.x].getPower();
			field.skillDamage(attackElement,attackPower,new Point(maxCard.x,maxCard.y));
			break;
		case 5:
			attackElement = card[p.y][p.x].getElement();
			attackPower = card[p.y][p.x].getPower();
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(range[i][j] == 1 && cardPlayer[i][j] != 0){
						field.skillDamage(attackElement,attackPower,new Point(j,i));
					}
				}
			}
			break;
		case 6:
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(range[i][j] == 1 && cardPlayer[i][j] != 0){
						card[i][j].calcMoveRange(-1);
						field.setEffect(new Point(j,i),TARGET_SUPPORT);
					}
				}
			}
			break;
		case 7:
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(range[i][j] == 1 && cardPlayer[i][j] != 0){
						field.setEffect(new Point(j,i),FRIEND_SUPPORT);
						card[i][j].calcMoveRange(1);
					}
				}
			}
			break;
		case 8:
			field.getPlayer(turn).drawHand();
			break;
		case 9:
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(range[i][j] == 1 && cardPlayer[i][j] != 0 && card[i][j] != null){
						field.skillDamage(0,200,new Point(j,i));
						card[p.y][p.x].powerCalc(200);
					}
				}
			}
			field.setEffect(p,FRIEND_SUPPORT);
			break;
		case 10:
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(range[i][j] == 1 && cardPlayer[i][j] != 0 && card[i][j] != null){
						field.setEffect(new Point(j,i),FRIEND_SUPPORT);
						card[i][j].powerCalc(1000);
					}
				}
			}
			break;
		case 11:
			field.move(sumField[0], friend[0]);
			field.setEffect(friend[0],FRIEND_SUPPORT);
			break;
		case 12:
			card[p.y][p.x].setElement(card[friend[0].y][friend[0].x].getElement());
			field.setEffect(friend[0],FRIEND_SUPPORT);
			field.setEffect(p,FRIEND_SUPPORT);
			break;
		case 13:
			attackElement = card[p.y][p.x].getElement();
			attackPower = card[p.y][p.x].getPower();
			field.skillDamage(attackElement,attackPower,target[0]);
			break;
		case 14:
			attackElement = card[p.y][p.x].getElement();
			attackPower = card[p.y][p.x].getPower();
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(range[i][j] == 1 && cardPlayer[i][j] != 0) field.skillDamage(attackElement,attackPower,new Point(j,i));
				}
			}
			break;
		case TEST:
			field.skillDamage(0,MAX_DAMAGE,target[0]);
			field.skillDamage(0,MAX_DAMAGE,target[1]);
			field.skillDamage(0,MAX_DAMAGE,friend[0]);
			field.skillDamage(0,MAX_DAMAGE,friend[1]);
			field.skillDamage(0,MAX_DAMAGE,friend[2]);
			break;
		}
	}
	
	public int getTargetNumber(){
		switch(skillNumber){
		case 13:
			sumTarget = 1;
			break;
		case TEST:
			sumTarget = 2;
			break;
		default:
			sumTarget = 0;
			break;
		}
		return sumTarget;
	}
	
	public int getFriendNumber(){
		sumFriend = 0;
		switch(skillNumber){
		case 11:
			sumFriend = 1;
			break;
		case 12:
			sumFriend = 1;
			break;
		case TEST:
			sumFriend = 3;
			break;
		default:
			sumFriend = 0;
			break;
		}
		return sumFriend;
	}
	
	public int getSumCard(){
		switch(skillNumber){
		case 11:
			sumCard = 1;
			break;
		case 12:
			sumCard = 1;
			break;
		case 13:
			sumCard = 1;
			break;
		case TEST:
			sumCard = 5;
			break;
		default:
			sumCard = 0;
			break;
		}
		return sumCard;
	}
	
	public int getSumField(){
		switch(skillNumber){
		case 3:
			sumField = 1;
			break;
		case 11:
			sumField = 1;
			break;
		default:
			sumField = 0;
			break;
		}
		return sumField;
	}
	
	public void setRange(Point p, int[][] range, Field field){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		int turn = field.getTurn();
		int turnTemp;
		switch(skillNumber){
		case 0:
			break;
		case 1:
			int k = 0;
			for(int i = p.y-turn; k < 3; i -= turn, k++){
				for(int j = p.x-k; j < p.x+k+1; j++){
					if(i >= 0 && i < ROW && j >= 0 && j < COL){
						turnTemp = cardPlayer[i][j] * turn;
						if(card[i][j] == null || turnTemp == -1 || (turnTemp == -2 && card[i][j].getASkill() != 1 && card[i][j].getASkill() != 5)){
							range[i][j] = 1;
						}
					}
				}
			}
			break;
		case 2:
			range[p.y][p.x] = 1;
			break;
		case 3:
			setRangeAllField(range,field);
			break;
		case 4:
			setRangeAllEnemy(range,field);
			break;
		case 5:
			for(int i = 1; p.y - i * turn > 0; i++){
				if((cardPlayer[i][p.x] == 0) || (card[i][p.x] != null && cardPlayer[i][p.x] * turn < 0 && card[i][p.x].getASkill() != 1) && card[i][p.x].getASkill() != 5){
					range[i][p.x] = 1;
				}
			}
			break;
		case 6:
			setRangeAllEnemy(range,field);
			break;
		case 7:
			setRangeAllFriend(range,field);
			break;
		case 8:
			break;
		case 9:
			for(int i = p.y-1; i < p.y+2; i++){
				for(int j = p.x-1; j < p.x+2; j++){
					if(i >= 0 && i < ROW && j >= 0 && j < COL){
						if(cardPlayer[i][j] * turn <= 0 && card[i][j].getASkill() != 1 && card[i][j].getASkill() != 5){
							range[i][j] = 1;
						}
					}
				}
			}
			break;
		case 10:
			for(int i = p.y-1; i < p.y+2; i++){
				for(int j = p.x-1; j < p.x+2; j++){
					if(i >= 0 && i < ROW && j >= 0 && j < COL){
						if(card[i][j] != null && cardPlayer[i][j] * turn >= 0){
							range[i][j] = 1;
						}
					}
				}
			}
			break;
		case 11:
			setRangeAllField(range,field);
			setRangeAllFriend(range,field);
			break;
		case 12:
			setRangeAllFriend(range,field);
			break;
		case 13:
			setRangeAllEnemy(range,field);
			break;
		case 14:
			setRangeAllEnemy(range,field);
			setRangeAllFriend(range,field);
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					turnTemp = cardPlayer[i][j] * turn;
					if((turnTemp == -2 || turnTemp > 0) && (card[i][j].getASkill() == 1 || card[i][j].getASkill() == 5)){
						range[i][j] = 0;
					}
				}
			}
			range[p.y][p.x] = 0;
			break;
		case TEST:
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					range[i][j] = 1;
				}
			}
			break;
		}
	}
	
	private int abs(int x){
		if(x < 0) return -x;
		else return x;
	}
	
	public void draw(int turn, int field, Graphics g){
		if((field * turn > 0) || (abs(field) == 2 || abs(field) == 4)){
			g.setFont(font);
			g.setColor(Color.white);
			for(int i = 0; i < WORD_ROW; i++){
				String mExplanation = getExplanation();
				if(mExplanation.length() < i * WORD_COUNT + WORD_COUNT){
					g.drawString(mExplanation.substring(i * WORD_COUNT , mExplanation.length()),
						M_SKILL_X, M_SKILL_Y + i * STRING_Y_SIZE);
					break;
				}else{
					g.drawString(mExplanation.substring(i * WORD_COUNT , i * WORD_COUNT + WORD_COUNT),
						M_SKILL_X, M_SKILL_Y + i * STRING_Y_SIZE);
				}
			}
		}
	}
}