package BL;

import DAT.DATtabla;
import clases.tabla;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jhesy
 */
public class BLtabla {
       DATtabla ManejadorTabla = new DATtabla();    
    ArrayList<tabla> ArrTabla = new ArrayList<>();    
   
    public ArrayList<tabla> Consultar_tabla_v(String user, String pass,String nombre) throws ClassNotFoundException, SQLException {
        int intIncremento =0;
        ResultSet rs;
        rs = ManejadorTabla.Consultar_tabla_s(user,pass,nombre);
        ResultSetMetaData rm = rs.getMetaData();
        //Recupera los campos de la tabla
        int columnCount = rm.getColumnCount();
        ArrayList<String> columns = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = rm.getColumnName(i);
            columns.add(columnName);
        }
        //Envia los datos a la Clase
        while (rs.next()) 
        {
            tabla e = new tabla();
            for (String columnName : columns) 
            {
                String value = rs.getString(columnName);
                if (columnName.equals("TABLE_NAME"))
                    e.setStrNombre(value);
                
            }
            ArrTabla.add(e);
                    intIncremento = intIncremento +1;
        }
        return ArrTabla;
    }
}
