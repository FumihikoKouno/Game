import java.awt.Graphics;

public class Card {
	public enum Mark{
		SPADES,
		CLUBS,
		HEARTS,
		DIAMONDS,
	};
	
	Mark mark;
	int number;
	
	public Card(Mark mark, int number){
		this.mark = mark;
		this.number = number;
	}
	
	public void draw(Graphics g, boolean show){
		
	}
}
