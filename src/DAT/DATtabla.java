package DAT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jhesy
 */
public class DATtabla {
    DATconexion c = new DATconexion();
    
    
     public ResultSet Consultar_tabla_s(String user, String pass,String nombre) throws ClassNotFoundException, SQLException
    {
        Statement st = c.AbrirConexion(user, pass).createStatement();
        String Sentencia = "select table_name from information_schema.tables where table_schema = '"+nombre+"'";
        ResultSet rs = st.executeQuery(Sentencia);
        return rs;
    } 
     
     public ResultSet consultarDosTablas(String usuario, String pass, String base,String tabla1,String tabla2,String dato1,String dato2) throws ClassNotFoundException, SQLException {
        Statement st = c.AbrirConexion(usuario, pass).createStatement();
        String sentencia = "select * from " + base + "." + tabla1 + " t1 JOIN " + base + "." + tabla2 + " t2 where t1." +dato1 + " = t2." + dato2;
         System.out.println("*******"+sentencia);
        ResultSet rs = st.executeQuery(sentencia);
        return rs;
       
     } 
    
} 