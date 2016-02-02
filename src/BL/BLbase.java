/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import DAT.DATbase;
import clases.base;
import clases.tabla;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jhesy
 */
public class BLbase {
    
    DATbase ManejadorTabla = new DATbase();
      
    //Para el Arreglo de Objetos
    
    ArrayList<base> ArrBase = new ArrayList<>();
    
    public ArrayList<base> Consultar(String user, String pass) throws ClassNotFoundException, SQLException {
        ArrBase.clear();
        int intIncremento =0;
        ResultSet rs;
        rs = ManejadorTabla.Consultar(user, pass);
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
            base e = new base();
            for (String columnName : columns) 
            {
                String value = rs.getString(columnName);
                if (columnName.equals("TABLE_SCHEMA"))
                    e.setStrNombre(value);
                
            }
            ArrBase.add(e);
            intIncremento = intIncremento +1;
        }
        return ArrBase;
    }
}
