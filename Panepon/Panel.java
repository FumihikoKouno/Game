
import java.awt.Graphics;

class Panel{
    private int offset_x, offset_y;
    private int kind;
    private int delete_limit;
    private boolean falling;
    private int end_frame;
    private int d_animation_time;
    private int connected;
    
    public Panel(int k){
	connected = 0;
	kind = k;
	delete_limit = 0;
	d_animation_time = 0;
	offset_x = 0;
	offset_y = 0;
	end_frame = 0;
	falling = false;
    }
    public void update(){
	if(offset_x < 0){
	    offset_x = Math.min(offset_x+Data.MPF,0);
	}
	if(offset_x > 0){
	    offset_x = Math.max(offset_x-Data.MPF,0);
	}
	if(offset_y < 0){
	    offset_y = Math.min(offset_y+Data.GRAVITY,0);
	}
	if(offset_y > 0){
	    offset_y = Math.max(offset_y-Data.GRAVITY,0);
	}
    }	
    
    public void setConnected(int i){ connected = i; }
    public int getConnected(){ return connected; }
    public int getDeleteLimit(){ return delete_limit; }
    public void setDeleteFrame(int count, int max){
	d_animation_time = Data.frame+Data.DELETE_TIME;
	delete_limit = d_animation_time+count*Data.DELETE_DIFFERENCE_TIME;
	end_frame = d_animation_time+max*Data.DELETE_DIFFERENCE_TIME; 
    }
    
    public boolean cMoving(){ return offset_x != 0; }
    public boolean rMoving(){ return offset_y != 0; }
    public void setKind(int k){ kind = k; }
    public int getKind(){ return kind; }
    public void setOffset(int x, int y){ offset_x = x; offset_y = y; }
    public void setFalling(boolean b){ falling = b; }
    public boolean isFalling(){ return falling; }
    public boolean isDeleting(){ return delete_limit>0; }
    public boolean isDeleted(){ return delete_limit != 0 && delete_limit <= Data.frame; }
    public boolean end(){ return end_frame != 0 && end_frame < Data.frame; }
    
    public void draw(Graphics g, int x, int y){
    	int drawX = (Data.FIELD_START_X + x * Data.PANEL_SIZE + offset_x)*Data.zoom;
    	int drawY = (-Data.scrollOffset + Data.FIELD_START_Y + y * Data.PANEL_SIZE + offset_y)*Data.zoom;
	int imageX = kind*Data.PANEL_SIZE;
	int imageY = 0;
	if(y == Data.ROW){
	    g.drawImage(Data.image.nextPanelImage,
			drawX, drawY, drawX+Data.PANEL_SIZE*Data.zoom, drawY+Data.PANEL_SIZE*Data.zoom,
			imageX, imageY, imageX + Data.PANEL_SIZE, imageY + Data.PANEL_SIZE,
			null
			);
	}else{
	    if(kind >= 0){
		if((isDeleting() && d_animation_time-1 < Data.frame) || (isDeleting() && (d_animation_time > Data.frame) && ((Data.frame%(Data.DELETE_TIME/3))>=(Data.DELETE_TIME/6)))){
		    g.drawImage(Data.image.deletingPanelImage,
				drawX, drawY, drawX+Data.PANEL_SIZE*Data.zoom, drawY+Data.PANEL_SIZE*Data.zoom,
				imageX, imageY, imageX + Data.PANEL_SIZE, imageY + Data.PANEL_SIZE,
				null
				);
		}else{
		    g.drawImage(Data.image.panelImage,
				drawX, drawY, drawX+Data.PANEL_SIZE*Data.zoom, drawY+Data.PANEL_SIZE*Data.zoom,
				imageX, imageY, imageX + Data.PANEL_SIZE, imageY + Data.PANEL_SIZE,
				null
				);
		}
	    }
	}
    }
}
