package gui.dialog;

import connectDB.JDBCConnection;
import controller.KhachHangController;
import entities.KhachHang;
import gui.page.KhachHangPage;
import gui.page.LuongPage;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MessageDialog;
import utils.RandomGenerator;
import utils.Validation;

/**
 *
 * @author atuandev
 */
public class CreateLuong extends javax.swing.JDialog {
    private LuongPage NV_GUI;

    public CreateLuong(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public CreateLuong(java.awt.Frame parent, boolean modal, LuongPage NV_GUI) throws Exception {
        super(parent, modal);
        initComponents();
        this.NV_GUI = NV_GUI;
        loadData();
    }
    private void loadData() throws Exception{
        String[] employeeNames = JDBCConnection.selectNameNV();
        for (String name : employeeNames) {
                jComboBox1.addItem(name);
        }
        txtLuongCanBan.setText("15000000");
        txtLuongCanBan.setEditable(false);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel15 = new javax.swing.JPanel();
        lblDialog = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        lblHoTen = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel19 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtLuongCanBan = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtSoNgayCong = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtPhuCap = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtThuong = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        btnHuy = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel15.setBackground(new java.awt.Color(0, 153, 153));
        jPanel15.setMinimumSize(new java.awt.Dimension(100, 60));
        jPanel15.setPreferredSize(new java.awt.Dimension(500, 50));
        jPanel15.setLayout(new java.awt.BorderLayout());

        lblDialog.setFont(new java.awt.Font("Roboto Medium", 0, 18)); // NOI18N
        lblDialog.setForeground(new java.awt.Color(255, 255, 255));
        lblDialog.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDialog.setText("THÊM THÔNG TIN LƯƠNG NHÂN VIÊN");
        jPanel15.add(lblDialog, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel15, java.awt.BorderLayout.NORTH);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setPreferredSize(new java.awt.Dimension(500, 40));
        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        lblHoTen.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHoTen.setText("Họ tên");
        lblHoTen.setMaximumSize(new java.awt.Dimension(44, 40));
        lblHoTen.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel18.add(lblHoTen);

        jComboBox1.setPreferredSize(new java.awt.Dimension(330, 40));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel18.add(jComboBox1);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(500, 40));
        jPanel19.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel12.setText("Lương căn bản");
        jLabel12.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabel12.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel19.add(jLabel12);

        txtLuongCanBan.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtLuongCanBan.setPreferredSize(new java.awt.Dimension(330, 40));
        jPanel19.add(txtLuongCanBan);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setPreferredSize(new java.awt.Dimension(500, 40));
        jPanel21.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        jLabel14.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel14.setText("Số ngày công");
        jLabel14.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabel14.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel21.add(jLabel14);

        txtSoNgayCong.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtSoNgayCong.setPreferredSize(new java.awt.Dimension(330, 40));
        jPanel21.add(txtSoNgayCong);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setPreferredSize(new java.awt.Dimension(500, 40));
        jPanel22.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        jLabel15.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel15.setText("Phụ cấp");
        jLabel15.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabel15.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel22.add(jLabel15);

        txtPhuCap.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPhuCap.setPreferredSize(new java.awt.Dimension(330, 40));
        jPanel22.add(txtPhuCap);

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setPreferredSize(new java.awt.Dimension(500, 40));
        jPanel23.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        jLabel16.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel16.setText("Thưởng");
        jLabel16.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabel16.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel23.add(jLabel16);

        txtThuong.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtThuong.setPreferredSize(new java.awt.Dimension(330, 40));
        jPanel23.add(txtThuong);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(275, 275, 275))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 5));

        btnHuy.setBackground(new java.awt.Color(255, 102, 102));
        btnHuy.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
        btnHuy.setForeground(new java.awt.Color(255, 255, 255));
        btnHuy.setText("HỦY BỎ");
        btnHuy.setBorderPainted(false);
        btnHuy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHuy.setFocusPainted(false);
        btnHuy.setFocusable(false);
        btnHuy.setPreferredSize(new java.awt.Dimension(200, 40));
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel8.add(btnHuy);

        btnAdd.setBackground(new java.awt.Color(0, 204, 102));
        btnAdd.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("THÊM");
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.setFocusPainted(false);
        btnAdd.setFocusable(false);
        btnAdd.setPreferredSize(new java.awt.Dimension(200, 40));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel8.add(btnAdd);

        getContentPane().add(jPanel8, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try {
            String idNV = JDBCConnection.selectIDNV((String) jComboBox1.getSelectedItem());
            float luongCoBan = Float.parseFloat(txtLuongCanBan.getText());
            int soNgayCong = Integer.parseInt(txtSoNgayCong.getText());
            float phuCap = Float.parseFloat(txtPhuCap.getText());
            float thuong = Float.parseFloat(txtThuong.getText());
            JDBCConnection.insertInfoLuong( idNV,  luongCoBan, soNgayCong, phuCap, thuong);
            NV_GUI.loadTable();
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(CreateLuong.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        String selectedItem = (String) jComboBox1.getSelectedItem();
        try {
            String phucap = JDBCConnection.selectPhuCap(selectedItem);
            txtPhuCap.setText(phucap);
            String thuong = JDBCConnection.selectThuong(selectedItem);
            txtThuong.setText(thuong);
        } catch (Exception ex) {
            Logger.getLogger(CreateLuong.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnHuy;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblDialog;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JTextField txtLuongCanBan;
    private javax.swing.JTextField txtPhuCap;
    private javax.swing.JTextField txtSoNgayCong;
    private javax.swing.JTextField txtThuong;
    // End of variables declaration//GEN-END:variables
}
