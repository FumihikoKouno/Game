/**
 * すべてのイベントの基盤クラス
 * 敵、仕掛け(スイッチとか？)、扉とか
 * プレイヤーの行動によって何か変化が生じるものは
 * このクラスを継承して作る
 */

package Game.Sprite;

import Game.Sprite.Player;
import Game.Common.Data;
import Game.Sprite.Weapon.Weapon;

import java.awt.Graphics;
import java.awt.Image;

public class Sprite{
	/**
	 * staticな変数
	 */
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int HIT_DIRECT = 4;
	public static int nextSpriteID = 0;
	/**
	 * メンバ変数
	 */
	// trueにするとスプライトを消滅させる変数
	public boolean end = false;
	/**
	 * スプライトの位置
	 * スプライトの一番左上の部分の座標を指す
	 */
	protected int x=0,y=0;
	// スプライトの速度
	protected int vx=0, vy=0;
	// スプライトの向き
	protected int direction=0;
	// スプライトの大きさ
	protected int width=0, height=0;
	// スプライトのID
	protected int spriteID;
	// スプライトのアニメーション用変数
	protected int animationStatus;
	protected int animationFrame;
	// ジャンプしているかどうかの判定変数
	protected boolean jump = false;
	// ジャンプ中の回数
	protected int jumpCount = 0;
	/**
	 * 無敵状態かどうか
	 * ダメージくらって点滅しているあの状態を表わす
	 */
	protected boolean invisible = false;
	
	// スプライトの画像の左上座標
	protected int IMAGE_X;
	protected int IMAGE_Y;
	// スプライトのイメージ
	protected Image image = Data.image.spriteImage;
	/**
	 * こっからメソッド
	 */
	/**
	 * コンストラクタ
	 * 各スプライトに固有のIDを振り分けて、
	 * 向きとアニメーション用変数を初期化
	 */
	public Sprite(){
		spriteID = nextSpriteID;
		nextSpriteID++;
		animationStatus = 0;
		animationFrame = Data.frame;
		direction = 0;
	}
	/**
	 * コンストラクタ
	 * 座標を決めている
	 */
	public Sprite(int x, int y){
		this();
		this.x = x;
		this.y = y;
	}
	/**
	 * ジャンプしていれば true
	 */
	public boolean jumping(){ return false; }

	// 地面に着地したときの処理
	public void land(){ jumpCount = 0; jump = false; }
	public boolean isInvisible(){ return invisible; }
	
	public void setX(int x){ this.x = x; }
	public void setY(int y){ this.y = y; } 
	public int getX(){ return x; }
	public int getY(){ return y; }
	public int getVx(){ return vx; }
	public int getVy(){ return vy; }
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public int getDirection(){ return direction; }
	public int getSpriteID(){ return spriteID; }

	// 画面外に出たときの処理
	public void screenOut(){}
	
	public void setVx(int vx){
		setV(vx,this.vy);
	}
	public void setVy(int vy){
		setV(this.vx,vy);
	}
	
	public void setV(int vx, int vy){
		this.vx = vx;
		this.vy = vy;
	}

	// スプライトのupdate
	public void update(){}
	/**
	 * スプライトのアニメーションのupdate
	 * f フレームごとに画像が切り替わる
	 */
	public void animationUpdate(int f){
		if(Data.frame - animationFrame >= f){
			animationStatus = 1 - animationStatus;
			animationFrame = Data.frame;
		}
	}
	// スプライトの移動処理
	public void move(){
		x += vx;
		y += vy;
	}
	// スプライトがマップに当たった場合の処理
	// 引数はどの方向に当たったかとその寸前の位置
	public void mapHit(int dir, int dest){}
	// プレイヤーがスプライトに触れたときの関数
	public void touch(Sprite s, int dir, int[] dest){}
	// このスプライトに対してspriteが攻撃したときの関数
	public void attacked(Sprite sprite){}
	// 描画処理
	public void draw(Graphics g, int screenX, int screenY){
		int ix = IMAGE_X+animationStatus * width;
		int iy = IMAGE_Y+direction * height;
		g.drawImage(image,
			x-screenX, y-screenY, x-screenX+width, y-screenY+height,
			ix, iy, ix+width, iy+height,
			null
		);
	}
}