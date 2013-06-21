class Creater extends Block{
	private static Point[][] shape = {
		{ new Point(0,0) },
		{ new Point(0,0) },
		{ new Point(0,0) },
		{ new Point(0,0) }};


	public Creater(Field field){
		super(new Point(4,0), shape, 10, field);
	}
}
