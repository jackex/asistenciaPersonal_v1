/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

//import com.itextpdf.text.BadElementException;
import control.SQLEmpleados;
import javax.swing.JOptionPane;
import modelo.Empleados;
import control.HuellaDigital;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JRootPane;

/**
 *
 * @author jack
 */
public class AgregarEmpleados extends javax.swing.JFrame {

    /**
     * Creates new form AgregarEmpleados
     */
    Index index;
    private SQLEmpleados SQL = new SQLEmpleados(this);
    //HuellaDigital HUELLA = new HuellaDigital(this);
    public HuellaDigital HUELLA2;
    public File ARCHIVOIMAGEN;

    public void imagenPorDefecto() throws IOException {
        this.imagenEmpleado.setIcon(new ImageIcon("/imagenes/usuario_defecto.jfif"));
    }

    public AgregarEmpleados() {
        super("Nuevo Empleado");
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        initComponents();
        this.BotonFoto.setEnabled(false);
        this.mensajeHuella.setEnabled(false);
        this.botonAgregarEmpleado.setEnabled(false);
        this.ACTIVARHUELLA.setEnabled(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(AgregarEmpleados.HIDE_ON_CLOSE);
        this.setResizable(false);
        SQL.obtenerTipoDocumento();
        SQL.obtenerSexo();
        //SQL.obtenerContrato();
        SQL.obtenerCargos();
        SQL.obtenerDepartamentos();
        //this.mostrarCampos(false);
    }

    public void cerrarVentana() {
        this.setDefaultCloseOperation(AgregarEmpleados.HIDE_ON_CLOSE);
    }

    public void noCerrarVentana() {
        this.setDefaultCloseOperation(AgregarEmpleados.DO_NOTHING_ON_CLOSE);
    }

    private void limpiarCampos() {
        this.documento.setText("");
        this.primer_nombre.setText("");
        this.segundo_nombre.setText("");
        this.primerApellido.setText("");
        this.segundoApellido.setText("");
        this.telefono.setText("");
        this.direccion.setText("");
        this.correo.setText("");
        this.contraseña.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        primer_nombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        segundo_nombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        primerApellido = new javax.swing.JTextField();
        segundoApellido = new javax.swing.JTextField();
        telefono = new javax.swing.JTextField();
        direccion = new javax.swing.JTextField();
        sexo = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        documento = new javax.swing.JTextField();
        tipoDocumento = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        correo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        contraseña = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        cargo = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        DEPARTAMENTOS = new javax.swing.JComboBox<>();
        MUNICIPIOS = new javax.swing.JComboBox<>();
        BotonFoto = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        LblMostrarHuella = new javax.swing.JLabel();
        ACTIVARHUELLA = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mensajeHuella = new javax.swing.JTextArea();
        botonAgregarEmpleado = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        imagenEmpleado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información Empleado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        primer_nombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(primer_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 230, 30));

        jLabel1.setFont(new java.awt.Font("Bitstream Vera Serif", 0, 15)); // NOI18N
        jLabel1.setText("Primer apellido");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        jLabel2.setFont(new java.awt.Font("Bitstream Vera Serif", 0, 15)); // NOI18N
        jLabel2.setText("Primer nombre");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        segundo_nombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(segundo_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 230, 30));

