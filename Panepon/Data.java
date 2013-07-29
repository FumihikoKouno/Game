/**
 * いろんなクラスから参照されるであろうデータをまとめたクラス
 * すべてのメンバをstaticで定義している
 */
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public class Data{
	/**
	 * ゲームの状態を表わす定数
	 */
	public static final int TITLE = 0;
	public static final int ENDLESS = 1;
	public static final int SCORE_ATTACK = 2;
	public static final int STAGE_CLEAR = 3;
	public static final int DEMO = 4;
	public static final int RANKING = 5;
	public static final int EXIT = 6;

	/**
	 * trueならマウス操作やキー操作を受け付けない
	 * リプレイやデモ用
	 * ただし、後から追加したため
	 * リトライやタイトルに戻るキーは対象外
	 */
	public static boolean mouseCansel;
	public static boolean keyCansel;
	
	// ゲームの経過時間、もしくは残り時間
	public static int time;
	
	// 画面の表示倍率
	public static int zoom = 1;
	
	/**
	 * ハードモード用変数
	 * 1:ノーマルモード
	 * 2:ハードモード
	 */
	public static int hard = 1;

	
	/**
	 * こっからしばらく描画用の座標定数
	 */
	public static final int RANKING_NAME_X = 70;
	public static final int RANKING_TIME_X = 350;
	public static final int RANKING_SCORE_X = 210;
	public static final int RANKING_MAX_CHAIN_X = 430;
	public static final int RANKING_MAX_DELETE_X = 530;
	public static final int RANKING_STAGE_TIME_X = 215;
	public static final int RANKING_STAGE_SCORE_X = 300;
	public static final int RANKING_TOP_Y = 120;
	public static final int RANKING_DIFF_Y = 29;
	
	public static final int SCORE_OUTPUT = 0;
	public static final int SCORE_INPUT = 1;
	
	public static final int MESSAGE_X_SIZE = 20;
	public static final int MESSAGE_Y_SIZE = 20;
	
	public static final int TITLE_CURSOR_X = 310;
	public static final int TITLE_CURSOR_Y = 190;
	public static final int TITLE_DIFFERENCE = 48;
	
	public static final int RANKING_CURSOR_X = 5;
	public static final int RANKING_CURSOR_Y = 93;
	public static final int RANKING_DIFFERENCE = 29;

	public static final int FIELD_START_X = 178;
	public static final int FIELD_START_Y = 64;
	
	public static final int LV_X = 43;
	public static final int LV_Y = 113;

	public static final int REST_X = 447;
	public static final int REST_Y = 113;

	public static final int SCORE_X = 447;
	public static final int SCORE_Y = 202;
	
	public static final int MAX_CHAIN_X = 447;
	public static final int MAX_CHAIN_Y = 296;
	
	public static final int MAX_DELETE_X = 447;
	public static final int MAX_DELETE_Y = 389;
	// 座標定数ここまで
	
	/**
	 * カーソル移動、パネル移動が行われたフレームとその座標
	 * どのフレームでスクロールしたかを保持するArrayList
	 * リプレイ用
	 */
	public static ArrayList<Integer> replayCursorFrame = new ArrayList<Integer>();
	public static ArrayList<Integer> replayCursorX = new ArrayList<Integer>();
	public static ArrayList<Integer> replayCursorY = new ArrayList<Integer>();
	public static ArrayList<Integer> replayScrollFrame = new ArrayList<Integer>();
	public static ArrayList<Integer> replaySwapFrame = new ArrayList<Integer>();
	public static ArrayList<Integer> replaySwapX = new ArrayList<Integer>();
	public static ArrayList<Integer> replaySwapY = new ArrayList<Integer>();

	// 乱数のシード リプレイ用に取っておく
	public static long seed;
	
	/**
	 * カーソルの最大x座標、最大y座標を表わす変数
	 */
	public static int cursorMaxX;
	public static int cursorMaxY;
	
	// どのくらいスクロールしているか
	public static int scrollOffset;

	// パネルの大きさ
	public static final int PANEL_SIZE = 32;
	// 一回のスクロールでどのくらい上昇するか
	public static final int SCROLL_UNIT = 4;
	// 下のパネルが消えた後に少し浮いているフレーム数
	public static final int DELETE_RAG = 10;
	// 揃えてから実際にパネルが消失するまでの時間
	public static final int DELETE_TIME = 50;
	// 消えるパネルの時間差 これを入れることでポンポンポンって順番に消える
	public static final int DELETE_DIFFERENCE_TIME = 10;
	// 1フレームあたりにパネルがどのくらい横に動くか
	public static final int MPF = PANEL_SIZE/2;
	// パネルが1フレームあたり落ちる量
	public static int GRAVITY = PANEL_SIZE/2;
	// パネルの種類の数
	public static final int PANEL_NUMBER = 6;
	// ゲームプレイ時のカーソルの初期位置
	public static final int INIT_CURSOR_X = 2;
	public static final int INIT_CURSOR_Y = 8;
	// 画面の横と縦のサイズ
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	// プログラム起動時からのフレーム数
	public static int frame;
	// せり上げレベル
	public static int lv;
	// 連鎖数
	public static int chain;
	// 1秒間に何フレームか
	public static final int fps = 30;
	// ゲームプレイ時のパネルの横と縦の最大数
	public static final int ROW = 10;
	public static final int COL = 6;
	// スコアアタック時のタイムリミット
	public static final int TIME_LIMIT = fps*120;
	// ステージクリア時のゴールまでのせり上がったライン数
	public static final int CLEAR_LINE = 20;
	
	// マウスが押されているかの判定変数
	public static boolean mousePressed = false;
	// マウスが押されたときにそのカーソルが指しているパネルのx、y座標
	public static int pressedX;
	public static int pressedY;

	// エフェクトの種類を示す定数
	public static final int CHAIN_EFFECT = 0;
	public static final int SAME_EFFECT = 1;
	public static final int DELETE_EFFECT = 2;
	// 連鎖数及び同時消し数のエフェクトの大きさ
	public static final int EFFECT_SIZE = 30;
	// エフェクトが出ているフレーム数
	public static final int EFFECT_TIME = 20;
	// 最大連鎖数
	public static int maxChain;
	// スコア
	public static int score;
	// 最大同時消し数
	public static int maxDelete;
	// ゲームの状態 初期状態はタイトル
	public static int gameStatus = TITLE;
	// 画像データをまとめたクラスのインスタンス
	public static final ImageData image = new ImageData();
	// デバッグ用変数
	private static int debugCount;
	// どの種類のフォントかを示す定数
	public static final int SCORE_FONT = 0;
	public static final int MESSAGE_FONT = 1;
	
	// フォントの設定関数
	public static void setFont(Graphics g, int fontNo){
		switch(fontNo){
		case SCORE_FONT:
			g.setColor(Color.WHITE);
			g.setFont(new Font("ＭＳ Ｐゴシック",0, 24*zoom));
			break;
		case MESSAGE_FONT:
			g.setColor(Color.WHITE);
			g.setFont(new Font("ＭＳ Ｐゴシック",0, 18*zoom));
			break;
		}
	}

	// デバッグ出力
	public static void debugPrint(String s){
		debugCount++;
		System.out.println("frame " + frame + " : " + s);
	}
}
