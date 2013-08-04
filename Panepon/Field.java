/**
 * ゲームプレイ画面の基底クラス
 * ゲームプレイの基本操作の関数とか
 * いろんなモードに共通の変数を定義している
 * このクラスを継承することで比較的簡単にいろんなモードができる
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import java.util.Random;

import javax.swing.ImageIcon;

public class Field{
	
	public Field(){}
	/**
	 * ゲームオーバーになった瞬間に設定される変数
	 * 名前入力とかタイトルに戻るとかのフレーム数になる
	 */
	protected int gameOverFrame;
	// エフェクトリスト
	protected ArrayList<Effect> effect = new ArrayList<Effect>();
	// ゲーム開始の瞬間のフレーム
	protected int startFrame;
	// スクロールした瞬間のフレーム
	protected int scrollFrame;
	// マウス操作でつかんでいるパネル、それのx、y座標
	protected int pressingPanelX;
	protected int pressingPanelY;
	private Panel pressingPanel;
	// パネル入れ替えキーのspaceが離されているかどうか
	private boolean cursorReleased;
	// 乱数用変数
	protected Random random = new Random();
	// フィールドのパネル配列
	protected Panel[][] panel = new Panel[Data.ROW][Data.COL];
	// カーソル
	protected Cursor cursor;
	// 新たに出てくる一列のパネル
	protected Panel[] newLine = new Panel[Data.COL];
	// マウスのボタンが離されているかどうか
	private boolean mouseReleased;
	// リトライボタンが離されているかどうか
	private boolean retryReleased;
	// タイトルに戻るボタンが離されているかどうか
	private boolean toTitleReleased;
	// リトライ関数
	public void retry(){
		KeyStatus.setAll(false);
		init();
	}
	// 毎フレームごとのupdate関数
	public void update(){
		// ゲーム開始前だったら何もしない
		if(startFrame > Data.frame) return;
		// リトライキーが離されていればフラグセット
		if(!KeyStatus.retry) retryReleased = true;
		// リトライキーが前フレームでは離されていて、今フレームで押された場合リトライする
		if(retryReleased && KeyStatus.retry){
			retryReleased = false;
			retry();
			return;
		}
		// タイトルについてもリトライ同様
		if(!KeyStatus.toTitle) toTitleReleased = true;
		if(toTitleReleased && KeyStatus.toTitle){
			toTitleReleased = false;
			Data.gameStatus = Data.TITLE;
			Data.keyCansel = false;
			Data.mouseCansel = false;
			return;
		}
		// ゲームオーバー判定兼ゲームオーバー処理関数を呼ぶ
		gameOver();
		// ゲームオーバーフレームが0出ない場合ゲームオーバーしたことになるので以降の処理はしない
		if(gameOverFrame != 0) return;
		// カーソルのx、y座標を保持しておく
		int ty = cursor.getY();
		int tx = cursor.getX();
		// せり上げレベルはスコア/1000(小数以下切り捨て)とする
		Data.lv = Data.score/1000;
		// せり上げレベルの最大は9
		if(Data.lv > 9) Data.lv = 9;
		if(!Data.mousePressed){
			// マウスのボタンが押されていなければキーボードによるカーソル操作処理
			
			// もしマウスが持っているパネルがあればそれを離す
			if(pressingPanel != null) releasePanel();
			// マウスボタンが離したことを示しておく
			mouseReleased = true;
			// カーソルを押されているキーに対応して動かす
			cursor.move();
			// 入れ替えキーを離したフラグ立て
			if(!KeyStatus.change) cursorReleased = true;
			// 前フレームに入れ替えキーが離されていて今フレームで入れ替えキーが押されていればパネルを入れ替える
			if(KeyStatus.change && cursorReleased) swapping();
		}else{
			// マウスのボタンが押されていた場合はマウスによるカーソル操作処理
			
			// 前フレームにマウスボタンが離されていたらパネルをつかむ処理
			if(mouseReleased){
				pressPanel();
				mouseReleased = false;
				cursor.set(Data.pressedX,Data.pressedY);
			}
			cursor.setX(Data.pressedX);
			// つかんでいるパネルがあり、それが消えているか、誤ったパネルだったらそれを離す
			if(pressingPanel != null && (pressingPanel.isDeleting() || pressingPanel != panel[pressingPanelY][pressingPanelX])) releasePanel();
			// それ以外はパネルを動かす処理
			else panelMove();
		}
		// せり上げ
		scroll();
		// フィールドにあるすべてのパネルのupdate
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] != null) panel[i][j].update();
			}
		}
		// エフェクトのupdate
		for(int i = 0; i < effect.size(); i++){
			effect.get(i).update();
		}
		// 落ちるパネルと消えるパネルのフラグをセット
		setFallingFlag();
		setDeleteFlag();
		// 実際にパネルを落としたり、消したりする処理
		fallPanels();
		deletePanels();
		// パネルを完全に消す
		endPanels();
		// エフェクトを消す
		deleteEffect();
		// 全ての処理後のカーソルに位置を得る
		int cx = cursor.getX();
		int cy = cursor.getY();
		// カーソルの位置が動いていたらreplay用に記録
		if(ty != cy || tx != cx){
			Data.replayCursorFrame.add(new Integer(Data.frame-startFrame));
			Data.replayCursorX.add(new Integer(cx));
			Data.replayCursorY.add(new Integer(cy));
		}
		// 最大連鎖を更新
		if(Data.maxChain < Data.chain) Data.maxChain = Data.chain;
		// 連鎖が切れたら連鎖数を0にする
		if(chainResetable()) Data.chain = 0;
	}
	
	// マウスによってパネルを動かす関数
	private void panelMove(){
		// 動くかどうかのフラグとどっちに動くかのフラグ
		boolean movable = true;
		boolean left = true;
		int x = -1;
		int y = -1;
		int newOffset = 0;
		// つかんでいるパネルがなかったり、移動中だったり、マウスの指すxに変化がなかったら処理を終了
		if(pressingPanel == null) return;
		if(pressingPanel.cMoving()) return;
		if(pressingPanelX == Data.pressedX) return;
		// つかんでいるパネルが右に動く場合
		if(pressingPanelX < Data.pressedX){
			left = false;
			x = pressingPanelX + 1;
			y = pressingPanelY;
			// newOffsetは移動するパネルに設定する横移動のオフセット
			newOffset = -Data.PANEL_SIZE;
			// 移動先パネルが存在し、そのパネルが横移動中や消えている最中なら移動不可とする
			if(panel[y][x] != null && (panel[y][x].cMoving() || panel[y][x].isDeleting())) movable = false;
		}
		// つかんでいるパネルが左に動く場合
		if(pressingPanelX > Data.pressedX){
			left = true;
			x = pressingPanelX - 1;
			y = pressingPanelY;
			// newOffsetは移動するパネルに設定する横移動のオフセット
			newOffset = Data.PANEL_SIZE;
			// 移動先パネルが存在し、そのパネルが横移動中や消えている最中なら移動不可とする
			if(panel[y][x] != null && (panel[y][x].cMoving() || panel[y][x].isDeleting())) movable = false;
		}
		// 移動可能なら移動する
		if(movable){
			// 左移動と右移動で変える
			if(left) swapPanel(x,y);
			else swapPanel(pressingPanelX,pressingPanelY);
			// つかんでいるパネルのx、y座標を更新
			pressingPanelX = x;
			pressingPanelY = y;
		}
	}
	
	// マウスでパネルを離す関数
	private void releasePanel(){
		pressingPanel = null;
		pressingPanelX = -1;
		pressingPanelY = -1;
	}
	
	// マウスでパネルをつかむ関数
	private void pressPanel(){
		int x = Data.pressedX;
		int y = Data.pressedY;
		if(x < 0 || x >= Data.COL) return;
		if(y < 0 || y >= Data.ROW) return;
		// そもそもパネルがなかったり、横移動中だったり、消えている最中ならつかめない
		if(panel[y][x] == null || panel[y][x].cMoving() || panel[y][x].isDeleting()) return;
		pressingPanel = panel[Data.pressedY][Data.pressedX];
		pressingPanelX = x;
		pressingPanelY = y;		
	}

	// エフェクト削除関数
	private void deleteEffect(){
		int i = 0;
		while(i < effect.size()){
			if(effect.get(i).ended()) effect.remove(i);
			else i++;
		}
	}
	
	// gameover用関数、各モードで実装
	protected void gameOver(){assert(0);}
	
	// 連鎖数がリセットされるかどうか
	private boolean chainResetable(){
		// 消えているパネルがあり、連鎖が1以下なら連鎖数を1とする
		if(deletePanelExist()){
			if(Data.chain <= 1){
				Data.chain = 1;
				return false;
			}
		}
		// 連鎖中のパネルがなければ連鎖はリセットされる
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				if(panel[i][j].getConnected() != 0) return false;
			}
		}
		return true;
	}

	// 新たに出現するパネルのラインを作る
	protected void createNewLine(){
		int tmp;
		int count;
		for(int i = 0; i < Data.COL; i++){
			while(true){
				count = 1;
				tmp = random.nextInt(Data.PANEL_NUMBER*Data.hard);
				for(int j = i-1; j >= 0; j--){
					if(tmp == newLine[j].getKind()) count++;
					else break;
				}
				for(int j = Data.ROW-1; j >= 0; j--){
					if(panel[j][i] != null && panel[j][i].getKind() == tmp) count++;
					else break;
				}
				if(count < 3) break;
			}
			newLine[i] = new Panel(tmp);
		}
	}

	// フィールドの一番上にパネルがあるか
	protected boolean topExist(){
		for(int i = 0; i < Data.COL; i++){
			if(panel[0][i] != null) return true;
		}
		return false;
	}
	
	// パネルを落とす関数
	private void fallPanels(){
		for(int i = Data.ROW-1; i >= 0; i--){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				// 落ちるフラグが立っていて、そのパネルが縦移動のオフセットが0で消えていなく、下がなければ落ちる(オフセットを設定)
				if(panel[i][j].isFalling()){
					if(!panel[i][j].rMoving() && !panel[i][j].isDeleting() && panel[i+1][j] == null){
						panel[i+1][j] = panel[i][j];
						panel[i][j].setOffset(0,-Data.PANEL_SIZE);
						panel[i][j] = null;
					}
				}
			}
		}
	}

	// カーソルの位置のパネルを入れ替える関数
	private void swapping(){
		cursorReleased = false;
		int x = cursor.getX();
		int y = cursor.getY();
		boolean leftMovable = true;
		boolean rightMovable = true;
		// カーソルの左側が存在し横移動していたり、消えていたりしたら動かせない
		if(panel[y][x] != null && (panel[y][x].cMoving() || panel[y][x].isDeleting())) leftMovable = false;
		// カーソルの右側が存在し横移動していたり、消えていたりしたら動かせない
		if(panel[y][x+1] != null && (panel[y][x+1].cMoving() || panel[y][x+1].isDeleting())) rightMovable = false;
		// カーソルの左、右の両方を動かせる場合動かす
		if(leftMovable && rightMovable){
			swapPanel(x,y);
		}
	}
	
	// 入れ替えるパネルの左側の座標を引数にとりパネルを実際に入れ替える関数
	protected void swapPanel(int x, int y){
		// 入れ替えたことを記憶
		Data.replaySwapFrame.add(new Integer(Data.frame-startFrame));
		Data.replaySwapX.add(new Integer(x));
		Data.replaySwapY.add(new Integer(y));
		// 両方にパネルがなければ処理終了
		if(panel[y][x] == null && panel[y][x+1] == null) return;
		// 左だけなければ右を動かす
		if(panel[y][x] == null){
			panel[y][x+1].setOffset(Data.PANEL_SIZE,0);
			panel[y][x] = panel[y][x+1];
			panel[y][x+1] = null;
			return;
		}
		// 右だけなければ左を動かす
		if(panel[y][x+1] == null){
			panel[y][x].setOffset(-Data.PANEL_SIZE,0);
			panel[y][x+1] = panel[y][x];
			panel[y][x] = null;
			return;
		}
		// それ以外は入れ替える
		panel[y][x].setOffset(-Data.PANEL_SIZE,0);
		panel[y][x+1].setOffset(Data.PANEL_SIZE,0);
		Panel tmp = panel[y][x];
		panel[y][x] = panel[y][x+1];
		panel[y][x+1] = tmp;
	}

	// 落下中もしくは落下するフラグの立っているパネルがあるかどうか
	private boolean fallingPanelExist(){
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				if(panel[i][j].isFalling()) return true;
			}
		}
	return false;
	}

	// 連鎖中のパネルがあるかどうか
	private boolean connectPanelExist(){
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				if(panel[i][j].getConnected() != 0) return true;
			}
		}
		return false;
	}
	
	// 横移動中のパネルがあるかどうか
	private boolean movingPanelExist(){
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				if(panel[i][j].cMoving()) return true;
			}
		}
		return false;
	}
	
	// スクロールが止まればtrue
	private boolean scrollStop(){
		// ゲームオーバーなら止まる
		if(gameOverFrame != 0) return true;
		// 最上のパネルがありスクロールオフセットが0でないなら止まる(いわゆるゲームオーバーになる状態)
		if(topExist() && Data.scrollOffset != 0) return true;
		// スクロールボタンが押されている場合
		if(KeyStatus.scroll){
			// 落ちているパネルがあったり消えているパネルがあるときに最上のパネルが存在すれば止まる
			if((fallingPanelExist() || connectPanelExist() || deletePanelExist()) && topExist()) return true;
			// それ以外は動く
			else return false;
		}
		//スクロールボタンが押されていない時
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				// 消えているパネルがあったり落ちるパネルがあると止まる
				if(panel[i][j].isDeleting()) return true;
				if(isFallable(j,i)) return true;
				if(panel[i][j].isFalling()) return true;
			}
		}
		return false;
	}
	
	
	// 消えているパネルがあるかどうか
	protected boolean deletePanelExist(){
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				if(panel[i][j].isDeleting()) return true;
			}
		}
		return false;
	}
	
	// 新しいラインをフィールドに出現させる関数(つまり通常の色にする関数)
	protected void appearNewLine(){
		// スクロールに合わせてカーソルを上げる
		cursor.moveUp();
		pressingPanelY -= 1;
		for(int i = 1; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
			panel[i-1][j] = panel[i][j];
			}
		}
		for(int i = 0; i < Data.COL; i++){
			panel[Data.ROW-1][i] = newLine[i];
		}
		// 新たなラインを作る
		createNewLine();
	}

	// スクロール関数
	protected void scroll(){
		// スクロールが止まるならこのフレームでスクロールが動いたことにし、時間をリセット
		if(scrollStop()){
			scrollFrame = Data.frame;
			return;
		}
		// 前のスクロールから一定時間経ったら少し上にあげる
		if(scrollFrame + (10 - Data.lv) * 2 <= Data.frame || KeyStatus.scroll){
			// リプレイ用記録
			Data.replayScrollFrame.add(new Integer(Data.frame-startFrame));
			// オフセット増加
			Data.scrollOffset = (Data.scrollOffset + Data.SCROLL_UNIT*Data.hard) % Data.PANEL_SIZE;
			// 一列分挙がったら新たなラインを出現させる
			if(Data.scrollOffset == 0){
				appearNewLine();
			}
			// スクロールしたフレームを保存
			scrollFrame = Data.frame;
		}
	}
	
	// パネルを完全に消す
	private boolean endPanels(){
		boolean connection;
		for(int j = 0; j < Data.COL;j++){
			connection = false;
			for(int i = Data.ROW-1; i >= 0; i--){
				if(panel[i][j] == null){
					connection = false;
					continue;
				}
				// パネルが消えたら連鎖フラグを立てそのパネルを消す
				if(panel[i][j].end()){
					connection = true;
					panel[i][j] = null;
				}else{
					// パネルがあって連鎖フラグがあれば連鎖設定をする
					if(connection) panel[i][j].setConnected(Data.frame);
				}
			}
		}
		// なぜtrueを返しているか覚えていない、多分仕様変更によるもの。今は使っていないはず
		return true;
	}
	
	// パネルの表示を消す(実際は移動できないし上のパネルは落ちない状態にする)
	private void deletePanels(){
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null) continue;
				if(panel[i][j].isDeleted()){
					if(panel[i][j].getKind() != -1){
						panel[i][j].setKind(-1);
						Data.score += 10;
					}
				}
			}
		}
	}
	
	// 初期化
	public void init(){
		Data.replayScrollFrame.clear();
		Data.replaySwapFrame.clear();
		Data.replaySwapX.clear();
		Data.replaySwapY.clear();
		Data.replayCursorFrame.clear();
		Data.replayCursorX.clear();
		Data.replayCursorY.clear();
		gameOverFrame = 0;
		cursor = new Cursor(Data.INIT_CURSOR_X,Data.INIT_CURSOR_Y);
		cursor.setLoopAble(false);
		Data.cursorMaxX = Data.COL-2;
		Data.cursorMaxY = Data.ROW-1;
		pressingPanel = null;
		pressingPanelX = -1;
		pressingPanelY = -1;
		mouseReleased = true;
		Data.score = 0;
		Data.chain = 0;
		Data.maxDelete = 0;
		Data.maxChain = 0;
		scrollFrame = Data.frame;
		cursorReleased = true;
		toTitleReleased = false;
		retryReleased = false;
		Data.scrollOffset = 0;
	}
	
	// 対応するx、y座標のパネルが消えるパネルかどうか
	protected boolean isDeletable(int x, int y){
		if(panel[y][x].isFalling() || isFallable(x,y) || panel[y][x].isDeleting() || panel[y][x].cMoving()) return false;
		int count_row = 1;
		int count_col = 1;
		int myKind = panel[y][x].getKind();
		for(int u = y-1; u >= 0; u--){
			if(panel[u][x] == null) break;
			if(panel[u][x].isFalling() || isFallable(x,u) || panel[u][x].isDeleting() || panel[u][x].cMoving()) break;
			if(panel[u][x].getKind() != myKind) break;
			else count_row++;
		}
		for(int d = y+1; d < Data.ROW; d++){
			if(panel[d][x] == null) break;
			if(panel[d][x].isFalling() || isFallable(x,d) || panel[d][x].isDeleting() || panel[d][x].cMoving()) break;
			if(panel[d][x].getKind() != myKind) break;
			else count_row++;
		}
		for(int l = x-1; l >= 0; l--){
			if(panel[y][l] == null) break;
			if(panel[y][l].isFalling() || isFallable(l,y) || panel[y][l].isDeleting() || panel[y][l].cMoving()) break;
			if(panel[y][l].getKind() != myKind) break;
			else count_col++;
		}
		for(int r = x+1; r < Data.COL; r++){
			if(panel[y][r] == null) break;
			if(panel[y][r].isFalling() || isFallable(r,y) || panel[y][r].isDeleting() || panel[y][r].cMoving()) break;
			if(panel[y][r].getKind() != myKind) break;
			else count_col++;
		}
		// 3つ以上つながってれば消えるパネルなのでtrue
		if(count_row>=3 || count_col>=3) return true;
		else return false;
	}
	
	// 消えるフラグを立てる
	private void setDeleteFlag(){
		boolean first = true;
		boolean firstChain = true;
		int minI = Data.ROW;
		int minJ = Data.COL;
		int count = 0;
		int max = 0;
		int tmp = 0;
		boolean[][] flag = new boolean[Data.ROW][Data.COL];
		for(int i = 0; i < Data.ROW; i++){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j] == null){
					flag[i][j] = false;
					continue;
				}
				if(isDeletable(j,i)){
					if(minI > i) minI = i;
					if(minJ > j) minJ = j;
					flag[i][j] = true;
					max++;
				}else{
					flag[i][j] = false;
				}
			}
		}
		// 同時消し数の更新
		if(Data.maxDelete < max) Data.maxDelete = max;
		count = max-1;
		for(int i = Data.ROW-1; i >= 0; i--){
			for(int j = Data.COL-1; j >= 0; j--){
				// 消える奴フラグが立ったら
				if(flag[i][j]){
					// 消えるエフェクトをセット
					effect.add(new Effect(Data.DELETE_EFFECT,j,i,panel[i][j].getKind()));
					// 同時消しのエフェクトとポイント追加
					if(first && max > 3){
						effect.add(new Effect(Data.SAME_EFFECT,minJ,minI,max));
						Data.score += max*max*5;
					}
					// 連鎖のエフェクトとポイント追加
					if(firstChain && panel[i][j].getConnected() != 0 && Data.chain > 0){
						firstChain = false;
						Data.chain++;
						effect.add(new Effect(Data.CHAIN_EFFECT,minJ,minI,Data.chain));
						Data.score += Data.chain*Data.chain*10;
					}
					// 最初のフラグを消す(ポイント追加とかエフェクトを重複させないため)
					first = false;
					if(Data.chain == 0) Data.chain = 1;
					// 消えたフレームをセット(順番に消えるようにするためcountとかmaxとかを使う)
					panel[i][j].setDeleteFrame(count,max-1);
					count--;
				}else{
					// 消えないパネルの場合で連鎖フラグが立っていた場合
					if(panel[i][j] != null && panel[i][j].getConnected() < 0){
						// 移動していないパネルであり
						if(!panel[i][j].cMoving() && !panel[i][j].rMoving()){
							// 最下にあったり下のパネルの連鎖フラグがなかったり、消えている最中だったリしてなおかつ自分が消えている最中でなければ連鎖フラグをリセット
							if((i+1==Data.ROW || (panel[i+1][j] != null && (panel[i+1][j].getConnected() == 0 || panel[i+1][j].isDeleting()))) && !panel[i][j].isDeleting()){
								panel[i][j].setConnected(0);
							}
						}
					}
				}
			}
		}
	}
	
	// x、y座標が落ちうるパネルかどうか
	protected boolean isFallable(int x, int y){
		int tmp = panel[y][x].getConnected();
		// 一番下のパネルについて
		if(y == Data.ROW-1){
			// そもそも落ちている最中ならtrue
			if(panel[Data.ROW-1][x].rMoving()) return true;
			else{
				// 連鎖フラグが立っていれば着地した瞬間のフレームをマイナスでセット
				if(tmp != 0) panel[y][x].setConnected(-Data.frame);
				return false;
			}
		}else{
			// このゲームではパネルは斜めに動くことは無いので、横に動いていれば落ちていない
			if(panel[y][x].cMoving()){
				// 連鎖フラグが立っていれば着地した瞬間のフレームをマイナスでセット
				if(tmp != 0) panel[y][x].setConnected(-Data.frame);
				return false;
			}
			// そもそも落ちている最中ならtrue
			if(panel[y][x].rMoving()){
				return true;
			}
			// 下のパネルが存在しなければ落ちるのでtrue
			if(panel[y+1][x] == null){
				return true;
			}
			boolean ret = panel[y+1][x].isFalling();
			// 下のパネルが落ちるパネルで連鎖フラグが立っていれば着地した瞬間のフレームをマイナスでセット
			if(!ret && tmp != 0) panel[y][x].setConnected(-Data.frame);
			// 下のパネルの落ちる判定と同じになる
			return ret;
		}
	}
	
	// 落ちるフラグをセット
	public void setFallingFlag(){
		int tmp = 0;
		for(int i = Data.ROW-1; i>=0; i--){
			for(int j = 0; j < Data.COL; j++){
				if(panel[i][j]==null) continue;
				// ここに入るtmpは下のパネルが完全に消えたときのフレーム数(消えてなければ0)
				tmp = panel[i][j].getConnected();
				// 落ちるパネルでかつ下のパネルが完全に消えてから一定時間経過していれば落ちるフラグを立てる
				panel[i][j].setFalling(isFallable(j,i) && (tmp == 0 || (tmp+Data.DELETE_RAG/Data.hard<=Data.frame)));
			}
		}
	}
	
	// いろいろ描画
	public void draw(Graphics g){
		if(gameOverFrame == 0){
			for(int i = 0; i < Data.ROW; i++){
				for(int j = 0; j < Data.COL; j++){
					if(panel[i][j] == null) continue;
					panel[i][j].draw(g,j,i);
				}
			}
			for(int i = 0; i < Data.COL; i++){
				newLine[i].draw(g,i,Data.ROW);
			}
			cursor.draw(g,Data.ENDLESS);
		}
		if(Data.hard==1){
			g.drawImage(Data.image.fieldImage,0,0,Data.WIDTH*Data.zoom,Data.HEIGHT*Data.zoom,0,0,Data.WIDTH,Data.HEIGHT,null);
		}else if(Data.hard==2){
			g.drawImage(Data.image.hardFieldImage,0,0,Data.WIDTH*Data.zoom,Data.HEIGHT*Data.zoom,0,0,Data.WIDTH,Data.HEIGHT,null);
		}
		for(int i = 0; i < effect.size(); i++){
			effect.get(i).draw(g);
		}
		int space_x = 20;
		int space_y = 7;
		Data.setFont(g,Data.SCORE_FONT);
		g.drawString(Data.score+"",(Data.SCORE_X+space_x)*Data.zoom,(Data.SCORE_Y-space_y)*Data.zoom);
		g.drawString(Data.lv+"",(Data.LV_X+space_x)*Data.zoom,(Data.LV_Y-space_y)*Data.zoom);
		g.drawString(Data.maxChain+"",(Data.MAX_CHAIN_X+space_x)*Data.zoom,(Data.MAX_CHAIN_Y-space_y)*Data.zoom);
		g.drawString(Data.maxDelete+"",(Data.MAX_DELETE_X+space_x)*Data.zoom,(Data.MAX_DELETE_Y-space_y)*Data.zoom);
		if(gameOverFrame != 0){
			g.drawString("Game Over",(Data.WIDTH/2-100)*Data.zoom,(Data.HEIGHT/2)*Data.zoom);
		}
	}
}
