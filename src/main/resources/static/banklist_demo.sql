CREATE TABLE lms_service.banks (
	id INT auto_increment NOT NULL,
	name varchar(100) NULL,
	`type` int NULL,
	created_date DATETIME NULL,
	last_modified_date DATETIME NULL,
	CONSTRAINT bank_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;
