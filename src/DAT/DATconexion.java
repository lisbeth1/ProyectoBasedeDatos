package DAT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DATconexion {
    
    //Conectarse a la BDD
    public static Connection con;
    
    
    public Connection getConnection (String user, String pass) throws ClassNotFoundException, SQLException 
    {
        System.out.println("");
        Class.forName("com.mysql.jdbc.Driver");
           con=DriverManager.getConnection("jdbc:mysql://localhost","root","");
           System.out.println("conexion establecida");
        return con;
    }
    
    public Connection AbrirConexion(String user, String pass) throws ClassNotFoundException, SQLException
    {
        con = getConnection(user, pass);
        return con;
    }
    
    public void CerrarConexion() throws SQLException
    {
       con.close();
    }

  

   
}
