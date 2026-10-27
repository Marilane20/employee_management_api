
-- INSERT ADDRESSES


INSERT INTO adresses (id, street, city, postalcode) VALUES
(1, '123 Main Street', 'New York', '10001'),
(2, '456 Oak Avenue', 'Los Angeles', '90001'),
(3, '789 Pine Road', 'Chicago', '60601'),
(4, '321 Elm Street', 'Houston', '77001'),
(5, '654 Maple Drive', 'Phoenix', '85001'),
(6, '987 Cedar Lane', 'Philadelphia', '19101'),
(7, '147 Birch Boulevard', 'San Antonio', '78201'),
(8, '258 Walnut Way', 'San Diego', '92101');



-- INSERT DEPARTMENTS


INSERT INTO departments (id, name) VALUES
(1, 'Engineering'),
(2, 'Human Resources'),
(3, 'Marketing'),
(4, 'Sales'),
(5, 'Finance');



-- INSERT EMPLOYEES

-- Les managers sont insérés en premier.
-- manager_id = NULL pour les managers.


INSERT INTO employees
(id, employee_id, name, email, phone, job_title, hire_date, salary, adresse_id, department_id, manager_id)
VALUES

-- Managers
(1, 'EMP001', 'John Smith', 'john.smith@company.com',
 '555-0101', 'Engineering Manager', '2020-01-15',
 120000.00, 1, 1, NULL),

(2, 'EMP002', 'Sarah Johnson', 'sarah.johnson@company.com',
 '555-0102', 'HR Manager', '2019-03-20',
 95000.00, 2, 2, NULL),

(3, 'EMP003', 'Michael Brown', 'michael.brown@company.com',
 '555-0103', 'Marketing Manager', '2020-06-10',
 100000.00, 3, 3, NULL),

(4, 'EMP004', 'Emily Davis', 'emily.davis@company.com',
 '555-0104', 'Sales Manager', '2018-11-05',
 110000.00, 4, 4, NULL),

-- Regular Employees
(5, 'EMP005', 'David Wilson', 'david.wilson@company.com',
 '555-0105', 'Senior Software Engineer', '2021-02-01',
 95000.00, 5, 1, 1),

(6, 'EMP006', 'Jennifer Martinez', 'jennifer.martinez@company.com',
 '555-0106', 'Software Engineer', '2022-04-15',
 80000.00, 6, 1, 1),

(7, 'EMP007', 'Robert Garcia', 'robert.garcia@company.com',
 '555-0107', 'HR Specialist', '2021-08-20',
 65000.00, 7, 2, 2),

(8, 'EMP008', 'Lisa Anderson', 'lisa.anderson@company.com',
 '555-0108', 'Marketing Specialist', '2022-01-10',
 70000.00, 8, 3, 3),

(9, 'EMP009', 'William Taylor', 'william.taylor@company.com',
 '555-0109', 'Sales Representative', '2021-05-25',
 75000.00, NULL, 4, 4),

(10, 'EMP010', 'Amanda Thomas', 'amanda.thomas@company.com',
 '555-0110', 'Junior Software Engineer', '2023-03-15',
 65000.00, NULL, 1, 1);


-- =========================================================
-- INSERT PROJECTS
-- =========================================================

INSERT INTO projects (id, name) VALUES
(1, 'Cloud Migration'),
(2, 'Mobile App Development'),
(3, 'Website Redesign'),
(4, 'CRM Implementation'),
(5, 'Data Analytics Platform');


-- =========================================================
-- INSERT EMPLOYEE_PROJECTS
-- =========================================================

INSERT INTO employee_projects (employee_id, project_id) VALUES

-- John Smith
(1, 1),
(1, 2),

-- David Wilson
(5, 1),
(5, 5),

-- Jennifer Martinez
(6, 2),
(6, 5),

-- Amanda Thomas
(10, 2),

-- Michael Brown
(3, 3),

-- Lisa Anderson
(8, 3),

-- Emily Davis
(4, 4),

-- William Taylor
(9, 4);

