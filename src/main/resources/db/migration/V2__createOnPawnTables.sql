CREATE SEQUENCE seq_effect_on_pawn;

CREATE TABLE effect_on_pawn (
  id      BIGINT PRIMARY KEY,
  type    VARCHAR(25) NOT NULL,
  pawn_id BIGINT      NOT NULL REFERENCES pawn (id)
);

CREATE TABLE increment_value_on_pawn (
  id       BIGINT PRIMARY KEY,
  duration INT NOT NULL
);

CREATE SEQUENCE seq_move_action;
CREATE TABLE move_on_pawn (
  id BIGINT PRIMARY KEY,
  x  BIGINT NOT NULL,
  y  BIGINT NOT NULL
)