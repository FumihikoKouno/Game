import java.awt.Container;

import javax.swing.JFrame;

public class Tetris extends JFrame {
	public Tetris() {
		setTitle("Tetris");
		MainPanel panel = new MainPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);
		pack();
	}

	public static void main(String[] args) {
		if(args.length > 0){
			if(args[0].equals("-d")) Flag.printDebug = true;
		}
		Tetris frame = new Tetris();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}