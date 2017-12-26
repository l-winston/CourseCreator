import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Player {
	public static double velocity = 50;

	public int x, y;
	public double bearing, turnSpeed;
	public ArrayList<Line2D.Double> feelers = new ArrayList<Line2D.Double>();

	public Player(int x, int y, double bearing, double turnSpeed) {
		this.x = x;
		this.y = y;
		this.bearing = bearing;
		this.turnSpeed = turnSpeed;
	}

	public void updatePos() {
		this.bearing += this.turnSpeed / (1000 / GUI.TIME_INTERVAL);
		this.x += this.velocity / (1000 / GUI.TIME_INTERVAL) * Math.cos(bearing);
		this.y += this.velocity / (1000 / GUI.TIME_INTERVAL) * Math.sin(bearing);
		try {
			Thread.sleep(GUI.TIME_INTERVAL);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.blue);
		g2d.fillOval(x - (10 / 2), y - (10 / 2), 10, 10);
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
		
		feelers.add(new Line2D.Double(x, y,x-100*Math.cos(angleW), y-100*Math.sin(angleW)));
		feelers.add(new Line2D.Double(x, y,x-100*Math.cos(angleNW), y-100*Math.sin(angleNW)));
		feelers.add(new Line2D.Double(x, y,x-100*Math.cos(angleN), y-100*Math.sin(angleN)));
		feelers.add(new Line2D.Double(x, y,x-100*Math.cos(angleNE), y-100*Math.sin(angleNE)));
		feelers.add(new Line2D.Double(x, y,x-100*Math.cos(angleE), y-100*Math.sin(angleE)));

		for(Line2D.Double l: feelers){
			g2d.draw(l);
		}
	}

}
