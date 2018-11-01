package project3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Arrays;


public class DialogRentGame extends JDialog implements ActionListener {

	private JTextField titleTxt;
	private JTextField renterTxt;
	private JTextField rentedOnTxt;
	private JTextField DueBackTxt;

	private JButton okButton;
	private JButton cancelButton;

	private boolean closeStatus;

	private DVD unit;

	final JComboBox<String> gameSysType;
	//final JList gameList;
	/*********************************************************
		 Instantiate a Custom Dialog as 'modal' and wait for the
		 user to provide data and click on a button.

     ********************************************************
     * @param parent reference to the JFrame application
     * @param d an instantiated object to be filled with data*/

	public DialogRentGame(JFrame parent, DVD d) {
		// call parent and create a 'modal' dialog
		super(parent, true);

		setTitle("Rent a Game:");
		closeStatus = false;
		setSize(400,200);
		
		unit = d; 
		// prevent user from closing window
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		// instantiate and display text fields


		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(7,2));
		
		textPanel.add(new JLabel("Your Name:"));
		renterTxt = new JTextField("John Doe",30);
		textPanel.add(renterTxt);

		textPanel.add(new JLabel("Title of Game:"));
		titleTxt = new JTextField("Shadow of the Colossus",30);
		textPanel.add(titleTxt);


		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		textPanel.add(new JLabel("Rented on Date: "));
		rentedOnTxt = new JTextField(df.format(date),30);			//
		textPanel.add(rentedOnTxt);
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);  // number of days to add
		date = c.getTime();
		  
		textPanel.add(new JLabel("Due Back: "));
		DueBackTxt = new JTextField(df.format(date),15);
		textPanel.add(DueBackTxt);

		textPanel.add(new JLabel("Game System Type:"));
		//gameList = new JList(PlayerType.values());
		gameSysType = new JComboBox(PlayerType.values());
		textPanel.add(gameSysType);
		//gameSysType.setEditable(true);



		
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

//		String[] gameStringArray = Arrays.toString(PlayerType.values());

		// Create an ActionListener for the JComboBox component.
		//
//		gameSysType.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent event) {
//
//
//			}
//		});

	}


	/**************************************************************
		 Respond to either button clicks
		 @param e the action event that was just fired
	 **************************************************************/
	public void actionPerformed(ActionEvent e) {
		
		JButton button = (JButton) e.getSource();
		//
		// Get the source of the component, which is our combo
		// box.
		//
		//JComboBox gameSysType = (JComboBox) e.getSource();
		PlayerType field = null;
		Object selected = gameSysType.getSelectedItem();
		switch(selected.toString()) {
			case "Xbox360":
				field = PlayerType.Xbox360;
				break;
			case "PS3":
				field = PlayerType.PS3;
				break;
			case "XboxOne":
				field = PlayerType.XboxOne;
				break;
			case "PC":
				field = PlayerType.PC;
				break;
			case "PS4":
				field = PlayerType.PS4;
				break;
			default:
				throw new IllegalArgumentException("Invalid drop-down index");
		}

		// if OK clicked the fill the object
		if (button == okButton) {
			// save the information in the object
			closeStatus = true;
			
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date daterentedOn, dateDue;
			df.setLenient(false);
			try {
				daterentedOn = df.parse(rentedOnTxt.getText());
				dateDue = df.parse(DueBackTxt.getText());
				
				GregorianCalendar rentedOn = new GregorianCalendar();
				GregorianCalendar dueBackOn = new GregorianCalendar();

				rentedOn.setTime(daterentedOn);
				dueBackOn.setTime(dateDue);
				
				unit.setRentedOn(rentedOn);
				unit.setDueBack(dueBackOn);
				unit.setNameOfRenter(renterTxt.getText());
				unit.setTitle(titleTxt.getText());
				unit.setInfo(field);
				
			} catch (Exception e1) {
				System.out.println ("I have unepectly quit, sorry! goodbey");
				System.out.println(unit.getInfo());
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



