import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Font;

import javax.swing.ImageIcon;

public class Field implements Common, Runnable{
	
	private Image fieldImage;
	private Image cardImage;
	private Image menuImage;
	private Image turnEndImage;
	private Image dropCardImage;
	
	private boolean attackAble;
	
	private int decideSelect;
	
	private int sumTargetSelect;
	private int sumFriendSelect;
	
	private int cost;
	private int selectedMenu;
	private int selectedDrop;
	
	private Effect effect = null;
	
	private int sumCardSelect;
	private int sumFieldSelect;
	
	private AI cpu0 = null;
	private AI cpu1 = null;
	
	private Point sumCard[];
	private Point sumField[];
	
	/* field[][]        */
	/* 0 : 空欄         */
	/* + : 自分のカード */
	/* - : 相手のカード */
	private int field[][];
	private int range[][];
	private int selectedField;
	private Point selected;
	private FieldCard card[][];
	private FieldCard selectedCard;
	private FieldCard drawCard;
	
	private boolean menu;
	private boolean endConfirm;
	
	private int turn;
	private int endConfirmCursor;
	
	private boolean decideDropCard;
	
	private Point targetCard[];
	private Point friendCard[];
	private MSkill selectedMSkill;
	
	private Player player0;
	private Player player1;
	
	private int menuAble[];
	
	private Point cursor;
	private int menuCursor;
	
	private Point select;
	
	public Field() {
		loadImage();
	}
	
	public void init(){
		field = new int[ROW][COL];
		range = new int[ROW][COL];
		card = new FieldCard[ROW][COL];
		selected = new Point(-1,-1);
		cursor = new Point(-1,-1);
		card = new FieldCard[ROW][COL];
		menuAble = new int[MENU_NUMBER];
		attackAble = false;
		selectedField = 0;
		selectedMenu = -1;
		cost = Flag.initCost;
		turn = 1;
		menu = false;
		player0 = new Player(1,this);
		player1 = new Player(-1,this);
		select = new Point(-1,-1);
		selectedDrop = MAX_HAND_NUMBER;
		setPlayer();
		new Thread(this).start();
		while(!Flag.keyInputable);
		if(Flag.player2 == 1){
			cpu1 = new AI(player1,this);
		}
		if(Flag.player1 == 1){
			cpu0 = new AI(player0,this);
			cpu0.myTurn();
			endTurn();
		}
	}
	public void run(){
		Flag.keyInputable = false;
		setEffect(BATTLE_START);
		Flag.keyInputable = true;
		while(!Flag.gameSet){
			try{
				Thread.sleep(50);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		endGame();
	}
	
	public void setSelectedCard(Point p){
		selectedCard = null;
		if(p.x >= 0){
			selected = new Point(p.x,p.y);
			switch(p.y){
			case -1:
				selectedCard = player1.getHand(p.x);
				selectedField = -1;
				break;
			case ROW:
				selectedCard = player0.getHand(p.x);
				selectedField = 1;
				break;
			default:
				selectedCard = card[p.y][p.x];
				selectedField = field[p.y][p.x];
				break;
			}
			selectedMSkill = new MSkill(selectedCard.getMSkill());
			sumTargetSelect = selectedMSkill.getTargetNumber();
			sumFriendSelect = selectedMSkill.getFriendNumber();
			sumCardSelect = selectedMSkill.getSumCard();
			sumFieldSelect = selectedMSkill.getSumField();
			targetCard = new Point[sumTargetSelect];
			friendCard = new Point[sumFriendSelect];
			sumCard = new Point[sumCardSelect];
			sumField = new Point[sumFieldSelect];
		}
	}
	
	public void setTargetSelect(Point[] p){
		targetCard = p;
	}
	public void setFriendSelect(Point[] p){
		friendCard = p;
	}
	public void setCardSelect(Point[] p){
		sumCard = p;
	}
	public void setFieldSelect(Point[] p){
		sumField = p;
	}
	
	public void setSelectedMenu(int n){
		selectedMenu = n;
		menuCursor = n;
	}
	
	public boolean getDropCard(){
		if(player0.getWarning() || player1.getWarning()) return true;
		else return false;
	}
	
	public boolean getDecideDropCard(){
		return decideDropCard;
	}
	
	public void setDecideSelect(int s){
		decideSelect = s;
	}
	
	public void selectDropCard(int s){
		selectedDrop = s;
	}
	
	public int getCost(){
		return cost;
	}
	
	public boolean handExchange(){
		decideDropCard = false;
		if(decideSelect == 0){
			if(player0.getWarning()){
				player0.handExchange(selectedDrop);
			}
			if(player1.getWarning()){
				player1.handExchange(selectedDrop);
			}
			return true;
		}
		decideSelect = 0;
		return false;
	}
	public void decideDropCard(){
		decideDropCard = true;
	}
	
	public boolean getEndConfirm(){
		return endConfirm;
	}
	
	public void selectEndConfirm(int s){
		endConfirmCursor = s;
	}
	
	public void deleteEndConfirm(){
		endConfirmCursor = 0;
		endConfirm = false;
	}
	
	public void setEndConfirm(){
		endConfirm = true;
	}
	
	public void decideEndConfirm(){
		if(endConfirmCursor == 0) endTurn();
		deleteEndConfirm();
	}
	
	public void costCalc(int p){
		cost += p;
	}
	
	public void endGame(){
		if(turn == -1){
			setEffect(BLUE_WIN);
		}else{
			setEffect(RED_WIN);
		}
		cpu0 = null;
		cpu1 = null;
		Flag.gameOver = true;
		return;
	}
	
	
	public void endTurn(){
		endConfirm = false;
		menu = false;
		selectedMenu = -1;
		if(!Flag.game || Flag.gameSet) return;
		attackAble = true;
		Flag.keyInputable = false;
		cost = Flag.initCost;
		turn *= -1;
		if(turn == -1){
			setEffect(BLUE_TURN);
			player1.drawHand();
		}else{
			setEffect(RED_TURN);
			player0.drawHand();
		}
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(card[i][j] != null){
					if(field[i][j] * turn < 0) card[i][j].turnEnd();
					new ASkill(card[i][j].getASkill()).exe(new Point(j,i),this);
				}
			}
		}
		if(cpu0 != null){
			if(cpu0.isAITurn()){
				cpu0.myTurn();
				endTurn();
			}
		}
		if(cpu1 != null){
			if(cpu1.isAITurn()){
				cpu1.myTurn();
				endTurn();
			}
		}
		Flag.keyInputable = true;
	}
	
