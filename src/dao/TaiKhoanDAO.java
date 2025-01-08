package dao;

import connectDB.JDBCConnection;
import entities.NhanVien;
import entities.TaiKhoan;
import entities.VaiTro;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.RandomGenerator;

public class TaiKhoanDAO extends InterfaceDAO<TaiKhoan, String> {

    private final String INSERT_SQL = "INSERT INTO TaiKhoan values (?,?,?,?,?)";
    private final String UPDATE_SQL = "UPDATE TaiKhoan SET username=?, password=?, idNV=?, idVT=? where idTK=?";
    private final String DELETE_BY_ID = "DELETE from TaiKhoan where idTK = ?";

    private final String SELECT_ALL_SQL
            = "SELECT tk.*, nv.hoTen, nv.sdt, nv.gioiTinh, nv.namSinh, nv.ngayVaoLam, vt.ten as tenVT "
            + "FROM TaiKhoan tk "
            + "INNER JOIN NhanVien nv ON tk.idNV = nv.idNV "
            + "INNER JOIN VaiTro vt ON tk.idVT = vt.idVT";
    
    private final String SELECT_BY_ID = "SELECT tk.*, nv.hoTen, nv.sdt, nv.gioiTinh, nv.namSinh, nv.ngayVaoLam, vt.ten as tenVT "
            + "FROM TaiKhoan tk "
            + "INNER JOIN NhanVien nv ON tk.idNV = nv.idNV "
            + "INNER JOIN VaiTro vt ON tk.idVT = vt.idVT "
            + "WHERE tk.idTK = ?";
    
    private final String SELECT_BY_USERNAME = "SELECT tk.*, nv.hoTen, nv.sdt, nv.gioiTinh, nv.namSinh, nv.ngayVaoLam, vt.ten as tenVT "
            + "FROM TaiKhoan tk "
            + "INNER JOIN NhanVien nv ON tk.idNV = nv.idNV "
            + "INNER JOIN VaiTro vt ON tk.idVT = vt.idVT "
            + "WHERE tk.username = ?";
    
    @Override
    public void create(TaiKhoan e) {
        JDBCConnection.update(INSERT_SQL, e.getId(), e.getUsername(), e.getPassword(), e.getNhanVien().getId(), e.getVaiTro().getId());
    }

    @Override
    public void update(TaiKhoan e) {
        JDBCConnection.update(UPDATE_SQL, e.getUsername(), e.getPassword(), e.getNhanVien().getId(), e.getVaiTro().getId(), e.getId());
    }

    @Override
    public void deleteById(String id) {
        JDBCConnection.update(DELETE_BY_ID, id);
    }
    public List<TaiKhoan> selectBySqlPhatDO(String sql, Object... args) {
    List<TaiKhoan> list = new ArrayList<>();
    try {
        ResultSet rs = JDBCConnection.query(sql, args);
        while (rs.next()) {
            TaiKhoan entity = new TaiKhoan();
            entity.setId(rs.getString("idTK"));
            entity.setUsername(rs.getString("username"));
            entity.setPassword(rs.getString("password"));
            NhanVien nv = new NhanVien();
            nv.setHoTen(rs.getString("hoTen"));
            entity.setNhanVien(nv);
            VaiTro vt = new VaiTro();
            vt.setTen(rs.getString("tenVT"));
            entity.setVaiTro(vt);
            list.add(entity);
        }
        rs.getStatement().getConnection().close();
    } catch (Exception ex) {
        throw new RuntimeException(ex);
    }
    return list;
}
    //Phát Đỗ

