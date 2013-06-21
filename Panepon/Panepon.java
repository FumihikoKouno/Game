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
		Panepon frame = new Panepon();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.start();
	}
}