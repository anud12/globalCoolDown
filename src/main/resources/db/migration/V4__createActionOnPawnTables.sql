CREATE TABLE action_on_pawn (
  id BIGINT PRIMARY KEY,
);

CREATE TABLE action_effect_on_pawn (
  id BIGINT PRIMARY KEY ,
  action_id BIGINT REFERENCES action_on_pawn(id) NOT NULL ,
  effect_id BIGINT REFERENCES EFFECT_ON_PAWN(id) NOT NULL
)