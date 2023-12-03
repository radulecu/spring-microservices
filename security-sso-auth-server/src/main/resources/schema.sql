DROP TABLE users if EXISTS ;
DROP TABLE user_roles if EXISTS ;

CREATE TABLE users(
  user_name varchar(255) not null,
  password varchar(255) not null,
  enabled BOOLEAN default true);

CREATE TABLE user_roles(
  user_name varchar(255) not null,
  role varchar(255) not null,);

-- create table client_details(
--   CLIENT_ID VARCHAR (255) not null unique ,
--   CLIENT_SECRET VARCHAR (255) not null   ,
--   RESOURCE_IDS VARCHAR (255) null ,
--   SCOPES VARCHAR (255) null ,
--   GRANT_TYPES VARCHAR (255) null ,
--   AUTHORITIES VARCHAR (255) null
-- );
