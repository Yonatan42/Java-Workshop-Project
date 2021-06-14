TYPE=TRIGGERS
triggers='CREATE DEFINER=`admin`@`localhost` trigger java_workshop_db.trigger_categories_update\nbefore update on java_workshop_db.categories\nfor each row\nset new.modified = now()'
sql_modes=1436549152
definers='admin@localhost'
client_cs_names='utf8'
connection_cl_names='utf8_general_ci'
db_cl_names='utf8_general_ci'
created=162332075915
