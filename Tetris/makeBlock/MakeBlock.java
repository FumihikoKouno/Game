import java.awt.Container;

import javax.swing.JFrame;

public class MakeBlock extends JFrame {
	public MakeBlock() {
		// �^�C�g����ݒ�
		setTitle("MakeBlock");

		// �p�l�����쐬
		MainPanel panel = new MainPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);

		// �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
		pack();
	}

	public static void main(String[] args) {
		MakeBlock frame = new MakeBlock();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}