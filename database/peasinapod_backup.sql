-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: peasinapod
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `peasinapod`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `peasinapod` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `peasinapod`;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `profile_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi2wo4dyk4rok7v4kak8sgkwx0` (`user_id`),
  KEY `FK272mkl0ib6eiqw7ib47tiptyx` (`profile_id`),
  CONSTRAINT `FK272mkl0ib6eiqw7ib47tiptyx` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`),
  CONSTRAINT `FKi2wo4dyk4rok7v4kak8sgkwx0` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES (77,2,3),(79,11,1),(81,6,1),(82,9,6),(85,3,9),(89,6,3),(91,13,8),(92,3,15),(93,3,8),(111,4,8),(112,4,9),(113,2,9),(117,9,20),(118,2,4),(119,3,4);
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matches`
--

DROP TABLE IF EXISTS `matches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matches` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_1_id` bigint DEFAULT NULL,
  `user_2_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcbtyigx08urws0415l5syawg7` (`user_1_id`),
  KEY `FKo9jq1o9t38e3aalxly4jhd0sd` (`user_2_id`),
  CONSTRAINT `FKcbtyigx08urws0415l5syawg7` FOREIGN KEY (`user_1_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKo9jq1o9t38e3aalxly4jhd0sd` FOREIGN KEY (`user_2_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matches`
--

LOCK TABLES `matches` WRITE;
/*!40000 ALTER TABLE `matches` DISABLE KEYS */;
INSERT INTO `matches` VALUES (46,15,6);
/*!40000 ALTER TABLE `matches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `surname` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `distance` int DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKc1dkiawnlj6uoe6fnlwd6j83j` (`user_id`),
  CONSTRAINT `FKawh070wpue34wqvytjqr4hj5e` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES (2,'Mario','Mario','Mario',1,'Super plumber by day, code warrior by night! Love problem-solving, exploring new coding worlds, and cooking mushroom pasta. Looking for a fellow dev with adventure in their heart and a love for creative solutions. LetΓÇÖs-a go!',10,'male'),(3,'Mario','Luigi','Luigi',3,'Tech troubleshooter by day, ghostbuster by night! Love problem-solving, exploring new coding adventures, and making the best pizza. Seeking a fellow dev with a passion for innovation and a heart for adventure. LetΓÇÖs level up together!',15,'male'),(4,'The Hedgehog','Sonic','Sonic',4,'Hi, IΓÇÖm Sonic the Hedgehog, the blue blur who\'s traded speed for syntax! Known for my lightning-fast speed and heroic escapades against Dr. Robotnik. I now channel my boundless energy into mastering the art of JavaScript.',20,'male'),(5,'Kidd','Alex','Alex Kidd',5,'IΓÇÖm a retro gaming icon who\'s taken a fascinating turn in the digital age! Known for my epic adventures in the land of Radaxian, IΓÇÖve recently swapped my trusty motorbike for a sleek Linux machine.',10,'male'),(6,'Toadstool','Peach','Princess Peach',6,'Algorithm alchemist with a royal touch! ≡ƒìæ Love solving puzzles, exploring new code realms, and baking sweet treats. Seeking a fellow dev with a creative mind and a heart full of adventure. Let\'s write our own fairy tale in code!',30,'female'),(9,'Bot','Astro','AstroBot',7,'Meet Astro Bot, the playful and adventurous little robot whoΓÇÖs taken his curiosity to a new dimension! Virtual hero by design, Python programming prodigy by passion!',5,'other'),(11,'Zelda','Princess','Princess Zelda',8,'Royal problem solver and code enchantress! Love unraveling mysteries, exploring digital realms, and creating epic adventures. Seeking a fellow dev with a courageous heart and a knack for innovation. Together, let\'s embark on a legendary coding quest!',10,'female'),(12,'Croft','Lara','Relic Hunter Lara',9,'Adventurer and codebreaker extraordinaire! ≡ƒÅ╣ Love solving ancient puzzles, exploring digital dungeons, and uncovering hidden secrets. Seeking a fellow dev with a spirit of adventure and a talent for innovation. Let\'s embark on epic coding quests together!',20,'female'),(13,'The Hedgehog','Knuckles','Knuckles',15,'Guardian of ancient relics and master code! Love cracking tough algorithms, exploring new code zones, and protecting digital treasures. Seeking fellow dev with a sense of adventure and innovation. Together, we can safeguard the digital realm!',40,'male'),(18,'Kong','Donkey','Donkey',20,'King of the jungle and king of code! ≡ƒìî Love solving complex puzzles, building innovative solutions, and swinging into new challenges. Seeking a fellow dev with a creative mind and a love for adventure. Let\'s climb the coding ladder together!',50,'male');
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programming_languages`
--

DROP TABLE IF EXISTS `programming_languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programming_languages` (
  `profile_id` bigint NOT NULL,
  `language` enum('C_PLUS_PLUS','JAVA','JAVASCRIPT','PYTHON','RUST') DEFAULT NULL,
  KEY `FKqkqc5nh1be9mfpny0kq9ppjoe` (`profile_id`),
  CONSTRAINT `FKqkqc5nh1be9mfpny0kq9ppjoe` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programming_languages`
--

LOCK TABLES `programming_languages` WRITE;
/*!40000 ALTER TABLE `programming_languages` DISABLE KEYS */;
INSERT INTO `programming_languages` VALUES (2,'JAVA'),(2,'C_PLUS_PLUS'),(2,'PYTHON'),(9,'PYTHON'),(9,'PYTHON');
/*!40000 ALTER TABLE `programming_languages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'$2a$10$p4djMY4US48mRDahbuMFr.sPLnvp/I5osR06t.aG.r35I22iCj.Aq','mario@nintendo.com'),(3,'$2a$10$9g5z14CoaL/IcBAYjQAUYuVFPljmQiKXWR.z5Kj91Z845UyOLKGUK','luigi@nintendo.com'),(4,'$2a$10$9g5z14CoaL/IcBAYjQAUYuVFPljmQiKXWR.z5Kj91Z845UyOLKGUK','sonic@sega.com'),(5,'$2a$10$9g5z14CoaL/IcBAYjQAUYuVFPljmQiKXWR.z5Kj91Z845UyOLKGUK','alex@sega.com'),(6,'$2a$10$9g5z14CoaL/IcBAYjQAUYuVFPljmQiKXWR.z5Kj91Z845UyOLKGUK','peach@nintendo.com'),(7,'$2a$10$9g5z14CoaL/IcBAYjQAUYuVFPljmQiKXWR.z5Kj91Z845UyOLKGUK','astro@sony.com'),(8,'$2a$10$9g5z14CoaL/IcBAYjQAUYuVFPljmQiKXWR.z5Kj91Z845UyOLKGUK','zelda@nintendo.com'),(9,'$2a$10$9g5z14CoaL/IcBAYjQAUYuVFPljmQiKXWR.z5Kj91Z845UyOLKGUK','lara@sega.com'),(15,'$2a$10$F7tKjBUKxn63J5KdPKsxgOHwPhf6PzmaAYcSlsVuA7AciTX8ohl0q','knuckles@sega.com'),(20,'$2a$10$wfPhg6RcuJyOdqdZyMwgn.BWvw4NyUrpZ5WIu6xfkBRM8pPv8FOc6','dk@nintendo.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FKrhfovtciq1l558cw6udg0h0d3` (`role_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,2),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),(9,2),(15,2),(20,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-28 18:05:32
