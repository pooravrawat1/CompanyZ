CREATE DATABASE IF NOT EXISTS employeedata;
USE employeedata;

DROP TABLE IF EXISTS employee_job_titles;
DROP TABLE IF EXISTS employee_division;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS payroll;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS job_titles;
DROP TABLE IF EXISTS division;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS states;

CREATE TABLE states (
    state_id INT PRIMARY KEY AUTO_INCREMENT,
    state_name VARCHAR(50) NOT NULL,
    state_code CHAR(2) NOT NULL UNIQUE
);

CREATE TABLE cities (
    city_id INT PRIMARY KEY AUTO_INCREMENT,
    city_name VARCHAR(100) NOT NULL,
    state_id INT NOT NULL,
    FOREIGN KEY (state_id) REFERENCES states(state_id)
);

CREATE TABLE employees (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    ssn VARCHAR(11) UNIQUE NOT NULL,
    dob DATE NOT NULL,
    hire_date DATE NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    email VARCHAR(100),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE division (
    div_id INT PRIMARY KEY AUTO_INCREMENT,
    div_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE job_titles (
    job_title_id INT PRIMARY KEY AUTO_INCREMENT,
    job_title_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE address (
    emp_id INT PRIMARY KEY,
    street VARCHAR(255),
    city_id INT,
    state_id INT,
    zip VARCHAR(10),
    gender VARCHAR(20),
    identified_race VARCHAR(50),
    mobile_phone VARCHAR(20),
    FOREIGN KEY (emp_id) REFERENCES employees(emp_id),
    FOREIGN KEY (city_id) REFERENCES cities(city_id),
    FOREIGN KEY (state_id) REFERENCES states(state_id)
);

CREATE TABLE employee_division (
    emp_id INT NOT NULL,
    div_id INT NOT NULL,
    PRIMARY KEY (emp_id, div_id),
    FOREIGN KEY (emp_id) REFERENCES employees(emp_id),
    FOREIGN KEY (div_id) REFERENCES division(div_id)
);

CREATE TABLE employee_job_titles (
    emp_id INT NOT NULL,
    job_title_id INT NOT NULL,
    PRIMARY KEY (emp_id, job_title_id),
    FOREIGN KEY (emp_id) REFERENCES employees(emp_id),
    FOREIGN KEY (job_title_id) REFERENCES job_titles(job_title_id)
);

CREATE TABLE payroll (
    pay_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_id INT NOT NULL,
    pay_date DATE NOT NULL,
    gross_pay DECIMAL(10, 2) NOT NULL,
    net_pay DECIMAL(10, 2) NOT NULL,
    tax_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (emp_id) REFERENCES employees(emp_id)
);