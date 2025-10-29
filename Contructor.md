# 🏪 POS SYSTEM - COMPLETE MICROSERVICES IMPLEMENTATION

## 📁 COMPLETE PROJECT STRUCTURE

```
pos-backend-microservices/
├── discovery-server/
│   ├── src/main/java/com/pos/discovery/
│   │   └── DiscoveryServerApplication.java
│   └── src/main/resources/application.yml
├── api-gateway/
│   ├── src/main/java/com/pos/gateway/
│   │   └── ApiGatewayApplication.java
│   └── src/main/resources/application.yml
├── common-modules/
│   ├── common-dtos/
│   │   └── src/main/java/com/pos/common/dto/
│   ├── common-utils/
│   │   └── src/main/java/com/pos/common/utils/
│   └── common-exceptions/
│       └── src/main/java/com/pos/common/exceptions/
├── customer-service/
│   ├── src/main/java/com/pos/customer/
│   │   ├── CustomerServiceApplication.java
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   └── entity/
│   └── src/main/resources/application.yml
├── inventory-service/
│   ├── src/main/java/com/pos/inventory/
│   │   ├── InventoryServiceApplication.java
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   └── entity/
│   └── src/main/resources/application.yml
├── order-service/
│   ├── src/main/java/com/pos/order/
│   │   ├── OrderServiceApplication.java
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   └── entity/
│   └── src/main/resources/application.yml
├── payment-service/
│   ├── src/main/java/com/pos/payment/
│   │   ├── PaymentServiceApplication.java
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   └── entity/
│   └── src/main/resources/application.yml
└── saga-orchestrator/
    ├── src/main/java/com/pos/saga/
    │   ├── SagaOrchestratorApplication.java
    │   ├── controller/
    │   ├── service/
    │   ├── repository/
    │   └── entity/
    └── src/main/resources/application.yml
```

## 🎯 SERVICE RESPONSIBILITIES & APIs

### **1. 👥 CUSTOMER-SERVICE**
**Nhiệm vụ**: Quản lý thông tin khách hàng, điểm tích lũy, lịch sử mua hàng

```java
// APIs:
POST   /api/customers                 # Đăng ký khách hàng mới
GET    /api/customers/{id}           # Lấy thông tin khách hàng
GET    /api/customers/phone/{phone}  # Tìm khách hàng bằng SĐT
PUT    /api/customers/{id}           # Cập nhật thông tin
POST   /api/customers/{id}/points    # Cộng/trừ điểm tích lũy
GET    /api/customers/{id}/points    # Lấy điểm tích lũy
GET    /api/customers/{id}/orders    # Lịch sử mua hàng
```

### **2. 📦 INVENTORY-SERVICE**
**Nhiệm vụ**: Quản lý danh mục sản phẩm và tồn kho

```java
// APIs:
// Product Management
POST   /api/products                 # Thêm sản phẩm mới
GET    /api/products                 # Danh sách sản phẩm
GET    /api/products/{id}           # Chi tiết sản phẩm
GET    /api/products/barcode/{code} # Tìm sản phẩm bằng mã vạch
PUT    /api/products/{id}           # Cập nhật sản phẩm
DELETE /api/products/{id}           # Xóa sản phẩm

// Inventory Management
GET    /api/inventory/{productId}    # Kiểm tra tồn kho
POST   /api/inventory/check-stock    # Kiểm tra stock nhiều sản phẩm
POST   /api/inventory/update-stock   # Cập nhật tồn kho
POST   /api/inventory/reserve        # Tạm giữ sản phẩm
POST   /api/inventory/cancel-reserve # Hủy tạm giữ
GET    /api/inventory/low-stock      # Sản phẩm sắp hết hàng
```

### **3. 🛒 ORDER-SERVICE**
**Nhiệm vụ**: Quản lý đơn hàng, giỏ hàng, xử lý bán hàng tại quầy

```java
// APIs:
// Order Management
POST   /api/orders                   # Tạo đơn hàng mới (scan sản phẩm)
GET    /api/orders/{orderId}         # Lấy thông tin đơn hàng
GET    /api/orders                   # Danh sách đơn hàng (filter)
POST   /api/orders/{orderId}/cancel  # Hủy đơn hàng
POST   /api/orders/{orderId}/complete # Hoàn tất đơn hàng

// Cart & Checkout
POST   /api/orders/{orderId}/items   # Thêm sản phẩm vào đơn
PUT    /api/orders/{orderId}/items/{itemId} # Cập nhật số lượng
DELETE /api/orders/{orderId}/items/{itemId} # Xóa sản phẩm

// Payment Selection
POST   /api/orders/{orderId}/payment # Chọn phương thức thanh toán

// Receipt
GET    /api/orders/{orderId}/receipt # In hóa đơn

// Reports
GET    /api/orders/reports/daily     # Báo cáo doanh thu ngày
GET    /api/orders/reports/products  # Báo cáo sản phẩm bán chạy
```

### **4. 💳 PAYMENT-SERVICE**
**Nhiệm vụ**: Xử lý thanh toán đa phương thức (tiền mặt, VNPay)

```java
// APIs:
// Payment Processing
POST   /api/payments/cash            # Thanh toán tiền mặt
POST   /api/payments/vnpay/create    # Tạo link thanh toán VNPay
POST   /api/payments/vnpay/confirm   # Xác nhận thanh toán VNPay
GET    /api/payments/{paymentId}     # Lấy thông tin thanh toán

// Refund
POST   /api/payments/refund          # Hoàn tiền

// Payment Methods
GET    /api/payments/methods         # Danh sách phương thức thanh toán

// Reports
GET    /api/payments/reports/daily   # Báo cáo thanh toán ngày
```

### **5. 🔄 SAGA-ORCHESTRATOR**
**Nhiệm vụ**: Điều phối luồng tạo đơn hàng, đảm bảo data consistency

```java
// APIs:
POST   /api/saga/order/create        # Khởi động saga tạo đơn hàng
GET    /api/saga/{sagaId}           # Lấy trạng thái saga
GET    /api/saga/order/{orderId}    # Lấy saga theo orderId
POST   /api/saga/{sagaId}/compensate # Thực hiện compensation
```

