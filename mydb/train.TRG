TYPE=TRIGGERS
triggers='CREATE DEFINER=`root`@`localhost` TRIGGER `mydb`.`Train_BEFORE_INSERT` BEFORE INSERT ON `Train` FOR EACH ROW\nBEGIN\n   IF New.carCount > 30 || New.carCount < 3 THEN\n    SIGNAL SQLSTATE \'10000\'\n        SET MESSAGE_TEXT = \'Invalid car amount\';\n    END IF;\nEND'
sql_modes=1344274432
definers='root@localhost'
client_cs_names='cp866'
connection_cl_names='cp866_general_ci'
db_cl_names='latin1_swedish_ci'
