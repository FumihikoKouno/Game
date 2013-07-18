/**
 * プレイヤーを表わすクラス
 */

package Game.Sprite;

import Game.Common.*;
import Game.Sprite.Weapon.*;

import java.awt.Graphics;

public class Player extends Sprite{
	/**
	 * プレイヤーのジャンプボタンが一度離されたかどうか
	 * これがないとジャンプボタン押しっぱなしで何度もジャンプしてしまう
	 */
	public boolean jumpReleased = true;
	// 上のジャンプが離されたかどうかの攻撃ボタン版
	public boolean attackReleased = true;
	// コイン枚数
	public int coin;
	// 武器
	public Weapon weapon;
	/**
	 * 方向と大きさの定義
	 */
	
	/**
	 * 攻撃を受けた瞬間のフレーム数
	 * 何フレーム無敵かとかノックバックで何フレーム動けないとか
	 * その辺の判断に用いる
	 */
	private int attackedFrame;
	
	// ライフ(今はただ数値を保持してるだけ、マイナスになろうが関係なし)
	private int life;
	
	/**
	 * 移動スピード
	 * (今は下に定義されている)速度の値を用いて
	 * 実際に主人公がどのくらいのスピードで動くのかを表わす
	 */
	private int speed;
	
	/**
	 * 移動速度関係
	 * 今はここに定義しているが
	 * 将来的には全身装備のクラスを参照する
	 */
	private int movingSpeed = 6;
	private int jumpSpeed = 15;
	private static final int jumpMax = 2;
	
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
		vx = 0; vy = 0;
		width = 32;
		height = 32;
		life = 10;
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
	/**
	 * 各フレームでのupdate用関数
	 */
	public void update(){
		/**
		 * 無敵状態およびノックバックの処理
		 */
		if(invisible){
			/**
			 * ノックバック中、操作不能
			 */
			if(Data.frame - attackedFrame < 10){
				return;
			}
			/**
			 * 無敵が切れる処理
			 * ダメージくらってから45フレーム(処理落ちがなければ1.5秒)たったら無敵解除
			 */
			if(Data.frame - attackedFrame > 45){
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
			if(jumpCount == 0) speed = 2 * movingSpeed;
		}else{
			if(jumpCount == 0) speed = movingSpeed;
		}
		/**
		 * 重力分を加算
		 * 但しData.CHIP_SIZEを超えると床をすり抜ける現象が発生するので
		 * そこが最大値になるようにしている
		 * 空中にData.CHIP_SIZEよりも小さいスクリプトがある場合、調整が必要そう
		 */
		if((Data.frame&1)==0) vy += Data.gravity;
		if(vy > Data.CHIP_SIZE) vy = Data.CHIP_SIZE-1;
		/**
		 * 攻撃ボタンが押されたときの処理
		 * 今のところ適当に作ったソードだけだけど
		 * 将来的にはどの装備かによって別のインスタンスを作る
		 * 多分どの装備かっていう変数はDataクラスに作ることになると思う
		 */
		if(KeyStatus.attack){
			if(weapon == null){
				if(jumpCount == 0 && attackReleased){
					attackReleased = false;
					// ここでswitch(equipment){ case Data.SWORD: break; case Data.ARROW: break }みたいに場合分け予定
					switch(direction){
					case UP:
					        weapon = new Arrow(Weapon.UP);
//						weapon = new Sword(Weapon.UP);
						break;
					case DOWN:
						weapon = new Arrow(Weapon.DOWN);
//						weapon = new Sword(height, Weapon.DOWN);
						break;
					case LEFT:
						weapon = new Arrow(Weapon.LEFT);
//						weapon = new Sword(Weapon.LEFT);
						break;
					case RIGHT:
						weapon = new Arrow(Weapon.RIGHT);
//						weapon = new Sword(Weapon.RIGHT);
						break;
					}
				}
			}
		}else{
			attackReleased = true;
		}
		/**
		 * ソードで切っている最中は動けないようにしている
		 * ソードを出した位置で固定しているので、動けるとちょっとめり込む
		 */
		if(weapon != null && weapon instanceof Sword){
			vx = 0;
			vy = 0;
		}else{
			/**
			 * 移動やジャンプの処理
			 * 壁やスプライトに当たる判定はMapクラスで行う
			 * ここでは何も障害がなかった場合にどれだけ移動するかを設定する
			 */
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
			if(KeyStatus.jump){
				if(jumpReleased && jumpCount < jumpMax){
					// ジャンプボタンが押され かつ ジャンプできる場合
					jumpReleased = false;
					jump = true;
					jumpCount++;
					// ジャンプ力+1(+1は直後に重力で減る分)
					vy = -(jumpSpeed+Data.gravity);
				}
			}else{
				jumpReleased = true;
			}
			if(jumpReleased && vy < 0){
				jump = false;
				vy = 0;
			}
		}
	}
	/**
	 * d 分ダメージを受けたときの処理
	 * 今はノックバックとかの速度は数値で適当にやってる
	 */
	public void damage(int d){
		life -= d;
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

	/**
	 * 描画処理
	 */
	public void draw(Graphics g, int screenX, int screenY){
		if(!invisible || Data.frame % 2 != 0){
		    super.draw(g,screenX,screenY);
		}
		if(weapon != null) weapon.draw(g, screenX, screenY);
		g.drawString("life : " + life, 15,15);
		g.drawString("player : " + x + ", " + y, 15, 30);
		g.drawString("coin : " + coin, 15, 45);
	}
}