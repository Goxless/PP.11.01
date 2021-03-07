-- MySQL dump 10.13  Distrib 5.6.46, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	5.6.46-log

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
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car` (
  `CarID` int(11) NOT NULL AUTO_INCREMENT,
  `carType` varchar(45) NOT NULL,
  `carCapacity` varchar(45) NOT NULL,
  `Train_ID` int(11) NOT NULL,
  `fullness` int(15) DEFAULT NULL,
  PRIMARY KEY (`CarID`,`Train_ID`),
  KEY `Train_ID` (`Train_ID`),
  CONSTRAINT `car_ibfk_1` FOREIGN KEY (`Train_ID`) REFERENCES `train` (`TrainID`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES (3,'Pass','31',1,6),(4,'Pass','15',2,0),(5,'Pass','15',2,0),(6,'Pass','15',2,0),(7,'Pass','15',2,0),(8,'Pass','30',1,0),(9,'Pass','30',1,0),(10,'Pass','30',1,0),(11,'Pass','30',1,0),(12,'Pass','30',1,0),(13,'Pass','30',1,0),(14,'Pass','30',1,0),(46,'pass','30',13,0),(47,'pass','30',13,0),(48,'pass','30',13,0);
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = cp866 */ ;
/*!50003 SET character_set_results = cp866 */ ;
/*!50003 SET collation_connection  = cp866_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `mydb`.`Car_BEFORE_INSERT` BEFORE INSERT ON `Car` FOR EACH ROW
BEGIN

SELECT COUNT(*) INTO @Car_count FROM Car c WHERE c.Train_ID = new.Train_ID;
SELECT carCount INTO @Car_in_train_count FROM Train t WHERE t.TrainID = new.Train_ID;

  IF @Car_count >= @Car_in_train_count THEN
    SIGNAL SQLSTATE '10000'
        SET MESSAGE_TEXT = 'Invalid car amount';
        
  END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `Person_ID` int(11) NOT NULL,
  `Person_Name` varchar(45) DEFAULT NULL,
  `Person_Surname` varchar(45) DEFAULT NULL,
  `Person_Middlename` varchar(45) DEFAULT NULL,
  `Person_Age` varchar(45) DEFAULT NULL,
  `Ticket_Number` int(11) NOT NULL,
  PRIMARY KEY (`Ticket_Number`,`Person_ID`),
  CONSTRAINT `person_ibfk_1` FOREIGN KEY (`Ticket_Number`) REFERENCES `ticket` (`Ticket_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'Austin',' Dickinson ','Dorian','25',1),(2,'Austin',' Dickinson ','Dorian','25',2),(3,'James','Luis','Johnson','30',3);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule` (
  `ride_id` int(11) NOT NULL AUTO_INCREMENT,
  `StartPoint` varchar(255) NOT NULL,
  `EndPoint` varchar(255) NOT NULL,
  `departureTime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `arrivalTime` timestamp(6) NOT NULL DEFAULT '0000-00-00 00:00:00.000000',
  `price` int(11) NOT NULL,
  PRIMARY KEY (`ride_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (1,'test1','test1','2021-02-05 08:01:16.655243','2021-05-05 02:05:05.000000',100),(2,'Kazan','Saint-Petersburg','2021-06-06 03:06:06.000000','2021-02-05 08:01:23.000000',200),(4,'2131','3123','2021-02-05 08:01:43.231117','2021-02-05 18:34:29.000000',400);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `Ticket_Number` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `TicketDest` varchar(45) NOT NULL,
  `departureTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `arrivalTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ticketPrice` int(11) NOT NULL,
  `CarID` int(11) NOT NULL,
  `Status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Ticket_Number`,`CarID`,`userID`),
  KEY `indx_test` (`Ticket_Number`,`ticketPrice`,`CarID`),
  KEY `CarID` (`CarID`),
  KEY `userID` (`userID`),
  CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`CarID`) REFERENCES `car` (`CarID`),
  CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`CarID`) REFERENCES `car` (`CarID`),
  CONSTRAINT `ticket_ibfk_3` FOREIGN KEY (`userID`) REFERENCES `user` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,0,'Tver','2020-09-28 19:17:09','2020-09-28 19:17:09',760,5,'Solded'),(2,0,'Ryazan','2020-09-28 19:18:12','2020-09-28 19:18:12',912,7,'Solded'),(3,0,'Ryazan','2020-09-28 19:29:31','2020-09-28 19:29:31',840,7,'Solded'),(5,1,'test1','2021-02-05 08:01:17','2021-05-05 02:05:05',100,4,NULL),(6,1,'Saint-Petersburg','2021-02-05 08:01:23','2021-06-06 03:06:06',200,4,' payment awaiting'),(7,1,'Saint-Petersburg','2021-02-05 08:01:23','2021-06-06 03:06:06',200,4,'Awaiting'),(9,3,'Saint-Petersburg','2021-02-05 08:01:23','2021-06-06 03:06:06',200,4,'Awaiting'),(10,0,'test1','2021-02-05 08:01:17','2021-05-05 02:05:05',100,4,'Awaiting'),(11,1,'test99','2021-02-05 05:01:16','2021-02-05 05:01:16',994,4,'Awaiting'),(12,1,'test99','2021-02-05 05:01:16','2021-02-05 05:01:16',994,4,'Awaiting'),(13,4,'Saint-Petersburg','2021-02-05 08:01:23','2021-06-06 03:06:06',200,4,'Awaiting'),(14,12,'test99','2021-02-05 05:01:16','2021-02-05 05:01:16',994,4,'Awaiting'),(15,12,'3123','2021-02-05 08:01:43','2021-02-05 18:34:29',400,4,'Awaiting'),(16,13,'3123','2021-02-05 08:01:43','2021-02-05 18:34:29',400,4,'Awaiting'),(17,13,'Saint-Petersburg','2021-06-06 03:06:06','2021-02-05 08:01:23',200,4,'Awaiting'),(29,11,'test1','2021-02-05 08:01:17','2021-05-05 02:05:05',100,4,'Awaiting'),(30,11,'test1','2021-02-05 08:01:17','2021-05-05 02:05:05',100,4,'Awaiting'),(31,11,'test1','2021-02-05 08:01:17','2021-05-05 02:05:05',100,4,'Awaiting'),(32,11,'test1','2021-02-05 08:01:17','2021-05-05 02:05:05',100,4,'Awaiting'),(33,11,'test1','2021-02-05 08:01:17','2021-05-05 02:05:05',100,4,'Awaiting'),(34,11,'test1','2021-02-05 08:01:17','2021-05-05 02:05:05',100,4,'Awaiting'),(35,11,'test1','2021-02-05 08:01:17','2021-05-05 02:05:05',100,4,'Awaiting'),(36,15,'test1','2021-02-05 08:01:17','2021-05-05 02:05:05',100,4,'Awaiting');
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `train`
--

