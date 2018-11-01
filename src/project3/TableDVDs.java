package project3;
/**********************************************************************
 TableDVDs.java implements the abstract list model for the GUI's JList.
 This includes printing to the list, adding, removing, and sorting the
 list.
 @author Jason Rupright
 @version 10/26/2018
 *********************************************************************/

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

/**********************************************************************
 * Constructs a ArrayList of DVDs to sort and pass arguments to and
 * from the DVD Class types
 **********************************************************************/
@XmlRootElement(name = "listDVDs")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ArrayList.class, DVD.class, Game.class, BluRay.class})
//@XmlSeeAlso(DVD.class)
public class TableDVDs extends AbstractTableModel implements Serializable{
    //@XmlAttribute
    private DVD dvd;

    private long version;

    @XmlAttribute
    private static final int COLUMN_NAME        = 0;

    @XmlAttribute
    private static final int COLUMN_TITLE       = 1;

    @XmlAttribute
    private static final int COLUMN_RDATE       = 2;

    @XmlAttribute
    private static final int COLUMN_RTDATE      = 3;

    @XmlAttribute
    private static final int COLUMN_INFO        = 4;
    /******************************************************************
     *the string list for the column names.
     ******************************************************************/
    //@XmlAttribute
    private String[] columnNames = {
            "Customer Name",
            "Title",
            "Rental Date",
            "Due Date",
            "Information"};

    /******************************************************************
     *Constructs a blank ListEngine and initializes the dvd list.
     *used to sort with column name
     * @return String
     * @args int columnIndex
     ******************************************************************/
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    //@XmlAttribute
    protected ArrayList<DVD> listDVDs;
    /******************************************************************
     *Constructs a blank ListEngine and initializes the dvd list.
     ******************************************************************/
    public TableDVDs() {
        super();
        listDVDs = new ArrayList<DVD>();
        createList();
    }

    /******************************************************************
     Returns the element at the specified index. Used by the
     fireInterval...() methods.
     @param index of element
     @return element at specified index.
     ******************************************************************/
    public Object getElementAt(int index) {

        //Given a valid index...
        if (index >= 0 && index < listDVDs.size()) {

            //Parse the purchase date into a string
            String dateP = DateFormat.getDateInstance(DateFormat.SHORT).format(
                    listDVDs.get(index).getRentedOn().getTime());

            //Parse the due date into a string
            String dateD = DateFormat.getDateInstance(DateFormat.SHORT).format(
                    listDVDs.get(index).getDueBack().getTime());

            //Creates the output string for a DVD
            String s = "Name: " + listDVDs.get(index).getNameOfRenter()
                    + " Title: " + listDVDs.get(index).getTitle()
                    + " Date Purchased: " + dateP + " Date Due: " + dateD;

            //Parses the console type for game and output the string
            if (listDVDs.get(index).isGame()) {
                Game game = (Game) listDVDs.get(index);
                String consoleString = "";

                if (game.getPlayer() == PlayerType.Xbox360)
                    consoleString = "Xbox 360";

                if (game.getPlayer() == PlayerType.XboxOne)
                    consoleString = "Xbox One";

                if (game.getPlayer() == PlayerType.PS3)
                    consoleString = "PS3";

                if (game.getPlayer() == PlayerType.PS4)
                    consoleString = "PS2";

                if (game.getPlayer() == PlayerType.PC)
                    consoleString = "GameCube";

                return s + " Console: " + consoleString;
            }

            //Just return the dvd string
            else
                return s;
        }

        //Dont return anything if the index is out of bounds
        else {
            return null;
        }
    }
    /******************************************************************
     Returns the unit at the specified colum length.
     @return int
     ******************************************************************/
    public int getColumnCount() {
        return columnNames.length;
    }

    /******************************************************************
     Returns the unit at the specified row length.
     @return int
     ******************************************************************/
    @Override
    public int getRowCount() {
        return listDVDs.size();     // returns number of items in the arraylist
    }

    /******************************************************************
     Returns the unit in string format given a colum type and a index row
     @args int row, int col
     @return String
     ******************************************************************/
    @Override
    public String getValueAt(int row, int col) {
        GregorianCalendar dat = new GregorianCalendar();

        switch (col) {

            case COLUMN_NAME:
                Object nameList =  (listDVDs.get(row).getNameOfRenter());
                return nameList.toString();
            case COLUMN_TITLE:
                Object titleList =  (listDVDs.get(row).getTitle());
                return titleList.toString();
            case COLUMN_RDATE:
                //Object rentedOn = (listDVDs.get(row).getRentedOn());
                Object rentedOn =  (DateFormat.getDateInstance(DateFormat.SHORT)
                        .format(listDVDs.get(row).getRentedOn().getTime()));
                return rentedOn.toString();
            case COLUMN_RTDATE:
                //Object returnOn =  listDVDs.get(row).getDueBack();
                Object returnOn =  (DateFormat.getDateInstance(DateFormat.SHORT)
                        .format(listDVDs.get(row).getDueBack().getTime()));
                return returnOn.toString();
            case COLUMN_INFO:
                Object infoList =  (listDVDs.get(row).getInfo());
                return infoList.toString();
            default:
                return null;
        }

    }
    /******************************************************************
     Returns the unit in Object raw format given a colum type and a index row
     @args int row, int col
     @return Object
     ******************************************************************/
    public Object getABSValueAt(int row, int col) {
        Calendar cal = new GregorianCalendar();
        switch (col) {

            case COLUMN_NAME:
                Object nameList =  (listDVDs.get(row).getNameOfRenter());
                return nameList;
            case COLUMN_TITLE:
                Object titleList =  (listDVDs.get(row).getTitle());
                return titleList;
            case COLUMN_RDATE:
                Object rentedOn = (listDVDs.get(row).getRentedOn());
                return rentedOn;
            case COLUMN_RTDATE:
                Object returnOn =  listDVDs.get(row).getDueBack();
                return returnOn;
            case COLUMN_INFO:
                Object infoList =  (listDVDs.get(row).getInfo());
                return infoList;
            default:
                return null;
        }

    }