	private void setPlayer(){
		field[11][5] = 2;
		card[11][5] = new FieldCard(0);
		field[0][5] = -2;
		card[0][5] = new FieldCard(0);
	}
	
	public int[][] getRange(){
		return range;
	}
	
	public FieldCard[][] getCard(){
		return card;
	}
	
	public int[][] getField(){
		return field;
	}
	public int getTurn(){
		return turn;
	}
	
	public boolean setCard(Point p, int playerNumber, FieldCard card){
		if(range[p.y][p.x] == 1){
			field[p.y][p.x] = playerNumber;
			this.card[p.y][p.x] = card;
			return true;
		}
		return false;
	}
	
	public boolean getMenu(){
		return menu;
	}
	
	public void select(int y){
		menuCursor = y;
	}
		
	public void select(Point p){
		cursor = p;
		switch(p.y){
		case -1:
			if(p.x >= 2 && p.x < MAX_HAND_NUMBER + 2){
				if(player1.getHand(p.x-2) != null){
					select = new Point(p.x-2,p.y);
				}else{
					select = new Point(-1,-1);
				}
			}else{
				select = new Point(-1,-1);
			}
			break;
		case ROW:
			if(p.x < MAX_HAND_NUMBER){
				if(player0.getHand(p.x) != null){
					select = p;
				}else{
					select = new Point(-1,-1);
				}
			}else{
				select = new Point(-1,-1);
			}
			break;
		default:
			if(field[p.y][p.x] != 0){
				select = p;
			}else{
				select = new Point(-1,-1);
			}
		}
	}
	
	public int getSelectedMenu(){
		return selectedMenu;
	}
	
	public Point[] getTargetCard(){
		return targetCard;
	}
	
	public Point[] getFriendCard(){
		return friendCard;
	}
	
	public Point[] getSumCard(){
		return sumCard;
	}
	
