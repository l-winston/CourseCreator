import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

public class GUI {

	public static JFrame frame;
	public static BufferedImage image;
	public static Graphics2D g2d;
	public static Area walls = new Area();
	
	public static Player manualPlayer = new Player(0, 0, 0, 0);

	public static final int IMAGE_WIDTH = 500;
	public static final int IMAGE_HEIGHT = 500;
	public static final int TIME_INTERVAL = 50;// time interval between
												// updating positon of Player(s)
												// in millis

	public static ArrayList<HoneyComb> hcs = new ArrayList<HoneyComb>();

	public static void main(String[] args) {
		initFrame();

		HoneyComb honeycomb = new HoneyComb(IMAGE_HEIGHT, IMAGE_WIDTH, 10, 10);
		hcs.add(honeycomb);

		Hexagon[][] hc = honeycomb.array;

		draw(honeycomb);
		
		while (true) {
			//updateWalls();
			manualPlayer.updatePos();
			draw(honeycomb);
			manualPlayer.draw(g2d);
		}
	}

	private static void updateWalls() {
		walls.reset();
		for (HoneyComb hc : hcs) {
			Hexagon[][] array = hc.array;
			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < array[0].length; j++) {
					if (array[i][j].isWall) {
						walls.add(new Area(array[i][j].poly));
					}
				}
			}
		}
	}

	private static void draw(HoneyComb honeycomb) {
		g2d.setColor(Color.gray);
		g2d.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

		Hexagon[][] hc = honeycomb.array;

		for (int i = 0; i < hc.length; i++) {
			for (int j = 0; j < hc[0].length; j++) {
				g2d.setColor(hc[i][j].isWall ? Color.green : Color.red);
				g2d.fill(hc[i][j].poly);
				g2d.setColor(Color.black);
				g2d.draw(hc[i][j].poly);
			}
		}
		frame.repaint();
	}

	private static void initFrame() {
		frame = new JFrame("frame");
		image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2d = image.createGraphics();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel jl = new JLabel(new ImageIcon(image));
		frame.getContentPane().add(jl);

		Action left = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				manualPlayer.bearing -= Math.toRadians(15);
				System.out.println(manualPlayer.turnSpeed);
			}
		};

		Action right = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				manualPlayer.bearing += Math.toRadians(15);
				System.out.println(manualPlayer.turnSpeed);
			}
		};
		jl.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
		jl.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
		jl.getActionMap().put("left", left);
		jl.getActionMap().put("right", right);
		jl.addMouseListener(new MyML());
		
		frame.pack();
		frame.setVisible(true);
		jl.setFocusable(true);

		g2d.setColor(Color.gray);
		g2d.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
	}

	public static void clicked(int x, int y) {
		loop: for (HoneyComb hc : hcs) {
			Hexagon[][] array = hc.array;
			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < array[0].length; j++) {
					if (array[i][j].poly.contains(new Point(x, y))) {
						array[i][j].isWall ^= true;
						draw(hc);
						break loop;
					}
				}
			}

		}
	}

}
