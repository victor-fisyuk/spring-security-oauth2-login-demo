INSERT INTO users (username, password, enabled, provider, provider_id)
VALUES (
    'user',
    '{bcrypt}$2a$10$PPKqteGYxY3k5xTfohjuruYzvBs8xWrpJthOky0uGmE8grYD9awnK', -- password
    true,
    'GOOGLE',
    'TODO: Google user id'
), (
    'admin',
    '{bcrypt}$2a$10$PPKqteGYxY3k5xTfohjuruYzvBs8xWrpJthOky0uGmE8grYD9awnK', -- password
    true,
    'GITHUB',
    'TODO: GitHub user id'
);

INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_USER'),
       ('admin', 'ROLE_ADMIN');