	public Point[] getSumField(){
		return sumField;
	}
	public void cancelSelectTarget(){
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(abs(field[i][j]) > 2){
					field[i][j] -= 2 * (field[i][j]/abs(field[i][j]));
				}
			}
		}
		selected = null;
		selectedField = 0;
		selectedCard = null;
		selectedMSkill = null;
		targetCard = null;
		friendCard = null;
		sumTargetSelect = 0;
		sumFriendSelect = 0;
		selectedMenu = -1;
		menuCursor = 0;
		select(cursor);
	}
	
	public FieldCard getSelectedCard(){
		return selectedCard;
	}
	public Point getSelect(){
		return selected;
	}
	public void setEffect(int type){
		Flag.keyInputable = false;
		effect = new Effect();
		effect.setEffectType(type);
		while(effect != null);
		Flag.keyInputable = true;
	}
	public void setEffect(Point p, int type){
		Flag.keyInputable = false;
		effect = new Effect();
		effect.setPoint(p);
		effect.setEffectType(type);
		while(effect != null);
		Flag.keyInputable = true;
	}
	
	public void setEffect(Point p, int damage, int type){
		Flag.keyInputable = false;
		effect = new Effect();
		effect.setPoint(p);
		effect.setDamage(damage);
		effect.setEffectType(type);
		while(effect != null);
		Flag.keyInputable = true;
	}
	
	private boolean attack(Point p){
		if(range[p.y][p.x] == 1 && p.y >= 0 && p.y < ROW){
			if(field[p.y][p.x] * selectedField < 0){
				double plus = 1;
				int relation = card[p.y][p.x].getElement()-selectedCard.getElement();
				if(relation == 1 || relation == -3) plus = 0.5;
				if(relation == -1 || relation == 3) plus = 2;
				if(card[p.y][p.x].getElement() == 0 || card[selected.y][selected.x].getElement() == 0) plus = 1;
				ASkill defenceASkill = new ASkill(card[p.y][p.x].getASkill());
				int finalDamage = (int)(-selectedCard.getPower()*plus*defenceASkill.defenceMul(selected,p,this)+defenceASkill.defenceAdd(selected,p,this));
				setEffect(p,ATTACK);
				setEffect(p,finalDamage,DAMAGE);
				if(card[p.y][p.x].powerCalc(finalDamage)){
					if(field[p.y][p.x] < 0){
						player1.goGrave(card[p.y][p.x].getCardNumber());
					}
					if(field[p.y][p.x] > 0){
						player0.goGrave(card[p.y][p.x].getCardNumber());
					}
					card[p.y][p.x] = null;
					field[p.y][p.x] = 0;
				}else{
					field[p.y][p.x] = (turn * -1) << 1;
				}
				return true;
			}
		}
		return false;
	}
	
	public void skillDamage(int element, int power, Point p){
		double plus = 1;
		int finalDamage;
		int relation = card[p.y][p.x].getElement()-element;
		if(relation == 1 || relation == -3) plus = 0.5;
		if(relation == -1 || relation == 3) plus = 2;
		if(card[p.y][p.x].getElement() == 0 || element == 0) plus = 1;
		if(card[p.y][p.x].getASkill() == 1) plus = 0;
		ASkill defenceASkill = new ASkill(card[p.y][p.x].getASkill());
		finalDamage = (int)(-power*plus*defenceASkill.defenceMul(element,power,p,this)+defenceASkill.defenceAdd(element,power,p,this));
		setEffect(p,ATTACK);
		setEffect(p,finalDamage,DAMAGE);
		if(card[p.y][p.x].powerCalc(finalDamage)){
			if(field[p.y][p.x] < 0){
				player1.goGrave(card[p.y][p.x].getCardNumber());
			}
			if(field[p.y][p.x] > 0){
				player0.goGrave(card[p.y][p.x].getCardNumber());
			}
			card[p.y][p.x] = null;
			field[p.y][p.x] = 0;
		}else{
			field[p.y][p.x] = (field[p.y][p.x]/abs(field[p.y][p.x])) << 1;
		}
	}
	
	public Player getPlayer(int n){
		if(n == 1) return player0;
		if(n == -1) return player1;
		return null;
	}
	
	public void move(Point destination, Point p){
		if(setCard(destination,field[p.y][p.x],card[p.y][p.x])){
			deleteCard(p);
			cancelSelectTarget();
		}
	}
	
	public void selectTarget(Point p){
		if(p.y == -1 || p.y == ROW) return;
		switch(selectedMenu){
		case 0:
			switch(turn){
			case -1:
				if(setCard(p,-1,selectedCard)){
					cost -= selectedCard.getCallCost();
					player1.deleteHand(selected.x);
					cancelSelectTarget();
				}
				break;
			case 1:
				if(setCard(p,1,selectedCard)){
					cost -= selectedCard.getCallCost();
					player0.deleteHand(selected.x);
					cancelSelectTarget();
				}
				break;
			}
			break;
		case 1:
			if(setCard(p,selectedField,selectedCard)){
				cost -= selectedCard.getMoveCost();
				deleteCard(selected);
				cancelSelectTarget();
			}
		case 2:
			if(attack(p)){
				cost -= selectedCard.getAttackCost();
				field[selected.y][selected.x] = turn << 1;
				if(field[p.y][p.x] != 0){
					field[p.y][p.x] = (turn * -1) << 1;
				}
				cancelSelectTarget();
			}
			break;
		case 3:
			boolean checkTarget = false;
			boolean checkFriend = false;
			boolean checkCard = false;
			boolean checkField = false;
			if(range[p.y][p.x] == 1){
				if(field[p.y][p.x] != 0){
					int i = 0;
					if(sumCardSelect > 0){
						while(i < sumCardSelect && sumCard[i] != null) i++;
						if(i < sumCardSelect && abs(field[p.y][p.x]) <= 2){
							field[p.y][p.x] = (field[p.y][p.x]/abs(field[p.y][p.x])) * 2;
							sumCard[i] = new Point(p.x,p.y);
						}
					}
				}
				if(field[p.y][p.x] == 0){
					int i = 0;
					if(sumFieldSelect > 0){
						while(i < sumFieldSelect && sumField[i] != null) i++;
						if(i < sumFieldSelect){
							sumField[i] = new Point(p.x,p.y);
						}
					}
				}
				if(field[p.y][p.x] * selectedField > 0){
					int i = 0;
					if(sumFriendSelect > 0){
						while(i < sumFriendSelect && friendCard[i] != null) i++;
						if(i < sumFriendSelect && abs(field[p.y][p.x]) <= 2){
							field[p.y][p.x] += 2 * turn;
							friendCard[i] = new Point(p.x,p.y);
						}
					}
				}
				if(field[p.y][p.x] * selectedField < 0){
					int i = 0;
					if(sumTargetSelect > 0){
						while(i < sumTargetSelect && targetCard[i] != null) i++;
						if(i < sumTargetSelect && abs(field[p.y][p.x]) <= 2){
							field[p.y][p.x] -= 2 * turn;
							targetCard[i] = new Point(p.x,p.y);
						}
					}
				}
			}
			if(sumFriendSelect == 0 || friendCard[sumFriendSelect-1] != null){
				checkFriend = true;
			}
			if(sumTargetSelect == 0 || targetCard[sumTargetSelect-1] != null){
				checkTarget = true;
			}
			if(sumCardSelect == 0 || sumCard[sumCardSelect-1] != null){
				checkCard = true;
			}
			if(sumFieldSelect == 0 || sumField[sumFieldSelect-1] != null){
				checkField = true;
			}
			if(checkFriend && checkTarget && checkField && checkCard){
				cost -= selectedCard.getSkillCost();
				if(field[selected.y][selected.x] != 0){
					field[selected.y][selected.x] = turn << 1;
				}
				selectedMSkill.exe(selected,this);
				cancelSelectTarget();
			}
			break;
		}
	}
	
	public void deleteCard(Point p){
		field[p.y][p.x] = 0;
		card[p.y][p.x] = null;
		select(cursor);
	}
	
	public void decideMenu(){
		switch(menuCursor){
		case 0:
			if(menuAble[0] == 1){
				selectedMenu = 0;
				deleteMenu();
				return;
			}
			break;
		case 1:
			if(menuAble[1] == 1){
				selectedMenu = 1;
				deleteMenu();
				return;
			}
			break;
		case 2:
			if(menuAble[2] == 1){
				selectedMenu = 2;
				deleteMenu();
				return;
			}
			break;
		case 3:
			if(menuAble[3] == 1){
				selectedMenu = 3;
				deleteMenu();
				return;
			}
			break;
		}
		selectedMenu = -1;
		menuCursor = 0;
		deleteMenu();
	}
	
	public int[] getMenuAble(){
		return menuAble;
	}
	
	public void setMenu(Point p){
		switch(p.y){
		case -1:
			if(p.x >= 2){
				FieldCard temp = player1.getHand(p.x-2);
				if(temp == null || turn != -1){
					for(int i = 0; i < MENU_NUMBER-1; i++){
						menuAble[i] = 0;
					}
					menuAble[MENU_NUMBER-1] = 1;
				}else{
					int tempCost = temp.getCallCost();
					if(tempCost != 0 && tempCost <= cost){
						menuAble[0] = 1;
					}else{
						menuAble[0] = 0;
					}
					menuAble[1] = 0;
					menuAble[2] = 0;
					menuAble[3] = 0;
					menuAble[4] = 1;
				}
			}
			break;
		case ROW:
			if(p.x < MAX_HAND_NUMBER){
				FieldCard temp = player0.getHand(p.x);
				if(temp == null || turn != 1){
					for(int i = 0; i < MENU_NUMBER-1; i++){
						menuAble[i] = 0;
					}
					menuAble[MENU_NUMBER-1] = 1;
				}else{
					int tempCost = temp.getCallCost();
					if(tempCost != 0 && tempCost <= cost){
						menuAble[0] = 1;
					}else{
						menuAble[0] = 0;
					}
					menuAble[1] = 0;
					menuAble[2] = 0;
					menuAble[3] = 0;
					menuAble[4] = 1;
				}
			}
			break;
		default:
			if(field[p.y][p.x] * turn <= 0){
				for(int i = 0; i < MENU_NUMBER-1; i++){
					menuAble[i] = 0;
				}
				menuAble[MENU_NUMBER-1] = 1;
			}else{
				FieldCard temp = card[p.y][p.x];
				menuAble[0] = 0;
				int tempCost;
				tempCost = temp.getMoveCost();
				if(tempCost != 0 && tempCost <= cost){
					menuAble[1] = 1;
				}else{
					menuAble[1] = 0;
				}
				tempCost = temp.getAttackCost();
				if(tempCost != 0 && tempCost <= cost && attackAble){
					menuAble[2] = 1;
				}else{
					menuAble[2] = 0;
				}
				tempCost = temp.getSkillCost();
				if(tempCost != 0 && tempCost <= cost){
					menuAble[3] = 1;
				}else{
					menuAble[3] = 0;
				}
				menuAble[4] = 1;
			}
			break;
		}
		menu = true;
	}
	
	public void deleteMenu(){
		menu = false;
		if(selectedMenu < 0){
			menuCursor = 0;
		}
	}
	
	private int abs(int x){
		if(x < 0) return -x;
		else return x;
	}
	
	public void draw(Graphics g){
		g.setFont(font);
		g.setColor(Color.white);
		g.drawImage(fieldImage,0,0,null);
		g.drawString(cost+"",REST_COST_X, REST_COST_Y);
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(selectedMenu == -1) range[i][j] = 0;
				if(field[i][j] != 0){
					int player;
					if(field[i][j] < 0) player = 1;
					else player = 0;
					g.drawImage(cardImage,
						FIELD_X + j * CS , FIELD_Y + i * CS,
						FIELD_X + j * CS + CS, FIELD_Y + i * CS + CS,
						(abs(field[i][j]) - 1) * CS, player * CS,
						(abs(field[i][j]) - 1) * CS + CS, player * CS + CS, null);
				}
			}
		}
		
		for(int i = 0; i < MAX_HAND_NUMBER && player0.getHand(i) != null; i++){
			g.drawImage(cardImage,
				HAND1_X + i * CS , HAND1_Y,
				HAND1_X + i * CS + CS, HAND1_Y + CS,
				0,0,CS,CS,null);
		}
		
		for(int i = 0; i < MAX_HAND_NUMBER && player1.getHand(i) != null; i++){
			g.drawImage(cardImage,
				HAND2_X + i * CS , HAND2_Y,
				HAND2_X + i * CS + CS, HAND2_Y + CS,
				0,CS,CS,CS<<1,null);
		}
		
		if(player0.getCurrentDeck() < MAX_DECK_NUMBER){
			g.drawImage(cardImage,
				DECK1_X, DECK1_Y,
				DECK1_X + CS, DECK1_Y + CS,
				0,0,CS,CS,null);
		}
		if(player0.getCurrentGrave() > 0){
			g.drawImage(cardImage,
				GRAVE1_X, GRAVE1_Y,
				GRAVE1_X + CS, GRAVE1_Y + CS,
				0,0,CS,CS,null);
		}
		
		if(player1.getCurrentDeck() < MAX_DECK_NUMBER){
			g.drawImage(cardImage,
				DECK2_X, DECK2_Y,
				DECK2_X + CS, DECK2_Y + CS,
				0,CS,CS,CS<<1,null);
		}
		
		if(player1.getCurrentGrave() > 0){
			g.drawImage(cardImage,
				GRAVE2_X, GRAVE2_Y,
				GRAVE2_X + CS, GRAVE2_Y + CS,
				0,CS,CS,CS<<1,null);
		}
		
		if(selectedMenu == -1){
			setSelectedCard(select);
		}
		
		drawCard = null;
		if(select.x >= 0 && !player0.getWarning() && !player1.getWarning()){
			switch(select.y){
			case -1:
				drawCard = player1.getHand(select.x);
				if(drawCard == null) break;
				drawCard.draw(turn,-1,g);
				new MSkill(drawCard.getMSkill()).draw(turn,-1,g);
				new ASkill(drawCard.getASkill()).draw(turn,-1,g);
				break;
			case ROW:
				drawCard = player0.getHand(select.x);
				if(drawCard == null) break;
				drawCard.draw(turn,1,g);
				new MSkill(drawCard.getMSkill()).draw(turn,1,g);
				new ASkill(drawCard.getASkill()).draw(turn,1,g);
				break;
			default:
				drawCard = card[select.y][select.x];
				if(drawCard == null) break;
				drawCard.draw(turn,field[select.y][select.x],g);
				new MSkill(drawCard.getMSkill()).draw(turn,field[select.y][select.x],g);
				new ASkill(drawCard.getASkill()).draw(turn,field[select.y][select.x],g);
				break;
			}
		}
		if(effect != null){
			effect.draw(g);
			if(effect.getEnd()) effect = null;
		}
		if((player0.getWarning()) && (cpu0 == null || !cpu0.isAITurn()) && (cpu1 == null || !cpu1.isAITurn())) drawDrop(1,g);
		if((player1.getWarning()) && (cpu0 == null || !cpu0.isAITurn()) && (cpu1 == null || !cpu1.isAITurn())) drawDrop(-1,g);
		if((menu || selectedMenu >= 0) && (cpu0 == null || !cpu0.isAITurn()) && (cpu1 == null || !cpu1.isAITurn())) drawMenu(g);
		if((endConfirm) && (cpu0 == null || !cpu0.isAITurn()) && (cpu1 == null || !cpu1.isAITurn())) drawEndConfirm(g);
	}
	
	public void drawDrop(int player, Graphics g){
		FieldCard dropCard = null;
		int decide = 0;
		if(decideDropCard){
			decide = 1;
		}
		g.drawImage(dropCardImage,120,270,420,370,
			0,decide * 100,300, decide * 100 + 100,
			null);
		Graphics2D g2 = (Graphics2D) g;
		Point dropTemp = new Point(190 ,305 + 200 * player);
		g.drawImage(cardImage,
			dropTemp.x, dropTemp.y, dropTemp.x + CS, dropTemp.y + CS,
			0,(abs(player-1)/2) * CS,CS,(abs(player-1)/2) * CS + CS,
			null);
			
		g2.setComposite(clear);
		g2.setColor(Color.red);
		switch(player){
		case -1:
			while(dropCard == null){
				if(selectedDrop < MAX_HAND_NUMBER){
					g2.fillRect(HAND2_X + selectedDrop * CS, HAND2_Y, CS, CS);
					g2.setComposite(opaque);
					dropCard = player1.getHand(selectedDrop);
				}else{
					g2.fillRect(190, 105, CS, CS);
					g2.setComposite(opaque);
					dropCard = player1.getTemp();
				}
			}
			dropCard.draw(turn,-1,g);
			new MSkill(dropCard.getMSkill()).draw(turn,-1,g);
			new ASkill(dropCard.getASkill()).draw(turn,-1,g);
			break;
		case 1:
			while(dropCard == null){
				if(selectedDrop < MAX_HAND_NUMBER){
					g2.fillRect(HAND1_X + selectedDrop * CS, HAND1_Y, CS, CS);
					g2.setComposite(opaque);
					dropCard = player0.getHand(selectedDrop);
				}else{
					g2.fillRect(190, 505, CS, CS);
					g2.setComposite(opaque);
					dropCard = player0.getTemp();
				}
			}
			dropCard.draw(turn,1,g);
			new MSkill(dropCard.getMSkill()).draw(turn,1,g);
			new ASkill(dropCard.getASkill()).draw(turn,1,g);
			break;
		}
		if(decideDropCard){
			g2.setComposite(clear);
			g2.setColor(Color.red);
			g2.fillRect(184 + decideSelect * 91, 327, 75, 25);
			g2.setComposite(opaque);
		}
	}
	
	private void drawEndConfirm(Graphics g){
		g.drawImage(turnEndImage,220,270,null);
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(clear);
		g2.setColor(Color.red);
		int offset = 0;
		if(endConfirmCursor == 1) offset = 92;
		g2.fillRect(220 + 14 + offset, 270 + 54, 75, 25);
		g2.setComposite(opaque);
	}
	
	private void drawMenu(Graphics g){
		int menuX = FIELD_X + cursor.x * CS + CS;
		int menuY = FIELD_Y + cursor.y * CS - CS;
		int menuCursorX = menuX;
		int menuCursorY = menuY + MENU_Y_OFFSET + menuCursor * MENU_Y_SIZE;
		if(menu){
			g.drawImage(menuImage,
				menuX, menuY,
				menuX + MENU_X_SIZE, menuY + MENU_Y_OFFSET,
				0,0,MENU_X_SIZE,MENU_Y_OFFSET,null);
			for(int i = 0; i < MENU_NUMBER; i++){
				g.drawImage(menuImage,
					menuX, menuY + MENU_Y_OFFSET + i * MENU_Y_SIZE,
					menuX + MENU_X_SIZE, menuY + MENU_Y_OFFSET + (i+1) * MENU_Y_SIZE,
					menuAble[i] * MENU_X_SIZE ,MENU_Y_OFFSET + i * MENU_Y_SIZE,
					menuAble[i] * MENU_X_SIZE + MENU_X_SIZE, MENU_Y_OFFSET + (i+1) * MENU_Y_SIZE,null);
			}
			g.drawImage(menuImage,
				menuX, menuY + MENU_Y_OFFSET + MENU_NUMBER * MENU_Y_SIZE,
				menuX + MENU_X_SIZE, menuY + MENU_Y_OFFSET + (MENU_NUMBER+1) * MENU_Y_SIZE,
				0, MENU_Y_OFFSET + MENU_NUMBER * MENU_Y_SIZE,
				MENU_X_SIZE, MENU_NUMBER * MENU_Y_SIZE + MENU_Y_OFFSET * 2,null);
		}
		
		if(selectedCard != null){
			setRange(selected);
			boolean showCost = false;
			switch(selected.y){
			case -1:
				if(turn < 0) showCost = true;
				break;
			case ROW:
				if(turn > 0) showCost = true;
				break;
			default:
				if((turn * field[selected.y][selected.x] > 0) || (abs(field[selected.y][selected.x]) == 2))
				showCost = true;
				break;
			}
			if(showCost){
				switch(menuCursor){
				case 0:
					g.drawString(selectedCard.getCallCost()+"", SPEND_COST_X,SPEND_COST_Y);
					break;
				case 1:
					g.drawString(selectedCard.getMoveCost()+"", SPEND_COST_X,SPEND_COST_Y);
					break;
				case 2:
					g.drawString(selectedCard.getAttackCost()+"", SPEND_COST_X,SPEND_COST_Y);
					break;
				case 3:
					g.drawString(selectedCard.getSkillCost()+"", SPEND_COST_X,SPEND_COST_Y);
					break;
				case 4:
					g.drawString("0", SPEND_COST_X,SPEND_COST_Y);
					break;
				}
			}
		}
		g.drawString(cost+"",REST_COST_X, REST_COST_Y);
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(clear);
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				g2.setColor(Color.red);
				for(int k = 0; k < sumFieldSelect && sumField[k] != null; k++){
					if(sumField[k].y == i && sumField[k].x == j){
						g2.setColor(Color.blue);
						break;
					}
				}
				if(range[i][j] != 0){
					g2.fillRect(FIELD_X + j * CS, FIELD_Y + i * CS, CS, CS);
				}
			}
		}
		g2.setColor(Color.red);
		if(menu) g2.fillRect(menuCursorX,menuCursorY,MENU_X_SIZE,MENU_Y_SIZE);
		g2.setComposite(opaque);
	}
	
	public void setRange(Point p){
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				range[i][j] = 0;
			}
		}
		if(menuAble[menuCursor] == 0) return;
		int moveASkill = 0;
		if(selectedCard != null){
			moveASkill = selectedCard.getASkill();
		}
		int upRange = 0;
		int downRange = 0;
		int leftRange = 0;
		int rightRange = 0;
		switch(menuCursor){
		case 0:
			int area = 0;
			FieldCard tempHand = null;
			switch(p.y){
			case -1:
				area = 0;
				tempHand = player1.getHand(p.x);
				break;
			case ROW:
				area = ROW-3;
				tempHand = player0.getHand(p.x);
				break;
			}
			if(tempHand != null){
				for(int i = 0; i < 3; i++){
					for(int j = 0; j < COL; j++){
						if(field[i+area][j] == 0){
							range[i+area][j] = 1;
						}
					}
				}
			}
			break;
		case 1:
			if(p.y >= 0 && p.y < ROW && selectedCard != null){
				if(field[p.y][p.x] < 0){
					upRange = selectedCard.getMoveDownRange();
					downRange = selectedCard.getMoveUpRange();
					leftRange = selectedCard.getMoveRightRange();
					rightRange = selectedCard.getMoveLeftRange();
				}
				if(field[p.y][p.x] > 0){
					upRange = selectedCard.getMoveUpRange();
					downRange = selectedCard.getMoveDownRange();
					leftRange = selectedCard.getMoveLeftRange();
					rightRange = selectedCard.getMoveRightRange();
				}
				for(int i = 0; i < upRange; i++){
					if(p.y-(i+1) < 0) break;
					if(field[p.y-(i+1)][p.x] != 0){
						if(moveASkill == 2){
							continue;
						}else{
							break;
						}
					}
					range[p.y-(i+1)][p.x] = 1;
				}
				for(int i = 0; i < downRange; i++){
					if(p.y+(i+1) >= ROW) break;
					if(field[p.y+(i+1)][p.x] != 0){
						if(moveASkill == 2){
							continue;
						}else{
							break;
						}
					}
					range[p.y+(i+1)][p.x] = 1;
				}
				for(int i = 0; i < leftRange; i++){
					if(p.x-(i+1) < 0) break;
					if(field[p.y][p.x-(i+1)] != 0){
						if(moveASkill == 2){
							continue;
						}else{
							break;
						}
					}
					range[p.y][p.x-(i+1)] = 1;
				}
				for(int i = 0; i < rightRange; i++){
					if(p.x+(i+1) >= COL) break;
					if(field[p.y][p.x+(i+1)] != 0){
						if(moveASkill == 2){
							continue;
						}else{
							break;
						}
					}
					range[p.y][p.x+(i+1)] = 1;
				}
				if(moveASkill == 3){
					int tempRange;
					tempRange = (int)Math.sqrt(upRange * leftRange);
					for(int i = 0; i < tempRange; i++){
						if(p.x-(i+1) < 0 || p.y-(i+1) < 0) break;
						range[p.y-(i+1)][p.x-(i+1)] = 1;
					}
					tempRange = (int)Math.sqrt(leftRange * downRange);
					for(int i = 0; i < tempRange; i++){
						if(p.x-(i+1) < 0 || p.y+(i+1) >= ROW) break;
						range[p.y+(i+1)][p.x-(i+1)] = 1;
					}
					tempRange = (int)Math.sqrt(downRange * rightRange);
					for(int i = 0; i < tempRange; i++){
						if(p.x+(i+1) >= COL || p.y+(i+1) >= ROW) break;
						range[p.y+(i+1)][p.x+(i+1)] = 1;
					}
					tempRange = (int)Math.sqrt(rightRange * upRange);
					for(int i = 0; i < tempRange; i++){
						if(p.x+(i+1) >= COL || p.y-(i+1) < 0) break;
						range[p.y-(i+1)][p.x+(i+1)] = 1;
					}
				}
			}
			break;
		case 2:
			if(p.y >= 0 && p.y < ROW && selectedCard != null){
				if(field[p.y][p.x] < 0){
					upRange = selectedCard.getAttackDownRange();
					downRange = selectedCard.getAttackUpRange();
					leftRange = selectedCard.getAttackRightRange();
					rightRange = selectedCard.getAttackLeftRange();
				}
				if(field[p.y][p.x] > 0){
					upRange = selectedCard.getAttackUpRange();
					downRange = selectedCard.getAttackDownRange();
					leftRange = selectedCard.getAttackLeftRange();
					rightRange = selectedCard.getAttackRightRange();
				}
				for(int i = 0; i < upRange; i++){
					if(p.y-(i+1) < 0) break;
					if(field[p.y-(i+1)][p.x] != 0){
						if(field[p.y-(i+1)][p.x] * turn < 0){
							range[p.y-(i+1)][p.x] = 1;
						}
						break;
					}
					range[p.y-(i+1)][p.x] = 1;
				}
				for(int i = 0; i < downRange; i++){
					if(p.y+(i+1) >= ROW) break;
					if(field[p.y+(i+1)][p.x] != 0){
						if(field[p.y+(i+1)][p.x] * turn < 0){
							range[p.y+(i+1)][p.x] = 1;
						}
						break;
					}
					range[p.y+(i+1)][p.x] = 1;
				}
				for(int i = 0; i < leftRange; i++){
					if(p.x-(i+1) < 0) break;
					if(field[p.y][p.x-(i+1)] != 0){
						if(field[p.y][p.x-(i+1)] * turn < 0){
							range[p.y][p.x-(i+1)] = 1;
						}
						break;
					}
					range[p.y][p.x-(i+1)] = 1;
				}
				for(int i = 0; i < rightRange; i++){
					if(p.x+(i+1) >= COL) break;
					if(field[p.y][p.x+(i+1)] != 0){
						if(field[p.y][p.x+(i+1)] * turn < 0){
							range[p.y][p.x+(i+1)] = 1;
						}
						break;
					}
					range[p.y][p.x+(i+1)] = 1;
				}
				if(moveASkill == 4){
					int tempRange;
					tempRange = (int)Math.sqrt(upRange * leftRange);
					for(int i = 0; i < tempRange; i++){
						if(p.x-(i+1) < 0 || p.y-(i+1) < 0) break;
						range[p.y-(i+1)][p.x-(i+1)] = 1;
					}
					tempRange = (int)Math.sqrt(leftRange * downRange);
					for(int i = 0; i < tempRange; i++){
						if(p.x-(i+1) < 0 || p.y+(i+1) >= ROW) break;
						range[p.y+(i+1)][p.x-(i+1)] = 1;
					}
					tempRange = (int)Math.sqrt(downRange * rightRange);
					for(int i = 0; i < tempRange; i++){
						if(p.x+(i+1) >= COL || p.y+(i+1) >= ROW) break;
						range[p.y+(i+1)][p.x+(i+1)] = 1;
					}
					tempRange = (int)Math.sqrt(rightRange * upRange);
					for(int i = 0; i < tempRange; i++){
						if(p.x+(i+1) >= COL || p.y-(i+1) < 0) break;
						range[p.y-(i+1)][p.x+(i+1)] = 1;
					}
				}
			}
			break;
		case 3:
			new MSkill(selectedCard.getMSkill()).setRange(p,range,this);
			break;
		case 4:
			break;
		}
	}
	
	private void loadImage(){
		ImageIcon icon = new ImageIcon(getClass().getResource("image/field.gif"));
		fieldImage = icon.getImage();
		icon = new ImageIcon(getClass().getResource("image/card.gif"));
		cardImage = icon.getImage();
		icon = new ImageIcon(getClass().getResource("image/menu.gif"));
		menuImage = icon.getImage();
		icon = new ImageIcon(getClass().getResource("image/turnEnd.gif"));
		turnEndImage = icon.getImage();
		icon = new ImageIcon(getClass().getResource("image/dropCard.gif"));
		dropCardImage = icon.getImage();
	}
}
