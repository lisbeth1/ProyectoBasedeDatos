package clases;

/**
 *
 * @author Jhesy
 */
public class columna {
    String strNombre;
    String strTipo;

    public columna() {
    }

    public columna(String strNombre, String strTipo) {
        this.strNombre = strNombre;
        this.strTipo = strTipo;
    }

    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    public String getStrTipo() {
        return strTipo;
    }

    public void setStrTipo(String strTipo) {
        this.strTipo = strTipo;
    }
    
}
