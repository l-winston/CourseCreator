import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyML implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GUI.clicked(e.getX(), e.getY());
		GUI.mouseIn = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		GUI.mouseIn = true;

		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		GUI.mouseIn = false;
		
	}
}
