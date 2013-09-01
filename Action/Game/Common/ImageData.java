/**
 * �摜�f�[�^���܂Ƃ߂�N���X
 */
package Game.Common;

import java.awt.Image;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class ImageData{
	public final Image mapImage = new ImageIcon(getClass().getResource("../Image/Map/mapImage.gif")).getImage();
	public final Image playerImage = new ImageIcon(getClass().getResource("../Image/Player/player.gif")).getImage();
	public final Image spriteImage = new ImageIcon(getClass().getResource("../Image/Sprite/sprite.gif")).getImage();
	public final Image swordImage = new ImageIcon(getClass().getResource("../Image/Weapon/sword.gif")).getImage();
	public final Image arrowImage = new ImageIcon(getClass().getResource("../Image/Weapon/arrow.gif")).getImage();
	public final Image enemyImage = new ImageIcon(getClass().getResource("../Image/Sprite/enemy1.gif")).getImage();
	public Image backgroundImage;
	// �X�v���C�g�̃C���[�W�͂܂Ƃ߂Ĉ�̃t�@�C���ɂ��Ă���Ă������Ǝv��
	// �܂��ŏI�I�ɂ�(TODO)
	public final Image menuImage = new ImageIcon(getClass().getResource("../Image/Picture/menu.gif")).getImage();
	public final Image elementIconImage = new ImageIcon(getClass().getResource("../Image/Icon/element.gif")).getImage();
	public final Image weaponIconImage = new ImageIcon(getClass().getResource("../Image/Icon/weapon.gif")).getImage();
	public final Image bodyIconImage = new ImageIcon(getClass().getResource("../Image/Icon/body.gif")).getImage();
}