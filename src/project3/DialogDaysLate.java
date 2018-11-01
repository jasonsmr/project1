package project3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DialogDaysLate extends JDialog implements ActionListener {

	private JTextField DueBackTxt;

	private JButton okButton;
	private JButton cancelButton;
	private boolean closeStatus;

	private GregorianCalendar dat;


	static final int OK = 0;
	static final int CANCEL = 1;

	/*********************************************************
	 Instantiate a Custom Dialog as 'modal' and wait for the
	 user to provide data and click on a button.

	 @param parent reference to the JFrame application
	 *********************************************************/

	public DialogDaysLate(JFrame parent, GregorianCalendar dat) {
		// call parent and create a 'modal' dialog
		super(parent, true);

		setTitle("Rent a Auto:");
		closeStatus = false;
		setSize(400,200);

		this.dat = dat;
		// prevent user from closing window
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// instantiate and display text fields

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(6,2));

		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);  // number of days to add
		date = c.getTime();



		textPanel.add(new JLabel("Date: "));
		DueBackTxt = new JTextField(df.format(date),15);
		textPanel.add(DueBackTxt);

		getContentPane().add(textPanel, BorderLayout.CENTER);

		// Instantiate and display two buttons
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		setSize(300,300);
		setVisible (true);
	}

	/**************************************************************
	 Respond to either button clicks
	 @param e the action event that was just fired
	 **************************************************************/
	public void actionPerformed(ActionEvent e) {

		JButton button = (JButton) e.getSource();

		// if OK clicked the fill the object
		if (button == okButton) {
			// save the information in the object
			closeStatus = true;

			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date  dateDue;
			try {
				dateDue = df.parse(DueBackTxt.getText());

				GregorianCalendar dueBackOn = new GregorianCalendar();
				dat.setTime(dateDue);

			} catch (ParseException e1) {
				System.out.println ("I have unepectly quit, sorry! goodbye");
			}
		}


		// make the dialog disappear
		dispose();
	}

	/**************************************************************
	 Return a String to let the caller know which button
	 was clicked

	 @return an int representing the option OK or CANCEL
	 **************************************************************/
	public boolean closeOK(){
		return closeStatus;
	}

}