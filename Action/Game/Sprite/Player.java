/**
 * プレイヤーを表わすクラス
 */

package Game.Sprite;

import Game.Common.*;
import Game.Sprite.Weapon.*;
import Game.Body.*;

import java.awt.Graphics;
import java.awt.Color;

public class Player extends Sprite{
	// このフレーム中に何かに着地したらtrue
	private boolean landing = false;
	
	private boolean shiftReleased = true;
	
	private final int END_TIME = 30;
	
	// やられたときのライフ(やられたエフェクト用に)
	private int endedLife;
	
	/**
	 * プレイヤーのジャンプボタンが一度離されたかどうか
	 * これがないとジャンプボタン押しっぱなしで何度もジャンプしてしまう
	 */
	public boolean jumpReleased = true;
	// 上のジャンプが離されたかどうかの攻撃ボタン版
	public boolean attackReleased = true;
	// コイン枚数
	public byte coin;
	// どの武器を装備しているか
	public int weaponID;
	// どの属性を装備しているか
	public int element;
	// 武器
	public Weapon weapon;
	// 体の装備
	public int bodyID;
	
	/**
	 * 攻撃を受けた瞬間のフレーム数
	 * 何フレーム無敵かとかノックバックで何フレーム動けないとか
	 * その辺の判断に用いる
	 */
	private int attackedFrame;
	
	// ライフ(今はただ数値を保持してるだけ、マイナスになろうが関係なし)
	private int life;
	//
	private int lifeMax;
	
	/**
	 * 移動スピード
	 * (今は下に定義されている)速度の値を用いて
	 * 実際に主人公がどのくらいのスピードで動くのかを表わす
	 */
	private byte speed;
	
	/**
	 * 移動速度関係
	 * 全身装備のクラスを参照する
	 */
	private byte invisibleTime;
	private byte nockBackTime;
	private byte defence;
	private byte movingSpeed;
	private byte jumpSpeed;
	private byte jumpMax;
	private byte froat;
	
	public void equipBody(int id){
		bodyID = id;
		equipBody();
	}
	
	public void equipBody(){
		Body body = null;
		switch(bodyID){
		case 0:
			body = new Body0();
			break;
		case 1:
			body = new Body1();
			break;
		default:
			break;
		}
		froat = body.getFroat();
		invisibleTime = body.getInvisibleTime();
		nockBackTime = body.getNockBackTime();
		defence = body.getDefence();
		movingSpeed = body.getMovingSpeed();
		jumpMax = body.getJumpMax();
		jumpSpeed = body.getJumpSpeed();
	}
	
	public Player clone(){
		Player newPlayer = new Player();
		newPlayer.x = this.x;
		newPlayer.y = this.y;
		newPlayer.image = this.image;
		newPlayer.vx = this.vx;
		newPlayer.vy = this.vy;
		newPlayer.width = this.width;
		newPlayer.height = this.height;
		newPlayer.life = this.life;
		newPlayer.weaponID = this.weaponID;
		newPlayer.element = this.element;
		newPlayer.bodyID = this.bodyID;
		return newPlayer;
	}


	/**
	 * コンストラクタ
	 * なんとなく引数なしバージョンも書いてるけど
	 * 今は使ってないし、多分将来的にも使わない
	 */
	public Player(){
		this(0,0);
	}
	/**
	 * コンストラクタ
	 * 主人公の初期位置を引数にとる
	 * 位置と各変数の初期化
	 */
	public Player(int x, int y){
		super(x,y);
		image = Data.image.playerImage;
		endedLife = 1;
		vx = 0; vy = 0;
		width = 32;
		height = 32;
		direction = RIGHT;
		lifeMax = 10;
		life = 10;
		weaponID = 0;
		element = 0;
		equipBody(0);
	}
	public void land(){
		super.land();
		landing = true;
	}
	
	public void testAtShift(){
		if(KeyStatus.pause){
			if(shiftReleased){
				life -= 100;
				StateData.gotElement[1]+=99;
				StateData.gotElement[2]+=99;
				StateData.gotElement[3]+=99;
				StateData.flag.gotWeapon=3;
				StateData.flag.gotBody=3;
				shiftReleased = false;
			}
		}else{
			shiftReleased = true;
		}
	}
	
