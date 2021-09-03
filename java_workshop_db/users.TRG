TYPE=TRIGGERS
triggers='CREATE DEFINER=`admin`@`localhost` TRIGGER trigger_users_insert\nBEFORE INSERT ON users \nFOR EACH ROW\nSET NEW.secret_key = generate_secret_key()' 'CREATE DEFINER=`admin`@`localhost` TRIGGER trigger_users_update\nBEFORE UPDATE ON users\nFOR EACH ROW\nSET NEW.modified = NOW()'
sql_modes=1436549152 1436549152
definers='admin@localhost' 'admin@localhost'
client_cs_names='utf8' 'utf8'
connection_cl_names='utf8_general_ci' 'utf8_general_ci'
db_cl_names='utf8_general_ci' 'utf8_general_ci'
created=163067381753 163067381259
