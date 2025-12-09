-- CREATE DATABASE
DROP DATABASE IF EXISTS employeeData;
CREATE DATABASE employeeData;
USE employeeData;

-- STATE TABLE (50 states)
CREATE TABLE state (
    state_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    state_code CHAR(2) NOT NULL UNIQUE
);

-- CITY TABLE
CREATE TABLE city (
    city_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

-- EMPLOYEES TABLE
CREATE TABLE employees (
    empid INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    ssn CHAR(9) UNIQUE NOT NULL,
    dob DATE NOT NULL,
    hire_date DATE NOT NULL,
    current_salary DECIMAL(10,2) NOT NULL,

    email VARCHAR(100) UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'EMPLOYEE') NOT NULL DEFAULT 'EMPLOYEE'
);

-- ADDRESS TABLE
CREATE TABLE address (
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
CREATE TABLE division (
    id INT PRIMARY KEY AUTO_INCREMENT,
    division_name VARCHAR(80) NOT NULL
);

-- EMPLOYEE_DIVISION TABLE
-- (1 division per employee)
CREATE TABLE employee_division (
    empid INT PRIMARY KEY,
    div_id INT NOT NULL,

    FOREIGN KEY (empid) REFERENCES employees(empid),
    FOREIGN KEY (div_id) REFERENCES division(id)
);

-- JOB_TITLES TABLE
CREATE TABLE job_titles (
    job_title_id INT PRIMARY KEY AUTO_INCREMENT,
    title_name VARCHAR(80) NOT NULL
);

-- EMPLOYEE_JOB_TITLES TABLE
-- (1 job title per employee)
CREATE TABLE employee_job_titles (
    empid INT PRIMARY KEY,
    job_title_id INT NOT NULL,
    start_date DATE NOT NULL DEFAULT (CURRENT_DATE),

    FOREIGN KEY (empid) REFERENCES employees(empid),
    FOREIGN KEY (job_title_id) REFERENCES job_titles(job_title_id)
);

-- PAYROLL TABLE
CREATE TABLE payroll (
    pay_id INT PRIMARY KEY AUTO_INCREMENT,
    empid INT NOT NULL,
    pay_date DATE NOT NULL,
    gross DECIMAL(10,2) NOT NULL,
    net DECIMAL(10,2) NOT NULL,
    tax DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (empid) REFERENCES employees(empid)
);

