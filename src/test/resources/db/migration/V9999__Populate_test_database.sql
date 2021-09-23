insert into `user` (id, username)
values (1, 'Administrator'),
       (2, 'John Doe'),
       (3, 'Mary Doe');

insert into post (id, title, body, user_id)
values (1, 'Example post no. 1', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse placerat.', 2),
       (2, 'Another example post', 'Integer malesuada lorem non nunc.', 2),
       (3, 'Writing example applications in Kotlin', 'Kotlin methods are fun', 3);