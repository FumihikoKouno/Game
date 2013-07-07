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
	// プレイヤー
	private Player player;
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
		player = new Player(x, y);
		mapData = new MapData(id);
	}
	/**
	 * マップのスクロール関数
	 * プレイヤーがちょうどいい位置に写るように画面をスクロールする
	 * 強制スクロール面などを実装したい場合は
	 * これでは対応できない
	 */
	public void scroll(){
		int npx = player.x + player.vx;
		int npy = player.y + player.vy;
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
		player.draw(g,x,y);
		// ポーズ中の場合、画面にポーズの文字を点滅させる
		if(pausing && (Data.frame/30)%2 == 0) g.drawString("Pause",Data.WIDTH/2, Data.HEIGHT/2);
	}
	/**
	 * mapHitUpからmapHitRightは
	 * 各方向のマップとの衝突判定を行う関数
	 * マップとの衝突判定をいじる場合はこれらをいじる
	 */
	public int mapHitUp(int px, int py, int width, int height){
		int from = (px+Data.CD_DIFF)/Data.CHIP_SIZE;
		int to = (px+width-1-Data.CD_DIFF)/Data.CHIP_SIZE;
		for(int i = from; i <= to; i++){
			if(py+Data.CD_DIFF < 0) return -Data.CD_DIFF;
			int newChipY = (py+Data.CD_DIFF)/Data.CHIP_SIZE;
			if(mapData.pass[newChipY][i] == 1){
				return newChipY*Data.CHIP_SIZE + Data.CHIP_SIZE - Data.CD_DIFF;
			}
		}
		return Integer.MIN_VALUE;
	}
	public int mapHitDown(int px, int py, int width, int height){
		int from = (px+Data.CD_DIFF)/Data.CHIP_SIZE;
		int to = (px+width-1-Data.CD_DIFF)/Data.CHIP_SIZE;
		for(int i = from; i <= to; i++){
			int newChipY = (py+height-Data.CD_DIFF)/Data.CHIP_SIZE;
			if(newChipY >= mapData.row) return mapData.row * Data.CHIP_SIZE - height + Data.CD_DIFF;
			if(mapData.pass[newChipY][i] == 1){
				return newChipY*Data.CHIP_SIZE - height + Data.CD_DIFF;
			}
		}
		return Integer.MIN_VALUE;
	}
	public int mapHitLeft(int px, int py, int width, int height){
		int from = (py+Data.CD_DIFF)/Data.CHIP_SIZE;
		int to = (py+height-1-Data.CD_DIFF)/Data.CHIP_SIZE;
		for(int i = from; i <= to; i++){
			if(px+Data.CD_DIFF < 0) return -Data.CD_DIFF;
			int newChipX = (px+Data.CD_DIFF)/Data.CHIP_SIZE;
			if(mapData.pass[i][newChipX] == 1){
				return newChipX*Data.CHIP_SIZE + Data.CHIP_SIZE - Data.CD_DIFF;
			}
		}
		return Integer.MIN_VALUE;
	}
	public int mapHitRight(int px, int py, int width, int height){
		int from = (py+Data.CD_DIFF)/Data.CHIP_SIZE;
		int to = (py+height-1-Data.CD_DIFF)/Data.CHIP_SIZE;
		for(int i = from; i <= to; i++){
			int newChipX = (px+width-Data.CD_DIFF)/Data.CHIP_SIZE;
			if(newChipX >= mapData.col) return mapData.col * Data.CHIP_SIZE - width + Data.CD_DIFF;
			if(mapData.pass[i][newChipX] == 1){
				return newChipX*Data.CHIP_SIZE - width + Data.CD_DIFF;
			}
		}
		return Integer.MIN_VALUE;
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
		if(tmp.vy < 0){
			int d = mapHitUp(tmp.x,tmp.y+tmp.vy,tmp.width,tmp.height);
			if(d != Integer.MIN_VALUE){
				tmp.mapHit(Sprite.UP,d);
				ret = true;
			}
		}
		if(tmp.vy > 0){
			int d = mapHitDown(tmp.x,tmp.y+tmp.vy,tmp.width,tmp.height);
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
				if(tmp instanceof Player) player.fall();
			}
		}
		if(tmp.vx < 0){
			int d = mapHitLeft(tmp.x+tmp.vx,tmp.y+tmp.vy,tmp.width,tmp.height);
			if(d != Integer.MIN_VALUE){
				tmp.mapHit(Sprite.LEFT,d);
				ret = true;
			}
		}
		if(tmp.vx > 0){
			int d = mapHitRight(tmp.x+tmp.vx,tmp.y+tmp.vy,tmp.width,tmp.height);
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
	public int hitUp(int px1, int py1, int width1, int height1, int px2, int py2, int width2, int height2){
		if(px1+Data.CD_DIFF > px2+width2-1-Data.CD_DIFF || px1+width1-1-Data.CD_DIFF < px2+Data.CD_DIFF) return Integer.MIN_VALUE;
		if(py1+Data.CD_DIFF <= py2+height2-1-Data.CD_DIFF && py1+height1-1 > py2+height2-1){
			return py2+height2-Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
	public int hitDown(int px1, int py1, int width1, int height1, int px2, int py2, int width2, int height2){
		if(px1+Data.CD_DIFF > px2+width2-1-Data.CD_DIFF || px1+width1-1-Data.CD_DIFF < px2+Data.CD_DIFF) return Integer.MIN_VALUE;
		if(py1 < py2 && py1+height1-1-Data.CD_DIFF >= py2+Data.CD_DIFF){
			return py2-height1+2*Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
	public int hitLeft(int px1, int py1, int width1, int height1, int px2, int py2, int width2, int height2){
		if(py1+Data.CD_DIFF > py2+height2-1-Data.CD_DIFF || py1+height1-1-Data.CD_DIFF < py2+Data.CD_DIFF) return Integer.MIN_VALUE;
		if(px1+Data.CD_DIFF <= px2+width2-1-Data.CD_DIFF && px1+width1-1 > px2+width2-1){
			return px2+width2-Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
	public int hitRight(int px1, int py1, int width1, int height1, int px2, int py2, int width2, int height2){
		if(py1+Data.CD_DIFF > py2+height2-1-Data.CD_DIFF || py1+height1-1-Data.CD_DIFF < py2+Data.CD_DIFF) return Integer.MIN_VALUE;
		if(px1 < px2 && px1+width1-1-Data.CD_DIFF >= px2+Data.CD_DIFF){
			return px2-width1+2*Data.CD_DIFF;
		}
		return Integer.MIN_VALUE;
	}
	/**
	 * スプライト同士のスプライトの衝突判定
	 * 今のところプレイヤーと敵、武器と敵の衝突判定にのみ使っている
	 */
	public boolean spriteAndSpriteHit(Sprite s1, Sprite s2){
		int dUp = hitUp(s1.x,s1.y+s1.vy,s1.width,s1.height,s2.x,s2.y+s2.vy,s2.width,s2.height);
		int dDown = hitDown(s1.x,s1.y+s1.vy,s1.width,s1.height,s2.x,s2.y+s2.vy,s2.width,s2.height);
		int dLeft = hitLeft(s1.x+s1.vx,s1.y,s1.width,s1.height,s2.x+s2.vx,s2.y,s2.width,s2.height);
		int dRight = hitRight(s1.x+s1.vx,s1.y,s1.width,s1.height,s2.x+s2.vx,s2.y,s2.width,s2.height);
		if(dUp != Integer.MIN_VALUE){
			if(s1 instanceof Player) s2.touch(s1,Sprite.DOWN,dUp);
			if(s1 instanceof Weapon) s2.attacked(s1);
			return true;
		}
		if(dDown != Integer.MIN_VALUE){
			if(s1 instanceof Player) s2.touch(s1,Sprite.UP,dDown);
			if(s1 instanceof Weapon) s2.attacked(s1);
			return true;
		}
		if(dLeft != Integer.MIN_VALUE){
			if(s1 instanceof Player) s2.touch(s1,Sprite.RIGHT,dLeft);
			if(s1 instanceof Weapon) s2.attacked(s1);
			return true;
		}
		if(dRight != Integer.MIN_VALUE){
			if(s1 instanceof Player) s2.touch(s1,Sprite.LEFT,dRight);
			if(s1 instanceof Weapon) s2.attacked(s1);
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
		player.update();
		/**
		 * 武器と壁との衝突判定
		 * 矢とかは刺さるか消えるかするよね？
		 */
		if(player.weapon != null){
			player.weapon.update(mapData);
			if(player.weapon.end) player.weapon = null;
			else spriteAndMapHit(player.weapon);
		}
		// スプライトの衝突判定
		// 壁・プレイヤー・武器との衝突判定を行う
		for(int i = 0; i < mapData.spriteList.size(); i++){
			Sprite tmp = mapData.spriteList.get(i);
			tmp.update(mapData);
			spriteAndSpriteHit(player,tmp);
			if(player.weapon != null){
				spriteAndSpriteHit(player.weapon,tmp);
			}
			if(tmp.end){
				mapData.spriteList.remove(tmp);
				continue;
			}
			spriteAndMapHit(tmp);
			tmp.move();
		}
		// プレイヤーのマップとの衝突判定
		spriteAndMapHit(player);
		/**
		 * ここまでで、マップとか敵とかにぶつかることによる
		 * 速度変動の処理が全部終わったので、
		 * 主人公の移動と、その移動先に合わせた画面スクロール
		 */
		scroll();
		player.move();
	}
}
