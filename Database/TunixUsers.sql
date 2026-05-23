CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'password123';

GRANT ALL PRIVILEGES ON Tunix.* TO 'appuser'@'localhost';

FLUSH PRIVILEGES;