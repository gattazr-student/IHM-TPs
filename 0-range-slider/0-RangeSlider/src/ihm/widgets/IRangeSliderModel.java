package ihm.widgets;

/**
 *
 */
public interface IRangeSliderModel {
	/**
	 * Add the provided IRangeSliderListener to the list of Listeners waiting
	 * for events on the Model
	 *
	 * @param listener
	 *            listener to add
	 */
	void addListener(IRangeSliderListener listener);

	/**
	 * @return maximum value the range can take
	 */
	int getMaximum();

	/**
	 * @return minimum value of the range
	 */
	int getMinimum();

	/**
	 * @return second value (upper value) of the range
	 */
	int getSecondValue();

	/**
	 * @return value (lower value) of the range
	 */
	int getValue();

	/**
	 * Remove the provided IRangeSliderListener from the listeners
	 *
	 * @see #addListener
	 *
	 * @param listener
	 *            listener to remove
	 */
	void removeListener(IRangeSliderListener listener);

	/**
	 * @param max
	 *            maximum value the range can take
	 */
	void setMaximum(int max);

	/**
	 * @param min
	 *            minimum value of the range
	 */
	void setMinimum(int min);

	/**
	 *
	 * @param value
	 * @param second_value
	 * @param min
	 * @param max
	 */
	void setRangeProperties(int value, int second_value, int min, int max);

	/**
	 *
	 * @param second_value
	 *            second value (upper value) of the range
	 */
	void setSecondValue(int second_value);

	/**
	 *
	 * @param value
	 *            value (lower value) of the range
	 */
	void setValue(int value);
}