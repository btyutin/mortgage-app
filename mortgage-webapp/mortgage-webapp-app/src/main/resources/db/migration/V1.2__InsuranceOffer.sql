create table insurance_offer (
    id varchar(255) not null,
    pay_amount numeric(19, 2),
    risk int4,
    status varchar(255),
    primary key (id)
);

alter table if exists insurance_offer_mortgate_agreement add constraint FK9wayp3li16sx56i2vhuoun4yp foreign key (offer_id) references mortgage_agreement;
alter table if exists insurance_offer_mortgate_agreement add constraint FKsdqoyf0slbuyag93siyme910r foreign key (mortgage_id) references insurance_offer;