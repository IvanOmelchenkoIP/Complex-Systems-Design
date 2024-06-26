CREATE DATABASE TESTDB;

CREATE TABLE IF NOT EXISTS Orders (
	id int NOT NULL,
	name varchar(255) NOT NULL,
	priority int NOT NULL,
	CONSTRAINT PK_Orders PRIMARY KEY(id)
);

CREATE USER testuser WITH PASSWORD 'testpass';

GRANT ALL PRIVILEGES ON DATABASE TESTDB TO testuser;

GRANT ALL PRIVILEGES ON TABLE Oders to testuser;

