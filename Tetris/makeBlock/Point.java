class Point{
	private int x,y;
	public static Point add(Point p1, Point p2){ return new Point(p1.X() + p2.X(), p1.Y() + p2.Y()); }
	public static Point distance(Point p1, Point p2){ return new Point(Math.abs(p1.X()-p2.X()), Math.abs(p1.Y()-p2.Y())); }
	public Point(){ this(0,0); }
	public Point(int x, int y){ this.x = x; this.y = y; }
	public int X(){ return x; }
	public int Y(){ return y; }
	public int distance(Point p){ return Math.abs(x-p.X()) + Math.abs(y-p.Y()); }
	public void add(Point p){ x += p.X(); y += p.Y(); }
	public void addX(int x){ this.x += x; }
	public void addY(int y){ this.y += y; }
	public Point copy(){ return new Point(x,y); }
	public boolean equals(Point p){ return x == p.X() && y == p.Y(); }
	public String toString(){ return "(" + x + "," + y + ")"; }
}