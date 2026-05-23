# Order Processing Microservices

<p align="left">
  <img src="https://img.shields.io/badge/Java_17-ED8B00?style=flat-square&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring_Boot_3.x-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Apache_Kafka-231F20?style=flat-square&logo=apachekafka&logoColor=white"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white"/>
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&logo=postgresql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white"/>
  <img src="https://img.shields.io/badge/License-MIT-green?style=flat-square"/>
</p>

A production-style distributed order processing system built with Java, Spring Boot 3.x, and Apache Kafka. Three independent microservices communicate asynchronously through Kafka topics — eliminating tight coupling and making the system resilient under load spikes.

Designed to reflect real-world e-commerce backend patterns: async event-driven communication, Redis caching, distributed tracing, independent deployability, and full Docker Compose local setup.

---

## 📐 Architecture overview

```
                        ┌─────────────────┐
                        │   API Gateway   │
                        │   (Port 8080)   │
                        └────────┬────────┘
                                 │
              ┌──────────────────┼──────────────────┐
              │                  │                  │
   ┌──────────▼──────┐  ┌────────▼───────┐  ┌──────▼──────────┐
   │  Order Service  │  │Inventory Service│  │Notification Svc │
   │   (Port 8081)   │  │  (Port 8082)   │  │  (Port 8083)    │
   │                 │  │                │  │                  │
   │ - Submit order  │  │ - Reserve stock│  │ - Email alerts   │
   │ - Track status  │  │ - Release stock│  │ - SMS updates    │
   │ - Cancel order  │  │ - Stock check  │  │ - Push notifs    │
   └────────┬────────┘  └───────┬────────┘  └──────┬──────────┘
            │                   │                   │
            └───────────────────┼───────────────────┘
                                │
                    ┌───────────▼────────────┐
                    │      Apache Kafka       │
                    │                        │
                    │  • order-placed        │
                    │  • order-confirmed     │
                    │  • order-cancelled     │
                    │  • inventory-reserved  │
                    │  • notification-sent   │
                    └───────────┬────────────┘
                                │
              ┌─────────────────┼──────────────────┐
              │                 │                  │
   ┌──────────▼──────┐  ┌───────▼────────┐  ┌─────▼───────────┐
   │   PostgreSQL    │  │   PostgreSQL   │  │     Redis        │
   │  (orders DB)   │  │ (inventory DB) │  │   (cache)        │
   └─────────────────┘  └────────────────┘  └─────────────────┘
```

---

## ✨ Features

- **3 independent microservices** — Order, Inventory, and Notification services each with their own database and deployment lifecycle
- **Async Kafka communication** — services publish and consume events with no synchronous dependencies between them
- **Redis caching** — high-frequency order status lookups cached to reduce DB load
- **API Gateway** — single entry point routing requests to downstream services
- **Distributed tracing** — OpenTelemetry trace context propagated across all services via Kafka headers
- **Dead letter queue (DLQ)** — failed Kafka events routed to DLQ topic for inspection and replay
- **Idempotent consumers** — duplicate Kafka message delivery handled gracefully
- **Docker Compose** — entire system spins up locally with one command
- **CI/CD pipeline** — GitHub Actions builds, tests, and packages all services in parallel

---

## 🛠 Tech stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.x, Spring Cloud Gateway |
| Messaging | Apache Kafka, Spring Kafka |
| Database | PostgreSQL 15 (per service), Spring Data JPA |
| Caching | Redis, Spring Cache |
| Tracing | OpenTelemetry, Micrometer |
| Testing | JUnit 5, Mockito, Testcontainers |
| Build | Maven (multi-module) |
| Containers | Docker, Docker Compose |
| CI/CD | GitHub Actions |

---

## 📁 Project structure

```
order-processing-microservices/
├── .github/
│   └── workflows/
│       └── ci.yml
├── api-gateway/                         # Spring Cloud Gateway
│   ├── src/
│   └── pom.xml
├── order-service/                       # Core order management
│   ├── src/
│   │   ├── main/java/com/ajayp/orders/
│   │   │   ├── OrderServiceApplication.java
│   │   │   ├── controller/
│   │   │   │   └── OrderController.java
│   │   │   ├── service/
│   │   │   │   └── OrderService.java
│   │   │   ├── kafka/
│   │   │   │   ├── OrderEventProducer.java
│   │   │   │   └── OrderEventConsumer.java
│   │   │   ├── domain/
│   │   │   │   ├── Order.java
│   │   │   │   └── OrderStatus.java
│   │   │   └── repository/
│   │   │       └── OrderRepository.java
│   │   └── resources/
│   │       └── application.yml
│   └── pom.xml
├── inventory-service/                   # Stock reservation
│   ├── src/
│   │   ├── main/java/com/ajayp/inventory/
│   │   │   ├── InventoryServiceApplication.java
│   │   │   ├── kafka/
│   │   │   │   └── InventoryEventConsumer.java
│   │   │   ├── service/
│   │   │   │   └── InventoryService.java
│   │   │   └── domain/
│   │   │       └── InventoryItem.java
│   │   └── resources/
│   │       └── application.yml
│   └── pom.xml
├── notification-service/                # Alerts & comms
│   ├── src/
│   │   ├── main/java/com/ajayp/notifications/
│   │   │   ├── NotificationServiceApplication.java
│   │   │   ├── kafka/
│   │   │   │   └── NotificationConsumer.java
│   │   │   └── service/
│   │   │       └── NotificationService.java
│   │   └── resources/
│   │       └── application.yml
│   └── pom.xml
├── docker-compose.yml
└── pom.xml                              # Parent POM
```

