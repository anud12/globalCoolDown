CREATE SEQUENCE seq_action_on_pawn;
CREATE TABLE action_on_pawn (
  id             BIGINT PRIMARY KEY,
  name           VARCHAR(25) NOT NULL,
  save_date_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  pawn_id        BIGINT      NOT NULL REFERENCES pawn (id),
  parent_id      BIGINT REFERENCES action_on_pawn (id)
);


CREATE SEQUENCE seq_effect_on_pawn;
CREATE TABLE effect_on_pawn (
  id             BIGINT PRIMARY KEY,
  type           VARCHAR(255) NOT NULL,
  pawn_id        BIGINT      NOT NULL REFERENCES pawn (id),
  age            INT         NOT NULL,
  action_id      BIGINT REFERENCES action_on_pawn (id),
  is_side_effect BOOLEAN     NOT NULL
);

CREATE TABLE increment_value_on_pawn (
  id       BIGINT PRIMARY KEY REFERENCES effect_on_pawn (id),
  duration INT NOT NULL,
  rate     INT NOT NULL
);

CREATE TABLE move_on_pawn (
  id BIGINT PRIMARY KEY REFERENCES effect_on_pawn (id),
  x  BIGINT NOT NULL,
  y  BIGINT NOT NULL
);

CREATE TABLE create_pawn_on_pawn(
  id BIGINT PRIMARY KEY REFERENCES effect_on_pawn(id),
  pawn_generator VARCHAR(255) NOT NULL
);

CREATE SEQUENCE seq_condition_on_pawn;
CREATE TABLE condition_on_pawn (
  id         BIGINT PRIMARY KEY,
  attribute  VARCHAR(255),
  comparator VARCHAR(255),
  value      BIGINT NOT NULL,
  action_id  BIGINT REFERENCES action_on_pawn (id)
);