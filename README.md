# Quick run (Windows) — Chạy nhanh (Windows)

Mình đã thêm sẵn 3 script để chạy từng service trên Windows (CMD). Bạn có thể chạy trực tiếp trên máy local bằng cách mở `cmd.exe` hoặc double-click file `.cmd` trong File Explorer.

- [Run AuthService](./run-auth.cmd) — `run-auth.cmd`
- [Run InventoryService](./run-inventory.cmd) — `run-inventory.cmd`
- [Run API Gateway](./run-apigateway.cmd) — `run-apigateway.cmd`

Lưu ý quan trọng:
- Click vào liên kết trong GitHub hoặc IDE sẽ mở file để xem, nhưng không tự động chạy nó. Để chạy, mở `cmd.exe` (hoặc PowerShell) và chạy script cục bộ.
- Các script dùng Maven Wrapper (`mvnw.cmd`) có sẵn trong từng module; Docker không cần để chạy theo cách này.

Cách chạy (CMD):

```cmd
cd /d D:\QuanLiTapHoa
run-auth.cmd
```

Hoặc chạy service khác:

```cmd
cd /d D:\QuanLiTapHoa
run-inventory.cmd

cd /d D:\QuanLiTapHoa
run-apigateway.cmd
```

Cách chạy (PowerShell):

```powershell
Set-Location -Path 'D:\QuanLiTapHoa'
.\run-auth.cmd
```

Double-click: mở File Explorer, vào thư mục dự án, double-click `run-auth.cmd` → sẽ mở cửa sổ cmd và chạy service.

Nếu bạn muốn chạy nhiều service cùng lúc, mở nhiều cửa sổ cmd và chạy từng script trong mỗi cửa sổ.

---

# Ví dụ: chạy AuthService
cd `D:\QuanLiTapHoa\services\AuthService`
.\mvnw.cmd spring-boot:run

# Ví dụ: chạy InventoryService
cd `D:\QuanLiTapHoa\services\InventoryService`
.\mvnw.cmd spring-boot:run

# Ví dụ: chạy API Gateway
cd `D:\QuanLiTapHoa\services\APIGateWay`
.\mvnw.cmd spring-boot:run
# POS Microservices System

Hệ thống POS này được thiết kế theo kiến trúc **Microservices**, bao gồm các service chính và service mở rộng. Hệ thống tập trung vào quản lý bán hàng, kho hàng, người dùng và báo cáo.

---

## 1. Overview of Services

| STT | Tên Service         | Chức năng chính (Core Functions)                                        | Giai Đoạn |
|-----|---------------------|-------------------------------------------------------------------------|-----------|
| 1   | User & Auth Service | Đăng ký/Đăng nhập, Quản lý nhân viên cơ bản, Phân quyền (JWT)           | Cốt lõi   |
| 2   | Inventory Service   | Đăng ký/Tìm kiếm sản phẩm, Quản lý danh mục, Quản lý tồn kho (số lượng) | Cốt lõi   |
| 3   | Order Service       | Tạo hóa đơn bán hàng, Thanh toán, Lưu trữ chi tiết hóa đơn              | Cốt lõi   |
| 4   | API Gateway         | Định tuyến, Load Balancing, Xác thực JWT cơ bản                         | Cốt lõi   |
| 5   | Discovery Service   | Đăng ký và tìm kiếm Service (Eureka/Consul)                             | Cốt lõi   |
| 6   | Supplier Service    | Quản lý thông tin nhà cung cấp, Theo dõi đơn nhập cơ bản                | Mở rộng   |
| 7   | CRM Service         | Đăng ký khách hàng, Lưu lịch sử mua hàng, Phân loại khách hàng          | Mở rộng   |
| 8   | Payment Service     | Tích hợp cổng thanh toán (VNPAY...)                                     | Mở rộng   |
| 9   | Reporting Service   | Báo cáo cơ bản (Doanh thu, Tồn kho, Bán chạy)                           | Mở rộng   |
| 10  | Forecasting Service | AI Prophet - Dự báo nhu cầu nhập hàng                                   | Nâng cao  |

---

## 2. Kiến trúc hệ thống

- **Client**: Web/desktop/mobile app kết nối qua API Gateway.
- **API Gateway**:
    - Định tuyến request tới các service tương ứng.
    - Hỗ trợ Load Balancing.
    - Xác thực JWT cơ bản.
- **Discovery Service (Eureka/Consul)**: Quản lý đăng ký và tìm kiếm các microservice (tuỳ chọn, nếu deploy dynamic).
- **Core Services**:
    - User & Auth Service
    - Inventory Service
    - Order Service
- **Extended Services**:
    - Supplier, CRM, Payment, Reporting, Forecasting

---

## 3. Giai đoạn phát triển

1. **Cốt lõi (P1)**: triển khai các service quan trọng cho vận hành cơ bản.
2. **Mở rộng (P2)**: thêm các service hỗ trợ và nâng cao trải nghiệm người dùng.
3. **Nâng cao (P3)**: các tính năng AI/Forecasting và phân tích dữ liệu.

---

## 4. Công nghệ sử dụng

- **Backend**: Java + Spring Boot + Spring Cloud
- **Database**: SQL Server / MongoDB (tuỳ service)
- **Gateway**: Spring Cloud Gateway
- **Security**: JWT authentication
- **Containerization**: Docker, Kubernetes (tuỳ triển khai)

---

## 5. Hướng dẫn cài đặt nhanh

1. Clone repository về máy:
  ## Quick Run (Windows)
  
  [![Run AuthService](https://img.shields.io/badge/Run-AuthService-blue?logo=windows)](./services/AuthService) [![Run InventoryService](https://img.shields.io/badge/Run-InventoryService-blue?logo=windows)](./services/InventoryService) [![Run API Gateway](https://img.shields.io/badge/Run-APIGateWay-blue?logo=windows)](./services/APIGateWay)
  
  Click a badge to open the service folder on GitHub. To run locally in CMD / PowerShell, use the commands below:
  
      cd `D:\QuanLiTapHoa\services\AuthService`
      .\mvnw.cmd spring-boot:run
  
      cd `D:\QuanLiTapHoa\services\InventoryService`
      .\mvnw.cmd spring-boot:run
  
      cd `D:\QuanLiTapHoa\services\APIGateWay`
      .\mvnw.cmd spring-boot:run ```bash
   git clone <repo-url>
````

2. Khởi chạy các service theo thứ tự:

    1. Database
    2. Discovery Service (nếu dùng)
    3. User & Auth Service
    4. Inventory Service
    5. Order Service
    6. API Gateway
3. Kiểm tra endpoint:

    * `http://localhost:8080/auth/...` → AuthService
    * `http://localhost:8080/inventory/...` → InventoryService
````
