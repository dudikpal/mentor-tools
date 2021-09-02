create table students (

        id bigint not null auto_increment,
        name varchar(255) not null,
        email varchar(255) not null,
        github_name varchar(255) not null,
        description varchar(255),
        primary key (id)

        )
        engine=InnoDB