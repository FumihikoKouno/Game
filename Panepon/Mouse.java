import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener{
	public Mouse(){
	}
	public void mouseClicked(MouseEvent e){
	}
	public void mouseEntered(MouseEvent e){
	}
	public void mouseExited(MouseEvent e){
	}
	public void mousePressed(MouseEvent e){
		if(Data.mouseCansel) return;
		int x = e.getX();
		int y = e.getY();
		if(x < Data.FIELD_START_X || x >= Data.FIELD_START_X + Data.PANEL_SIZE * Data.COL){
			Data.mousePressed = false;
			return;
		}
		if(y < Data.FIELD_START_Y - Data.scrollOffset || y >= Data.FIELD_START_Y + Data.PANEL_SIZE * Data.ROW - Data.scrollOffset){
			Data.mousePressed = false;
			return;
		}
		for(int i = 0; i < Data.ROW; i++){
			if(Data.FIELD_START_Y + i * Data.PANEL_SIZE - Data.scrollOffset <= y && y < Data.FIELD_START_Y + (i + 1) * Data.PANEL_SIZE - Data.scrollOffset){
				Data.pressedY = i;
			}
		}
		for(int j = 0; j < Data.COL; j++){
			if(Data.FIELD_START_X + j * Data.PANEL_SIZE <= x && x < Data.FIELD_START_X + (j + 1) * Data.PANEL_SIZE){
				Data.pressedX = j;
			}
		}
		Data.mousePressed = true;
		return;
	}
	public void mouseReleased(MouseEvent e){
		if(Data.mouseCansel) return;
		Data.mousePressed = false;
	}
}