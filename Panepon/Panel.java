/**
 * パネル一つ一つのクラス
 */
import java.awt.Graphics;

class Panel{
	// 移動のオフセット
	private int offset_x, offset_y;
	// パネルの種類
	private int kind;
	// パネルの表示が消えるときのフレーム数
	private int delete_limit;
	// 落ちるフラグ
	private boolean falling;
	// パネルが完全に消失するフレーム
	private int end_frame;
	// 点滅アニメーション終了フレーム
	private int d_animation_time;
	// 連鎖用変数
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
	
	// update関数。オフセットに応じてパネルを動かす
	public void update(){
		if(offset_x < 0){
			offset_x = Math.min(offset_x+Data.MPF,0);
		}
		if(offset_x > 0){
			offset_x = Math.max(offset_x-Data.MPF,0);
		}
		if(offset_y < 0){
			offset_y = Math.min(offset_y+Data.GRAVITY*Data.hard,0);
		}
		if(offset_y > 0){
			offset_y = Math.max(offset_y-Data.GRAVITY*Data.hard,0);
		}
	}
	
	public void setConnected(int i){ connected = i; }
	public int getConnected(){ return connected; }
	public int getDeleteLimit(){ return delete_limit; }
	// 与えられた引数に応じて各終了フレームをセット
	// countは何番目に消えるかでmaxは何個同時消しをしたか
	public void setDeleteFrame(int count, int max){
		d_animation_time = Data.frame+Data.DELETE_TIME/Data.hard;
		delete_limit = d_animation_time+count*Data.DELETE_DIFFERENCE_TIME/Data.hard;
		end_frame = d_animation_time+max*Data.DELETE_DIFFERENCE_TIME/Data.hard; 
	}
	
	public boolean cMoving(){ return offset_x != 0; }
	public boolean rMoving(){ return offset_y != 0; }
	public void setKind(int k){ kind = k; }
	public int getKind(){ return kind; }
	public void setOffset(int x, int y){ offset_x = x; offset_y = y; }
	public void setFalling(boolean b){ falling = b; }
	public boolean isFalling(){ return falling; }
	// 消えている最中ならtrue
	public boolean isDeleting(){ return delete_limit>0; }
	// 表示が消えるフレームに達したらtrue
	public boolean isDeleted(){ return delete_limit != 0 && delete_limit <= Data.frame; }
	// パネルが完全に消えるフレームに達したらtrue
	public boolean end(){ return end_frame != 0 && end_frame < Data.frame; }

	// 描画
	public void draw(Graphics g, int x, int y){
		int drawX = (Data.FIELD_START_X + x * Data.PANEL_SIZE + offset_x)*Data.zoom;
		int drawY = (-Data.scrollOffset + Data.FIELD_START_Y + y * Data.PANEL_SIZE + offset_y)*Data.zoom;
		int imageX = kind*Data.PANEL_SIZE;
		int imageY = 0;
		// まだ出現していない一番下のくらいパネル
		if(y == Data.ROW){
			g.drawImage(Data.image.nextPanelImage,
				drawX, drawY, drawX+Data.PANEL_SIZE*Data.zoom, drawY+Data.PANEL_SIZE*Data.zoom,
				imageX, imageY, imageX + Data.PANEL_SIZE, imageY + Data.PANEL_SIZE,
				null
				);
		}else{
			// パネルの表示
			if(kind >= 0){
				// 消えているときに光る部分の表示
				if((isDeleting() && d_animation_time-1 < Data.frame) || (isDeleting() && (d_animation_time > Data.frame) && ((Data.frame%(Data.DELETE_TIME/3))>=(Data.DELETE_TIME/6)))){
					g.drawImage(Data.image.deletingPanelImage,
						drawX, drawY, drawX+Data.PANEL_SIZE*Data.zoom, drawY+Data.PANEL_SIZE*Data.zoom,
						imageX, imageY, imageX + Data.PANEL_SIZE, imageY + Data.PANEL_SIZE,
						null
						);
				}else{
					// 通常の表示
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
