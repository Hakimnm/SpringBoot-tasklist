insert into users (name, username, password)
values ('test', 'test@gmail.com',
        '$2a$12$EOThIiEbXBvyFoEckMiqpeh0B76tPhY6M0ypTKP5m/ijw2/Q8zb9G'),
    ('demo','demo@gmail.com','$2a$12$b7m3nKYlE8HQ/2aiLkaKtedFjdqi9P5cjkrjnhypYQNmpFykr9bP2')
    on conflict (username) do nothing;

insert into tasks(title, description, status, expiration_date)
values
    ('Buy cheese', null, 'TODO', '2026-01-31 12:00:00'),
    ('Do homework','Math,Literature','IN_PROGRESS','2026-01-31 00:00:00'),
    ('Clean romes',null,'DONE',null),
    ('Call Mike','Ask about meeting','TODO','2026-01-30 12:00:00')
    ON CONFLICT (id) DO NOTHING;

insert into users_task(task_id, user_id)
values (1, 2), (2,2),
(3,2),
(4,1)
    on conflict (user_id, task_id) do nothing;

insert into users_roles(user_id,role)
values (1,'ROLE_ADMIN'),
(1,'ROLE_USER'),
(2,'ROLE_USER')
    on conflict (user_id, role) do nothing;