/**
 * �Q�[���̐i�s��ɂ���ĕς��ϐ��Q
 * �Z�[�u�f�[�^�͂��̃N���X�̒l��S�ĕێ����Ă�����
 * �Z�[�u�ł���͂�
 */

package Game.Common;
import Game.Sprite.Player;
import Game.MapData.MapData;

public class StateData{
	// �e�z��v�f�������t���A�C�e���̐�
	public static byte[] gotElement =  new byte[Data.ELEMENT_NUM];
	// �v���C���[
	public static Player player;
	// �}�b�v�f�[�^
	public static MapData mapData;
	// �t���O
	public static Flag flag;
}