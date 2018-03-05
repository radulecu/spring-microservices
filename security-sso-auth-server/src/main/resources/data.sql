-- users in system
INSERT INTO users (user_name, password) VALUES ('jlong', 'spring');
INSERT INTO users (user_name, password) VALUES ('pwebb', 'boot');
INSERT INTO users (user_name, password) VALUES ('eureka', 'eurekapass');

INSERT INTO user_roles (user_name, role) VALUES ('jlong', 'ROLE_USER');
INSERT INTO user_roles (user_name, role) VALUES ('jlong', 'ROLE_ACTUATOR');
INSERT INTO user_roles (user_name, role) VALUES ('jlong', 'ROLE_ADMIN');

INSERT INTO user_roles (user_name, role) VALUES ('pwebb', 'ROLE_USER');

INSERT INTO user_roles (user_name, role) VALUES ('eureka', 'ROLE_USER');

-- oauth client details
-- insert into client_details(   client_id, client_secret,  resource_ids,   scopes,   grant_types,                                  authorities)
--                     values(   'acme' ,  'acmesecret',    null,           'openid,read',   'authorization_code,refresh_token,password',  null );