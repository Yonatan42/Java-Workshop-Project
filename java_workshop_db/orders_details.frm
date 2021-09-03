TYPE=VIEW
query=select `o`.`id` AS `order_id`,`u`.`id` AS `user_id`,`p`.`id` AS `product_id`,concat(`u`.`last_name`,\' , \',`u`.`first_name`) AS `full_name`,`u`.`email` AS `email`,`o`.`phone` AS `phone`,`o`.`address` AS `address`,`p`.`title` AS `product_name`,`op`.`price_at_order` AS `price`,`op`.`quantity` AS `quantity`,(`op`.`price_at_order` * `op`.`quantity`) AS `total_price`,`o`.`created` AS `transaction_date` from (((`java_workshop_db`.`orders` `o` join `java_workshop_db`.`users` `u` on((`o`.`customer_id` = `u`.`id`))) join `java_workshop_db`.`order_products` `op` on((`o`.`id` = `op`.`order_id`))) join `java_workshop_db`.`products` `p` on((`op`.`product_id` = `p`.`id`)))
md5=11bff5101aaffa24c7a5db2e686bb365
updatable=1
algorithm=0
definer_user=admin
definer_host=localhost
suid=2
with_check_option=0
timestamp=2021-09-03 13:09:16
create-version=1
source=SELECT \no.id AS order_id, \nu.id AS user_id,\np.id AS product_id,\nCONCAT(u.last_name,  \' , \', u.first_name) AS full_name,\nu.email,\no.phone,\no.address,\np.title AS product_name,\nop.price_at_order AS price,\nop.quantity,\nop.price_at_order * op.quantity AS total_price,\no.created AS transaction_date\nFROM orders AS o \nINNER JOIN users AS u ON o.customer_id = u.id \nINNER JOIN order_products AS op ON o.id = op.order_id\nINNER JOIN products as p ON op.product_id = p.id
client_cs_name=utf8
connection_cl_name=utf8_general_ci
view_body_utf8=select `o`.`id` AS `order_id`,`u`.`id` AS `user_id`,`p`.`id` AS `product_id`,concat(`u`.`last_name`,\' , \',`u`.`first_name`) AS `full_name`,`u`.`email` AS `email`,`o`.`phone` AS `phone`,`o`.`address` AS `address`,`p`.`title` AS `product_name`,`op`.`price_at_order` AS `price`,`op`.`quantity` AS `quantity`,(`op`.`price_at_order` * `op`.`quantity`) AS `total_price`,`o`.`created` AS `transaction_date` from (((`java_workshop_db`.`orders` `o` join `java_workshop_db`.`users` `u` on((`o`.`customer_id` = `u`.`id`))) join `java_workshop_db`.`order_products` `op` on((`o`.`id` = `op`.`order_id`))) join `java_workshop_db`.`products` `p` on((`op`.`product_id` = `p`.`id`)))
