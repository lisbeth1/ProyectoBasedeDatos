package DAT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jhesy
 */
public class DATcolumna {
    DATconexion c = new DATconexion();
    public ResultSet Consultar_columna(String user, String pass,String columna) throws ClassNotFoundException, SQLException
  {
        Statement st = c.AbrirConexion(user, pass).createStatement();
        String Sentencia = "select column_name , DATA_TYPE from INFORMATION_SCHEMA.COLUMNS where table_name = '"+columna+"'";
        ResultSet rs = st.executeQuery(Sentencia);
        return rs;
    } 
}
