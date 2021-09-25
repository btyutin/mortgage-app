create table insurance_offer_mortgate_agreement (
    offer_id varchar(255),
    mortgage_id varchar(255) not null,
    primary key (mortgage_id)
);

alter table if exists insurance_offer_mortgate_agreement add constraint FK9wayp3li16sx56i2vhuoun4yp foreign key (offer_id) references mortgage_agreement;
alter table if exists insurance_offer_mortgate_agreement add constraint FKsdqoyf0slbuyag93siyme910r foreign key (mortgage_id) references insurance_offer;