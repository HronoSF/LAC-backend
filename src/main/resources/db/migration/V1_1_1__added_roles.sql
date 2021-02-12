CREATE TABLE role
(
    id   varchar(100),
    name varchar(255),
    CONSTRAINT "pk_role.role" PRIMARY KEY (id)
);

INSERT INTO role(id, name)
VALUES ('28603405-7f92-4912-8c35-5c950fa1ad2d', 'ROLE_CLIENT'),
       ('da4214c0-3acf-4c47-a4d9-050560ebfd1d', 'ROLE_ADMIN');

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