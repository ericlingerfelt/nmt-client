package org.nuclearmasses.gui.dialogs;

import info.clearthought.layout.*;
import java.awt.*;
import javax.swing.*;
import org.nuclearmasses.gui.format.WordWrapLabel;

/**
 * This class is a utility dialog which is launched on a new thread in order for 
 * user interaction to be blocked while time consuming processes are completed.
 */
public class DelayDialog extends JDialog{

	/**
	 * Class constructor.
	 *
	 * @param owner 			the {@link Frame} that owns this dialog
	 * @param string the string
	 * @param titleString 	the String to be displayed in this dialog's title bar
	 */
	public DelayDialog(Frame owner, String string, String titleString){
	
		super(owner, titleString, true);
		setSize(320, 175);
		setLocation((int)(owner.getLocation().getX() + owner.getSize().width/2.0 - this.getSize().width/2.0)
				, (int)(owner.getLocation().getY() + owner.getSize().height/2.0 - this.getSize().height/2.0));

		double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, 5, TableLayoutConstants.PREFERRED, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		setLocationRelativeTo(owner);
		
		WordWrapLabel textArea = new WordWrapLabel();
		textArea.setText(string);
		
		JScrollPane sp = new JScrollPane(textArea
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		sp.setBorder(null);

		c.add(sp, "1, 1, f, f");
	}
	
	/**
	 * Opens this class on a new thread.
	 */
	public void openDelayDialog(){
		OpenDialogTask task = new OpenDialogTask(this);
		task.execute();
	}
	
	/**
	 * Closes this class.
	 */
	public void closeDelayDialog(){
		setVisible(false);
		dispose();
	}
}

class OpenDialogTask extends org.nuclearmasses.gui.util.SwingWorker<Void, Void>{
	
	private JDialog dialog;
	
	public OpenDialogTask(JDialog dialog){
		this.dialog = dialog;
	}
	
	protected Void doInBackground(){
		dialog.setVisible(true);
		return null;
	}
}



