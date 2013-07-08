import java.awt.Graphics;
import java.awt.Image;

public class Demo extends Field{
	public Demo(){}
	
	private int demoMouseX;
	private int demoMouseY;
	private MessageWindow mw;
	private int demoState;
	private int demoFrame;
	
	public void update(){
		demoControl();
		super.update();
	}
	
	private void demoControl(){
		int messageX = 5;
		int messageY = 200;
		int messageCOL = 8;
		int messageROW = 10;
		switch(demoState){
		case 0:
			if(demoFrame + 2== Data.frame){
				mw = new MessageWindow("このゲームの操作説明を行います。",messageX,messageY,messageCOL,messageROW);
			}
			if(demoFrame + 30 == Data.frame){
				mw = new MessageWindow("まずキーボードの上下左右でカーソルを動かすことができます。",messageX,messageY,messageCOL,messageROW);
				demoState++;
				demoFrame = Data.frame;
			}
			break;
		case 1:
			KeyStatus.up = false;
			KeyStatus.down = false;
			KeyStatus.left = false;
			KeyStatus.right = false;
			if(demoFrame + 120 == Data.frame){
				demoFrame = Data.frame;
				demoState++;
			}
			if(demoFrame + 100 == Data.frame){
				KeyStatus.right = true;
			}
			if(demoFrame + 80 == Data.frame){
				KeyStatus.left = true;
			}
			if(demoFrame + 60 == Data.frame){
				KeyStatus.down = true;
			}
			if(demoFrame + 40 == Data.frame){
				KeyStatus.up = true;
			}
			if(demoFrame + 20 == Data.frame){
				KeyStatus.up = true;
			}
			break;
		case 2:
			KeyStatus.change = false;
			KeyStatus.right = false;
			if(demoFrame + 2 == Data.frame){
				mw = new MessageWindow("Spaceキーを押すことでカーソルの位置のパネルを入れ替えることができます。入れ替えは左右の入れ替えのみです。",messageX,messageY,messageCOL,messageROW);
			}
			if(demoFrame + 100 == Data.frame){
				demoFrame = Data.frame;
				demoState++;
			}
			if(demoFrame + 80 == Data.frame){
				KeyStatus.change = true;
			}
			if(demoFrame + 60 == Data.frame){
				KeyStatus.change = true;
			}
			if(demoFrame + 40 == Data.frame){
				KeyStatus.change = true;
			}
			if(demoFrame + 20 == Data.frame){
				KeyStatus.change = true;
			}
			break;
		case 3:
			KeyStatus.up = false;
			KeyStatus.down = false;
			KeyStatus.right = false;
			KeyStatus.change = false;
			if(demoFrame + 2 == Data.frame){
				mw = new MessageWindow("縦または横に3つ以上の同じ色のパネルをそろえるとパネルを消すことができます。",messageX,messageY,messageCOL,messageROW);
			}
			if(demoFrame + 320 == Data.frame){
				demoState++;
				demoFrame = Data.frame;
			}
			if(demoFrame + 220 == Data.frame){
				KeyStatus.change = true;
			}
			if(demoFrame + 190 == Data.frame){
				KeyStatus.right = true;
			}
			if(demoFrame + 170 == Data.frame){
				KeyStatus.up = true;
			}
			if(demoFrame + 150 == Data.frame){
				KeyStatus.up = true;
			}
			if(demoFrame + 70 == Data.frame){
				KeyStatus.change = true;
			}
			if(demoFrame + 50 == Data.frame){
				KeyStatus.down = true;
			}
			if(demoFrame + 30 == Data.frame){
				KeyStatus.down = true;
			}
			break;
		case 4:
			KeyStatus.change = false;
			if(demoFrame + 2 == Data.frame){
				mw = new MessageWindow("パネルを落とすこともできます。",messageX,messageY,messageCOL,messageROW);
			}
			if(demoFrame + 150 == Data.frame){
				demoFrame = Data.frame;
				demoState++;
			}
			if(demoFrame + 50 == Data.frame){
				KeyStatus.change = true;
			}
			break;
		case 5:
			if(demoFrame + 2 == Data.frame){
				mw = new MessageWindow("時間経過とともにパネルがせりあがってきます。",messageX,messageY,messageCOL,messageROW);
			}
			if(demoFrame + 10 == Data.frame){ Data.scrollOffset += Data.SCROLL_UNIT; }
			if(demoFrame + 20 == Data.frame){ Data.scrollOffset += Data.SCROLL_UNIT; }
			if(demoFrame + 30 == Data.frame){ Data.scrollOffset += Data.SCROLL_UNIT; }
			if(demoFrame + 40 == Data.frame){ Data.scrollOffset += Data.SCROLL_UNIT; }
			if(demoFrame + 50 == Data.frame){ Data.scrollOffset += Data.SCROLL_UNIT; }
			if(demoFrame + 60 == Data.frame){ Data.scrollOffset += Data.SCROLL_UNIT; }
			if(demoFrame + 70 == Data.frame){ Data.scrollOffset += Data.SCROLL_UNIT; }
			if(demoFrame + 80 == Data.frame){
				appearNewLine();
				newLine[0] = new Panel(4);
				newLine[1] = new Panel(3);
				newLine[2] = new Panel(1);
				newLine[3] = new Panel(5);
				newLine[4] = new Panel(5);
				newLine[5] = new Panel(3);
				Data.scrollOffset = 0; 
			}
			if(demoFrame + 90 == Data.frame){ Data.scrollOffset += Data.SCROLL_UNIT; }
			if(demoFrame + 100 == Data.frame){ Data.scrollOffset += Data.SCROLL_UNIT; }
			if(demoFrame + 110 == Data.frame){ Data.scrollOffset += Data.SCROLL_UNIT; }
			if(demoFrame + 120 == Data.frame){ Data.scrollOffset += Data.SCROLL_UNIT; }
			if(demoFrame + 130 == Data.frame){ Data.scrollOffset += Data.SCROLL_UNIT; }
			if(demoFrame + 140 == Data.frame){ Data.scrollOffset += Data.SCROLL_UNIT; }
			if(demoFrame + 150 == Data.frame){ Data.scrollOffset += Data.SCROLL_UNIT; }
			if(demoFrame + 160 == Data.frame){
				Data.scrollOffset = 0;
				appearNewLine();
				newLine[0] = new Panel(3);
				newLine[1] = new Panel(1);
				newLine[2] = new Panel(2);
				newLine[3] = new Panel(3);
				newLine[4] = new Panel(5);
				newLine[5] = new Panel(0);
				demoFrame = Data.frame;
				demoState++; 
			}
			break;
		case 6:
			KeyStatus.down = false;
			KeyStatus.right = false;
			KeyStatus.change = false;
			if(demoFrame + 2 == Data.frame){
				mw = new MessageWindow("このように連鎖をすることができます。",messageX,messageY,messageCOL,messageROW);
			}
			if(demoFrame + 300 == Data.frame){
				demoFrame = Data.frame;
				demoState++;
			}
			if(demoFrame + 120 == Data.frame){
				KeyStatus.change = true;
			}
			if(demoFrame + 80 == Data.frame){
				KeyStatus.down = true;
			}
			if(demoFrame + 60 == Data.frame){
				KeyStatus.down = true;
			}
			if(demoFrame + 40 == Data.frame){
				KeyStatus.down = true;
			}
			if(demoFrame + 20 == Data.frame){
				KeyStatus.right = true;
			}
			break;
		case 7:
			KeyStatus.left = false;
			KeyStatus.change = false;
			if(demoFrame + 2 == Data.frame){
				mw = new MessageWindow("パネルを抜き出すこともできます。",messageX,messageY,messageCOL,messageROW);
			}
			if(demoFrame + 100 == Data.frame){
				demoFrame = Data.frame;
				demoState++;
			}
			if(demoFrame + 60 == Data.frame){
				KeyStatus.change = true;
			}
			if(demoFrame + 40 == Data.frame){
				KeyStatus.left = true;
			}
			if(demoFrame + 20 == Data.frame){
				KeyStatus.left = true;
			}
			break;
		case 8:
			KeyStatus.up = false;
			KeyStatus.left = false;
			KeyStatus.right = false;
			KeyStatus.down = false;
			KeyStatus.change = false;
			if(demoFrame + 2 == Data.frame){
				mw = new MessageWindow("パネルが消えている最中にもカーソルを動かしたりパネルを入れ替えたりできます。",messageX,messageY,messageCOL,messageROW);
			}
			if(demoFrame + 200 == Data.frame){
				demoFrame = Data.frame;
				demoState++;
			}
			
			if(demoFrame + 160 == Data.frame){
				KeyStatus.change = true;
			}
			if(demoFrame + 140 == Data.frame){
				KeyStatus.change = true;
			}
			if(demoFrame + 120 == Data.frame){
				KeyStatus.up = true;
			}
			if(demoFrame + 110 == Data.frame){
				KeyStatus.right = true;
			}
			if(demoFrame + 100 == Data.frame){
				KeyStatus.change = true;
			}
			if(demoFrame + 80 == Data.frame){
				KeyStatus.left = true;
			}
			if(demoFrame + 60 == Data.frame){
				KeyStatus.change = true;
			}
			if(demoFrame + 40 == Data.frame){
				KeyStatus.right = true;
			}
			if(demoFrame + 20 == Data.frame){
				KeyStatus.down = true;
			}
			break;
		case 9:
			if(demoFrame + 2 == Data.frame){
				mw = new MessageWindow("Shiftボタンで早くパネルをせり上げることができます。",messageX,messageY,messageCOL,messageROW);
			}
			if(demoFrame + 100 == Data.frame){
				demoFrame = Data.frame;
				demoState++;
			}
			if(demoFrame + 37 == Data.frame){
				KeyStatus.scroll = false;
				Data.scrollOffset = 0; 
				appearNewLine();
			}
			if(demoFrame + 30 == Data.frame){
				KeyStatus.scroll = true;
			}
			break;
		case 10:
			KeyStatus.left = false;
			KeyStatus.down = false;
			KeyStatus.change = false;
			if(demoFrame + 2 == Data.frame){
				mw = new MessageWindow("パネルを割り込ませるようにして連鎖を作ることもできます。",messageX,messageY,messageCOL,messageROW);
			}
			if(demoFrame + 240 == Data.frame){
				demoFrame = Data.frame;
				demoState++;
			}
			if(demoFrame + 160 == Data.frame){
				KeyStatus.change = true;
			}
			if(demoFrame + 100 == Data.frame){
				KeyStatus.down = true;
			}
			if(demoFrame + 80 == Data.frame){
				KeyStatus.change = true;
			}
			if(demoFrame + 60 == Data.frame){
				KeyStatus.down = true;
			}
			if(demoFrame + 50 == Data.frame){
				KeyStatus.left = true;
			}
			if(demoFrame + 35 == Data.frame){
				KeyStatus.left = true;
			}
			if(demoFrame + 20 == Data.frame){
				KeyStatus.left = true;
			}
			break;
		case 11:
			if(demoFrame + 2 == Data.frame){
				mw = new MessageWindow("操作はキーボードだけでなくマウスで操作することもできます。",messageX,messageY,messageCOL,messageROW);
			}
			if(demoFrame + 20 + Data.PANEL_SIZE * 6 + 50 == Data.frame){
				Data.mousePressed = false;
				demoState++;
				demoFrame = Data.frame;
			}
			if(demoFrame + 20 + Data.PANEL_SIZE * 3 <= Data.frame && demoFrame + 20 + Data.PANEL_SIZE * 6 > Data.frame){
				demoMouseX--;
				for(int j = 0; j < Data.COL; j++){
					if(Data.FIELD_START_X + j * Data.PANEL_SIZE <= demoMouseX && demoMouseX < Data.FIELD_START_X + (j + 1) * Data.PANEL_SIZE){
						Data.pressedX = j;
						break;
					}
				}
			}
			if(demoFrame + 20 < Data.frame && demoFrame + 20 + Data.PANEL_SIZE * 3/* 20 + 96 */ > Data.frame){
				demoMouseX++;
				for(int j = 0; j < Data.COL; j++){
					if(Data.FIELD_START_X + j * Data.PANEL_SIZE <= demoMouseX && demoMouseX < Data.FIELD_START_X + (j + 1) * Data.PANEL_SIZE){
						Data.pressedX = j;
						break;
					}
				}
			}
			if(demoFrame + 20 == Data.frame){
				Data.pressedX = 1;
				Data.pressedY = Data.ROW-1;
				Data.mousePressed = true;
				demoMouseX = Data.FIELD_START_X + Data.PANEL_SIZE*3/2;
				demoMouseY = Data.FIELD_START_Y + (Data.ROW-1)*Data.PANEL_SIZE + Data.PANEL_SIZE/2;
			}
			break;
		case 12:
			if(demoFrame + 2 == Data.frame){
				mw = new MessageWindow("パネルが一番上に達するとゲームオーバーです。",messageX,messageY,messageCOL,messageROW);
			}
			KeyStatus.scroll = true;
			break;
		default:
			if(demoFrame + 30 == Data.frame){
				Data.gameStatus = Data.TITLE;
				Data.keyCansel = false;
				Data.mouseCansel = false;
			}
			break;
		}
		scrollFrame = Data.frame;
	}

