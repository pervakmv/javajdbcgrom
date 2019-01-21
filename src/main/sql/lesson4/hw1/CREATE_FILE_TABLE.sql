CREATE TABLE FILE_(
ID NUMBER,
CONSTRAINT FILE_ID PRIMARY KEY(ID),
NAME NVARCHAR2(10),
FORMAT NVARCHAR2(4),
FILESIZE NUMBER,
STORAGE_ID NUMBER,
CONSTRAINT STORAGE_FK FOREIGN KEY(STORAGE_ID) REFERENCES STORAGE(ID)
);

CREATE TABLE STORAGE(
ID NUMBER,
CONSTRAINT STORAGE_ID PRIMARY KEY(ID),
FORMATSUPPORTED NVARCHAR2(21),
STORAGECOUNTRY NVARCHAR2(30),
STORAGEMAXSIZE NUMBER
);

CREATE TABLE HISTORY(
ID NUMBER,
CONSTRAINT HISTORY_ID PRIMARY KEY(ID),
OPERATIONTYPE NVARCHAR2(20),
STATUS NVARCHAR2(10)
);