-- users in system
INSERT INTO user_info (user_name, password) VALUES ('jlong', 'spring');
INSERT INTO user_info (user_name, password) VALUES ('pwebb', 'boot');

INSERT INTO role (user_name, role) VALUES ('jlong', 'ROLE_USER');
INSERT INTO role (user_name, role) VALUES ('jlong', 'ROLE_ACTUATOR');
INSERT INTO role (user_name, role) VALUES ('jlong', 'ROLE_ADMIN');

INSERT INTO role (user_name, role) VALUES ('pwebb', 'ROLE_USER');

-- oauth client details
-- insert into client_details(   client_id, client_secret,  resource_ids,   scopes,   grant_types,                                  authorities)
--                     values(   'acme' ,  'acmesecret',    null,           'openid,read',   'authorization_code,refresh_token,password',  null );