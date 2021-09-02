create table trainingclasses (id bigint not null auto_increment,
        ending_date date,
        starting_date date,
        name varchar(255) not null,
        primary key (id))
        engine=InnoDB