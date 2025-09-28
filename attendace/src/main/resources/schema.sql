CREATE TABLE Student (
    StudentID INTEGER PRIMARY KEY AUTOINCREMENT,
    EMPLID INTEGER NOT NULL UNIQUE,
    StudentFName TEXT NOT NULL,
    StudentLName TEXT NOT NULL,
    StudentEmail TEXT NOT NULL UNIQUE
);

CREATE TABLE Course (
    CourseID INTEGER PRIMARY KEY AUTOINCREMENT,
    CourseCode TEXT NOT NULL,
    CourseName TEXT NOT NULL
);

CREATE TABLE EnrolledIn (
    StudentID INTEGER,
    CourseID INTEGER,
    PRIMARY KEY (StudentID, CourseID),
    FOREIGN KEY (StudentID) REFERENCES Student(StudentID),
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID)
);

CREATE TABLE Session (
    SessionDate TEXT,
    CourseID INTEGER,
    PRIMARY KEY (SessionDate, CourseID),
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID)
);

CREATE TABLE Attendance (
    SessionDate TEXT,
    CourseID INTEGER,
    StudentID INTEGER,
    AStatus TEXT NOT NULL CHECK (AStatus IN ('Present', 'Absent')),
    PRIMARY KEY (SessionDate, CourseID, StudentID),
    FOREIGN KEY (SessionDate, CourseID) REFERENCES Session(SessionDate, CourseID),
    FOREIGN KEY (StudentID) REFERENCES Student(StudentID)
);