DROP TABLE IF EXISTS `train`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `train` (
  `TrainID` int(11) NOT NULL AUTO_INCREMENT,
  `trainNumber` varchar(45) NOT NULL DEFAULT '21',
  `trainName` varchar(45) NOT NULL,
  `trainType` varchar(45) NOT NULL,
  `trainDest` varchar(45) NOT NULL,
  `carCount` int(11) NOT NULL,
  `Schedule_ride_id` int(11) NOT NULL,
  PRIMARY KEY (`TrainID`,`Schedule_ride_id`),
  KEY `Schedule_ride_id` (`Schedule_ride_id`),
  CONSTRAINT `train_ibfk_1` FOREIGN KEY (`Schedule_ride_id`) REFERENCES `schedule` (`ride_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `train`
--

LOCK TABLES `train` WRITE;
/*!40000 ALTER TABLE `train` DISABLE KEYS */;
INSERT INTO `train` VALUES (-1,'43','test2','test2','test',3,2),(1,'21','BlackBird','43SFH','Moscow',16,1),(1,'43','test','test','test',3,2),(2,'21','BlueBird','43SFH','Moscow',4,1),(4,'43','test2','test2','test',3,2),(6,'123','123','123','123',12,1),(7,'123','123','123','123',12,1),(8,'new test12','new test1','new test1','new test1',4,1),(9,'new test','new test','new test','test',25,2),(10,'567','test','test','test',25,1),(11,'1','32','32','32',3,2),(12,'111111','111111','111111','111111',3,1),(13,'22222','22222','22222','22222',3,1);
/*!40000 ALTER TABLE `train` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = cp866 */ ;
/*!50003 SET character_set_results = cp866 */ ;
/*!50003 SET collation_connection  = cp866_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `mydb`.`Train_BEFORE_INSERT` BEFORE INSERT ON `Train` FOR EACH ROW
BEGIN
   IF New.carCount > 30 || New.carCount < 3 THEN
    SIGNAL SQLSTATE '10000'
        SET MESSAGE_TEXT = 'Invalid car amount';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Login` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(20) NOT NULL,
  `Phone` varchar(20) NOT NULL,
  `AccessMode` varchar(45) DEFAULT 'default',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Login` (`Login`),
  UNIQUE KEY `Phone` (`Phone`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (0,'admin','admin','admin','admin','admin','admin'),(1,'test','test','test','test','test',NULL),(3,'Sanya','Sanya','Sanya','Dianov','+83423423423','default'),(4,'Testing','Testing','Testing','Testing','+74321223453','default'),(11,'gdf','gdf','gdfg','dfgdf','+76927445856','default'),(12,'Kirill','Kirill','Kirill','Romanov','79854115234','default'),(13,'KiryaRomanov','KiryaRomanov','Kirya','Romanov','+79624587541','default'),(14,'test1','test1','test1','test1','+79312456578','default'),(15,'Kolya','Kolya','Kolya','Rakhimov','+79312548579','default');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-07 15:35:03
