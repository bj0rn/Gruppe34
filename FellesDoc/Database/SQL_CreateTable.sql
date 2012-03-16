CREATE TABLE User
(Username Varchar(20) NOT NULL,
Password Varchar(20) NOT NULL,
Name Varchar(50) NOT NULL,
Age Int NOT NULL,
PhoneNumber Int NOT NULL,
Email Varchar(30) NOT NULL,
PRIMARY KEY(Username));

CREATE TABLE Calendar
(CalendarID Int NOT NULL,
Username Varchar(20),
PRIMARY KEY(CalendarID),
FOREIGN KEY(Username) references User(Username)
        ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE Shows
(Username Varchar(20),
CalendarID Int,
PRIMARY KEY(Username, CalendarID),
FOREIGN KEY(Username) references User(Username)
        ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(CalendarID) references Calendar(CalendarID)
        ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE Location
(LocationID int NOT NULL,
PRIMARY KEY(LocationID));

CREATE TABLE CalendarEntry
(CalendarEntryID Int NOT NULL,
TimeStart Datetime,
TimeEnd Datetime,
TimeCreated Datetime,
Description Varchar(100),
EntryType ENUM('Meeting', 'Appointment'),
LocationID Int,
PRIMARY KEY(CalendarEntryID),
FOREIGN KEY(LocationID) references Location(LocationID)
        ON UPDATE CASCADE);

CREATE TABLE Contains
(Role Enum("Owner", "Participant") NOT NULL,
State Enum("Accepted", "Rejected", "Pending") NOT NULL,
CalendarID Int,
CalendarEntryID Int,
PRIMARY KEY(CalendarID, CalendarEntryID),
FOREIGN KEY(CalendarID) references Calendar(CalendarID)
        ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(CalendarEntryID) references CalendarEntry(CalendarEntryID)
        ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE Place
(Description Varchar(100),
LocationID INT NOT NULL,
PRIMARY KEY (LocationID),
FOREIGN KEY(LocationID) references Location(LocationID)
        ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE Room
(RoomName Varchar(20) NOT NULL PRIMARY KEY,
Description Varchar(100),
Capacity Int NOT NULL,
LocationID INT NOT NULL,
FOREIGN KEY(LocationID) references Location(LocationID)
        ON UPDATE CASCADE ON DELETE CASCADE);
