import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

import java.io.*;

class Field implements Common{
	private PrintWriter debug;
	
	private double baseTime;
	private double currentTime;
	private double playTime;
	private boolean turn;
	
	private int score;
	private int lv;
	private int line;
	
	private int changeNumber = BLOCK_NUMBER-1;
	
	private boolean nextCall;
	
	private BGM bgm;
	private boolean effecting;
	boolean[] deleteBlock = new boolean[ROW];

	Block current;
	Block[] kind = new Block[BLOCK_NUMBER];
	private int[][] field = new int[ROW][COL];
	
	private int[] next = new int[3];
	private int hold;
	
	private boolean changed;
	
	Fall fallThread;
	
	Point ghost;
	
	public void init(){
		if(Flag.printDebug){
			try{
				debug = new PrintWriter(new BufferedWriter(new FileWriter("debug.txt")));
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		for(int i = 0; i < 3; i++){
			next[i] = (int)(Math.random() * BLOCK_NUMBER);
		}
		nextBlock();
		culcGhost();
		new Fall(1000).start();
		bgm = new BGM();
		bgm.play();
	}
	
	public void hold(){
		if(changed) return;
		if(Flag.printDebug) debug.println("hold()");
		resetTime();
		Flag.special = false;
		int temp = hold;
		hold = current.color();
		switch(temp){
		case 0:
			nextCall = false;
			nextBlock();
			break;
		case 1:
			current = new Z(this);
			break;
		case 2:
			current = new L(this);
			break;
		case 3:
			current = new O(this);
			break;
		case 4:
			current = new S(this);
			break;
		case 5:
			current = new I(this);
			break;
		case 6:
			current = new J(this);
			break;
		case 7:
			current = new T(this);
			break;
		case 8:
			current = new F(this);
			break;
		case 9:
			current = new Deleter(this);
			break;
		case 10:
			current = new Creater(this);
			break;
		case 11:
			current = new Changer(this);
			break;
		}
		checkGameover();
		culcGhost();
		changeNumber = BLOCK_NUMBER-1;
		changed = true;
	}
	
	public void setTurn(boolean b, boolean end){
		turn = b;
		if(end){
			for(int i = 0; i < ROW; i++){
				if(deleteBlock[i]) delete(i);
			}
		}
	}
	
	public void setEffect(boolean b){
		effecting = b;
		if(!b){
			resetTime();
			nextBlock();
		}
	}
	
	private void delete(int del){
		if(Flag.printDebug) debug.println("delete("+del+")");
		for(int i = del-1; i >= 0; i--){
			for(int j = 0; j < COL; j++){
				field[i+1][j] = field[i][j];
			}
		}
		for(int j = 0; j < COL; j++){
			field[0][j] = 0;
		}
		line++;
		lv = line/10;
	}
	
	public void delete(Point p){
		Point[] shape = current.shape();
		int px = p.X();
		int py = p.Y();
		for(int i = 0; i < shape.length; i++){
			int delX = px + shape[i].X();
			int delY = py + shape[i].Y();
			if(delY < 0) continue;
			field[delY][delX] = 0;
		}
		return;
	}
	
	public boolean delete(){
		boolean check;
		int sumLine = 0;
		for(int i = 0; i < ROW; i++){
			check = true;
			deleteBlock[i] = false;
			for(int j = 0; j < COL; j++){
				check = check && field[i][j] > 0;
			}
			if(check){
				sumLine++;
				deleteBlock[i] = true;
			}
		}
		for(int i = 0; i < ROW; i++){
			if(deleteBlock[i]){
				new Effect(BLOCK_DELETE,current,this).start();
				score += (int)(Math.pow(sumLine,2))*100;
				return true;
			}
		}
		return false;
	}
	
	public boolean hit(Point p){
		int x = p.X();
		int y = p.Y();
		if(x < 0 || x >= COL || y >= ROW) return true;
		if(y < 0 && x >= 0 && x < COL) return false;
		if(current.color() == 9 && Flag.special){
			return false;
		}else{
			return field[y][x] > 0;
		}
	}
	
	public void keyPressed(int key){
		if(effecting) return;
		switch(key){
		case KEY_UP:
			if(Flag.special){
				int x = current.point().X();
				int y = current.point().Y();
				current.warp(ghost);
				int gy = ghost.Y();
				switch(current.color()){
				case 9:
					for(int i = y; i < gy; i++){
						delete(new Point(x,i));
					}
					break;
				case 10:
					for(int i = y; i < gy; i++){
						set(new Point(x,i));
					}
					break;
				default:
					break;
				}
			}else{
				current.warp(ghost);
			}
			set();
			break;
		case KEY_DOWN:
			if(!current.move(DOWN)) set();
			resetTime();
			break;
		case KEY_LEFT:
			current.move(LEFT);
			break;
		case KEY_RIGHT:
			current.move(RIGHT);
			break;
		case KEY_A:
			current.spin(-1);
			break;
		case KEY_S:
			current.spin(1);
			break;
		case KEY_SPACE:
			special();
			break;
		case KEY_SHIFT:
			hold();
			break;
		}
		culcGhost();
	}
	
	public void special(){
		int color = current.color();
		Point point = current.point();
		if(color < 9) return;
		new Effect(BLOCK_SET,current,this).start();
		Flag.special = !Flag.special;
		Block temp = null;
		if(Flag.special){
			switch(color){
			case 11:
				switch(changeNumber){
				case 10:
					temp = new Z(this);
					break;
				case 0:
					temp = new L(this);
					break;
				case 1:
					temp = new O(this);
					break;
				case 2:
					temp = new S(this);
					break;
				case 3:
					temp = new I(this);
					break;
				case 4:
					temp = new J(this);
					break;
				case 5:
					temp = new T(this);
					break;
				case 6:
					temp = new F(this);
					break;
				case 7:
					temp = new Deleter(this);
					break;
				case 8:
					temp = new Creater(this);
					break;
				case 9:
					temp = new Changer(this);
					break;
				}
				changeNumber = (changeNumber+1)%BLOCK_NUMBER;
				current.setInit(temp);
				checkGameover();
				culcGhost();
				Flag.special = false;
				break;
			default:
				break;
			}
		}
	}
	
	public void culcGhost(){
		if(effecting) return;
		
		Point[] shape = current.shape();
		Point dest = current.point();
		dest.add(DOWN);
		
		boolean notUnder = true;
		
		while(true){
			for(int i = 0; i < shape.length; i++){
				if(hit(Point.add(dest,shape[i]))) notUnder = false;
			}
			if(!notUnder){
				dest.add(UP);
				break;
			}else{
				dest.add(DOWN);
			}
		}
		ghost = dest;
	}
	
	public void set(Point p){
		Point[] shape = current.shape();
		Point point = current.point();
		Point temp;
		boolean hit = false;
		int color = current.color();
		int px = p.X();
		int py = p.Y();
		for(int i = 0; i < shape.length; i++){
			int setX = px + shape[i].X();
			int setY = py + shape[i].Y();
			Point setPoint = new Point(setX,setY);
			if(setY < 0) continue;
			for(int j = 0; j < shape.length; j++){
				temp = point.copy();
				temp.add(shape[j]);
				if(temp.equals(setPoint)){
					hit = true;
					break;
				}
			}
			if(!hit) field[setY][setX] = color;
			hit = false;
		}
	}
	
	public synchronized void set(){
		if(effecting) return;
		if(current.testMove(DOWN)) return;
		if(nextCall){
			nextCall = false;
		}else{
			return;
		}
		if(Flag.printDebug) debug.println("set()");
		changeNumber = BLOCK_NUMBER-1;
		changed = false;
		Flag.special = false;
		Point point = current.point();
		Point[] shape = current.shape();
		int color = current.color();
		int px = point.X();
		int py = point.Y();
		for(int i = 0; i < shape.length; i++){
			int setX = px + shape[i].X();
			int setY = py + shape[i].Y();
			if(setY < 0) continue;
			field[setY][setX] = color;
		}
		if(!delete()) new Effect(BLOCK_SET,current,this).start();
	}
	
	public void resetTime(){
		baseTime = java.lang.System.currentTimeMillis();
		currentTime = baseTime;
	}
	
	public void nextBlock(){
		if(effecting) return;
		if(Flag.printDebug) debug.println("nextBlock()");
		
		if(nextCall){
			return;
		}else{
			nextCall = true;
		}
		
		switch(next[0]){
		case 0:
			current = new Z(this);
			break;
		case 1:
			current = new L(this);
			break;
		case 2:
			current = new O(this);
			break;
		case 3:
			current = new S(this);
			break;
		case 4:
			current = new I(this);
			break;
		case 5:
			current = new J(this);
			break;
		case 6:
			current = new T(this);
			break;
		case 7:
			current = new F(this);
			break;
		case 8:
			current = new Deleter(this);
			break;
		case 9:
			current = new Creater(this);
			break;
		case 10:
			current = new Changer(this);
			break;
		}
		checkGameover();
		next[0] = next[1];
		next[1] = next[2];
		next[2] = (int)(Math.random() * BLOCK_NUMBER);
		culcGhost();
	}
	
	private void checkGameover(){
		Point point = current.point();
		Point[] shape = current.shape();
		for(int i = 0; i < shape.length; i++){
			if(hit(Point.add(point,shape[i]))){
				if(Flag.printDebug) debug.close();
				bgm.stop();
				Flag.gameover = true;
				try{
					Thread.sleep(3000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				System.exit(1);
			}
		}
	}
	
	private class Fall extends Thread{
		private int fallSpeed;
		public Fall(int speed){
			resetTime();
			fallSpeed = speed;
			playTime = 1000;
		}
		public void run(){
			while(!Flag.gameover){
				try{
					Thread.sleep(30);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				currentTime = java.lang.System.currentTimeMillis();
				if(fallSpeed-lv*10 < currentTime-baseTime){
					if(!current.testMove(DOWN)){
						while((playTime-(fallSpeed-lv*10) > java.lang.System.currentTimeMillis()-baseTime) && !current.testMove(DOWN));
						if(!current.move(DOWN)){
							set();
						}
					}else{
						current.move(DOWN);
					}
					resetTime();
				}
			}
		}
	}
	public void draw(Graphics g){
		g.drawImage(image.fieldImage,0,0,null);
		for(int i = 0; i < 9; i++){
			int sx = SCORE_X + i * NUMBER_WIDTH;
			int sy = SCORE_Y;
			int num = (score/((int)Math.pow(10,8-i)))%10;
			g.drawImage(image.numberImage,
				sx, sy, sx+NUMBER_WIDTH, sy+NUMBER_HEIGHT,
				num*NUMBER_WIDTH, 0, num*NUMBER_WIDTH+NUMBER_WIDTH, NUMBER_HEIGHT,
				null
			);
		}
		
		for(int i = 0; i < 2; i++){
			int sx = LV_X + i * NUMBER_WIDTH;
			int sy = LV_Y;
			int num = (lv/((int)Math.pow(10,1-i)))%10;
			g.drawImage(image.numberImage,
				sx, sy, sx+NUMBER_WIDTH, sy+NUMBER_HEIGHT,
				num*NUMBER_WIDTH, 0, num*NUMBER_WIDTH+NUMBER_WIDTH, NUMBER_HEIGHT,
				null
			);
		}
		for(int i = 0; i < 4; i++){
			int sx = LINE_X + i * NUMBER_WIDTH;
			int sy = LINE_Y;
			int num = (line/((int)Math.pow(10,3-i)))%10;
			g.drawImage(image.numberImage,
				sx, sy, sx+NUMBER_WIDTH, sy+NUMBER_HEIGHT,
				num*NUMBER_WIDTH, 0, num*NUMBER_WIDTH+NUMBER_WIDTH, NUMBER_HEIGHT,
				null
			);
		}
		
		for(int i = 0; i < 3; i++){
			int nx = NEXT_X + i * NEXT_SPACE;
			int ny = NEXT_Y;
			g.drawImage(image.nextImage,nx,ny,nx+50,ny+50,
				next[i]*50,0,next[i]*50+50,50,
				null);
		}
		g.drawImage(image.nextImage,HOLD_X,HOLD_Y,HOLD_X+50,HOLD_Y+50,
			(hold-1)*50,0,(hold-1)*50+50,50,
			null);
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(field[i][j] > 0){
					int sx = FIELD_X + j*BLOCK_SIZE;
					int sy = FIELD_Y + i*BLOCK_SIZE;
					if(deleteBlock[i] && turn){
						g.drawImage(image.setImage,sx, sy,
							sx + BLOCK_SIZE, sy + BLOCK_SIZE,
							(field[i][j]-1)*BLOCK_SIZE, 0,
							(field[i][j]-1)*BLOCK_SIZE + BLOCK_SIZE, BLOCK_SIZE,
							null);
					}else{
						g.drawImage(image.blockImage,sx, sy,
							sx + BLOCK_SIZE, sy + BLOCK_SIZE,
							(field[i][j]-1)*BLOCK_SIZE, 0,
							(field[i][j]-1)*BLOCK_SIZE + BLOCK_SIZE, BLOCK_SIZE,
							null);
					}
				}
			}
		}
		if(!effecting){
			Point[] ghostShape = current.shape();
			int gx = ghost.X();
			int gy = ghost.Y();
			for(int i = 0; i < ghostShape.length; i++){
				int color = current.color();
				int sx = FIELD_X + (gx + ghostShape[i].X())*BLOCK_SIZE;
				int sy = FIELD_Y + (gy + ghostShape[i].Y())*BLOCK_SIZE;
				if(sy < FIELD_Y) continue;
				g.drawImage(image.ghostImage,sx, sy,
					sx + BLOCK_SIZE, sy + BLOCK_SIZE,
					(color-1)*BLOCK_SIZE, 0,
					(color-1)*BLOCK_SIZE + BLOCK_SIZE, BLOCK_SIZE,
					null);
			}
		}
		if(!turn) current.draw(g);
	}
}