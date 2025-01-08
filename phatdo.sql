use qlht
go
----------------------------------------
CREATE TABLE LichSuXoa (
    id INT IDENTITY(1,1) PRIMARY KEY, -- ID tự tăng
    username NVARCHAR(50) NOT NULL,   -- Tên tài khoản bị xóa
    idNV nvarchar(10) NOT NULL,                -- ID nhân viên
    idVT nvarchar(10) NOT NULL,                -- ID vai trò
    deletedBy NVARCHAR(50) NOT NULL,  -- Tên người đã xóa tài khoản
    deleteTime DATETIME NOT NULL      -- Thời gian xóa
);
go
-----------------------------------------
--View
---Tạo View cho page Tài khoản
CREATE VIEW View_TaiKhoan
AS
SELECT 
	tk.idTK,
    tk.username,
    tk.password,
    nv.hoTen,
    vt.ten AS tenVT
FROM 
    TaiKhoan tk
INNER JOIN 
    NhanVien nv ON tk.idNV = nv.idNV
INNER JOIN 
    VaiTro vt ON tk.idVT = vt.idVT;
go
select * from View_TaiKhoan
drop view View_TaiKhoan
go

---Tạo view cho page thêm tài khoản
CREATE VIEW View_ThemTaiKhoan
AS
SELECT 
    ROW_NUMBER() OVER (ORDER BY idNV) AS STT,
    idNV AS MaNhanVien,
    hoTen AS HoTen,
    sdt AS SoDienThoai,
    ngayVaoLam AS NgayVaoLam
FROM NhanVien;
go
select * from View_ThemTaiKhoan

-- Tạo vai trò NVBH (Nhân viên bán hàng)
CREATE ROLE nvbh;
GRANT SELECT, INSERT, DELETE ON HoaDon TO nvbh;
GRANT SELECT, INSERT, DELETE, UPDATE ON KhachHang TO nvbh;
GRANT SELECT, INSERT, DELETE, UPDATE ON Thuoc TO nvbh;
GRANT SELECT, INSERT, DELETE, UPDATE ON ChiTietHoaDon TO nvbh;
GRANT SELECT ON TaiKhoan TO nvbh;
GRANT SELECT ON VaiTro TO nvbh;
GRANT SELECT ON NhanVien TO nvbh;
GRANT SELECT ON XuatXu TO nvbh;
GRANT SELECT ON DanhMuc TO nvbh;
GRANT SELECT ON DonViTinh TO nvbh;

-- Tạo vai trò NVSP (Nhân viên sản phẩm)
CREATE ROLE nvsp;
GRANT SELECT, INSERT, UPDATE, DELETE ON Thuoc TO nvsp;
GRANT SELECT, INSERT, UPDATE, DELETE ON PhieuNhap TO nvsp;
GRANT SELECT, INSERT, UPDATE, DELETE ON [dbo].[NhaCungCap] TO nvsp;
GRANT SELECT, INSERT, UPDATE, DELETE ON DanhMuc TO nvsp;
GRANT SELECT, INSERT, UPDATE, DELETE ON DonViTinh TO nvsp;
GRANT SELECT, INSERT, UPDATE, DELETE ON XuatXu TO nvsp;
GRANT SELECT, INSERT, DELETE ON ChiTietPhieuNhap TO nvsp;
GRANT SELECT ON HoaDon TO nvsp;
GRANT SELECT ON ChiTietHoaDon TO nvsp;
GRANT SELECT ON TaiKhoan TO nvsp;
GRANT SELECT ON VaiTro TO nvsp;
GRANT SELECT ON NhanVien TO nvsp;
GRANT SELECT ON KhachHang TO nvsp;

drop role nvbh
drop role nvsp
go

-- Funtion
--- Funtion tính toán thời gian chờ
CREATE FUNCTION CalculateWaitingTime(@deleteTime DATETIME)
RETURNS INT
AS
BEGIN
    RETURN DATEDIFF(DAY, @deleteTime, GETDATE());
END
go
--- Function trả về 1 bảng các tài khoản quá hạn 30 ngày 
CREATE FUNCTION GetExpiredAccounts()
RETURNS TABLE
AS
RETURN
(
    SELECT *
    FROM LichSuXoa
    WHERE dbo.CalculateWaitingTime(deleteTime) > 30
);
go

--Procedure
---Procedure xóa người dùng và tài khoản khi tài khoản bị xóa trong bảng
CREATE PROCEDURE DeleteLoginAndUser
    @username NVARCHAR(50)
AS
BEGIN
    DECLARE @sql NVARCHAR(MAX);

    -- Kiểm tra và xóa người dùng trong cơ sở dữ liệu
    IF EXISTS (SELECT 1 FROM sys.database_principals WHERE name = @username)
    BEGIN
        SET @sql = 'DROP USER [' + @username + '];';
        EXEC sp_executesql @sql;
    END

    -- Kiểm tra và xóa tài khoản đăng nhập trong SQL Server
    IF EXISTS (SELECT 1 FROM sys.server_principals WHERE name = @username)
    BEGIN
        SET @sql = 'DROP LOGIN [' + @username + '];';
        EXEC sp_executesql @sql;
    END
