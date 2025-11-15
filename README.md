

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

## 5. Hướng dẫn cài đặt nhanh với docker.

### Chạy dev với docker-compose.dev.yml

1) Tạo file `.env` tại thư mục gốc `D:\QuanLiTapHoa` với nội dung tối thiểu:

```dotenv
# MySQL
MYSQL_ROOT_PASSWORD=123456
MYSQL_DATABASE=appdb
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=123456

# JWT
JWT_SECRET=super-secret-change-me

# RabbitMQ
RABBITMQ_HOST=rabbitmq
RABBITMQ_PORT=5672
RABBITMQ_DEFAULT_USER=admin
RABBITMQ_DEFAULT_PASS=admin

# Cloudinary (nếu dùng upload ảnh)
CLOUDINARY_CLOUD_NAME=
CLOUDINARY_API_KEY=
CLOUDINARY_API_SECRET=

# Timezone
TZ=Asia/Ho_Chi_Minh
```

2) Khởi chạy toàn bộ services (tại thư mục gốc):

```powershell
docker compose -f docker-compose.dev.yml up -d --build
```

3) Dừng và xoá containers:

```powershell
docker compose -f docker-compose.dev.yml down
```

4) Xem logs nhanh (tuỳ chọn):

```powershell
docker compose -f docker-compose.dev.yml logs -f apigateway
```

Các endpoint/dev URLs:
- MySQL: 127.0.0.1:3307 → 3306 (container), DB: `appdb` (volume: `mysql-data-dev`).
- Adminer (duyệt DB): http://localhost:8086
- RabbitMQ Management: http://localhost:15672 (user/pass trong `.env`)
- AuthService: http://localhost:8081 (container: 8080)
- InventoryService: http://localhost:8082 (container: 8080)
- OrderService: http://localhost:8084 (container: 8080)
- API Gateway: http://localhost:8080

Thông tin đăng nhập MySQL cho Adminer:
- Server: `mysql-db` (hoặc 127.0.0.1:3307)
- User: `root`
- Pass: lấy từ `MYSQL_ROOT_PASSWORD` trong `.env`
- Database: `appdb`

Ghi chú cho Windows:
- Nếu gặp lỗi mount `~/.m2/repository` khi chạy Docker Desktop, bạn có thể xoá 2 dòng mount `~/.m2/repository` trong `docker-compose.dev.yml` cho các service Java, hoặc thay bằng đường dẫn tuyệt đối tới `C:\Users\<User>\.m2\repository`.


