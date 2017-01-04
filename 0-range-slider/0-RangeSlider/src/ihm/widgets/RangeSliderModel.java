package ihm.widgets;

import java.util.LinkedList;

/**
 * Implementation of an IRangeSliderModel
 *
 */
public class RangeSliderModel implements IRangeSliderModel {
	LinkedList<IRangeSliderListener> listener_list = new LinkedList<>();
	int value;
	int second_value;
	int minimum;
	int maximum;

	@Override
	public void addListener(IRangeSliderListener listener) {
		if (!this.listener_list.contains(listener)) {
			this.listener_list.add(listener);
		}
	}

	@Override
	public int getMaximum() {
		return this.maximum;
	}

	@Override
	public int getMinimum() {
		return this.minimum;
	}

	@Override
	public int getSecondValue() {
		return this.second_value;
	}

	@Override
	public int getValue() {
		return this.value;
	}

	@Override
	public void removeListener(IRangeSliderListener listener) {
		if (this.listener_list.contains(listener)) {
			this.listener_list.remove(listener);
		}
	}

	@Override
	public void setMaximum(int maximum) {
		if (maximum != this.maximum) {
			this.maximum = maximum;
			for (int i = 0; i < this.listener_list.size(); i++) {
				this.listener_list.get(i).maximumChanged(maximum);
			}
			// Check if values are out of bounds
			if (this.value > maximum) {
				setValue(maximum);
			}
			if (this.second_value > maximum) {
				setSecondValue(maximum);
			}
		}
	}

	@Override
	public void setMinimum(int minimum) {
		if (minimum != this.minimum) {
			this.minimum = minimum;
			for (int i = 0; i < this.listener_list.size(); i++) {
				this.listener_list.get(i).minimumChanged(minimum);
			}
			// Check if values are out of bounds
			if (this.value < minimum) {
				setValue(minimum);
			}
			if (this.second_value < minimum) {
				setSecondValue(minimum);
			}
		}
	}

	@Override
	public void setRangeProperties(int value, int second_value, int min, int max) {
		// Do this first
		setMinimum(min);
		setMaximum(max);
		// Do this after
		setValue(value);
		setSecondValue(second_value);
	}

	@Override
	public void setSecondValue(int second_value) {
		// Check if value is in the bounds
		if (second_value < this.minimum) {
			second_value = this.minimum;
		} else if (second_value > this.maximum) {
			second_value = this.maximum;
		}

		if (second_value != this.second_value) {
			this.second_value = second_value;
			for (int i = 0; i < this.listener_list.size(); i++) {
				this.listener_list.get(i).secondValueChanged(second_value);
			}
		}
	}

	@Override
	public void setValue(int value) {
		// Check if value is in the bounds
		if (value < this.minimum) {
			value = this.minimum;
		} else if (value > this.maximum) {
			value = this.maximum;
		}

		if (value != this.value) {
			this.value = value;
			for (int i = 0; i < this.listener_list.size(); i++) {
				this.listener_list.get(i).valueChanged(value);
			}
		}
	}
}