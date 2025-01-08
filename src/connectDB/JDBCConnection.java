package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author atuandev
 */
public class JDBCConnection {

    static String url = "jdbc:sqlserver://localhost:1433;databasename=qlht;encrypt=true;trustServerCertificate=true;";
    static String user = "sa";
    static String password = "123456789";

    public static PreparedStatement getStmt(String sql, Object... args) throws Exception {
        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement stmt;
        if (sql.trim().startsWith("{")) {
            stmt = con.prepareCall(sql);
        } 
        else {
            stmt = con.prepareStatement(sql);
        }

        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        return stmt;
    }
    public static PreparedStatement getStmtWithUser(String sql,String username,String pass, Object... args) throws Exception {
        System.out.println(username);
        System.out.println(pass);
        String userToUse = username.equals("admin") ? user : username;
        String passwordToUse = username.equals("admin") ? password : pass;

        Connection con = DriverManager.getConnection(url, userToUse, passwordToUse);
        PreparedStatement stmt;
        if (sql.trim().startsWith("{")) {
            stmt = con.prepareCall(sql);
        } else {
            stmt = con.prepareStatement(sql);
        }

        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        return stmt;
    }
    public static int update(String sql, Object... args) {
        try {
            PreparedStatement stmt = JDBCConnection.getStmt(sql, args);
            try {
                return stmt.executeUpdate();
            } finally {
                stmt.getConnection().close();
            }
        } catch (Exception e) {
            if (e.getMessage().contains("Số điện thoại đã tồn tại")) {
                JOptionPane.showMessageDialog(
                null,
                "Số điện thoại đã tồn tại!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
            );              
            return 0;
        }
        if (e.getMessage().contains("Số điện thoại phải đủ 10 chữ số và chỉ chứa số!")) {
                JOptionPane.showMessageDialog(
                null,
                "Số điện thoại không được để trống và có 10 chữ số!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
            );
            
            return 0;
        }
            throw new RuntimeException(e);
        }
    }

    public static ResultSet query(String sql, Object... args) throws Exception {
        PreparedStatement stmt = JDBCConnection.getStmt(sql, args);
        return stmt.executeQuery();
    }
    public static ResultSet query(String sql) throws Exception {
        PreparedStatement stmt = JDBCConnection.getStmt(sql);
        return stmt.executeQuery();
    }
    public static ResultSet queryWithUser(String sql,String user,String pass, Object... args) throws Exception {
        System.out.println(user);
        PreparedStatement stmt = JDBCConnection.getStmtWithUser(sql,user,pass, args);
        return stmt.executeQuery();
    }
    public static void callProduce(String id) throws SQLException{
        Connection connection = null;
        CallableStatement callableStatement = null;
        connection = DriverManager.getConnection(url, user, password);
        String sql = "{CALL InThongTinNhanVien(?)}"; // Thủ tục với một tham số đầu vào
        callableStatement = connection.prepareCall(sql);
        callableStatement.setString(1, id);
        boolean hasResults = callableStatement.execute();
        System.out.println(hasResults);
    }
    public static DefaultTableModel selectAllLuong() throws Exception{
        String sql = "SELECT * FROM dbo.GetLuong()";
        PreparedStatement ps = JDBCConnection.getStmt(sql);
        ResultSet rs = ps.executeQuery();
        String[] header = {"STT", "Mã nhân viên", "Tên nhân viên", "Lương"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        int stt = 1; // Đếm số thứ tự
        while (rs.next()) {
            String maNV = rs.getString("Ma");
            String tenNV = rs.getString("HoTen");
            float luong = rs.getFloat("Luong");
            
            // Thêm dữ liệu vào model
            model.addRow(new Object[]{stt++, maNV, tenNV, luong});
        }
        return model;
    }
    public static String[] selectNameNV() throws Exception{
        String sql = "select * from ViewTenNVkLuong";
        PreparedStatement ps = JDBCConnection.getStmt(sql);
        ResultSet rs = ps.executeQuery();
        String[] names = new String[100];
        int index = 0;
        while (rs.next()) {
            names[index++] = rs.getString("hoTen");  // Lấy tên từ cột "hoTen"
        }
        return names;
    }
    public static String selectIDNV(String name) throws Exception{
        String sql = "select idNV from NhanVien where hoTen = ?";
        PreparedStatement ps = JDBCConnection.getStmt(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
        // Giả sử thủ tục trả về một cột duy nhất, lấy giá trị từ cột đó
        return rs.getString(1);  // Trả về giá trị từ cột đầu tiên
    }
        return null;
    }
    public static String selectPhuCap(String name) throws Exception{
        String sql = "exec GetPhuCap ?";
        PreparedStatement ps = JDBCConnection.getStmt(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
        // Giả sử thủ tục trả về một cột duy nhất, lấy giá trị từ cột đó
        return rs.getString(1);  // Trả về giá trị từ cột đầu tiên
    }
        return null;
    }
    public static String selectSoNgayCong(String id) throws Exception{
        String sql = "select SoNgayCong from BangLuong where idNV = ?";
        PreparedStatement ps = JDBCConnection.getStmt(sql);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
        // Giả sử thủ tục trả về một cột duy nhất, lấy giá trị từ cột đó
        return rs.getString(1);  // Trả về giá trị từ cột đầu tiên
    }
        return null;
    }
    public static String selectThuong(String name) throws Exception{
        String sql = "exec GetThuong ?";
        PreparedStatement ps = JDBCConnection.getStmt(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
        // Giả sử thủ tục trả về một cột duy nhất, lấy giá trị từ cột đó
        return rs.getString(1);  // Trả về giá trị từ cột đầu tiên
    }
        return null;
    }
    
    public static void insertInfoLuong(String idNV, float luongCoBan, int soNgayCong, float phuCap, float thuong) throws Exception{
        String sql = "insert into BangLuong(idNV,luongCoban,soNgayCong,phuCap,Thuong) values (?,?,?,?,?)";
        PreparedStatement ps = JDBCConnection.getStmt(sql);
        ps.setString(1, idNV);
        ps.setFloat(2, luongCoBan);
        ps.setInt(3, soNgayCong);
        ps.setFloat(4, phuCap);
        ps.setFloat(5, thuong);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Thêm thông tin lương thành công cho nhân viên: " + idNV);
        } else {
            System.out.println("Không thể thêm thông tin lương.");
        }
    }
    public static void updateInfoLuong(String idNV, float luongCoBan, int soNgayCong, float phuCap, float thuong) throws Exception {
    String sql = "UPDATE BangLuong SET luongCoban = ?, soNgayCong = ?, phuCap = ?, Thuong = ? WHERE idNV = ?";
    try (
        PreparedStatement ps = JDBCConnection.getStmt(sql)
    ) {
        // Gán các tham số vào câu lệnh SQL
        ps.setFloat(1, luongCoBan);
        ps.setInt(2, soNgayCong);
        ps.setFloat(3, phuCap);
        ps.setFloat(4, thuong);
        ps.setString(5, idNV);

        // Thực thi câu lệnh SQL
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Cập nhật thông tin lương thành công cho nhân viên: " + idNV);
        } else {
            System.out.println("Không thể cập nhật thông tin lương. Mã nhân viên không tồn tại: " + idNV);
        }
    }
}
}
