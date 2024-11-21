insert into roles (id, role)
    values (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

-- add user with pass - 123
insert into users (id, user_name, password)
    values (2, 'Petya', '$2a$12$IzP7n8.HaFL/yy7qyduUsODbMI7q.m1IvrWpsBrK./fjjBev2akPS');

insert into user_roles (role_id, user_id)
    values (1, 1), (1, 2), (2, 2);