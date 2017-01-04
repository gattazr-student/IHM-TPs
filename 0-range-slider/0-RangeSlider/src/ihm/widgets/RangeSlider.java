package ihm.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JComponent;

/**
 * RangeSlider Component
 *
 */
public class RangeSlider extends JComponent implements MouseListener, MouseMotionListener, IRangeSliderListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 3118878313597340586L;

	// Used for collision box
	static final int _rsize = 11;
	static final int _half_rsize = _rsize / 2;

	// Collision box of the first thumb
	Rectangle rect = new Rectangle(0, 0, _rsize, _rsize);
	// Collision box of the second thumb
	Rectangle second_rect = new Rectangle(0, 0, _rsize, _rsize);

	// Data model (this could be passed in constructor)
	RangeSliderModel model = new RangeSliderModel();

	// True if a thumb is being dragged
	boolean dragging = false;
	boolean dragging_second = false;

	/**
	 * Default constructor
	 *
	 * @param width
	 *            : width of the widget
	 * @param min
	 *            : minimum slider value
	 * @param max
	 *            : maximum slider value
	 */
	public RangeSlider(int min, int max) {
		this(min, max, (max - min) / 4 + min, 3 * (max - min) / 4 + min);
	}

	/**
	 * Creates a range slider of size (100, 12)px Use setWidth(...) to set its
	 * width (height shouldn't be changed, but you can still call
	 * setSize(Dimension) if you want)
	 *
	 * @param min
	 *            : minimum slider value
	 * @param max
	 *            : maximum slider value
	 * @param first_value
	 *            : first thumb value
	 * @param second_value
	 *            : second_value thumb value
	 */
	public RangeSlider(int min, int max, int first_value, int second_value) {
		this.model.setRangeProperties(first_value, second_value, min, max);

		this.model.addListener(this); // MVC powa !
		addMouseListener(this);
		addMouseMotionListener(this);

		setWidth(300);
	}

	/**
	 * Allows a thumb to cross the other This could be cared by RangeSliderModel
	 * but let's make it simple
	 */
	private void checkValues() {
		if (this.model.getSecondValue() < this.model.getValue()) {
			int temp = this.model.getSecondValue();
			this.model.setSecondValue(this.model.getValue());
			this.model.setValue(temp);

			boolean b_temp = this.dragging_second;
			this.dragging_second = this.dragging;
			this.dragging = b_temp;
		}
	}

	/**
	 * Draws a thumb according to its rectangle
	 *
	 * @param g
	 *            : graphics
	 * @param rect
	 *            : rectangle of the thumb
	 */
	private void drawThumb(Graphics g, Rectangle rect) {
		Graphics2D g2 = (Graphics2D) g;
		Shape shape = new Ellipse2D.Double(rect.getLocation().x, rect.getLocation().y, rect.getWidth(),
				rect.getHeight());

		g2.setColor(Color.WHITE);
		g2.fill(shape);
		g2.setColor(Color.GRAY);
		g2.draw(shape);
	}

	@Override
	public Dimension getMaximumSize() {
		return getSize();
	}

	@Override
	public Dimension getMinimumSize() {
		return getSize();
	}

	/**
	 * @return : binded RangeSliderModel
	 */
	public RangeSliderModel getModel() {
		return this.model;
	}

	@Override
	public Dimension getPreferredSize() {
		return getSize();
	}

	@Override
	public void maximumChanged(int new_maximum) {
		repaint(); // repaint component if model changed
	}

	@Override
	public void minimumChanged(int new_minimum) {
		repaint(); // repaint component if model changed
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		/* not used */ }

	@Override
	public void mouseDragged(MouseEvent e) {
		/* Move the upper or lower thumb if dragging of one in progess */
		double left_pos = _half_rsize;
		double right_pos = getWidth() - _half_rsize - 2; // -2 for symmetry
		double width = right_pos - left_pos;

		if (this.dragging) {
			int result = (int) ((e.getX() / width) * (this.model.getMaximum() - this.model.getMinimum())
					+ this.model.getMinimum());

			if (this.model.getValue() != result && this.model.getSecondValue() != result) {
				this.model.setValue(result);
				checkValues();
			}
		} else if (this.dragging_second) {
			int result = (int) ((e.getX() / width) * (this.model.getMaximum() - this.model.getMinimum())
					+ this.model.getMinimum());

			if (this.model.getSecondValue() != result && this.model.getValue() != result) {
				this.model.setSecondValue(result);
				checkValues();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		/* Not used */
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		/* not used */
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		/* not used */
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		/* Start dragging (upper or lower ) if clicking near a Thumb */
		if (this.rect.contains(arg0.getPoint())) {
			this.dragging = true;
		} else if (this.second_rect.contains(arg0.getPoint())) {
			this.dragging_second = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		/* Stop dragging */
		this.dragging = false;
		this.dragging_second = false;
	}

	@Override
	public void paint(Graphics g) {
		/* Paint the component */
		Graphics2D g2 = (Graphics2D) g;

		paintTrack(g2);
		drawThumb(g, this.rect); // Thumb 1 (value)
		drawThumb(g, this.second_rect); // Thumb 2 (second_value)

		g2.dispose();
	}

	/**
	 * Draws the slider line and sets thumbs positions
	 *
	 * @param g
	 *            : graphics
	 */
	private void paintTrack(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		double left_pos = _half_rsize;
		double top_pos = getHeight() % 2 == 0 ? getHeight() / 2 - 1 : getHeight() / 2;
		double right_pos = getWidth() - _half_rsize - 2; // - 2 for pretty
															// drawing (tested
															// on Windows)
		double width = right_pos - left_pos;

		double delta = this.model.getMaximum() - this.model.getMinimum();

		double lright_pos = left_pos + width * ((this.model.getValue() - this.model.getMinimum()) / delta);
		Shape left = new Line2D.Double(left_pos, top_pos, lright_pos, top_pos);
		double mright_pos = left_pos + width * ((this.model.getSecondValue() - this.model.getMinimum()) / delta);
		Shape middle = new Line2D.Double(lright_pos, top_pos, mright_pos, top_pos);
		Shape right = new Line2D.Double(mright_pos, top_pos, left_pos + width, top_pos);

		// Set thumbs location
		this.rect.setLocation((int) (lright_pos - _half_rsize), (int) (top_pos - _half_rsize));
		this.second_rect.setLocation((int) (mright_pos - _half_rsize), (int) (top_pos - _half_rsize));

		g2.setColor(Color.GRAY);
		g2.draw(left);
		g2.setColor(Color.BLUE);
		g2.draw(middle);
		g2.setColor(Color.GRAY);
		g2.draw(right);
	}

	@Override
	public void secondValueChanged(int new_second_value) {
		repaint(); // repaint component if model changed
	}

	/**
	 * Set the with of the component
	 *
	 * @param width
	 */
	public void setWidth(int width) {
		setSize(width, _rsize + 1); // + 1 for pretty drawing (tested on
									// Windows)
	}

	@Override
	public void valueChanged(int new_value) {
		repaint(); // repaint component if model changed
	}
}