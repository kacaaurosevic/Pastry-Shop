DROP DATABASE IF EXISTS seminarski;
CREATE DATABASE seminarski CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE seminarski;

CREATE TABLE mesto (
  idMesto BIGINT NOT NULL AUTO_INCREMENT,
  nazivMesta VARCHAR(100) NOT NULL,
  PRIMARY KEY (idMesto)
);

CREATE TABLE kupac (
  idKupac BIGINT NOT NULL AUTO_INCREMENT,
  imePrezimeKupca VARCHAR(100) NOT NULL,
  kontakt VARCHAR(50),
  mesto BIGINT NOT NULL,
  PRIMARY KEY (idKupac),
  FOREIGN KEY (mesto) REFERENCES mesto (idMesto)
);

CREATE TABLE poslasticar (
  idPoslasticar BIGINT NOT NULL AUTO_INCREMENT,
  imePrezime VARCHAR(100) NOT NULL,
  datumRodjenja DATETIME,
  korisnickoIme VARCHAR(50) NOT NULL UNIQUE,
  sifra VARCHAR(100) NOT NULL,
  PRIMARY KEY (idPoslasticar)
);

CREATE TABLE poslastica (
  idPoslastica BIGINT NOT NULL AUTO_INCREMENT,
  naziv VARCHAR(100) NOT NULL,
  opis VARCHAR(255),
  cenaP INT NOT NULL,
  PRIMARY KEY (idPoslastica)
);

CREATE TABLE smena (
  idSmena BIGINT NOT NULL AUTO_INCREMENT,
  nazivSmene VARCHAR(50) NOT NULL,
  pocetakSmene TIME NOT NULL,
  krajSmene TIME NOT NULL,
  PRIMARY KEY (idSmena)
);

CREATE TABLE poslasticarsmena (
  poslasticar BIGINT NOT NULL,
  smena BIGINT NOT NULL,
  datumPoslasticarSmena DATETIME NOT NULL,
  PRIMARY KEY (poslasticar, smena, datumPoslasticarSmena),
  FOREIGN KEY (poslasticar) REFERENCES poslasticar (idPoslasticar),
  FOREIGN KEY (smena) REFERENCES smena (idSmena)
);

CREATE TABLE porudzbina (
  idPorudzbina BIGINT NOT NULL AUTO_INCREMENT,
  ukupanIznos DOUBLE NOT NULL DEFAULT 0,
  obradjena BOOLEAN NOT NULL DEFAULT FALSE,
  poslasticar BIGINT NOT NULL,
  kupac BIGINT NOT NULL,
  PRIMARY KEY (idPorudzbina),
  FOREIGN KEY (poslasticar) REFERENCES poslasticar (idPoslasticar),
  FOREIGN KEY (kupac) REFERENCES kupac (idKupac)
);

CREATE TABLE stavkaporudzbina (
  rb BIGINT NOT NULL,
  porudzbina BIGINT NOT NULL,
  osnovnaCena INT NOT NULL,
  kolicina INT NOT NULL,
  iznosSP DOUBLE NOT NULL,
  poslastica BIGINT NOT NULL,
  PRIMARY KEY (rb, porudzbina),
  FOREIGN KEY (porudzbina) REFERENCES porudzbina (idPorudzbina) ON DELETE CASCADE,
  FOREIGN KEY (poslastica) REFERENCES poslastica (idPoslastica)
);

INSERT INTO mesto (nazivMesta) VALUES ('Beograd'), ('Novi Sad'), ('Niš');

INSERT INTO kupac (imePrezimeKupca, kontakt, mesto) VALUES
('Marko Marković', '060111222', 1),
('Ana Anić', '061333444', 2);

INSERT INTO poslasticar (imePrezime, datumRodjenja, korisnickoIme, sifra) VALUES
('Pera Perić', '1990-05-15 00:00:00', 'pera', 'pera123');

INSERT INTO poslastica (naziv, opis, cenaP) VALUES
('Čokoladna torta', 'Torta sa čokoladom i lešnicima', 450),
('Tiramisu', 'Klasični italijanski desert', 380),
('Baklava', 'Slatkiš sa orasima i sirupom', 220);

INSERT INTO smena (nazivSmene, pocetakSmene, krajSmene) VALUES
('Prva smena', '07:00:00', '15:00:00'),
('Druga smena', '15:00:00', '23:00:00');

INSERT INTO poslasticarsmena (poslasticar, smena, datumPoslasticarSmena) VALUES
(1, 1, '2026-10-12 07:00:00');

INSERT INTO porudzbina (ukupanIznos, obradjena, poslasticar, kupac) VALUES (1280, FALSE, 1, 1);
INSERT INTO stavkaporudzbina (rb, porudzbina, osnovnaCena, kolicina, iznosSP, poslastica) VALUES
(1, 1, 450, 2, 900, 1),
(2, 1, 380, 1, 380, 2);
