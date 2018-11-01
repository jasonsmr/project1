package project3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

//@XmlRootElement(name = "PlayerType")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Info implements Serializable {
    private String info = "BLANK TYPE";
    private String output = "I AM BLANK GAME OUTPUT";
    private PlayerType player;
    private boolean doubleLayer;

    public Info() {
        this.info = "DVD";
        this.doubleLayer = false;
        this.output = this.toString();
    }

    public Info(boolean blueRay) {
        this.info = "BluRay";
        this.doubleLayer = blueRay;
        this.output = this.toString();
    }

    public Info(PlayerType player) {
        this.info = "Game";
        this.player = player;
        this.doubleLayer = false;
        this.output = this.toString();
    }

    public String getInfo() {
        return this.output;
    }

    public String toString() {
        switch (this.info) {
            case "DVD":
                return "DVD - Standard Single Layered: DVD";

            case "BluRay":
                return "BluRay - Double Layered: " + this.doubleLayer;

            case "Game":
                return "Game - Required Player System: " + this.player;

            default:
                throw new IllegalArgumentException();
        }
    }


}
