package clases;

/**
 *
 * @author Jhesy
 */
public class tabla {
 
    String strNombre;
    String strBase;

    public tabla() {
    }

    public tabla(String strNombre, String strBase) {
        this.strNombre = strNombre;
        this.strBase = strBase;
    }

    
    public String getStrBase() {
        return strBase;
    }

    public void setStrBase(String strBase) {
        this.strBase = strBase;
    }

    

    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }
    
}
