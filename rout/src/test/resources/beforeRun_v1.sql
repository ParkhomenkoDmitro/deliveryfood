INSERT INTO public.users (user_id, USER_DETAILS_TYPE, name, version) VALUES (1, 'ADM', 'Kris Evans', 1);

INSERT INTO public.ROLE (ROLE_ID, NAME) VALUES (1, 'SUPERADMIN_ROLE');

INSERT INTO public.USER_ROLE (USER_ID, ROLE_ID) VALUES  (1, 1);