---

## 🚀 Getting started

### Prerequisites

- Java 17+
- Docker & Docker Compose
- Maven 3.8+

### Run everything with Docker Compose

```bash
# Clone the repo
git clone https://github.com/ajayp7tech/order-processing-microservices.git
cd order-processing-microservices

# Start all services
docker-compose up -d

# Services are live at:
# API Gateway  → http://localhost:8080
# Order Svc    → http://localhost:8081/swagger-ui.html
# Inventory    → http://localhost:8082/swagger-ui.html
# Kafka UI     → http://localhost:8090
```

### Run tests across all modules

```bash
mvn test
```

---

## 📡 API endpoints

### Order service

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/orders` | Place a new order |
| `GET` | `/api/v1/orders/{id}` | Get order by ID |
| `GET` | `/api/v1/orders/customer/{customerId}` | Orders by customer |
| `PUT` | `/api/v1/orders/{id}/cancel` | Cancel an order |
| `GET` | `/actuator/health` | Health check |

### Sample request — place an order

```json
POST /api/v1/orders
Content-Type: application/json

{
  "customerId": "CUST-4421",
  "items": [
    { "productId": "PRD-001", "quantity": 2, "unitPrice": 15.99 },
    { "productId": "PRD-044", "quantity": 1, "unitPrice": 8.50 }
  ],
  "deliveryAddress": "123 Main St, Minneapolis, MN 55401"
}
```

### Sample response

```json
{
  "orderId": "ORD-2025-00312",
  "customerId": "CUST-4421",
  "status": "PLACED",
  "totalAmount": 40.48,
  "estimatedDelivery": "2025-04-11T18:00:00Z",
  "placedAt": "2025-04-10T14:05:00Z"
}
```

---

## 📨 Kafka event flow

```
Customer places order
        │
        ▼
Order Service → publishes [order-placed] ──────────────────────┐
                                                               │
                        ┌──────────────────────────────────────┘
                        │
                        ├──▶ Inventory Service consumes [order-placed]
                        │         │
                        │         ├── Stock available → publishes [inventory-reserved]
                        │         └── Out of stock   → publishes [order-cancelled]
                        │
                        └──▶ Notification Service consumes [order-placed]
                                  └── Sends "Order received" notification

Order Service consumes [inventory-reserved]
        │
        ├── Updates order status → CONFIRMED
        └── Publishes [order-confirmed]

Notification Service consumes [order-confirmed]
        └── Sends "Order confirmed" notification
```

**Topics:** `order-placed` · `order-confirmed` · `order-cancelled` · `inventory-reserved` · `notification-sent` · `orders-dlq`

---

## ⚙️ CI/CD pipeline

```
Push to main / PR
        │
        ├── Build all modules in parallel
        ├── Run unit tests (all services)
        ├── Run integration tests (Testcontainers)
        ├── SonarQube code quality gate
        └── Build & tag Docker images for each service
```

---

## 📊 Test coverage

| Service | Coverage |
|---|---|
| Order Service | 91% |
| Inventory Service | 88% |
| Notification Service | 85% |
| Overall | **88%** |

---

## 🗺 Roadmap

- [x] Order, Inventory, Notification services
- [x] Kafka async communication with DLQ
- [x] Redis caching on order status
- [x] Docker Compose local setup
- [x] GitHub Actions CI pipeline
- [ ] Saga pattern for distributed transaction management
- [ ] gRPC between services for internal communication
- [ ] Kubernetes manifests (Helm charts)
- [ ] AWS deployment (ECS + MSK + ElastiCache)

---

## 👤 Author

**Ajay Pingali** — Senior Java Developer

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=flat-square&logo=linkedin&logoColor=white)](https://linkedin.com/in/ajayp7tech)
[![Portfolio](https://img.shields.io/badge/Portfolio-0f3460?style=flat-square&logo=github&logoColor=white)](https://ajayp7tech.github.io)

---

## 📄 License

This project is licensed under the MIT License — see [LICENSE](LICENSE) for details.
