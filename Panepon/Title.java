/**
 * タイトルクラス
 */
import java.awt.Graphics;

class Title{
	// カーソル
	private Cursor cursor;
	// 各モードのメッセージ
	private MessageWindow mw;
	private MessageWindow endless;
	private MessageWindow scoreAttack;
	private MessageWindow stageClear;
	private MessageWindow demo;
	private MessageWindow ranking;
	private MessageWindow exit;
	private MessageWindow config;
	// ハードモード用変数
	private boolean hardReleased;
	private int hardCount;
	// メッセージの表示位置
	private final int messageX = 5;
	private final int messageY = 300;
	private final int messageCOL = 15;
	private final int messageROW = 2;
	public Title(){
		cursor = new Cursor(0,0);
	}
	// 初期化
	public void init(){
		// カーソルがループするように
		cursor.setLoopAble(true);
		// 各メッセージのインスタンス生成
		endless = new MessageWindow("時間制限なしでゲームオーバーになるまで続くモードです。",messageX,messageY,messageCOL,messageROW);
		scoreAttack = new MessageWindow("2分間でできるだけスコアを稼ぐモードです。",messageX,messageY,messageCOL,messageROW);
		stageClear = new MessageWindow("すべてのパネルが一定ラインを下回るとクリアになるモードです。",messageX,messageY,messageCOL,messageROW);
		demo = new MessageWindow("デモです。基本的な操作説明をします。",messageX,messageY,messageCOL,messageROW);
		ranking = new MessageWindow("ランキングを表示します。",messageX,messageY,messageCOL,messageROW);
		config = new MessageWindow("設定を変更します",messageX,messageY,messageCOL,messageROW);
		exit = new MessageWindow("プログラムを終了します。",messageX,messageY,messageCOL,messageROW);
		// ハードモード用カウント初期化
		hardCount = 0;
		hardReleased = true;
		// カーソル最大値を設定
		Data.cursorMaxX = 0;
		Data.cursorMaxY = Data.EXIT-1;
	}
	// カーソル位置リセット
	public void cursorReset(){
		cursor.set(0,0);
	}
	
	// エンターが押されたときの処理
	private void enter(){
		if(cursor.getY()+1 == Data.DEMO && Data.hard == 2) return;
		Data.gameStatus = cursor.getY()+1;
	}

	// 表示するメッセージの種類を設定する
	private void messageUpdate(){
		switch(cursor.getY()+1){
		case Data.ENDLESS:
			mw = endless;
			break;
		case Data.SCORE_ATTACK:
			mw = scoreAttack;
			break;
		case Data.STAGE_CLEAR:
			mw = stageClear;
			break;
		case Data.DEMO:
			mw = demo;
			break;
		case Data.RANKING:
			mw = ranking;
			break;
		case Data.EXIT:
			mw = exit;
			break;
		}
	}
	
	// update関数
	public void update(){
		// hardのキーが離されていたらフラグを立てる
		if(!KeyStatus.hard) hardReleased = true;
		// 前フレームでhardのキーが離されていて、今のフレームでhardのキーが押されていれば
		else if(hardReleased && KeyStatus.hard){
			// フラグリセットと押した数を加算
			hardReleased = false;
			hardCount++;
		}
		// hardが5回押されたらハードモードに
		if(hardCount == 5){
			Data.hard = 3 - Data.hard;
			hardCount = 0;
		}
		// カーソル移動
		cursor.move();
		// メッセージupdate
		messageUpdate();
		// エンター処理
		if(KeyStatus.enter) enter();
	}
	
	// 描画
	public void draw(Graphics g){
		if(Data.hard == 1){
			g.drawImage(Data.image.titleImage,0,0,Data.WIDTH*Data.zoom,Data.HEIGHT*Data.zoom,
			0,0,Data.WIDTH,Data.HEIGHT,
			null);
		}else if(Data.hard == 2){
			g.drawImage(Data.image.hardTitleImage,0,0,Data.WIDTH*Data.zoom,Data.HEIGHT*Data.zoom,
			0,0,Data.WIDTH,Data.HEIGHT,
			null);
		}
		cursor.draw(g,Data.TITLE);
		if(mw != null) mw.draw(g);
	}
}
