INSERT INTO admin_account(created_at, created_by, deleted_yn, modified_at, modified_by, email, name, password, roles)
values
    (now(),'admin@admin.com','N',now(),'admin@admin.com','admin@admin.com','admin','{noop}1234','ADMIN'),
    (now(),'staff@staff.com','N',now(),'staff@staff.com','staff@staff.com','staff','{noop}1234','STAFF');
