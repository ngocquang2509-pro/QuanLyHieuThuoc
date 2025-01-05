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

insert into NhanVien values ('abc','123123123','0111111912','Nam',2003,'2026-01-06')
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

insert into NhanVien values ('123','abc','0967667128','Nam',2003,'2026-01-06')
delete from NhanVien where idNV = '123'
select * from NhanVien




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


CREATE TABLE BangLuong (
    idNV NVARCHAR(10) NOT NULL,
    luongCoban float NOT NULL default 15000000,    
    soNgayCong INT NOT NULL,         
    phuCap float DEFAULT 0,       
    thuong float DEFAULT 0,       
    luongThucTe float ,                           
    PRIMARY KEY (idNV),
    FOREIGN KEY (idNV) REFERENCES NhanVien(idNV)  
);
drop table BangLuong
INSERT INTO BangLuong (idNV, soNgayCong, phuCap, thuong) 
VALUES
('ADMIN', 22, 2000000, 1000000),
('DKJFJO1K2', 20, 1500000, 800000),
('IU42JDKJ2', 23, 1800000, 1200000),
('LKD2SFSL1', 21, 1600000, 700000),
('QJH5YD6WM', 25, 2200000, 1500000);

select NhanVien.idNV,hoTen,luongThucTe from BangLuong,NhanVien where BangLuong.idNV = NhanVien.idNV 
UPDATE BangLuong
SET 
    luongThucTe = (luongCoban / 30) * soNgayCong + phuCap + thuong;


-- viet view luong cua cac nhan vien va tao ham 
CREATE VIEW v_Luong AS
SELECT NhanVien.idNV AS Ma, 
       NhanVien.hoTen AS HoTen,
       BangLuong.luongThucTe AS Luong
FROM NhanVien
INNER JOIN BangLuong ON NhanVien.idNV = BangLuong.idNV;

CREATE FUNCTION GetLuong()
RETURNS TABLE
AS
RETURN
(
    SELECT Ma, HoTen, Luong
    FROM v_Luong
);
SELECT * FROM dbo.GetLuong();

-- tao view lay ten nhung nhan vien chua co luong 

CREATE VIEW ViewTenNVkLuong AS
SELECT NhanVien.hoTen
FROM NhanVien
LEFT JOIN BangLuong ON NhanVien.idNV = BangLuong.idNV
WHERE BangLuong.idNV IS NULL;

select * from ViewTenNVkLuong


-- viet thu tuc de lay phu cap cua luong nhan vien neu ngayvaolam > 2 thang thi phu cap 2 tr < 2 thang phu cap 1 trieu 
ALTER PROCEDURE GetPhuCap
    @tenNV NVARCHAR(10)
AS
BEGIN
    DECLARE @ngayVaoLam DATE;
    DECLARE @phuCap FLOAT;

    -- Lấy ngày vào làm của nhân viên từ bảng NhanVien
    SELECT @ngayVaoLam = ngayVaoLam
    FROM NhanVien
    WHERE hoTen = @tenNV;

    -- Kiểm tra điều kiện ngày vào làm để tính phuCap
    IF DATEDIFF(MONTH, @ngayVaoLam, GETDATE()) >= 2
    BEGIN
        SET @phuCap = 2000000;  -- Phụ cấp 2 triệu nếu làm việc trên 2 tháng
    END
    ELSE
    BEGIN
        SET @phuCap = 1000000;  -- Phụ cấp 1 triệu nếu làm việc dưới 2 tháng
    END

    -- Trả về giá trị phụ cấp
    SELECT @phuCap AS PhuCap;
END;

exec GetPhuCap N'Admin'

select * from NhanVien

-- viet thu tuc de lay thuong cua nhan vien neu ngay vao lam tu 6 thang tro len thi duoc thuong 5 trieu , nguoc lai thuong 3 tr 
CREATE PROCEDURE GetThuong
    @tenNV NVARCHAR(10)
AS
BEGIN
    -- Tính số tháng từ ngày vào làm đến hiện tại
    DECLARE @ngayVaoLam Date;
	DECLARE @thuong FLOAT;

    SELECT @ngayVaoLam = ngayVaoLam
    FROM NhanVien
    WHERE hoTen = @tenNV;

    -- Kiểm tra điều kiện ngày vào làm để tính phuCap
    IF DATEDIFF(MONTH, @ngayVaoLam, GETDATE()) >= 2
    BEGIN
        SET @thuong = 5000000;  -- Phụ cấp 2 triệu nếu làm việc trên 2 tháng
    END
    ELSE
    BEGIN
        SET @thuong = 3000000;  -- Phụ cấp 1 triệu nếu làm việc dưới 2 tháng
    END

    -- Trả về giá trị phụ cấp
    SELECT @thuong AS Thuong;
END;

exec GetThuong 'Admin'

-- viet trigger khi them thong tin luong thi Luong se cap nhat

CREATE TRIGGER trg_CapNhatLuong
ON BangLuong
AFTER INSERT, UPDATE
AS
BEGIN
    -- Cập nhật LuongThucTe dựa trên các giá trị mới thêm hoặc sửa
    UPDATE BangLuong
    SET LuongThucTe = (BangLuong.LuongCoBan / 30 * BangLuong.SoNgayCong) + BangLuong.PhuCap + BangLuong.Thuong
    FROM BangLuong
    INNER JOIN inserted ON BangLuong.idNV = inserted.idNV;
END;


insert into bangLuong(idNV,luongCoban,soNgayCong,phuCap,Thuong) values ('admin',20000,22,2000,20000);
update BangLuong set soNgayCong = 10 where idNV = '123'
SELECT * FROM dbo.GetLuong()

--viet trigger xoa nhan vien thi xoa luon Luong cua nhan vien (su dung con tro )

CREATE TRIGGER trg_DeleteNhanVien
ON NhanVien
AFTER DELETE
AS
BEGIN
    -- Khai báo con trỏ
    DECLARE @idNV NVARCHAR(10);

    -- Khởi tạo con trỏ để duyệt qua các bản ghi trong bảng DELETED
    DECLARE cur CURSOR FOR
    SELECT idNV FROM DELETED;

    -- Mở con trỏ
    OPEN cur;

    -- Lấy từng mã nhân viên trong bảng DELETED
    FETCH NEXT FROM cur INTO @idNV;

    -- Xóa thông tin lương tương ứng trong BangLuong
    WHILE @@FETCH_STATUS = 0
    BEGIN
        DELETE FROM BangLuong WHERE idNV = @idNV;
        FETCH NEXT FROM cur INTO @idNV;
    END;

    -- Đóng và giải phóng con trỏ
    CLOSE cur;
    DEALLOCATE cur;
END;
drop trigger trg_DeleteNhanVien
delete from NhanVien where idNV = '123'


