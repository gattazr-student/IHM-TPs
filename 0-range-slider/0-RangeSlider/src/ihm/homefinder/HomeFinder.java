package ihm.homefinder;

import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

/**
 * HomeFinder app
 */
public class HomeFinder extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -3691936905705312671L;

	/**
	 * Entry point of HomeFinder
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		/* Creation of Homes */
		LinkedList<Home> wHomes = new LinkedList<>();
		int wX, wY, wRooms, wPrice;
		for (int i = 0; i < 300; i++) {
			/* Creation of 300 random Homes */
			wX = (int) (Math.random() * 10000 % 800) + 1; // 1 < x < 800
			wY = (int) (Math.random() * 10000 % 800) + 1; // 1 < y < 800
			wRooms = (int) (Math.random() * 10000 % 20) + 1; // 1 < rooms < 20
			wPrice = (int) (Math.random() * 10000 % 10000); // 1 < price < 10000
			wHomes.add(new Home(new Location(wX, wY), wRooms, wPrice));
		}

		/* Create the JFrame */
		HomeFinder wHomeFinder = new HomeFinder(wHomes);
		wHomeFinder.setSize(800, 800);
		wHomeFinder.setVisible(true);
		wHomeFinder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * @param aHomes
	 *            Homes that are to be manipulated by HomeFinder
	 */
	public HomeFinder(List<Home> aHomes) {
		/* FrameLayout */
		setLayout(new BorderLayout());

		/* ControlPanel on SOUTH */
		ControlPanel wControlPanel = new ControlPanel();
		add(wControlPanel, BorderLayout.SOUTH);

		/* MapPanel on CENTER */
		MapPanel wMapPanel = new MapPanel(aHomes, wControlPanel);
		add(wMapPanel, BorderLayout.CENTER);
	}

}
