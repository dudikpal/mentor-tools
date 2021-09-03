create table registrations (id bigint not null auto_increment,
        status varchar(255),
        student_id bigint,
        training_class_id bigint,
        primary key (id))
        engine=InnoDB