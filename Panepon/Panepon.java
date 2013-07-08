import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.JFrame;


public class Panepon extends JFrame {
	MainPanel panel;
	Container contentPane;
	public Panepon() {
		setTitle("Panepon");
		panel = new MainPanel();
		contentPane = getContentPane();
		contentPane.add(panel,BorderLayout.CENTER);
		pack();
	}
	
	public void start(){
		panel.start();
	}
	
	public static void main(String[] args) {
		if(args.length >= 1 && args[0] != null){
			try{
				Data.zoom = Integer.parseInt(args[0]);
			}catch(NumberFormatException e){
				Data.zoom = 1;
			}
		}
		Panepon frame = new Panepon();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.start();
	}
}