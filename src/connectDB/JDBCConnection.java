package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import javax.swing.JOptionPane;

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
        if(username.equals("phat")){
            user = username;
            password = pass;
            System.out.println("connectDB.JDBCConnection.getStmtWithUser()");
        }
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
        if (e.getMessage().contains("Ngày vào làm không được nhỏ hơn ngày hôm nay!")) {
                JOptionPane.showMessageDialog(
                null,
                "Ngày vào làm không được nhỏ hơn ngày hôm nay!",
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
}
