import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Random;

public class AI implements Common{
	
	private Player player;
	private Field field;
	
	private static final double CallProbability = 0.2;
	private static final double MoveProbability = 0.2 + CallProbability;
	private static final double AttackProbability = 0.4 + MoveProbability;
	private static final double SkillProbability = 0.2 + AttackProbability;
	
	private Random rand = new Random();
	
	private int playerNumber;
	
	public AI(Player player, Field field) {
		this.player = player;
		this.field = field;
		playerNumber = player.getPlayerNumber();
	}
	
	public boolean isAITurn(){
		if(field.getTurn() * playerNumber > 0){
			return true;
		}
		return false;
	}
	
	public void myTurn(){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		boolean fieldExisting = true;
		boolean handExisting = true;
		int count = 0;
		if(player.getCurrentHandNumber() == MAX_HAND_NUMBER){
			field.setDecideSelect(0);
			field.selectDropCard((int)(Math.random() * (MAX_HAND_NUMBER+1)));
			field.handExchange();
			field.selectDropCard(MAX_HAND_NUMBER);
		}
		while(!turnEnd() && count < 20){
			if(Flag.gameSet || !Flag.game) return;
			double rand = Math.random();
			if(rand < CallProbability && handExisting){
				int currentHandNumber = player.getCurrentHandNumber();
				if(currentHandNumber == 0){
					handExisting = false;
					continue;
				}
				int n = (int)(Math.random() * currentHandNumber);
				int temp = 0;
				if(playerNumber > 0){
					temp = ROW;
				}
				if(playerNumber < 0){
					n += 2;
					temp = -1;
				}
				Point selCard = new Point(n,temp);
				field.setMenu(selCard);
				int[] menuAble = field.getMenuAble();
				if(playerNumber < 0){
					selCard.x -= 2;
				}
				if(menuAble[0] == 1){
					call(selCard);
				}
			}else if(fieldExisting){
				int flag = 0;
				Point p = null;
				Point enemyPlayer = null;
				Point[] temp = new Point[ROW*COL];
				int tempNumber = 0;
				for(int i = 0; i < ROW; i++){
					for(int j = 0; j < COL; j++){
						if(cardPlayer[i][j] * playerNumber < 0 && card[i][j].getCardNumber() == 0){
							enemyPlayer = new Point(j,i);
							i = ROW;
							break;
						}
					}
				}
				
				for(int i = 0; i < ROW; i++){
					for(int j = 0; j < COL; j++){
						if(cardPlayer[i][j] * playerNumber > 0){
							temp[tempNumber] = new Point(j,i);
							tempNumber++;
							flag++;
						}
					}
				}
				if(flag == 0){
					fieldExisting = false;
					continue;
				}
				double diff;
				int min;
				for(int i = 0; i < tempNumber-1; i++){
					diff = enemyPlayer.difference(temp[i]);
					min = i;
					for(int j = i+1; j < tempNumber; j++){
						if(enemyPlayer.difference(temp[j]) < diff){
							min = j;
						}
					}
					if(i != min){
						Point swap = temp[min];
						temp[min] = temp[i];
						temp[i] = swap;
					}
				}
				int selectP = 0;
				while(p == null){
					if(Math.random() < 0.3){
						p = temp[selectP];
					}
					selectP = (selectP+1)%tempNumber;
				}
				field.setMenu(p);
				int[] menuAble = field.getMenuAble();
				flag=0;
				for(int i = 1; i < 4; i++){
					if(menuAble[i] == 1){
						flag++;
					}
				}
				if(flag == 0){
					break;
				}
				boolean result = false;
				int cycleCount = 0;
				int select = 0;
				while(!result && cycleCount < 20){
					if(rand < MoveProbability) select = 1;
					else if(rand < AttackProbability) select = 2;
					else if(rand < SkillProbability) select = 3;
					switch(select){
					case 1:
						if(menuAble[1] == 1){
							result = move(p);
						}
						break;
					case 2:
						if(menuAble[2] == 1){
							result = attack(p);
						}
						break;
					case 3:
						if(menuAble[3] == 1){
							result = skill(p);
						}
						break;
					}
					cycleCount++;
				}
			}
			try{
				Thread.sleep(200);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			count++;
		}
	}
	
	public boolean turnEnd(){
		int cost = field.getCost();
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		for(int i = 0; i < player.getCurrentHandNumber(); i++){
			if(player.getHand(i).getCallCost() <= field.getCost()){
				return false;
			}
		}
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(cardPlayer[i][j] * playerNumber > 0){
					if(card[i][j].getMoveCost() <= cost){
						return false;
					}
					if(card[i][j].getAttackCost() <= cost){
						return false;
					}
					if(card[i][j].getSkillCost() <= cost){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean call(Point selCard){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		field.setSelectedMenu(0);
		field.setSelectedCard(selCard);
		field.setRange(selCard);
		int[][] range = field.getRange();
		Point myPlayer = null;
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(cardPlayer[i][j] * playerNumber > 0 && card[i][j].getCardNumber() == 0){
					myPlayer = new Point(j,i);
					break;
				}
			}
		}
		Point destination = null;
		double diff = Double.MAX_VALUE;
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(range[i][j] == 1){
					if(myPlayer.difference(new Point(j,i)) < diff){
						destination = new Point(j,i);
						diff = myPlayer.difference(destination);
					}
				}
			}
		}
		if(destination == null){
			return false;
		}else{
			field.selectTarget(destination);
			return true;
		}
	}
	
