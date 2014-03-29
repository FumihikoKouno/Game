import javax.swing.JPanel;


public class Field extends JPanel{
	private Deck deck;
	private Player[] players;
	public Field(){
		init();
	}
	public void init(){
		deck = new Deck();
		for(int i = 0; i < 2; i++){
			players[i] = new Player(i,this);
		}
	}
	public Card drawCard(){
		return deck.getTopCard();
	}

	public void drump(){
		
	}
	
	public void paintComponent(){
		
	}
}
