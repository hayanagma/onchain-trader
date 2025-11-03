# Onchain Trader

A modular, on-chain trading bot platform built with **Java Spring Boot microservices** and a **modern Nuxt frontend**.  
Currently under active development — expect frequent changes.

---

## 📖 Overview

**Onchain Trader** is a self-custody trading platform that allows users to connect their wallets, subscribe to trading plans, and execute trades directly on supported blockchain networks.  
No centralized custody. No API keys. All logic is verifiable on-chain.

---

## ⚙️ Tech Stack

- **Backend:** Java 21, Spring Boot 3.4  
- **Frontend:** Nuxt 4, TypeScript, Tailwind  
- **Database:** PostgreSQL  
- **Cache:** Redis  
- **Containerization:** Docker, Docker Compose  
- **Monitoring (planned):** Prometheus, Grafana, Jaeger / Zipkin  
- **Orchestration (planned):** Kubernetes  
- **Proxy (planned):** Nginx with GeoIP2 and TLS termination  

---

## 🌐 Networks Overview

Two categories are used internally:

### Trading Networks (`NetworkType`)
Currently integrated:
- **TRON**
- **ETHEREUM**
- **SOLANA**
- **BITCOIN**

More networks will be added as the trading engine expands.

### Payment Networks (`PaymentNetworkType`)
Currently integrated for subscription and payment flows:
- **BITCOIN**
- **ETHEREUM**
- **SOLANA**
- **MONERO**
- **TRON**
- **LITECOIN**

Additional networks will be integrated as payment gateways evolve.

---

## 🧩 Microservices

| Service | Description |
|----------|-------------|
| `gateway` | Routes traffic, handles authentication, CORS, and load balancing |
| `auth` | Manages JWT authentication, refresh tokens, and token versioning |
| `identity` | Handles trader accounts, wallet verification, and nonce challenges |
| `ledger` | Tracks balances, payments, and subscription transactions |
| `trader` | Core trading logic, execution, and network integrations |
| `mail` | Newsletter, contact form, and support messages |

---

## 💳 Subscription System

- Supports on-chain payments across multiple blockchain networks  
- Uses the `ledger` microservice for payment tracking and accounting  
- Subscription tiers: **Free**, **Pro**, **Premium**  
- Payment confirmation via polling and automatic expiration  
- Integrated networks: **BTC**, **ETH**, **SOL**, **TRX**, **LTC**, **XMR**

---

## 🧰 Development Setup

### Step 1 — Clone the repository
```bash
git clone https://github.com/hayanagma/onchain-trader.git
cd onchain-trader
```

### Step 2 — Create and configure `.env`
```bash
cp .env.example .env
```
Update the `.env` file accordingly with all required variables for your environment.

### Step 3 — Add key files
```
keys/
├── private.pem.example
└── public.pem.example
```
Replace `.example` with `private.pem` and `public.pem` when deploying.

### Step 4 — Build and start containers
```bash
docker compose up --build
```

### Step 5 — Access services
- **API Gateway:** `http://localhost:8080`  
- **Frontend UI:** `http://localhost:3000`

---

## 🔐 Authentication Flow

- Wallet challenge using on-chain signature  
- JWT access + refresh cookies  
- Token version bump for revocation  
- Separate flows for **Admin** and **Trader**

---

## 🧭 Roadmap

- [x] Multi-network subscription payments  
- [x] Wallet signature-based authentication  
- [x] Internal ledger and payment tracking  
- [x] Trading networks integration (TRON, ETHEREUM, SOLANA, BITCOIN)  
- [ ] More trading platforms (DEX and CEX integrations)  
- [ ] Strategy engine and analytics dashboard  
- [ ] Kubernetes + Nginx production deployment  
- [ ] Prometheus + Grafana monitoring  
- [ ] Distributed tracing with Jaeger/Zipkin  

---

## 🪪 License

**MIT License © 2025 Hayanagma**  
This project is a work in progress and not yet production-ready.

---

> ⚙️ Keep this README updated as new networks, microservices, and infrastructure are added.
