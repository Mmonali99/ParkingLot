
# ParkingLot

A full-stack **Parking Lot Management System** designed to streamline parking operations with ticket issuance, spot allocation, and invoice generation. Built with Spring Boot for the backend and React for the frontend, it offers a robust solution for parking administration.

## Features

- **Authentication**: Secure JWT-based login/registration with roles (Admin, Operator).
- **Dashboard**: Real-time stats on total spots, occupied spots, active tickets, and revenue.
- **Parking Management**: Manage lots, floors, and spots (Occupied, Reserved, Available) for vehicle types (Car, Bike, Bus).
- **Gate Management**: Configure entry/exit gates and assign operators.
- **Tickets**: Issue tickets, track entry, and process exits with invoices.
- **Invoices**: Record payments (Cash, Card, UPI) with exit details.
- **Frontend**: Responsive UI with React components (Cards, Tables, Modals, Sidebar).

## Tech Stack

### Backend
- **Spring Boot 3.2.3**: REST API framework.
- **Spring Data JPA**: MySQL integration.
- **Spring Security**: JWT authentication.
- **MySQL 8**: Data storage.
- **Maven**: Build tool.

### Frontend
- **React 18**: UI framework.
- **React Router**: Navigation.
- **Axios**: API requests.
- **CSS**: Custom styles.

### Database
- **MySQL**: Tables for Users, Gates, Operators, Parking Lots, Floors, Spots, Vehicles, Tickets, Invoices.

## Prerequisites

- **Java 17**
- **Node.js 18+**
- **MySQL 8**
- **Maven**
- **Git**

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/parkinglot.git
cd parkinglot
```

### 2. Backend Setup
1. **Navigate to Backend**:
   ```bash
   cd parking-lot-backend
   ```
2. **Configure MySQL**:
   - Create database:
     ```sql
     CREATE DATABASE parking_lot_db;
     ```
   - Update `src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/parking_lot_db
     spring.datasource.username=root
     spring.datasource.password=your-password
     ```
3. **Run Backend**:
   ```bash
   mvn spring-boot:run
   ```
   - API runs on `http://localhost:8080`.

### 3. Frontend Setup
1. **Navigate to Frontend**:
   ```bash
   cd parking-lot-frontend
   ```
2. **Install Dependencies**:
   ```bash
   npm install
   ```
3. **Run Frontend**:
   ```bash
   npm start
   ```
   - App runs on `http://localhost:3000`.

### 4. Database Initialization
- Execute SQL scripts (assumed provided):
  ```sql
  USE parking_lot_db;
  -- Run CREATE TABLE for Users, Gates, Spots, etc.
  -- Insert initial data (e.g., admin user)
  ```
- Default admin: `admin` / `admin123`.

## Usage

1. **Launch App**: Visit `http://localhost:3000`.
2. **Login**: Use `admin` / `admin123` or register.
3. **Navigate**: Access Dashboard, Gates, Tickets via sidebar.
4. **Manage**: Issue tickets, update spots, process exits.

## API Endpoints

- **Auth**: 
  - `POST /api/auth/login`
  - `POST /api/auth/register`
- **Tickets**: 
  - `GET /api/tickets`
  - `POST /api/tickets/issue`
  - `POST /api/tickets/exit/{ticketNumber}`
- **Gates**: 
  - `GET /api/gates`
  - `PUT /api/gates/{gateId}`
- Full list in `controllers/`.

## Project Structure

```
parkinglot/
├── parking-lot-backend/
│   ├── src/main/java/com/parkinglot/
│   │   ├── config/         # Security & CORS setup
│   │   ├── controllers/    # REST endpoints
│   │   ├── dtos/          # Data Transfer Objects
│   │   ├── models/        # JPA entities
│   │   ├── repositories/  # JPA repositories
│   │   ├── services/      # Business logic
│   │   └── strategies/    # Spot/payment strategies
│   ├── pom.xml
│   └── application.properties
├── parking-lot-frontend/
│   ├── src/
│   │   ├── components/    # Reusable UI (Cards, Tables)
│   │   ├── hooks/         # Custom hooks (useApi, useAuth)
│   │   ├── pages/         # Page components (Dashboard, Tickets)
│   │   ├── styles/        # CSS files
│   │   └── utils/         # Helper functions
│   ├── package.json
│   └── public/
└── README.md
```

## Contributing

1. Fork the repo.
2. Create a branch: `git checkout -b feature/your-feature`.
3. Commit: `git commit -m "Add feature"`.
4. Push: `git push origin feature/your-feature`.
5. Submit a Pull Request.

