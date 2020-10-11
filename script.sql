-- SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
-- SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
-- SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Database cash_register
-- -----------------------------------------------------
-- UNLOCK TABLES;
-- DROP DATABASE IF EXISTS `cash_register` ;
CREATE DATABASE IF NOT EXISTS `cash_register` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cash_register` ;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

SET NAMES utf8 ;

-- -----------------------------------------------------
-- Table `account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `account` (
  `account_id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(20) DEFAULT NULL,
  `password` VARCHAR(45) DEFAULT NULL,
  `first_name` VARCHAR(45) DEFAULT NULL,
  `second_name` VARCHAR(45) DEFAULT NULL,
  `role` VARCHAR(45) DEFAULT 'GUEST',
  `code` INT DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `account_login_uindex` (`login`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'cashier','cashier','Bogdan','Hmelnitskiy','cashier', null),(2,'scash','scash','Ktoto','Toto','superCashier',1337),(3,'expert','expert','Nikita','Kletsun','expert', null);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

-- -----------------------------------------------------
-- Table `check`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `check` (
  `check_id` INT NOT NULL AUTO_INCREMENT,
  `totalprice` DOUBLE DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `status` VARCHAR(45) NOT NULL,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`check_id`),
  KEY `fk_check_account1_idx` (`account_id`),
  CONSTRAINT `fk_check_account1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

-- -----------------------------------------------------
-- Table `product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `product` (
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `product_code` INT DEFAULT NULL,
  `name` VARCHAR(50) DEFAULT NULL,
  `price` INT DEFAULT NULL,
  `amount` INT DEFAULT NULL,
  `measure` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_code_uindex` (`product_code`),
  UNIQUE KEY `product_name_uindex` (`name`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,1111,'Apple',2500,6,'kg'), (2,1234,'Bread',1500,15,'pc');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

-- -----------------------------------------------------
-- Table `check_has_product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `check_has_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `check_has_product` (
  `check_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `amount` INT DEFAULT NULL,
  PRIMARY KEY (`check_id`, `product_id`),
  KEY `fk_check_has_product_product1_idx` (`product_id`),
  KEY `fk_check_has_product_check_idx` (`check_id`),
  CONSTRAINT `fk_check_has_product_check` FOREIGN KEY (`check_id`) REFERENCES `check` (`check_id`),
  CONSTRAINT `fk_check_has_product_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


-- SET SQL_MODE=@OLD_SQL_MODE;
-- SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
-- SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
