import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotion implements MouseMotionListener{
	public MouseMotion(){
	}
	public void mouseMoved(MouseEvent e){
	}
	public void mouseDragged(MouseEvent e){
		if(Data.mouseCansel) return;
		int x = e.getX();
		int y = e.getY();
		for(int i = 0; i < Data.ROW; i++){
			if(Data.FIELD_START_Y + i * Data.PANEL_SIZE - Data.scrollOffset <= y && y < Data.FIELD_START_Y + (i + 1) * Data.PANEL_SIZE - Data.scrollOffset){
				Data.pressedY = i;
				break;
			}
		}
		for(int j = 0; j < Data.COL; j++){
			if(Data.FIELD_START_X + j * Data.PANEL_SIZE <= x && x < Data.FIELD_START_X + (j + 1) * Data.PANEL_SIZE){
				Data.pressedX = j;
				break;
			}
		}
		return;
	}
}