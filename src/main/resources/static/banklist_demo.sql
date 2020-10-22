CREATE TABLE devdb.banklist_demo (
	id INT auto_increment NOT NULL,
	bank_name varchar(100) NULL,
	`type` int NULL,
	created DATETIME NULL,
	CONSTRAINT banklist_demo_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;
