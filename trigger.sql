--trigger kiem tra so dien thoai co trung
CREATE TRIGGER trg_after_insert_nhanvien
ON NhanVien
AFTER INSERT,UPDATE
AS
BEGIN
    -- Kiểm tra trùng số điện thoại
    IF EXISTS (
        SELECT 1
        FROM inserted i
        INNER JOIN NhanVien n ON n.sdt = i.sdt
        WHERE i.idNV != n.idNV -- So sánh tránh chính bản ghi mới
    )
    BEGIN
        RAISERROR('Số điện thoại đã tồn tại!', 16, 1);
        ROLLBACK TRANSACTION;
        RETURN;
    END;
END;

insert into NhanVien values ('123','asdasda','0967566712','Nam',2003,'2026-01-06')
delete from NhanVien where idNV = '123'
select * from NhanVien

drop trigger trg_after_insert_nhanvien

--trigger kiem tra do dai sdt
CREATE TRIGGER trg_check_phone_length
ON NhanVien
AFTER INSERT, UPDATE
AS
BEGIN
    -- Kiểm tra số điện thoại không đủ 10 chữ số
    IF EXISTS (
        SELECT 1
        FROM inserted
        WHERE LEN(sdt) <> 10 OR sdt LIKE '%[^0-9]%'
    )
    BEGIN
        RAISERROR('Số điện thoại phải đủ 10 chữ số và chỉ chứa số!', 16, 1);
        ROLLBACK TRANSACTION;
        RETURN;
    END;
END;
drop trigger trg_check_phone_length

insert into NhanVien values ('123','abc','0967667120','Nam',2003,'2026-01-06')
delete from NhanVien where idNV = '123'
select * from NhanVien

--func check họ tên 

CREATE FUNCTION dbo.fn_ValidateHoTen (
    @hoTen NVARCHAR(255)
)
RETURNS NVARCHAR(255)
AS
BEGIN
    -- Kiểm tra họ tên không được để trống
    IF (@hoTen IS NULL OR LTRIM(RTRIM(@hoTen)) = '')
        RETURN 'Tên nhân viên không được để trống!'
    
    -- Trả về NULL nếu hợp lệ
    RETURN NULL
END;

select * from NhanVien
drop function fn_ValidateHoTen
select * from TaiKhoan

--trigger check ngay vao lam 

CREATE TRIGGER trg_CheckNgayLamViec
ON NhanVien
AFTER INSERT
AS
BEGIN
    -- Kiểm tra ngày vào làm trong bảng inserted
    IF EXISTS (
        SELECT 1
        FROM inserted
        WHERE ngayVaoLam < CAST(GETDATE() AS DATE)
    )
    BEGIN
        -- Nếu có lỗi, báo lỗi và hủy giao dịch
        RAISERROR ('Ngày vào làm không được nhỏ hơn ngày hôm nay!', 16, 1);
        ROLLBACK TRANSACTION;
        RETURN;
    END;
END;
DROP TRIGGER trg_CheckNgayLamViec
insert into NhanVien values ('123','abc','0967667120','Nam',2003,'2024-01-06')
delete from NhanVien where idNV = '123'
select * FROM NhanVien

-- tao view NhanVien(idNV, hoTen, ngayVaoLam)

CREATE VIEW vw_NhanVien
AS
SELECT idNV, hoTen, ngayVaoLam
FROM NhanVien;

--viet ham de lay NhanVien co ngay vao lam tu 6 thang tro len 

CREATE FUNCTION dbo.fn_NhanVien6ThangTroLen ()
RETURNS TABLE
AS
RETURN
(
    SELECT idNV, hoTen, ngayVaoLam
    FROM vw_NhanVien
    WHERE ngayVaoLam <= DATEADD(MONTH, -6, GETDATE())
);
SELECT * FROM dbo.fn_NhanVien6ThangTroLen();

--datepart: Phần của ngày bạn muốn cộng hoặc trừ. Ví dụ: YEAR, MONTH, DAY, HOUR, MINUTE, SECOND,...
--number: Số lượng mà bạn muốn cộng hoặc trừ. Nếu là số dương, bạn sẽ cộng thêm thời gian, nếu là số âm, bạn sẽ trừ đi thời gian.
--date: Giá trị ngày bạn muốn thực hiện phép toán trên đó.


-- tao view voi cac truong day du 
CREATE VIEW ViewNhanVien AS
SELECT
    idNV,
    hoTen,
    sdt,
    gioiTinh,
    namSinh,
    ngayVaoLam
FROM NhanVien;

ALTER PROCEDURE InThongTinNhanVien
    @idNV NVARCHAR(10)
AS
BEGIN
    DECLARE @hoTen NVARCHAR(255),
            @sdt NVARCHAR(10),
            @gioiTinh NVARCHAR(10),
            @namSinh INT,
            @ngayVaoLam DATE;
    
    -- Khai báo con trỏ để duyệt qua các nhân viên có idNV trùng khớp
    DECLARE nhanVien_cursor CURSOR FOR
        SELECT hoTen, sdt, gioiTinh, namSinh, ngayVaoLam
        FROM NhanVien
        WHERE idNV = @idNV;

    -- Mở con trỏ
    OPEN nhanVien_cursor;

    -- Lấy dữ liệu từ con trỏ vào các biến
    FETCH NEXT FROM nhanVien_cursor INTO @hoTen, @sdt, @gioiTinh, @namSinh, @ngayVaoLam;

    -- Kiểm tra xem có dữ liệu hay không
    IF @@FETCH_STATUS = 0
    BEGIN
        -- In thông tin của nhân viên
        PRINT N'Thông tin nhân viên:';
        PRINT N'Họ tên: ' + @hoTen;
        PRINT N'Số điện thoại: ' + @sdt;
        PRINT N'Giới tính: ' + @gioiTinh;
        PRINT N'Năm sinh: ' + CAST(@namSinh AS NVARCHAR(4));
        PRINT N'Ngày vào làm: ' + CAST(@ngayVaoLam AS NVARCHAR(10));
    END
    ELSE
    BEGIN
        PRINT 'Không tìm thấy nhân viên với mã ID đã cho!';
    END

    -- Đóng con trỏ
    CLOSE nhanVien_cursor;
    DEALLOCATE nhanVien_cursor;
END;
select * from NhanVien
EXEC InThongTinNhanVien @idNV = 'ADMIN';
