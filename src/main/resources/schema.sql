CREATE TABLE IF NOT EXISTS admins (
   seq           bigint NOT NULL AUTO_INCREMENT,
   email         varchar(50) NOT NULL,
   passwd        varchar(80) NOT NULL,
   create_at     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
   PRIMARY KEY (seq),
   CONSTRAINT unq_admin_email UNIQUE (email)
);