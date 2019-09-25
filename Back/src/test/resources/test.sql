SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE public.users;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO public.users
(id, email, first_name, last_name, password, reset_token)
VALUES(1, 'test1@test.fr', 'test1', 'TEST1', '$2a$12$gPhTV2TjgEreC0L8sjZWqe.dg48cPXdiHTt95GgKei9fcBBO7ZTx2', NULL);
INSERT INTO public.users
(id, email, first_name, last_name, password, reset_token)
VALUES(2, 'test2@test.fr', 'test2', 'TEST2', '$2a$12$SrdgFLlprAt8zrMWi4hROuo4R.vhH/yZqPeWbPs9AU6T6TWpCvbzW', NULL);


