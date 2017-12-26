import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.*;

public class GUI {

	public static JFrame frame;
	public static BufferedImage image;
	public static Graphics2D g2d;
	public static Area walls = new Area();

	public static final int IMAGE_WIDTH = 750;
	public static final int IMAGE_HEIGHT = 750;
	public static final int TIME_INTERVAL = 50;

	public static Player manualPlayer = new Player(IMAGE_WIDTH / 2, IMAGE_HEIGHT / 2, 0, 0);

	public static boolean mouseIn = false;

	public static ArrayList<HoneyComb> hcs = new ArrayList<HoneyComb>();

	public static void main(String[] args) throws Exception {
		initFrame();

		HoneyComb board = new HoneyComb(IMAGE_HEIGHT, IMAGE_WIDTH, 10, 10);
		hcs.add(board);

		draw(board);

		while (true) {
			
			Thread.sleep(TIME_INTERVAL);
			updateWalls();
			g2d.setColor(Color.white);
			g2d.fill(walls);
			manualPlayer.updatePos();
			draw(board);
			manualPlayer.draw(g2d);
			if (mouseIn) {
				Point location = MouseInfo.getPointerInfo().getLocation();
				double y = location.getY() - manualPlayer.y;
				double x = location.getX() - manualPlayer.x;
				manualPlayer.bearing = Math.atan2(y, x);
				g2d.setColor(Color.orange);
				g2d.fillOval(toInt(location.getX()) - 3, toInt(location.getY()) - 3, 6, 6);
			}

			Set<Point2D> intersections = new HashSet<Point2D>();

			for (Line2D.Double line : manualPlayer.feelers) {
				Set<Point2D> points = getIntersections(walls, line);
				double minDist = 10000;
				Point2D minPt = null;
				for (Point2D pt : points) {
					double dist = pt.distance(new Point2D.Double(manualPlayer.x, manualPlayer.y));
					if (dist < minDist) {
						minDist = dist;
						minPt = pt;
					}
				}
				// System.out.println(minDist);
				if (minPt != null)
					intersections.add(minPt);
			}
			for (Iterator<Point2D> it = intersections.iterator(); it.hasNext();) {
				final Point2D point = it.next();
				g2d.setColor(Color.black);
				g2d.fillOval(toInt(point.getX()) - 5, toInt(point.getY()) - 5, 10, 10);
			}
		}
	}

	public static int toInt(double d) {
		return ((int) Math.round(d));
	}

	private static void updateWalls() {
		walls.reset();
		for (HoneyComb hc : hcs) {
			Hexagon[][] array = hc.array;
			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < array[0].length; j++) {
					if (array[i][j].isWall) {
						walls.add(new Area(array[i][j].path));
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
				g2d.setColor(hc[i][j].isWall ? Color.red : Color.green);
				g2d.fill(hc[i][j].path);
				g2d.setColor(Color.black);
				g2d.draw(hc[i][j].path);
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

		jl.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
		jl.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
		jl.addMouseListener(new MyML());

		jl.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "save");
		
		Action save = new AbstractAction() {
  			public void actionPerformed(ActionEvent e) {
 				
  			}
  		};
		
		frame.setUndecorated(true);
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
					if (array[i][j].path.contains(new Point(x, y))) {
						array[i][j].isWall ^= true;
						draw(hc);
						break loop;
					}
				}
			}

		}
	}

	public static Set<Point2D> getIntersections(final Area poly, final Line2D.Double line) throws Exception {

		final PathIterator polyIt = poly.getPathIterator(null); // Getting an
																// iterator
																// along the
																// polygon path
		final double[] coords = new double[6]; // Double array with length 6
												// needed by iterator
		final double[] firstCoords = new double[2]; // First point (needed for
													// closing polygon path)
		final double[] lastCoords = new double[2]; // Previously visited point
		final Set<Point2D> intersections = new HashSet<Point2D>(); // List to
																	// hold
																	// found
																	// intersections
		polyIt.currentSegment(firstCoords); // Getting the first coordinate pair
		lastCoords[0] = firstCoords[0]; // Priming the previous coordinate pair
		lastCoords[1] = firstCoords[1];
		polyIt.next();
		while (!polyIt.isDone()) {
			final int type = polyIt.currentSegment(coords);
			switch (type) {
			case PathIterator.SEG_LINETO: {
				final Line2D.Double currentLine = new Line2D.Double(lastCoords[0], lastCoords[1], coords[0], coords[1]);
				if (currentLine.intersectsLine(line))
					intersections.add(getIntersection(currentLine, line));
				lastCoords[0] = coords[0];
				lastCoords[1] = coords[1];
				break;
			}
			case PathIterator.SEG_CLOSE: {
				final Line2D.Double currentLine = new Line2D.Double(coords[0], coords[1], firstCoords[0],
						firstCoords[1]);
				if (currentLine.intersectsLine(line))
					intersections.add(getIntersection(currentLine, line));
				break;
			}
			default: {
				//throw new Exception("Unsupported PathIterator segment type.");
			}
			}
			polyIt.next();
		}
		return intersections;

	}

	public static Point2D getIntersection(final Line2D.Double line1, final Line2D.Double line2) {

		final double x1, y1, x2, y2, x3, y3, x4, y4;
		x1 = line1.x1;
		y1 = line1.y1;
		x2 = line1.x2;
		y2 = line1.y2;
		x3 = line2.x1;
		y3 = line2.y1;
		x4 = line2.x2;
		y4 = line2.y2;
		final double x = ((x2 - x1) * (x3 * y4 - x4 * y3) - (x4 - x3) * (x1 * y2 - x2 * y1))
				/ ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
		final double y = ((y3 - y4) * (x1 * y2 - x2 * y1) - (y1 - y2) * (x3 * y4 - x4 * y3))
				/ ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));

		return new Point2D.Double(x, y);

	}
}
