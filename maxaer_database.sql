DROP DATABASE if exists maxaer;

CREATE DATABASE maxaer;

USE maxaer;

CREATE TABLE User (
	userID int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	uname varchar(20) NOT NULL,
    passHash varChar(50) NOT NULL
);

CREATE TABLE UserStats (
	statID int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT, 
    userID int(11) NOT NULL, 
    deaths int(11) NOT NULL DEFAULT 0, 
    tplayed int(11) NOT NULL DEFAULT 0,
    wins int(11) NOT NULL DEFAULT 0,
    losses int(11) NOT NULL DEFAULT 0,
    FOREIGN KEY fk1(userID) REFERENCES User(userID)
);

CREATE TABLE HighScores(
	scoreID int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    userID int(11) NOT NULL,
    score int(11) NOT NULL, 
    FOREIGN KEY fk1(userID) REFERENCES User(userID)
);




