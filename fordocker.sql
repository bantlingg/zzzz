-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: quarry_monitoring
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `equipment`
--
CREATE DATABASE quarry_monitoring;

DROP TABLE IF EXISTS `equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `quarry_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7woq3ctwcntqbkw7lrq1knhot` (`quarry_id`),
  CONSTRAINT `FK7woq3ctwcntqbkw7lrq1knhot` FOREIGN KEY (`quarry_id`) REFERENCES `quarries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment`
--

LOCK TABLES `equipment` WRITE;
/*!40000 ALTER TABLE `equipment` DISABLE KEYS */;
INSERT INTO `equipment` VALUES (1,'погрузчик гусеничный JCB','В ремонте','погрузчик',2);
/*!40000 ALTER TABLE `equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipment_licenses`
--

DROP TABLE IF EXISTS `equipment_licenses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipment_licenses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiry_date` date NOT NULL,
  `issue_date` date NOT NULL,
  `license_number` varchar(255) NOT NULL,
  `notes` text,
  `status` varchar(255) NOT NULL,
  `equipment_id` bigint NOT NULL,
  `worker_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbyimd9vjd1py6aqkragq228ni` (`equipment_id`),
  KEY `FK9gd3rpdib6gc5ipjm9ft09og9` (`worker_id`),
  CONSTRAINT `FK9gd3rpdib6gc5ipjm9ft09og9` FOREIGN KEY (`worker_id`) REFERENCES `workers` (`id`),
  CONSTRAINT `FKbyimd9vjd1py6aqkragq228ni` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment_licenses`
--

