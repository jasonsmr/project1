package project3;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.swing.table.*;

/**********************************************************************
 *
 * Maintains the GUIRentalStore for the red box rental store
 * @author Jason Rupright
 * @version 10/26/2018
 *
 **********************************************************************/
public class GUIRentalStore extends JFrame implements ActionListener{


    /** Holds menu bar */
    private JMenuBar menus;

    /** menus in the menu bar */
    private JMenu fileMenu;
    private JMenu actionMenu;

    /** menu items in each of the menus */
    private JMenuItem openSerItem;
    private JMenuItem exitItem;
    private JMenuItem saveSerItem;
    private JMenuItem openTextItem;
    private JMenuItem saveTextItem;
    private JMenuItem sortDate;
    private JMenuItem rentBluRay;
    private JMenuItem rentDVD;
    private JMenuItem rentGame;
    private JMenuItem returnItem;
    /** The search menu item */
    private JMenuItem searchItem;
    private JMenuItem dayLate;
    private JCheckBoxMenuItem filterTog;
    private JFileChooser fc;


    /** Holds the list engine */
    private TableDVDs tableDVDs;


    /** Holds jTable */
    private JTable jTable;

    private DVD dvd;

    /** Scroll pane */
    //private JScrollPane scrollList;
    //stores amount due by customer
//	private Double amount;
//	private String due;


    /******************************************************************
     *
     * A constructor that starts a new GUI1024 for the rental store
     *
     ******************************************************************/
    public GUIRentalStore(){
        //adding menu bar and menu items
        menus = new JMenuBar();
        fileMenu = new JMenu("File");
        actionMenu = new JMenu("Action");
        openSerItem = new JMenuItem("Open File");
        exitItem = new JMenuItem("Exit");
        saveSerItem = new JMenuItem("Save File");
        sortDate = new JMenuItem("Sort");
        openTextItem = new JMenuItem("Open Text");
        saveTextItem = new JMenuItem("Save Text");
        rentBluRay = new JMenuItem("Rent BluRay");
        rentDVD = new JMenuItem("Rent DVD");
        rentGame = new JMenuItem("Rent Game");
        returnItem = new JMenuItem("Return");
        searchItem = new JMenuItem("Search");
        dayLate = new JMenuItem("Days Late");
        filterTog = new JCheckBoxMenuItem("Filter Toggle");

        //adding items to bar
        fileMenu.add(openSerItem);
        fileMenu.add(saveSerItem);
        fileMenu.addSeparator();
        fileMenu.add(openTextItem);
        fileMenu.add(saveTextItem);
        sortDate.addActionListener(this);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        actionMenu.add(rentBluRay);
        actionMenu.add(rentDVD);
        actionMenu.add(rentGame);
        actionMenu.add(searchItem);
        actionMenu.addSeparator();
        actionMenu.add(returnItem);
        actionMenu.addSeparator();
        actionMenu.add(dayLate);
        actionMenu.add(filterTog);

        menus.add(fileMenu);
        menus.add(actionMenu);

        //adding actionListener
        openSerItem.addActionListener(this);
        saveSerItem.addActionListener(this);
        openTextItem.addActionListener(this);
        saveTextItem.addActionListener(this);
        fileMenu.add(sortDate);
        exitItem.addActionListener(this);
        rentBluRay.addActionListener(this);
        rentDVD.addActionListener(this);
        rentGame.addActionListener(this);
        returnItem.addActionListener(this);
        searchItem.addActionListener(this);
        dayLate.addActionListener(this);
        fileMenu.addActionListener(this);
        filterTog.addActionListener(this);

        filterTog.setState(false);
        setJMenuBar(menus);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableDVDs = new TableDVDs();
        jTable = new JTable(tableDVDs);
        actionMenu.add(dayLate);


        add(new JScrollPane(jTable), BorderLayout.CENTER);

        pack();
        setVisible(true);
        setSize(1000,400);

        //sets amount due to a double with two decimal places
        //due = toStringL(amount);
        //System.out.print("As string" +due+"\n");
        //JTableHeader header = jTable.getTableHeader();
        //header.setUpdateTableInRealTime(true);
        //header.addMouseListener(tableModel.new ColumnListener(jTable));
        //header.setReorderingAllowed(true);
    }

    /**method toStringL takes double and converts to a monitored format (x.xx) with two decimal places*/
    public String toStringL(Double amount) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        formatter.setGroupingUsed(false);
        formatter.setMinimumFractionDigits(2);
        String DoubleD = formatter.format(amount);
        return DoubleD;
    }

