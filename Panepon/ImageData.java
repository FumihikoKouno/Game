/**
 * 画像データをまとめて持つクラス
 */
import java.awt.Image;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class ImageData{
	public final Image panelImage = new ImageIcon(getClass().getResource("Image/Panel.gif")).getImage();
	public final Image fieldImage = new ImageIcon(getClass().getResource("Image/Field.gif")).getImage();
	public final Image cursorImage = new ImageIcon(getClass().getResource("Image/Cursor.gif")).getImage();
	public final Image nextPanelImage = new ImageIcon(getClass().getResource("Image/nextPanel.gif")).getImage();
	public final Image deletingPanelImage = new ImageIcon(getClass().getResource("Image/deletingPanel.gif")).getImage();
	public final Image chainImage = new ImageIcon(getClass().getResource("Image/chain.gif")).getImage();
	public final Image sameImage = new ImageIcon(getClass().getResource("Image/same.gif")).getImage();
	public final Image titleImage = new ImageIcon(getClass().getResource("Image/Title.gif")).getImage();
	public final Image mouseImage = new ImageIcon(getClass().getResource("Image/mouseCursor.gif")).getImage();
	public final Image endlessImage = new ImageIcon(getClass().getResource("Image/EndlessScore.gif")).getImage();
	public final Image scoreAttackImage = new ImageIcon(getClass().getResource("Image/ScoreAttackScore.gif")).getImage();
	public final Image stageClearImage = new ImageIcon(getClass().getResource("Image/StageClearScore.gif")).getImage();
	public final Image scoreChangeImage = new ImageIcon(getClass().getResource("Image/ScoreChange.gif")).getImage();
	public final Image hardTitleImage = new ImageIcon(getClass().getResource("Image/hard_title.gif")).getImage();
	public final Image hardFieldImage = new ImageIcon(getClass().getResource("Image/hard_field.gif")).getImage();
}