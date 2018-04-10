CREATE SEQUENCE users_seq;
CREATE TABLE users (
  id            BIGINT PRIMARY KEY,
  username      VARCHAR(25) NOT NULL,
  security_role VARCHAR(25) NOT NULL
)