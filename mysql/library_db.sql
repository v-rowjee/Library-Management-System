-- create schema
CREATE SCHEMA `library_db` ;

-- creating table account
CREATE TABLE `library_db`.`account` (
  `acc_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`acc_id`),
  UNIQUE INDEX `acc_id_UNIQUE` (`acc_id` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE);
  
  
-- create table book with foreign key
CREATE TABLE `library_db`.`book` (
  `book_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `author` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`book_id`),
  UNIQUE INDEX `book_id_UNIQUE` (`book_id` ASC) VISIBLE);

-- cerate table account_book for when a user borrow a book
CREATE TABLE `library_db`.`account_book` (
  `borrow_id` INT NOT NULL AUTO_INCREMENT,
  `borrower_id` INT NOT NULL,
  `book_id` INT NOT NULL,
  `date_borrowed` VARCHAR(45) NOT NULL,
  `date_returned` VARCHAR(45) NULL,
  PRIMARY KEY (`borrow_id`),
  INDEX `borrower_id_fk_idx` (`borrower_id` ASC) VISIBLE,
  INDEX `book_id_fk_idx` (`book_id` ASC) VISIBLE,
  CONSTRAINT `borrower_id_fk`
    FOREIGN KEY (`borrower_id`)
    REFERENCES `library_db`.`account` (`acc_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `book_id_fk`
    FOREIGN KEY (`book_id`)
    REFERENCES `library_db`.`book` (`book_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
  
-- populating table account
INSERT INTO `library_db`.`account` (username, password, firstname, lastname) VALUES ('admin','1234','Ved','Rowjee');

-- view account table details
SELECT * FROM `library_db`.`account`;


