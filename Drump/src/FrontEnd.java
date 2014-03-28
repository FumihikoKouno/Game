import javax.swing.JFrame;


public class FrontEnd extends JFrame {
	public FrontEnd(){
		init();
	}
	public void init(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		gameLoop();
	}
	public void update(){
		System.out.println("test");
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
