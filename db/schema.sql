CREATE TABLE halls (
   id INTEGER PRIMARY KEY CHECK (id > 0),
   client_id INTEGER,
   status INTEGER
);

CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    fio varchar(100) NOT NULL,
    phone varchar(15)
);

INSERT INTO halls (id, status) VALUES (1, 0);
INSERT INTO halls (id, status) VALUES (2, 0);
INSERT INTO halls (id, status) VALUES (3, 0);
INSERT INTO halls (id, status) VALUES (4, 0);
INSERT INTO halls (id, status) VALUES (5, 0);
INSERT INTO halls (id, status) VALUES (6, 0);
INSERT INTO halls (id, status) VALUES (7, 0);
INSERT INTO halls (id, status) VALUES (8, 0);
INSERT INTO halls (id, status) VALUES (9, 0);
