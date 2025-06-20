INSERT INTO associazione (id, nome, indirizzo) VALUES (-1, 'ASRoma3','Via Pasquale Franchino 180');
INSERT INTO associazione (id, nome, indirizzo) VALUES (-2,'Certosa' ,'Via CentoCelle 27');
INSERT INTO sport (id, nome) VALUES (1,'Calcio a otto');

insert into campo (capienza,associazione_id,id,sport_id,nome,durata_slot,ora_apertura,ora_chiusura) values (8,-1,-4,1,'A2',60,'09:00:00','18:00:00');


INSERT INTO associazione_sport_list (associazioni_id, sport_list_id) VALUES (-1,1);


