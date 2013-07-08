import java.awt.Graphics;
class Effect{
	private int kind;
	private int start;
	private int x,y;
	private int num;
	private boolean end;
	public Effect(int kind, int x, int y, int num){
		this.kind = kind;
		this.x = x;
		this.y = y;
		this.num = num;
		end = false;
		start = Data.frame;
	}
	
	public void update(){
		if(start+Data.EFFECT_TIME < Data.frame) end = true;
	}
	
	public boolean ended(){
		return end;
	}
	
	public void draw(Graphics g){
		int drawX = (Data.FIELD_START_X + Data.PANEL_SIZE * x)*Data.zoom;
		int drawY = (Data.FIELD_START_Y + Data.PANEL_SIZE * y)*Data.zoom;
		int imageX;
		int imageY;
		switch(kind){
		case Data.CHAIN_EFFECT:
			if(num >= 20){
				imageX = 0;
				imageY = 0;
			}else{
				imageX = (num%10)*Data.EFFECT_SIZE;
				imageY = (num/10)*Data.EFFECT_SIZE;
			}
			drawX += (Data.PANEL_SIZE * 1.5)*Data.zoom;
			drawY -= (Data.PANEL_SIZE<<1)*Data.zoom;
			g.drawImage(Data.image.chainImage,
				drawX, drawY, drawX+Data.EFFECT_SIZE*Data.zoom, drawY+Data.EFFECT_SIZE*Data.zoom,
				imageX, imageY, imageX + Data.EFFECT_SIZE, imageY + Data.EFFECT_SIZE,
				null
			);
			break;
		case Data.SAME_EFFECT:
			drawX += (Data.PANEL_SIZE + 1.5)*Data.zoom;
			drawY -= Data.PANEL_SIZE*Data.zoom;
			if(num >= 40){
				imageX = 0;
				imageY = 0;
			}else{
				imageX = (num%10)*Data.EFFECT_SIZE;
				imageY = (num/10)*Data.EFFECT_SIZE;
			}
			g.drawImage(Data.image.sameImage,
				drawX, drawY, drawX+Data.EFFECT_SIZE*Data.zoom, drawY+Data.EFFECT_SIZE*Data.zoom,
				imageX, imageY, imageX + Data.EFFECT_SIZE, imageY + Data.EFFECT_SIZE,
				null
			);
			break;
		}
	}
}