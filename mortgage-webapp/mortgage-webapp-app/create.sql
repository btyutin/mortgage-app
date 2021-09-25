create table insurance_agreement_mortgate_agreement (insurance_id varchar(255), mortgage_id varchar(255) not null, primary key (mortgage_id))
create table insurance_offer_mortgate_agreement (offer_id varchar(255), mortgage_id varchar(255) not null, primary key (mortgage_id))
create table insurance_police_insurance_agreement (insurance_id varchar(255), police_id varchar(255) not null, primary key (police_id))
create table insurance_agreement (document_number varchar(255) not null, amount numeric(19, 2), document_date timestamp, pay_amount numeric(19, 2), pay_date numeric(19, 2), person_id uuid, person_name varchar(255), person_patronymic varchar(255), person_surname varchar(255), risk varchar(255), status varchar(255), primary key (document_number))
create table insurance_offer (id varchar(255) not null, pay_amount numeric(19, 2), risk int4, status varchar(255), primary key (id))
create table insurance_police (id varchar(255) not null, date_end timestamp, date_start timestamp, police_number varchar(255), primary key (id))
create table mortgage_agreement (document_number varchar(255) not null, address varchar(255), building_year int4 not null, city varchar(255), document_date timestamp, estimated_cost numeric(19, 2), kadaster_number varchar(255), passport_number varchar(255), passport_series varchar(255), person_birth_date timestamp, person_name varchar(255), person_occupation varchar(255), person_patronymic varchar(255), person_sex varchar(255), person_surname varchar(255), remaining_amount numeric(19, 2), status int4, type varchar(255), primary key (document_number))
alter table if exists insurance_agreement_mortgate_agreement add constraint FKtnhe3rohyt689iqevxrmbrfe foreign key (insurance_id) references mortgage_agreement
alter table if exists insurance_agreement_mortgate_agreement add constraint FK3ja74346c79dw3svu036ub4ka foreign key (mortgage_id) references insurance_agreement
alter table if exists insurance_offer_mortgate_agreement add constraint FK9wayp3li16sx56i2vhuoun4yp foreign key (offer_id) references mortgage_agreement
alter table if exists insurance_offer_mortgate_agreement add constraint FKsdqoyf0slbuyag93siyme910r foreign key (mortgage_id) references insurance_offer
alter table if exists insurance_police_insurance_agreement add constraint FKlv5sb3f6t6w8hmxsbxn5tns96 foreign key (insurance_id) references insurance_agreement
alter table if exists insurance_police_insurance_agreement add constraint FKtm4n7qmw8qg4cg6t1sk8okdvh foreign key (police_id) references insurance_police
create table insurance_agreement_mortgate_agreement (insurance_id varchar(255), mortgage_id varchar(255) not null, primary key (mortgage_id))
create table insurance_offer_mortgate_agreement (offer_id varchar(255), mortgage_id varchar(255) not null, primary key (mortgage_id))
create table insurance_police_insurance_agreement (insurance_id varchar(255), police_id varchar(255) not null, primary key (police_id))
create table insurance_agreement (document_number varchar(255) not null, amount numeric(19, 2), document_date timestamp, pay_amount numeric(19, 2), pay_date numeric(19, 2), person_id uuid, person_name varchar(255), person_patronymic varchar(255), person_surname varchar(255), risk varchar(255), status varchar(255), primary key (document_number))
create table insurance_offer (id varchar(255) not null, pay_amount numeric(19, 2), risk int4, status varchar(255), primary key (id))
create table insurance_police (id varchar(255) not null, date_end timestamp, date_start timestamp, police_number varchar(255), primary key (id))
create table mortgage_agreement (document_number varchar(255) not null, address varchar(255), bank_address varchar(255), building_year int4 not null, city varchar(255), document_date timestamp, estimated_cost numeric(19, 2), kadaster_number varchar(255), passport_number varchar(255), passport_series varchar(255), person_birth_date timestamp, person_id uuid, person_name varchar(255), person_occupation varchar(255), person_patronymic varchar(255), person_sex varchar(255), person_surname varchar(255), remaining_amount numeric(19, 2), status int4, type varchar(255), primary key (document_number))
alter table if exists insurance_agreement_mortgate_agreement add constraint FKtnhe3rohyt689iqevxrmbrfe foreign key (insurance_id) references mortgage_agreement
alter table if exists insurance_agreement_mortgate_agreement add constraint FK3ja74346c79dw3svu036ub4ka foreign key (mortgage_id) references insurance_agreement
alter table if exists insurance_offer_mortgate_agreement add constraint FK9wayp3li16sx56i2vhuoun4yp foreign key (offer_id) references mortgage_agreement
alter table if exists insurance_offer_mortgate_agreement add constraint FKsdqoyf0slbuyag93siyme910r foreign key (mortgage_id) references insurance_offer
alter table if exists insurance_police_insurance_agreement add constraint FKlv5sb3f6t6w8hmxsbxn5tns96 foreign key (insurance_id) references insurance_agreement
alter table if exists insurance_police_insurance_agreement add constraint FKtm4n7qmw8qg4cg6t1sk8okdvh foreign key (police_id) references insurance_police
create table insurance_agreement_mortgate_agreement (insurance_id varchar(255), mortgage_id varchar(255) not null, primary key (mortgage_id))
create table insurance_offer_mortgate_agreement (offer_id varchar(255), mortgage_id varchar(255) not null, primary key (mortgage_id))
create table insurance_police_insurance_agreement (insurance_id varchar(255), police_id varchar(255) not null, primary key (police_id))
create table insurance_agreement (document_number varchar(255) not null, amount numeric(19, 2), document_date timestamp, pay_amount numeric(19, 2), pay_date numeric(19, 2), person_id uuid, person_name varchar(255), person_patronymic varchar(255), person_surname varchar(255), police_number varchar(255), risk varchar(255), status varchar(255), primary key (document_number))
create table insurance_offer (id varchar(255) not null, pay_amount numeric(19, 2), risk int4, status varchar(255), primary key (id))
create table insurance_police (id varchar(255) not null, date_end timestamp, date_start timestamp, police_number varchar(255), status varchar(255), primary key (id))
create table mortgage_agreement (document_number varchar(255) not null, address varchar(255), bank_address varchar(255), building_year int4 not null, city varchar(255), document_date timestamp, estimated_cost numeric(19, 2), kadaster_number varchar(255), passport_number varchar(255), passport_series varchar(255), person_birth_date timestamp, person_id uuid, person_name varchar(255), person_occupation varchar(255), person_patronymic varchar(255), person_sex varchar(255), person_surname varchar(255), remaining_amount numeric(19, 2), status int4, type varchar(255), primary key (document_number))
alter table if exists insurance_agreement_mortgate_agreement add constraint FKtnhe3rohyt689iqevxrmbrfe foreign key (insurance_id) references mortgage_agreement
alter table if exists insurance_agreement_mortgate_agreement add constraint FK3ja74346c79dw3svu036ub4ka foreign key (mortgage_id) references insurance_agreement
alter table if exists insurance_offer_mortgate_agreement add constraint FK9wayp3li16sx56i2vhuoun4yp foreign key (offer_id) references mortgage_agreement
alter table if exists insurance_offer_mortgate_agreement add constraint FKsdqoyf0slbuyag93siyme910r foreign key (mortgage_id) references insurance_offer
alter table if exists insurance_police_insurance_agreement add constraint FKlv5sb3f6t6w8hmxsbxn5tns96 foreign key (insurance_id) references insurance_agreement
alter table if exists insurance_police_insurance_agreement add constraint FKtm4n7qmw8qg4cg6t1sk8okdvh foreign key (police_id) references insurance_police
create table insurance_agreement_mortgate_agreement (insurance_id varchar(255), mortgage_id varchar(255) not null, primary key (mortgage_id))
create table insurance_offer_mortgate_agreement (offer_id varchar(255), mortgage_id varchar(255) not null, primary key (mortgage_id))
create table insurance_police_insurance_agreement (insurance_id varchar(255), police_id varchar(255) not null, primary key (police_id))
create table insurance_agreement (document_number varchar(255) not null, amount numeric(19, 2), document_date timestamp, pay_amount numeric(19, 2), pay_date numeric(19, 2), person_id uuid, person_name varchar(255), person_patronymic varchar(255), person_surname varchar(255), police_number varchar(255), risk varchar(255), status varchar(255), primary key (document_number))
create table insurance_offer (id varchar(255) not null, pay_amount numeric(19, 2), risk int4, status varchar(255), primary key (id))
create table insurance_police (id varchar(255) not null, date_end timestamp, date_start timestamp, police_number varchar(255), status varchar(255), primary key (id))
create table mortgage_agreement (document_number varchar(255) not null, address varchar(255), bank_address varchar(255), building_year int4 not null, city varchar(255), document_date timestamp, estimated_cost numeric(19, 2), kadaster_number varchar(255), passport_number varchar(255), passport_series varchar(255), person_birth_date timestamp, person_id uuid, person_name varchar(255), person_occupation varchar(255), person_patronymic varchar(255), person_sex varchar(255), person_surname varchar(255), remaining_amount numeric(19, 2), status int4, type varchar(255), primary key (document_number))
alter table if exists insurance_agreement_mortgate_agreement add constraint FKtnhe3rohyt689iqevxrmbrfe foreign key (insurance_id) references mortgage_agreement
alter table if exists insurance_agreement_mortgate_agreement add constraint FK3ja74346c79dw3svu036ub4ka foreign key (mortgage_id) references insurance_agreement
alter table if exists insurance_offer_mortgate_agreement add constraint FK9wayp3li16sx56i2vhuoun4yp foreign key (offer_id) references mortgage_agreement
alter table if exists insurance_offer_mortgate_agreement add constraint FKsdqoyf0slbuyag93siyme910r foreign key (mortgage_id) references insurance_offer
alter table if exists insurance_police_insurance_agreement add constraint FKlv5sb3f6t6w8hmxsbxn5tns96 foreign key (insurance_id) references insurance_agreement
alter table if exists insurance_police_insurance_agreement add constraint FKtm4n7qmw8qg4cg6t1sk8okdvh foreign key (police_id) references insurance_police