LOCK TABLES `equipment_licenses` WRITE;
/*!40000 ALTER TABLE `equipment_licenses` DISABLE KEYS */;
INSERT INTO `equipment_licenses` VALUES (1,'0123-03-12','0123-03-12','123123','123123','EXPIRED',1,1);
/*!40000 ALTER TABLE `equipment_licenses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `explosion_licenses`
--

DROP TABLE IF EXISTS `explosion_licenses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `explosion_licenses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiry_date` date NOT NULL,
  `issue_date` date NOT NULL,
  `license_number` varchar(255) NOT NULL,
  `license_type` varchar(255) NOT NULL,
  `max_power` int NOT NULL,
  `notes` text,
  `status` varchar(255) NOT NULL,
  `worker_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkwbxiq732chk4rodr64yfx60k` (`worker_id`),
  CONSTRAINT `FKkwbxiq732chk4rodr64yfx60k` FOREIGN KEY (`worker_id`) REFERENCES `workers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `explosion_licenses`
--

LOCK TABLES `explosion_licenses` WRITE;
/*!40000 ALTER TABLE `explosion_licenses` DISABLE KEYS */;
INSERT INTO `explosion_licenses` VALUES (1,'3123-03-12','0123-03-12','123123','Взрывник',123123,'12312312123123','ACTIVE',1);
/*!40000 ALTER TABLE `explosion_licenses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `explosions`
--

DROP TABLE IF EXISTS `explosions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `explosions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_time` datetime(6) NOT NULL,
  `location` varchar(255) NOT NULL,
  `power` double NOT NULL,
  `status` varchar(255) NOT NULL,
  `quarry_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdn70qe8et8nv355ko7o1diopl` (`quarry_id`),
  CONSTRAINT `FKdn70qe8et8nv355ko7o1diopl` FOREIGN KEY (`quarry_id`) REFERENCES `quarries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `explosions`
--

LOCK TABLES `explosions` WRITE;
/*!40000 ALTER TABLE `explosions` DISABLE KEYS */;
INSERT INTO `explosions` VALUES (2,'1231-03-12 18:03:00.000000','123123',123123,'COMPLETED',2),(4,'1231-03-12 18:03:00.000000','123123',123123,'PLANNED',2),(5,'1233-03-12 16:03:00.000000','321',213132,'PLANNED',2),(7,'1233-03-12 16:02:00.000000','12321',123123,'IN_PROGRESS',2);
/*!40000 ALTER TABLE `explosions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `photos`
--

DROP TABLE IF EXISTS `photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `photos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `upload_date` datetime(6) NOT NULL,
  `quarry_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK31u7ynbxtifwuwt324e4uonjr` (`quarry_id`),
  CONSTRAINT `FK31u7ynbxtifwuwt324e4uonjr` FOREIGN KEY (`quarry_id`) REFERENCES `quarries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `photos`
--

LOCK TABLES `photos` WRITE;
/*!40000 ALTER TABLE `photos` DISABLE KEYS */;
INSERT INTO `photos` VALUES (1,'','/uploads/photos/1748451323369_but5.jpg','камень2','2025-05-28 16:55:23.370347',2),(4,'','/uploads/photos/1748456267855_JQB2QeWXUEyOxzodJXPc86Sb-SI-1920.jpg','123','2025-05-28 18:17:47.857750',2),(5,'','/uploads/photos/1748457027876_336830 - 004-2020-08-28-19-05-21-Штаб-квартира - Kovalyuk-result-result.jpg','123','2025-05-28 18:30:27.878650',2);
/*!40000 ALTER TABLE `photos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quarries`
--

DROP TABLE IF EXISTS `quarries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quarries` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `area` double NOT NULL,
  `depth` double NOT NULL,
  `location` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quarries`
--

LOCK TABLES `quarries` WRITE;
/*!40000 ALTER TABLE `quarries` DISABLE KEYS */;
INSERT INTO `quarries` VALUES (2,5000,500,'Где-то рядом с екатеринбургом','Урал Асбест','ACTIVE');
/*!40000 ALTER TABLE `quarries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `report_date` datetime(6) NOT NULL,
  `report_type` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `author_id` bigint NOT NULL,
  `quarry_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk6ucusc11cht9lrt5w5v9otrw` (`author_id`),
  KEY `FKeuijhgkhuajnb3f5pkhkxiyys` (`quarry_id`),
  CONSTRAINT `FKeuijhgkhuajnb3f5pkhkxiyys` FOREIGN KEY (`quarry_id`) REFERENCES `quarries` (`id`),
  CONSTRAINT `FKk6ucusc11cht9lrt5w5v9otrw` FOREIGN KEY (`author_id`) REFERENCES `workers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
INSERT INTO `reports` VALUES (1,'123123аыфолтштофыатошафывотшаывтоаывтоылвааывтош','2025-05-28 17:02:04.779902','ЕЖЕДНЕВНЫЙ','123123123',1,2),(2,'123123','2025-05-28 17:25:24.289038','DAILY','123123',1,2),(3,'3232','2025-05-28 17:49:41.997939','ГОДОВОЙ','12332',3,2),(5,'123123','2025-05-28 18:17:32.989530','ЕЖЕДНЕВНЫЙ','123321',3,2),(6,'12323','2025-05-28 18:30:47.143616','ЕЖЕДНЕВНЫЙ','отчет',1,2);
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `safety_inspections`
--

DROP TABLE IF EXISTS `safety_inspections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `safety_inspections` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `findings` text NOT NULL,
  `inspection_date` datetime(6) NOT NULL,
  `inspection_type` varchar(255) NOT NULL,
  `next_inspection_date` datetime(6) NOT NULL,
  `recommendations` text,
  `status` varchar(255) NOT NULL,
  `inspector_id` bigint NOT NULL,
  `quarry_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkmbua9xoqn0hh4fu9jm36l667` (`inspector_id`),
  KEY `FKtp74euqw68vso2owluiea2ms1` (`quarry_id`),
  CONSTRAINT `FKkmbua9xoqn0hh4fu9jm36l667` FOREIGN KEY (`inspector_id`) REFERENCES `workers` (`id`),
  CONSTRAINT `FKtp74euqw68vso2owluiea2ms1` FOREIGN KEY (`quarry_id`) REFERENCES `quarries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `safety_inspections`
--

LOCK TABLES `safety_inspections` WRITE;
/*!40000 ALTER TABLE `safety_inspections` DISABLE KEYS */;
INSERT INTO `safety_inspections` VALUES (2,'123','2025-05-28 17:45:24.018521','REGULAR','1212-12-12 16:21:00.000000','321','PASSED',3,2);
/*!40000 ALTER TABLE `safety_inspections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `pas` varchar(255) NOT NULL,
  `role` varchar(20) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `quarry_id` bigint DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_k8d0f2n7n88w1a16yhua64onx` (`user_name`),
  KEY `FKcidej4d00oh71px8qriocs2q3` (`quarry_id`),
  CONSTRAINT `FKcidej4d00oh71px8qriocs2q3` FOREIGN KEY (`quarry_id`) REFERENCES `quarries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin@mail.ru','admin','ADMIN','admin',NULL),(2,'user@mail.ru','user','USER','user',2),(3,'manager@mail.ru','manager','MANAGER','manager',2),(4,'asd@awdasd','asd','USER','asd',2),(6,'bomb@mail.ru','bomb','BLASTING_ENGINEER','bomb',2),(8,'guest@mail.ru','guest','GUEST','guest',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workers`
--

DROP TABLE IF EXISTS `workers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `position` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `quarry_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo2r4viu9ajvkxh4mcgu8hnn8w` (`quarry_id`),
  CONSTRAINT `FKo2r4viu9ajvkxh4mcgu8hnn8w` FOREIGN KEY (`quarry_id`) REFERENCES `quarries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workers`
--

LOCK TABLES `workers` WRITE;
/*!40000 ALTER TABLE `workers` DISABLE KEYS */;
INSERT INTO `workers` VALUES (1,'Александр','Никита','оператор погрузчика','FIRED',2),(3,'Никита','Никита','инспектор','ACTIVE',2);
/*!40000 ALTER TABLE `workers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-29  3:42:44
