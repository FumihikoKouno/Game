import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import Card.*;
import Common.Data;
import Common.MouseEnterExitListener;

public class Field extends JPanel implements MouseListener{
	Box[][] boxes = new Box[Data.FIELD_Y][Data.FIELD_X];
	private Point selectingPoint = new Point();
	
	private COMMAND selecting = COMMAND.NONE;
	private Card[] enemies;
	private Card[] friends;
	
	enum COMMAND{
		NONE,
		ATTACK,
		ACTIVE_SKILL,
		EVOLVE,
		MOVE,
	}
	
	public Field(){
		setLayout(new GridLayout(Data.FIELD_Y,Data.FIELD_X));
		for(int i = 0; i < Data.FIELD_Y; i++){
			for(int j = 0; j < Data.FIELD_X; j++){
				boxes[i][j] = new Box();
				boxes[i][j].addMouseListener(this);
				if(i<3) boxes[i][j].setElement(Box.ELEMENT.NORTH_FIELD);
				else if(i>=Data.FIELD_Y-3) boxes[i][j].setElement(Box.ELEMENT.SOUTH_FIELD);
				else boxes[i][j].setElement(Box.ELEMENT.CENTER_FIELD);
				add(boxes[i][j]);
				if(i==Data.FIELD_Y-1 && (j==0 || j==5)){
					boxes[i][j].setCard(new Player(Card.ELEMENT.SOUTH));
				}
			}
		}
	}

	public void resetSelect(){
		selecting = COMMAND.NONE;
		resetSelectableRange();		
	}
	
	public void rightClick(MouseEvent e){
		if(selecting != COMMAND.NONE){
			resetSelect();
		}else{
		}
	}
	
	public void clickSelect(MouseEvent e){
		Box box = (Box)e.getSource();
		Card card = box.getCard();
		Card selectingCard = boxes[selectingPoint.y][selectingPoint.x].getCard();
		switch(selecting){
		case ACTIVE_SKILL:
			break;
		case ATTACK:
			if(card == null) break;
			selectingCard.doAttack(card);
			break;
		case EVOLVE:
			break;
		case MOVE:
			if(card != null || !box.getSelectable()) break;
			box.setCard(selectingCard);
			boxes[selectingPoint.y][selectingPoint.x].setCard(null);
			break;
		default:
			break;
		}
		resetSelect();
	}
	
	public void setSelecting(int x, int y ){
		selectingPoint.x = x;
		selectingPoint.y = y;
	}
	
	private void resetSelectableRange(){
		for(int i = 0; i < Data.FIELD_Y; i++){
			for(int j = 0; j < Data.FIELD_X; j++){
				boxes[i][j].setSelectable(false);
			}
		}
	}
	
	private void setSelectableRange(COMMAND com){
		Card card = boxes[selectingPoint.y][selectingPoint.x].getCard();
		boolean[][] range;
		switch(com){
		case ATTACK:
			range = card.getAttackableRange(selectingPoint);
			break;
		case ACTIVE_SKILL:
			range = card.getActiveSkillRange(selectingPoint);
			break;
		case MOVE:
			range = card.getMoveRange(selectingPoint);
			break;
		default:
			return;
		}
		for(int i = 0; i < Data.FIELD_Y; i++){
			for(int j = 0; j < Data.FIELD_X; j++){
				if(range[i][j]) boxes[i][j].setSelectable(true);
			}
		}
	}
	
	public void selectCommand(COMMAND com){
		selecting = com;
		Card card = boxes[selectingPoint.y][selectingPoint.x].getCard();
		switch(com){
		case ACTIVE_SKILL:
			enemies = new Card[card.getEnemiesForActiveSkill()];
			friends = new Card[card.getFriendsForActiveSkill()];
			break;
		default:
			return;
		}
	}
	
