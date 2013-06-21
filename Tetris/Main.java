import java.awt.Container;

import javax.swing.JFrame;

public class Main extends JFrame {
	public Main() {
		setTitle("Main");
		MainPanel panel = new MainPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);
		pack();
	}

	public static void main(String[] args) {
		Main frame = new Main();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}