    /******************************************************************
     Adds a new unit to the list
     @param dvd
     @return completion status
     ******************************************************************/
    public boolean add(DVD dvd) {
        listDVDs.add(dvd);
        fireTableRowsInserted(0, listDVDs.size()-1);
        return true;
    }


    /******************************************************************
     Removes the unit at the specified index
     @param index of unit
     @return completion status
     ******************************************************************/
    public boolean remove(int index) {
        listDVDs.remove(index);
        fireTableRowsDeleted(0, listDVDs.size()-1);
        return true;
    }


    /******************************************************************
     Updates the list
     @param dvd
     @return completion status
     ******************************************************************/
    public boolean update(DVD dvd) {
        fireTableRowsUpdated(0, listDVDs.size()-1);
        return true;
    }


    public void saveDatabase(String file) {
        ObjectOutputStream outStream = null;
        try {
            outStream = new ObjectOutputStream(new FileOutputStream(file));
            for (DVD dvd : listDVDs) {
                outStream.writeObject(dvd);
            }

        } catch (IOException ioException) {
            System.err.println("Error opening file.");
            ioException.printStackTrace();
        } catch (NoSuchElementException noSuchElementException) {
            System.err.println("Invalid input.");
        } finally {
            try {
                if (outStream != null)
                    outStream.close();
            } catch (IOException ioException) {
                System.err.println("Error closing file.");
                ioException.printStackTrace();
            }
        }
    }

