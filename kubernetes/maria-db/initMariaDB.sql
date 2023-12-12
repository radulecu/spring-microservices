GRANT ALL PRIVILEGES ON *.* TO 'dbuser'@'%';

create schema if NOT EXISTS bookmark_service;
create schema if NOT EXISTS contact_service;
create schema if NOT EXISTS sso_auth_service;
