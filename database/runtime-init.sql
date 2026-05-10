CREATE DATABASE IF NOT EXISTS ems_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root123';
FLUSH PRIVILEGES;
