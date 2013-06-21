class F extends Block{
	// shape[上右下左][ブロックの位置]
	private static Point[][] shape = {
		{	new Point(0,1),new Point(1,1),new Point(2,1),new Point(3,1),new Point(4,1),
			new Point(2,2),new Point(4,2),
			new Point(4,3) },
		{	new Point(3,0),
			new Point(3,1),
			new Point(2,2),new Point(3,2),
			new Point(3,3),
			new Point(1,4),new Point(2,4),new Point(3,4) },
		{	new Point(0,1),
			new Point(0,2),new Point(2,2),
			new Point(0,3),new Point(1,3),new Point(2,3),new Point(3,3),new Point(4,3) },
		{	new Point(1,0),new Point(2,0),new Point(3,0),
			new Point(1,1),
			new Point(1,2),new Point(2,2),
			new Point(1,3),
			new Point(1,4) } };
	public F(Field field){
		super(new Point(3,-1), shape, 8, field);
	}
}