    public List<List<Object>> getDeletedAccounts() {
        String sql = "SELECT * FROM LichSuXoa";
        List<List<Object>> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCConnection.query(sql);
            while (rs.next()) {
                List<Object> row = new ArrayList<>();
                row.add(rs.getString("id"));
                row.add(rs.getString("username"));
                row.add(rs.getString("idNV"));
                row.add(rs.getString("idVT"));
                row.add(rs.getString("deletedBy"));
                row.add(rs.getTimestamp("deleteTime"));
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void restoreTaiKhoan(String username, String password) throws Exception {
        // Sinh mã ngẫu nhiên cho tài khoản
        String newIdTK = RandomGenerator.getRandomId();

        String sql = "INSERT INTO TaiKhoan (idTK, username, password, idNV, idVT) "
                   + "SELECT ?, username, ?, idNV, idVT FROM LichSuXoa WHERE username = ?";
        JDBCConnection.update(sql, newIdTK, password, username);

        // Xóa tài khoản khỏi bảng LichSuXoa sau khi khôi phục
        sql = "DELETE FROM LichSuXoa WHERE username = ?";
        JDBCConnection.update(sql, username);
    }
    
    public void deleteLoginAndUser(String username) {
        String sql = "{CALL DeleteLoginAndUser(?)}";
        JDBCConnection.update(sql, username);
    }
    public void updateUserRole(String username, String newRole) {
        String sql = "{CALL UpdateUserRole(?, ?)}";
        JDBCConnection.update(sql, username, newRole);
    }
    public List<List<Object>> getExpiredAccounts() {
        String sql = "SELECT * FROM dbo.GetExpiredAccounts()";
        List<List<Object>> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCConnection.query(sql);
            while (rs.next()) {
                List<Object> row = new ArrayList<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("username"));
                row.add(rs.getString("idNV"));
                row.add(rs.getString("idVT"));
                row.add(rs.getString("deletedBy"));
                row.add(rs.getTimestamp("deleteTime"));
                list.add(row);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteExpiredAccounts() {
        String sql = "{CALL DeleteExpiredAccounts()}";
        try {
            JDBCConnection.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteAccounts(List<Integer> ids) {
        String idsString = ids.toString().replaceAll("[\\[\\] ]", ""); // Chuyển danh sách ID thành chuỗi
        String sql = "{CALL DeleteAccounts(?)}";
        try {
            JDBCConnection.update(sql, idsString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected List<TaiKhoan> selectBySql(String sql, Object... args) {
        List<TaiKhoan> listE = new ArrayList<>();
        try {
            ResultSet rs = JDBCConnection.query(sql, args);
            while (rs.next()) {
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setId(rs.getString("idTK"));
                taiKhoan.setUsername(rs.getString("username"));
                taiKhoan.setPassword(rs.getString("password"));

                // Create NhanVien object
                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(rs.getString("idNV"));
                nhanVien.setHoTen(rs.getString("hoTen"));
                nhanVien.setSdt(rs.getString("sdt"));
                nhanVien.setGioiTinh(rs.getString("gioiTinh"));
                nhanVien.setNamSinh(rs.getInt("namSinh"));
                nhanVien.setNgayVaoLam(rs.getDate("ngayVaoLam"));
                taiKhoan.setNhanVien(nhanVien);

                // Create VaiTro object
                VaiTro vaiTro = new VaiTro();
                vaiTro.setId(rs.getString("idVT"));
                vaiTro.setTen(rs.getString("tenVT"));
                taiKhoan.setVaiTro(vaiTro);

                listE.add(taiKhoan);
            }
            rs.getStatement().getConnection().close();
            return listE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    protected List<TaiKhoan> selectBySqlWithUser(String sql,String user,String pass, Object... args) {
        System.out.println(user);
        List<TaiKhoan> listE = new ArrayList<>();
        try {
            ResultSet rs = JDBCConnection.queryWithUser(sql,user,pass, args);
            while (rs.next()) {
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setId(rs.getString("idTK"));
                taiKhoan.setUsername(rs.getString("username"));
                taiKhoan.setPassword(rs.getString("password"));

                // Create NhanVien object
                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(rs.getString("idNV"));
                nhanVien.setHoTen(rs.getString("hoTen"));
                nhanVien.setSdt(rs.getString("sdt"));
                nhanVien.setGioiTinh(rs.getString("gioiTinh"));
                nhanVien.setNamSinh(rs.getInt("namSinh"));
                nhanVien.setNgayVaoLam(rs.getDate("ngayVaoLam"));
                taiKhoan.setNhanVien(nhanVien);

                // Create VaiTro object
                VaiTro vaiTro = new VaiTro();
                vaiTro.setId(rs.getString("idVT"));
                vaiTro.setTen(rs.getString("tenVT"));
                taiKhoan.setVaiTro(vaiTro);

                listE.add(taiKhoan);
            }
            rs.getStatement().getConnection().close();
            return listE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<TaiKhoan> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }
    public List<TaiKhoan> selectAllPhatDo() {
        String sql = "SELECT * FROM View_TaiKhoan";
        return this.selectBySqlPhatDO(sql);
    }
    @Override
    public TaiKhoan selectById(String id) {
        List<TaiKhoan> list = selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public TaiKhoan selectByUsername(String username,String pass) {
        List<TaiKhoan> list = selectBySqlWithUser(SELECT_BY_USERNAME,username,pass, username);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
