CREATE TABLE client_data
(
    id                varchar(100),
    first_name        varchar(255),
    last_name         varchar(255),
    middle_name       varchar(255),
    address           varchar(1024) NOT NULL,
    phone_number      varchar(255)  NOT NULL,
    password          varchar(255),
    is_activated      bool                   DEFAULT FALSE,
    registration_date TIMESTAMP     NOT NULL DEFAULT now(),
    update_at         TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT "pk_client_data.client_data" PRIMARY KEY (id),
    UNIQUE (phone_number)
);

CREATE TABLE client_account
(
    id             varchar(100) NOT NULL,
    user_id        varchar(100) NOT NULL,
    bik            varchar(100) NOT NULL,
    bank_name      varchar(255) NOT NULL,
    bank_corr_acc  varchar(255) NOT NULL,
    info           varchar(255) NOT NULL,
    account_number varchar(255) NOT NULL,
    created_at     TIMESTAMP DEFAULT now(),
    CONSTRAINT "pk_client_account.client_account" PRIMARY KEY (id),
    CONSTRAINT "fk_client_account.client_account" FOREIGN KEY (user_id)
        REFERENCES client_data (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE client_profile_activation_data
(
    id          varchar(100) NOT NULL,
    user_id     varchar(100) NOT NULL,
    code        int          NOT NULL,
    send_at     TIMESTAMP   DEFAULT now(),
    valid_to    TIMESTAMP   DEFAULT now(),
    code_status varchar(50) DEFAULT NULL,
    CONSTRAINT "pk_client_profile_activation_data.client_profile_activation_data" PRIMARY KEY (id),
    CONSTRAINT "fk_client_profile_activation_data.client_profile_activation_data" FOREIGN KEY (user_id)
        REFERENCES client_data (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION
)
