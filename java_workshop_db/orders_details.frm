TYPE=VIEW
query=select `o`.`id` AS `order_id`,`c`.`id` AS `customer_id`,`p`.`id` AS `product_id`,concat(`c`.`last_name`,\' , \',`c`.`first_name`) AS `customer_name`,`c`.`email` AS `email`,`o`.`phone` AS `phone`,`o`.`address` AS `address`,`p`.`title` AS `product_name`,`op`.`price_at_order` AS `price`,`op`.`quantity` AS `quantity`,(`op`.`price_at_order` * `op`.`quantity`) AS `total_price` from (((`java_workshop_db`.`orders` `o` join `java_workshop_db`.`customers` `c` on((`o`.`customer_id` = `c`.`id`))) join `java_workshop_db`.`order_products` `op` on((`o`.`id` = `op`.`order_id`))) join `java_workshop_db`.`products` `p` on((`op`.`product_id` = `p`.`id`)))
md5=f66a3d11f8b1cc39125c3b7d02fac27e
updatable=1
algorithm=0
definer_user=admin
definer_host=localhost
suid=2
with_check_option=0
timestamp=2021-08-03 12:23:14
create-version=1
source=SELECT \no.id AS order_id, \nc.id AS customer_id,\np.id AS product_id,\nCONCAT(c.last_name,  \' , \', c.first_name) AS customer_name,\nc.email,\no.phone,\no.address,\np.title AS product_name,\nop.price_at_order AS price,\nop.quantity,\nop.price_at_order * op.quantity AS total_price\nFROM orders AS o \nINNER JOIN customers AS c ON o.customer_id = c.id \nINNER JOIN order_products AS op ON o.id = op.order_id\nINNER JOIN products as p ON op.product_id = p.id
client_cs_name=utf8
connection_cl_name=utf8_general_ci
view_body_utf8=select `o`.`id` AS `order_id`,`c`.`id` AS `customer_id`,`p`.`id` AS `product_id`,concat(`c`.`last_name`,\' , \',`c`.`first_name`) AS `customer_name`,`c`.`email` AS `email`,`o`.`phone` AS `phone`,`o`.`address` AS `address`,`p`.`title` AS `product_name`,`op`.`price_at_order` AS `price`,`op`.`quantity` AS `quantity`,(`op`.`price_at_order` * `op`.`quantity`) AS `total_price` from (((`java_workshop_db`.`orders` `o` join `java_workshop_db`.`customers` `c` on((`o`.`customer_id` = `c`.`id`))) join `java_workshop_db`.`order_products` `op` on((`o`.`id` = `op`.`order_id`))) join `java_workshop_db`.`products` `p` on((`op`.`product_id` = `p`.`id`)))
