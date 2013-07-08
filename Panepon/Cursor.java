import java.awt.Graphics;

class Cursor{
	private int x, y;
	private int beforeMove;
	private boolean loop;
	public Cursor(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){ return x; }
	public int getY(){ return y; }
		public void moveUp(){ y = Math.max(y-1,0); }
	public void set(int x, int y){
		if(x > Data.cursorMaxX) this.x = Data.cursorMaxX;
		else if(x < 0) this.x = 0;
		else this.x = x;
		if(y > Data.cursorMaxY) this.y = Data.cursorMaxY;
		else if(y < 0) this.y = 0;
		else this.y = y;
	}
	public void setX(int x){
		if(x > Data.cursorMaxX) this.x = Data.cursorMaxX;
		else if(x < 0) this.x = 0;
		else this.x = x;
	}
	public void setLoopAble(boolean b){
		loop = b;
	}
	public void move(){
		boolean moved = false;
		moved = KeyStatus.up || KeyStatus.down || KeyStatus.left || KeyStatus.right;
		if(!moved) beforeMove = 0;
		if(beforeMove>0 && beforeMove <5){
			beforeMove++;
			return;
		}
		if(KeyStatus.up){
			if(loop){
				if(y == 0) y = Data.cursorMaxY;
				else y = y - 1;
			}else{
				y = Math.max(y-1,0);
			}
		}
		if(KeyStatus.down){
			if(loop){
				if(y == Data.cursorMaxY) y = 0;
				else y = y + 1;
			}else{
				y = Math.min(y+1,Data.cursorMaxY);
			}
		}
		if(KeyStatus.left){
			if(loop){
				if(x == 0) x = Data.cursorMaxX;
				else x = x - 1;
			}else{
				x = Math.max(x-1,0);
			}
		}
		if(KeyStatus.right){
			if(loop){
				if(x == Data.cursorMaxX) x = 0;
				else x = x + 1;
			}else{
				x = Math.min(x+1,Data.cursorMaxX);
			}
		}
		if(beforeMove == 0 && moved) beforeMove = 1;
	}
	
	public void draw(Graphics g, int status){
		int drawX = 0;
		int drawY = 0;
		int width = 0;
		int height = 0;
		int imageX = 0;
		int imageY = 0;
		switch(status){
		case Data.TITLE:
			drawX = (Data.TITLE_CURSOR_X + 5 * ((Data.frame%30<15)?0:1))*Data.zoom;
			drawY = (Data.TITLE_CURSOR_Y + Data.TITLE_DIFFERENCE * y)*Data.zoom;
			width = Data.PANEL_SIZE;
			height = Data.PANEL_SIZE;
			imageX = 0;
			imageY = Data.PANEL_SIZE;
			break;
		case Data.ENDLESS:
		case Data.SCORE_ATTACK:
		case Data.STAGE_CLEAR:
		case Data.DEMO:
			drawX = (Data.FIELD_START_X + x * Data.PANEL_SIZE)*Data.zoom;
			drawY = (-Data.scrollOffset + Data.FIELD_START_Y + y * Data.PANEL_SIZE)*Data.zoom;
			width = (Data.PANEL_SIZE << 1);
			height = Data.PANEL_SIZE;
			imageX = 0;
			imageY = 0;
			break;
		default:
			break;
		}
		g.drawImage(Data.image.cursorImage,drawX,drawY,drawX+width*Data.zoom,drawY+height*Data.zoom,
			imageX,imageY,imageX+width,imageY+height,
			null);
	}
	
}
