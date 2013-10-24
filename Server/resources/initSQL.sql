/*-=-=-=-=-=-=-=-=-=-=
 * SESSIONS
 -=-=-=-=-=-=-=-=-=-=*/

CREATE TABLE IF NOT EXISTS Session (
  Token    VARCHAR PRIMARY KEY NOT NULL,
  UserId   INTEGER,
  Lifetime BIGINT,
  FOREIGN KEY (UserId) REFERENCES User (UserId)
);

/*-=-=-=-=-=-=-=-=-=-=
 * CONTACTS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS Contact (
  ContactId  INTEGER PRIMARY KEY AUTOINCREMENT,
  Name       VARCHAR NOT NULL,
  MiddleName VARCHAR,
  Surname    VARCHAR NOT NULL,
  Company    VARCHAR,
  Phone      VARCHAR,
  Email      VARCHAR,
  Address    VARCHAR,
  Suburb     VARCHAR,
  City       VARCHAR,
  Country    VARCHAR,
  Post       VARCHAR,
  State      VARCHAR,
  isActive   BOOLEAN DEFAULT 1
);

/*-=-=-=-=-=-=-=-=-=-=
 * USERS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS User (
  UserId       INTEGER PRIMARY KEY AUTOINCREMENT,
  ContactId    INTEGER,
  User         VARCHAR,
  Password     VARCHAR,
  isAdmin      BOOLEAN DEFAULT 0,
  StrategyId   INTEGER,
  Strategy     VARCHAR DEFAULT 'local',
  StrategyData VARCHAR,
  Token        VARCHAR UNIQUE NOT NULL,
  FOREIGN KEY (ContactId) REFERENCES Contact (ContactId)
);

/*-=-=-=-=-=-=-=-=-=-=
 * SERVICE PROVIDERS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS Provider (
  ProviderId INTEGER PRIMARY KEY AUTOINCREMENT,
  ContactId  INTEGER,
  Biography  VARCHAR,
  Portrait   VARCHAR,
  Initiated  DATE NOT NULL,
  Terminated DATE,
  isActive   BOOLEAN DEFAULT 1,
  Color      VARCHAR DEFAULT '#006dcc',
  FOREIGN KEY (ContactId) REFERENCES Contact (ContactId)
);

/*-=-=-=-=-=-=-=-=-=-=
 * SERVICE HOURS
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS Provider_Hours (
  ProviderId INTEGER,
  Day        INTEGER NOT NULL,
  Start      TIME    NOT NULL,
  BreakStart TIME    NOT NULL,
  BreakEnd   TIME    NOT NULL,
  End        TIME    NOT NULL,
  FOREIGN KEY (ProviderId) REFERENCES Provider (ProviderId)
);

/*-=-=-=-=-=-=-=-=-=-=
 * APPOINTMENT TYPE
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS Appointment_Type (
  TypeId      INTEGER PRIMARY KEY AUTOINCREMENT,
  Description VARCHAR,
  Duration    TIME,
  isAllDay    BOOLEAN DEFAULT 0,
  isActive   BOOLEAN DEFAULT 1
);

/*-=-=-=-=-=-=-=-=-=-=
 * APPOINTMENT
 -=-=-=-=-=-=-=-=-=-=*/
CREATE TABLE IF NOT EXISTS Appointment (
  AppointmentId INTEGER PRIMARY KEY AUTOINCREMENT,
  TypeId        INTEGER,
  ContactId     INTEGER,
  ProviderId    INTEGER,
  Date          DATE NOT NULL,
  Time          TIME NOT NULL,
  FOREIGN KEY (AppointmentId) REFERENCES Appointment_Type (AppointmentId),
  FOREIGN KEY (ContactId) REFERENCES Contact (ContactId),
  FOREIGN KEY (ProviderId) REFERENCES Provider (ProviderId)
);