	public void createCommandPopup(Box selectingBox, Card selectingCard, MouseEvent e){
		JPopupMenu popup = new JPopupMenu();
		int attackCost = selectingCard.getAttackCost();
		int activeSkillCost = selectingCard.getActiveSkillCost();
		int evolveCost = selectingCard.getEvolveCost();
		int moveCost = selectingCard.getMoveCost();

		if(moveCost!=0){
			JMenuItem move = new JMenuItem("Move : Cost"+moveCost);
			move.addMouseListener(new MouseEnterExitListener(){
				public void mouseEntered(MouseEvent e) {
					setSelectableRange(COMMAND.MOVE);
				}
				public void mouseExited(MouseEvent e) {
					resetSelectableRange();
				}
			});
			move.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					selectCommand(COMMAND.MOVE);
				}
			});
			popup.add(move);
		}
		if(attackCost!=0){
			JMenuItem attack = new JMenuItem("Attack : Cost"+attackCost);
			attack.addMouseListener(new MouseEnterExitListener(){
				public void mouseEntered(MouseEvent e) {
					setSelectableRange(COMMAND.ATTACK);
				}
				public void mouseExited(MouseEvent e) {
					resetSelectableRange();
				}
			});
			attack.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					selectCommand(COMMAND.ATTACK);
				}
			});
			popup.add(attack);
		}
		if(activeSkillCost!=0){
			JMenuItem activeSkill = new JMenuItem("ActiveSkill : Cost"+activeSkillCost);
			activeSkill.addMouseListener(new MouseEnterExitListener(){
				public void mouseEntered(MouseEvent e) {
					setSelectableRange(COMMAND.ACTIVE_SKILL);
				}
				public void mouseExited(MouseEvent e) {
					resetSelectableRange();
				}
			});
			activeSkill.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					selectCommand(COMMAND.ACTIVE_SKILL);
				}
			});
			popup.add(activeSkill);
		}
		if(evolveCost!=0 && 
		   !selectingCard.isEvolved() && 
		   ((selectingCard.getUser()==Card.ELEMENT.SOUTH && selectingBox.getElement() == Box.ELEMENT.NORTH_FIELD) ||
		   (selectingCard.getUser()==Card.ELEMENT.NORTH && selectingBox.getElement() == Box.ELEMENT.SOUTH_FIELD))
		){
			JMenuItem evolution = new JMenuItem("Evolution : Cost"+evolveCost);
			evolution.addMouseListener(new MouseEnterExitListener(){
				public void mouseEntered(MouseEvent e) {
					setSelectableRange(COMMAND.EVOLVE);
				}
				public void mouseExited(MouseEvent e) {
					resetSelectableRange();
				}
			});
			evolution.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					selectCommand(COMMAND.EVOLVE);
				}
			});
			popup.add(evolution);
		}
		popup.show(e.getComponent(), e.getX(),e.getY());
	}
	
	public void clickFirstCard(MouseEvent e){
		Box selectingBox = boxes[selectingPoint.y][selectingPoint.x];
		Card selectingCard = selectingBox.getCard();
		if(selectingCard==null) return;
		System.out.println(""+selectingCard.getPower());
		createCommandPopup(selectingBox,selectingCard,e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object comp = e.getSource();
		int x=0;
		int y=0;
		for(int i = 0; i < Data.FIELD_Y; i++){
			for(int j = 0; j < Data.FIELD_X; j++){
				if(boxes[i][j] == comp){
					y = i;
					x = j;
					i = Data.FIELD_Y;
					break;
				}
			}
		}
		Box box = boxes[selectingPoint.y][selectingPoint.x];

		switch(e.getButton()){
		// Left Click
		case MouseEvent.BUTTON1:
			switch(box.getElement()){
			case SOUTH_FIELD:
			case NORTH_FIELD:
			case CENTER_FIELD:
				if(selecting != COMMAND.NONE) clickSelect(e);
				else{
					setSelecting(x,y);
					clickFirstCard(e);
				}
				break;
			default:
				return;
			}
			break;
		// Right Click
		case MouseEvent.BUTTON3:
			rightClick(e);
			break;
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}	
}
