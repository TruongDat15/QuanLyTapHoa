
# POS Microservices System

Hệ thống POS này được thiết kế theo kiến trúc **Microservices**, bao gồm các service chính và service mở rộng. Hệ thống tập trung vào quản lý bán hàng, kho hàng, người dùng và báo cáo.

---

## 1. Overview of Services

| STT | Tên Service         | Chức năng chính (Core Functions)                                        | Độ Ưu Tiên      | Giai Đoạn |
|-----|---------------------|-------------------------------------------------------------------------|-----------------|-----------|
| 1   | User & Auth Service | Đăng ký/Đăng nhập, Quản lý nhân viên cơ bản, Phân quyền (JWT)           | Cao (P1)        | Cốt lõi   |
| 2   | Inventory Service   | Đăng ký/Tìm kiếm sản phẩm, Quản lý danh mục, Quản lý tồn kho (số lượng) | Cao (P1)        | Cốt lõi   |
| 3   | Order Service       | Tạo hóa đơn bán hàng, Thanh toán, Lưu trữ chi tiết hóa đơn              | Cao (P1)        | Cốt lõi   |
| 4   | API Gateway         | Định tuyến, Load Balancing, Xác thực JWT cơ bản                         | Cao (P1)        | Cốt lõi   |
| 5   | Discovery Service   | Đăng ký và tìm kiếm Service (Eureka/Consul)                             | Cao (P1)        | Cốt lõi   |
| 6   | Supplier Service    | Quản lý thông tin nhà cung cấp, Theo dõi đơn nhập cơ bản                | Trung bình (P2) | Mở rộng   |
| 7   | CRM Service         | Đăng ký khách hàng, Lưu lịch sử mua hàng, Phân loại khách hàng          | Trung bình (P2) | Mở rộng   |
| 8   | Payment Service     | Tích hợp cổng thanh toán (VNPAY...)                                     | Trung bình (P2) | Mở rộng   |
| 9   | Reporting Service   | Báo cáo cơ bản (Doanh thu, Tồn kho, Bán chạy)                           | Trung bình (P2) | Mở rộng   |
| 10  | Forecasting Service | AI Prophet - Dự báo nhu cầu nhập hàng                                   | Thấp (P3)       | Nâng cao  |

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
   ```bash
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

