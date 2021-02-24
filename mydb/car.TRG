TYPE=TRIGGERS
triggers='CREATE DEFINER=`root`@`localhost` TRIGGER `mydb`.`Car_BEFORE_INSERT` BEFORE INSERT ON `Car` FOR EACH ROW\nBEGIN\n\nSELECT COUNT(*) INTO @Car_count FROM Car c WHERE c.Train_ID = new.Train_ID;\nSELECT carCount INTO @Car_in_train_count FROM Train t WHERE t.TrainID = new.Train_ID;\n\n  IF @Car_count >= @Car_in_train_count THEN\n    SIGNAL SQLSTATE \'10000\'\n        SET MESSAGE_TEXT = \'Invalid car amount\';\n        \n  END IF;\nEND'
sql_modes=1344274432
definers='root@localhost'
client_cs_names='cp866'
connection_cl_names='cp866_general_ci'
db_cl_names='latin1_swedish_ci'
