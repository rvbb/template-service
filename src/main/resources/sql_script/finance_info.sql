-- public.finance_info definition

-- Drop table

-- DROP TABLE public.finance_info;

CREATE TABLE public.finance_info (
id int8 NOT NULL,
company_address varchar(500) NOT NULL,
company_name timestamp NOT NULL,
loan_application_id int4 NOT NULL,
expense float8 NOT NULL,
last_update date NULL,
pre_tax_income float8 NOT NULL,
CONSTRAINT banklist_demo_pkey PRIMARY KEY (id)
);