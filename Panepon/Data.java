/**
 * �����ȃN���X����Q�Ƃ����ł��낤�f�[�^���܂Ƃ߂��N���X
 * ���ׂẴ����o��static�Œ�`���Ă���
 */
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public class Data{
	/**
	 * �Q�[���̏�Ԃ�\�킷�萔
	 */
	public static final int TITLE = 0;
	public static final int ENDLESS = 1;
	public static final int SCORE_ATTACK = 2;
	public static final int STAGE_CLEAR = 3;
	public static final int DEMO = 4;
	public static final int RANKING = 5;
	public static final int EXIT = 6;

	/**
	 * true�Ȃ�}�E�X�����L�[������󂯕t���Ȃ�
	 * ���v���C��f���p
	 * �������A�ォ��ǉ���������
	 * ���g���C��^�C�g���ɖ߂�L�[�͑ΏۊO
	 */
	public static boolean mouseCansel;
	public static boolean keyCansel;
	
	// �Q�[���̌o�ߎ��ԁA�������͎c�莞��
	public static int time;
	
	// ��ʂ̕\���{��
	public static int zoom = 1;
	
	/**
	 * �n�[�h���[�h�p�ϐ�
	 * 1:�m�[�}�����[�h
	 * 2:�n�[�h���[�h
	 */
	public static int hard = 1;

	
	/**
	 * �������炵�΂炭�`��p�̍��W�萔
	 */
	public static final int RANKING_NAME_X = 70;
	public static final int RANKING_TIME_X = 350;
	public static final int RANKING_SCORE_X = 210;
	public static final int RANKING_MAX_CHAIN_X = 430;
	public static final int RANKING_MAX_DELETE_X = 530;
	public static final int RANKING_STAGE_TIME_X = 215;
	public static final int RANKING_STAGE_SCORE_X = 300;
	public static final int RANKING_TOP_Y = 120;
	public static final int RANKING_DIFF_Y = 29;
	
	public static final int SCORE_OUTPUT = 0;
	public static final int SCORE_INPUT = 1;
	
	public static final int MESSAGE_X_SIZE = 20;
	public static final int MESSAGE_Y_SIZE = 20;
	
	public static final int TITLE_CURSOR_X = 310;
	public static final int TITLE_CURSOR_Y = 190;
	public static final int TITLE_DIFFERENCE = 48;
	
	public static final int RANKING_CURSOR_X = 5;
	public static final int RANKING_CURSOR_Y = 93;
	public static final int RANKING_DIFFERENCE = 29;

	public static final int FIELD_START_X = 178;
	public static final int FIELD_START_Y = 64;
	
	public static final int LV_X = 43;
	public static final int LV_Y = 113;

	public static final int REST_X = 447;
	public static final int REST_Y = 113;

	public static final int SCORE_X = 447;
	public static final int SCORE_Y = 202;
	
	public static final int MAX_CHAIN_X = 447;
	public static final int MAX_CHAIN_Y = 296;
	
	public static final int MAX_DELETE_X = 447;
	public static final int MAX_DELETE_Y = 389;
	// ���W�萔�����܂�
	
	/**
	 * �J�[�\���ړ��A�p�l���ړ����s��ꂽ�t���[���Ƃ��̍��W
	 * �ǂ̃t���[���ŃX�N���[����������ێ�����ArrayList
	 * ���v���C�p
	 */
	public static ArrayList<Integer> replayCursorFrame = new ArrayList<Integer>();
	public static ArrayList<Integer> replayCursorX = new ArrayList<Integer>();
	public static ArrayList<Integer> replayCursorY = new ArrayList<Integer>();
	public static ArrayList<Integer> replayScrollFrame = new ArrayList<Integer>();
	public static ArrayList<Integer> replaySwapFrame = new ArrayList<Integer>();
	public static ArrayList<Integer> replaySwapX = new ArrayList<Integer>();
	public static ArrayList<Integer> replaySwapY = new ArrayList<Integer>();

	// �����̃V�[�h ���v���C�p�Ɏ���Ă���
	public static long seed;
	
	/**
	 * �J�[�\���̍ő�x���W�A�ő�y���W��\�킷�ϐ�
	 */
	public static int cursorMaxX;
	public static int cursorMaxY;
	
	// �ǂ̂��炢�X�N���[�����Ă��邩
	public static int scrollOffset;

	// �p�l���̑傫��
	public static final int PANEL_SIZE = 32;
	// ���̃X�N���[���łǂ̂��炢�㏸���邩
	public static final int SCROLL_UNIT = 4;
	// ���̃p�l������������ɏ��������Ă���t���[����
	public static final int DELETE_RAG = 10;
	// �����Ă�����ۂɃp�l������������܂ł̎���
	public static final int DELETE_TIME = 50;
	// ������p�l���̎��ԍ� ��������邱�ƂŃ|���|���|�����ď��Ԃɏ�����
	public static final int DELETE_DIFFERENCE_TIME = 10;
	// 1�t���[��������Ƀp�l�����ǂ̂��炢���ɓ�����
	public static final int MPF = PANEL_SIZE/2;
	// �p�l����1�t���[�������藎�����
	public static int GRAVITY = PANEL_SIZE/2;
	// �p�l���̎�ނ̐�
	public static final int PANEL_NUMBER = 6;
	// �Q�[���v���C���̃J�[�\���̏����ʒu
	public static final int INIT_CURSOR_X = 2;
	public static final int INIT_CURSOR_Y = 8;
	// ��ʂ̉��Əc�̃T�C�Y
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	// �v���O�����N��������̃t���[����
	public static int frame;
	// ����グ���x��
	public static int lv;
	// �A����
	public static int chain;
	// 1�b�Ԃɉ��t���[����
	public static final int fps = 30;
	// �Q�[���v���C���̃p�l���̉��Əc�̍ő吔
	public static final int ROW = 10;
	public static final int COL = 6;
	// �X�R�A�A�^�b�N���̃^�C�����~�b�g
	public static final int TIME_LIMIT = fps*120;
	// �X�e�[�W�N���A���̃S�[���܂ł̂���オ�������C����
	public static final int CLEAR_LINE = 20;
	
	// �}�E�X��������Ă��邩�̔���ϐ�
	public static boolean mousePressed = false;
	// �}�E�X�������ꂽ�Ƃ��ɂ��̃J�[�\�����w���Ă���p�l����x�Ay���W
	public static int pressedX;
	public static int pressedY;

	// �G�t�F�N�g�̎�ނ������萔
	public static final int CHAIN_EFFECT = 0;
	public static final int SAME_EFFECT = 1;
	public static final int DELETE_EFFECT = 2;
	// �A�����y�ѓ����������̃G�t�F�N�g�̑傫��
	public static final int EFFECT_SIZE = 30;
	// �G�t�F�N�g���o�Ă���t���[����
	public static final int EFFECT_TIME = 20;
	// �ő�A����
	public static int maxChain;
	// �X�R�A
	public static int score;
	// �ő哯��������
	public static int maxDelete;
	// �Q�[���̏�� ������Ԃ̓^�C�g��
	public static int gameStatus = TITLE;
	// �摜�f�[�^���܂Ƃ߂��N���X�̃C���X�^���X
	public static final ImageData image = new ImageData();
	// �f�o�b�O�p�ϐ�
	private static int debugCount;
	// �ǂ̎�ނ̃t�H���g���������萔
	public static final int SCORE_FONT = 0;
	public static final int MESSAGE_FONT = 1;
	
	// �t�H���g�̐ݒ�֐�
	public static void setFont(Graphics g, int fontNo){
		switch(fontNo){
		case SCORE_FONT:
			g.setColor(Color.WHITE);
			g.setFont(new Font("�l�r �o�S�V�b�N",0, 24*zoom));
			break;
		case MESSAGE_FONT:
			g.setColor(Color.WHITE);
			g.setFont(new Font("�l�r �o�S�V�b�N",0, 18*zoom));
			break;
		}
	}

	// �f�o�b�O�o��
	public static void debugPrint(String s){
		debugCount++;
		System.out.println("frame " + frame + " : " + s);
	}
}
