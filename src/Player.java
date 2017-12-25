import java.awt.Color;
import java.awt.Graphics2D;

public class Player {
	public static double velocity = 50;
	
	public int x, y;
	public double bearing, turnSpeed;
	public Player(int x, int y, double bearing, double turnSpeed){
		this.x = x;
		this.y = y;
		this.bearing = bearing;
		this.turnSpeed = turnSpeed;
	}
	
	public void updatePos(){
		 this.bearing += this.turnSpeed/(1000/GUI.TIME_INTERVAL);
		 this.x += this.velocity/(1000/GUI.TIME_INTERVAL) * Math.cos(bearing);
		 this.y += this.velocity/(1000/GUI.TIME_INTERVAL) * Math.sin(bearing);
		 System.out.println(Math.pow(this.velocity/(1000/GUI.TIME_INTERVAL) * Math.cos(bearing), 2) + Math.pow(this.velocity/(1000/GUI.TIME_INTERVAL) * Math.sin(bearing), 2));
		 //System.out.println(x + " " + y + " " + bearing);
		 try {
			Thread.sleep(GUI.TIME_INTERVAL);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2d){
		g2d.setColor(Color.blue);
		g2d.fillOval(x-(10/2), y-(10/2), 10, 10);
	}
	
}
