import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Player {
	public static double velocity = 50;
	public static double feelerLength = 1000;

	public double x, y;
	public double bearing, turnSpeed;
	public ArrayList<Line2D.Double> feelers = new ArrayList<Line2D.Double>();

	public Player(double x, double y, double bearing, double turnSpeed) {
		this.x = x;
		this.y = y;
		this.bearing = bearing;
		this.turnSpeed = turnSpeed;
	}

	public void updatePos() {
		this.bearing += this.turnSpeed / (1000 / GUI.TIME_INTERVAL);
		double deltaX = this.velocity / (1000 / GUI.TIME_INTERVAL) * Math.cos(bearing);
		double deltaY = this.velocity / (1000 / GUI.TIME_INTERVAL) * Math.sin(bearing);
		this.x += deltaX;
		this.y += deltaY;
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.blue);
		g2d.fillOval(GUI.toInt(x - (10 / 2)), GUI.toInt(y - (10 / 2)), 10, 10);
		drawLines(g2d);
	}

	public void drawLines(Graphics2D g2d) {
		g2d.setColor(Color.cyan);
		double angleW = bearing-Math.PI/2;
		double angleNW = bearing+Math.PI*3/4;
		double angleN = bearing+Math.PI;
		double angleNE = bearing+Math.PI*5/4;
		double angleE = bearing+Math.PI/2;
		
		feelers = new ArrayList<Line2D.Double>();
		
		feelers.add(new Line2D.Double(x, y,x-feelerLength*Math.cos(angleW), y-feelerLength*Math.sin(angleW)));
		feelers.add(new Line2D.Double(x, y,x-feelerLength*Math.cos(angleNW), y-feelerLength*Math.sin(angleNW)));
		feelers.add(new Line2D.Double(x, y,x-feelerLength*Math.cos(angleN), y-feelerLength*Math.sin(angleN)));
		feelers.add(new Line2D.Double(x, y,x-feelerLength*Math.cos(angleNE), y-feelerLength*Math.sin(angleNE)));
		feelers.add(new Line2D.Double(x, y,x-feelerLength*Math.cos(angleE), y-feelerLength*Math.sin(angleE)));

		for(Line2D.Double l: feelers){
			g2d.draw(l);
		}
	}

}
