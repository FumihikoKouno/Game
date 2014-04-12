package game.common;
import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageData {
	public final Image cardsImage = new ImageIcon(getClass().getResource("cards.jpg")).getImage();
	public final Image reverseImage = new ImageIcon(getClass().getResource("reverse.jpg")).getImage();
}
