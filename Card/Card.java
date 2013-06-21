import java.awt.Image;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;

public class Card{
	protected Image cardImage;
	protected Image elementImage;
	
	protected int cardNumber;
	protected int element;
	protected int power;
	protected int callCost;
	protected int moveCost;
	protected int attackCost;
	protected int skillCost;
	protected int moveUpRange;
	protected int moveDownRange;
	protected int moveLeftRange;
	protected int moveRightRange;
	protected int attackUpRange;
	protected int attackDownRange;
	protected int attackLeftRange;
	protected int attackRightRange;
	protected int mSkill;
	protected String mSkillName;
	protected int aSkill;
	protected String aSkillName;
	
	protected int tempElement;
	protected int tempPower;
	protected int tempCallCost;
	protected int tempMoveCost;
	protected int tempAttackCost;
	protected int tempSkillCost;
	protected int tempMoveUpRange;
	protected int tempMoveDownRange;
	protected int tempMoveLeftRange;
	protected int tempMoveRightRange;
	protected int tempAttackUpRange;
	protected int tempAttackDownRange;
	protected int tempAttackLeftRange;
	protected int tempAttackRightRange;
	protected int tempMSkill;
	protected int tempASkill;
	
	public Card(int cardNumber){
		this.cardNumber = cardNumber;
		loadCardData(cardNumber);
		tempInit();
	}
	
	public void calcMoveRange(int n){
		moveUpRange += n;
		moveDownRange += n;
		moveLeftRange += n;
		moveRightRange += n;
	}
	
	public void setElement(int n){
		element = n;
	}
	
	public int mid(int a, int b, int c){
		if(a < b && b < c) return b;
		if(a < c && c < b) return c;
		if(b < a && a < c) return a;
		if(b < c && c < a) return c;
		if(c < a && a < b) return a;
		if(c < b && b < a) return b;
		return 0;
	}
	
	public boolean powerCalc(int p){
		power = mid(0,power+p,9999);
		if(power == 0) return true;
		return false;
	}
	
	public void turnEnd(){
		element -= tempElement;
		power -= tempPower;
		callCost -= tempCallCost;
		moveCost -= tempMoveCost;
		attackCost -= tempAttackCost;
		skillCost -= tempSkillCost;
		moveUpRange -= tempMoveUpRange;
		moveDownRange -= tempMoveDownRange;
		moveLeftRange -= tempMoveLeftRange;
		moveRightRange -= tempMoveRightRange;
		attackUpRange -= tempAttackUpRange;
		attackDownRange -= tempAttackDownRange;
		attackLeftRange -= tempAttackLeftRange;
		attackRightRange -= tempAttackRightRange;
		mSkill -= tempMSkill;
		aSkill -= tempASkill;
		tempInit();
	}
	
	public void tempInit(){
		tempElement = 0;
		tempPower = 0;
		tempCallCost = 0;
		tempMoveCost = 0;
		tempAttackCost = 0;
		tempSkillCost = 0;
		tempMoveUpRange = 0;
		tempMoveDownRange = 0;
		tempMoveLeftRange = 0;
		tempMoveRightRange = 0;
		tempAttackUpRange = 0;
		tempAttackDownRange = 0;
		tempAttackLeftRange = 0;
		tempAttackRightRange = 0;
		tempMSkill = 0;
		tempASkill = 0;
	}
	
	public Image getCardImage(){return cardImage;}
	public int getCardNumber(){return cardNumber;}
	public int getElement(){return element;}
	public int getPower(){return power;}
	public int getCallCost(){return callCost;}
	public int getMoveCost(){return moveCost;}
	public int getAttackCost(){return attackCost;}
	public int getSkillCost(){return skillCost;}
	public int getMoveUpRange(){return moveUpRange;}
	public int getMoveDownRange(){return moveDownRange;}
	public int getMoveLeftRange(){return moveLeftRange;}
	public int getMoveRightRange(){return moveRightRange;}
	public int getAttackUpRange(){return attackUpRange;}
	public int getAttackDownRange(){return attackDownRange;}
	public int getAttackLeftRange(){return attackLeftRange;}
	public int getAttackRightRange(){return attackRightRange;}
	public int getMSkill(){return mSkill;}
	public int getASkill(){return aSkill;}
	
	public void loadCardData(int n){
		String fileName = "card" + n;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
				getClass().getResourceAsStream("data/card/" + fileName + ".card")));
			String line;
			ImageIcon icon;
			
			try{
				icon = new ImageIcon(getClass().getResource("image/card/" + fileName + ".gif"));
				cardImage = icon.getImage();
			}catch(NullPointerException e){
				icon = new ImageIcon(getClass().getResource("image/card/noImage.gif"));
				cardImage = icon.getImage();
			}
			
			icon = new ImageIcon(getClass().getResource("image/element.gif"));
			elementImage = icon.getImage();
			
			line = br.readLine();
			element = Integer.parseInt(line);
			line = br.readLine();
			power = Integer.parseInt(line);
			line = br.readLine();
			callCost = Integer.parseInt(line);
			line = br.readLine();
			moveCost = Integer.parseInt(line);
			line = br.readLine();
			attackCost = Integer.parseInt(line);
			line = br.readLine();
			skillCost = Integer.parseInt(line);
			line = br.readLine();
			moveUpRange = Integer.parseInt(line);
			line = br.readLine();
			moveDownRange = Integer.parseInt(line);
			line = br.readLine();
			moveLeftRange = Integer.parseInt(line);
			line = br.readLine();
			moveRightRange = Integer.parseInt(line);
			line = br.readLine();
			attackUpRange = Integer.parseInt(line);
			line = br.readLine();
			attackDownRange = Integer.parseInt(line);
			line = br.readLine();
			attackLeftRange = Integer.parseInt(line);
			line = br.readLine();
			attackRightRange = Integer.parseInt(line);
			line = br.readLine();
			mSkillName = line;
			line = br.readLine();
			mSkill = Integer.parseInt(line);
			line = br.readLine();
			aSkillName = line;
			line = br.readLine();
			aSkill = Integer.parseInt(line);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
