-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: jobportal
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `job_company`
--

DROP TABLE IF EXISTS `job_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_company` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_company`
--

LOCK TABLES `job_company` WRITE;
/*!40000 ALTER TABLE `job_company` DISABLE KEYS */;
INSERT INTO `job_company` VALUES (1,NULL,'Java Company'),(2,NULL,'Java Company'),(3,NULL,'SoftWare Company'),(4,NULL,'ABC');
/*!40000 ALTER TABLE `job_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_location`
--

DROP TABLE IF EXISTS `job_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_location` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_location`
--

LOCK TABLES `job_location` WRITE;
/*!40000 ALTER TABLE `job_location` DISABLE KEYS */;
INSERT INTO `job_location` VALUES (1,'Cairo','Egypt'),(2,'Cairo','Egypt'),(3,'Alex','Egypt'),(4,'Cairo','Egypt');
/*!40000 ALTER TABLE `job_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_post_activity`
--

DROP TABLE IF EXISTS `job_post_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_post_activity` (
  `job_post_id` bigint NOT NULL AUTO_INCREMENT,
  `job_description` varchar(10000) DEFAULT NULL,
  `job_title` varchar(255) DEFAULT NULL,
  `job_type` varchar(255) DEFAULT NULL,
  `posted_date` datetime(6) DEFAULT NULL,
  `remote` varchar(255) DEFAULT NULL,
  `salary` varchar(255) DEFAULT NULL,
  `job_company_id` bigint DEFAULT NULL,
  `job_location_id` bigint DEFAULT NULL,
  `posted_by_id` bigint DEFAULT NULL,
  PRIMARY KEY (`job_post_id`),
  KEY `FKpjpv059hollr4tk92ms09s6is` (`job_company_id`),
  KEY `FK44003mnvj29aiijhsc6ftsgxe` (`job_location_id`),
  KEY `FK62yqqbypsq2ik34ngtlw4m9k3` (`posted_by_id`),
  CONSTRAINT `FK44003mnvj29aiijhsc6ftsgxe` FOREIGN KEY (`job_location_id`) REFERENCES `job_location` (`id`),
  CONSTRAINT `FK62yqqbypsq2ik34ngtlw4m9k3` FOREIGN KEY (`posted_by_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKpjpv059hollr4tk92ms09s6is` FOREIGN KEY (`job_company_id`) REFERENCES `job_company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_post_activity`
--

LOCK TABLES `job_post_activity` WRITE;
/*!40000 ALTER TABLE `job_post_activity` DISABLE KEYS */;
INSERT INTO `job_post_activity` VALUES (1,'We are looking for a Java Developer with experience in Spring Boot, RESTful APIs, and MySQL. You will develop and maintain scalable Java applications, ensuring security and performance. Knowledge of Spring Data JPA, Thymeleaf, and front-end technologies (Bootstrap, jQuery) is a plus. Strong problem-solving skills and experience with Git and Maven are required.','Java Developer','Full-Time','2025-03-28 18:45:35.081000','Office only','25000',1,1,1),(2,'As a Senior Java Developer, you will design, develop, and maintain scalable Java applications, ensuring high performance, security, and efficiency. You will optimize database interactions, lead code reviews, and mentor junior developers while enforcing best practices. Collaboration with cross-functional teams is essential to deliver high-quality solutions that meet business needs.','Senior Java Developer','Full-Time','2025-03-29 09:51:03.278000','Office only','50000',2,2,1),(3,'We are looking for a .NET Junior Developer to join our team and contribute to building scalable web applications. You will work with C#, ASP.NET, and SQL Server, assisting in developing, testing, and maintaining backend services. Collaborate with senior developers to troubleshoot and enhance system performance. A strong foundation in OOP, databases, and RESTful APIs is preferred.','.Net Junior Developer','Full-Time','2025-03-30 15:46:53.761000','Office only','50000',3,3,1),(4,'A Data Scientist leverages data to extract meaningful insights, build predictive models, and support data-driven decision-making. They use programming, statistics, and machine learning to analyze complex datasets and uncover patterns. Collaboration with business teams helps translate data into actionable strategies. Strong problem-solving skills and expertise in tools like Python, SQL, and AI frameworks are essential for success in this role.','Data Scientist','Full-Time','2025-03-31 14:50:24.440000','Office only','25000',4,4,3);
/*!40000 ALTER TABLE `job_post_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_seeker_apply`
--

DROP TABLE IF EXISTS `job_seeker_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_seeker_apply` (
  `apply_id` bigint NOT NULL AUTO_INCREMENT,
  `apply_date` date DEFAULT NULL,
  `cover_letter` varchar(500) DEFAULT NULL,
  `job_id` bigint DEFAULT NULL,
  `user_account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`apply_id`),
  KEY `FKakxmpj8n1iun720xvfne3diyv` (`job_id`),
  KEY `FKbgkjhc8q15ycdeb3nf18172uy` (`user_account_id`),
  CONSTRAINT `FKakxmpj8n1iun720xvfne3diyv` FOREIGN KEY (`job_id`) REFERENCES `job_post_activity` (`job_post_id`),
  CONSTRAINT `FKbgkjhc8q15ycdeb3nf18172uy` FOREIGN KEY (`user_account_id`) REFERENCES `job_seekers_profile` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_seeker_apply`
--

LOCK TABLES `job_seeker_apply` WRITE;
/*!40000 ALTER TABLE `job_seeker_apply` DISABLE KEYS */;
INSERT INTO `job_seeker_apply` VALUES (1,'2025-03-31','I\'m Good',1,2),(2,'2025-04-01','I am excited to apply for the Senior Java Developer position at Java Company. With expertise in Java, Spring Boot, and MySQL, I design scalable applications with high performance and security. I lead code reviews, optimize databases, and mentor developers. I look forward to contributing to your team.',2,2),(3,'2025-04-01','I am eager to apply for the .NET Junior Developer position at SoftWare Company. Proficient in C#, ASP.NET, and SQL Server, I assist in developing and maintaining scalable web applications. With a strong foundation in OOP, databases, and RESTful APIs, I look forward to contributing to your team.',3,2),(4,'2025-04-01','I am eager to apply for the Data Scientist position at your company. With expertise in Python, SQL, and AI frameworks, I analyze complex datasets to drive insights and build predictive models. My problem-solving skills and collaboration experience enable me to translate data into actionable strategies.',4,2),(5,'2025-04-01','I am excited to apply for the Senior Java Developer position at Java Company. With expertise in Java, Spring Boot, and MySQL, I design scalable applications with high performance and security. I lead code reviews, optimize databases, and mentor developers. I look forward to contributing to your team.\r\n\r\nBest regards,',1,4),(6,'2025-04-01','I am excited to apply for the Senior Java Developer position at Java Company. With expertise in Java, Spring Boot, and MySQL, I design scalable applications with high performance and security. I lead code reviews, optimize databases, and mentor developers. I look forward to contributing to your team.\r\n\r\nBest regards,',2,4),(7,'2025-04-01','I am eager to apply for the .NET Junior Developer position at SoftWare Company. Proficient in C#, ASP.NET, and SQL Server, I assist in developing and maintaining scalable web applications. With a strong foundation in OOP, databases, and RESTful APIs, I look forward to contributing to your team.',3,4),(8,'2025-04-01','I am eager to apply for the Data Scientist position at your company. With expertise in Python, SQL, and AI frameworks, I analyze complex datasets to drive insights and build predictive models. My problem-solving skills and collaboration experience enable me to translate data into actionable strategies.\r\n\r\nBest regards,',4,4),(9,'2025-04-09','i\'m good',1,5),(10,'2025-04-09','.',2,5),(11,'2025-04-09','.',3,5),(12,'2025-04-09','.',4,5),(13,'2025-05-03','i\'m good ',1,6);
/*!40000 ALTER TABLE `job_seeker_apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_seeker_save`
--

DROP TABLE IF EXISTS `job_seeker_save`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_seeker_save` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `job_id` bigint DEFAULT NULL,
  `user_account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK346w0g053dcbavpx0o6ufig1l` (`user_account_id`,`job_id`),
  KEY `FKr7wp8mf5tnpcbarjydb14v901` (`job_id`),
  CONSTRAINT `FKoq0c21najootqfe1ow9eqdmr4` FOREIGN KEY (`user_account_id`) REFERENCES `job_seekers_profile` (`user_id`),
  CONSTRAINT `FKr7wp8mf5tnpcbarjydb14v901` FOREIGN KEY (`job_id`) REFERENCES `job_post_activity` (`job_post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_seeker_save`
--

LOCK TABLES `job_seeker_save` WRITE;
/*!40000 ALTER TABLE `job_seeker_save` DISABLE KEYS */;
INSERT INTO `job_seeker_save` VALUES (1,1,2),(2,1,6);
/*!40000 ALTER TABLE `job_seeker_save` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_seekers_profile`
--

DROP TABLE IF EXISTS `job_seekers_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_seekers_profile` (
  `user_id` bigint NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `current_job` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `graduation_date` datetime(6) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `photos_image_path` varchar(500) DEFAULT NULL,
  `resume` varchar(255) DEFAULT NULL,
  `university` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FKti4ow4m6vx17tikqmbk99tmj0` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_seekers_profile`
--

LOCK TABLES `job_seekers_profile` WRITE;
/*!40000 ALTER TABLE `job_seekers_profile` DISABLE KEYS */;
INSERT INTO `job_seekers_profile` VALUES (2,'Cairo','Egypt','Java Developer','Seeker','2022-07-01 00:00:00.000000','One','Mechatronics','2.PNG','Hassan Adel cv.pdf','Ain Shams University '),(4,'Cairo','Egypt','Java Developer','Seeker','2024-07-01 00:00:00.000000','Two','Mechatronics','3.PNG','Hassan Adel cv.pdf','Ain Shams University '),(5,'Cairo','Egypt','Java Developer','said','2025-07-01 00:00:00.000000','ashraf','Mechatronics','/images/default-profile.png',NULL,'Ain Shams University '),(6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `job_seekers_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recruiters_profile`
--

DROP TABLE IF EXISTS `recruiters_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recruiters_profile` (
  `user_id` bigint NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `profile_photo_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FKon6veiky5gd43ofo6txu2onfv` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recruiters_profile`
--

LOCK TABLES `recruiters_profile` WRITE;
/*!40000 ALTER TABLE `recruiters_profile` DISABLE KEYS */;
INSERT INTO `recruiters_profile` VALUES (1,'Cairo','ABC','Egypt','Recuriter','One','3.PNG'),(3,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `recruiters_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'recruiter'),(2,'job seeker');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skills`
--

DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `experience_level` varchar(255) DEFAULT NULL,
  `skill_name` varchar(255) DEFAULT NULL,
  `years_of_experience` int NOT NULL,
  `job_seeker_profile_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK79vnitytw6nr66ol90nxvy733` (`job_seeker_profile_id`),
  CONSTRAINT `FK79vnitytw6nr66ol90nxvy733` FOREIGN KEY (`job_seeker_profile_id`) REFERENCES `job_seekers_profile` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skills`
--

LOCK TABLES `skills` WRITE;
/*!40000 ALTER TABLE `skills` DISABLE KEYS */;
INSERT INTO `skills` VALUES (1,'Intermediate','c++',0,2),(2,'Intermediate','Java',0,2),(3,'Advanced','Java',0,4);
/*!40000 ALTER TABLE `skills` ENABLE KEYS */;
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
  `is_active` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `registration_date` date DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'rec1@gmail.com',_binary '','recruiter1','$2a$10$flixVkvYgdwBkmLTiQMcyuP9m4Kp6vfrLyz4slzS6xW34CllENIiW','2025-03-28',1),(2,'seeker1@gmail.com',_binary '','Seeker1','$2a$10$caNSgDBboRZWG1Q6PMSiPuuL5J..Vf5A60dSIUQTOv2MH21KqceG6','2025-03-28',2),(3,'rec2@gmail.com',_binary '','recruiter2','$2a$10$aIqI6pcGpS7w8uehkUIMvOa4uWRH.2mkLV.55ZoMswVpVCXfw903.','2025-03-31',1),(4,'seeker2@gmail.com',_binary '','Seeker2','$2a$10$qOZ3uwOXRr77tyKfAgVWn.G3DrqjdD6IXMv/lrOTZ1SB7SfC7ujlC','2025-04-01',2),(5,'felly@gmail.com',_binary '','felly','$2a$10$fi2NYi2SJmjjEwH47OxdseuDW0E3oQGzXjuPQX5OJaQ7Ua.OK0b.S','2025-04-09',2),(6,'ahmedsaad@gmail.com',_binary '','ahmed saad','$2a$10$LBjN23A9oe1CiAizP6mFieQZ5lNKcq0YSVzQ/qzZl3DAAemMRiu6C','2025-05-03',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-30 12:19:09
