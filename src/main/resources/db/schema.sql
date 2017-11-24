DROP TABLE IF EXISTS vacancies;

CREATE TABLE vacancies
(
  id     INTEGER PRIMARY KEY AUTO_INCREMENT,
  name   VARCHAR(100) NOT NULL,
  date   TIMESTAMP NOT NULL,
  employer VARCHAR(60) NOT NULL,
  salary VARCHAR(30)
);