INSERT INTO sso_auth_service.user_info (user_name, enabled ,password) VALUES ('jlong', true, 'spring');
INSERT INTO sso_auth_service.user_info (user_name, enabled ,password) VALUES ('pwebb', true, 'boot');

INSERT INTO sso_auth_service.role (user_name, role) VALUES ('jlong', 'ROLE_USER');
INSERT INTO sso_auth_service.role (user_name, role) VALUES ('jlong', 'ROLE_ACTUATOR');
INSERT INTO sso_auth_service.role (user_name, role) VALUES ('jlong', 'ROLE_ADMIN');

INSERT INTO sso_auth_service.role (user_name, role) VALUES ('pwebb', 'ROLE_USER');
