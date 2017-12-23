
public class HoneyComb {
	public Hexagon[][] array;
	public int enclosingHeight, enclosingWidth;
	
	public HoneyComb(int enclosingHeight, int enclosingWidth, int height, int width){
		this.array = new Hexagon[height][width];
		this.enclosingHeight = enclosingHeight;
		this.enclosingWidth = enclosingWidth;
		
		double radius = Math.min(enclosingWidth*2/(3*width + 1), enclosingHeight/(Math.sqrt(3)/2 + (height*Math.sqrt(3))));
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				array[i][j] = new Hexagon(i, j, j%2==0, radius);
			}
		}
		
		
	}
}
