import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Random;

public class Player implements Common{
	
	private Field field;
	private int playerNumber;
	
	private int deck[] = new int[MAX_DECK_NUMBER];
	private int grave[] = new int[MAX_DECK_NUMBER];
	private FieldCard hand[] = new FieldCard[MAX_HAND_NUMBER];
	private FieldCard tempDraw;
	
	private int currentDeckTop;
	private int currentGraveTop;
	private int currentHandNumber;
	
	private boolean warning;
	
	private Random rand = new Random();
	
	public Player(int playerNumber, Field field) {
		this.field = field;
		this.playerNumber = playerNumber;
		warning = false;
		currentDeckTop = 0;
		currentGraveTop = 0;
		currentHandNumber = 0;
		loadData();
		for(int i = 0; i < 5; i++){
			drawHand();
		}
	}
	
	public int getCurrentHandNumber(){
		return currentHandNumber;
	}
	
	public int getPlayerNumber(){
		return playerNumber;
	}
	
	public int getCurrentGrave(){
		return currentGraveTop;
	}
	
	public FieldCard getTemp(){
		return tempDraw;
	}
	
	public void drawHand(){
		if(currentHandNumber < MAX_HAND_NUMBER){
			if(currentDeckTop < MAX_DECK_NUMBER){
				hand[currentHandNumber] = new FieldCard(deck[currentDeckTop]);
				currentDeckTop++;
				currentHandNumber++;
			}
		}else{
			if(currentDeckTop < MAX_DECK_NUMBER){
				warning = true;
				tempDraw = new FieldCard(deck[currentDeckTop]);
				currentDeckTop++;
			}
		}
	}
	
	public boolean getWarning(){
		return warning;
	}
	
	public int getCurrentDeck(){
		return currentDeckTop;
	}
	
	public void goGrave(int card){
		grave[currentGraveTop] = card;
		currentGraveTop++;
		if(card == 0){
			Flag.gameSet = true;
		}
	}
	
	public void deleteHand(int n){
		for(int i = n; i < currentHandNumber-1; i++){
			hand[i] = hand[i+1];
		}
		hand[currentHandNumber-1] = null;
		currentHandNumber--;
	}
	
	public FieldCard getHand(int n){
		if(n > currentHandNumber){
			return null;
		}
		return hand[n];
	}
	
	public void loadData(){
		String fileName = "data/deck/deck" + playerNumber + ".deck";
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
				getClass().getResourceAsStream(fileName)));
			String line;
			for(int i = 0; i < MAX_DECK_NUMBER; i++){
				line = br.readLine();
				deck[i] = Integer.parseInt(line);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		deckShuffle();
	}
	
	public void deckShuffle(){
		int randomNumber;
		int cardTemp;
		for(int i = 0; i < MAX_DECK_NUMBER; i++){
			randomNumber = rand.nextInt(MAX_DECK_NUMBER);
			cardTemp = deck[i];
			deck[i] = deck[randomNumber];
			deck[randomNumber] = cardTemp;
		}
	}
	
	public void handExchange(int n){
		if(n < MAX_HAND_NUMBER){
			goGrave(hand[n].getCardNumber());
			while(n < MAX_HAND_NUMBER-1){
				hand[n] = hand[n+1];
				n++;
			}
			hand[MAX_HAND_NUMBER-1] = tempDraw;
			tempDraw = null;
		}else{
			goGrave(tempDraw.getCardNumber());
			tempDraw = null;
		}
		warning = false;
	}
	
}
