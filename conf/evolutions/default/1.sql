# Tasks schema

# ---- !ups

CREATE SEQUENCE task_id_seq;
CREATE TABLE task (
  id integer NOT NULL DEFAULT nextval('task_id_seq'),
  label varchar(255)
);

# ---- !downs

DROP TABLE task;
DROP SEQUENCE task_id_seq;