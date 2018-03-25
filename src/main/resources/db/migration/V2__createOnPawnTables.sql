CREATE SEQUENCE seq_action_on_pawn;
CREATE TABLE action_on_pawn (
  id   BIGINT PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  save_date_time TIMESTAMP NOT NULL,
  parent_id BIGINT REFERENCES action_on_pawn(id)
);

CREATE SEQUENCE seq_effect_on_pawn;
CREATE TABLE effect_on_pawn (
  id        BIGINT PRIMARY KEY,
  type      VARCHAR(25) NOT NULL,
  pawn_id   BIGINT      NOT NULL REFERENCES pawn (id),
  action_id BIGINT REFERENCES action_on_pawn (id)
);

CREATE TABLE increment_value_on_pawn (
  id       BIGINT PRIMARY KEY REFERENCES effect_on_pawn (id),
  duration INT NOT NULL
);

CREATE TABLE move_on_pawn (
  id BIGINT PRIMARY KEY REFERENCES effect_on_pawn (id),
  x  BIGINT NOT NULL,
  y  BIGINT NOT NULL
);