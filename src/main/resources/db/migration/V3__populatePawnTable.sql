INSERT INTO pawn (id, name, val, x, y, speed) VALUES (0, 'Billy', 1, 0, 0, 1000);
INSERT INTO pawn (id, name, val, x, y, speed) VALUES (1, 'Jimmy', 1, 0, 0, 1000);


INSERT INTO EFFECT_ON_PAWN VALUES (0, 'INCREMENT_VALUE', 0);
INSERT INTO EFFECT_ON_PAWN VALUES (1, 'INCREMENT_VALUE', 1);

INSERT INTO increment_value_on_pawn VALUES (0, 20);
INSERT INTO increment_value_on_pawn VALUES (1, 20);


INSERT INTO EFFECT_ON_PAWN VALUES (2, 'MOVE', 0);

INSERT INTO MOVE_ON_PAWN VALUES (2, 10000, 10000);

ALTER SEQUENCE seq_effect_on_pawn RESTART WITH 3;