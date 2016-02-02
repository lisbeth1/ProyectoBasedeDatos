package DAT;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jhesy
 */
public class DATcampo {
      DATconexion d = new DATconexion();

    public ResultSet consultarCampo(String user, String pass, String campo, String tabla ,String base ) throws ClassNotFoundException, SQLException {

        Statement st = d.AbrirConexion(user, pass).createStatement();
        String sentencia ="select "+campo+" from "+base+"."+tabla ;
        ResultSet rs = st.executeQuery(sentencia);
        return rs;
    }
}