	public boolean landing(){
		return landing;
	}
	
	/**
	 * 落下用関数
	 * …といっても重力とかの話ではなく
	 * 歩いて崖から落ちた場合などに浮かないための関数
	 */
	public void fall(){
		if(jumpCount == 0) jumpCount = 1;
	}
	
	public void mapHit(int dir, int dest){
		switch(dir){
		case UP:
			vy = dest - y;
			break;
		case DOWN:
			vy = dest - y;
			land();
			break;
		case LEFT:
		case RIGHT:
			vx = dest - x;
			break;
		}
	}
	/**
	 * ジャンプしていれば true
	 */
	public boolean jumping(){
		return jump;
	}
	
	public void screenOut(){
		if(life > 0) life -= 1000;
	}
	
	/**
	 * 各フレームでのupdate用関数
	 */
	public void update(){
		if(life <= 0){
			dead();
			return;
		}
		landing = false;
		/**
		 * 無敵状態およびノックバックの処理
		 */
		if(invisible){
			/**
			 * ノックバック中、操作不能
			 */
			if(Data.frame - attackedFrame < nockBackTime){
				return;
			}
			/**
			 * 無敵が切れる処理
			* ダメージくらってからinvisibleFrame分のフレーム(処理落ちがなければ1.5秒)たったら無敵解除
			 */
			if(Data.frame - attackedFrame > invisibleTime){
				invisible = false;
			}
		}
		/**
		 * 15フレームごとにグラフィックのステータスを変える
		 * これの変化でプニプニしたり、歩く動作をする
		 */
		animationUpdate(15);
		/**
		 * プレイヤーの横方向の速度決め
		 * ダッシュボタンが押されていれば 2 倍にしている
		 * 今のところ書いていないが、speedが早すぎると
		 * 壁をすり抜ける現象が起きるので注意
		 * 例えばData.CHIP_SIZEより大きいと壁を抜けるようになる
		 */
		if(KeyStatus.dash){
			if(jumpCount == 0) speed = (byte)(movingSpeed<<1);
		}else{
			if(jumpCount == 0) speed = (byte)movingSpeed;
		}
		
		// shiftボタンを押すことで何かをテストする
		testAtShift();
		
		/**
		 * 重力分を加算
		 * 但しData.CHIP_SIZEを超えると床をすり抜ける現象が発生するので
		 * そこが最大値になるようにしている
		 * 空中にData.CHIP_SIZEよりも小さいスクリプトがある場合、調整が必要そう
		 */
		if(vy == 0) vy = 1;
		if((Data.frame%froat)==0) vy += Data.gravity;
		if(vy > Data.CHIP_SIZE) vy = Data.CHIP_SIZE-1;
		/**
		 * 攻撃ボタンが押されたときの処理
		 */
		if(KeyStatus.attack){
			if(weapon == null){
				if(/*jumpCount == 0 && */attackReleased){
					attackReleased = false;
					attack();
				}
			}
		}else{
			attackReleased = true;
		}
		/**
		 * ソードで切っている最中は動けないようにしている
		 * ソードを出した位置で固定しているので、動けるとちょっとめり込む
		 */
		/*
		if(weapon != null && weapon instanceof Sword){
			vx = 0;
			vy = 0;
		}else{
			*/
			/**
			 * 移動やジャンプの処理
			 * 壁やスプライトに当たる判定はMapクラスで行う
			 * ここでは何も障害がなかった場合にどれだけ移動するかを設定する
			 */
			setMove();
			setJump();
//		}
	}
	
