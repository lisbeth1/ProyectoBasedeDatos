package GUI;

import BL.BLbase;
import BL.BLcampo;
import BL.BLcolumna;
import BL.BLtabla;
import clases.base;
import Clases.campo;
import DAT.DATconexion;
import DAT.DATtabla;
import clases.columna;
import clases.tabla;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author jcminga
 */
public class principal extends javax.swing.JFrame {

    public principal() {
        initComponents();
    }

    DATconexion ManejadorConexion = new DATconexion();
    BLbase ManejadorBase = new BLbase();
    BLtabla ManejadorTabla = new BLtabla();
    BLcolumna ManejadorColumna = new BLcolumna();
    BLcampo ManejadorCampo = new BLcampo();
    ArrayList<columna> ArrColumna = new ArrayList<>();
    ArrayList<columna> ArrColumna1 = new ArrayList<>();
    ArrayList<campo> ArrCampo = new ArrayList<>();
    ArrayList<campo> ArrCampo1 = new ArrayList<>();
    DATtabla tablas = new DATtabla();

    ArrayList<String> datos1 = new ArrayList<>();
    ArrayList<String> datos1_1 = new ArrayList<>();
    ArrayList<campo> ArrDatos1 = new ArrayList<>();
    ArrayList<String> datos2 = new ArrayList<>();
    ArrayList<String> datos2_2 = new ArrayList<>();
    ArrayList<Object> objetoTabla1 = new ArrayList<Object>();
    ArrayList<Object> valores = new ArrayList<Object>();
    ArrayList<Object> valores1 = new ArrayList<Object>();

    float porBase = 0;
    float cont;
    String base;
    String nombreC = null;
    String tabla1;
    String tabla2;
    String user;
    String pass;

    public void ingreso(String u, String ps) {
        user = u;
        pass = ps;
    }

    public void ListarDatos_base() throws ClassNotFoundException, SQLException {
        ArrayList<clases.base> ArrBase = ManejadorBase.Consultar(user, pass);
        Object columnas[] = {"BASES DE DATOS"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        jTable1.setModel(modelo);
        for (base objeto : ArrBase) {
            String modeloTemp[] = {objeto.getStrNombre()};
            modelo.addRow(modeloTemp);
        }
    }

    public void ListarDatos_tabla_v(String nombre) throws ClassNotFoundException, SQLException {
        base = nombre;
        ArrayList<tabla> ArrTabla = ManejadorTabla.Consultar_tabla_v(user, pass, nombre);
        Object columnas[] = {"TABLAS DE LA BASE"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        jTable2.setModel(modelo);
        for (tabla objeto : ArrTabla) {
            String modeloTemp[] = {objeto.getStrNombre()};
            modelo.addRow(modeloTemp);
        }
        ArrTabla.clear();
    }

    public void listar_columnas_t_1(String nombre) throws ClassNotFoundException, SQLException {
        ArrColumna.clear();
        ArrColumna1.clear();
        datos1.clear();
        datos1_1.clear();

        datos2.clear();
        datos2_2.clear();
        DefaultTableModel miTableModel = (DefaultTableModel) jTable3.getModel();
        int cols = miTableModel.getColumnCount();
        int fils = miTableModel.getRowCount();

        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                valores.add(j, miTableModel.getValueAt(i, j));
            }
        }
        for (int k = 0; k < valores.size(); k++) {
            System.out.println(valores.get(k));
            if (k == 0) {
                ArrColumna = ManejadorColumna.Consultar_tabla_1(user, pass, (String) valores.get(k));
                tabla1 = (String) valores.get(k);

            } else if (k == 1) {
                ArrColumna1 = ManejadorColumna.Consultar_tabla_2(user, pass, (String) valores.get(k));
                tabla2 = (String) valores.get(k);
            }
            System.out.println(tabla1);
            for (int l = 0; l < ArrColumna.size(); l++) {
                datos1.add(l, ArrColumna.get(l).getStrNombre());
                datos1_1.add(l, ArrColumna.get(l).getStrTipo());
            }

            ArrColumna.clear();
            for (int r = 0; r < ArrColumna1.size(); r++) {
                datos2.add(r, ArrColumna1.get(r).getStrNombre());
                datos2_2.add(r, ArrColumna1.get(r).getStrTipo());
            }
            ArrColumna1.clear();

        }

        Object columnasC[] = {"Tablas1: " + tabla1, "Tabla2: " + tabla2, "% de compatibilidad"};
        DefaultTableModel modelo1 = new DefaultTableModel(null, columnasC);
        jTable4.setModel(modelo1);
        for (int t = 0; t < datos1.size(); t++) {
            for (int j = 0; j < datos2.size(); j++) {
                ArrCampo = ManejadorCampo.ConsultarCampo(user, pass, base, tabla1, datos1.get(t));
                ArrCampo1 = ManejadorCampo.ConsultarCampo2(user, pass, base, tabla2, datos2.get(j));
                if (datos1.get(t).equals(datos2.get(j)) && datos1_1.get(t).equals(datos2_2.get(j))) {//compara el tipo de datos y nombre de cada columna
                    porBase = 100;
                    Object nuevaFila[] = {datos1.get(t), datos2.get(j), porBase};
                    modelo1.addRow(nuevaFila);
                } else {
                    if (datos1_1.get(t).equals(datos2_2.get(j))) {//compara el tipo de dato de cada columna de la primera tabla con las de la segunda
                        for (int f = 0; f < ArrCampo.size(); f++) {
                            for (int h = 0; h < ArrCampo1.size(); h++) {
                                if (ArrCampo.get(f).getCampo().equals(ArrCampo1.get(h).getCampo())) {//compara las tuplas de las columnas de la primera tabla con las de la segunda
                                    cont = cont + 1;
                                }
                            }
                        }
                        porBase = (cont / (ArrCampo.size() * ArrCampo1.size())) * 100;//multiplica el numero de coincidencias por 100 y divide para el total de coincidencias que deben haber
                        if (porBase > 0) {
                            Object nuevaFila[] = {datos1.get(t), datos2.get(j), porBase};
                            modelo1.addRow(nuevaFila);
                        }
                    }
                }
                cont = 0;
                porBase = 0;
            }
        }
    }

