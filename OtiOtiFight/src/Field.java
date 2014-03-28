import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;


public class Field extends JPanel {
	public static final int MAX_SIZE = 6;
	public int row = MAX_SIZE;
	public int col = MAX_SIZE;
	
	public static int BLOCK_START_X = 30;
	public static int BLOCK_START_Y = 30;
	
	public static int BLOCK_END_X = MAX_SIZE*Block.SIZE+BLOCK_START_X;
	public static int BLOCK_END_Y = MAX_SIZE*Block.SIZE+BLOCK_START_Y;
	
	public Character chara;

	
	public Block[][] blocks;
	public Field(){
		init();
	}
	
	public void init(){
		setPreferredSize(new Dimension(400,400));

		chara = new Character(this);
		blocks = new Block[row][col];
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				blocks[i][j] = new Block();
			}
		}
	}
	
	public void update(){
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				blocks[i][j].update();
			}
		}
		chara.update();
	}
	
	public boolean blockExists(int x, int y){
		return blocks[y][x].falled();
	}
	
	public void attack(int x, int y){
		blocks[y][x].attacked();
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(0,0,400,400);
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				int x = j*Block.SIZE+BLOCK_START_X;
				int y = i*Block.SIZE+BLOCK_START_Y;
				g.setColor(Color.BLACK);
				g.drawRect(x,y,Block.SIZE,Block.SIZE);
				g.setColor(Color.WHITE);
				g.fillRect(x+1+blocks[i][j].getOtiTime(),y+1+blocks[i][j].getOtiTime(),Block.SIZE-2-(blocks[i][j].getOtiTime()<<1),Block.SIZE-2-(blocks[i][j].getOtiTime()<<1));
			}
		}
		g.setColor(Color.BLUE);
		g.fillRect(BLOCK_START_X+chara.getX(), BLOCK_START_Y+chara.getY(),Character.SIZE,Character.SIZE);
	}

}
