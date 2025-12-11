USE employeedata;

INSERT INTO states (state_code, state_name) VALUES
('AL', 'Alabama'), ('AK', 'Alaska'), ('AZ', 'Arizona'), ('AR', 'Arkansas'), ('CA', 'California'),
('CO', 'Colorado'), ('CT', 'Connecticut'), ('DE', 'Delaware'), ('FL', 'Florida'), ('GA', 'Georgia'),
('HI', 'Hawaii'), ('ID', 'Idaho'), ('IL', 'Illinois'), ('IN', 'Indiana'), ('IA', 'Iowa'),
('KS', 'Kansas'), ('KY', 'Kentucky'), ('LA', 'Louisiana'), ('ME', 'Maine'), ('MD', 'Maryland'),
('MA', 'Massachusetts'), ('MI', 'Michigan'), ('MN', 'Minnesota'), ('MS', 'Mississippi'), ('MO', 'Missouri'),
('MT', 'Montana'), ('NE', 'Nebraska'), ('NV', 'Nevada'), ('NH', 'New Hampshire'), ('NJ', 'New Jersey'),
('NM', 'New Mexico'), ('NY', 'New York'), ('NC', 'North Carolina'), ('ND', 'North Dakota'), ('OH', 'Ohio'),
('OK', 'Oklahoma'), ('OR', 'Oregon'), ('PA', 'Pennsylvania'), ('RI', 'Rhode Island'), ('SC', 'South Carolina'),
('SD', 'South Dakota'), ('TN', 'Tennessee'), ('TX', 'Texas'), ('UT', 'Utah'), ('VT', 'Vermont'),
('VA', 'Virginia'), ('WA', 'Washington'), ('WV', 'West Virginia'), ('WI', 'Wisconsin'), ('WY', 'Wyoming');

INSERT INTO cities (city_name, state_id) VALUES
('New York City', (SELECT state_id FROM states WHERE state_code = 'NY')),
('Los Angeles', (SELECT state_id FROM states WHERE state_code = 'CA')),
('Houston', (SELECT state_id FROM states WHERE state_code = 'TX')),
('Phoenix', (SELECT state_id FROM states WHERE state_code = 'AZ')),
('Philadelphia', (SELECT state_id FROM states WHERE state_code = 'PA')),
('San Antonio', (SELECT state_id FROM states WHERE state_code = 'TX')),
('San Diego', (SELECT state_id FROM states WHERE state_code = 'CA')),
('Dallas', (SELECT state_id FROM states WHERE state_code = 'TX')),
('San Jose', (SELECT state_id FROM states WHERE state_code = 'CA')),
('Austin', (SELECT state_id FROM states WHERE state_code = 'TX')),
('Jacksonville', (SELECT state_id FROM states WHERE state_code = 'FL')),
('Fort Worth', (SELECT state_id FROM states WHERE state_code = 'TX')),
('Columbus', (SELECT state_id FROM states WHERE state_code = 'OH')),
('Indianapolis', (SELECT state_id FROM states WHERE state_code = 'IN')),
('Charlotte', (SELECT state_id FROM states WHERE state_code = 'NC')),
('Chicago', (SELECT state_id FROM states WHERE state_code = 'IL')),
('Miami', (SELECT state_id FROM states WHERE state_code = 'FL')),
('Seattle', (SELECT state_id FROM states WHERE state_code = 'WA')),
('Denver', (SELECT state_id FROM states WHERE state_code = 'CO')),
('Boston', (SELECT state_id FROM states WHERE state_code = 'MA'));

INSERT INTO division (div_name) VALUES
('Human Resources'),
('Engineering'),
('Finance'),
('Operations'),
('Marketing'),
('Sales'),
('Legal'),
('Information Technology');

INSERT INTO job_titles (job_title_name) VALUES
('HR Manager'),
('Senior Software Engineer'),
('Software Engineer'),
('Financial Analyst'),
('Operations Manager'),
('Marketing Manager'),
('Sales Representative'),
('Legal Counsel'),
('IT Support Specialist'),
('Database Administrator'),
('Senior Financial Analyst'),
('Junior Software Engineer');

INSERT INTO employees (emp_id, first_name, last_name, ssn, dob, hire_date, salary, email, password, role) VALUES
(1, 'Jane', 'Smith', '111-22-3333', '1985-03-15', '2015-06-01', 95000.00, 'jane.smith@companyz.com', 'password123', 'ADMIN'),
(2, 'John', 'Doe', '222-33-4444', '1990-07-22', '2018-01-15', 85000.00, 'john.doe@companyz.com', 'password123', 'EMPLOYEE'),
(3, 'Sarah', 'Johnson', '333-44-5555', '1992-11-05', '2020-04-10', 75000.00, 'sarah.johnson@companyz.com', 'password123', 'EMPLOYEE'),
(4, 'Michael', 'Brown', '444-55-6666', '1988-05-20', '2017-03-15', 92000.00, 'michael.brown@companyz.com', 'password123', 'EMPLOYEE'),
(5, 'Emily', 'Davis', '555-66-7777', '1995-09-10', '2021-09-01', 68000.00, 'emily.davis@companyz.com', 'password123', 'EMPLOYEE'),
(6, 'David', 'Wilson', '666-77-8888', '1987-12-03', '2016-05-20', 125000.00, 'david.wilson@companyz.com', 'password123', 'ADMIN'),
(7, 'Lisa', 'Martinez', '777-88-9999', '1993-02-14', '2019-07-15', 72000.00, 'lisa.martinez@companyz.com', 'password123', 'EMPLOYEE'),
(8, 'Robert', 'Garcia', '888-99-0000', '1991-08-25', '2018-11-01', 87000.00, 'robert.garcia@companyz.com', 'password123', 'EMPLOYEE');

