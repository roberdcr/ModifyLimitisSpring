CREATE DATABASE payments;

GRANT ALL ON payments.* TO springappuser@'%' IDENTIFIED BY 'pspringappuser';
GRANT ALL ON payments.* TO springappuser@localhost IDENTIFIED BY 'pspringappuser';

USE payments;

CREATE TABLE cards (
  id VarChar(16) PRIMARY KEY,
  client_id VarChar(9) REFERENCES client(client_id),
  account_id  VarChar(20) REFERENCES accounts(pk_account),
  type VarChar(9),
  buyLimitDiary DOUBLE,
  buyLimitMonthly DOUBLE,
  cashLimitDiary DOUBLE,
  cashLimitMonthly DOUBLE,
  commissionEmission DOUBLE,
  commissionMaintenance DOUBLE,
  comissionRenovate DOUBLE
);

CREATE TABLE accounts (
  id_office VarChar(4) REFERENCES office(id_office),
  id_bank VarChar(4) REFERENCES bank(id_bank),
  account_number VarChar(10),
  CONSTRAINT pk_account PRIMARY KEY (id_office, id_bank, account_number)
);

CREATE TABLE bank (
  id_bank VarChar(4) PRIMARY KEY
);

CREATE TABLE office (
  id_office VarChar(4) PRIMARY KEY,
  id_bank VarChar(4) REFERENCES bank(id_bank)
);

CREATE TABLE client (
  client_id VarChar(9) PRIMARY KEY,
  age INTEGER,
  name VarChar(25),
  surname VarChar(30)
);

