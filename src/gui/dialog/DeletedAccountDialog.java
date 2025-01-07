package gui.dialog;

import controller.TaiKhoanController;
import gui.page.TaiKhoanPage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.MessageDialog;
import utils.BCrypt;

public class DeletedAccountDialog extends javax.swing.JDialog {

    private final TaiKhoanController TK_CON = new TaiKhoanController();
    private DefaultTableModel modal;
    private final TaiKhoanPage taiKhoanPage;

    public DeletedAccountDialog(java.awt.Frame parent, boolean modal, TaiKhoanPage taiKhoanPage) {
        super(parent, modal);
        this.taiKhoanPage = taiKhoanPage;
        initComponents();
        fillTable();
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Cho phép chọn nhiều bản ghi
    }

    private void fillTable() {
    modal = new DefaultTableModel();
    String[] header = new String[]{"ID", "Username", "ID NV", "ID VT", "Deleted By", "Delete Time"};
    modal.setColumnIdentifiers(header);
    table.setModel(modal);

    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    table.setDefaultRenderer(Object.class, centerRenderer);

    // Điều chỉnh độ rộng cột
    table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    table.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
    table.getColumnModel().getColumn(1).setPreferredWidth(150); // Username
    table.getColumnModel().getColumn(2).setPreferredWidth(100); // ID NV
    table.getColumnModel().getColumn(3).setPreferredWidth(100); // ID VT
    table.getColumnModel().getColumn(4).setPreferredWidth(150); // Deleted By
    table.getColumnModel().getColumn(5).setPreferredWidth(200); // Delete Time

    // Lấy dữ liệu và thêm vào bảng
    List<List<Object>> list = TK_CON.getDeletedAccounts();
    for (List<Object> row : list) {
        modal.addRow(row.toArray());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel15 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tableItemPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnCheckExpired = new javax.swing.JButton();
        btnQuayLai = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        btnKhoiPhuc1 = new javax.swing.JButton();
        btnXoa1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel15.setBackground(new java.awt.Color(0, 153, 153));
        jPanel15.setMinimumSize(new java.awt.Dimension(100, 60));
        jPanel15.setPreferredSize(new java.awt.Dimension(500, 50));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("Roboto Medium", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Lịch sử tài khoản đã xóa");
        jLabel8.setPreferredSize(new java.awt.Dimension(149, 40));
        jPanel15.add(jLabel8, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel15, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        tableItemPanel.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"123", "Anh Tuấn", "123123", null, null, null},
                {"13124", "czczxc", "zxc", null, null, null},
                {"14123", "zxczc", "zxc", null, null, null},
                {"124123", "zxczx", "zxc", null, null, null}
            },
            new String [] {
                "Mã", "Họ tên", "Số điện thoại", "Giới tính", "Năm sinh", "Ngày vào làm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setFocusable(false);
        table.setRowHeight(40);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setShowHorizontalLines(true);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        tableItemPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 100));

        btnCheckExpired.setBackground(new java.awt.Color(255, 204, 0));
        btnCheckExpired.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
        btnCheckExpired.setForeground(new java.awt.Color(255, 255, 255));
        btnCheckExpired.setText("Kiểm tra quá hạn");
        btnCheckExpired.setBorderPainted(false);
        btnCheckExpired.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCheckExpired.setFocusPainted(false);
        btnCheckExpired.setFocusable(false);
        btnCheckExpired.setPreferredSize(new java.awt.Dimension(200, 40));
        btnCheckExpired.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckExpiredActionPerformed(evt);
            }
        });

        btnQuayLai.setBackground(new java.awt.Color(3, 169, 244));
        btnQuayLai.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
        btnQuayLai.setForeground(new java.awt.Color(255, 255, 255));
        btnQuayLai.setText("Quay lại");
        btnQuayLai.setBorderPainted(false);
        btnQuayLai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnQuayLai.setFocusPainted(false);
        btnQuayLai.setFocusable(false);
        btnQuayLai.setPreferredSize(new java.awt.Dimension(200, 40));
        btnQuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLaiActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setText("Khôi phục tài khoản");

        jTextField1.setText("Username");

        jTextField2.setText("Password");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        btnKhoiPhuc1.setBackground(new java.awt.Color(76, 175, 80));
        btnKhoiPhuc1.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
        btnKhoiPhuc1.setForeground(new java.awt.Color(255, 255, 255));
        btnKhoiPhuc1.setText("Khôi phục");
        btnKhoiPhuc1.setBorderPainted(false);
        btnKhoiPhuc1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKhoiPhuc1.setFocusPainted(false);
        btnKhoiPhuc1.setFocusable(false);
        btnKhoiPhuc1.setPreferredSize(new java.awt.Dimension(200, 40));
        btnKhoiPhuc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhuc1ActionPerformed(evt);
            }
        });

        btnXoa1.setBackground(new java.awt.Color(244, 67, 54));
        btnXoa1.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
        btnXoa1.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa1.setText("Xóa");
        btnXoa1.setBorderPainted(false);
        btnXoa1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoa1.setFocusPainted(false);
        btnXoa1.setFocusable(false);
        btnXoa1.setPreferredSize(new java.awt.Dimension(200, 40));
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnKhoiPhuc1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(btnXoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnQuayLai, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCheckExpired, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKhoiPhuc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuayLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheckExpired, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(88, Short.MAX_VALUE))
        );

        tableItemPanel.add(jPanel3, java.awt.BorderLayout.LINE_END);

        jPanel2.add(tableItemPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 5));
        getContentPane().add(jPanel8, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuayLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuayLaiActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnQuayLaiActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // Lấy chỉ số dòng được chọn
        int row = table.getSelectedRow();

        // Kiểm tra xem có dòng nào được chọn không
        if (row >= 0) {
            String username = table.getValueAt(row, 1).toString();
            jTextField1.setText(username);
        }
    }//GEN-LAST:event_tableMouseClicked

    private void btnCheckExpiredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckExpiredActionPerformed
        List<List<Object>> expiredAccounts = TK_CON.getExpiredAccounts();
        if (expiredAccounts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có tài khoản nào quá hạn khôi phục.");
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Có " + expiredAccounts.size() + " tài khoản quá hạn khôi phục. Bạn có muốn xóa chúng không?", "Xóa tài khoản quá hạn", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                TK_CON.deleteExpiredAccounts();
                fillTable(); // Cập nhật lại bảng sau khi xóa
                JOptionPane.showMessageDialog(this, "Đã xóa các tài khoản quá hạn khôi phục.");
            }
        }
    }//GEN-LAST:event_btnCheckExpiredActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void btnKhoiPhuc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhuc1ActionPerformed
        int row = table.getSelectedRow();
        if (row >= 0) {
            String username = table.getValueAt(row, 1).toString();
            String hashedPassword = BCrypt.hashpw(jTextField2.getText(), BCrypt.gensalt(10)); 
            TK_CON.restoreTaiKhoan(username, hashedPassword);
            fillTable(); // Cập nhật lại bảng sau khi khôi phục
            taiKhoanPage.loadTable(TK_CON.getAllListPhatDo()); // Làm mới bảng tài khoản
            MessageDialog.info(this, "Khôi phục tài khoản thành công!");
        } else {
            MessageDialog.error(this, "Vui lòng chọn tài khoản để khôi phục!");
        }
    }//GEN-LAST:event_btnKhoiPhuc1ActionPerformed
    
    // Hàm để lấy các bản ghi đã chọn
    private List<Integer> getSelectedRows() {
        int[] selectedRows = table.getSelectedRows();
        List<Integer> ids = new ArrayList<>();
        for (int row : selectedRows) {
            String idStr = (String) table.getValueAt(row, 0);
            ids.add(Integer.parseInt(idStr));
        }
        return ids;
    }
    
    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
        // TODO add your handling code here:
        List<Integer> ids = getSelectedRows();
        if (ids.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một bản ghi để xóa.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa các tài khoản đã chọn?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            TK_CON.deleteAccounts(ids);
            fillTable(); // Cập nhật lại bảng sau khi xóa
        }
    }//GEN-LAST:event_btnXoa1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckExpired;
    private javax.swing.JButton btnKhoiPhuc1;
    private javax.swing.JButton btnQuayLai;
    private javax.swing.JButton btnXoa1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTable table;
    private javax.swing.JPanel tableItemPanel;
    // End of variables declaration//GEN-END:variables
}
