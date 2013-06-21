import java.awt.Container;

import javax.swing.JFrame;

public class MakeBlock extends JFrame {
	public MakeBlock() {
		// タイトルを設定
		setTitle("MakeBlock");

		// パネルを作成
		MainPanel panel = new MainPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);

		// パネルサイズに合わせてフレームサイズを自動設定
		pack();
	}

	public static void main(String[] args) {
		MakeBlock frame = new MakeBlock();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}