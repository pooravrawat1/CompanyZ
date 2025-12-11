-- CREATE DATABASE
DROP DATABASE IF EXISTS employeeData;
CREATE DATABASE employeeData;
USE employeeData;

-- STATE TABLE (50 states)
CREATE TABLE IF NOT EXISTS state (
	state_id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	state_code CHAR(2) NOT NULL UNIQUE
);

-- CITY TABLE
CREATE TABLE IF NOT EXISTS city (
	city_id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL
);

-- EMPLOYEES TABLE
CREATE TABLE IF NOT EXISTS employees (
	empid INT PRIMARY KEY AUTO_INCREMENT,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	ssn VARCHAR(12) UNIQUE NOT NULL,
	hire_date DATE NOT NULL,
	current_salary DECIMAL(10,2) NOT NULL,

	email VARCHAR(100) UNIQUE,
	password_hash VARCHAR(255) NOT NULL DEFAULT("password123"),
	role ENUM('ADMIN', 'EMPLOYEE') NOT NULL DEFAULT 'EMPLOYEE'
);

-- ADDRESS TABLE
CREATE TABLE IF NOT EXISTS address (
	empid INT PRIMARY KEY,
	street VARCHAR(100) NOT NULL,
	city_id INT NOT NULL,
	state_id INT NOT NULL,
	zip VARCHAR(10) NOT NULL,
	gender VARCHAR(20),
	identified_race VARCHAR(50),
	dob DATE NOT NULL,
	mobile_phone VARCHAR(20),

	FOREIGN KEY (empid) REFERENCES employees(empid),
	FOREIGN KEY (city_id) REFERENCES city(city_id),
	FOREIGN KEY (state_id) REFERENCES state(state_id)
);

-- DIVISION TABLE
CREATE TABLE IF NOT EXISTS division (
	div_id INT PRIMARY KEY AUTO_INCREMENT,
	division_name VARCHAR(80) NOT NULL
);

-- EMPLOYEE_DIVISION TABLE
-- (1 division per employee)
CREATE TABLE IF NOT EXISTS employee_division (
	empid INT PRIMARY KEY,
	div_id INT NOT NULL,
	

	FOREIGN KEY (empid) REFERENCES employees(empid),
	FOREIGN KEY (div_id) REFERENCES division(div_id)
);

-- JOB_TITLES TABLE
CREATE TABLE IF NOT EXISTS job_titles (
	title_id INT PRIMARY KEY AUTO_INCREMENT,
	title_name VARCHAR(80) NOT NULL
);

-- EMPLOYEE_JOB_TITLES TABLE
-- (1 job title per employee)
CREATE TABLE IF NOT EXISTS employee_job_titles (
	empid INT NOT NULL,
	title_id INT NOT NULL,
	-- title_name VARCHAR(50) NOT NULL,
	-- start_date DATE NOT NULL DEFAULT (CURRENT_DATE),

	FOREIGN KEY (empid) REFERENCES employees(empid),
	FOREIGN KEY (title_id) REFERENCES job_titles(title_id)
);

-- PAYROLL TABLE
CREATE TABLE IF NOT EXISTS payroll (
	pay_id INT PRIMARY KEY AUTO_INCREMENT,
	empid INT NOT NULL,
	pay_date DATE NOT NULL,
	gross DECIMAL(10,2) NOT NULL,
	net DECIMAL(10,2) NOT NULL,

	FOREIGN KEY (empid) REFERENCES employees(empid)
);

-- data insertion

INSERT INTO job_titles (title_id, title_name) VALUES 
	(100,'software manager'),
	(101,'software architect'),
	(102,'software engineer'),
	(103,'software developer'),
	(200,'marketing manager'),
	(201,'marketing associate'),
	(202,'marketing assistant'),
	(900,'Chief Exec. Officer'),
	(901,'Chief Finn. Officer'),
	(902,'Chief Info. Officer');

INSERT INTO employees (empid, first_name, last_name, email, hire_date, current_salary, ssn) VALUES 
	(111,'Snoopy', 'Beagle', 'Snoopy@example.com', 	'2022-08-01', 45000.00, '111-11-1111'),
	(2,'Charlie', 'Brown', 	'Charlie@example.com', 	'2022-07-01', 48000.00, '111-22-1111'),
	(3,'Lucy', 'Doctor', 		'Lucy@example.com', 	'2022-07-03', 55000.00, '111-33-1111'),
	(4,'Pepermint', 'Patti', 	'Peppermint@example.com', '2022-08-02', 98000.00, '111-44-1111'),
	(5,'Linus', 'Blanket', 	'Linus@example.com', 	'2022-09-01', 43000.00, '111-55-1111'),
	(6,'PigPin', 'Dusty', 	'PigPin@example.com', 	'2022-10-01', 33000.00, '111-66-1111'),
	(7,'Scooby', 'Doo', 		'Scooby@example.com', 	'1973-07-03', 78000.00, '111-77-1111'),
	(8,'Shaggy', 'Rodgers', 	'Shaggy@example.com', 	'1973-07-11', 77000.00, '111-88-1111'),
	(9,'Velma', 'Dinkley', 	'Velma@example.com', 	'1973-07-21', 82000.00, '111-99-1111'),
	(10,'Daphne', 'Blake', 	'Daphne@example.com', 	'1973-07-30', 59000.00, '111-00-1111'),
	(11,'Bugs', 'Bunny', 		'Bugs@example.com', 	'1934-07-01', 18000.00, '222-11-1111'),
	(12,'Daffy', 'Duck', 		'Daffy@example.com', 	'1935-04-01', 16000.00, '333-11-1111'),
	(13,'Porky', 'Pig', 		'Porky@example.com', 	'1935-08-12', 16550.00, '444-11-1111'),
	(14,'Elmer', 'Fudd', 		'Elmer@example.com', 	'1934-08-01', 15500.00, '555-11-1111'),
	(15,'Marvin', 'Martian', 	'Marvin@example.com', 	'1937-05-01', 28000.00, '777-11-1111');

INSERT INTO division (div_id, division_name) VALUES 
	(1,'SouthEast'),
	(2,'NorthEast'),
	(3,'West Coast'),
	(999,'HQ');

INSERT INTO employee_division (empid, div_id) VALUES 
	(111, 1),
	(2, 3),
	(3, 999),
	(4, 1),
	(5, 1),
	(6, 1),
	(7, 1),
	(8, 1);
			
INSERT INTO employee_job_titles (empid, title_id) VALUES
	(111,902),
	(2,900),
	(3,901),
	(4,102),
	(5,101),
	(6,201),
	(7,100),
	(8,102),
	(9,102),
	(10,102),
	(11,200),
	(12,201),
	(13,202),
	(14,103),
	(15,103);



