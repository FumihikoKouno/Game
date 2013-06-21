import java.awt.Image;
import javax.swing.ImageIcon;

class ImageData{
	public final Image blockImage = new ImageIcon(getClass().getResource("image/block.gif")).getImage();
	public final Image ghostImage = new ImageIcon(getClass().getResource("image/ghost.gif")).getImage();
	public final Image fieldImage = new ImageIcon(getClass().getResource("image/field.gif")).getImage();
	public final Image setImage = new ImageIcon(getClass().getResource("image/set.gif")).getImage();
	public final Image nextImage = new ImageIcon(getClass().getResource("image/next.gif")).getImage();
	public final Image numberImage = new ImageIcon(getClass().getResource("image/number.gif")).getImage();
	public final Image titleImage = new ImageIcon(getClass().getResource("image/title.gif")).getImage();
}