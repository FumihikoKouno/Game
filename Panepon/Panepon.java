/**
 * ゲームのメイン
 * いわゆるフロントエンドといってもいいかもしれない
 */
import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.JFrame;


public class Panepon extends JFrame {
	MainPanel panel;
	Container contentPane;
	// コンストラクタ
	public Panepon() {
		// タイトルのセット
		setTitle("Panepon");
		// メインパネルのインスタンス生成
		panel = new MainPanel();
		// Paneponフレームの表示領域(コンポーネント?)を得る
		contentPane = getContentPane();
		// メインパネルを表示領域に追加
		contentPane.add(panel,BorderLayout.CENTER);
		// ウィンドウサイズを表示領域に合わせる
		pack();
	}
	
	// ゲームスタート
	public void start(){
		panel.start();
	}
	
	// main関数
	public static void main(String[] args) {
		// 引数に応じて表示倍率セット
		if(args.length >= 1 && args[0] != null){
			try{
				Data.zoom = Integer.parseInt(args[0]);
			}catch(NumberFormatException e){
				Data.zoom = 1;
			}
		}
		// Paneponインスタンス生成
		Panepon frame = new Panepon();
		// 閉じるボタン(右上の×)を押された処理を設定
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 実際に表示
		frame.setVisible(true);
		// ゲームスタート
		frame.start();
	}
}