class Deleter extends Block{
	// shape[上右下左][ブロックの位置]
	private static Point[][] shape = {
		{ new Point(0,0) },
		{ new Point(0,0) },
		{ new Point(0,0) },
		{ new Point(0,0) }};
	
	public Deleter(Field field){
		super(new Point(4,0), shape, 9, field);
	}
	
}