package Game;

import java.awt.Container;
import javax.swing.JFrame;

public class Game extends JFrame {
	private MainPanel panel;
	private Container contentPane;
	public Game() {
		setTitle("Game");
		panel = new MainPanel();
		contentPane = getContentPane();
		contentPane.add(panel);
		pack();
	}
	public void start(){
		panel.run();
	}

	public static void main(String[] args) {
		Game frame = new Game();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.start();
	}
}