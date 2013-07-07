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
	public final Image enemyImage = new ImageIcon(getClass().getResource("../Image/Sprite/enemy1.gif")).getImage();
	public Image backgroundImage = new ImageIcon(getClass().getResource("../Image/Picture/background.jpg")).getImage();
	public final Image coinImage = new ImageIcon(getClass().getResource("../Image/Sprite/coin.gif")).getImage();
	public final Image menuImage = new ImageIcon(getClass().getResource("../Image/Picture/menu.gif")).getImage();
}