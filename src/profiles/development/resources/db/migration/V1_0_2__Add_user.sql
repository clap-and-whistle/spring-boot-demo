insert into user_account_base
    (account_status, email, password, user_account_profile_id)
  values
    -- password = 'hoge01TEST'
    (0, 'hoge01@example.local', '$2a$10$rKNNt5Np1tRUx3vArlZwJ.jTgNspEfD/hLyZjLPeMAX5N0nhbvb7G', 1);
insert into user_account_profile (id, birth_date_str, full_name) values (1, '20000410', 'hoge01');
