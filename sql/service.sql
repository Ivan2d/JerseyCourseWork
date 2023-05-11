DROP DATABASE IF EXISTS service;
CREATE DATABASE service;
USE service;

CREATE TABLE `user` (
   id INT NOT NULL AUTO_INCREMENT,
   firstname VARCHAR(50) NOT NULL,
   lastname VARCHAR(50) NOT NULL,
  `login` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `mail` VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE customer (
  userid INT NOT NULL,
  PRIMARY KEY (userid),
  FOREIGN KEY (userid) REFERENCES `user` (id) ON DELETE CASCADE
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE executor (
  userid INT NOT NULL,
  PRIMARY KEY (userid),
  FOREIGN KEY (userid) REFERENCES `user` (id) ON DELETE CASCADE
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE operator (
  userid INT NOT NULL,
  PRIMARY KEY (userid),
  FOREIGN KEY (userid) REFERENCES `user` (id) ON DELETE CASCADE
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE application (
  id INT NOT NULL AUTO_INCREMENT,
  type VARCHAR(50) NOT NULL,
  status VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE customer_application (
  id INT NOT NULL AUTO_INCREMENT,
  customerId INT(11) NOT NULL,
  applicationId INT(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY customer_application (customerId, applicationId),
  FOREIGN KEY (applicationId) REFERENCES application (id) ON DELETE CASCADE,
  FOREIGN KEY (customerId) REFERENCES customer (userid) ON DELETE CASCADE
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE executor_application (
  id INT NOT NULL AUTO_INCREMENT,
  executorId INT(11) NOT NULL,
  applicationId INT(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY executor_application (executorId, applicationId),
  FOREIGN KEY (applicationId) REFERENCES application (id) ON DELETE CASCADE,
  FOREIGN KEY (executorId) REFERENCES executor (userid) ON DELETE CASCADE
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE operator_application (
  id INT NOT NULL AUTO_INCREMENT,
  operatorId INT(11) NOT NULL,
  applicationId INT(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY operator_application (operatorId, applicationId),
  FOREIGN KEY (applicationId) REFERENCES application (id) ON DELETE CASCADE,
  FOREIGN KEY (operatorId) REFERENCES operator (userid) ON DELETE CASCADE
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE division (
  id INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE executor_division (
  id INT NOT NULL AUTO_INCREMENT,
  executorId INT(11) NOT NULL,
  divisionId INT(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY executor_division (executorId, divisionId),
  FOREIGN KEY (executorId) REFERENCES executor (userid) ON DELETE CASCADE,
  FOREIGN KEY (divisionId) REFERENCES division (id) ON DELETE CASCADE
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE document (
  id INT NOT NULL AUTO_INCREMENT,
  customerId INT NOT NULL,
  executorId INT NOT NULL,
  applicationId INT NOT NULL,
  PRIMARY KEY (id)
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE session (
  id INT NOT NULL,
  uuid VARCHAR(36),
  PRIMARY KEY (id)
)
CHARACTER SET utf8
COLLATE utf8_general_ci;