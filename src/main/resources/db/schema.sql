DROP TABLE IF EXISTS vacancies;

CREATE TABLE vacancies
(
  id     INTEGER PRIMARY KEY AUTO_INCREMENT,
  name   VARCHAR(1000) NOT NULL,
  date   TIMESTAMP NOT NULL,
  employer VARCHAR(1000) NOT NULL,
  salary VARCHAR(100)
);