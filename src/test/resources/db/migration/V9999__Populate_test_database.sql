insert into `user` (id, username)
values (1, 'Administrator'),
       (2, 'John Doe'),
       (3, 'Mary Doe');

insert into user_credentials (user_id, password)
values (1, '$2a$10$2KOuXO8SUQGSsWhbDrAQoet9Ts8yJ4y853WYVHF0C3Y2Lv1YNacHS'), -- abc123
       (2, '$2a$10$bS.HuGI.l5pFEjfjDIjB2.3t9h62kRSi3exUTBhbs6vqrJouNTDh2'), -- PaSsW0rD!
       (3, '$2a$10$0K6JAr2YTe82YWid8dPNKeNQ3q73HVH4viQbAq.j.Z6pv3XItAO46'); -- 1234abcd!@#$

insert into user_roles (user_id, roles)
values (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');

insert into post (id, title, body, user_id)
values (1, 'Example post no. 1', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse placerat.', 2),
       (2, 'Another example post', 'Integer malesuada lorem non nunc.', 2),
       (3, 'Writing example applications in Kotlin', 'Kotlin methods are fun', 3);

insert into comment (id, title, body, user_id, post_id)
values (1, 'Example comment 1', 'Praesent sapien leo, viverra sed.', 2, 1),
       (2, 'Great article', 'Nice example!', 3, 1),
       (3, 'Nulla sit amet ante in', 'Curabitur ut maximus augue. Nunc luctus nibh risus.', 3, 3),
       (4, 'Maecenas non sapien a elit', 'Integer pulvinar nunc elit, eu interdum nisi ornare.', 2, 3),
       (5, 'Curabitur viverra blandit finibus', 'Nullam maximus risus vel urna mattis sollicitudin. Curabitur.', 3, 3);
