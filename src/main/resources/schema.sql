drop table if exists notas;

create table notas (
    id bigint auto_increment primary key,
    title varchar(150) not null,
    subtitle varchar(255) not null,
    text text not null,
   );