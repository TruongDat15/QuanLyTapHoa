
``````
Client           SagaOrchestrator    OrderService     InventoryService    PaymentService
|                     |                |                  |                 |
|---Create & Pay----->|                |                  |                 |
|                     |---POST /orders->|                  |                 |
|                     |                |--save order DB-->|
|                     |<--Order ID-----|                  |                 |
|                     |---POST /inventory/decrease-------->|                 |
|                     |                |                  |--reduce stock--|
|                     |                |                  |<--Success/Fail--|
|                     |<--Success------|                  |                 |
|                     |---POST /payments/cash------------->|                 |
|                     |                |                  |--save payment--|
|                     |<--Success------|                  |                 |
|<---Success----------|                |                  |                 |
|                     |                |                  |                 |
|       <Rollback> if any step fails
|                     |---POST /inventory/increase------->|                 |
|                     |---DELETE /orders/{id}------------>|                 |
|                     |---POST /payments/refund---------->|                 |
`````