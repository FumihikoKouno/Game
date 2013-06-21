public class Point{
	public int x;
	public int y;
	public Point(){
		this(0,0);
	}
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public double difference(Point p){
		return (Math.pow(p.x-this.x,2)+Math.pow(p.y-this.y,2));
	}
	
	public String toString(){
		return "("+ x +"," + y +")";
	}
}