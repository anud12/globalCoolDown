CREATE SEQUENCE seq_action_on_pawn;

CREATE TABLE action_on_pawn (
  id BIGINT PRIMARY KEY,
  name VARCHAR2(20) NOT NULL,
);


CREATE SEQUENCE seq_action_effect_on_pawn;

CREATE TABLE action_effect_on_pawn (
  id BIGINT PRIMARY KEY ,
  action_id BIGINT NOT NULL REFERENCES action_on_pawn(id),
  effect_id BIGINT NOT NULL REFERENCES EFFECT_ON_PAWN(id)
);