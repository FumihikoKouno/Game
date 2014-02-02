import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Common.Data;


public class Hand extends JPanel{
	enum ELEMENT{
		SOUTH,
		NORTH,
	};
	
	private ELEMENT element;
	private Box[] boxes = new Box[Data.HAND_NUMBER];

	public Hand(ELEMENT elem){
		element = elem;
		switch(element){
		case SOUTH:
			setLayout(new FlowLayout(FlowLayout.LEFT));
			break;
		case NORTH:
			setLayout(new FlowLayout(FlowLayout.RIGHT));
			break;
		default:
			break;
		}
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
			add(boxes[i]);
		}
	}
}
