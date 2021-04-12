BEGIN;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    login VARCHAR(255) NOT NULL,
    password varchar(64) NOT NULL
);
INSERT INTO users (login, password) VALUES
                                           -- superadmin
('superadmin', '$2y$12$GOlts5fwm1I8sfnoareQdeJa6xfCmuKv/fqsf/SD/wGZwW.0blGnq'),
                                           -- admin
('admin', '$2y$12$rBtn1bbKhTB/DDe6hlBjVuLy9uDwSpfjIVCJ362N//LZI1ez9ebD2'),
                                           -- manager
('manager', '$2y$12$9gRCV9aagJOH3GFQffosVON5Ou5gaxnxOnXLlj7w8AmY3OUECtZ.y'),
                                           -- user
('user', '$2y$12$g3oU2ErfkSt/RMWNdjT2xeQdUT78pAURWCGGwonP2ueWuW4XJl0Sm');

CREATE TABLE roles (
    id SERIAL PRIMARY KEY NOT NULL,
    name varchar(65) NOT NULL
);
INSERT INTO roles (name) VALUES
('ROLE_SUPERADMIN'),
('ROLE_ADMIN'),
('ROLE_MANAGER'),
('ROLE_USER');

CREATE TABLE users_roles (
    user_id BIGINT NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY(user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);
INSERT INTO users_roles VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

COMMIT;