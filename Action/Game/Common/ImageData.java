/**
 * 画像データをまとめるクラス
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
	public final Image swordImage = new ImageIcon(getClass().getResource("../Image/Weapon/sword.gif")).getImage();
	public final Image arrowImage = new ImageIcon(getClass().getResource("../Image/Weapon/arrow.gif")).getImage();
	public final Image enemyImage = new ImageIcon(getClass().getResource("../Image/Sprite/enemy1.gif")).getImage();
	public Image backgroundImage = new ImageIcon(getClass().getResource("../Image/Picture/background.jpg")).getImage();
	// スプライトのイメージはまとめて一つのファイルにしてやってもいいと思う
	// まぁ最終的には(TODO)
	public final Image coinImage = new ImageIcon(getClass().getResource("../Image/Sprite/coin.gif")).getImage();
	public final Image menuImage = new ImageIcon(getClass().getResource("../Image/Picture/menu.gif")).getImage();
	public final Image rightUpSlopeImage = new ImageIcon(getClass().getResource("../Image/Sprite/RightUpSlope.gif")).getImage();
	public final Image leftUpSlopeImage = new ImageIcon(getClass().getResource("../Image/Sprite/LeftUpSlope.gif")).getImage();
	public final Image appearingChipImage = new ImageIcon(getClass().getResource("../Image/Sprite/appearingChip.gif")).getImage();
	public final Image froatingStageImage = new ImageIcon(getClass().getResource("../Image/Sprite/froatingStage.gif")).getImage();
	public final Image brokenChipImage = new ImageIcon(getClass().getResource("../Image/Sprite/broken.gif")).getImage();
	public final Image elementIconImage = new ImageIcon(getClass().getResource("../Image/Icon/element.gif")).getImage();
	public final Image weaponIconImage = new ImageIcon(getClass().getResource("../Image/Icon/weapon.gif")).getImage();
	public final Image bodyIconImage = new ImageIcon(getClass().getResource("../Image/Icon/body.gif")).getImage();
	public final Image needleImage = new ImageIcon(getClass().getResource("../Image/Sprite/needle.gif")).getImage();
	public final Image signboardImage = new ImageIcon(getClass().getResource("../Image/Sprite/signboard.gif")).getImage();
}