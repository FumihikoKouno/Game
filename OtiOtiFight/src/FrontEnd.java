import javax.swing.JFrame;


public class FrontEnd extends JFrame {
	
	public static final int FPS = 30;

	public Field field;

	public KeyStatus key;
	
	public FrontEnd(){
		init();
		pack();
	}
	
	public void init(){
		key = new KeyStatus();
		addKeyListener(key);
		field = new Field();
		add(field);
	}
	
	public void update(){
		field.update();
		repaint();
	}
	
	public void gameLoop(){
		long time;
		long MSPF = 1000/FPS;
		while(true){
			try{
				time = System.currentTimeMillis();
				update();
				time = System.currentTimeMillis()-time;
				time = MSPF-time;
				if(time>0) Thread.sleep(time);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		FrontEnd fe = new FrontEnd();
		fe.setDefaultCloseOperation(EXIT_ON_CLOSE);
		fe.setVisible(true);
		fe.gameLoop();
	}

}
