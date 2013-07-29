/**
 * カーソル関係のクラス
 * ゲームプレイ画面ではパネル移動のカーソル
 * タイトルやランキングでは項目選択のカーソル
 */

import java.awt.Graphics;

class Cursor{
	// カーソルのx, y座標
	private int x, y;
	
	/**
	 * 前にカーソルが動いたときからのフレーム数
	 * 0だと前のフレームにはカーソルを動かしていない
	 * 移動キーを押しっぱなしにしたときにいきなり大幅に動かないようにするため
	 */
	private int beforeMove;
	
	/**
	 * trueならカーソルが最上のときに上を押したら最下に
	 * カーソルが最下のときに下を押したときに最上にいく
	 */
	private boolean loop;
	
	// コンストラクタ 初期x, 初期y が引数
	public Cursor(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	// x座標を得る
	public int getX(){ return x; }
	// y座標を得る
	public int getY(){ return y; }
	// カーソルを一つ上に移動する(y座標を-1する)
	public void moveUp(){ y = Math.max(y-1,0); }
	// 引数のx,y座標にカーソルを移す
	public void set(int x, int y){
		if(x > Data.cursorMaxX) this.x = Data.cursorMaxX;
		else if(x < 0) this.x = 0;
		else this.x = x;
		if(y > Data.cursorMaxY) this.y = Data.cursorMaxY;
		else if(y < 0) this.y = 0;
		else this.y = y;
	}
	// x座標を設定する
	public void setX(int x){
		if(x > Data.cursorMaxX) this.x = Data.cursorMaxX;
		else if(x < 0) this.x = 0;
		else this.x = x;
	}
	// y座標を設定する
	public void setY(int y){
		if(y > Data.cursorMaxY) this.y = Data.cursorMaxY;
		else if(y < 0) this.y = 0;
		else this.y = y;
	}
	// カーソルがループするかどうかのフラグをセット
	public void setLoopAble(boolean b){ loop = b; }
	
	// 入力されているキーに応じてカーソルを動かす関数
	public void move(){
		boolean moved = false;
		moved = KeyStatus.up || KeyStatus.down || KeyStatus.left || KeyStatus.right;
		// このフレームでキーが離されていれば動いていないことを表わす0とする
		if(!moved){
			beforeMove = 0;
			return;
		}
		// 前からカーソルを動かしてから移動キーを押しっぱなしにしている場合5フレーム立たないと次が動かない
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

	// 描画関数 statusはゲームの状態を表わす
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
		case Data.RANKING:
			drawX = (Data.RANKING_CURSOR_X + 5 * ((Data.frame%30<15)?0:1))*Data.zoom;
			drawY = (Data.RANKING_CURSOR_Y + Data.RANKING_DIFFERENCE * y)*Data.zoom;
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
