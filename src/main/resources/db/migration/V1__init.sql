
-- Initial database schema

-- TABLE: departments
CREATE TABLE departments (
                             id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(255)
);


-- TABLE: adresses
CREATE TABLE adresses (
                          id BIGSERIAL PRIMARY KEY,
                          street VARCHAR(255) NOT NULL,
                          city VARCHAR(255) NOT NULL,
                          postalcode VARCHAR(255) NOT NULL
);



-- TABLE: projects
CREATE TABLE projects (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255)
);



-- TABLE: employees
CREATE TABLE employees (
                           id BIGSERIAL PRIMARY KEY,

                           employee_id VARCHAR(255) NOT NULL UNIQUE,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) NOT NULL UNIQUE,
                           phone VARCHAR(255) NOT NULL UNIQUE,
                           job_title VARCHAR(255) NOT NULL,
                           hire_date DATE NOT NULL,
                           salary DOUBLE PRECISION NOT NULL,

                           adresse_id BIGINT,
                           department_id BIGINT NOT NULL,
                           manager_id BIGINT,

                           CONSTRAINT fk_employee_address
                               FOREIGN KEY (adresse_id)
                                   REFERENCES adresses(id),

                           CONSTRAINT fk_employee_department
                               FOREIGN KEY (department_id)
                                   REFERENCES departments(id),

                           CONSTRAINT fk_employee_manager
                               FOREIGN KEY (manager_id)
                                   REFERENCES employees(id)
);



-- TABLE: employee_projects
-- Many-to-Many relationship
CREATE TABLE employee_projects (
                                   employee_id BIGINT NOT NULL,
                                   project_id BIGINT NOT NULL,

                                   CONSTRAINT pk_employee_projects
                                       PRIMARY KEY (employee_id, project_id),

                                   CONSTRAINT fk_employee_projects_employee
                                       FOREIGN KEY (employee_id)
                                           REFERENCES employees(id),

                                   CONSTRAINT fk_employee_projects_project
                                       FOREIGN KEY (project_id)
                                           REFERENCES projects(id)
);