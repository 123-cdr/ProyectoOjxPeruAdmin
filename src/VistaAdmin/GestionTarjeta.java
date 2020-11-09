
package VistaAdmin;

import Conexion.ConexionSQL;
import com.placeholder.PlaceHolder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * @author Davis
 */
public class GestionTarjeta extends javax.swing.JFrame {

    PlaceHolder holder;
    int idCliente = 0, idTarjeta = 0;
    boolean ClienteRepetido = false;
    
    public GestionTarjeta() {
        initComponents();
        holder = new PlaceHolder(txtBuscarCliente, "Ingrese DNI del Cliente");
    }
    
    String codigos(){          
        String cod = "";
        int j;
        int cont=1;
        String num="";
        String c="";
        String SQL="select max(codigoTarjeta) from Tarjeta";

        try {
            Statement st = cn.createStatement();
            ResultSet rs=st.executeQuery(SQL);
            if(rs.next())
            {              
                 c=rs.getString(1);
            }
                       
            if(c==null){
                cod = "1010-1010-1010-0001";
            }
            else{
                char r1=c.charAt(15);
                char r2=c.charAt(16);
                char r3=c.charAt(17);
                char r4=c.charAt(18);
                String r="";
                r=""+r1+r2+r3+r4;
                 j=Integer.parseInt(r);
                 GenerarCodigos gen= new GenerarCodigos();
                 gen.generar(j);
                 cod = "1010-1010-1010-"+gen.serie();                               
            }
             
        } catch (SQLException ex) {
             System.out.println("Error al ingresar datos de la Tarjeta: " + ex);
        }
        return cod;
    }
    
    void ingresar(){
        String sql="INSERT INTO Tarjeta (codigoTarjeta, fechaVencimiento,contrasenia,saldoTarjeta,estadoTarjeta, comentarioTarjeta) VALUES (?,?,?,?,?,?)";
        Date fec = new Date();
        int dia = fec.getDate();
        int mes = fec.getMonth()+1;
        int anio = fec.getYear()+1900;
        
        try {
            PreparedStatement pst  = cn.prepareStatement(sql);
            pst.setString(1, codigos());
            pst.setString(2, (anio+2)+"-"+mes+"-"+dia);
            pst.setString(3, txtContrasenia.getText());
            pst.setDouble(4, Double.parseDouble(txtSaldoTarjeta.getText()));
            pst.setInt(5, 1);
            pst.setString(6, "Nueva Tarjeta");

            int n=pst.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "Registro Guardado con Exito");
            }
        } catch (SQLException ex) {
            System.out.println("Error al ingresar datos de la Tarjeta: " + ex);
        }
    }
    
    void BuscarRepetidoCliente(){
        String mostrar="SELECT * FROM Cliente WHERE idCliente="+idCliente+" AND idTarjeta!="+0;

        try {
              Statement st = cn.createStatement();
              ResultSet rs = st.executeQuery(mostrar);
              if(rs.next())
              {
                  ClienteRepetido = true;
              }

        } catch (SQLException ex) {
            System.out.println("Error al buscar Cliente repetido: " + ex);
        }
    }
    
    void BuscarCliente(){     
        String buscarDNI = txtBuscarCliente.getText();
        //String mostrar="SELECT * FROM Cliente WHERE dni='"+buscarDNI+"' AND estadoCliente="+1;
        String mostrar="SELECT * FROM Cliente WHERE dni='"+buscarDNI+"' AND (estadoCliente=1 OR estadoCliente=3)";

        String clienteNombre="";
        try {
              Statement st = cn.createStatement();
              ResultSet rs = st.executeQuery(mostrar);
              if(rs.next())
              {
                  clienteNombre = rs.getString("nombre")+" "+rs.getString("apellidoP") + " "+ rs.getString("apellidoM");
                  idCliente = rs.getInt("idCliente");
                  txtCliente.setText(clienteNombre);
                  if(rs.getInt("estadoCliente")==3){
                      txtSaldoTarjeta.setText(""+EliminarTarjeta.saldoAnterior);
                  }
                  else if(rs.getInt("estadoCliente")==1){
                      txtSaldoTarjeta.setText("0");
                  }
              }

        } catch (SQLException ex) {
            System.out.println("Error al buscar Cliente: " + ex);
        }       
    }
    
    void ModificarCliente(){
        try {

            String insertar = "UPDATE Cliente SET "
            +"estadoCliente="+1+", "
            +"idTarjeta="+idTarjeta
            +" WHERE idCliente='"+idCliente+"'";
            PreparedStatement pst = cn.prepareStatement(insertar);

            pst.executeUpdate();

        } catch (Exception e) {
            System.out.println("error al modificar los datos del Cliente: "+e);
        }  
    }
    
    void SeleccionarTarjeta(){
        String mostrar="SELECT * FROM Tarjeta WHERE idTarjeta=(SELECT MAX(idTarjeta) FROM Tarjeta)";

        try {
              Statement st = cn.createStatement();
              ResultSet rs = st.executeQuery(mostrar);
              if(rs.next())
              {
                  idTarjeta = rs.getInt("idTarjeta");   
                  System.out.println(idTarjeta);
              }

        } catch (SQLException ex) {
            System.out.println("Error al buscar Tarjeta: " + ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        txtContrasenia = new javax.swing.JTextField();
        txtSaldoTarjeta = new javax.swing.JTextField();
        btnGuardar = new LIB.FSButtonMD();
        btnRegresar = new LIB.FSButtonMD();
        jLabel6 = new javax.swing.JLabel();
        txtBuscarCliente = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel1.setText("ACTIVAR TARJETA");

        jLabel2.setFont(new java.awt.Font("Yu Gothic Light", 1, 14)); // NOI18N
        jLabel2.setText("Cliente:");

        jLabel3.setFont(new java.awt.Font("Yu Gothic Light", 1, 14)); // NOI18N
        jLabel3.setText("Contraseña:");

        jLabel5.setFont(new java.awt.Font("Yu Gothic Light", 1, 14)); // NOI18N
        jLabel5.setText("Saldo Tarjeta:");

        txtCliente.setEditable(false);

        btnGuardar.setText("GUARDAR");
        btnGuardar.setColorHover(new java.awt.Color(51, 153, 0));
        btnGuardar.setColorTextHover(new java.awt.Color(255, 255, 255));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnRegresar.setText("REGRESAR");
        btnRegresar.setColorHover(new java.awt.Color(51, 153, 0));
        btnRegresar.setColorTextHover(new java.awt.Color(255, 255, 255));
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscaPerson.png"))); // NOI18N
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtContrasenia)
                                        .addComponent(txtSaldoTarjeta, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(116, 116, 116))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)
                        .addGap(35, 35, 35)
                        .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSaldoTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        BuscarRepetidoCliente();
        if(ClienteRepetido == true){
            JOptionPane.showMessageDialog(null, "El cliente cuenta con tarjeta activa");
        }
        else{
            ingresar();
            SeleccionarTarjeta();
            ModificarCliente();
            this.dispose();
        }       
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        BuscarCliente();
    }//GEN-LAST:event_jLabel6MouseClicked

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
            java.util.logging.Logger.getLogger(GestionTarjeta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionTarjeta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionTarjeta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionTarjeta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionTarjeta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private LIB.FSButtonMD btnGuardar;
    private LIB.FSButtonMD btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtBuscarCliente;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtContrasenia;
    private javax.swing.JTextField txtSaldoTarjeta;
    // End of variables declaration//GEN-END:variables
    ConexionSQL cc = new ConexionSQL();
    Connection cn= ConexionSQL.conexionn();
}
