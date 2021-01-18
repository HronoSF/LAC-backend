CREATE TABLE user_data
(
    id           varchar primary key,
    name         varchar NOT NULL,
    address      varchar NOT NULL,
    phone_number varchar NOT NULL,
    password     varchar,
    is_activated bool
);

CREATE TABLE user_account
(
    id             varchar primary key,
    bik            varchar NOT NULL,
    bank_name      varchar NOT NULL,
    bank_corr_acc  varchar NOT NULL,
    info           varchar NOT NULL,
    account_number varchar NOT NULL,
    created_at     timestamp default NULL,
    user_id        varchar NOT NULL references user_data (id)
);

CREATE TABLE profile_activation
(
    id       varchar primary key,
    code     int     NOT NULL,
    send_at timestamp default NULL,
    valid_to timestamp default NULL,
    user_id  varchar NOT NULL references user_data (id)
)
