import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

class Block implements Common{
	
	private int color;
	private Point point;
	private Point[][] shape;
	private Field field;
	
	private int showType;
	
	private int dir = 0;
		
	public Block(Point point, Point[][] shape, int color, Field field){
		this.color = color;
		this.point = point;
		this.shape = shape;
		this.field = field;
	}
	
	public void setShowType(int type){ showType = type; }
	
	public int color(){ return color; }
	public Point point(){ return point.copy(); }
	public Point[] shape(){
		int len = shape[dir].length;
		Point[] ret = new Point[len];
		for(int i = 0; i < len; i++){
			ret[i] = shape[dir][i].copy();
		}
		return ret;
	}
	public boolean testMove(Point p){
		int len = shape[dir].length;
		Point dest = Point.add(p,point);
		for(int i = 0; i < len; i++){
			if(field.hit(Point.add(dest,shape[dir][i]))) return false;
		}
		return true;
	}
	public Point[][] fullShape(){
		return shape;
	}
	
	public void setInit(Block b){
		point = b.point();
		shape = b.fullShape();
		dir = 0;
	}
	public boolean move(Point p){
		if(testMove(p)){
			Point temp = point.copy();
			point.add(p);
			if(Flag.special){
				switch(color){
				case 9:
					field.delete(point);
					break;
				case 10:
					field.set(temp);
					break;
				default:
					break;
				}
			}
			return true;
		}else{
			return false;
		}
	}
	public boolean warp(Point p){
		for(int i = 0; i < shape[dir].length; i++){
			if(field.hit(Point.add(p,shape[dir][i]))) return false;
		}
		point = p.copy();
		return true;
	}
	public void spin(int dir){
		int roled = (this.dir + dir + 4) % 4;
		Point temp;
		Point distance = new Point(COL,ROW);
		Point dest = point.copy();
		int shapeX,shapeY;
		int minX = COL;
		int maxX = -1;
		int minY = ROW;
		int maxY = -1;
		boolean hit = false;
		for(int i = 0; i < shape[roled].length; i++){
			shapeX = shape[roled][i].X();
			shapeY = shape[roled][i].Y();
			minX = Math.min(minX,shapeX);
			maxX = Math.max(maxX,shapeX);
			minY = Math.min(minY,shapeY);
			maxY = Math.max(maxY,shapeY);
		}
		for(int i = -minY-maxY+1; i < ROW-maxY; i++){
			for(int j = -minX; j < COL-maxX; j++){
				hit = false;
				temp = new Point(j,i);
				for(int k = 0; k < shape[roled].length; k++){
					if(field.hit(Point.add(temp,shape[roled][k]))){
						hit = true;
						break;
					}
				}
				if(!hit){
					Point checkPoint = Point.distance(temp,point);
					if(checkPoint.X() + checkPoint.Y() < distance.X() + distance.Y()){
						distance = checkPoint;
						dest = temp;
					}
				}
			}
		}
		if(distance.X() <= Math.max(1,maxX-minX) && distance.Y() <= Math.max(1,maxY-minY)){
			this.dir = roled;
			point = dest;
		}
	}
	public void draw(Graphics g){
		Image showImage = null;
		switch(showType){
		case BLOCK_SET:
			showImage = image.setImage;
			break;
		default:
			showImage = image.blockImage;
			break;
		}
		for(int i = 0; i < shape[dir].length; i++){
			int x = FIELD_X + (point.X() + shape[dir][i].X()) * BLOCK_SIZE;
			int y = FIELD_Y + (point.Y() + shape[dir][i].Y()) * BLOCK_SIZE;
			if(y >= FIELD_Y){
				g.drawImage(showImage, x, y, x + BLOCK_SIZE, y + BLOCK_SIZE,
					(color-1) * BLOCK_SIZE, 0, (color-1) * BLOCK_SIZE + BLOCK_SIZE, BLOCK_SIZE,
					null);
			}
		}
	}
}