import java.awt.Polygon;

public class Hexagon {
	public double radius;
	
	public static double internalDeg = Math.toRadians(60);
	public static int verticies = 6;
	
	public double distToSide, sideLength;
	
	public Polygon poly;

	public boolean isWall = false;

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
		
		int[] xpoints = new int[Hexagon.verticies];
		int[] ypoints = new int[Hexagon.verticies];

		for (int v = 0; v < Hexagon.verticies; v++) {
			
			xpoints[v] = (int) Math.round(xOffset + this.radius * Math.cos(v * this.internalDeg));
			ypoints[v] = (int) Math.round(yOffset + this.radius * Math.sin(v * this.internalDeg));
			
		}
		
		poly = new Polygon(xpoints, ypoints, Hexagon.verticies);
	}
}
