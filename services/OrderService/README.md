
# 🏪 **ORDER SERVICE - THỰC THỂ VÀ CHỨC NĂNG**

## 🗃️ **CÁC THỰC THỂ CHÍNH TRONG ORDER SERVICE**

### **1. 🧾 ORDER (Đơn hàng)**
- **orderId**: Mã đơn hàng duy nhất
- **cashierId**: ID nhân viên bán hàng
- **storeId**: ID cửa hàng
- **customerPhone**: SĐT khách hàng (optional)
- **customerName**: Tên khách hàng (từ customer service)
- **status**: Trạng thái đơn (DRAFT, PAID, COMPLETED, CANCELLED)
- **totalAmount**: Tổng tiền hàng
- **taxAmount**: Thuế VAT
- **finalAmount**: Tổng thanh toán
- **paymentMethod**: Phương thức thanh toán (CASH, VNPAY, CARD)
- **paymentId**: ID giao dịch thanh toán
- **createdAt**: Thời gian tạo
- **completedAt**: Thời gian hoàn thành

### **2. 📦 ORDER_ITEM (Chi tiết đơn hàng)**
- **productId**: ID sản phẩm
- **barcode**: Mã vạch sản phẩm
- **productName**: Tên sản phẩm
- **unitPrice**: Đơn giá
- **quantity**: Số lượng
- **totalPrice**: Thành tiền (unitPrice * quantity)

### **3. 🧾 RECEIPT (Hoá đơn)**
- **receiptId**: Mã hoá đơn
- **receiptNumber**: Số hoá đơn (theo quy định)
- **orderId**: Liên kết đến order
- **content**: Nội dung hoá đơn (HTML/PDF)
- **issuedAt**: Thời gian xuất hoá đơn
- **taxCode**: Mã số thuế cửa hàng
- **storeInfo**: Thông tin cửa hàng

### **4. 🔄 ORDER_SAGA (Quản lý Saga)**
- **sagaId**: ID saga
- **currentStep**: Bước hiện tại
- **status**: Trạng thái saga
- **errorMessage**: Thông báo lỗi (nếu có)

## 🎯 **CHỨC NĂNG CHÍNH CỦA ORDER SERVICE**

### **1. 📋 QUẢN LÝ ĐƠN HÀNG**
- **Tạo đơn hàng** mới từ danh sách sản phẩm
- **Thêm/Xoá sản phẩm** vào đơn hàng
- **Cập nhật số lượng** sản phẩm
- **Huỷ đơn hàng** khi cần
- **Tìm kiếm & Lọc** đơn hàng theo nhiều tiêu chí

### **2. 💳 XỬ LÝ THANH TOÁN**
- **Chọn phương thức** thanh toán (Tiền mặt, VNPay, Thẻ)
- **Tính toán tiền thừa** cho thanh toán tiền mặt
- **Tạo link thanh toán** VNPay
- **Xác nhận thanh toán** thành công
- **Xử lý hoàn tiền** khi huỷ đơn

### **3. 🧾 TẠO & QUẢN LÝ HOÁ ĐƠN**
- **Tự động tạo hoá đơn** khi đơn hoàn thành
- **Định dạng hoá đơn** theo chuẩn Bộ Tài Chính
- **Lưu trữ hoá đơn** điện tử
- **Tái xuất hoá đơn** khi cần
- **In hoá đơn** trực tiếp từ POS

### **4. 🔍 QUÉT & TÌM SẢN PHẨM**
- **Quét mã vạch** để thêm sản phẩm
- **Tìm kiếm sản phẩm** theo tên/mã
- **Kiểm tra tồn kho** trước khi thêm vào đơn
- **Hiển thị thông tin** giá, tồn kho

### **5. 📊 BÁO CÁO & THỐNG KÊ**
- **Doanh thu theo ngày/tuần/tháng**
- **Top sản phẩm bán chạy**
- **Thống kê theo nhân viên**
- **Báo cáo thuế** tự động

## 🔄 **LUỒNG TẠO HOÁ ĐƠN TỰ ĐỘNG**

### **1. KÍCH HOẠT TẠO HOÁ ĐƠN**
```
Order chuyển trạng thái → COMPLETED
    ↓
Tự động trigger tạo Receipt
```

### **2. NỘI DUNG HOÁ ĐƠN BAO GỒM:**
- **Thông tin cửa hàng**: Tên, địa chỉ, MST
- **Thông tin hoá đơn**: Số, ngày, ký hiệu
- **Thông tin khách hàng** (nếu có)
- **Chi tiết sản phẩm**: Tên, ĐVT, SL, Đơn giá, Thành tiền
- **Tổng tiền hàng, Thuế VAT, Tổng thanh toán**
- **Chữ ký số** và mã xác thực

### **3. LƯU TRỮ HOÁ ĐƠN**
- **Database**: Lưu content hoá đơn (HTML/PDF)
- **File System**: Lưu file PDF backup
- **External Storage**: Upload lên cloud (tuỳ chọn)

## 🔗 **TƯƠNG TÁC VỚI CÁC SERVICE KHÁC**

### **1. 📦 INVENTORY SERVICE**
- **Kiểm tra tồn kho** trước khi thêm sản phẩm
- **Cập nhật tồn kho** khi đơn hoàn thành
- **Lấy thông tin sản phẩm** (tên, giá, mã vạch)

