import java.util.Random;

public class Deck {
	private Card[] cards;
	private int idx;
	public Deck(){
		init();
	}
	public void init(){
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
}
