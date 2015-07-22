# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table exam_result (
  RESULT_ID                 integer not null,
  name                      varchar(255),
  ects                      integer,
  grade                     float,
  MODULE_ID                 integer,
  constraint pk_exam_result primary key (RESULT_ID))
;

create table module (
  MODULE_ID                 integer not null,
  name                      varchar(255),
  ects                      integer,
  constraint pk_module primary key (MODULE_ID))
;

create sequence exam_result_seq;

create sequence module_seq;

alter table exam_result add constraint fk_exam_result_module_1 foreign key (MODULE_ID) references module (MODULE_ID) on delete restrict on update restrict;
create index ix_exam_result_module_1 on exam_result (MODULE_ID);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists exam_result;

drop table if exists module;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists exam_result_seq;

drop sequence if exists module_seq;

