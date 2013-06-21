class Changer extends Block{
	private static Point[][] shape = {
		{ new Point(0,0) },
		{ new Point(0,0) },
		{ new Point(0,0) },
		{ new Point(0,0) }
	};


	public Changer(Field field){
		super(new Point(4,0), shape, 11, field);
	}
}
