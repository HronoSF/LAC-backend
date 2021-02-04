CREATE TABLE role
(
    id   varchar(100),
    name varchar(255),
    CONSTRAINT "pk_role.role" PRIMARY KEY (id)
);

INSERT INTO role(id, name)
VALUES (md5(random()::text || clock_timestamp()::text)::uuid, 'ROLE_CLIENT'),
       (md5(random()::text || clock_timestamp()::text)::uuid, 'ROLE_ADMIN');

CREATE TABLE user_to_role
(
    user_id varchar(100),
    role_id varchar(100),
    CONSTRAINT "fk_user_to_role.user" FOREIGN KEY (user_id)
        REFERENCES client (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fk_user_to_role.role" FOREIGN KEY (role_id)
        REFERENCES role (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION
)