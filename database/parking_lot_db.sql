USE parking_lot_db;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE gates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    gate_number INT NOT NULL,
    gate_type ENUM('ENTRY', 'EXIT') NOT NULL,
    current_operator_id VARCHAR(255),
    FOREIGN KEY (current_operator_id) REFERENCES operators(emp_id)
);

CREATE TABLE operators (
    emp_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


CREATE TABLE parking_lots (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE parking_floors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    floor_number INT NOT NULL,
    parking_lot_id BIGINT,
    FOREIGN KEY (parking_lot_id) REFERENCES parking_lots(id)
);


CREATE TABLE parking_spots (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    slot_number INT NOT NULL,
    status ENUM('OCCUPIED', 'RESERVED', 'AVAILABLE') NOT NULL,
    vehicle_type ENUM('CAR', 'BIKE', 'BUS') NOT NULL,
    parking_floor_id BIGINT,
    vehicle_id VARCHAR(255),
    FOREIGN KEY (parking_floor_id) REFERENCES parking_floors(id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_number)
);


CREATE TABLE vehicles (
    vehicle_number VARCHAR(255) PRIMARY KEY,
    vehicle_type ENUM('CAR', 'BIKE', 'BUS') NOT NULL
);


CREATE TABLE tickets (
    number VARCHAR(255) PRIMARY KEY,
    entry_time DATETIME NOT NULL,
    vehicle_id VARCHAR(255),
    parking_spot_id BIGINT,
    generated_at_id BIGINT,
    generated_by_id VARCHAR(255),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_number),
    FOREIGN KEY (parking_spot_id) REFERENCES parking_spots(id),
    FOREIGN KEY (generated_at_id) REFERENCES gates(id),
    FOREIGN KEY (generated_by_id) REFERENCES operators(emp_id)
);


CREATE TABLE invoices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(255) NOT NULL,
    exit_time DATETIME NOT NULL,
    total_cost DOUBLE NOT NULL,
    ticket_id VARCHAR(255),
    generated_at_id BIGINT,
    generated_by_id VARCHAR(255),
    payment_type ENUM('CARD', 'CASH', 'UPI') NOT NULL,
    FOREIGN KEY (ticket_id) REFERENCES tickets(number),
    FOREIGN KEY (generated_at_id) REFERENCES gates(id),
    FOREIGN KEY (generated_by_id) REFERENCES operators(emp_id)
);


CREATE TABLE parking_lots_entry_gates (
    parking_lot_id BIGINT,
    gate_id BIGINT,
    PRIMARY KEY (parking_lot_id, gate_id),
    FOREIGN KEY (parking_lot_id) REFERENCES parking_lots(id),
    FOREIGN KEY (gate_id) REFERENCES gates(id)
);


CREATE TABLE parking_lots_exit_gates (
    parking_lot_id BIGINT,
    gate_id BIGINT,
    PRIMARY KEY (parking_lot_id, gate_id),
    FOREIGN KEY (parking_lot_id) REFERENCES parking_lots(id),
    FOREIGN KEY (gate_id) REFERENCES gates(id)
);


INSERT INTO users (id, username, password, email, role) VALUES
(1, 'admin', '$2a$10$XURPShl2W1X96gOll5KLjeL0jO4lQOvKh3U0A34VyVxbin0SrNm.S', 'admin@example.com', 'ROLE_ADMIN'),
(2, 'operator1', '$2a$10$z5g8e8eZ5e8eZ5e8eZ5e8eZ5e8eZ5e8eZ5e8eZ5e8eZ5e8eZ5e8eZ', 'operator1@example.com', 'ROLE_OPERATOR');

SELECT * FROM users;

INSERT INTO operator (emp_id, name) VALUES
('OP001', 'John Doe'),
('OP002', 'Jane Smith');

SELECT * FROM operator;

INSERT INTO parking_lot (id, name, address) VALUES
(1, 'Downtown Parking', '123 Main St, Cityville');

SELECT * FROM parking_lot;

INSERT INTO parking_floor (id, floor_number, parking_lot_id) VALUES
(1, 1, 1),
(2, 2, 1);

SELECT * FROM parking_floor;

INSERT INTO parking_spot (id, slot_number, status, vehicle_type, parking_floor_id) VALUES
(1, 101, 'OCCUPIED', 'CAR', 1),
(2, 102, 'AVAILABLE', 'BIKE', 1),
(3, 103, 'RESERVED', 'BUS', 1),
(4, 201, 'OCCUPIED', 'CAR', 2),
(5, 202, 'AVAILABLE', 'BIKE', 2),
(6, 203, 'AVAILABLE', 'BUS', 2);

SELECT * FROM parking_spot;

INSERT INTO gate (id, gate_number, gate_type, current_operator_emp_id) VALUES
(1, 1, 'ENTRY', 'OP001'),
(2, 2, 'EXIT', 'OP002');

SELECT * FROM gate;

INSERT INTO parking_lot_entry_gates (parking_lot_id, entry_gates_id) VALUES (1, 1);

SELECT * FROM parking_lot_entry_gates;

INSERT INTO parking_lot_exit_gates (parking_lot_id, exit_gates_id) VALUES (1, 2);

SELECT * FROM parking_lot_exit_gates;


INSERT INTO vehicle (vehicle_number, vehicle_type) VALUES
('CAR123', 'CAR'),
('BIKE456', 'BIKE'),
('BUS789', 'BUS');

SELECT * FROM vehicle;

INSERT INTO ticket (number, entry_time, vehicle_vehicle_number, parking_spot_id, generated_at_id, generated_by_emp_id) VALUES
('TICKET001', '2025-03-04 10:00:00', 'CAR123', 1, 1, 'OP001'),
('TICKET002', '2025-03-04 11:00:00', 'BIKE456', 4, 1, 'OP001'),
('TICKET003', '2025-03-04 12:00:00', 'BUS789', NULL, 1, 'OP001');


SELECT * FROM ticket;

INSERT INTO invoice (id, number, exit_time, total_cost, ticket_number, generated_at_id, generated_by_emp_id, payment_type) VALUES
(1, 'INV-TICKET003', '2025-03-04 14:00:00', 20.00, 'TICKET003', 2, 'OP002', 'CASH');

SELECT * FROM invoice;

UPDATE parking_spot SET status = 'AVAILABLE', vehicle_vehicle_number = NULL WHERE id = 3;