	public boolean move(Point selCard){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		Point enemyPlayer = new Point(0,0);
		field.setSelectedMenu(1);
		field.setSelectedCard(selCard);
		field.setRange(selCard);
		if(card[selCard.y][selCard.x].getCardNumber() == 0){
			if(Math.random() < 0.99){
				return false;
			}
		}
		int[][] range = field.getRange();
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(cardPlayer[i][j] * playerNumber < 0 && card[i][j].getCardNumber() == 0){
					enemyPlayer = new Point(j,i);
					break;
				}
			}
		}
		
		Point destination = null;
		double diff = Double.MAX_VALUE;
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(range[i][j] == 1){
					if(enemyPlayer.difference(new Point(j,i)) < diff){
						destination = new Point(j,i);
						diff = enemyPlayer.difference(destination);
					}
				}
			}
		}
		if(enemyPlayer.difference(selCard) < diff){
			return false;
		}
		
		if(destination != null){
			field.selectTarget(destination);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean attack(Point selCard){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		field.setSelectedMenu(2);
		field.setSelectedCard(selCard);
		field.setRange(selCard);
		int[][] range = field.getRange();
		Point destination = null;
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(range[i][j] == 1 && cardPlayer[i][j] * playerNumber < 0){
					if(card[i][j].getCardNumber() == 0){
						destination = new Point(j,i);
						i = ROW;
						break;
					}
					destination = new Point(j,i);
				}
			}
		}
		if(destination == null){
			return false;
		}else{
			field.selectTarget(destination);
			return true;
		}
	}
	
	public boolean skill(Point selCard){
		FieldCard[][] card = field.getCard();
		int[][] cardPlayer = field.getField();
		field.setSelectedMenu(3);
		field.setSelectedCard(selCard);
		field.setRange(selCard);
		int[][] range = field.getRange();
		MSkill mSkill = new MSkill(card[selCard.y][selCard.x].getMSkill());
		int flag = 0;
		
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(range[i][j] == 1 && cardPlayer[i][j] != 0){
					flag++;
				}
			}
		}
		
		int sumTargetSelect = mSkill.getTargetNumber();
		int sumFriendSelect = mSkill.getFriendNumber();
		int sumCardSelect = mSkill.getSumCard();
		int sumFieldSelect = mSkill.getSumField();
		
		if(sumFieldSelect == 0 && flag == 0){
			return false;
		}
		
		Point[] target = new Point[sumTargetSelect];
		Point[] friend = new Point[sumFriendSelect];
		Point[] cardSelect = new Point[sumCardSelect];
		Point[] fieldSelect = new Point[sumFieldSelect];
		int sumTargetSelected = 0;
		int sumFriendSelected = 0;
		int sumCardSelected = 0;
		int sumFieldSelected = 0;
		
		int cycleCount = 0;
		while(sumTargetSelected < sumTargetSelect && cycleCount < 100){
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(range[i][j] == 1 && cardPlayer[i][j] * playerNumber < 0){
						if(Math.random() < 0.1 && sumTargetSelected < sumTargetSelect){
							target[sumTargetSelected] = new Point(j,i);
							sumTargetSelected++;
							sumCardSelected++;
						}
					}
				}
			}
			cycleCount++;
		}
		if(cycleCount > 100){
			return false;
		}
		cycleCount = 0;
		while(sumFriendSelected < sumFriendSelect && cycleCount < 100){
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(range[i][j] == 1 && cardPlayer[i][j] * playerNumber > 0){
						if(Math.random() < 0.1 && sumFriendSelected < sumFriendSelect){
							friend[sumFriendSelected] = new Point(j,i);
							sumFriendSelected++;
							sumCardSelected++;
						}
					}
				}
			}
			cycleCount++;
		}
		if(cycleCount > 100){
			return false;
		}
		cycleCount = 0;
		while(sumCardSelected < sumCardSelect && cycleCount < 100){
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(range[i][j] == 1 && cardPlayer[i][j] != 0){
						if(Math.random() < 0.1 && sumCardSelected < sumCardSelect){
							cardSelect[sumCardSelected] = new Point(j,i);
							sumCardSelected++;
						}
					}
				}
			}
			cycleCount++;
		}
		if(cycleCount > 100){
			return false;
		}
		cycleCount = 0;
		Point enemyPlayer = null;
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(cardPlayer[i][j] * playerNumber < 0 && card[i][j].getCardNumber() == 0){
					enemyPlayer = new Point(j,i);
					i = ROW;
					break;
				}
			}
		}
		
		Point[] destination = new Point[ROW*COL];
		int destinationNumber = 0;
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(range[i][j] == 1){
					destination[destinationNumber] = new Point(j,i);
					destinationNumber++;
				}
			}
		}
		
		double diff;
		int min;
		for(int i = 0; i < destinationNumber-1; i++){
			diff = enemyPlayer.difference(destination[i]);
			min = i;
			for(int j = i+1; j < destinationNumber; j++){
				if(enemyPlayer.difference(destination[j]) < diff){
					min = j;
					diff = enemyPlayer.difference(destination[j]);
				}
				if(i != min){
					Point temp = destination[i];
					destination[i] = destination[min];
					destination[min] = temp;
				}
			}
		}
		if(enemyPlayer.difference(selCard) < enemyPlayer.difference(destination[0])){
			return false;
		}
		
		while(sumFieldSelected < sumFieldSelect && cycleCount < 100){
			for(int i = 0; i < destinationNumber; i++){
				if(sumFieldSelected < sumFieldSelect){
					fieldSelect[sumFieldSelected] = destination[i];
					sumFieldSelected++;
				}
			}
			cycleCount++;
		}
		if(cycleCount > 100){
			return false;
		}
		field.setTargetSelect(target);
		field.setFriendSelect(friend);
		field.setCardSelect(cardSelect);
		field.setFieldSelect(fieldSelect);
		
		field.selectTarget(new Point(0,0));
		return true;
		
	}
	
	
}
