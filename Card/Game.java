import java.awt.Container;

import javax.swing.JFrame;

public class Game extends JFrame {
	public Game() {
		// �^�C�g����ݒ�
		setTitle("Game");

		// �p�l�����쐬
		MainPanel panel = new MainPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);

		// �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
		pack();
	}

	public static void main(String[] args) {
		Game frame = new Game();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}