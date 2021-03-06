/**
 * 特殊エフェクト用クラス
 * 連鎖、同時消し、パネル消しの瞬間に出すエフェクトを用意している
 */
import java.awt.Graphics;
import java.awt.Color;

class Effect{
	// エフェクトの種類
	private int kind;
	// エフェクトが始まったときのフレーム数
	private int start;
	// エフェクトを出すときに利用するx、y座標
	private int x,y;
	// 連鎖数とか同時消し数とか消したパネルの種類とか
	private int num;
	// エフェクトが終了したらtrue
	private boolean end;
	// コンストラクタ エフェクトの種類とか場所とかを指定
	public Effect(int kind, int x, int y, int num){
		this.kind = kind;
		this.x = x;
		this.y = y;
		this.num = num;
		end = false;
		start = Data.frame;
	}
	
	// 毎フレームごとの update
	public void update(){
		if(start+Data.EFFECT_TIME < Data.frame) end = true;
	}
	// 終了フラグを返す
	public boolean ended(){ return end; }
	// 描画
	public void draw(Graphics g){
		int drawX = (Data.FIELD_START_X + Data.PANEL_SIZE * x)*Data.zoom;
		int drawY = (Data.FIELD_START_Y + Data.PANEL_SIZE * y)*Data.zoom;
		int imageX;
		int imageY;
		switch(kind){
		// 連鎖のエフェクト
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
		// 同時消しのエフェクト
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
		// パネル消去時のエフェクト
		case Data.DELETE_EFFECT:
			int red,green,brue;
			switch(num){
			case 0:
				red = 255;
				green = 0;
				brue = 0;
				break;
			case 1:
				red = 255;
				green = 255;
				brue = 0;
				break;
			case 2:
				red = 0;
				green = 255;
				brue = 0;
				break;
			case 3:
				red = 0;
				green = 255;
				brue = 255;
				break;
			case 4:
				red = 0;
				green = 0;
				brue = 255;
				break;
			case 5:
				red = 255;
				green = 0;
				brue = 128;
				break;
			case 6:
				red = 255;
				green = 128;
				brue = 128;
				break;
			case 7:
				red = 0;
				green = 0;
				brue = 255;
				break;
			case 8:
				red = 0;
				green = 255;
				brue = 0;
				break;
			case 9:
				red = 180;
				green = 180;
				brue = 180;
				break;
			case 10:
				red = 255;
				green = 0;
				brue = 255;
				break;
			case 11:
				red = 255;
				green = 255;
				brue = 255;
				break;
			default:
				red = 0;
				green = 0;
				brue = 0;
				break;
			}
			int f = (Data.frame-start);
			drawY -= Data.scrollOffset*Data.zoom;
			if(f > 20){
				g.setColor(new Color(red,green,brue,5));
			}else{
				g.setColor(new Color(red,green,brue,100-5*f));
			}
			g.fillRect(drawX-5*f,drawY-5*f,Data.PANEL_SIZE*Data.zoom+10*f,Data.PANEL_SIZE*Data.zoom+10*f);
			break;
		}
	}
}