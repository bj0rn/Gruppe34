CREATE TABLE "User" 
(Username Varchar(20) NOT NULL,
Password Varchar(20) NOT NULL,
Name Varchar(50) NOT NULL,
Age Int NOT NULL,
PhoneNumber Int NOT NULL,
Email Varchar(30) NOT NULL,
PRIMARY KEY(Username))

CREATE TABLE "Calendar"
(CalendarID Int NOT NULL,
Username Varchar(20),
PRIMARY KEY(CalendarID),
FOREIGN KEY(Username) references User(Username))

CREATE TABLE "Shows"
(Username Varchar(20),
CalendarID Int,
PRIMARY KEY(Username, CalendarID),
FOREIGN KEY(Username) references User(Username),
FOREIGN KEY(CalendarID) references Calendar(CalendarID))

CREATE TABLE "CalendarEntry"
(CalendarEntryID Int NOT NULL,
TimeStart Datetime,
TimeEnd Datetime,
TimeCreated Datetime NOT NULL DEFAULT GET_DATE(),
Description Varchar(100),
EntryType ENUM('Meeting', 'Appointment'),
LocationID Int,
PRIMARY KEY(CalendarEntryID)
FOREIGN KEY(LocationID) references Location)

CREATE TABLE "Contains"
(Role Enum("Owner", "Participant") NOT NULL,
State Enum("Accepted", "Rejected", "Pending") NOT NULL
CalendarID Int,
CalendarEntryID Int,
PRIMARY KEY(CalendarID, CalendarEntryID),
FOREIGN KEY(CalendarID) references Calendar(CalendarID),
FOREIGN KEY(CalendarEntryID) references CalendarEntry(CalendarEntryID))

CREATE TABLE "Location"
(LocationID int NOT NULL,
PRIMARY KEY(LocationID))

CREATE TABLE "Place"
(Description Varchar(100),
FOREIGN KEY(LocationID) references Location(LocationID))

CREATE TABLE "ROOM"
(RoomName Varchar(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
Description Varchar(100),
Capacity Int NOT NULL,
FOREIGN KEY(LocationID) references Location(LocationID))

