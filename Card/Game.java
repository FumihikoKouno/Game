import java.awt.Container;

import javax.swing.JFrame;

public class Game extends JFrame {
	public Game() {
		// タイトルを設定
		setTitle("Game");

		// パネルを作成
		MainPanel panel = new MainPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);

		// パネルサイズに合わせてフレームサイズを自動設定
		pack();
	}

	public static void main(String[] args) {
		Game frame = new Game();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}