import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class Deck extends JPanel{
	private Card[] cards;
	private int idx;
	public Deck(){
		init();
	}
	public void init(){
		setPreferredSize(new Dimension(Card.WIDTH*13,Card.HEIGHT*4));
		idx = 0;
		cards = new Card[52];
		for(int i = 0; i < 13; i++){
			cards[i] = new Card(Card.Mark.SPADES,i);
			cards[i+13] = new Card(Card.Mark.CLUBS,i);
			cards[i+13*2] = new Card(Card.Mark.HEARTS,i);
			cards[i+13*3] = new Card(Card.Mark.DIAMONDS,i);
		}
		shuffle();
	}
	public Card getTopCard(){
		if(idx<cards.length){
			idx++;
			return cards[idx-1];
		}else{
			return null;
		}
	}
	public void shuffle(){
		Random rand = new Random(System.currentTimeMillis());
		boolean[] shuffled = new boolean[52];
		Card[] tmp = new Card[52];
		int tmpIdx;
		for(int i = 0; i < 52; i++){
			do{
				tmpIdx = rand.nextInt(52);
			}while(shuffled[tmpIdx]);
			shuffled[tmpIdx] = true;
			tmp[i] = cards[tmpIdx];
		}
		cards = tmp;
	}
	
	public void paintComponent(Graphics g){
		for(int i = 0; i < 13; i++){
			cards[i].draw(g,i*Card.WIDTH,0,true);
			cards[i+13].draw(g,i*Card.WIDTH,Card.HEIGHT,true);
			cards[i+13*2].draw(g,i*Card.WIDTH,Card.HEIGHT*2,true);
			cards[i+13*3].draw(g,i*Card.WIDTH,Card.HEIGHT*3,true);
		}
	}
}
