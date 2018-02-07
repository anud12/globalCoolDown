CREATE TABLE pawn (
  id      BIGINT PRIMARY KEY,
  name    VARCHAR(25) NOT NULL,
  version BIGINT      NOT NULL DEFAULT 0,
  val     INT         NOT NULL,
  x       BIGINT      NOT NULL,
  y       BIGINT      NOT NULL,
  speed   BIGINT      NOT NULL
);
