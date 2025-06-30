

INSERT INTO sport (id, nome,immagine) VALUES (1,'Calcio a otto','sport_calcio_8.jpg');
INSERT INTO sport (id, nome,immagine) VALUES (2,'Calcetto','sport_calcio_5.jpg');
INSERT INTO sport (id, nome,immagine) VALUES (3,'Tennis','sport_tennis.jpg');
INSERT INTO sport (id, nome,immagine) VALUES (4,'Pallavolo','sport_pallavolo.jpeg');
INSERT INTO sport (id, nome,immagine) VALUES (5,'Basket','sport_basket.png');
INSERT INTO sport (id, nome,immagine) VALUES (6,'Padel','sport_padel.jpeg');
INSERT INTO sport (id, nome,immagine) VALUES (7,'Badminton','sport_badminton.jpeg');
INSERT INTO sport (id, nome,immagine) VALUES (8,'Rugby a sette','sport_rugby.jpeg');
INSERT INTO sport (id, nome,immagine) VALUES (9,'Beach Volley','sport_beachVolley.jpeg');
INSERT INTO sport (id, nome,immagine) VALUES (10,'Hockey su prato','sport_hockeySuPrato.jpeg');







INSERT INTO users (id, name, surname, email, eta) VALUES (100, 'Mario', 'Rossi', 'admin@example.com', 40);
INSERT INTO credentials (id, username, password, role, user_id) VALUES (200, 'adm', '$2a$12$s3QTUipQcQAOXN0aZ0scie6KFTZ9ISeU6nnIUmeeXwgrL4AsnJ4.u', 'ROLE_ADMIN', 100);
INSERT INTO users (id, name, surname, email, eta) VALUES (101, 'Giulia', 'Bianchi', 'user@example.com', 28);
INSERT INTO credentials (id, username, password, role, user_id) VALUES (201, 'ute', '$2a$12$x/pn.th/advauZHiRKICZer7ObgOzLpyOwZdZ4GrrOaaGKgS4yeee', 'ROLE_USER', 101);