//	public double calcAmount(Long date){
//		if(date<0)
//			return 3.00;
//
//		return amount;
//	}

    /******************************************************************
     *
     * This method handles event-handling code for the GUI1024
     *
     * @param e - Holds the action event parameter
     ******************************************************************/
    public void actionPerformed(ActionEvent e) {

        Object comp = e.getSource();

        if (e.getSource() == sortDate) {
            tableDVDs.sortDateDue();
        }

        // save elements
        if (e.getSource() == saveSerItem ||
                e.getSource() == saveTextItem) {
            try {
                fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal = fc.showSaveDialog(null);
                if (returnVal != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(null,
                            "No File Chosen!");
                } else {
                    if (e.getSource() == saveTextItem) {
                        String file = fc.getSelectedFile().getAbsolutePath();
                        tableDVDs.marshalingUsefullReadableFile(file);
                    } else {
                        String file = fc.getSelectedFile().getAbsolutePath();
                        tableDVDs.saveDatabase(file);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }

        // load elements
        if (e.getSource() == openSerItem ||
                e.getSource() == openTextItem) {
            tableDVDs.clear();
            try {
                fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal = fc.showOpenDialog(null);
                if (returnVal != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(null,
                            "No File Chosen!");
                } else {
                    if (e.getSource() == openSerItem) {
                        String file = fc.getSelectedFile().getAbsolutePath();
                        tableDVDs.loadDatabase(file);
                    } else {
                        String file = fc.getSelectedFile().getAbsolutePath();
                        tableDVDs.marshalingUsefullWritableFile(file);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }



        //MenuBar options
        if(e.getSource() == exitItem){
            System.exit(1);
        }
        if(e.getSource() == rentDVD){
            DVD dvd = new DVD();
            DialogRentDVD dialog = new DialogRentDVD(this, dvd);
            if(dialog.closeOK()){
                tableDVDs.add(dvd);
            }
        }
        if(e.getSource() == rentBluRay){
            DVD bluRay = new BluRay();
            DialogRentBluRay dialog = new DialogRentBluRay(this, bluRay);
            if(dialog.closeOK()){
                tableDVDs.add(bluRay);
            }
        }

        if(e.getSource() == rentGame){
            DVD game = new Game();
            DialogRentGame dialog = new DialogRentGame(this, game);
            if(dialog.closeOK()){
                tableDVDs.add(game);
            }
        }


        if(e.getSource() == returnItem) {

            //Get the index of the selected item
            int index = jTable.getSelectedRow();
            GregorianCalendar dat = new GregorianCalendar();
            DialogDaysLate dialog = new DialogDaysLate(this, dat);
            //Check for out of bounds indexes
            if(index < 0) {
                //Alert the user of an out of bounds index
                JOptionPane.showMessageDialog(null,
                        "Please select an item from the list");
            }
            else{

                //Initialize a calendar for todays date to compare
                //to the due date
                Calendar today = new GregorianCalendar();

                Calendar dueDate = new GregorianCalendar();



                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

                try {
                    today.setTime(dateFormat.parse(DateFormat.
                            getDateInstance(DateFormat.SHORT).
                            format(today.getTime())));
                } catch (ParseException en) {

                }

                /*****************************************************************
                 * Setting dates for rental by grabbing from dvd table list and finding the cost by passing an
                 *  amorphic function getCost then formatting properly as a string*/
                GregorianCalendar rentDate = (GregorianCalendar)tableDVDs.getABSValueAt(index, 2);
                long diffInMillies = (dat.getTime().getTime() - rentDate.getTime().getTime());
                double daysLate = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                double cost = tableDVDs.getDVD(index).setCost(dat);

                if(daysLate <0){
                        JOptionPane.showConfirmDialog(null,"Err: Bad Return Date: \nCannot occur before rental date!", JOptionPane.MESSAGE_PROPERTY, JOptionPane.PLAIN_MESSAGE);

                    }
                else {
                    dueDate.setTime(tableDVDs.getDVD(index).getDueBack().getTime());

                    String uname = tableDVDs.getDVD(index).getNameOfRenter();

                    String title = tableDVDs.getDVD(index).getTitle();

                    DecimalFormat df = new DecimalFormat("#0.00");

                    String costString = "Rental cost: $" +
                            df.format(cost) + "\n";

                    int resp1 = JOptionPane.showConfirmDialog(null, "Thank you, \"" + uname + "\"\nFor returning: \"" + title + "\"\nPlease Swipe Credit Card. \nAmount Due: " + costString, "Make Payment Please: ",
                            JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (resp1 == JOptionPane.OK_OPTION) {
                        tableDVDs.remove(index);
                    }
                }

            }
        }

        if(e.getSource() == searchItem) {
        }

//        if(e.getSource() == dayLate) {
//            DialogRentDVD ld = new DialogDaysLate(tableDVDs.get());
//        }
    }







    /*****************************************************************
     *
     * Main method that runs the rental store GUI1024
     *
     *****************************************************************/
    public static void main(String[] args) {
        new GUIRentalStore();
    }
}
