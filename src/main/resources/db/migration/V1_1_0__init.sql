CREATE TABLE "user"
(
    id           varchar(100),
    phone_number varchar(255) NOT NULL,
    CONSTRAINT "pk_user" PRIMARY KEY (id)
);


CREATE TABLE client
(
    id           varchar(100),
    first_name   varchar(255),
    last_name    varchar(255),
    middle_name  varchar(255),
    email        varchar(1024),
    is_activated bool      DEFAULT FALSE,
    is_deleted   bool      DEFAULT FALSE,
    created_at   TIMESTAMP DEFAULT now(),
    updated_at   TIMESTAMP DEFAULT NULL,
    deleted_at   TIMESTAMP DEFAULT NULL,
    CONSTRAINT pk_client_data PRIMARY KEY (id),
    CONSTRAINT fk_client_user FOREIGN KEY (id) REFERENCES "user" (id)
);

CREATE TABLE client_bank_data
(
    id                    varchar(100)  NOT NULL,
    bik                   varchar(255)  NOT NULL,
    bank_name             varchar(255)  NOT NULL,
    bank_corr_acc         varchar(255)  NOT NULL,
    client_account_number varchar(255)  NOT NULL,
    client_address        varchar(1024) NOT NULL,
    CONSTRAINT "pk_client_bank_data.client_bank_data" PRIMARY KEY (id)
);

CREATE TABLE product_data
(
    id             varchar(100) NOT NULL,
    purchase_data  varchar(255) NOT NULL,
    product_name   varchar(255) NOT NULL,
    seller_name    varchar(255) NOT NULL,
    seller_address varchar(255) NOT NULL,
    seller_inn     varchar(255) NOT NULL,
    CONSTRAINT "pk_product_data.product_data" PRIMARY KEY (id)
);

CREATE TABLE documents
(
    id                         varchar(100)  NOT NULL,
    client_id                  varchar(255)  NOT NULL,
    document_name              varchar(255)  NOT NULL,
    url                        varchar(2048) NOT NULL,
    client_bank_data_id        varchar(255)           DEFAULT NULL,
    product_data_id            varchar(255)           DEFAULT NULL,
    created_at                 TIMESTAMP              DEFAULT now(),
    type                       varchar(255)  NOT NULL,
    is_saved_before_activation bool          NOT NULL DEFAULT false,
    updated_at                 TIMESTAMP              DEFAULT NULL,
    CONSTRAINT "pk_documents.documents_documents" PRIMARY KEY (id),
    CONSTRAINT "fk_documents.documents_client" FOREIGN KEY (client_id)
        REFERENCES client (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fk_documents.product_data" FOREIGN KEY (product_data_id)
        REFERENCES product_data (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fk_documents.client_bank_data" FOREIGN KEY (client_bank_data_id)
        REFERENCES client_bank_data (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE client_profile_activation_data
(
    id        varchar(100) NOT NULL,
    client_id varchar(100) NOT NULL,
    code      int          NOT NULL,
    send_at   TIMESTAMP   DEFAULT now(),
    valid_to  TIMESTAMP   DEFAULT now(),
    status    varchar(50) DEFAULT NULL,
    CONSTRAINT "pk_client_profile_activation_data.client_activation_data" PRIMARY KEY (id),
    CONSTRAINT "fk_client_profile_activation_data.client_activation_data" FOREIGN KEY (client_id)
        REFERENCES client (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION
)
