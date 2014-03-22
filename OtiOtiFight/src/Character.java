
public class Character {
	public static final int SIZE = 30;
	public Field field;
	public int x, y;
	public Character(Field field){
		this.field = field;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public void update(){
		if(KeyStatus.up){
			y--;
			if(y<0) y=0;
		}
		if(KeyStatus.down){
			y++;
			if(y>Field.BLOCK_END_Y-Field.BLOCK_START_Y-SIZE) y = Field.BLOCK_END_Y-Field.BLOCK_START_Y-SIZE;
		}
		if(KeyStatus.left){
			x--;
			if(x<0) x=0;
		}
		if(KeyStatus.right){
			x++;
			if(x>Field.BLOCK_END_X-Field.BLOCK_START_X-SIZE) x = Field.BLOCK_END_X-Field.BLOCK_START_X-SIZE;
		}
		if(KeyStatus.enter){
			field.attack(0,0);
		}
	}

}
