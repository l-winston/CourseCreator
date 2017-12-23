
public class Player {
	public static double velocity = 1;
	
	public int x, y;
	public double bearing, turnSpeed;
	public Player(int x, int y, double bearing){
		this.x = x;
		this.y = y;
		this.bearing = bearing;
	}
	
	public void updatePos(){
		 this.bearing += this.turnSpeed/(1000/GUI.TIME_INTERVAL);
		 this.x += this.velocity/(1000/GUI.TIME_INTERVAL) * Math.cos(bearing);
		 this.y += this.velocity/(1000/GUI.TIME_INTERVAL) * Math.sin(bearing);
	}
	
	
}
