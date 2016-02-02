package BL;

import DAT.DATcolumna;
import clases.columna;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jhesy
 */
public class BLcolumna {
    DATcolumna ManejadorColumna = new DATcolumna();
    ArrayList<columna> ArrColumna1 = new ArrayList<>();
    ArrayList<columna> ArrColumna2 = new ArrayList<>();
    
    public ArrayList<columna> Consultar_tabla_1(String user, String pass, String nombre) throws ClassNotFoundException, SQLException {
        
        ArrColumna1.clear();
        int intIncremento =0;
        ResultSet rs;
        rs = ManejadorColumna.Consultar_columna(user, pass,nombre);
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
            columna e = new columna();
            for (String columnName : columns) 
            {
                String value = rs.getString(columnName);
                if (columnName.equals("COLUMN_NAME"))
                    e.setStrNombre(value);
                if (columnName.equals("DATA_TYPE"))
                    e.setStrTipo(value);
            }
            ArrColumna1.add(e);
            
            intIncremento = intIncremento +1;
        }
        return ArrColumna1;
    }
    
    public ArrayList<columna> Consultar_tabla_2(String user , String pass, String nombre) throws ClassNotFoundException, SQLException {
        int intIncremento =0;
        ResultSet rs;
        rs = ManejadorColumna.Consultar_columna(user, pass, nombre);
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
            columna e = new columna();
            for (String columnName : columns) 
            {
                String value = rs.getString(columnName);
                if (columnName.equals("COLUMN_NAME"))
                    e.setStrNombre(value);
                if (columnName.equals("DATA_TYPE"))
                    e.setStrTipo(value);
                
            }
            ArrColumna2.add(e);
            
            intIncremento = intIncremento +1;
        }
        return ArrColumna2;
    }
}



