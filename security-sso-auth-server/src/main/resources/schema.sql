DROP TABLE role IF EXISTS;
DROP TABLE user_info IF EXISTS;

CREATE TABLE user_info
(
    user_name VARCHAR(255) NOT NULL PRIMARY KEY,
    password  VARCHAR(255) NOT NULL,
    enabled   BOOLEAN DEFAULT TRUE
);

CREATE TABLE role
(
    user_name VARCHAR(255) NOT NULL foreign key REFERENCES user_info (user_name),
    role      VARCHAR(255) NOT NULL,
);

-- create table client_details(
--   CLIENT_ID VARCHAR (255) not null unique ,
--   CLIENT_SECRET VARCHAR (255) not null   ,
--   RESOURCE_IDS VARCHAR (255) null ,
--   SCOPES VARCHAR (255) null ,
--   GRANT_TYPES VARCHAR (255) null ,
--   AUTHORITIES VARCHAR (255) null
-- );
