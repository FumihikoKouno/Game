import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener, Common{
	
	private Field field;
	private MainPanel panel;
	
	public Mouse(Field field, MainPanel panel){
		this.field = field;
		this.panel = panel;
		panel.addMouseListener(this);
	}
	public void mouseClicked(MouseEvent e){
		field.setMousePoint(new Point(e.getX(),e.getY()));
		panel.update();
	}
	public void mouseEntered(MouseEvent e){
	}
	public void mouseExited(MouseEvent e){
	}
	public void mousePressed(MouseEvent e){
	}
	public void mouseReleased(MouseEvent e){
	}
}