    public void comparar() throws ClassNotFoundException, SQLException {
        String nTabla1 = null;

        DefaultTableModel miTableModel = (DefaultTableModel) jTable3.getModel();
        int cols = miTableModel.getColumnCount();
        int fils = miTableModel.getRowCount();
        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0) {
                    nTabla1 = (String) miTableModel.getValueAt(i, j);
                }
            }

        }
        listar_columnas_t_1(nTabla1);//se presentan las columnaas en la tabla de interfaz
    }

    public void borrar_tabla2() {
        DefaultTableModel modelo3 = (DefaultTableModel) jTable2.getModel();
        while (modelo3.getRowCount() > 0) {
            modelo3.removeRow(0);
        }

        TableColumnModel modCol = jTable2.getColumnModel();
        while (modCol.getColumnCount() > 0) {
            modCol.removeColumn(modCol.getColumn(0));
        }

    }

    public void borrar_tabla4() {
        DefaultTableModel modelo3 = (DefaultTableModel) jTable4.getModel();
        while (modelo3.getRowCount() > 0) {
            modelo3.removeRow(0);
        }

        TableColumnModel modCol = jTable4.getColumnModel();
        while (modCol.getColumnCount() > 0) {
            modCol.removeColumn(modCol.getColumn(0));
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jmAgregar = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        btnCerrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnComparar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btnEliminarF = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        btnConsultar = new javax.swing.JButton();

        jmAgregar.setText("Agregar");
        jmAgregar.setComponentPopupMenu(jPopupMenu1);
        jmAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmAgregarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jmAgregar);

        jMenuItem1.setText("CrearJoin");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem1);

        setBackground(new java.awt.Color(86, 25, 255));

        btnCerrar.setBackground(new java.awt.Color(255, 102, 102));
        btnCerrar.setFont(new java.awt.Font("Showcard Gothic", 0, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 0, 0));
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Showcard Gothic", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("Bases de Datos existentes");

        jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 255, 0), java.awt.Color.lightGray));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.setComponentPopupMenu(jPopupMenu1);
        jTable1.setGridColor(new java.awt.Color(0, 102, 102));
        jTable1.setSelectionBackground(java.awt.SystemColor.inactiveCaption);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        btnComparar.setBackground(new java.awt.Color(204, 204, 204));
        btnComparar.setFont(new java.awt.Font("Snap ITC", 0, 12)); // NOI18N
        btnComparar.setForeground(new java.awt.Color(0, 153, 153));
        btnComparar.setText("Comparar");
        btnComparar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompararActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable2.setComponentPopupMenu(jPopupMenu1);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("Tablas");

        btnEliminarF.setBackground(new java.awt.Color(204, 204, 204));
        btnEliminarF.setFont(new java.awt.Font("Snap ITC", 0, 12)); // NOI18N
        btnEliminarF.setForeground(new java.awt.Color(0, 153, 153));
        btnEliminarF.setText("Eliminar");
        btnEliminarF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarFActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jLabel3.setFont(new java.awt.Font("Showcard Gothic", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("Tablas a Comparar");

        jLabel4.setFont(new java.awt.Font("Showcard Gothic", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("RESULTADOS DE Posibles combinaciones");

        jLabel5.setFont(new java.awt.Font("Showcard Gothic", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("Join");

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable4.setComponentPopupMenu(jPopupMenu2);
        jScrollPane4.setViewportView(jTable4);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(jTable5);

        btnConsultar.setBackground(new java.awt.Color(204, 204, 204));
        btnConsultar.setFont(new java.awt.Font("Snap ITC", 0, 12)); // NOI18N
        btnConsultar.setForeground(new java.awt.Color(0, 153, 153));
        btnConsultar.setText("CONSULTAR");
        btnConsultar.setToolTipText("");
        btnConsultar.setAutoscrolls(true);
        btnConsultar.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(0, 51, 204)));
        btnConsultar.setBorderPainted(false);
        btnConsultar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnConsultar.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        btnConsultar.setFocusPainted(false);
        btnConsultar.setIconTextGap(2);
        btnConsultar.setOpaque(false);
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(545, 545, 545))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(115, 115, 115)
                            .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(601, 601, 601)
                            .addComponent(btnComparar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarF))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(33, 33, 33)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(93, 93, 93)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(79, 79, 79)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(477, 477, 477)
                        .addComponent(btnCerrar)))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel1)
                .addGap(234, 234, 234)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(191, 191, 191))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEliminarF)
                        .addComponent(btnComparar))
                    .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCerrar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        try {
            ListarDatos_base();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        borrar_tabla2();
        borrar_tabla4();
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.getSelectedRow();
        String strNombres = jTable1.getModel().getValueAt(row, 0).toString();
        try {
            ListarDatos_tabla_v(strNombres);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(principal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        borrar_tabla4();
    }//GEN-LAST:event_jTable1MouseClicked

    Object columnas[] = {"Datos"};
    DefaultTableModel modelo = new DefaultTableModel(null, columnas);

    private void jmAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmAgregarActionPerformed
        jTable3.setModel(modelo);
        int fils = jTable3.getRowCount();
        fils = fils - 1;
        if (fils < 1) {
            int row = jTable2.getSelectedRow();
            String strNombres = jTable2.getModel().getValueAt(row, 0).toString();
            Object nuevaFila[] = {strNombres};
            modelo.addRow(nuevaFila);
        }
    }//GEN-LAST:event_jmAgregarActionPerformed

    private void btnEliminarFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarFActionPerformed

        DefaultTableModel modelo1 = (DefaultTableModel) jTable3.getModel();
        int fila = jTable3.getSelectedRow();
        modelo1.removeRow(fila);
    }//GEN-LAST:event_btnEliminarFActionPerformed

    private void btnCompararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompararActionPerformed
        try {
            comparar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(principal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCompararActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

    }//GEN-LAST:event_jTable2MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int fila = jTable4.getSelectedRow();
        String dato = jTable4.getModel().getValueAt(fila, 0).toString();
        String dato2 = jTable4.getModel().getValueAt(fila, 1).toString();
        try {
            Join(dato, dato2);
            ManejadorConexion.CerrarConexion();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        System.exit(0);
        try {
            ManejadorConexion.CerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnCerrarActionPerformed
    public void Join(String dato, String dato2) throws ClassNotFoundException, SQLException {
        Object columnacomp[] = null;
        DefaultTableModel modelocomp = new DefaultTableModel(null, columnacomp);
        jTable5.setModel(modelocomp);
        ResultSet rs = tablas.consultarDosTablas("root", "", base, tabla1, tabla2, dato, dato2);
        ResultSetMetaData rm = rs.getMetaData();
        int cantidadColumnas = rm.getColumnCount();
        //Establecer como cabezeras el nombre de las colimnas
        for (int i = 1; i <= cantidadColumnas; i++) {
            modelocomp.addColumn(rm.getColumnLabel(i));
        }
        //Creando las filas para el JTable
        while (rs.next()) {
            Object[] campo = new Object[cantidadColumnas];
            for (int i = 0; i < cantidadColumnas; i++) {
                campo[i] = rs.getObject(i + 1);
            }
            modelocomp.addRow(campo);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnComparar;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnEliminarF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JMenuItem jmAgregar;
    // End of variables declaration//GEN-END:variables

}
