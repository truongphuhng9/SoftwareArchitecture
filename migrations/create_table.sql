CREATE TABLE users
(
    id       INT NOT NULL AUTO_INCREMENT,
    username varchar(100) UNIQUE,
    fullname varchar(100),
    password varchar(100),

    PRIMARY KEY (id)
);

CREATE TABLE tasks
(
    id       INT NOT NULL AUTO_INCREMENT,
    userId   INT NOT NULL,
    taskName VARCHAR(100),
    taskDesc VARCHAR(100),

    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES users (id)
);