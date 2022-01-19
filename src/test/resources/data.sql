insert into app_user values(1, "admin@email.com", "adminpw", "admin");
insert into app_user values(2, "client@email.com", "clientpw", "client");
insert into app_user values(3, "client2@email.com", "clientpw", "client2");
insert into app_user values(4, "adonly@email.com", "adonlypw", "adonly");
insert into app_user values(5, "manager@email.com", "managerpw", "manager");

insert into app_user_role (user_id, role) values(1,0);
insert into app_user_role (user_id, role) values(2,0);
insert into app_user_role (user_id, role) values(3,0);
insert into app_user_role (user_id, role) values(5,0);
insert into app_user_role (user_id, role) values(1,1);
insert into app_user_role (user_id, role) values(1,2);
insert into app_user_role (user_id, role) values(4,1);
insert into app_user_role (user_id, role) values(5,2);
이거 안됨

