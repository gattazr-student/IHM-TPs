package ihm.homefinder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import ihm.widgets.IRangeSliderListener;
import ihm.widgets.RangeSliderModel;

public class MapPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 5386079551695492202L;
	private List<Home> pHomes;

	private ControlPanel pControlPanel;

	private BufferedImage pMapImage;

	public MapPanel(List<Home> aHomes, ControlPanel aControlPanel) {
		this.pHomes = aHomes;
		this.pControlPanel = aControlPanel;
		try {
			this.pMapImage = ImageIO.read(new File("ressources/map.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		RangeSliderModel wPriceModel = aControlPanel.getPriceRangeModel();
		RangeSliderModel wRoomModel = aControlPanel.getRoomRangeModel();

		wPriceModel.addListener(new IRangeSliderListener() {
			@Override
			public void maximumChanged(int new_maximum) {
				repaint();
			}

			@Override
			public void minimumChanged(int new_minimum) {
				repaint();
			}

			@Override
			public void secondValueChanged(int new_second_value) {
				repaint();
			}

			@Override
			public void valueChanged(int new_value) {
				repaint();
			}
		});

		wRoomModel.addListener(new IRangeSliderListener() {
			@Override
			public void maximumChanged(int new_maximum) {
				repaint();
			}

			@Override
			public void minimumChanged(int new_minimum) {
				repaint();
			}

			@Override
			public void secondValueChanged(int new_second_value) {
				repaint();
			}

			@Override
			public void valueChanged(int new_value) {
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		/* Draw the "map" */
		g2d.drawImage(this.pMapImage, 0, 0, null);

		RangeSliderModel wPriceModel = this.pControlPanel.getPriceRangeModel();
		RangeSliderModel wRoomModel = this.pControlPanel.getRoomRangeModel();

		/* Draw dots on the map to symbolize homes */
		g.setColor(Color.RED);
		for (Home wHome : this.pHomes) {
			/* Check wether we have to display this Home or not */
			if (wPriceModel.getValue() < wHome.getPrice() && wHome.getPrice() < wPriceModel.getSecondValue()
					&& wRoomModel.getValue() < wHome.getNbRooms() && wHome.getNbRooms() < wRoomModel.getSecondValue()) {
				Location wLocation = wHome.getLocation();
				Ellipse2D.Double circle = new Ellipse2D.Double(wLocation.getLongitude(), wLocation.getLatitude(), 20,
						20);
				g2d.fill(circle);
			}
		}

	}

}
