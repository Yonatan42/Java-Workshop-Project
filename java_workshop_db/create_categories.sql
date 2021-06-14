CREATE TABLE java_workshop_db.categories (
   id INT NOT NULL auto_increment,
   name varchar (32) NOT NULL,
   created timestamp default now(),
   modified timestamp default now(),
   PRIMARY KEY (id)
);

create trigger java_workshop_db.trigger_categories_update
before update on java_workshop_db.categories
for each row
set new.modified = now();

# test category
insert into java_workshop_db.categories (name) values ('cat1');

#test update
update java_workshop_db.categories  set name = 'cat2' where id = 1;