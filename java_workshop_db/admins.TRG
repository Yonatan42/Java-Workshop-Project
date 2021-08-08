TYPE=TRIGGERS
triggers='CREATE DEFINER=`admin`@`localhost` TRIGGER trigger_admins_update\nBEFORE UPDATE ON admins\nFOR EACH ROW\nSET NEW.modified = NOW()'
sql_modes=1436549152
definers='admin@localhost'
client_cs_names='utf8'
connection_cl_names='utf8_general_ci'
db_cl_names='utf8_general_ci'
created=162842865351
