create table lessons (id bigint not null auto_increment,
        title varchar(255) not null,
        url varchar(255) not null,
        primary key (id))
        engine=InnoDB