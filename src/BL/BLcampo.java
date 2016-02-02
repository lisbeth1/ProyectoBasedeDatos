package BL;

import DAT.DATcampo;
import DAT.DATconexion;
import Clases.campo;
import clases.columna;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jhesy
 */
public class BLcampo {
    DATcampo ManejadorCampo= new DATcampo();
    
   DATconexion ManejadorConexion= new DATconexion();
    
    ArrayList<campo> ArrCampo = new ArrayList<>();
    ArrayList<campo> ArrCampo2 = new ArrayList<>();
    public ArrayList<campo> ConsultarCampo (String user, String pass, String base,String tabla, String campo) throws ClassNotFoundException, SQLException {
        ArrCampo.clear(); // limpiai arraylist
        int intIncremento =0;
        ResultSet rs;
        rs = ManejadorCampo.consultarCampo( user, pass,campo, tabla,base);
        ResultSetMetaData rm = rs.getMetaData(); //retorna los todos los valores de todas las bases
        //Recupera las columnas de la base
        int columnCount = rm.getColumnCount();
        ArrayList<String> columns = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = rm.getColumnName(i);
            columns.add(columnName);
        }
        //Envia los datos a la Clase
        while (rs.next()) 
        {
            campo c = new campo();
            for (String columnName : columns) 
            {
                String value = rs.getString(columnName);
                if (columnName.equals(campo))
                    c.setCampo(value);
              
            
            }
            ArrCampo.add(c);
            intIncremento = intIncremento +1;
            
        }
         ManejadorConexion.CerrarConexion();
        return ArrCampo;
    }
    public ArrayList<campo> ConsultarCampo2 (String user, String pass, String base,String tabla, String campo) throws ClassNotFoundException, SQLException {
        ArrCampo2.clear(); // limpiai arraylist
        int intIncremento =0;
        ResultSet rs;
        rs = ManejadorCampo.consultarCampo(user, pass, campo, tabla,base);
        ResultSetMetaData rm = rs.getMetaData(); //retorna los todos los valores de todas las bases
        //Recupera las columnas de la base
        int columnCount = rm.getColumnCount();
        ArrayList<String> columns = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = rm.getColumnName(i);
            columns.add(columnName);
        }
        //Envia los datos a la Clase
        while (rs.next()) 
        {
            campo c = new campo();
            for (String columnName : columns) 
            {
                String value = rs.getString(columnName);
                if (columnName.equals(campo))
                    c.setCampo(value);
              
            
            }
            ArrCampo2.add(c);
            intIncremento = intIncremento +1;
           
        }
         ManejadorConexion.CerrarConexion();
        return ArrCampo2;
        
    }
    

}
