class L extends Block{
	// shape[��E����][�u���b�N�̈ʒu]
	private static Point[][] shape = {
		{ new Point(2,0), new Point(0,1), new Point(1,1), new Point(2,1) },
		{ new Point(1,0), new Point(1,1), new Point(1,2), new Point(2,2) },
		{ new Point(0,2), new Point(0,1), new Point(1,1), new Point(2,1) },
		{ new Point(0,0), new Point(1,0), new Point(1,1), new Point(1,2) }};
	
	public L(Field field){
		super(new Point(3,0), shape, 2, field);
	}
}