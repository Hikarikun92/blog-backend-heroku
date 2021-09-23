insert into `user` (id, username)
values (1, 'Administrator'),
       (2, 'John Doe'),
       (3, 'Mary Doe');

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