import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import Card.*;
import Common.Data;
import Common.MouseEnterExitListener;


public class Hand extends JPanel implements MouseListener{
	enum ELEMENT{
		SOUTH,
		NORTH,
	};
	private static final double X_UNIT = 1.5;
	
	private static final boolean[][] southAreaRange =
		{
			{false,false,false,false,false,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
			{true,true,true,true,true,true,true,true,true},
			{true,true,true,true,true,true,true,true,true},
			{true,true,true,true,true,true,true,true,true},
		};
	
	private static final boolean[][] northAreaRange =
		{
		{true,true,true,true,true,true,true,true,true},
		{true,true,true,true,true,true,true,true,true},
		{true,true,true,true,true,true,true,true,true},
			{false,false,false,false,false,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
		};
	
	private StatusPanel statusPanel;
	private Field field;
	private int selectingPoint = -1;
	private ELEMENT element;
	private Box[] boxes = new Box[Data.HAND_NUMBER];

	public Hand(ELEMENT elem){
		setPreferredSize(new Dimension(Data.BOX_SIZE*Data.HAND_NUMBER, Data.BOX_SIZE));
		addMouseListener(this);
		element = elem;
		for(int i = 0; i < Data.HAND_NUMBER; i++){
			switch(element){
			case SOUTH:
				boxes[i] = new Box(Box.ELEMENT.SOUTH_HAND);
				break;
			case NORTH:
				boxes[i] = new Box(Box.ELEMENT.NORTH_HAND);
				break;
			default:
				return;
			}
		}
		boxes[0].setCard(new Player(Card.ELEMENT.NORTH));
		boxes[1].setCard(new Player(Card.ELEMENT.SOUTH));
		justify();
	}
	
	public void setField(Field field){
		this.field = field;
	}
	
	public void setStatusPanel(StatusPanel pane){
		statusPanel = pane;
	}
	
	private void setSelectingPoint(int x){
		selectingPoint = x;
	}
	
	private void resetSelecting(){
		selectingPoint = -1;
		field.resetSelect();
	}
	
	public void justify(){
		switch(element){
		case NORTH:
			for(int i = boxes.length-1; i >= 0; i--){
				if(boxes[i].getCard() == null){
					for(int j = i-1; j >= 0; j--){
						if(boxes[j].getCard() != null){
							boxes[i].setCard(boxes[j].getCard());
							boxes[j].setCard(null);
							break;
						}
					}
				}
			}
			break;
		case SOUTH:
			for(int i = 0; i < boxes.length; i++){
				if(boxes[i].getCard() == null){
					for(int j = i+1; j < boxes.length; j++){
						if(boxes[j].getCard() != null){
							boxes[i].setCard(boxes[j].getCard());
							boxes[j].setCard(null);
							break;
						}
					}
				}
			}
			break;
		default:
			break;
		}
	}

	public void clickHand(MouseEvent e){
		Card selectingCard = boxes[selectingPoint].getCard();
		if(selectingCard==null) return;
		statusPanel.setCard(selectingCard);
		JPopupMenu popup = new JPopupMenu();
		int summonCost = selectingCard.getSummonCost();
		JMenuItem summon = new JMenuItem("Summon : Cost"+summonCost);
		if(summonCost!=0){
			popup.add(summon);
			summon.addMouseListener(new MouseEnterExitListener(){
				@Override
				public void mouseEntered(MouseEvent e) {
					switch(element){
					case NORTH:
						field.setSelectableRange(northAreaRange);
						break;
					case SOUTH:
						field.setSelectableRange(southAreaRange);
						break;
					default:
						break;
					}
				}
				@Override
				public void mouseExited(MouseEvent e) {
					field.resetSelectableRange();
				}
			});
			summon.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					switch(element){
					case NORTH:
						field.selectCommand(Field.COMMAND.SUMMON_NORTH);
						break;
					case SOUTH:
						field.selectCommand(Field.COMMAND.SUMMON_SOUTH);
						break;
					}
				}
			});
		}
		popup.show(e.getComponent(), e.getX(),e.getY());
	}
	
	public Card summonCard(){
		Card ret = boxes[selectingPoint].getCard();
		boxes[selectingPoint].setCard(null);
		justify();
		return ret;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = -1;
		for(int i = 0; i < Data.HAND_NUMBER; i++){
			if(e.getX()>=(i*X_UNIT)*Data.BOX_SIZE && e.getX()<(i*X_UNIT+1)*Data.BOX_SIZE){
				x=i;
				i=Data.HAND_NUMBER;
				break;
			}
		}
		if(x>=0){
			if(selectingPoint < 0){
				setSelectingPoint(x);
				clickHand(e);
			}else{
				resetSelecting();
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	public void paintComponent(Graphics g){
		for(int i = 0; i < Data.HAND_NUMBER; i++){
			boxes[i].draw(g, i*X_UNIT, 0);
		}
	}
	
}
