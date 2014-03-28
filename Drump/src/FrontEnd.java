import javax.swing.JFrame;


public class FrontEnd extends JFrame {
	public Deck deck;
	public FrontEnd(){
		init();
	}
	public void init(){
		deck = new Deck();
		add(deck);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		gameLoop();
	}
	public void update(){
		deck.repaint();
	}
	public void gameLoop(){
		long SPF = 1000/Data.FPS;
		long time;
		try{
			time = System.currentTimeMillis();
			update();
			time = System.currentTimeMillis()-time;
			if(SPF>time) Thread.sleep(SPF-time);
		}catch(InterruptedException e){}
	}
	public static void main(String[] args) {
		FrontEnd fe = new FrontEnd();
	}

}
