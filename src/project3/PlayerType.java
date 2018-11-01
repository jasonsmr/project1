package project3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

//@XmlRootElement(name = "PlayerType")
//@XmlAccessorType(XmlAccessType.FIELD)
public enum PlayerType implements Serializable {
    Xbox360, PS3, XboxOne, PC, PS4, NONE;

    @Override
    public String toString() {
        switch(this) {
            case Xbox360: return "Xbox360";
            case PS3: return "PS3";
            case XboxOne: return "XboxOne";
            case PC: return "PC";
            case PS4: return "PS4";
            case NONE: return "";
            default: throw new IllegalArgumentException();
        }
    }
}
