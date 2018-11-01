package project3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.GregorianCalendar;
/**********************************************************************
 BluRay.java implements a BluRay unit having parameters such as title,
 customer name, as well as the date when it was purchased and the
 date when it is due.
 @author Jason Rupright
 @version 10/26/2018
 **********************************************************************/
@XmlRootElement(name = "BluRay")
//@XmlAccessorType(XmlAccessType.FIELD)
public class BluRay extends DVD implements Serializable {
    /**
     * Keeps a static version of the serial version ID for that compiled class
     */
    private final long serialVersionUID = super.serialVersionUID;
//    /** The name of the player type from PlayerType class*/
//    protected Info i;
    /** The string info returned formated from information class call */
    protected String info = "ERR: BLANK BLURAY 0";
    /** The player is set to false*/
    protected PlayerType player;
    /** The boolean var containing information whether BlueRay disk contains a DoubleLayer*/
    protected boolean doubleLayer ;
    /** The double value for cost*/
    protected double cost;


    @Override
    public double setCost(GregorianCalendar returnDate) {
        this.cost = super.setCost(returnDate);
        if(isDoubleLayer()==true) {
            if (2 * this.cost > 100) {
                this.cost = 100;
            } else {
                this.cost = 2 * this.cost;
            }
        }
        return this.cost;

    }

    @Override
    public PlayerType getPlayer() {
        return this.player;
    }
    @Override
    public void setPlayer(PlayerType player) {
        this.player = null;
    }

    @Override
    public double getCost() {
        return this.cost;
    }

    public BluRay() {
        super();
        System.out.println("Warn: No doubleLayer: "+doubleLayer);
    }

    public BluRay(GregorianCalendar rentedOn, GregorianCalendar dueBack,
                  String title, String name, boolean doubleLayer) {
        super(rentedOn, dueBack, title, name );
        this.info = "ERR: BLANK BLURAY 1";
        this.doubleLayer = doubleLayer;
        Info i = new Info(doubleLayer);
        if (i != null) {
            this.info = i.getInfo();
        } else
            throw new NullPointerException("Information var cannot be null in BluRay");
        //this.cost = this.setCost();
    }
    @Override
    public boolean isDoubleLayer() {
        return doubleLayer;
    }
    @Override
    public void setDoubleLayer(boolean doubleLayer) {
        this.doubleLayer = doubleLayer;
    }

    @Override
    public void setInfo(boolean doubleLayer) {
        this.info = "ERR: BLANK BLURAY 2";

        Info i = new Info(doubleLayer);
        if (i != null) {
            this.doubleLayer = doubleLayer;
            this.info = i.getInfo();
        } else
            throw new NullPointerException("Information var cannot be null in Game");
    }

    @Override
    public String getInfo() {

        return this.info;
    }

}
