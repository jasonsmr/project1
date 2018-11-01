package project3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.GregorianCalendar;
/**********************************************************************
 Game.java implements a Game unit having parameters such as title,
 customer name, as well as the date when it was purchased and the
 date when it is due.
 @author Jason Rupright
 @version 10/26/2018
 **********************************************************************/
@XmlRootElement(name = "Game")
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name = "Game")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Game extends DVD implements Serializable {
	/**
	 * Keeps a static version of the serial version ID for that compiled class
	 */
	protected final long serialVersionUID = super.serialVersionUID;
	/**
	 * The name of the player type from PlayerType class*
	 */
	protected PlayerType player;
//	/**
//	 * The name of the information type from Information class
//	 */
//	protected Info i;
	/**
	 * The string info returned formated from information class call
	 */
	public String info = "ERR: BLANK GAME 0";
	/**
	 * The double daysLate returned.
	 */
	protected double daysLate;
	/**
	 * String form of player var
	 */
	protected String playString;
	/**
	 * var to store boolean doublelayer
	 * doubleLayer is false in DVD.class
	 */
	private boolean doubleLayer;
	/**
	 * var to store double cost var
	 */
	protected double cost;

	/******************************************************************
	 * Experimental Overridden unit to setCost using the DVD type class
	 *
	 ******************************************************************/

	@Override
	public double setCost(GregorianCalendar returnDate) {
		this.cost = super.setCost(returnDate);
		if (this.playString == "PC") {
			if (2 * this.cost > 100) {
				this.cost = 100.00;
			} else {
				this.cost = 2 * this.cost;
			}
		}
		return this.cost;
	}
	//@XmlAttribute
	@Override
	public boolean isDoubleLayer() {
		return this.doubleLayer;
	}
	@Override
	public void setDoubleLayer(boolean doubleLayer) {
		this.doubleLayer = false;
	}

	@Override
	public double getCost() {
		return this.cost;
	}
	/******************************************************************
	 *getter method for playertype
	 * @Return Playertype
	 ******************************************************************/

	@Override
	public PlayerType getPlayer() {
		return this.player;
	}
	/******************************************************************
	 *setter method for playertype
	 * @Return void
	 * @args player
	 ******************************************************************/
//	@Override
//	public void setPlayer(PlayerType player) {
//		this.player = player;
//		this.playString = player.toString();
//	}
	/******************************************************************
	 *setter method for Info
	 * @Return void
	 * @args player
	 ******************************************************************/
	//@XmlAttribute
	@Override
	public void setInfo(PlayerType player) {
		this.info = "ERR: BLANK GAME 2";

		Info i = new Info(player);
		if (i != null) {
			this.playString = player.toString();
			this.info = i.getInfo();
		} else
			throw new NullPointerException("Information var cannot be null in Game");
	}
	/******************************************************************
	 *getter method for Info
	 * @Return String
	 ******************************************************************/

	@Override
	public String getInfo() {
		return this.info;
	}

	public Game() {
		super();
	}
	/******************************************************************
	 Constructs a Game given pramaters
	 @param rentedOn
	 @param dueBack
	 @param title
	 @param name
	 ******************************************************************/
	public Game(GregorianCalendar rentedOn, GregorianCalendar dueBack,
			String title, String name, PlayerType player) {
		super(rentedOn, dueBack, title, name);
		this.info = "ERR: BLANK GAME 1";
		this.player = player;
		Info i = new Info(player);
		if (i != null) {
			this.info = i.getInfo();
		} else
			throw new NullPointerException("Information var cannot be null in Game");
		this.playString = player.toString();
		//this.cost = this.setCost();
		this.doubleLayer = false;
	}
	/******************************************************************
	 Constructs a Game using Extra Player Type
	 @param player
	 ******************************************************************/
//	public Game(PlayerType player) {
//		super();
//		//this.player = player;
//		//this.playString = player.toString();
//	}

}
