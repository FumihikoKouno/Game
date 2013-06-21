import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import java.io.*;

import javax.swing.ImageIcon;

public class Field implements Common{
	
	private Image fieldImage = new ImageIcon(getClass().getResource("image/field.gif")).getImage();
	private Image blockImage = new ImageIcon(getClass().getResource("image/block.gif")).getImage();
	private Image selectImage = new ImageIcon(getClass().getResource("image/selectBlock.gif")).getImage();
	
	private MainPanel panel;
	private int color = 1;
	
	private boolean[][] up = new boolean[ROW][COL];
	private boolean[][] down = new boolean[ROW][COL];
	private boolean[][] left = new boolean[ROW][COL];
	private boolean[][] right = new boolean[ROW][COL];
	
	public void setMousePoint(Point p){
		if(p.X() >= BLOCK_START.X() && p.X() < BLOCK_END.X() && p.Y() >= BLOCK_START.Y() && p.Y() < BLOCK_END.Y()){
			clickBlock(p);
		}
		if(p.X() >= UP_START.X() && p.X() < UP_END.X() && p.Y() >= UP_START.Y() && p.Y() < UP_END.Y()){
			clickUp(p);
		}
		if(p.X() >= DOWN_START.X() && p.X() < DOWN_END.X() && p.Y() >= DOWN_START.Y() && p.Y() < DOWN_END.Y()){
			clickDown(p);
		}
		if(p.X() >= LEFT_START.X() && p.X() < LEFT_END.X() && p.Y() >= LEFT_START.Y() && p.Y() < LEFT_END.Y()){
			clickLeft(p);
		}
		if(p.X() >= RIGHT_START.X() && p.X() < RIGHT_END.X() && p.Y() >= RIGHT_START.Y() && p.Y() < RIGHT_END.Y()){
			clickRight(p);
		}
		if(p.X() >= 550 && p.Y() >= 450) output();
	}
	public void clickUp(Point p){
		int y = (p.Y()-UP_START.Y())/BLOCK_SIZE;
		int x = (p.X()-UP_START.X())/BLOCK_SIZE;
		up[y][x] = !up[y][x];
	}
	public void clickDown(Point p){
		int y = (p.Y()-DOWN_START.Y())/BLOCK_SIZE;
		int x = (p.X()-DOWN_START.X())/BLOCK_SIZE;
		down[y][x] = !down[y][x];
	}
	public void clickLeft(Point p){
		int y = (p.Y()-LEFT_START.Y())/BLOCK_SIZE;
		int x = (p.X()-LEFT_START.X())/BLOCK_SIZE;
		left[y][x] = !left[y][x];
	}
	public void clickRight(Point p){
		int y = (p.Y()-RIGHT_START.Y())/BLOCK_SIZE;
		int x = (p.X()-RIGHT_START.X())/BLOCK_SIZE;
		right[y][x] = !right[y][x];
	}
	
	public void clickBlock(Point p){
		color = (p.X()-BLOCK_START.X())/BLOCK_SIZE + 1;
	}
	
	public void output(){
		try{
			boolean ones = false;
			int minY = ROW;
			int minX = COL;
			int maxX = -1;
			PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter("Output.java")));
			output.println("class Output extends Block{");
			output.print("\tprivate static Point[][] shape = {\n\t\t{\t");
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(up[i][j]){
						if(ones){
							output.print(",new Point("+j+","+i+")" );
						}else{
							ones = true;
							output.print("new Point("+j+","+i+")");
						}
						minY = Math.min(minY,i);
						minX = Math.min(minX,j);
						maxX = Math.max(maxX,j);
					}
				}
				output.print("\n\t\t\t");
			}
			ones = false;
			output.print("\n\t\t},\n\t\t{\t");
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(right[i][j]){
						if(ones){
							output.print(",new Point("+j+","+i+")" );
						}else{
							ones = true;
							output.print("new Point("+j+","+i+")");
						}
					}
				}
				output.print("\n\t\t\t");
			}
			output.print("\n\t\t},\n\t\t{\t");
			ones = false;
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(down[i][j]){
						if(ones){
							output.print(",new Point("+j+","+i+")" );
						}else{
							ones = true;
							output.print("new Point("+j+","+i+")");
						}
					}
				}
				output.print("\n\t\t\t");
			}
			output.print("\n\t\t},\n\t\t{\t");
			ones = false;
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL; j++){
					if(left[i][j]){
						if(ones){
							output.print(",new Point("+j+","+i+")" );
						}else{
							ones = true;
							output.print("new Point("+j+","+i+")");
						}
					}
				}
				output.print("\n\t\t\t");
			}
			output.println("\n\t\t}};\n\n");
			
			output.println("\tpublic Output(Field field){");
			output.println("\t\tsuper(new Point("+ (COL/2-(maxX-minX+1)/2) +","+ (-minY) +"), shape, "+color+", field);");
			output.println("\t}");
			output.println("}");
			output.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void draw(Graphics g){
		g.drawImage(fieldImage,0,0,null);
		for(int i = 0; i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(up[i][j]){
					int x = UP_START.X() + j * BLOCK_SIZE;
					int y = UP_START.Y() + i * BLOCK_SIZE;
					g.drawImage(blockImage,x,y,x+BLOCK_SIZE,y+BLOCK_SIZE,
						(color-1)*BLOCK_SIZE,0,color*BLOCK_SIZE,BLOCK_SIZE,
						null);
				}
				if(down[i][j]){
					int x = DOWN_START.X() + j * BLOCK_SIZE;
					int y = DOWN_START.Y() + i * BLOCK_SIZE;
					g.drawImage(blockImage,x,y,x+BLOCK_SIZE,y+BLOCK_SIZE,
						(color-1)*BLOCK_SIZE,0,color*BLOCK_SIZE,BLOCK_SIZE,
						null);
				}
				if(left[i][j]){
					int x = LEFT_START.X() + j * BLOCK_SIZE;
					int y = LEFT_START.Y() + i * BLOCK_SIZE;
					g.drawImage(blockImage,x,y,x+BLOCK_SIZE,y+BLOCK_SIZE,
						(color-1)*BLOCK_SIZE,0,color*BLOCK_SIZE,BLOCK_SIZE,
						null);
				}
				if(right[i][j]){
					int x = RIGHT_START.X() + j * BLOCK_SIZE;
					int y = RIGHT_START.Y() + i * BLOCK_SIZE;
					g.drawImage(blockImage,x,y,x+BLOCK_SIZE,y+BLOCK_SIZE,
						(color-1)*BLOCK_SIZE,0,color*BLOCK_SIZE,BLOCK_SIZE,
						null);
				}
			}
		}
		
	}
}
