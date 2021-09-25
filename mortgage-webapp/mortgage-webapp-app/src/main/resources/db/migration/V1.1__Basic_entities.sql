CREATE TABLE insurance_agreement_mortgate_agreement
(
          insurance_id VARCHAR(255),
          mortgage_id  VARCHAR(255) NOT NULL,
          PRIMARY KEY (mortgage_id)
);

CREATE TABLE insurance_police_insurance_agreement
(
          insurance_id VARCHAR(255),
          police_id    VARCHAR(255) NOT NULL,
          PRIMARY KEY (police_id)
);

CREATE TABLE insurance_agreement
(
          document_number VARCHAR(255) NOT NULL,
          amount          NUMERIC(19, 2),
          document_date TIMESTAMP,
          pay_amount        NUMERIC(19, 2),
          pay_date          NUMERIC(19, 2),
          person_name       VARCHAR(255),
          person_patronymic VARCHAR(255),
          person_surname    VARCHAR(255),
          risk              VARCHAR(255),
          status            VARCHAR(255),
          PRIMARY KEY (document_number)
);

CREATE TABLE insurance_police
(
          id VARCHAR(255) NOT NULL,
          date_end TIMESTAMP,
          date_start TIMESTAMP,
          police_number VARCHAR(255),
          PRIMARY KEY (id)
);

CREATE TABLE mortgage_agreement
(
          document_number VARCHAR(255) NOT NULL,
          address         VARCHAR(255),
          building_year INT4 NOT NULL,
          city VARCHAR(255),
          document_date TIMESTAMP,
          estimated_cost  NUMERIC(19, 2),
          kadaster_number VARCHAR(255),
          passport_number VARCHAR(255),
          passport_series VARCHAR(255),
          person_birth_date TIMESTAMP,
          person_name       VARCHAR(255),
          person_occupation VARCHAR(255),
          person_patronymic VARCHAR(255),
          person_sex        VARCHAR(255),
          person_surname    VARCHAR(255),
          remaining_amount  NUMERIC(19, 2),
          status INT4,
          TYPE VARCHAR(255),
          PRIMARY KEY (document_number)
);

ALTER TABLE IF exists insurance_agreement_mortgate_agreement ADD CONSTRAINT fktnhe3rohyt689iqevxrmbrfe FOREIGN KEY (insurance_id) REFERENCES mortgage_agreement;
ALTER TABLE IF exists insurance_agreement_mortgate_agreement ADD CONSTRAINT fk3ja74346c79dw3svu036ub4ka FOREIGN KEY (mortgage_id) REFERENCES insurance_agreement;
ALTER TABLE IF exists insurance_police_insurance_agreement ADD CONSTRAINT fklv5sb3f6t6w8hmxsbxn5tns96 FOREIGN KEY (insurance_id) REFERENCES insurance_agreement;
ALTER TABLE IF exists insurance_police_insurance_agreement ADD CONSTRAINT fktm4n7qmw8qg4cg6t1sk8okdvh FOREIGN KEY (police_id) REFERENCES insurance_police;
