package Card;

import java.util.ArrayList;

import Common.Data;

public class Player extends Card {

	public Player(Card.ELEMENT e){super(e);}
	
	@Override
	public void init() {
		ID = 0;
	}
}
