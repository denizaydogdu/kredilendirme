CREATE TABLE customer (
    id                  BIGSERIAL PRIMARY KEY,
    customer_number     VARCHAR(12)   NOT NULL,
    customer_type       VARCHAR(20)   NOT NULL,
    title               VARCHAR(200)  NOT NULL,
    identifier          VARCHAR(11)   NOT NULL,
    tax_office          VARCHAR(100),
    email               VARCHAR(120),
    phone               VARCHAR(20),
    address_line        VARCHAR(255),
    district            VARCHAR(60),
    city                VARCHAR(60),
    postal_code         VARCHAR(10),
    establishment_date  DATE,
    exporter            BOOLEAN       NOT NULL DEFAULT FALSE,
    status              VARCHAR(20)   NOT NULL,
    created_at          TIMESTAMP     NOT NULL,
    updated_at          TIMESTAMP     NOT NULL,
    version             BIGINT        NOT NULL,
    CONSTRAINT uk_customer_number UNIQUE (customer_number),
    CONSTRAINT uk_customer_identifier UNIQUE (identifier)
);

CREATE TABLE customer_financial_info (
    id                      BIGSERIAL PRIMARY KEY,
    customer_id             BIGINT         NOT NULL,
    annual_revenue          NUMERIC(19, 4),
    total_assets            NUMERIC(19, 4),
    employee_count          INTEGER,
    sector                  VARCHAR(100),
    risk_group              VARCHAR(20)    NOT NULL,
    last_balance_sheet_date DATE,
    created_at              TIMESTAMP      NOT NULL,
    updated_at              TIMESTAMP      NOT NULL,
    version                 BIGINT         NOT NULL,
    CONSTRAINT uk_financial_info_customer UNIQUE (customer_id),
    CONSTRAINT fk_financial_info_customer FOREIGN KEY (customer_id)
        REFERENCES customer (id) ON DELETE CASCADE
);

CREATE INDEX idx_customer_type_status ON customer (customer_type, status);
CREATE INDEX idx_customer_title ON customer (title);
