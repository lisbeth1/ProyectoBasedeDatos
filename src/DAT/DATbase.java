/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jhesy
 */
public class DATbase {
    DATconexion c = new DATconexion();
    public ResultSet Consultar(String user, String pass) throws ClassNotFoundException, SQLException
    {
        Statement st = c.AbrirConexion(user, pass).createStatement();
        String Sentencia = "SELECT table_schema  FROM information_schema.tables GROUP BY table_schema";
        ResultSet rs = st.executeQuery(Sentencia);
        return rs;
    }   
}
