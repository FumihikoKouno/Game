import java.awt.Container;

import javax.swing.JFrame;

public class MakeCard extends JFrame {
	public MakeCard() {
		// タイトルを設定
		setTitle("MakeCard");

		// パネルを作成
		MainPanel panel = new MainPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);

		// パネルサイズに合わせてフレームサイズを自動設定
		pack();
	}

	public static void main(String[] args) {
		MakeCard frame = new MakeCard();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}