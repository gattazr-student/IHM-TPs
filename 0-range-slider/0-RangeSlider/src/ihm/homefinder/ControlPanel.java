package ihm.homefinder;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import ihm.widgets.RangeSliderModel;
import ihm.widgets.RangeSliderPanel;

/**
 * Panel Holding the controls the the HomeFinder application
 */
public class ControlPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 5964855321589548453L;

	private RangeSliderPanel pRangePanelRooms;
	private RangeSliderPanel pRangePanelPrices;

	public ControlPanel() {
		this.pRangePanelRooms = new RangeSliderPanel("Rooms", 1, 20, 3, 5);
		this.pRangePanelPrices = new RangeSliderPanel("Price", 1, 10000, 400, 3000);

		setLayout(new BorderLayout());

		JPanel wPanel = new JPanel();
		wPanel.setLayout(new GridLayout(2, 1));
		wPanel.add(this.pRangePanelRooms);
		wPanel.add(this.pRangePanelPrices);

		add(wPanel, BorderLayout.NORTH);
	}

	/**
	 * Return the RangeSliderModel of the RangeSlider that is used to manipulate
	 * the price range
	 *
	 * @return RangeSliderModel
	 */
	public RangeSliderModel getPriceRangeModel() {
		return this.pRangePanelPrices.getRangeSlider().getModel();
	}

	/**
	 * Return the RangeSliderModel of the RangeSlider that is used to manipulate
	 * the number of rooms range
	 *
	 * @return RangeSliderModel
	 */
	public RangeSliderModel getRoomRangeModel() {
		return this.pRangePanelRooms.getRangeSlider().getModel();
	}

}