    /******************************************************************
     * Loads (deserializes) the Account objects from the specified file.
     * @param file autoName of the file to load from.
     ******************************************************************/
    public void loadDatabase(String file) {
        ObjectInputStream inputStream = null;
        this.version = 0L;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(file));
            while (true) {
                DVD dvd = (DVD) inputStream.readObject();
                add(dvd);
                this.version = inputStream.readByte()& 0xFF;
            }
        } catch (EOFException eofException) {
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Object creation failed.");
        } catch (  StreamCorruptedException e) {
            JOptionPane.showMessageDialog(null,"Unsupported Binary DB version: " + version);
            if (version <= 0L){
                JOptionPane.showMessageDialog(null,"Try opening valid file type.");
            }
            e.printStackTrace();
        } catch (IOException ioException) {
            if(version != DVD.getSerialVersionUID()) {
                JOptionPane.showMessageDialog(null, "Windows NT Error: Windows error 3: No such Administrator privilege.\nWindows error 4: Administrator doesnt have high enough Administrator privilege to create additional Administrator privileges.\n\nAbort, Retry, Fail?");
                int resp1 = JOptionPane.showConfirmDialog(null,"Try logging on as Administrator.", JOptionPane.MESSAGE_PROPERTY, JOptionPane.PLAIN_MESSAGE);

                if (resp1 == JOptionPane.OK_OPTION) {
                }
            }
            ioException.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException ioException) {
                System.err.println("Error closing file.");
                ioException.printStackTrace();
            }
        }
    }


    /******************************************************************
     Saves the text object to the file at the given filepath
     @param filename
     @return completion status
     ******************************************************************/
    public void saveAsText(String filename) throws IOException{
        try {
            //BufferedWriter bfw = new BufferedWriter(new FileWriter(filename));
            FileOutputStream f = new FileOutputStream(new File(filename));
            ObjectOutputStream o = new ObjectOutputStream(f);

            for(int i = 0; i < listDVDs.size(); i++) {
                DVD dvd = getDVD(i);
                //if(dvd.isGame()){

                //}
                o.writeObject(dvd.getNameOfRenter());
                o.writeObject(dvd.getRentedOn().toString());
                o.writeObject(dvd.getDueBack().toString());
                o.writeObject(dvd.getInfo());
            }
            o.close();
            f.close();
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"Error in saving text");

        }
    }

    /******************************************************************
     Loads the text object from the file at the given filepath
     //@param filename
     @return completion status
     ******************************************************************/
    public void loadFromText(String filename) {
        try {
//            FileInputStream fin = new FileInputStream(filename);
//            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
//            String strLine;
//            int col;
            FileInputStream fi = new FileInputStream(new File(filename));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            DVD dvd = (DVD) oi.readObject();
            while(dvd != null) {
                //listDVDs = (ArrayList<DVD>);
                add(dvd);
            }
            fireTableDataChanged();
            oi.close();
            fi.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error in loading text");
        }

    }
    public void marshalingUsefullReadableFile(String filename) throws JAXBException {

//        for(int i = 0; i< listDVDs.size(); i++) {
//            DVD dvd = getDVD(i);
//            dvd.getNameOfRenter();
//        }

        TableDVDs list = new TableDVDs();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        GregorianCalendar temp1 = new GregorianCalendar();
        GregorianCalendar temp2 = new GregorianCalendar();
        try {
            Date d1 = df.parse("3/20/2018");
            temp1.setTime(d1);
            Date d2 = df.parse("12/20/2018");
            temp2.setTime(d2);
            DVD dvd1 = new Game(temp1, temp2, "Oxygen Not Included", "Jay Bird", PlayerType.PC);


            list.clear();
            list.add(dvd1);
            for (DVD dvd: listDVDs) {
                list.add(dvd);
            }

            JAXBContext context = JAXBContext.newInstance(TableDVDs.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(list, new FileWriter(filename));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error in saving text");
        }
        catch (ParseException e) {
            throw new RuntimeException("Error in testing, creation of list");
        }
    }

    public void marshalingUsefullWritableFile(String filename) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(TableDVDs.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();



        //We had written this file in marshalling example

        TableDVDs table = (TableDVDs) jaxbUnmarshaller.unmarshal( new File(filename) );


        System.out.println(table.listDVDs);
        this.clear();
        for (DVD dvd: table.listDVDs) {
            this.add(dvd);
        }
        //listDVDs = list.listDVDs;
        System.out.println(listDVDs);
//        while()
//        for(DVD dvd : table.getDVD(index))
//
//        {
//
//            System.out.println(dvd.getDVD());
//
//            System.out.println(dvd.get());
//
//        }
    }



    /******************************************************************
     Creates a beginning starter list of users to work with
     @return void
     ******************************************************************/
    public void createList() {

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        GregorianCalendar temp1 = new GregorianCalendar();
        GregorianCalendar temp2 = new GregorianCalendar();
        GregorianCalendar temp3 = new GregorianCalendar();
        GregorianCalendar temp4 = new GregorianCalendar();
        GregorianCalendar temp5 = new GregorianCalendar();
        GregorianCalendar temp6 = new GregorianCalendar();

        try {
            Date d1 = df.parse("3/20/2018");
            temp1.setTime(d1);
            Date d2 = df.parse("4/20/2018");
            temp2.setTime(d2);
            Date d3 = df.parse("12/20/2018");
            temp3.setTime(d3);
            Date d4 = df.parse("1/20/2018");
            temp4.setTime(d4);
            Date d5 = df.parse("1/20/2010");
            temp5.setTime(d5);
            Date d6 = df.parse("1/20/2020");
            temp6.setTime(d6);


            Game game1 = new Game(temp1, temp2,"Street Fighter Alpha", "Allie Allen ", PlayerType.Xbox360);
            Game game2 = new Game(temp5, temp3,"The Shadow of the Colossus", "Sam Stone" , PlayerType.PS4);
            Game game3 = new Game(temp1, temp6,"God of War", "Callous Meridious" , PlayerType.PC);
            DVD bluRay1 = new DVD(temp1, temp3, "Titanic III", "Jim Carry" );
            BluRay bluRay2 = new BluRay(temp5, temp2, "Shadow Runner", "Jim Booluchi" , false);
            BluRay bluRay3 = new BluRay(temp5, temp1, "A Nightmare Before Christmas", "Charlie Chaplin" , true);

            add(game1);
            add(game2);
            add(game3);
            add(bluRay1);
            add(bluRay2);
            add(bluRay3);


        } catch (ParseException e) {
            throw new RuntimeException("Error in testing, creation of list");
        }

    }

    /******************************************************************
     Returns the unit at the specified index.
     @param index of unit
     @return unit at specified index
     ******************************************************************/
    public DVD getDVD(int index) {
        return listDVDs.get(index);
    }

    /******************************************************************
     Returns the dvd list
     @return the dvd list
     ******************************************************************/
    public ArrayList<DVD> getList() {
        return listDVDs;
    }

    /******************************************************************
     Clears the dvd list
     @return none
     ******************************************************************/
    public void clear() {
        listDVDs.clear();
    }

    /******************************************************************
     Sorts the dvd list and displays the sorted list
     @return none
     ******************************************************************/
    public void sortDateDue() {
        fireTableCellUpdated(0,listDVDs.size() - 1);
        Collections.sort(listDVDs);
    }

    /******************************************************************
     Helper method to sort through the list
     ******************************************************************/
    public int compare(Double s, Double t) {
        return s.compareTo(t);
    }
}