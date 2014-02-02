import javax.swing.JPanel;


public class Deck extends JPanel{
	private Box box;
	enum ELEMENT{
		NORTH,
		SOUTH,
	};
	private ELEMENT element;
	
	public Deck(ELEMENT elem){
		element = elem;
		switch(element){
		case SOUTH:
			box = new Box(Box.ELEMENT.SOUTH_DECK);
			break;
		case NORTH:
			box = new Box(Box.ELEMENT.NORTH_DECK);
			break;
		default:
			return;
		}
		add(box);
	}
}
