INSERT INTO owner VALUES (1, 'Jon');
INSERT INTO owner VALUES (2, 'Alice');
INSERT INTO owner VALUES (3, 'Ben');

INSERT INTO account VALUES (1, 1, 100,'USD', PARSEDATETIME('2019-01-01', 'yyyy-MM-dd'));
INSERT INTO account VALUES (2, 2, 10,'USD', PARSEDATETIME('2019-01-01', 'yyyy-MM-dd'));
INSERT INTO account VALUES (3, 3, 5,'USD', PARSEDATETIME('2019-01-01', 'yyyy-MM-dd'));
INSERT INTO account VALUES (4, 3, 5,'PLN', PARSEDATETIME('2019-01-01', 'yyyy-MM-dd'));

INSERT INTO TRANSFER VALUES ('2fa1078f-9c2e-4b22-85c7-3ce11b3c06a1', 1, 2, 10,'USD', PARSEDATETIME('2019-01-01', 'yyyy-MM-dd'));
INSERT INTO TRANSFER VALUES ('d3bc32a4-21f7-48c6-bd08-af1337dc2b72', 2, 1, 10,'USD', PARSEDATETIME('2019-01-01', 'yyyy-MM-dd'));

