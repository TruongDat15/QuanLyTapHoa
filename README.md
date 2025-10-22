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

Tiếp theo — cách dùng, lệnh và mẹo debug (CMD trên Windows)
Build & chạy toàn bộ bằng Docker Compose Mở cmd.exe, vào thư mục dự án rồi chạy:
cd /d D:\QuanLiTapHoa
docker-compose up --build
Chạy ở chế độ background (detached):
docker-compose up --build -d
Xem trạng thái container / logs / dừng
Kiểm tra container đang chạy:
docker ps
docker-compose ps
Xem logs (theo service):
docker-compose logs --follow apigateway
docker-compose logs --follow inventoryservice
Dừng và xóa container/network:
docker-compose down
Nếu cần force remove volumes:
docker-compose down -v
Nếu một container exit ngay sau khi khởi
Xem logs chi tiết:
docker logs <container_name_or_id>
Ví dụ:
docker logs apigateway
Nếu build thất bại: bật verbose build:
docker-compose build --no-cache --progress=plain apigateway
Lưu ý cấu hình DB / environment
Trong docker-compose.yml mình đặt:
MYSQL_DATABASE=appdb
SPRING_DATASOURCE_URL cho services trỏ đến jdbc:mysql://mysql-db:3306/appdb?useSSL=false
Nếu application.yml của service có tên DB/credential khác, bạn cần chỉnh environment tương ứng hoặc thay đổi application.yml.
Trong Docker network, hostname của MySQL là mysql-db (không dùng localhost).
Mẹo khi build lâu / bị lỗi Maven
Lần đầu Docker build sẽ chạy Maven và tải dependencies — có thể mất thời gian.
Nếu build trong container báo lỗi do quyền mvnw không thực thi, các Dockerfile đã đã gọi chmod +x ./mvnw.
Nếu project cần profile hoặc biến môi trường (ví dụ -Pprod hoặc SPRING_PROFILES_ACTIVE), thêm ARG/ENV/command/environment trong docker-compose.yml hoặc điều chỉnh Dockerfile.
Kiểm tra port conflict trên máy host
Nếu host đã dùng port 8080/8081/8082, Docker báo lỗi bind; thay port host trong docker-compose.yml (ví dụ 8085:8080).
Một số lệnh hữu ích bổ sung
Build một image cụ thể:
docker-compose build inventoryservice
Chạy một service riêng:
docker-compose up --build inventoryservice
Vào shell container để debug:
docker run -it --rm inventoryservice:latest sh
(hoặc nếu container đã chạy: docker exec -it inventoryservice sh)
Các rủi ro/điểm cần kiểm tra nếu service không khởi:
Java version: project dùng Java 21, Dockerfile đã dùng Temurin 21 JRE.
Nếu một module cần secret hoặc phụ thuộc bên ngoài (Redis, Kafka...), cần thêm service vào docker-compose.yml.
Nếu service yêu cầu migration DB (Flyway/Liquibase), đảm bảo DB đã sẵn sàng trước khi service thực hiện migration — có thể cần healthcheck / wait-for logic.
Muốn mình làm gì tiếp theo?
Tôi có thể chạy thử docker-compose up --build ở môi trường này để kiểm tra lỗi build/run (sẽ thấy lỗi nếu Docker không cài hoặc có lỗi build). Muốn mình chạy thử không? Nếu có, mình sẽ chạy lệnh và gửi log lỗi/khắc phục.
Hoặc mình có thể:
Thêm healthchecks cho services trong docker-compose.yml.
Thêm volume để cache .m2 giữa builds (rút ngắn thời gian build).
Điều chỉnh application.yml/env cho DB/ports nếu bạn cho biết cấu hình mong muốn.
Bạn muốn mình chạy thử build/run bây giờ hay chỉ cần hướng dẫn để bạn chạy trên máy?