	protected void gameOver(){
		if(topExist() && (Data.scrollOffset != 0)){
			KeyStatus.scroll = false;
			if(gameOverFrame == 0) gameOverFrame = Data.frame;
				if(gameOverFrame + 60 <= Data.frame){
				Data.gameStatus = Data.TITLE;
				Data.keyCansel = false;
				Data.mouseCansel = false;
			}else{
				return;
			}
		}
	}


	public void init(){
		super.init();
		mw = null;
		gameOverFrame = 0;
		demoMouseX = 0;
		demoMouseY = 0;
		demoFrame = Data.frame;
		Data.keyCansel = true;
		Data.mouseCansel = true;
		demoState = 0;
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < Data.COL; j++){
				panel[i][j] = null;
			}
		}
		panel[7][0] = new Panel(1);
		panel[7][1] = new Panel(0);
		panel[7][2] = new Panel(2);
		panel[7][3] = new Panel(3);
		panel[7][4] = new Panel(4);
		panel[7][5] = new Panel(5);
		
		panel[8][0] = new Panel(1);
		panel[8][1] = new Panel(1);
		panel[8][2] = new Panel(2);
		panel[8][3] = new Panel(4);
		panel[8][4] = new Panel(0);
		panel[8][5] = new Panel(3);
		
		panel[9][0] = new Panel(5);
		panel[9][1] = new Panel(5);
		panel[9][2] = new Panel(4);
		panel[9][3] = new Panel(5);
		panel[9][4] = new Panel(3);
		panel[9][5] = new Panel(3);
		
		newLine[0] = new Panel(3);
		newLine[1] = new Panel(4);
		newLine[2] = new Panel(1);
		newLine[3] = new Panel(5);
		newLine[4] = new Panel(3);
		newLine[5] = new Panel(2);
	}
	
	public void draw(Graphics g){
		super.draw(g);
		int space_x = 20;
		int space_y = 7;
		Data.setFont(g,Data.SCORE_FONT);
		if(Data.gameStatus == Data.DEMO && Data.mousePressed){
			g.drawImage(Data.image.mouseImage,demoMouseX*Data.zoom,demoMouseY*Data.zoom,
				(demoMouseX+13)*Data.zoom,(demoMouseY+20)*Data.zoom,
				0,0,13,20,null);
		}
		if(mw != null) mw.draw(g);
	}
}
