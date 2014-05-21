CREATE DATABASE payments;

GRANT ALL ON payments.* TO springappuser@'%' IDENTIFIED BY 'pspringappuser';
GRANT ALL ON payments.* TO springappuser@localhost IDENTIFIED BY 'pspringappuser';

USE payments;

CREATE TABLE cards (
  id VarChar(19) PRIMARY KEY,
  client_id VarChar(9) REFERENCES client(client_id),
  account_id  VarChar(20) REFERENCES accounts(pk_account),
  pin VarChar(4),
  buyLimitDiary DOUBLE,
  buyLimitMonthly DOUBLE,
  cashLimitDiary DOUBLE,
  cashLimitMonthly DOUBLE,
  emissionDate VarChar(10),
  expirationDate VarChar(5),
  type VarChar(9),
  cvv VarChar(3),
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

CREATE TABLE transaction (
	id VarChar(30) PRIMARY KEY,
	amount DOUBLE,
	transaction_Date DATE,
	effective_Date DATE,
	concept VarChar(80)
);

CREATE TABLE transactionHistory (
	transaction_id VarChar(30) REFERENCES transaction (id),
	card_id VarChar(19) REFERENCES cards (id),
	CONSTRAINT pk_transacyion_history PRIMARY KEY (transaction_id, card_id)
);
