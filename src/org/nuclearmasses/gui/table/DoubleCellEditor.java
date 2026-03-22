package org.nuclearmasses.gui.table;

import javax.swing.*;
import java.text.*;

/**
 * This class creates a proper table cell editor for double values.
 */
public class DoubleCellEditor extends DefaultCellEditor{

	/**
	 * Class constructor.
	 *
	 * @param tf a {@link JTextField}
	 */
	public DoubleCellEditor(final JTextField tf) {
		super(tf);
		tf.setHorizontalAlignment(SwingConstants.RIGHT);
		tf.setBorder(null);

		delegate = new EditorDelegate() {

			public void setValue(Object param) {
				Double value = (Double)param;
				if (value == null) {
					tf.setText("");
				} else {
					double d = value.doubleValue();
					tf.setText(NumberFormat.getInstance().format(d));
				}
			}

			public Object getCellEditorValue() {
				try {
					String field = tf.getText();
					double parsed = NumberFormat.getInstance().parse(field).doubleValue();
					Double d = new Double(parsed);
					return d;
				} catch (Exception e) {
					return null;
				}
			}
		};
	}
}