        jLabel3.setFont(new java.awt.Font("Bitstream Vera Serif", 0, 15)); // NOI18N
        jLabel3.setText("Segundo nombre");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel4.setFont(new java.awt.Font("Bitstream Vera Serif", 0, 15)); // NOI18N
        jLabel4.setText("Segundo apellido");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        jLabel5.setFont(new java.awt.Font("Bitstream Vera Serif", 0, 15)); // NOI18N
        jLabel5.setText("Telefono");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        jLabel6.setFont(new java.awt.Font("Bitstream Vera Serif", 0, 15)); // NOI18N
        jLabel6.setText("Dirección");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 82, -1));

        jLabel7.setFont(new java.awt.Font("Bitstream Vera Serif", 0, 15)); // NOI18N
        jLabel7.setText("Municipio");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, -1, -1));

        primerApellido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(primerApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 230, 30));

        segundoApellido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(segundoApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 231, 30));

        telefono.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 230, 30));

        direccion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 231, 30));

        jPanel1.add(sexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 430, 230, -1));

        jLabel8.setFont(new java.awt.Font("Bitstream Vera Serif", 0, 15)); // NOI18N
        jLabel8.setText("Tipo Documento");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Bitstream Vera Serif", 0, 15)); // NOI18N
        jLabel9.setText("Documento");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 80, 20));

        documento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        documento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentoActionPerformed(evt);
            }
        });
        jPanel1.add(documento, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 230, 30));

        jPanel1.add(tipoDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 230, -1));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 549, -1, 17));

        jLabel16.setFont(new java.awt.Font("Bitstream Vera Serif", 0, 15)); // NOI18N
        jLabel16.setText("Correo");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 82, -1));

        correo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 231, 30));

        jLabel17.setFont(new java.awt.Font("Bitstream Vera Serif", 0, 15)); // NOI18N
        jLabel17.setText("Contraseña");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, -1, -1));

        contraseña.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(contraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, 230, 30));

        jLabel18.setFont(new java.awt.Font("Bitstream Vera Serif", 0, 15)); // NOI18N
        jLabel18.setText("Cargo");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, -1, -1));

        jPanel1.add(cargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, 231, -1));

        jLabel10.setFont(new java.awt.Font("Bitstream Vera Serif", 0, 15)); // NOI18N
        jLabel10.setText("Sexo");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, -1));

        jLabel11.setFont(new java.awt.Font("Bitstream Vera Serif", 0, 15)); // NOI18N
        jLabel11.setText("Departamento");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, -1, -1));

        DEPARTAMENTOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DEPARTAMENTOSActionPerformed(evt);
            }
        });
        jPanel1.add(DEPARTAMENTOS, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 470, 230, -1));

        jPanel1.add(MUNICIPIOS, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 510, 230, -1));

        BotonFoto.setBackground(new java.awt.Color(255, 255, 255));
        BotonFoto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BotonFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/explorer_folders_32px.png"))); // NOI18N
        BotonFoto.setText("Examinar");
        BotonFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonFotoActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Huella Digital", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LblMostrarHuella, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(LblMostrarHuella, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );

        ACTIVARHUELLA.setBackground(new java.awt.Color(255, 255, 255));
        ACTIVARHUELLA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ACTIVARHUELLA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fingerprint_32PX.png"))); // NOI18N
        ACTIVARHUELLA.setText("Iniciar Detección de Huella");
        ACTIVARHUELLA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACTIVARHUELLAActionPerformed(evt);
            }
        });

        mensajeHuella.setEditable(false);
        mensajeHuella.setColumns(20);
        mensajeHuella.setRows(5);
        jScrollPane1.setViewportView(mensajeHuella);

        botonAgregarEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        botonAgregarEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonAgregarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/floppysave32px.png"))); // NOI18N
        botonAgregarEmpleado.setText("Agregar");
        botonAgregarEmpleado.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        botonAgregarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarEmpleadoActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(0, 230, 64));

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("AGREGAR NUEVO EMPLEADO");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("X");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(222, 222, 222)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        imagenEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imagenEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add_user.png"))); // NOI18N
        imagenEmpleado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BotonFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                            .addComponent(ACTIVARHUELLA, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                            .addComponent(imagenEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(268, 268, 268)
                .addComponent(botonAgregarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(imagenEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BotonFoto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ACTIVARHUELLA, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonAgregarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarEmpleadoActionPerformed
        // TODO add your handling code here:   
        if (!this.documento.getText().equals("") && !this.primer_nombre.getText().equals("") && !this.segundo_nombre.getText().equals("")
                && !this.primerApellido.getText().equals("") && !this.segundoApellido.getText().equals("")
                && !this.telefono.getText().equals("") && !this.direccion.getText().equals("")
                && !this.contraseña.getText().equals("")) {
            if (!SQL.validarEmpleado(this.documento.getText())) {
                if (this.ARCHIVOIMAGEN != null) {
                    Empleados empleado = new Empleados();
                    empleado.setTIPO_DOCUMENTO(this.tipoDocumento.getSelectedItem().toString());
                    empleado.setDOCUMENTO(this.documento.getText());
                    empleado.setPRIMER_NOMBRE(this.primer_nombre.getText());
                    empleado.setSEGUNDO_NOMBRE(this.segundo_nombre.getText());
                    empleado.setPRIMER_APELLIDO(this.primerApellido.getText());
                    empleado.setSEGUNDO_APELLIDO(this.segundoApellido.getText());
                    empleado.setTELEFONO(this.telefono.getText());
                    empleado.setDIRECCION(this.direccion.getText());
                    empleado.setCORREO(this.correo.getText());
                    empleado.setCONTRASENA(this.contraseña.getText());
                    empleado.setSEXO(this.sexo.getSelectedItem().toString());
                    empleado.setCARGO(this.cargo.getSelectedItem().toString());
                    String MUNICIPIO = this.MUNICIPIOS.getSelectedItem().toString();
                    SQL.ingresarEmpleados(empleado.getSEXO(), empleado.getTIPO_DOCUMENTO(),
                            empleado.getDOCUMENTO(), empleado.getPRIMER_NOMBRE(), empleado.getSEGUNDO_NOMBRE(),
                            empleado.getPRIMER_APELLIDO(), empleado.getSEGUNDO_APELLIDO(), empleado.getTELEFONO(),
                             empleado.getDIRECCION(), empleado.getCORREO(), empleado.getCONTRASENA(), empleado.getCARGO(),
                            this.HUELLA2, this.ARCHIVOIMAGEN, MUNICIPIO);
                    this.limpiarCampos();
                    JOptionPane.showMessageDialog(null, "Empleado ingresado correctamente!");
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una imagen", "Mensaje", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ya existe un empleado con este documento");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe diligenciar todos los campos");
        }
    }//GEN-LAST:event_botonAgregarEmpleadoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
//        this.HUELLA.Iniciar();
//        this.HUELLA.start();
//        this.HUELLA.estadoHuellas();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void BotonFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonFotoActionPerformed
        // TODO add your handling code here:
        JFileChooser archivo = new JFileChooser();
        archivo.setDialogTitle("Seleccionar imagen");
        archivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (archivo.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = archivo.getSelectedFile();
            if (file.getName().endsWith("jpeg") || file.getName().endsWith("jpg") || file.getName().endsWith("png")) {
                this.ARCHIVOIMAGEN = file;
                ImageIcon imagen = new ImageIcon(file.getPath());
                System.out.println(file.getPath());
                this.imagenEmpleado.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(this.imagenEmpleado.getWidth(),
                        this.imagenEmpleado.getHeight(), java.awt.Image.SCALE_DEFAULT)));
            } else {
                JOptionPane.showMessageDialog(null, "El archivo debe ser una imagen", "Mensaje", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_BotonFotoActionPerformed

    private void documentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentoActionPerformed
        // TODO add your handling code here:
//        if(this.documento.getText().equals("")){
//            JOptionPane.showMessageDialog(null,"Debe digitar un documento", "Mensaje", JOptionPane.WARNING_MESSAGE);
//        }else{
//            if(SQL.validarEmpleado(this.documento.getText())){
//                JOptionPane.showMessageDialog(null,"El usuario ya existe", "Mensaje", JOptionPane.WARNING_MESSAGE);
//            }else{
//                this.ACTIVARHUELLA.setEnabled(true);
//            }
//        }
    }//GEN-LAST:event_documentoActionPerformed

    private void ACTIVARHUELLAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ACTIVARHUELLAActionPerformed
        // TODO add your handling code here:
        int respuesta;
        respuesta = JOptionPane.showOptionDialog(null, "¿Esta seguro que desea continuar?", "Mensaje", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (respuesta == JOptionPane.YES_NO_OPTION) {
            if (!this.documento.getText().equals("") && !this.primer_nombre.getText().equals("") && !this.segundo_nombre.getText().equals("")
                    && !this.primerApellido.getText().equals("") && !this.segundoApellido.getText().equals("")
                    && !this.telefono.getText().equals("") && !this.direccion.getText().equals("")
                    && !this.contraseña.getText().equals("")) {
                this.noCerrarVentana();
                this.ACTIVARHUELLA.setEnabled(false);
                this.LblMostrarHuella.setIcon(null);
                HuellaDigital HUELLA = new HuellaDigital(this);
                HUELLA.Iniciar();
                HUELLA.start();
                HUELLA.estadoHuellas();
            } else {
                JOptionPane.showMessageDialog(null, "Debe llenar todos los datos del empleado", "Mensaje", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_ACTIVARHUELLAActionPerformed

    private void DEPARTAMENTOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEPARTAMENTOSActionPerformed
        // TODO add your handling code here:
        this.MUNICIPIOS.removeAllItems();
        SQL.obtenerMunicipios(this.DEPARTAMENTOS.getSelectedItem().toString());
    }//GEN-LAST:event_DEPARTAMENTOSActionPerformed

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jLabel13MouseClicked

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
            java.util.logging.Logger.getLogger(AgregarEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgregarEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgregarEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgregarEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AgregarEmpleados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton ACTIVARHUELLA;
    public javax.swing.JButton BotonFoto;
    public javax.swing.JComboBox<String> DEPARTAMENTOS;
    public javax.swing.JLabel LblMostrarHuella;
    public javax.swing.JComboBox<String> MUNICIPIOS;
    public javax.swing.JButton botonAgregarEmpleado;
    public javax.swing.JComboBox<String> cargo;
    private javax.swing.JTextField contraseña;
    private javax.swing.JTextField correo;
    private javax.swing.JTextField direccion;
    private javax.swing.JTextField documento;
    private javax.swing.JLabel imagenEmpleado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    public javax.swing.JTextArea mensajeHuella;
    private javax.swing.JTextField primerApellido;
    private javax.swing.JTextField primer_nombre;
    private javax.swing.JTextField segundoApellido;
    private javax.swing.JTextField segundo_nombre;
    public javax.swing.JComboBox<String> sexo;
    private javax.swing.JTextField telefono;
    public javax.swing.JComboBox<String> tipoDocumento;
    // End of variables declaration//GEN-END:variables
}
