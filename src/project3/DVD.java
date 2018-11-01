package project3;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.xml.bind.annotation.*;

import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;
import static java.util.Comparator.naturalOrder;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Comparator;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


/**********************************************************************
 DVD.java implements a DVD unit having parameters such as title,
 customer name, as well as the date when it was purchased and the
 date when it is due.
 @author Jason Rupright
 @version 10/26/2018
 **********************************************************************/
@XmlRootElement(name = "DVD")
//@XmlAccessorType(XmlAccessType.FIELD)

//@XmlSeeAlso({Game.class, BluRay.class})
public class DVD implements Serializable, Comparable<DVD>{
    /**
     * Keeps a static version of the serial version ID for that compiled class
     */
    protected static final long serialVersionUID = 2L;
    /**
     * The date the DVD was rented
     */
    protected GregorianCalendar rentedOn;
    /**
     * The date the DVD is due back
     */
    protected GregorianCalendar dueBack;
    /**
     * The date the DVD is due back
     */
//    protected GregorianCalendar returnDate;
//    /**
//     * The title of the DVD
//     */
    protected String title;
    /**
     * The name of the person who is renting the DVD
     */
    protected String nameOfRenter;
    /**
     * The name of the player type from PlayerType class
     */
    protected Info i;
    /**
     * The string info returned formated from info class call
     */
    protected String info = "ERR: BLANK DVD 0";
    /**
     * The string info returned formated from info class call
     */
    protected double cost;

    protected double daysLate;

    //
//    private long diffInMillies;
//
    private PlayerType player;
    private boolean doubleLayer;



    /******************************************************************
     Constructs a blank DVD
     ******************************************************************/
    public DVD() {
    }

    /******************************************************************
     Constructs a DVD class given each of the dvd's parameters
     @throws NullPointerException
     @param  rentedOn
     @param  dueBack
     @param title
     @param name
     ******************************************************************/
    public DVD(GregorianCalendar rentedOn, GregorianCalendar dueBack, String title, String name) {
        super();

        this.rentedOn = rentedOn;
        this.dueBack = dueBack;
        this.title = title;
        this.nameOfRenter = name;
		this.doubleLayer = false;
		this.player = null;
        this.info = "ERR: BLANK DVD 1";
        this.i = new Info();
        //this.cost = setCost();
        
        if (this.i != null) {
            this.info = this.i.getInfo();
        } else
            throw new NullPointerException("Information var cannot be null in DVD");
    }

    /******************************************************************
     *getter method for GregorianCalender
     * @Return GregorianCalendar
     ******************************************************************/
    public GregorianCalendar getRentedOn() {
        return this.rentedOn;
    }

    public void setRentedOn(GregorianCalendar opened) {
        this.rentedOn = opened;
    }

    /******************************************************************
     Returns the date the DVD is due.
     @return Date on which the DVD is due
     ******************************************************************/
    public GregorianCalendar getDueBack() {
        return dueBack;
    }
    /******************************************************************
     *setter method for GregorianCalender
     * @args  dueback
     * @returns void
     ******************************************************************/
    public void setDueBack(GregorianCalendar dueBack) {
        this.dueBack = dueBack;
    }
    /******************************************************************
     *getter method for title
     * @Return String
     ******************************************************************/
    public String getTitle() {
        return title;
    }
    /******************************************************************
     *setter method for Title
     * @args title
     ******************************************************************/
    public void setTitle(String title) {
        this.title = title;
    }
    /******************************************************************
     *getter method for userName
     * @Return String
     ******************************************************************/
    public String getNameOfRenter() {
        return nameOfRenter;
    }
    /******************************************************************
     *getter method for setting userName/renterName
     * @Return void
     * @args nameofRenter
     ******************************************************************/
    public void setNameOfRenter(String nameOfRenter) {
        this.nameOfRenter = nameOfRenter;
    }
    /******************************************************************
     *getter method for Info
     * @Return String
     ******************************************************************/
    @XmlAttribute(name = "info")
    public String getInfo() {
        return this.info;
    }
    /******************************************************************
     *setter method for Info
     * @Return void
     * @args player
     ******************************************************************/
    public void setInfo(PlayerType player) {
        this.info = "I AM IN DVD NOT GAME";
        System.out.println(info);
    }
    /******************************************************************
     *setter method for Info
     * @Return void
     * @args doubleLayer
     ******************************************************************/
    public void setInfo(boolean doubleLayer) {
        this.info = "I AM IN DVD, NOT BLURAY";
        System.out.println(info);
    }
    /******************************************************************
     *setter method for Info
     * @Return void
     ******************************************************************/
    public void setInfo() {
        this.info = "ERR: BLANK DVD 2";
        this.i = new Info();
		if(i != null) {
            this.info = i.getInfo();
        } else
            throw new NullPointerException("Information var cannot be null in Game");
    }

    /******************************************************************
     *getter method for PlayerType
     * @Return PlayerType
     ******************************************************************/
    public PlayerType getPlayer() {
        return player;
    }
    /******************************************************************
     *setter method for player
     * @Return void
     * @args player
     ******************************************************************/
    public void setPlayer(PlayerType player) {
        this.player = null;
    }
    /******************************************************************
     *getter method for doubleLayer
     * @Return boolean doublelayer
     ******************************************************************/
    public boolean isDoubleLayer() {
        return doubleLayer;
    }
    /******************************************************************
     *setter method for doubleLayer
     * @args boolean doublelayer
     ******************************************************************/
    public void setDoubleLayer(boolean doubleLayer) {
        this.doubleLayer = false;
    }

    /******************************************************************
     Returns whether or not the DVD is a Game
     @return If the DVD is a game (false)
     ******************************************************************/
    public boolean isGame() {
        return false;
    }
    /******************************************************************
     Experamental unit to setCost using the DVD type class
     ******************************************************************/
    public double setCost(GregorianCalendar returnDate) {
        this.cost = 3.00;
        Calendar cal = returnDate;
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        date = cal.getTime();

        System.out.println("Return Date: "+dateFormat.format(date));


        long diffInMillies = (returnDate.getTime().getTime() - this.rentedOn.getTime().getTime());
        this.daysLate = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        double cost = 0.00;
        if (daysLate < 0) {

            JOptionPane.showConfirmDialog(null, "Err: Bad Return Date: \nCannot occur before rental date!", JOptionPane.MESSAGE_PROPERTY, JOptionPane.PLAIN_MESSAGE);
        }

        if (daysLate == 1 || daysLate == 0) {
            System.out.println("DVD Class cost0: " + cost);
            this.cost = 3.00;
        }
        if (daysLate > 1) {
            if (daysLate > 50) {
                this.cost = 50.00;
                System.out.println("DVD Class cost1: " + cost);
            } else {
                System.out.println("DVD Class cost2: " + cost);
                this.cost = cost + daysLate;
            }
        }


        return this.cost;
    }

    public double getCost() {
        return this.cost;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /******************************************************************
     CompareTo method for the date due, used in sorting the ArrayList
     @param dvd
     @return result of the comparison of the dates of two objects
     ******************************************************************/
    public int compareTo(DVD dvd) {
        return dueBack.compareTo(dvd.getDueBack());
    }
}