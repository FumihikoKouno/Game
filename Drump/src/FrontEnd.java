import javax.swing.JFrame;


public class FrontEnd extends JFrame {
	public Field field;
	public FrontEnd(){
		init();
	}
	public void init(){
		field = new Field();
		add(field);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
//		field.repaint();
		gameLoop();
	}
	public void update(){
		field.repaint();
	}
	public void gameLoop(){
		long SPF = 1000/Data.FPS;
		long time;
		while(true){
			try{
				time = System.currentTimeMillis();
				update();
				time = System.currentTimeMillis()-time;
				if(SPF>time) Thread.sleep(SPF-time);
			}catch(InterruptedException e){}
		}
	}
	public static void main(String[] args) {
		FrontEnd fe = new FrontEnd();
	}

}
