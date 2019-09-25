INSERT INTO public.users
(id, email, first_name, last_name, password, reset_token)
VALUES(1, 'client@comparetout.fr', 'Adrien', 'Deblock', '$2a$12$gPhTV2TjgEreC0L8sjZWqe.dg48cPXdiHTt95GgKei9fcBBO7ZTx2', NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users
(id, email, first_name, last_name, password, reset_token)
VALUES(2, 'fournisseur@comparetout.fr', 'Lukas', 'Ramus', '$2a$12$gPhTV2TjgEreC0L8sjZWqe.dg48cPXdiHTt95GgKei9fcBBO7ZTx2', NULL) ON CONFLICT DO NOTHING;