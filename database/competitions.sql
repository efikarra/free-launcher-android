CREATE DATABASE  IF NOT EXISTS `competitions` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `competitions`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: competitions
-- ------------------------------------------------------
-- Server version	5.6.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bid`
--

DROP TABLE IF EXISTS `bid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bid` (
  `bid_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `price` decimal(12,2) NOT NULL,
  `est_time` date NOT NULL,
  `exp_id` bigint(20) NOT NULL,
  `proj_id` bigint(20) NOT NULL,
  PRIMARY KEY (`bid_id`),
  UNIQUE KEY `unique_index` (`proj_id`,`exp_id`),
  KEY `expert_bid_idx` (`exp_id`),
  KEY `project_bid_idx` (`proj_id`),
  CONSTRAINT `bid_expert_exp_id` FOREIGN KEY (`exp_id`) REFERENCES `expert` (`exp_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bid_project_proj_id` FOREIGN KEY (`proj_id`) REFERENCES `project` (`proj_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bid`
--

LOCK TABLES `bid` WRITE;
/*!40000 ALTER TABLE `bid` DISABLE KEYS */;
INSERT INTO `bid` VALUES (31,55.00,'2015-09-19',5,20),(35,1900.00,'2015-12-20',50,26),(58,333.00,'2015-09-20',5,26),(59,490.00,'2015-11-05',5,25),(61,600.00,'2015-11-28',42,22),(62,480.00,'2015-11-26',42,25),(66,850.00,'2015-09-21',5,27),(68,900.00,'2015-11-10',52,23),(69,580.00,'2015-10-17',52,24),(70,1300.00,'2015-12-05',42,28);
/*!40000 ALTER TABLE `bid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employer`
--

DROP TABLE IF EXISTS `employer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employer` (
  `emp_id` bigint(20) NOT NULL,
  PRIMARY KEY (`emp_id`),
  CONSTRAINT `employer_user_user_id` FOREIGN KEY (`emp_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employer`
--

LOCK TABLES `employer` WRITE;
/*!40000 ALTER TABLE `employer` DISABLE KEYS */;
INSERT INTO `employer` VALUES (10),(44),(48),(49);
/*!40000 ALTER TABLE `employer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluates`
--

DROP TABLE IF EXISTS `evaluates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluates` (
  `exp_id` bigint(20) NOT NULL,
  `proj_id` bigint(20) NOT NULL,
  `eval_id` bigint(20) NOT NULL,
  PRIMARY KEY (`exp_id`,`proj_id`),
  KEY `evaluation_idx` (`eval_id`),
  KEY `eval_for_project_proj_id_idx` (`proj_id`),
  CONSTRAINT `eval_for_evaluation_eval_id` FOREIGN KEY (`eval_id`) REFERENCES `evaluation` (`eval_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `eval_for_expert_exp_id` FOREIGN KEY (`exp_id`) REFERENCES `expert` (`exp_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `eval_for_project_proj_id` FOREIGN KEY (`proj_id`) REFERENCES `project` (`proj_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluates`
--

LOCK TABLES `evaluates` WRITE;
/*!40000 ALTER TABLE `evaluates` DISABLE KEYS */;
/*!40000 ALTER TABLE `evaluates` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_EmployerEvaluation_INSERT  
AFTER INSERT ON competitions.evaluates
 FOR EACH ROW BEGIN    
 UPDATE competitions.user as u    
 INNER JOIN competitions.project as proj ON u.user_id=proj.emp_id
 SET u.rating=TRUNCATE((SELECT AVG(eval.rating)  
 FROM competitions.evaluates as evalFor    
 INNER JOIN competitions.evaluation as eval ON evalFor.eval_id=eval.eval_id  
 INNER JOIN competitions.project as proj ON evalFor.proj_Id=proj.proj_Id
 WHERE proj.emp_id=(SELECT emp_id FROM competitions.project as p WHERE p.proj_id=NEW.proj_Id)     
 GROUP BY proj.emp_id    ),1)    
 WHERE proj.proj_id=NEW.proj_id; 
 END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_EmployerEvaluation_UPDATE 
AFTER UPDATE ON competitions.evaluates
 FOR EACH ROW BEGIN    
 UPDATE competitions.user as u    
 INNER JOIN competitions.project as proj ON u.user_id=proj.emp_id
 SET u.rating=TRUNCATE((SELECT AVG(eval.rating)  
 FROM competitions.evaluates as evalFor    
 INNER JOIN competitions.evaluation as eval ON evalFor.eval_id=eval.eval_id  
 INNER JOIN competitions.project as proj ON evalFor.proj_Id=proj.proj_Id
 WHERE proj.emp_id=(SELECT emp_id FROM competitions.project as p WHERE p.proj_id=NEW.proj_Id)     
 GROUP BY proj.emp_id    ),1)    
 WHERE proj.proj_id=NEW.proj_id; 
 END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_EmployerEvaluation_DELETE 
AFTER DELETE ON competitions.evaluates
FOR EACH ROW BEGIN    
 UPDATE competitions.user as u    
 INNER JOIN competitions.project as proj ON u.user_id=proj.emp_id
 SET u.rating=TRUNCATE((SELECT AVG(eval.rating)  
 FROM competitions.evaluates as evalFor    
 INNER JOIN competitions.evaluation as eval ON evalFor.eval_id=eval.eval_id  
 INNER JOIN competitions.project as proj ON evalFor.proj_Id=proj.proj_Id
 WHERE proj.emp_id=(SELECT emp_id FROM competitions.project as p WHERE p.proj_id=OLD.proj_Id)     
 GROUP BY proj.emp_id    ),1)    
 WHERE proj.proj_id=OLD.proj_id; 
 END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `evaluation`
--

DROP TABLE IF EXISTS `evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluation` (
  `eval_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment` varchar(200) NOT NULL,
  `rating` decimal(3,1) NOT NULL,
  PRIMARY KEY (`eval_id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation`
--

LOCK TABLES `evaluation` WRITE;
/*!40000 ALTER TABLE `evaluation` DISABLE KEYS */;
INSERT INTO `evaluation` VALUES (48,'excellent',9.0),(49,'Good',6.0);
/*!40000 ALTER TABLE `evaluation` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_Evaluation_UPDATE 
AFTER UPDATE ON competitions.evaluation
FOR EACH ROW
BEGIN
   UPDATE (SELECT AVG(eval.rating) as avgR, evalFor.exp_id as expId
   FROM competitions.is_evaluated_for as evalFor
   INNER JOIN competitions.evaluation as eval ON evalFor.eval_id=eval.eval_id
    GROUP BY evalFor.exp_id
   ) as avgTable,competitions.user as u
   SET u.rating=TRUNCATE(avgTable.avgR,1)
   WHERE expId=u.user_id;
   UPDATE (SELECT AVG(eval.rating) AS avgR, proj.emp_id as empId
 FROM competitions.evaluates as evalFor    
 INNER JOIN competitions.evaluation as eval ON evalFor.eval_id=eval.eval_id  
 INNER JOIN competitions.project as proj ON evalFor.proj_Id=proj.proj_Id
 GROUP BY proj.emp_id ) as avgTable,competitions.user as u
 INNER JOIN competitions.project as proj ON u.user_id=proj.emp_id
   SET u.rating=TRUNCATE(avgTable.avgR,1)
   WHERE empId=u.user_id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_Evaluation_DELETE
AFTER DELETE ON competitions.evaluation
FOR EACH ROW
BEGIN
   UPDATE (SELECT AVG(eval.rating) as avgR, evalFor.exp_id as expId
   FROM competitions.is_evaluated_for as evalFor
   INNER JOIN competitions.evaluation as eval ON evalFor.eval_id=eval.eval_id
    GROUP BY evalFor.exp_id
   ) as avgTable,competitions.user as u
   SET u.rating=TRUNCATE(avgTable.avgR,1)
   WHERE expId=u.user_id;
   UPDATE (SELECT AVG(eval.rating) AS avgR, proj.emp_id as empId
 FROM competitions.evaluates as evalFor    
 INNER JOIN competitions.evaluation as eval ON evalFor.eval_id=eval.eval_id  
 INNER JOIN competitions.project as proj ON evalFor.proj_Id=proj.proj_Id
 GROUP BY proj.emp_id ) as avgTable,competitions.user as u
 INNER JOIN competitions.project as proj ON u.user_id=proj.emp_id
   SET u.rating=TRUNCATE(avgTable.avgR,1)
   WHERE empId=u.user_id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `expert`
--

DROP TABLE IF EXISTS `expert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expert` (
  `exp_id` bigint(20) NOT NULL,
  PRIMARY KEY (`exp_id`),
  CONSTRAINT `expert_user_user_id` FOREIGN KEY (`exp_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expert`
--

LOCK TABLES `expert` WRITE;
/*!40000 ALTER TABLE `expert` DISABLE KEYS */;
INSERT INTO `expert` VALUES (5),(42),(50),(52),(53);
/*!40000 ALTER TABLE `expert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expert_qualification`
--

DROP TABLE IF EXISTS `expert_qualification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expert_qualification` (
  `exp_id` bigint(20) NOT NULL,
  `qual_id` bigint(20) NOT NULL,
  PRIMARY KEY (`exp_id`,`qual_id`),
  KEY `fk_qualification_idx` (`qual_id`),
  CONSTRAINT `expert_qualification_expert_exp_id` FOREIGN KEY (`exp_id`) REFERENCES `expert` (`exp_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `expert_qualification_qualification_qual_id` FOREIGN KEY (`qual_id`) REFERENCES `qualification` (`qual_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expert_qualification`
--

LOCK TABLES `expert_qualification` WRITE;
/*!40000 ALTER TABLE `expert_qualification` DISABLE KEYS */;
INSERT INTO `expert_qualification` VALUES (5,1),(5,2),(52,3),(52,4),(42,5),(42,6),(52,7),(5,8),(42,8),(5,9),(42,9),(53,9),(50,10),(53,10);
/*!40000 ALTER TABLE `expert_qualification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gcm_registration`
--

DROP TABLE IF EXISTS `gcm_registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gcm_registration` (
  `registrationId` varchar(200) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_index` (`user_id`,`registrationId`),
  KEY `user_idx` (`user_id`),
  CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gcm_registration`
--

LOCK TABLES `gcm_registration` WRITE;
/*!40000 ALTER TABLE `gcm_registration` DISABLE KEYS */;
/*!40000 ALTER TABLE `gcm_registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `is_evaluated_for`
--

DROP TABLE IF EXISTS `is_evaluated_for`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `is_evaluated_for` (
  `exp_id` bigint(20) NOT NULL,
  `proj_id` bigint(20) NOT NULL,
  `eval_id` bigint(20) NOT NULL,
  PRIMARY KEY (`exp_id`,`proj_id`),
  KEY `is_evaluated_for_project_exp_id_idx` (`proj_id`),
  KEY `is_evaluated_for_evaluation_exp_id_idx` (`eval_id`),
  CONSTRAINT `is_evaluated_for_evaluation_eval_id` FOREIGN KEY (`eval_id`) REFERENCES `evaluation` (`eval_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `is_evaluated_for_expert_exp_id` FOREIGN KEY (`exp_id`) REFERENCES `expert` (`exp_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `is_evaluated_for_project_proj_id` FOREIGN KEY (`proj_id`) REFERENCES `project` (`proj_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `is_evaluated_for`
--

LOCK TABLES `is_evaluated_for` WRITE;
/*!40000 ALTER TABLE `is_evaluated_for` DISABLE KEYS */;
INSERT INTO `is_evaluated_for` VALUES (5,20,48),(52,24,49);
/*!40000 ALTER TABLE `is_evaluated_for` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_ExpertEvaluation_INSERT  
AFTER INSERT ON competitions.is_evaluated_for
 FOR EACH ROW BEGIN    
 UPDATE competitions.user as u    
 SET u.rating=TRUNCATE((SELECT AVG(eval.rating)  
 FROM competitions.is_evaluated_for as evalFor    
 INNER JOIN competitions.evaluation as eval ON evalFor.eval_id=eval.eval_id  
 WHERE NEW.exp_id=evalFor.exp_id     GROUP BY evalFor.exp_id    ),1)    
 WHERE u.user_id=NEW.exp_id; 
 END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_ExpertEvaluation_UPDATE  
AFTER UPDATE ON competitions.is_evaluated_for
 FOR EACH ROW BEGIN    
 UPDATE competitions.user as u    
 SET u.rating=TRUNCATE((SELECT AVG(eval.rating)  
 FROM competitions.is_evaluated_for as evalFor    
 INNER JOIN competitions.evaluation as eval ON evalFor.eval_id=eval.eval_id  
 WHERE NEW.exp_id=evalFor.exp_id     GROUP BY evalFor.exp_id    ),1)    
 WHERE u.user_id=NEW.exp_id; 
 END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_ExpertEvaluation_DELETE 
AFTER DELETE ON competitions.is_evaluated_for
FOR EACH ROW BEGIN    
 UPDATE competitions.user as u  
  SET u.rating=TRUNCATE((SELECT AVG(eval.rating)  
 FROM competitions.is_evaluated_for as evalFor    
 INNER JOIN competitions.evaluation as eval ON evalFor.eval_id=eval.eval_id  
 WHERE OLD.exp_id=evalFor.exp_id     
 GROUP BY evalFor.exp_id    ),1)    
 WHERE u.user_id=OLD.exp_id; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `proj_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `time_limit` date NOT NULL,
  `price_limit` decimal(12,2) DEFAULT NULL,
  `country` varchar(80) DEFAULT NULL,
  `location` varchar(80) DEFAULT NULL,
  `title` varchar(50) NOT NULL,
  `short_descr` varchar(200) NOT NULL,
  `long_descr` text,
  `emp_id` bigint(20) NOT NULL,
  `is_closed` tinyint(1) NOT NULL,
  `end_date` date NOT NULL,
  PRIMARY KEY (`proj_id`),
  KEY `project_employer_idx` (`emp_id`),
  CONSTRAINT `project_employer_emp_id` FOREIGN KEY (`emp_id`) REFERENCES `employer` (`emp_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (19,'2016-04-15',2000.00,'Greece','Grevena','iPhone app','We need a front-end iPhone app','We need a front-end iPhone app :\n- Back-end is Java and they communicate using JSON.\n- It doesn\'t need connection to database and just send request to server and server will process request and return it back.\n- Main part is scan vehicle VIN number and convert it to number.\n- Please have experience on scan vehicle VIN number and just convert it to number.\n- Communication is a key.\n- This is just version 1 and we will go for next version after version 1 immediately.\n- Wee need to have source code for every milestone.',10,0,'2015-09-30'),(20,'2015-12-18',1000.00,'Greece','Attica','Real time data analysis','Real time data analysis module for existing survey application Add to Watch List','We require a developer to write the real time data analysis module for our survey application. We are looking for real time analysis of web-based data entry with less than 2 seconds lag time. Specifically, the developer will be required to demonstrate using sharp visualization (d3js charts for example) data analysis as the data is being entered, and program certain statistical tests (t-test, chi square, correlations) so that data can be statistically compared in real time. We already have front end design for the visualization in bootstrap html, ready to be implemented.\n\nThe programmer will then successfully integrate this module into existing software application. He must develop it in PHP using MVC and Code Igniter framework. We are working with MySQL database.\n\nThe programmer must submit source code and documentation at the end of the work.',10,1,'2015-09-30'),(22,'2015-12-11',600.00,NULL,NULL,'Responsive Mobile Website','Create a responsive Mobile Website based on an existing psd design and RESTful API','A web app for travel. Contains Google Maps integration',10,0,'2015-09-30'),(23,'2015-12-13',1000.00,NULL,NULL,'Write a Book','Write a Book','This project is for romance short stories. If this story is successful, I will be hiring you for more stories, so this can be an excellent opportunity for a right person.\n\nI want this finished in 4 days. I\'ll pay 60$ for this one. If you deliver before deadline, I\'ll pay 65$.\n\nAbout Your Assignment:\n\n- Write romantic story with erotica elements, around 18, 000 words\n\n- A rich, detailed outline will be provided per story including theme, character names, plot points, etc. You will be given a freedom to create the story, but you have to be able to follow the outline provided to create your story.\n\nThe successful applicant will have:\n- Good command of the English language and appropriate use of English-American dialogue. Witty banter and knowledge of contemporary slang is a plus.\n- Ability to provide text that is proofread and free of grammar errors with proper formatting and editing. I will not tolerate the bad sentence construction or spelling errors.\n- 100% original content. Plagiarism will not be accepted. Any non-original work will be returned without compensation and you will no longer be considered for future projects.\n- Experience in Contemporary Romance / Romantic Erotica preferred but not required.\n\nYour proposal should include: - A sample of your fiction writing preferably a sample in Romance or Romantic Erotica\n\nPlease note: This position is a ghostwriting position. This means that once I purchase the stories you will not retain any rights to the work. I will have all rights to publish, distribute, sell and generally use the stories in perpetuity in any media including digital, electronic and print. Further, these works will be published under a pen name, copyrighted by my company and cannot be distributed or used by you in the future.\n\nHappy bidding. And in your bid tell me who is the current president of USA, so I know you have read the details.',48,0,'2016-10-07'),(24,'2015-09-19',600.00,'Greece','','article writing','Professioner writer Available for all your article writing','I will deliver your order on a timely manner i can deliver the 30 articles less than two weeks and also i am looking to create a long term work relationship with you.\n\n\nI am The Best for your occupation?\n\nHighlights\n\n500 words or more!\n\nUnique substance\n\nHigh caliber!\n\nVery much Researched!\n\nCopyScape Checked!\n\n\nBest regards.',48,1,'2015-09-19'),(25,'2015-11-13',490.00,'Greece','Attica','Google Maps','Google Maps - Update Javascript','I have a website built here:\nhttp://thecaptain.com.au/Heli-Pads/index.html\n\nI need someone to \"EXPLAIN\" how to update the JS file such that I can put a pin in the middle of each circle.\n\nI\'d also like to understand how I can update to show the city name once you click on it...\n\nIt should be a 20 second job for someone familiar with Maps API.\n\nPlease don\'t ask where the JS file is, if you can\'t work you wont\' be able to do the work.',49,0,'2015-10-02'),(26,'2016-11-11',2000.00,NULL,NULL,'Android and HTML5 frontend & backend','Write an Android and HTML5 frontend & backend application (desktop)','Create a system that is to be used as the main tool for our ambulance service. This 2-part application will be installed on tablets (with 4G, GPS) provided to our ambulance personel (defined as AmbuTrack) and a Dispatcher app (defined as AmbuDispatch) which should be available on desktop windows 10.\n\nIt consists of an Planning tool which is basically a calender.\nThe planning tool allow the dispatcher to create \"transports\" of patients. Taking input of pickup adres , drop off adres, is patient sitting, laying down, wheelchair, time of arrival, transport type (more input fields to be defined). This is to plan incoming requests for transports via Phone, fax, e-mail. The planning tool should be available to users with \"dispatcher\" rights.\n\nThe dispatcher app, that can assign each transport to a team, pushing the message on screen to accept and a counter counting the time it takes to accept. Once accepted the map should come on screen and guide the driver to the destination. All tracking data (speed, location, drivername, team, car details, total driven KM, total wait-time) will be stored).\n\nOnce on site the app should go into counter mode again and based on the transport type the counter should start (some types of patient transport take longer time to \"register\" the patients into the hospitals, some have return trip with predefined wait time. These types of transports (xray, chemo, consult, discharge, urgent)are to be defined ).\n\nAt patient pickup the app must allow the user to take pictures of documents and make those documents available as PDF in the backend dispatch application for each transport.\nThere must be a way to take a picture of the Healthcare billing sticker provided by the patient or Healthcare institution where the patient is picked up. This should be sent to the dispatch app, where OCR should be applied to read the billing details of the patient.\nTaking the total KM, from, to destinations, wait-time and the billing details creating a bill based on the transport type. Formulas are to be provided.\nIt must also show our standard patient transport agreement form and allow the patient to sign on the tablet and save that also in pdf attached to the transport.\n\nThe dispatch app should allow us to create an adres book of all the hospitals, retirement homes and other Healthcare partners so the transportations can be created quick by selecting from our adresbook.\n\nThe app should be multiuser allowing login for each employee\nThe app should have Multi-language support (allowing us to put the app into Dutch & French & German) the 3 spoken languages in Belgium.\n\nThe hardware for production use (TABLET with perhaps an optional external GPS mouse) is to be discussed as we go along with development.\n\nWe need a professional partner who is used to work under pressure, deliver results and is able to speak proper english to communicate over Google Hangout',10,0,'2015-10-04'),(27,'2015-11-28',900.00,'Greece','Attica','Eating habits','analyze a sample of the food habits of europeans during last year. Ude of statistical packages like SPSS',NULL,10,1,'2015-09-30'),(28,'2015-12-12',1400.00,'Greece','Kentrikos Tomeas Athinon','statistician for algorithm','Looking for a statistician to help build an algorithm','A research consultation company is in urgent need of statisticians with expertise in advanced data analysis techniques like PCA, CFA, SEM, MLR, MDA, etc with hands on experience in SPSS. Experts in AMOS can also bid. Serious bidders only. Bidders please attach a sample. BIDS WITHOUT SAMPLE WILL NOT BE CONSIDERED.',44,0,'2015-09-29');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_qualification`
--

DROP TABLE IF EXISTS `project_qualification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_qualification` (
  `proj_id` bigint(20) NOT NULL,
  `qual_id` bigint(20) NOT NULL,
  PRIMARY KEY (`proj_id`,`qual_id`),
  KEY `qualification_of_the_project_idx` (`qual_id`),
  CONSTRAINT `project_qualification_project_proj_id` FOREIGN KEY (`proj_id`) REFERENCES `project` (`proj_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `project_qualification_qualification_qual_id` FOREIGN KEY (`qual_id`) REFERENCES `qualification` (`qual_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_qualification`
--

LOCK TABLES `project_qualification` WRITE;
/*!40000 ALTER TABLE `project_qualification` DISABLE KEYS */;
INSERT INTO `project_qualification` VALUES (22,1),(20,2),(26,2),(19,5),(25,5),(26,5),(20,6),(26,6),(24,7),(19,8),(22,8),(26,8),(19,9),(20,9),(25,9),(26,9),(27,10),(28,10);
/*!40000 ALTER TABLE `project_qualification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qualification`
--

DROP TABLE IF EXISTS `qualification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qualification` (
  `qual_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `description` varchar(200) NOT NULL,
  PRIMARY KEY (`qual_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qualification`
--

LOCK TABLES `qualification` WRITE;
/*!40000 ALTER TABLE `qualification` DISABLE KEYS */;
INSERT INTO `qualification` VALUES (1,'web developer','design html pages'),(2,'programmer','fffffffffffffffff'),(3,'drama actor','actor drama '),(4,'tv actor','daily series'),(5,'frontend developer','javascript, jquery,css,angularJS'),(6,'backend developer','javascript, ,java, hibernate,spring'),(7,'article writter','sports,news,politics,newspapers'),(8,'mobile phone developer','android, ios, cordova,ionic'),(9,'javascript developer','excellent knowledge of javascript,jquery,nodejs,backbone,angularjs'),(10,'statistician','SPSS,minitab,statgraphics');
/*!40000 ALTER TABLE `qualification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(100) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'EMPLOYEE_ROLE'),(2,'EMPLOYER_ROLE');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `firstname` varchar(100) NOT NULL,
  `lastname` varchar(100) NOT NULL,
  `email` varchar(80) NOT NULL,
  `role_id` int(11) NOT NULL,
  `rating` decimal(2,1) DEFAULT '5.0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `user_role_role_id_idx` (`role_id`),
  CONSTRAINT `user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (5,'annoula','$2a$10$pxNKOcZd26j5LGCVLV8hK.oYe0hsl7pgSiS3aXr/nZdjucWblHoRG','anna','xerouki','ann@gmail.com',1,9.0),(10,'george','$2a$10$STCaHdZjhsoqoN6mSA2TPeOgFVBiXVJVcB/a287WQU5KypSZ48FLC','george','gg','ge@hotmail.com',2,9.0),(42,'efi','$2a$10$2lm.CCz/72qdzY8tuyAEbOOr3j/i1kwLFw4Pl77KiDPQDFpQ00btC','efi','karra','efi.karrat@gmail.com',1,NULL),(44,'kostas','$2a$10$gCjinY.2e9TPjM8WU4rwaebI3tLf7O6kpG7IR6MNjzxi0xujxiEmm','kostas','mpintis','kost@gmail.com',2,NULL),(48,'eleni','$2a$10$crb2QgXZAz47AtFI6OtFd./.HuZsNYpYkmFcYyNl5X6wY7uUqDdH6','eleni','fesi','elen@gmail.com',2,NULL),(49,'stelios','$2a$10$ugTPScd45pk6NnU2q657s.dyL0oBD18oFds/.VwZH8SA2EhStXDOO','stilianos','bratsos','stelaras@hotmail.com',2,NULL),(50,'vaggelis','$2a$10$EwsKf/N.EGio.A24cuQJBe6iC/OIA7dJrZgDv8EiU8VkdLSVfb7bW','evaggelos','sxinias','vagsx@di.uoa.gr',1,NULL),(52,'stathis','$2a$10$6CGmLSKcfZtVdFnBlcISUe8XafQ/Bxey6I6X5steJ3xnkWLSX0H0C','eustathios','kastos','eustathios@hotmail.com',1,6.0),(53,'mpampis','$2a$10$MQK6.2FTvpqhT.AtpECvg.MxbisFLXmrKdGZS/yJvqJ4gPbCgsns.','xaralampos','koukis','babisk@gmail.com',1,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `works_on`
--

DROP TABLE IF EXISTS `works_on`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `works_on` (
  `exp_id` bigint(20) NOT NULL,
  `proj_id` bigint(20) NOT NULL,
  PRIMARY KEY (`exp_id`,`proj_id`),
  KEY `project_idx` (`proj_id`),
  CONSTRAINT `works_on_expert_exp_id` FOREIGN KEY (`exp_id`) REFERENCES `expert` (`exp_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `works_on_project_proj_id` FOREIGN KEY (`proj_id`) REFERENCES `project` (`proj_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `works_on`
--

LOCK TABLES `works_on` WRITE;
/*!40000 ALTER TABLE `works_on` DISABLE KEYS */;
INSERT INTO `works_on` VALUES (5,20),(52,24),(5,27);
/*!40000 ALTER TABLE `works_on` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-09-20 12:05:58
