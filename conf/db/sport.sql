DROP TABLE user_account;

CREATE TABLE IF NOT EXISTS user_account (
  id SERIAL PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  user_type VARCHAR(100) NOT NULL,
  is_subscribed BOOLEAN DEFAULT FALSE
);

ALTER TABLE user_account ADD CONSTRAINT user_account_unique_idx UNIQUE (username);

INSERT INTO user_account (username, password, user_type, is_subscribed) VALUES
('roman','abcd1234','admin',FALSE),
('user1','efgh5678','user',FALSE);

SELECT * FROM user_account;


CREATE TABLE IF NOT EXISTS games (
  id SERIAL PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  participants INTEGER NOT NULL
);

INSERT INTO games (name, participants) VALUES
('Chess', 5000),
('Table Tennis', 8000);

SELECT * FROM games;