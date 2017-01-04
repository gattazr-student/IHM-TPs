package ihm.widgets;

/**
 * Interface of Listener waiting for events on a RangeSlider
 *
 * Unlike classic Swing Listener, IRangeSliderListener provides detailed
 * information about the event through 4 functions.
 *
 */
public interface IRangeSliderListener {

	/**
	 * The maximum value of the range has changed
	 * 
	 * @param new_maximum
	 *            the new maximum value
	 */
	public void maximumChanged(int new_maximum);

	/**
	 * The minimum value of the range has changed
	 *
	 * @param new_minimum
	 *            the new minimum value
	 */
	public void minimumChanged(int new_minimum);

	/**
	 * The second value of the range has changed
	 *
	 * @param new_second_value
	 *            the new second value
	 */
	public void secondValueChanged(int new_second_value);

	/**
	 * The value of the range has changed
	 *
	 * @param new_value
	 *            the new value
	 */
	public void valueChanged(int new_value);

}