### **2. 👥 CUSTOMER SERVICE**
- **Tìm kiếm khách hàng** theo SĐT
- **Cập nhật điểm tích luỹ** khi đơn hoàn thành
- **Lấy thông tin khách hàng** cho hoá đơn

### **3. 💳 PAYMENT SERVICE**
- **Xử lý thanh toán** tiền mặt/VNPay
- **Xác nhận giao dịch** thành công
- **Xử lý hoàn tiền** khi huỷ đơn

### **4. 🔔 NOTIFICATION SERVICE**
- **Gửi hoá đơn điện tử** qua email/SMS
- **Thông báo trạng thái** đơn hàng

## 🎯 **CÁC TRẠNG THÁI ĐƠN HÀNG**

### **📊 ORDER STATUS FLOW:**
```
DRAFT → PAID → COMPLETED
    ↓
CANCELLED
```

- **DRAFT**: Đơn đang được tạo, chưa thanh toán
- **PAID**: Đã thanh toán, chờ xử lý kho
- **COMPLETED**: Đã xử lý xong, có hoá đơn
- **CANCELLED**: Đã huỷ, có thể hoàn tiền

## 💡 **TÍNH NĂNG NÂNG CAO**

### **1. 🧾 HOÁ ĐƠN ĐIỆN TỬ**
- **Mẫu hoá đơn** tuỳ chỉnh theo cửa hàng
- **Ký số hoá đơn** tự động
- **Gửi email/SMS** hoá đơn cho khách
- **Lưu trữ đám mây** để tra cứu

### **2. 🔄 OFFLINE SUPPORT**
- **Lưu đơn tạm** khi mất kết nối
- **Đồng bộ sau** khi có kết nối
- **Xử lý xung đột** khi đồng bộ

### **3. 📱 MULTI-DEVICE**
- **Đồng bộ real-time** giữa các thiết bị
- **Chia sẻ session** giữa các nhân viên

**Order Service đóng vai trò TRUNG TÂM trong hệ thống POS, kết nối tất cả các service và quản lý toàn bộ vòng đời đơn hàng từ lúc tạo đến khi xuất hoá đơn!** 🎉


```mermaid
sequenceDiagram
    participant Client
    participant OrderService
    participant InventoryService
    participant PaymentService
    participant InvoiceService

    %% 1. Tạo đơn tạm
    Client->>OrderService: Create Draft Order
    OrderService->>OrderService: Save order (DRAFT)
    OrderService->>InventoryService: OrderCreatedEvent(orderId, items)

    %% 2. Kiểm tra tồn kho & trừ tạm
    InventoryService-->>OrderService: StockReservedEvent / StockRejectedEvent
    alt Stock reserved
        InventoryService->>InventoryService: Reserve stock (tạm giữ)
        OrderService->>OrderService: Update order status CONFIRMED
    else Stock rejected
        OrderService->>OrderService: Update order status CANCELED
    end

    %% 3. Thanh toán
    Client->>OrderService: Payment Requested (Cash/QR)
    OrderService->>PaymentService: OrderPaymentRequestedEvent(orderId, paymentMethod)
    alt Cash payment
        PaymentService-->>OrderService: PaymentCompletedEvent
    else QR payment
        PaymentService->>PaymentService: Process QR payment
        alt Payment success
            PaymentService-->>OrderService: PaymentCompletedEvent
        else Payment failed
            PaymentService-->>OrderService: PaymentFailedEvent
        end
    end

    %% 4. Xuất hóa đơn / Hoàn tất hoặc huỷ
    alt PaymentCompletedEvent
        OrderService->>InventoryService: Deduct stock permanently
        OrderService->>InvoiceService: PaymentCompletedEvent
        InvoiceService-->>OrderService: InvoiceCreatedEvent
        OrderService->>OrderService: Update order status COMPLETED
    else PaymentFailedEvent
        OrderService->>InventoryService: Release reserved stock
        OrderService->>OrderService: Update order status CANCELED
    end
```


```mermaid
flowchart TD
A[Client tạo đơn] --> B[OrderService lưu đơn DRAFT]
B --> C[OrderService phát OrderCreatedEvent]
C --> D[InventoryService kiểm tra tồn kho]

    D -->| Đủ hàng | E[InventoryService giữ tạm kho Reserved]
    D -->| Hết hàng | F[OrderService cập nhật CANCELED]

    E --> G[OrderService nhận StockReservedEvent]
    G --> H[OrderService cập nhật trạng thái CONFIRMED]

    H --> I[Client nhấn Thanh toán Cash/QR]
    I --> J[OrderService phát OrderPaymentRequestedEvent nếu QR]

    subgraph Thanh_toan["Thanh toán"]
        direction LR
        J -->| Cash | K1[OrderService tự phát PaymentCompletedEvent]
        J -->| QR | K2[PaymentService xử lý QR payment]
        K2 -->| Thanh toán thành công | K1
        K2 -->| Thanh toán thất bại | L[OrderService nhận PaymentFailedEvent]
    end

    K1 --> M[OrderService trừ kho thật]
    K1 --> N[OrderService phát PaymentCompletedEvent đến InvoiceService]
    N --> O[InvoiceService tạo hóa đơn]
    O --> P[OrderService nhận InvoiceCreatedEvent]
    P --> Q[OrderService cập nhật trạng thái COMPLETED]

    L --> R[OrderService trả lại tạm kho]
    L --> S[OrderService cập nhật trạng thái CANCELED]
```