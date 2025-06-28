<<<<<<< HEAD
INSERT INTO associazione (id, nome, indirizzo,immagine) VALUES (-1, 'ASRoma3','Via Pasquale Franchino 180',null);
INSERT INTO associazione (id, nome, indirizzo,immagine) VALUES (-2,'Certosa' ,'Via CentoCelle 27',null);
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
insert into campo (capienza,associazione_id,id,sport_id,nome,durata_slot,ora_apertura,ora_chiusura) values (8,-1,-4,1,'A2',60,'09:00:00','18:00:00');


INSERT INTO associazione_sport_list (associazioni_id, sport_list_id) VALUES (-1,1);





INSERT INTO users (id, name, surname, email, eta) VALUES (100, 'Mario', 'Rossi', 'admin@example.com', 40);
INSERT INTO credentials (id, username, password, role, user_id) VALUES (200, 'adm', '$2a$12$s3QTUipQcQAOXN0aZ0scie6KFTZ9ISeU6nnIUmeeXwgrL4AsnJ4.u', 'ROLE_ADMIN', 100);
INSERT INTO users (id, name, surname, email, eta) VALUES (101, 'Giulia', 'Bianchi', 'user@example.com', 28);
INSERT INTO credentials (id, username, password, role, user_id) VALUES (201, 'ute', '$2a$12$x/pn.th/advauZHiRKICZer7ObgOzLpyOwZdZ4GrrOaaGKgS4yeee', 'ROLE_USER', 101);

=======
>>>>>>> bfe3f2e6096f8f17f1f69286500f4bb714519a86