END
go

---Procedure cập nhật vai trò cho 1 tài khoản
CREATE PROCEDURE UpdateUserRole
    @username NVARCHAR(50),
    @newRole NVARCHAR(50)
AS
BEGIN
    -- Cập nhật vai trò trong bảng TaiKhoan
    UPDATE TaiKhoan
    SET idVT = (SELECT idVT FROM VaiTro WHERE ten = @newRole)
    WHERE username = @username;

    -- Khai báo con trỏ để duyệt qua tất cả vai trò trong bảng VaiTro
    DECLARE @roleId NVARCHAR(50);
    DECLARE role_cursor CURSOR FOR
    SELECT idVT FROM VaiTro;

    OPEN role_cursor;
    FETCH NEXT FROM role_cursor INTO @roleId;

    -- Lặp qua tất cả vai trò và xóa người dùng khỏi từng vai trò
    WHILE @@FETCH_STATUS = 0
    BEGIN
        -- Xóa vai trò hiện tại của người dùng
        DECLARE @sql NVARCHAR(MAX);
        SET @sql = 'ALTER ROLE [' + @roleId + '] DROP MEMBER [' + @username + '];';
        EXEC sp_executesql @sql;

        FETCH NEXT FROM role_cursor INTO @roleId;
    END

    CLOSE role_cursor;
    DEALLOCATE role_cursor;

    -- Gán vai trò mới cho người dùng
    DECLARE @newRoleId NVARCHAR(50);
    SET @newRoleId = (SELECT idVT FROM VaiTro WHERE ten = @newRole);

    SET @sql = 'ALTER ROLE [' + @newRoleId + '] ADD MEMBER [' + @username + '];';
    EXEC sp_executesql @sql;

END
GO
drop proc UpdateUserRole
go
SELECT name FROM sys.database_principals WHERE type = 'R';
go

--- Procedure xóa tài khoản quá hạn 30 ngày
CREATE PROCEDURE DeleteExpiredAccounts
AS
BEGIN
    DECLARE @id INT;
    DECLARE expiredAccounts CURSOR FOR
    SELECT id FROM dbo.GetExpiredAccounts();

    OPEN expiredAccounts;
    FETCH NEXT FROM expiredAccounts INTO @id;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        DELETE FROM LichSuXoa WHERE id = @id;
        FETCH NEXT FROM expiredAccounts INTO @id;
    END

    CLOSE expiredAccounts;
    DEALLOCATE expiredAccounts;
END
go

--- Procedure xóa các tài khoản được chọn
CREATE PROCEDURE DeleteAccounts
    @Ids NVARCHAR(MAX)
AS
BEGIN
    DECLARE @Id INT
    DECLARE @Cursor CURSOR

    SET @Cursor = CURSOR FOR
    SELECT value FROM STRING_SPLIT(@Ids, ',')

    OPEN @Cursor
    FETCH NEXT FROM @Cursor INTO @Id

    WHILE @@FETCH_STATUS = 0
    BEGIN
        DELETE FROM LichSuXoa WHERE id = @Id
        FETCH NEXT FROM @Cursor INTO @Id
    END

    CLOSE @Cursor
    DEALLOCATE @

END
go
--Trigger

--- Trigger tự động tạo TK và gán quyền
CREATE TRIGGER trg_CreateLoginAfterInsert
ON TaiKhoan
AFTER INSERT
AS
BEGIN
    DECLARE @username NVARCHAR(50);
    DECLARE @password NVARCHAR(50);
    DECLARE @role NVARCHAR(50);

    SELECT @username = username, @password = password, @role = idVT
    FROM inserted;

    -- Tạo tài khoản đăng nhập trong SQL Server
    DECLARE @sql NVARCHAR(MAX);
    SET @sql = 'CREATE LOGIN [' + @username + '] WITH PASSWORD = ''' + @password + ''';';
	print @password;
    EXEC sp_executesql @sql;

    -- Tạo người dùng trong cơ sở dữ liệu
    SET @sql = 'CREATE USER [' + @username + '] FOR LOGIN [' + @username + '];';
    EXEC sp_executesql @sql;

    -- Gán quyền cho người dùng dựa trên vai trò
    IF @role = 'nvbh'
    BEGIN
        SET @sql = 'ALTER ROLE [nvbh] ADD MEMBER [' + @username + '];';
    END
    ELSE IF @role = 'nvsp'
    BEGIN
        SET @sql = 'ALTER ROLE [nvsp] ADD MEMBER [' + @username + '];';
    END

    EXEC sp_executesql @sql;
END
drop trigger trg_CreateLoginAfterInsert
go

---Trigger ghi lại lịch sử tài khoản bị xóa
CREATE TRIGGER trg_InsertDeletedAccounts
ON TaiKhoan
AFTER DELETE
AS
BEGIN
    INSERT INTO LichSuXoa (username, idNV, idVT, deletedBy, deleteTime)
    SELECT username, idNV, idVT, SYSTEM_USER, GETDATE()
    FROM deleted;
END
go
drop trigger trg_InsertDeletedAccounts
select * from LichSuXoa

go
