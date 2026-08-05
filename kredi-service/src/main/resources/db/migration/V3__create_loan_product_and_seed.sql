CREATE TABLE loan_product (
    id                  BIGSERIAL PRIMARY KEY,
    code                VARCHAR(40)    NOT NULL,
    name                VARCHAR(120)   NOT NULL,
    description         VARCHAR(500),
    loan_type           VARCHAR(20)    NOT NULL,
    calculation_method  VARCHAR(20)    NOT NULL,
    day_count           VARCHAR(10)    NOT NULL,
    currency            VARCHAR(3)     NOT NULL,
    min_amount          NUMERIC(19,4)  NOT NULL,
    max_amount          NUMERIC(19,4)  NOT NULL,
    min_term_months     INTEGER,
    max_term_months     INTEGER,
    active              BOOLEAN        NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP      NOT NULL DEFAULT NOW(),
    version             BIGINT         NOT NULL DEFAULT 0,
    CONSTRAINT uk_loan_product_code UNIQUE (code)
);

INSERT INTO loan_product
    (code, name, description, loan_type, calculation_method, day_count, currency,
     min_amount, max_amount, min_term_months, max_term_months)
VALUES
    ('SPOT_TL', 'Spot Kredi',
     'Faiz orani kullandirim tarihinde sabitlenen, anapara ve faizi vade sonunda odenen nakit kredi.',
     'CASH', 'SPOT', 'ACT_365', 'TRY', 100000, 50000000, NULL, NULL),

    ('REVOLVING_TL', 'Rotatif Kredi',
     'Tahsis edilen limit dahilinde istenildiginde kullandirim ve geri odeme yapilabilen nakit kredi.',
     'CASH', 'REVOLVING', 'ACT_365', 'TRY', 50000, 100000000, NULL, NULL),

    ('INSTALLMENT_COMMERCIAL_TL', 'Taksitli Ticari Kredi',
     'Geri odemeleri belirli vadelerde esit taksitler halinde yapilan nakit kredi.',
     'CASH', 'ANNUITY', 'ACT_365', 'TRY', 50000, 25000000, 3, 60),

    ('IGE_GUARANTEED_TL', 'IGE Teminatli Kredi',
     'IGE A.S. kefalet destegi ile ihracatci firmalara kullandirilan ihracat taahhutlu kredi.',
     'CASH', 'ANNUITY', 'ACT_365', 'TRY', 250000, 50000000, 3, 24),

    ('EXPORT_FX', 'Ihracat Doviz Kredisi',
     'Ihracat faaliyetlerinin sevk oncesi ve sonrasi finansmani icin yabanci para nakit kredi.',
     'CASH', 'SPOT', 'ACT_360', 'USD', 50000, 10000000, NULL, NULL),

    ('PREFINANCING', 'Prefinansman Kredisi',
     'Ticaret Bakanligi protokolu kapsaminda proje ve program bazli ihracat destegi prefinansmani.',
     'CASH', 'SPOT', 'ACT_360', 'USD', 100000, 5000000, NULL, NULL),

    ('LETTER_OF_GUARANTEE', 'Teminat Mektubu',
     'Bir isin yapilmasi veya borcun odenmesi taahhudunu garanti eden gayrinakdi kredi.',
     'NON_CASH', 'COMMISSION', 'ACT_360', 'TRY', 10000, 50000000, NULL, NULL),

    ('EXTERNAL_GUARANTEE', 'Harici Garanti / Kontrgaranti',
     'Uluslararasi ticarette yukumluluklerin yerine getirilecegini taahhut eden garanti.',
     'NON_CASH', 'COMMISSION', 'ACT_360', 'USD', 50000, 25000000, NULL, NULL),

    ('REFERENCE_LETTER', 'Referans / Niyet Mektubu',
     'Odeme taahhudu icermeyen, firma lehine duzenlenen bilgi amacli mektup.',
     'NON_CASH', 'COMMISSION', 'ACT_360', 'TRY', 0, 0, NULL, NULL),

    ('ACCEPTANCE_CREDIT', 'Kabul / Aval Kredisi',
     'Police bedelinin vadede odenecegini banka avali ile garanti eden gayrinakdi kredi.',
     'NON_CASH', 'COMMISSION', 'ACT_360', 'TRY', 100000, 20000000, NULL, NULL);

CREATE INDEX idx_loan_product_type_active ON loan_product (loan_type, active);