INSERT INTO address (emp_id, street, city_id, state_id, zip, gender, identified_race, mobile_phone) VALUES
(1, '123 5th Avenue', (SELECT city_id FROM cities WHERE city_name = 'New York City'), (SELECT state_id FROM states WHERE state_code = 'NY'), '10001', 'Female', 'White', '212-555-0001'),
(2, '456 Sunset Boulevard', (SELECT city_id FROM cities WHERE city_name = 'Los Angeles'), (SELECT state_id FROM states WHERE state_code = 'CA'), '90001', 'Male', 'White', '310-555-0002'),
(3, '789 Main Street', (SELECT city_id FROM cities WHERE city_name = 'Houston'), (SELECT state_id FROM states WHERE state_code = 'TX'), '77001', 'Female', 'Hispanic', '713-555-0003'),
(4, '321 Oak Lane', (SELECT city_id FROM cities WHERE city_name = 'Phoenix'), (SELECT state_id FROM states WHERE state_code = 'AZ'), '85001', 'Male', 'Asian', '602-555-0004'),
(5, '654 Pine Road', (SELECT city_id FROM cities WHERE city_name = 'Philadelphia'), (SELECT state_id FROM states WHERE state_code = 'PA'), '19101', 'Female', 'Black', '215-555-0005'),
(6, '987 Elm Street', (SELECT city_id FROM cities WHERE city_name = 'San Antonio'), (SELECT state_id FROM states WHERE state_code = 'TX'), '78001', 'Male', 'Hispanic', '210-555-0006'),
(7, '147 Maple Drive', (SELECT city_id FROM cities WHERE city_name = 'San Diego'), (SELECT state_id FROM states WHERE state_code = 'CA'), '92101', 'Female', 'Asian', '619-555-0007'),
(8, '258 Cedar Lane', (SELECT city_id FROM cities WHERE city_name = 'Dallas'), (SELECT state_id FROM states WHERE state_code = 'TX'), '75201', 'Male', 'White', '972-555-0008');

INSERT INTO employee_division (emp_id, div_id) VALUES
(1, (SELECT div_id FROM division WHERE div_name = 'Human Resources')),
(2, (SELECT div_id FROM division WHERE div_name = 'Engineering')),
(3, (SELECT div_id FROM division WHERE div_name = 'Finance')),
(4, (SELECT div_id FROM division WHERE div_name = 'Operations')),
(5, (SELECT div_id FROM division WHERE div_name = 'Marketing')),
(6, (SELECT div_id FROM division WHERE div_name = 'Human Resources')),
(7, (SELECT div_id FROM division WHERE div_name = 'Engineering')),
(8, (SELECT div_id FROM division WHERE div_name = 'Finance'));

INSERT INTO employee_job_titles (emp_id, job_title_id) VALUES
(1, (SELECT job_title_id FROM job_titles WHERE job_title_name = 'HR Manager')),
(2, (SELECT job_title_id FROM job_titles WHERE job_title_name = 'Senior Software Engineer')),
(3, (SELECT job_title_id FROM job_titles WHERE job_title_name = 'Financial Analyst')),
(4, (SELECT job_title_id FROM job_titles WHERE job_title_name = 'Operations Manager')),
(5, (SELECT job_title_id FROM job_titles WHERE job_title_name = 'Marketing Manager')),
(6, (SELECT job_title_id FROM job_titles WHERE job_title_name = 'HR Manager')),
(7, (SELECT job_title_id FROM job_titles WHERE job_title_name = 'Software Engineer')),
(8, (SELECT job_title_id FROM job_titles WHERE job_title_name = 'Senior Financial Analyst'));

INSERT INTO payroll (emp_id, pay_date, gross_pay, net_pay, tax_amount) VALUES
(1, '2025-01-15', 3653.85, 2850.00, 803.85),
(1, '2025-01-31', 3653.85, 2850.00, 803.85),
(1, '2025-02-15', 3653.85, 2850.00, 803.85),
(2, '2025-01-15', 3269.23, 2550.00, 719.23),
(2, '2025-01-31', 3269.23, 2550.00, 719.23),
(2, '2025-02-15', 3269.23, 2550.00, 719.23),
(3, '2025-01-15', 2884.62, 2250.00, 634.62),
(3, '2025-01-31', 2884.62, 2250.00, 634.62),
(3, '2025-02-15', 2884.62, 2250.00, 634.62),
(4, '2025-01-15', 3538.46, 2750.00, 788.46),
(4, '2025-01-31', 3538.46, 2750.00, 788.46),
(4, '2025-02-15', 3538.46, 2750.00, 788.46),
(5, '2025-01-15', 2615.38, 2050.00, 565.38),
(5, '2025-01-31', 2615.38, 2050.00, 565.38),
(5, '2025-02-15', 2615.38, 2050.00, 565.38),
(6, '2025-01-15', 4807.69, 3750.00, 1057.69),
(6, '2025-01-31', 4807.69, 3750.00, 1057.69),
(6, '2025-02-15', 4807.69, 3750.00, 1057.69),
(7, '2025-01-15', 2769.23, 2150.00, 619.23),
(7, '2025-01-31', 2769.23, 2150.00, 619.23),
(7, '2025-02-15', 2769.23, 2150.00, 619.23),
(8, '2025-01-15', 3346.15, 2600.00, 746.15),
(8, '2025-01-31', 3346.15, 2600.00, 746.15),
(8, '2025-02-15', 3346.15, 2600.00, 746.15);
