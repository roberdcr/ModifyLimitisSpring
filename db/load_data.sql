INSERT INTO bank (id_bank) VALUES (1234);
INSERT INTO office (id_office, id_bank) VALUES ('1235','1234');
INSERT INTO client (client_id, age, name, surname) VALUES ('71034506H', 22, 'Roberto', 'de Castro');
INSERT INTO accounts (id_office, id_bank, account_number) VALUES ('1235','1234','0000000000');
INSERT INTO cards (id, client_id, account_id, buyLimitDiary, buyLimitMonthly, cashLimitDiary, cashLimitMonthly, commissionEmmission, commissionMaintenance, comissionRenovate) VALUES ('1234011234567892','71034506H', '1234-1234-10-0000000000' ,100.00,1000.00,100.00,1000.00,0,0,0);