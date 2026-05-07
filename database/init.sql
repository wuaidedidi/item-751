-- Enterprise Employee Management System - Database Initialization Script

-- Use database
USE ems_db;

-- System User Table
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT 'Username',
    password VARCHAR(100) NOT NULL COMMENT 'Password (MD5)',
    nickname VARCHAR(50) COMMENT 'Nickname',
    avatar VARCHAR(255) COMMENT 'Avatar URL',
    status TINYINT DEFAULT 1 COMMENT 'Status: 0-Disabled, 1-Active',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time',
    INDEX idx_username (username),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='System User Table';

-- Department Table
CREATE TABLE IF NOT EXISTS department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    name VARCHAR(50) NOT NULL COMMENT 'Department Name',
    code VARCHAR(20) COMMENT 'Department Code',
    description VARCHAR(200) COMMENT 'Description',
    parent_id BIGINT DEFAULT NULL COMMENT 'Parent Department ID',
    sort_order INT DEFAULT 0 COMMENT 'Sort Order',
    status TINYINT DEFAULT 1 COMMENT 'Status: 0-Disabled, 1-Active',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time',
    INDEX idx_parent_id (parent_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Department Table';

-- Position Table
CREATE TABLE IF NOT EXISTS position (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    name VARCHAR(50) NOT NULL COMMENT 'Position Name',
    code VARCHAR(20) COMMENT 'Position Code',
    description VARCHAR(200) COMMENT 'Description',
    sort_order INT DEFAULT 0 COMMENT 'Sort Order',
    status TINYINT DEFAULT 1 COMMENT 'Status: 0-Disabled, 1-Active',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time',
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Position Table';

-- Employee Table
CREATE TABLE IF NOT EXISTS employee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    employee_no VARCHAR(20) NOT NULL UNIQUE COMMENT 'Employee No',
    name VARCHAR(50) NOT NULL COMMENT 'Name',
    gender TINYINT DEFAULT 1 COMMENT 'Gender: 0-Female, 1-Male',
    birth_date DATE COMMENT 'Birth Date',
    phone VARCHAR(20) COMMENT 'Phone',
    email VARCHAR(100) COMMENT 'Email',
    id_card VARCHAR(18) COMMENT 'ID Card',
    address VARCHAR(200) COMMENT 'Address',
    department_id BIGINT COMMENT 'Department ID',
    position_id BIGINT COMMENT 'Position ID',
    hire_date DATE COMMENT 'Hire Date',
    status TINYINT DEFAULT 1 COMMENT 'Status: 0-Left, 1-Active',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time',
    INDEX idx_employee_no (employee_no),
    INDEX idx_department_id (department_id),
    INDEX idx_position_id (position_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Employee Table';

-- ================================================
-- Initial Data (English)
-- ================================================

-- Admin Account (Password: 123456 -> MD5: e10adc3949ba59abbe56e057f20f883e)
INSERT INTO sys_user (username, password, nickname, status) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', 'Administrator', 1)
ON DUPLICATE KEY UPDATE password = 'e10adc3949ba59abbe56e057f20f883e';

-- Department Data
INSERT INTO department (name, code, description, parent_id, sort_order, status) VALUES
('Executive Office', 'CEO', 'Top Management', NULL, 1, 1),
('Human Resources', 'HR', 'HR and Recruitment', NULL, 2, 1),
('Finance', 'FIN', 'Financial Management', NULL, 3, 1),
('Engineering', 'RD', 'Product Development', NULL, 4, 1),
('Marketing', 'MKT', 'Marketing and Sales', NULL, 5, 1),
('Operations', 'OPS', 'Daily Operations', NULL, 6, 1),
('Frontend Team', 'RD-FE', 'Frontend Development', 4, 1, 1),
('Backend Team', 'RD-BE', 'Backend Development', 4, 2, 1),
('QA Team', 'RD-QA', 'Quality Assurance', 4, 3, 1),
('Recruiting Team', 'HR-RC', 'Talent Acquisition', 2, 1, 1)
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Position Data
INSERT INTO position (name, code, description, sort_order, status) VALUES
('CEO', 'GM', 'Chief Executive Officer', 1, 1),
('VP', 'DGM', 'Vice President', 2, 1),
('Manager', 'DM', 'Department Manager', 3, 1),
('Senior Engineer', 'SE', 'Senior Technical Staff', 4, 1),
('Engineer', 'ME', 'Technical Staff', 5, 1),
('Junior Engineer', 'JE', 'Junior Technical Staff', 6, 1),
('Product Manager', 'PM', 'Product Planning', 7, 1),
('UI Designer', 'UID', 'UI Design', 8, 1),
('HR Specialist', 'HRS', 'Human Resources', 9, 1),
('Accountant', 'FAS', 'Financial Staff', 10, 1),
('Sales Manager', 'SM', 'Sales Management', 11, 1),
('Sales Rep', 'SR', 'Sales Representative', 12, 1)
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Employee Data
INSERT INTO employee (employee_no, name, gender, birth_date, phone, email, id_card, address, department_id, position_id, hire_date, status) VALUES
('EMP001', 'John Smith', 1, '1980-05-15', '13800138001', 'john.smith@company.com', '110101198005150011', '123 Main Street, Building A', 1, 1, '2015-01-01', 1),
('EMP002', 'Sarah Johnson', 0, '1985-08-20', '13800138002', 'sarah.j@company.com', '110101198508200022', '456 Oak Avenue, Suite 100', 2, 3, '2016-03-15', 1),
('EMP003', 'Michael Brown', 1, '1988-12-10', '13800138003', 'michael.b@company.com', '110101198812100033', '789 Pine Road, Floor 5', 3, 3, '2017-06-01', 1),
('EMP004', 'David Wilson', 1, '1990-03-25', '13800138004', 'david.w@company.com', '110101199003250044', '321 Elm Street, Unit 8', 4, 3, '2018-01-10', 1),
('EMP005', 'Emily Davis', 0, '1992-07-18', '13800138005', 'emily.d@company.com', '110101199207180055', '654 Maple Drive, Apt 12', 5, 3, '2018-08-20', 1),
('EMP006', 'James Miller', 1, '1991-11-05', '13800138006', 'james.m@company.com', '110101199111050066', '987 Cedar Lane, Room 3', 7, 4, '2019-02-14', 1),
('EMP007', 'Lisa Anderson', 0, '1993-04-12', '13800138007', 'lisa.a@company.com', '110101199304120077', '147 Birch Court, Suite 50', 7, 5, '2019-07-01', 1),
('EMP008', 'Robert Taylor', 1, '1994-09-28', '13800138008', 'robert.t@company.com', '110101199409280088', '258 Walnut Way, Building C', 8, 4, '2020-01-06', 1),
('EMP009', 'Jennifer Thomas', 0, '1995-02-14', '13800138009', 'jennifer.t@company.com', '110101199502140099', '369 Spruce Boulevard', 8, 5, '2020-04-20', 1),
('EMP010', 'William Jackson', 1, '1996-06-30', '13800138010', 'william.j@company.com', '110101199606300100', '741 Ash Street, Floor 2', 8, 6, '2020-09-01', 1),
('EMP011', 'Amanda White', 0, '1989-10-22', '13800138011', 'amanda.w@company.com', '110101198910220111', '852 Cherry Avenue', 9, 5, '2019-11-11', 1),
('EMP012', 'Daniel Harris', 1, '1987-01-08', '13800138012', 'daniel.h@company.com', '110101198701080122', '963 Poplar Road', 4, 7, '2018-05-05', 1),
('EMP013', 'Nicole Martin', 0, '1994-08-16', '13800138013', 'nicole.m@company.com', '110101199408160133', '159 Willow Lane', 4, 8, '2020-02-28', 1),
('EMP014', 'Christopher Lee', 1, '1992-12-03', '13800138014', 'chris.l@company.com', '110101199212030144', '357 Oak Park Drive', 10, 9, '2019-06-18', 1),
('EMP015', 'Michelle Garcia', 0, '1991-05-27', '13800138015', 'michelle.g@company.com', '110101199105270155', '468 Sunset Boulevard', 3, 10, '2018-10-10', 1),
('EMP016', 'Kevin Martinez', 1, '1988-03-14', '13800138016', 'kevin.m@company.com', '110101198803140166', '579 River Road', 5, 11, '2017-12-01', 1),
('EMP017', 'Rachel Robinson', 0, '1993-11-09', '13800138017', 'rachel.r@company.com', '110101199311090177', '680 Lake View Drive', 5, 12, '2020-06-15', 1),
('EMP018', 'Steven Clark', 1, '1990-07-21', '13800138018', 'steven.c@company.com', '110101199007210188', '791 Mountain Avenue', 6, 3, '2019-03-25', 1),
('EMP019', 'Laura Lewis', 0, '1995-09-05', '13800138019', 'laura.l@company.com', '110101199509050199', '802 Valley Street', 7, 6, '2021-01-04', 1),
('EMP020', 'Brian Walker', 1, '1986-04-18', '13800138020', 'brian.w@company.com', '110101198604180200', '913 Highland Road', 1, 2, '2016-08-08', 1)
ON DUPLICATE KEY UPDATE name = VALUES(name);
