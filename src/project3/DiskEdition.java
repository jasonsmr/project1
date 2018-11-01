package project3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

//@XmlRootElement(name = "DiskEdition")
//@XmlAccessorType(XmlAccessType.FIELD)
public enum DiskEdition implements Serializable {
    Extended, Standard, DirectorsCut, NONE;

    @Override
    public String toString() {
        switch(this) {
            case Extended: return "Extended";
            case Standard: return "Standard";
            case DirectorsCut: return "DirectorsCut";
            case NONE: return "";
            default: throw new IllegalArgumentException();
        }
    }
}