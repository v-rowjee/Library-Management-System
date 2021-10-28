-- create schema
CREATE SCHEMA `library_db` ;

-- creating table account
CREATE TABLE `library_db`.`account` (
  `acc_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `current_book` INT NULL,
  PRIMARY KEY (`acc_id`),
  UNIQUE INDEX `acc_id_UNIQUE` (`acc_id` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE);
  
  
-- create table book
CREATE TABLE `library_db`.`book` (
  `book_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `author` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`book_id`),
  UNIQUE INDEX `book_id_UNIQUE` (`book_id` ASC) VISIBLE);

-- adding foreign key to current book referencing book table
ALTER TABLE `library_db`.`account` 
ADD INDEX `current_book_fk_idx` (`current_book` ASC) VISIBLE;
;
ALTER TABLE `library_db`.`account` 
ADD CONSTRAINT `current_book_fk`
  FOREIGN KEY (`current_book`)
  REFERENCES `library_db`.`book` (`book_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
  
-- populating table account
INSERT INTO `library_db`.`account` (username, password, firstname, lastname) VALUES ('admin','1234','Ved','Rowjee');
INSERT INTO `library_db`.`account` (username, password, firstname, lastname) VALUES ('user1','1234','Tom','Martinage');

-- view account table details
SELECT count(1) as rowcount, username, password, firstname, lastname FROM `library_db`.`account`;
SELECT * FROM `library_db`.`account`;

-- populating table book
INSERT INTO `library_db`.`book` (title,author) VALUES ('The Wedding Dress', 'Danielle Steel');
INSERT INTO `library_db`.`book` (title,author) VALUES ('Harry Potter and the Deathly Hallows', 'J.K.Rowling');
INSERT INTO `library_db`.`book` (title,author) VALUES ('Lord of the Rings', 'J.R.R. Tolkien');
INSERT INTO `library_db`.`book` (title,author) VALUES ('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling');
INSERT INTO `library_db`.`book` (title,author) VALUES ('Alice''s Adventures in Wonderland', 'Lewis Carroll');
INSERT INTO `library_db`.`book` (title,author) VALUES ('The Lion, the Witch, and the Wardrobe', 'C.S. Lewis');
INSERT INTO `library_db`.`book` (title,author) VALUES ('Pinocchio', 'Carlo Collodi');
INSERT INTO `library_db`.`book` (title,author) VALUES ('Catcher in the Rye', 'J.D. Salinger');
INSERT INTO `library_db`.`book` (title,author) VALUES ('Anne of Green Gables', 'L. M. Montgomery');
INSERT INTO `library_db`.`book` (title,author) VALUES ('Twenty Thousand Leagues Under the Sea', 'Jules Verne');
INSERT INTO `library_db`.`book` (title,author) VALUES ('In Search of Lost Time', 'Marcel Proust');
INSERT INTO `library_db`.`book` (title,author) VALUES ('Moby Dick', 'Herman Melville');
INSERT INTO `library_db`.`book` (title,author) VALUES ('The Odyssey', 'Homer');
INSERT INTO `library_db`.`book` (title,author) VALUES ('Crime and Punishment', 'Fyodor Dostoyevsky');

-- Making an account borrow a book
UPDATE `library_db`.`account` SET current_book = 2 WHERE username = 'user1';

