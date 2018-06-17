INSERT INTO users (id, username, security_role) VALUES (0, 'admin', 'USER');
INSERT INTO users (id, username, security_role) VALUES (1, 'gigi', 'USER');
ALTER SEQUENCE users_seq START 2;

INSERT INTO pawn (id, user_id, name, character_code, val, x, y, speed, VERSION) VALUES (0, 0, 'Billy', 65, 10000, 15000, 10000, 100, 0);
INSERT INTO pawn (id, user_id, name, character_code, val, x, y, speed, VERSION) VALUES (1, 0, 'Jimmy', 66, 10000, 20000, 10000, 100, 0);

INSERT INTO pawn (id, user_id, name, character_code, val, x, y, speed, VERSION) VALUES (2, 1, 'Bob', 67, 10000, 30000, 10000, 100, 0);
INSERT INTO pawn (id, user_id, name, character_code, val, x, y, speed, VERSION) VALUES (3, 1, 'George', 68, 10000, 10000, 30000, 100, 0);


ALTER SEQUENCE pawn_seq START 4;