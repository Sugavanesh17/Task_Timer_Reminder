-- 1. Table: Users
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Table: Tasks
CREATE TABLE Tasks (
    task_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    priority VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    due_date DATETIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- 3. Table: Reminders
CREATE TABLE Reminders (
    reminder_id INT AUTO_INCREMENT PRIMARY KEY,
    task_id INT,
    reminder_time DATETIME NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (task_id) REFERENCES Tasks(task_id)
);

-- 4. Table: Timers
CREATE TABLE Timers (
    timer_id INT AUTO_INCREMENT PRIMARY KEY,
    task_id INT,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    elapsed_time INT DEFAULT 0,  -- Time in seconds
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (task_id) REFERENCES Tasks(task_id)
);

--5. create users
--6. create tasks 
--7. timers and remainders are available now
