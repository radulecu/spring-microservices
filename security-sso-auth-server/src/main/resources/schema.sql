DROP TABLE users
IF EXISTS;
DROP TABLE user_roles
IF EXISTS;

CREATE TABLE users (
  user_name VARCHAR(255) NOT NULL,
  password  VARCHAR(255) NOT NULL,
  enabled   BOOLEAN DEFAULT TRUE
);

CREATE TABLE user_roles (
  user_name VARCHAR(255) NOT NULL,
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
