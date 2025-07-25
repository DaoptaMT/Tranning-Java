-- Create a new user
CREATE USER 'root'@'%' IDENTIFIED BY '123456';

-- Grant privileges to that user (optional)
GRANT ALL PRIVILEGES ON pharmacy.* TO 'root'@'%';

-- Apply changes
FLUSH PRIVILEGES;
