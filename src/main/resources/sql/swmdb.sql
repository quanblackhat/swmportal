=======
create database "swm";
create sequence "seq_province";
create sequence "seq_district";
create sequence "seq_region";
create sequence "seq_uses";
create sequence "seq_users";
create sequence "seq_contract";
create sequence "seq_device_mapping";
create sequence "seq_device";
create sequence "seq_measure_transactions";
create sequence "seq_price";
create sequence "seq_billing";
create sequence "seq_account";
create sequence "seq_role";
create sequence "seq_company";
create sequence "seq_group_role";
create sequence "seq_roles_mapping";
create sequence "seq_authenticate_assign";
create table "province" ("id" numeric(10, 0) not null, "name" varchar(255), "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), primary key ("id"));
create table "district" ("id" numeric(10, 0) not null, "name" varchar(255), "province_id" numeric(10, 0) not null, "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), primary key ("id"));
create table "region" ("id" numeric(10, 0) not null, "name" varchar(255), "district_id" numeric(10, 0) not null, "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), primary key ("id"));
create table "uses" ("id" numeric(10, 0) not null, "name" varchar(255), "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), primary key ("id"));
create table "users" ("id" numeric(10, 0) not null, "name" varchar(255), "id_card" varchar(20), "phone_number" varchar(20), "address" varchar(255), "region_id" numeric(10, 0) not null, "user_code" varchar(50), "bank_account" varchar(100), "bank" varchar(255), "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), primary key ("id"));
create table "contract" ("id" numeric(10, 0) not null, "number_contract" varchar(255), "uses_id" numeric(10, 0) not null, "user_id" numeric(10, 0) not null, "agent_id" numeric(10, 0) not null, "company_id" numeric(10, 0) not null, "expecte_amount" numeric(10, 2), "address" varchar(255), "region_id" numeric(10, 0) not null, "start_date" date, "end_date" date, "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), primary key ("id"));
create table "device_mapping" ("id" numeric(10, 0) not null, "contract_id" numeric(10, 0) not null, "device_id" numeric(10, 0) not null, "user_uses" varchar(255), "password" varchar(50), "pin_date" date, "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), primary key ("id"));
create table "device" ("id" numeric(10, 0) not null, "code" varchar(255), "setup_date" date, "region_id" numeric(10, 0) not null, "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), "api_key" varchar(255), primary key ("id"));
create table "measure_transactions" ("id" numeric(10, 0) not null, "device_id" numeric(10, 0) not null, "transaction_date" date, "amount" numeric(10, 2), "ph" numeric(10, 2), "ec" numeric(10, 2), "turbidity" numeric(10, 2), "temperature" numeric(10, 2), "created_date" date, primary key ("id"));
create table "price" ("id" numeric(10, 0) not null, "price" numeric(10, 0), "uses_id" numeric(10, 0) not null, "region_id" numeric(10, 0) not null, "start_date" date, "end_date" date, "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), "priceid" numeric(10, 0) not null, primary key ("id"));
create table "billing" ("id" numeric(10, 0) not null, "contract_id" numeric(10, 0) not null, "from_date" date, "to_date" date, "amount" numeric(10, 0), "price_id" numeric(10, 0) not null, "total_price" numeric(10, 0), "status" numeric(3, 0), "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), primary key ("id"));
create table "account" ("id" numeric(10, 0) not null, "username" varchar(50), "password" varchar(50), "user_id" numeric(10, 0) not null, "agent_id" numeric(10, 0) not null, "company_id" numeric(10, 0) not null, "status" numeric(10, 0), "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), primary key ("id"));
create table "role" ("id" numeric(10, 0) not null, "name" varchar(255), "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), primary key ("id"));
create table "agent" ("id" numeric(10, 0) not null, "agent_name" varchar(255), "phone_number" varchar(20), "representative" varchar(50), "function" varchar(50), "agent_address" varchar(255), "region_id" numeric(10, 0) not null, "tax_code" varchar(50), "bank_account" varchar(100), "bank" varchar(255), "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), primary key ("id"));
create table "company" ("id" numeric(10, 0) not null, "name" varchar(255), "address" varchar(255), "region_id" numeric(10, 0) not null, "phone_number" varchar(20), "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), primary key ("id"));
create table "group_role" ("id" numeric(10, 0) not null, "name" varchar(255), "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), "roleid" numeric(10, 0) not null, primary key ("id"));
create table "roles_mapping" ("id" numeric(10, 0) not null, "role_id" numeric(10, 0) not null, "group_role_id" numeric(10, 0) not null, "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), primary key ("id"));
create table "authenticate_assign" ("id" numeric(10, 0) not null, "account_id" numeric(10, 0) not null, "roles_id" numeric(10, 0) not null, "created_date" date, "created_by" numeric(10, 0), "updated_date" date, "updated_by" numeric(10, 0), primary key ("id"));
alter table "district" add constraint "FKdistrict240213" foreign key ("province_id") references "province" ("id");
alter table "region" add constraint "FKregion526202" foreign key ("district_id") references "district" ("id");
alter table "users" add constraint "FKusers866191" foreign key ("region_id") references "region" ("id");
alter table "agent" add constraint "FKagent39067" foreign key ("id") references "region" ("id");
alter table "company" add constraint "FKcompany40108" foreign key ("region_id") references "region" ("id");
alter table "contract" add constraint "FKcontract393068" foreign key ("region_id") references "region" ("id");
alter table "contract" add constraint "FKcontract448735" foreign key ("uses_id") references "uses" ("id");
alter table "contract" add constraint "FKcontract133897" foreign key ("company_id") references "company" ("id");
alter table "contract" add constraint "FKcontract965929" foreign key ("agent_id") references "agent" ("id");
alter table "contract" add constraint "FKcontract499094" foreign key ("user_id") references "users" ("id");
alter table "roles_mapping" add constraint "FKroles_mapp205704" foreign key ("role_id") references "role" ("id");
alter table "roles_mapping" add constraint "FKroles_mapp602892" foreign key ("group_role_id") references "group_role" ("id");
alter table "authenticate_assign" add constraint "FKauthentica350008" foreign key ("roles_id") references "group_role" ("id");
alter table "authenticate_assign" add constraint "FKauthentica793631" foreign key ("account_id") references "account" ("id");
alter table "billing" add constraint "FKbilling758886" foreign key ("contract_id") references "contract" ("id");
alter table "price" add constraint "FKprice565894" foreign key ("uses_id") references "uses" ("id");
alter table "price" add constraint "FKprice510227" foreign key ("region_id") references "region" ("id");
alter table "billing" add constraint "FKbilling833323" foreign key ("price_id") references "price" ("id");
alter table "device" add constraint "FKdevice368158" foreign key ("region_id") references "region" ("id");
alter table "device_mapping" add constraint "FKdevice_map936028" foreign key ("device_id") references "device" ("id");
alter table "device_mapping" add constraint "FKdevice_map498296" foreign key ("contract_id") references "contract" ("id");
alter table "measure_transactions" add constraint "FKmeasure_tr683388" foreign key ("device_id") references "device" ("id");
alter table "account" add constraint "FKaccount871005" foreign key ("user_id") references "users" ("id");
alter table "account" add constraint "FKaccount594018" foreign key ("agent_id") references "agent" ("id");
alter table "account" add constraint "FKaccount505808" foreign key ("company_id") references "company" ("id");

#18/12/2017
alter table "roles_mapping" add column "status" boolean;
ALTER TABLE "account" RENAME COLUMN "role_id" TO "grouprole_id";
ALTER TABLE "group_role" DROP COLUMN "roleid";
alter table "group_role" add column "desc" varchar(100);
alter table "account" drop column "agent_id" ;
alter table "account" drop column "company_id" ;
alter table "account" drop column "grouprole_id" ;
alter table "account" add column "grouprole_id" numeric(10, 0);
alter table "role" add column "code" varchar(200);
ALTER TABLE "group_role" ADD CONSTRAINT constraintname UNIQUE ("name");

-- 15/12/2017 alter company
ALTER TABLE company ALTER COLUMN phone_number TYPE varchar(255);

-- 18/12/2017 alter contract

-- 19/12/2017 uses
INSERT INTO uses(id, name, created_date, created_by, updated_date, updated_by) VALUES (1, 'Sinh hoạt', current_date, 1, current_date, 1);
INSERT INTO uses(id, name, created_date, created_by, updated_date, updated_by) VALUES (2, 'Sản xuất', current_date, 1, current_date, 1);
INSERT INTO uses(id, name, created_date, created_by, updated_date, updated_by) VALUES (3, 'Kinh doanh dịch vụ', current_date, 1, current_date, 1);

-- 21/12/2017 alter device_mapping
ALTER TABLE device_mapping
ADD user_id numeric(10, 0) not null;

ALTER TABLE device_mapping
DROP COLUMN contract_id;

ALTER TABLE users
DROP COLUMN district_id,
DROP COLUMN province_id;

-- 24/12/2017
ALTER TABLE uses
ADD uses_code varchar(50);

UPDATE uses SET uses_code = 'SH' WHERE id = 1;
UPDATE uses SET uses_code = 'SX' WHERE id = 2;
UPDATE uses SET uses_code = 'KDDV' WHERE id = 3;

ALTER TABLE contract
ADD expect_amount numeric(10, 2),
ADD user_uses varchar(255),
ADD register_type numeric(2, 0);

-- 27/12/2017
ALTER TABLE device_mapping
ADD status numeric(2, 0);

-- 08/01/2018 alter measure_transactions
ALTER TABLE measure_transactions ALTER COLUMN transaction_date TYPE timestamp;
ALTER TABLE measure_transactions ALTER COLUMN created_date TYPE timestamp;

ALTER TABLE contract
ADD status numeric(2, 0);

ALTER TABLE contract ALTER COLUMN user_id TYPE numeric(10, 0);

alter table "account" add column "level_type" numeric(2, 0);

alter table "account" add column "agent_id" numeric(10, 0);
alter table "account" add column "company_id" numeric(10, 0);
alter table "agent" add column "company_id" numeric(10, 0);
alter table "company" add column "agent_id" numeric(10, 0);
alter table "users" add column "company_id" numeric(10, 0);


