package ihm.widgets;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Simple JPanel containing a RangeSlider and JLabels to visualize the values
 * handled with the RangeSlider
 *
 */
public class RangeSliderPanel extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 8996180474263013592L;

	private RangeSlider pRangeSlider;
	private JLabel pMinLabel;
	private JLabel pMaxLabel;
	private JLabel pRangeValuesLabel;

	public RangeSliderPanel(String aLabel, int aMin, int aMax, int aValue, int aSecondValue) {

		setLayout(new BorderLayout());
		/* Create a RangeSlider and retrieve its model */
		this.pRangeSlider = new RangeSlider(aMin, aMax, aValue, aSecondValue);

		/* Creates Labels and update their value */
		this.pMinLabel = new JLabel();
		this.pMaxLabel = new JLabel();
		this.pRangeValuesLabel = new JLabel();
		updateLabels();

		/* Creates values panel */
		JPanel wValuesPanel = new JPanel();
		wValuesPanel.setLayout(new FlowLayout());
		wValuesPanel.add(new JLabel(aLabel + " : "));
		wValuesPanel.add(this.pRangeValuesLabel);

		/*
		 * Set components in the RangeSliderPanel according to the Orientation
		 */
		add(wValuesPanel, BorderLayout.NORTH);
		add(this.pMinLabel, BorderLayout.WEST);
		add(this.pMaxLabel, BorderLayout.EAST);
		add(this.pRangeSlider, BorderLayout.CENTER);

		/*
		 * Update the label values when there is an interaction with the
		 * RangeSlider
		 */
		this.pRangeSlider.getModel().addListener(new IRangeSliderListener() {

			@Override
			public void maximumChanged(int new_maximum) {
				RangeSliderPanel.this.updateLabels();
			}

			@Override
			public void minimumChanged(int new_minimum) {
				RangeSliderPanel.this.updateLabels();
			}

			@Override
			public void secondValueChanged(int new_second_value) {
				RangeSliderPanel.this.updateLabels();
			}

			@Override
			public void valueChanged(int new_value) {
				RangeSliderPanel.this.updateLabels();
			}
		});

	}

	/**
	 * Return the RangeSlider container
	 *
	 * @return the RangeSlider container
	 */
	public RangeSlider getRangeSlider() {
		return this.pRangeSlider;
	}

	/**
	 * Update the JLabel container in the Panel. The Labels in that panel are
	 * displaying the min, max and range values
	 */
	private void updateLabels() {
		RangeSliderModel wModel = this.pRangeSlider.getModel();
		this.pMinLabel.setText("" + wModel.getMinimum());
		this.pMaxLabel.setText("" + wModel.getMaximum());
		this.pRangeValuesLabel.setText(wModel.getValue() + " - " + wModel.getSecondValue());

	}

}