	/**
	 * ジャンプの処理
	 */
	public void setJump(){
		if(KeyStatus.jump){
			if(jumpReleased && jumpCount < jumpMax){
				// ジャンプボタンが押され かつ ジャンプできる場合
				jumpReleased = false;
				jump = true;
				jumpCount++;
				// ジャンプ力
				vy = -jumpSpeed;
			}
		}else{
			jumpReleased = true;
		}
		if((jumpReleased || !jump) && vy < 0){
			jump = false;
			vy = 0;
		}
	}
	/**
	 * 主人公の移動量とか向きとかをキーに応じてセットする関数
	 */
	public void setMove(){
		if(KeyStatus.up){
			direction = UP;
		}
		if(KeyStatus.down){
			direction = DOWN;
		}
		if(KeyStatus.left){
			if(KeyStatus.right){
				vx = 0;
			}else{
				vx = -speed;
				direction = LEFT;
			}
		}
		if(!KeyStatus.left){
			if(KeyStatus.right){
				vx = speed;
				direction = RIGHT;
			}else{
				vx = 0;
			}
		}
	}
	
	/**
	 * 武器を振る処理
	 */
	public void attack(){
		switch(weaponID){
		case Weapon.SWORD:
			weapon = new Sword();
			break;
		case Weapon.ARROW:
			weapon = new Arrow();
			break;
		}
		switch(direction){
		case UP:
			weapon.setDirection(Weapon.UP);
			break;
		case DOWN:
			weapon.setDirection(Weapon.DOWN);
			break;
		case LEFT:
			weapon.setDirection(Weapon.LEFT);
			break;
		case RIGHT:
			weapon.setDirection(Weapon.RIGHT);
			break;
		}
		weapon.element = element;
		weapon.appear();
		StateData.gotElement[element]--;
		if(StateData.gotElement[element] == 0){
			element = Element.NONE;
		}
	}
	
	/**
	 * d 分ダメージを受けたときの処理
	 * 今はノックバックとかの速度は数値で適当にやってる
	 */
	public void damage(int d){
		if(invisible) return;
		if(d>defence){
			life -= (d-defence);
		}else{
			life -= 1;
		}
		if(weapon != null && weapon instanceof Sword){
			weapon = null;
		}
		switch(direction){
		case UP:
			vx = 0;
			vy = 3;
			break;
		case DOWN:
			vx = 0;
			vy = -3;
			break;
		case LEFT:
			vx = 3;
			vy = 0;
			break;
		case RIGHT:
			vx = -3;
			vy = 0;
			break;
		}
		invisible = true;
		attackedFrame = Data.frame;
	}

	private void dead(){
		if(endedLife > 0){
			vx = vy = 0;
			endedLife = life;
		}else if(life <= endedLife-END_TIME){
			x = StateData.mapData.getFirstX();
			y = StateData.mapData.getFirstY();
			life = lifeMax;
			endedLife = 1;
		}else{
			life--;
		}
	}
	
	/**
	 * 描画処理
	 */
	public void draw(Graphics g, int screenX, int screenY){
		if(endedLife > 0){
			if(!invisible || Data.frame % 2 != 0){
				super.draw(g,screenX,screenY);
			}
			if(weapon != null) weapon.draw(g, screenX, screenY);
			g.setColor(Color.WHITE);

			LifeGauge.draw(g, x-screenX+width/2, y-screenY, life, lifeMax);
			g.drawString("life : " + life, 15,15);
			g.drawString("player : " + x + ", " + y, 15, 30);
			g.drawString("coin : " + coin, 15, 45);
		}else{
			int endFrame = ((endedLife - life) << 2);
			int dx = x - screenX;
			int dy = y - screenY;
			int size;
			if(Data.frame % 8 < 4){
				size = 8;
				dx += 4;
				dy += 4;
			}else{
				size = 16;
			}
			g.setColor(Color.WHITE);
			g.fillOval(dx,dy-endFrame*3/2,size,size);
			g.fillOval(dx-endFrame,dy-endFrame,size,size);
			g.fillOval(dx-endFrame*3/2,dy,size,size);
			g.fillOval(dx-endFrame,dy+endFrame,size,size);
			g.fillOval(dx,dy+endFrame*3/2,size,size);
			g.fillOval(dx+endFrame,dy+endFrame,size,size);
			g.fillOval(dx+endFrame*3/2,dy,size,size);
			g.fillOval(dx+endFrame,dy-endFrame,size,size);
		}
	}
}