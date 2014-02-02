import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import Card.Card;
import Common.Data;

public class Box extends JPanel{
	Card card;
	boolean selectable;
	public static final Color SELECTABLE_COLOR = new Color(255,0,0,64);
	public static final Color SOUTH_COLOR = new Color(0,255,0,64);
	public static final Color NORTH_COLOR = new Color(0,0,255,64);
	private Color defaultColor;
	
	
	enum ELEMENT{
		CENTER_FIELD,
		SOUTH_FIELD,
		NORTH_FIELD,
		SOUTH_HAND,
		NORTH_HAND,
		SOUTH_DECK,
		NORTH_DECK,
	}
	
	private ELEMENT element;
	
	public Box(ELEMENT elem){
		this();
		setElement(elem);
	}
	
	public Box(){
		setPreferredSize(new Dimension(Data.BOX_SIZE,Data.BOX_SIZE));
		defaultColor = Color.WHITE;
	}
	
	public ELEMENT getElement(){
		return element;
	}
	public void setElement(ELEMENT elem){
		element = elem; 
		switch(element){
		case CENTER_FIELD:
			defaultColor = Color.WHITE;
			break;
		case SOUTH_FIELD:
		case SOUTH_HAND:
		case SOUTH_DECK:
			defaultColor = SOUTH_COLOR;
			break;
		case NORTH_FIELD:
		case NORTH_HAND:
		case NORTH_DECK:
			defaultColor = NORTH_COLOR;
			break;
		default:
			defaultColor = Color.WHITE;
			break;
		}
	}
	public Card getCard(){ return card; }
	public void setCard(Card c){ card = c; }
	
	public boolean getSelectable(){ return selectable; }
	public void setSelectable(boolean b){ selectable = b; }
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Data.BOX_SIZE, Data.BOX_SIZE);
		g.setColor(Color.WHITE);
		g.fillRect(1, 1, Data.BOX_SIZE-2, Data.BOX_SIZE-2);
		g.setColor(defaultColor);
		g.fillRect(1, 1, Data.BOX_SIZE-2, Data.BOX_SIZE-2);
		if(selectable){
			g.setColor(SELECTABLE_COLOR);
			g.fillRect(1, 1, Data.BOX_SIZE-2, Data.BOX_SIZE-2);
		}
		g.setColor(Color.BLACK);
		if(card != null) g.fillOval(1,1,Data.BOX_SIZE-2,Data.BOX_SIZE-2);
	}
}
