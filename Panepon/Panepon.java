/**
 * �Q�[���̃��C��
 * ������t�����g�G���h�Ƃ����Ă�������������Ȃ�
 */
import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.JFrame;


public class Panepon extends JFrame {
	MainPanel panel;
	Container contentPane;
	// �R���X�g���N�^
	public Panepon() {
		// �^�C�g���̃Z�b�g
		setTitle("Panepon");
		// ���C���p�l���̃C���X�^���X����
		panel = new MainPanel();
		// Panepon�t���[���̕\���̈�(�R���|�[�l���g?)�𓾂�
		contentPane = getContentPane();
		// ���C���p�l����\���̈�ɒǉ�
		contentPane.add(panel,BorderLayout.CENTER);
		// �E�B���h�E�T�C�Y��\���̈�ɍ��킹��
		pack();
	}
	
	// �Q�[���X�^�[�g
	public void start(){
		panel.start();
	}
	
	// main�֐�
	public static void main(String[] args) {
		// �����ɉ����ĕ\���{���Z�b�g
		if(args.length >= 1 && args[0] != null){
			try{
				Data.zoom = Integer.parseInt(args[0]);
			}catch(NumberFormatException e){
				Data.zoom = 1;
			}
		}
		// Panepon�C���X�^���X����
		Panepon frame = new Panepon();
		// ����{�^��(�E��́~)�������ꂽ������ݒ�
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���ۂɕ\��
		frame.setVisible(true);
		// �Q�[���X�^�[�g
		frame.start();
	}
}