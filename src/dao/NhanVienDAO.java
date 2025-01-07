package dao;

import connectDB.JDBCConnection;
import entities.NhanVien;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class NhanVienDAO extends InterfaceDAO<NhanVien, String> {

    private final String INSERT_SQL = "INSERT INTO NhanVien values (?,?,?,?,?,?)";
    private final String UPDATE_SQL = "UPDATE NhanVien SET hoTen=?, sdt=?, gioiTinh=?, namSinh=?, ngayVaoLam=? where idNV=?";
    private final String DELETE_BY_ID = "DELETE from NhanVien where idNV = ?";

    private final String SELECT_ALL_SQL = "SELECT * FROM NhanVien ORDER BY ngayVaoLam";
    private final String SELECT_BY_ID = "SELECT * FROM NhanVien WHERE idNV = ?";
    private final String select_nv_6_month = "SELECT * FROM dbo.fn_NhanVien6ThangTroLen()";

    public void info_nv(String id) throws Exception{
        JDBCConnection.callProduce(id);
    }
    public void selectNv6Month() throws Exception{
        ResultSet rs = JDBCConnection.query(select_nv_6_month);
        StringBuilder result = new StringBuilder("Danh sách nhân viên đươc thưởng(6 tháng trở lên):\n\n");

            while (rs.next()) {
                String idNV = rs.getString("idNV");
                String hoTen = rs.getString("hoTen");
                String ngayVaoLam = rs.getString("ngayVaoLam");

                // Thêm thông tin vào chuỗi
                result.append("ID: ").append(idNV)
                      .append(", Họ Tên: ").append(hoTen)
                      .append(", Ngày Vào Làm: ").append(ngayVaoLam)
                      .append("\n");
            }

            // Hiển thị thông tin trong JOptionPane
            if (result.toString().equals("Danh sách nhân viên (6 tháng trở lên):\n\n")) {
                JOptionPane.showMessageDialog(null, "Không có nhân viên nào có ngày vào làm từ 6 tháng trở lên.", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, result.toString(), "Danh Sách Nhân Viên", JOptionPane.INFORMATION_MESSAGE);
            }
    }
    @Override
    public void create(NhanVien e) {
        JDBCConnection.update(INSERT_SQL, e.getId(), e.getHoTen(), e.getSdt(), e.getGioiTinh(), e.getNamSinh(), e.getNgayVaoLam());
    }

    @Override
    public void update(NhanVien e) {
        JDBCConnection.update(UPDATE_SQL, e.getHoTen(), e.getSdt(), e.getGioiTinh(), e.getNamSinh(), e.getNgayVaoLam(), e.getId());
    }

    @Override
    public void deleteById(String id) {
        JDBCConnection.update(DELETE_BY_ID, id);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> listE = new ArrayList<>();
        try {
            ResultSet rs = JDBCConnection.query(sql, args);
            while (rs.next()) {
                NhanVien e = new NhanVien();
                e.setId(rs.getString("idNV"));
                e.setHoTen(rs.getString("hoTen"));
                e.setSdt(rs.getString("sdt"));
                e.setGioiTinh(rs.getString("gioiTinh"));
                e.setNamSinh(rs.getInt("namSinh"));
                e.setNgayVaoLam(rs.getDate("ngayVaoLam"));
                listE.add(e);
            }
            rs.getStatement().getConnection().close();
            return listE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }
    //Phát ĐỖ
    public List<NhanVien> selectAllFromView() {
        String sql = "SELECT * FROM View_ThemTaiKhoan";
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCConnection.query(sql);
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setId(rs.getString("MaNhanVien"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setSdt(rs.getString("SoDienThoai"));
                nv.setNgayVaoLam(rs.getDate("NgayVaoLam"));
                list.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
