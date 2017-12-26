import java.awt.Polygon;
import java.awt.geom.Path2D;

public class Hexagon {
	public double radius;
	
	public static double internalDeg = Math.toRadians(60);
	public static int verticies = 6;
	
	public double distToSide, sideLength;
	
	public Path2D path = new Path2D.Double();

	public boolean isWall = true;

	public boolean offset;
	public int i;
	public int j;

	public Hexagon(int i, int j, boolean offset, double radius) {
		this.i = i;
		this.j = j;
		
		this.offset = offset;
		this.radius = radius;
		this.distToSide = radius * Math.sin(internalDeg);
		this.sideLength = 2 * radius * Math.cos(internalDeg);

		int xOffset = (int) (this.radius + this.j * (this.sideLength / 2 + this.radius));
		int yOffset = (int) (this.radius
				+ (this.offset ? this.i * 2 * this.distToSide : this.i * 2 * this.distToSide + this.distToSide));


		for (int v = 0; v < Hexagon.verticies; v++) {
			double x = xOffset + (this.radius+0.5) * Math.cos(v * this.internalDeg);
			double y = yOffset + (this.radius+0.5) * Math.sin(v * this.internalDeg);
			if(v == 0){
				path.moveTo(x, y);
			}else{
				path.lineTo(x, y);
			}
		}
		path.closePath();
	}
}
