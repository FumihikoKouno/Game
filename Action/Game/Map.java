/**
 * マップクラス
 * …というかプレイ中の全体をつかさどるクラスになってる
 */

package Game;

import Game.Common.*;
import Game.Sprite.*;
import Game.Sprite.Weapon.*;
import Game.MapData.MapData;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Map{
	// マップのID(今はマップが一個なので使ってない)
	private int id;
	/**
	 * 画面表示用の座標変数、これらによって画面が上手い具合にスクロールする
	 * 画面に映っている一番左上の座標を指す
	 */
	private int x, y;
	/**
	 * マップデータ
	 * マップに関する情報を保持する、マップ移動する際は
	 * mapData.load(int id)を使いマップ情報を更新する
	 * 但しload関数は今のところ未実装
	 */
	private MapData mapData;
	/**
	 * ポーズボタン、メニューボタンが離されたかどうかの変数
	 */
	private boolean pauseReleased;
	private boolean menuReleased;
	// ポーズ中
	private boolean pausing;

	/**
	 * コンストラクタ
	 * 初期設定してる
	 */
	public Map(int mapId, int x, int y){
		pauseReleased = false;
		pausing = false;
		id = mapId;
		Data.player = new Player(x, y);
		mapData = new MapData(id);
	}
	/**
	 * マップのスクロール関数
	 * プレイヤーがちょうどいい位置に写るように画面をスクロールする
	 * 強制スクロール面などを実装したい場合は
	 * これでは対応できない
	 */
	public void scroll(){
		int npx = Data.player.getX() + Data.player.getVx();
		int npy = Data.player.getY() + Data.player.getVy();
		int nx = npx - Data.WIDTH/2;
		int ny = npy - Data.HEIGHT/2;
		if(nx < 0) nx = 0;
		if(ny < 0) ny = 0;
		if(nx > mapData.col*Data.CHIP_SIZE-Data.WIDTH) nx = mapData.col*Data.CHIP_SIZE-Data.WIDTH;
		if(ny > mapData.row*Data.CHIP_SIZE-Data.HEIGHT) ny = mapData.row*Data.CHIP_SIZE-Data.HEIGHT;
		x = nx;
		y = ny;
	}
	/**
	 * 描画用関数
	 * マップの描画と同時に
	 * マップの持っているオブジェクトの描画関数も呼び出す
	 * 具体的にはプレイヤー(武器も同時)とスプライトの描画関数を呼び出す
	 */
	public void draw(Graphics g){
		/* 背景描画 */
		g.drawImage(Data.image.backgroundImage,0,0,null);
		/* マップの描画開始 */
		int row = Data.HEIGHT / Data.CHIP_SIZE;
		int col = Data.WIDTH / Data.CHIP_SIZE;
		for(int i = 0; i <= row; i++){
			for(int j = 0; j <= col; j++){ 
				int chipX = j + x / Data.CHIP_SIZE;
				if(chipX < 0) chipX = 0;
				if(chipX >= mapData.col) chipX = mapData.col-1;
				int chipY = i + y / Data.CHIP_SIZE;
				if(chipY < 0) chipY = 0;
				if(chipY >= mapData.row) chipY = mapData.row-1;
				if(mapData.data[chipY][chipX] == 0) continue;
				int imageX = (mapData.data[chipY][chipX] % 16) * Data.CHIP_SIZE;
				int imageY = (mapData.data[chipY][chipX] / 16) * Data.CHIP_SIZE;
				g.drawImage(Data.image.mapImage,
					j * Data.CHIP_SIZE - (x%Data.CHIP_SIZE), i * Data.CHIP_SIZE -(y%Data.CHIP_SIZE), 
					j * Data.CHIP_SIZE -(x%Data.CHIP_SIZE)+Data.CHIP_SIZE, i * Data.CHIP_SIZE -(y%Data.CHIP_SIZE)+Data.CHIP_SIZE,
					imageX, imageY, imageX + Data.CHIP_SIZE, imageY + Data.CHIP_SIZE,
					null
				);
			}
		}
		/* マップの描画終了 */
		/* スプライトの描画 */
		for(int i = 0; i < mapData.spriteList.size(); i++){
			mapData.spriteList.get(i).draw(g,x,y);
		}
		/* プレイヤーの描画 */
		Data.player.draw(g,x,y);
		// ポーズ中の場合、画面にポーズの文字を点滅させる
		if(pausing && (Data.frame/30)%2 == 0) g.drawString("Pause",Data.WIDTH/2, Data.HEIGHT/2);
	}
	/**
	 * mapHitUpからmapHitRightは
	 * 各方向のマップとの衝突判定を行う関数
	 * マップとの衝突判定をいじる場合はこれらをいじる
	 */
	public int mapHitUp(Sprite s){
	    int px = s.getX();
	    int py = s.getY() + s.getVy();
	    int width = s.getWidth();
	    int height = s.getHeight();
	    int from = (px+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int to = (px+width-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int f = (py+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int t = (py+height-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int ret = Integer.MIN_VALUE;
	    for(int i = from; i <= to; i++){
		for(int j = f; j <= t ; j++){
		    if(i<0||i>=mapData.col||j<0||j>=mapData.row) continue;
		    if(mapData.pass[j][i] == 1){
			ret = Math.max(ret, j*Data.CHIP_SIZE + Data.CHIP_SIZE - Data.CD_DIFF);
		    }
		}
		if(py+Data.CD_DIFF < 0) ret = Math.max(ret,-Data.CD_DIFF);
	    }
	    return ret;
	}
	public int mapHitDown(Sprite s){
	    int px = s.getX();
	    int py = s.getY() + s.getVy();
	    int width = s.getWidth();
	    int height = s.getHeight();
	    int from = (px+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int to = (px+width-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int f = (py+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int t = (py+height-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int ret = Integer.MAX_VALUE;
	    for(int i = from; i <= to; i++){
		for(int j = f; j <= t; j++){
		    if(i<0||i>=mapData.col||j<0) continue;
		    if(j >= mapData.row) ret = Math.min(ret,mapData.row * Data.CHIP_SIZE - height + Data.CD_DIFF);
		    else if(mapData.pass[j][i] == 1){
			ret = Math.min(ret,j*Data.CHIP_SIZE - height + Data.CD_DIFF);
		    }
		}
	    }
	    if(ret == Integer.MAX_VALUE) return Integer.MIN_VALUE;
	    return ret;
	}
	public int mapHitLeft(Sprite s){
	    int px = s.getX() + s.getVx();
	    int py = s.getY() + s.getVy();
	    int width = s.getWidth();
	    int height = s.getHeight();
	    int from = (py+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int to = (py+height-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int f = (px+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int t = (px+width-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int ret = Integer.MIN_VALUE;
	    for(int i = from; i <= to; i++){
		for(int j = f; j <= t; j++){
		    if(j<0||j>=mapData.col||i<0||i>=mapData.row) continue;
		    if(mapData.pass[i][j] == 1){
			ret = Math.max(ret,j*Data.CHIP_SIZE + Data.CHIP_SIZE - Data.CD_DIFF);
		    }
		}
		if(px+Data.CD_DIFF < 0) ret = Math.max(ret,-Data.CD_DIFF);
	    }
	    return ret;
	}
	public int mapHitRight(Sprite s){
	    int px = s.getX() + s.getVx();
	    int py = s.getY() + s.getVy();
	    int width = s.getWidth();
	    int height = s.getHeight();
	    int from = (py+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int to = (py+height-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int f = (px+Data.CD_DIFF)/Data.CHIP_SIZE;
	    int t = (px+width-1-Data.CD_DIFF)/Data.CHIP_SIZE;
	    int ret = Integer.MAX_VALUE;
	    for(int i = from; i <= to; i++){
		for(int j = f; j <= t; j++){
		    if(j<0||i<0||i>=mapData.row) continue;
		    if(j >= mapData.col) ret = Math.min(ret,mapData.col * Data.CHIP_SIZE - width + Data.CD_DIFF);
		    else if(mapData.pass[i][j] == 1){
			ret = Math.min(ret,j*Data.CHIP_SIZE - width + Data.CD_DIFF);
		    }  
		}
	    }
	    if(ret == Integer.MAX_VALUE) return Integer.MIN_VALUE;
	    return ret;
	}
	/**
	 * スプライトとマップの衝突判定
	 * 基本はplayerAndMapHitと同じ
	 * どの種類のスプライトがマップに衝突したかときの動作は
	 * 各クラスのmapHit関数を使う
	 * 必要ならば if(tmp instanceof ****) で特別な動作を書く
	 * 例えば壁を壊すようなスプライトなど
	 */
	public boolean spriteAndMapHit(Sprite tmp){
		boolean ret = false;
		if(tmp.getVy() < 0){
			int d = mapHitUp(tmp);
			if(d != Integer.MIN_VALUE){
				tmp.mapHit(Sprite.UP,d);
				ret = true;
			}
		}
		if(tmp.getVy() > 0){
			int d = mapHitDown(tmp);
			if(d != Integer.MIN_VALUE){
				tmp.mapHit(Sprite.DOWN,d);
				ret = true;
			}else{
				/**
				 * 下に接しなければプライヤーは空中にいることになる
				 * mapHitにまとめたかったけど、思いつかなかった
				 * これがなくても落下はするんだけど、
				 * 空中にいるという判定のために必要
				 */
				if(tmp instanceof Player) Data.player.fall();
			}
		}
		if(tmp.getVx() < 0){
			int d = mapHitLeft(tmp);
			if(d != Integer.MIN_VALUE){
				tmp.mapHit(Sprite.LEFT,d);
				ret = true;
			}
		}
		if(tmp.getVx() > 0){
			int d = mapHitRight(tmp);
			if(d != Integer.MIN_VALUE){
				tmp.mapHit(Sprite.RIGHT,d);
				ret = true;
			}
		}
		return ret;
	}
	/**
	 * hitUpからhitRightは物体同士の各方向に対する衝突判定
	 * 攻撃の衝突判定とかもこれらを使う
	 * 攻撃の判定とか、敵との接触判定とかの
	 * 当たり判定をいじる場合はこちら
	 * "1が上に当たる"がhitUp
	 */
    public int hitUp(Sprite s1, Sprite s2){
	    int px1 = s1.getX();
	    int py1 = s1.getY()+s1.getVy();
	    int width1 = s1.getWidth();
	    int height1 = s1.getHeight();
	    int px2 = s2.getX();
	    int py2 = s2.getY()+s2.getVy();
	    int width2 = s2.getWidth();
	    int height2 = s2.getHeight();
		if(px1+Data.CD_DIFF > px2+width2-1 || px1+width1-1-Data.CD_DIFF < px2) return Integer.MIN_VALUE;
		if(py1+Data.CD_DIFF <= py2+height2-1 && py1+height1-1-Data.CD_DIFF > py2+height2-1){
			return py2+height2-Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
    public int hitDown(Sprite s1, Sprite s2){
	    int px1 = s1.getX();
	    int py1 = s1.getY()+s1.getVy();
	    int width1 = s1.getWidth();
	    int height1 = s1.getHeight();
	    int px2 = s2.getX();
	    int py2 = s2.getY()+s2.getVy();
	    int width2 = s2.getWidth();
	    int height2 = s2.getHeight();
		if(px1+Data.CD_DIFF > px2+width2-1 || px1+width1-1-Data.CD_DIFF < px2) return Integer.MIN_VALUE;
		if(py1+Data.CD_DIFF < py2 && py1+height1-1-Data.CD_DIFF >= py2){
			return py2-height1+Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
    public int hitLeft(Sprite s1, Sprite s2){
	    int px1 = s1.getX()+s1.getVx();
	    int py1 = s1.getY();
	    int width1 = s1.getWidth();
	    int height1 = s1.getHeight();
	    int px2 = s2.getX()+s2.getVx();
	    int py2 = s2.getY();
	    int width2 = s2.getWidth();
	    int height2 = s2.getHeight();
		if(py1+Data.CD_DIFF > py2+height2-1 || py1+height1-1-Data.CD_DIFF < py2) return Integer.MIN_VALUE;
		if(px1+Data.CD_DIFF <= px2+width2-1 && px1+width1-1-Data.CD_DIFF > px2+width2-1){
			return px2+width2-Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
    public int hitRight(Sprite s1, Sprite s2){
	    int px1 = s1.getX()+s1.getVx();
	    int py1 = s1.getY();
	    int width1 = s1.getWidth();
	    int height1 = s1.getHeight();
	    int px2 = s2.getX()+s2.getVx();
	    int py2 = s2.getY();
	    int width2 = s2.getWidth();
	    int height2 = s2.getHeight();
		if(py1+Data.CD_DIFF > py2+height2-1 || py1+height1-1-Data.CD_DIFF < py2) return Integer.MIN_VALUE;
		if(px1+Data.CD_DIFF < px2 && px1+width1-1-Data.CD_DIFF >= px2){
			return px2-width1+Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
	/**
	 * スプライト同士のスプライトの衝突判定
	 */
	public boolean spriteAndSpriteHit(Sprite s1, Sprite s2){
		int[] hitPosition = new int[4];
		hitPosition[Sprite.UP] = hitUp(s1,s2);
		hitPosition[Sprite.DOWN]  = hitDown(s1,s2);
		hitPosition[Sprite.LEFT]  = hitLeft(s1,s2);
		hitPosition[Sprite.RIGHT]  = hitRight(s1,s2);
		int hitDir = 0;
		for(int i = 0; i < 4; i++){
			if(hitPosition[i] != Integer.MIN_VALUE) hitDir = hitDir | (1 << i);
		}
		if(hitDir > 0){
			if(s1 instanceof Player) s2.touch(s1,hitDir,hitPosition);
			else if(s1 instanceof Weapon) s2.attacked(s1);
			else{
				s2.touch(s1,hitDir,hitPosition);
				int tmp;
				tmp = hitPosition[Sprite.UP];
				hitPosition[Sprite.UP] = hitPosition[Sprite.DOWN];
				hitPosition[Sprite.DOWN] = tmp;
				tmp = hitPosition[Sprite.LEFT];
				hitPosition[Sprite.LEFT] = hitPosition[Sprite.RIGHT];
				hitPosition[Sprite.RIGHT] = tmp;
				hitDir = 0;
				for(int i = 0; i < 4; i++){
					if(hitPosition[i] != Integer.MIN_VALUE) hitDir = hitDir | (1 << i);
				}
				s1.touch(s2,hitDir,hitPosition);
			}
			return true;
		}
		return false;
	}

	/**
	 * フレームごとの更新用関数
	 * いろんなインスタンスのupdateも呼び出す
	 * 処理の順番は結構重要だったりする
	 * 後半の方が優先度が高い
	 * 例えば敵に当たって仰け反るよりも
	 * 壁にめり込まない方が大事など
	 */
	public void update(){
		/**
		 * メニュー呼び出し用の操作
		 * 今はメニューボタンに対応するボタンを設定していないし、
		 * そもそもメニューを作っていない
		 */
		if(!KeyStatus.menu) menuReleased = true;
		if(menuReleased && KeyStatus.menu){
			Data.gameStatus = Data.MENU;
			menuReleased = false;
			return;
		}
		/**
		 * ポーズ用操作
		 */
		if(!KeyStatus.pause) pauseReleased = true;
		if(pausing){
			if(pauseReleased && KeyStatus.pause){
				pausing = false;
				pauseReleased = false;
			}
			return;
		}else{
			if(pauseReleased && KeyStatus.pause){
				pausing = true;
				pauseReleased = false;
				return;
			}
		}
		// プレイヤーの状態更新
		Data.player.update();
		/**
		 * 武器と壁との衝突判定
		 * 矢とかは刺さるか消えるかするよね？
		 */
		if(Data.player.weapon != null){
			Data.player.weapon.update(mapData);
			if(Data.player.weapon.end) Data.player.weapon = null;
			else spriteAndMapHit(Data.player.weapon);
		}
		// スプライトの衝突判定
		// 壁・プレイヤー・武器との衝突判定を行う
		for(int i = 0; i < mapData.spriteList.size(); i++){
			Sprite tmp = mapData.spriteList.get(i);
			// 画面外のスプライトについての計算は行わない
			if(tmp.getX() < x - Data.SCREEN_OUT || tmp.getX() > x + Data.WIDTH + Data.SCREEN_OUT || tmp.getY() < y - Data.SCREEN_OUT || tmp.getY() > y + Data.WIDTH + Data.SCREEN_OUT) continue;
			tmp.update(mapData);
			spriteAndSpriteHit(Data.player,tmp);
			if(Data.player.weapon != null){
				spriteAndSpriteHit(Data.player.weapon,tmp);
			}
			for(int j = i+1; j < mapData.spriteList.size(); j++){
				Sprite tmp2 = mapData.spriteList.get(j);
				if(tmp2.getX() < x - Data.SCREEN_OUT || tmp2.getX() > x + Data.WIDTH + Data.SCREEN_OUT || tmp2.getY() < y - Data.SCREEN_OUT || tmp2.getY() > y + Data.WIDTH + Data.SCREEN_OUT) continue;
				spriteAndSpriteHit(tmp,tmp2);
			}
			if(tmp.end){
				mapData.spriteList.remove(tmp);
				continue;
			}
			spriteAndMapHit(tmp);
			tmp.move();
		}
		// プレイヤーのマップとの衝突判定
		spriteAndMapHit(Data.player);
		/**
		 * ここまでで、マップとか敵とかにぶつかることによる
		 * 速度変動の処理が全部終わったので、
		 * 主人公の移動と、その移動先に合わせた画面スクロール
		 */
		scroll();
		Data.player.move();
	}
}
