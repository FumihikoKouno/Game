class O extends Block{
	// shape[上右下左][ブロックの位置]
	private static Point[][] shape = {
		{ new Point(1,1), new Point(2,1), new Point(1,2), new Point(2,2) },
		{ new Point(1,1), new Point(2,1), new Point(1,2), new Point(2,2) },
		{ new Point(1,1), new Point(2,1), new Point(1,2), new Point(2,2) },
		{ new Point(1,1), new Point(2,1), new Point(1,2), new Point(2,2) }};
	
	public O(Field field){
		super(new Point(3,-1), shape, 3, field);
	}
}