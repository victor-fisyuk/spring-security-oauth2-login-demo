DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS hibernate_sequence;

CREATE SEQUENCE hibernate_sequence;

CREATE TABLE users (
    id bigint DEFAULT nextval('hibernate_sequence') PRIMARY KEY,
    username varchar(50) NOT NULL UNIQUE,
    password varchar(500) NOT NULL,
    enabled boolean NOT NULL,
    provider varchar(50),
    provider_id varchar(255),
    picture_url varchar(255)
);

CREATE UNIQUE INDEX idx_provider_provider_id ON users (provider, provider_id);

CREATE TABLE authorities (
    username varchar(50) NOT NULL,
    authority varchar(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX idx_auth_username ON authorities (username, authority);
