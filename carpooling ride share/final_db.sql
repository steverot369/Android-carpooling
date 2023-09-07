/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - carpoolingsystem
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`carpoolingsystem` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `carpoolingsystem`;

/*Table structure for table `emergency` */

DROP TABLE IF EXISTS `emergency`;

CREATE TABLE `emergency` (
  `emergency_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`emergency_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `emergency` */

insert  into `emergency`(`emergency_id`,`user_id`,`latitude`,`longitude`) values 
(1,1,'9.9763302','76.2862443'),
(2,1,'9.97629087','76.28623298'),
(3,1,'9.97629156','76.28623358'),
(4,1,'9.9763302','76.2862089'),
(5,2,'9.9763127','76.2862042');

/*Table structure for table `fair` */

DROP TABLE IF EXISTS `fair`;

CREATE TABLE `fair` (
  `fair_id` int(11) NOT NULL AUTO_INCREMENT,
  `km` varchar(50) DEFAULT NULL,
  `fair` varchar(50) DEFAULT NULL,
  `vehicle_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fair_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `fair` */

/*Table structure for table `o_vehicle` */

DROP TABLE IF EXISTS `o_vehicle`;

CREATE TABLE `o_vehicle` (
  `vehicle_id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) DEFAULT NULL,
  `vehicle` varchar(50) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  `details` varchar(50) DEFAULT NULL,
  `reg_no` varchar(50) DEFAULT NULL,
  `rate` varchar(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `seat_no` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `o_vehicle` */

insert  into `o_vehicle`(`vehicle_id`,`owner_id`,`vehicle`,`image`,`details`,`reg_no`,`rate`,`status`,`seat_no`) values 
(1,1,'traveler','static/1c48c95f-6924-4aa9-abcb-38a6aed3bbe2baby-groot-listening-to-music_2560x1440_xtrafondos.com.jpg','fortuner desel','kl32r1653','2000','active','9'),
(2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(3,1,'bolero','static/vehicle/dcdffeb0-39a2-4dd6-9098-814386e49c3bbaby-groot-listening-to-music_2560x1440_xtrafondos.com.jpg','good','6231233','2000','active','5'),
(4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7');

/*Table structure for table `owner` */

DROP TABLE IF EXISTS `owner`;

CREATE TABLE `owner` (
  `owner_id` int(11) NOT NULL AUTO_INCREMENT,
  `loginid` int(11) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `lname` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`owner_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `owner` */

insert  into `owner`(`owner_id`,`loginid`,`fname`,`lname`,`place`,`phone`,`email`) values 
(1,4,'sam','san','kollam','0988776554','samsanop@gmail.com'),
(3,6,'steve','rodrigues','kochi','6235233153','steverodrigues369@gmail.com'),
(4,10,'johon','doe','kollam ','7896544123','jhonedoe@gmail.com');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `apr_id` int(11) DEFAULT NULL,
  `amount` varchar(500) DEFAULT NULL,
  `date_time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`payment_id`,`apr_id`,`amount`,`date_time`) values 
(7,13,'159.0','2023-01-05 10:04:47'),
(8,17,'421.0','2023-01-21 16:18:36'),
(5,12,'282.0','2023-01-05 09:49:03'),
(9,17,'421.0','2023-01-21 16:19:59'),
(10,17,'421.0','2023-01-21 16:47:25'),
(11,17,'421.0','2023-01-21 16:48:01'),
(12,17,'421.0','2023-01-21 16:49:03'),
(13,22,'1039.2','2023-01-28 14:58:08'),
(14,22,'1039.2','2023-01-28 14:58:10'),
(15,22,'1039.2','2023-01-28 14:58:50'),
(16,22,'1039.2','2023-01-28 14:59:48'),
(17,23,'394.4','2023-01-28 15:01:54'),
(18,23,'315.52','2023-01-28 15:04:05');

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `rateid` int(10) NOT NULL AUTO_INCREMENT,
  `req_id` int(10) DEFAULT NULL,
  `rating` varchar(10) DEFAULT NULL,
  `review` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rateid`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `rating` */

insert  into `rating`(`rateid`,`req_id`,`rating`,`review`,`date_time`) values 
(1,22,'5.0',NULL,NULL),
(2,22,'5.0',NULL,NULL);

/*Table structure for table `rentpayment` */

DROP TABLE IF EXISTS `rentpayment`;

CREATE TABLE `rentpayment` (
  `r_payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `v_request_id` int(11) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`r_payment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

/*Data for the table `rentpayment` */

insert  into `rentpayment`(`r_payment_id`,`v_request_id`,`amount`,`date`) values 
(1,2,'2000.0','2023-01-05 06:40:05'),
(2,6,'2000.0','2023-01-05 20:08:04'),
(3,8,'2000.0','2023-01-21 15:07:35'),
(4,8,'2000.0','2023-01-21 15:07:37'),
(5,8,'2000.0','2023-01-21 15:07:39'),
(6,8,'2000.0','2023-01-21 15:07:40'),
(7,8,'2000.0','2023-01-21 15:07:40'),
(8,8,'2000.0','2023-01-21 15:07:43'),
(9,8,'2000.0','2023-01-21 15:07:43'),
(10,8,'2000.0','2023-01-21 15:07:43'),
(11,8,'2000.0','2023-01-21 15:10:32'),
(12,8,'2000.0','2023-01-21 15:11:46'),
(13,9,'2000.0','2023-01-21 15:16:47'),
(14,10,'2000.0','2023-01-28 13:41:50'),
(15,10,'2000.0','2023-01-28 14:14:31'),
(16,10,'2000.0','2023-01-28 14:15:48');

/*Table structure for table `req_aprvl` */

DROP TABLE IF EXISTS `req_aprvl`;

CREATE TABLE `req_aprvl` (
  `apr_id` int(10) NOT NULL AUTO_INCREMENT,
  `req_id` int(10) DEFAULT NULL,
  `renter_id` int(10) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`apr_id`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

/*Data for the table `req_aprvl` */

insert  into `req_aprvl`(`apr_id`,`req_id`,`renter_id`,`amount`,`status`) values 
(14,20,1,'1299.0','drop'),
(13,17,1,'159.0','drop'),
(12,16,1,'282.0','drop'),
(11,15,1,'742.0','drop'),
(17,25,1,'421.0','approved'),
(15,21,1,'4504.0','drop'),
(16,22,1,'1856.0','drop'),
(18,26,1,'1299.0','drop'),
(19,27,1,'493.0','drop'),
(20,28,1,'6435.0','drop'),
(21,29,1,'3240.0','drop'),
(22,30,1,'1299.0','approved'),
(23,31,1,'493.0','approved');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `req_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `flatitude` varchar(50) DEFAULT NULL,
  `flongitude` varchar(50) DEFAULT NULL,
  `tlatitude` varchar(50) DEFAULT NULL,
  `tlongitude` varchar(50) DEFAULT NULL,
  `noofpassenger` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`req_id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`req_id`,`user_id`,`flatitude`,`flongitude`,`tlatitude`,`tlongitude`,`noofpassenger`,`date`,`time`,`status`) values 
(20,1,'9.9762841','76.28621588','9.674136299999999','76.3400963','2','2023-01-05','10:30:28','droped'),
(21,1,'9.97632626','76.28622315','8.8932118','76.6141396','2','2023-01-21','13:46:59','droped'),
(17,1,'9.9763109','76.2862425','9.9657787','76.2421147','4','2023-01-05','09:56:54','droped'),
(16,1,'9.9763037','76.2862315','9.878191','76.3038672','3','2023-01-05','09:48:14','droped'),
(15,1,'9.9763049','76.2862334','9.674136299999999','76.3400963','2','2023-01-05','08:42:25','droped'),
(22,1,'9.97632626','76.28622315','9.674136299999999','76.3400963','2','2023-01-21','13:48:30','droped'),
(23,NULL,'9.9762935','76.2862386','9.9657787','76.2421147','2','2023-01-21','15:43:07','pending'),
(24,NULL,'9.9762935','76.2862386','8.8932118','76.6141396','5','2023-01-21','15:44:15','pending'),
(25,3,'9.9762961','76.2862384','9.9312328','76.26730409999999','3','2023-01-21','15:46:45','approved'),
(26,1,'9.9763115','76.2862044','9.674136299999999','76.3400963','5','2023-01-28','14:22:27','droped'),
(27,2,'9.9763073','76.2862018','9.878191','76.3038672','1','2023-01-28','14:24:22','droped'),
(28,2,'9.9763157','76.2862007','8.8932118','76.6141396','2','2023-01-28','14:28:40','droped'),
(29,1,'9.9763128','76.2862023','10.527641599999999','76.2144349','1','2023-01-28','14:29:44','droped'),
(30,1,'9.9763105','76.2862041','9.674136299999999','76.3400963','2','2023-01-28','14:33:51','shared'),
(31,2,'9.9763138','76.2862025','9.878191','76.3038672','1','2023-01-28','14:34:57','accepted');

/*Table structure for table `shared_requests` */

DROP TABLE IF EXISTS `shared_requests`;

CREATE TABLE `shared_requests` (
  `sreq_id` int(11) NOT NULL AUTO_INCREMENT,
  `req_id` int(11) DEFAULT NULL,
  `shared_user_id` int(11) DEFAULT NULL,
  `share_status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`sreq_id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `shared_requests` */

insert  into `shared_requests`(`sreq_id`,`req_id`,`shared_user_id`,`share_status`) values 
(9,26,2,'finnished'),
(7,22,1,'finnished'),
(8,26,1,'finnished'),
(6,22,1,'finnished'),
(10,28,2,'finnished'),
(11,28,1,'finnished'),
(12,30,1,'active'),
(13,30,2,'active');

/*Table structure for table `tbl_advancepay` */

DROP TABLE IF EXISTS `tbl_advancepay`;

CREATE TABLE `tbl_advancepay` (
  `advpay_id` int(10) NOT NULL AUTO_INCREMENT,
  `apr_id` int(10) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `balance` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`advpay_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_advancepay` */

insert  into `tbl_advancepay`(`advpay_id`,`apr_id`,`amount`,`balance`,`date`,`time`) values 
(4,15,'74.2','667.8','2023-01-05','09:11:39'),
(3,15,'74.2','667.8','2023-01-05','09:11:17'),
(5,15,'74.2','667.8','2023-01-05','09:12:18'),
(6,22,'185.6','1670.4','2023-01-21','14:03:14');

/*Table structure for table `tbl_booking` */

DROP TABLE IF EXISTS `tbl_booking`;

CREATE TABLE `tbl_booking` (
  `book_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `vehicle_id` int(10) DEFAULT NULL,
  `booking_from` varchar(50) DEFAULT NULL,
  `booking_to` varchar(50) DEFAULT NULL,
  `booking_date` varchar(50) DEFAULT NULL,
  `booking_time` varchar(50) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `booking_status` varchar(50) DEFAULT NULL,
  `req_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_booking` */

insert  into `tbl_booking`(`book_id`,`user_id`,`vehicle_id`,`booking_from`,`booking_to`,`booking_date`,`booking_time`,`amount`,`booking_status`,`req_id`) values 
(22,1,3,'Manjali Building','cherthala','2023-01-21','13:48:30','1670.4','pending',22),
(20,1,2,'Manjali Building','cherthala ','2023-01-05','10:30:28','1299.0','pending',20),
(21,1,2,'Manjali Building','kollam','2023-01-21','13:46:59','4504.0','pending',21),
(16,1,1,'Manjali Building','aroor','2023-01-05','09:48:14','282.0','pending',16),
(17,1,1,'Manjali Building','fort kochi','2023-01-05','09:56:54','159.0','pending',17),
(15,1,1,'Manjali Building','cherthala ','2023-01-05','08:42:25','667.8','pending',15),
(23,NULL,0,'Manjali Building','fort kochi','2023-01-21','15:43:07','0','pending',23),
(24,NULL,0,'Manjali Building','kollam','2023-01-21','15:44:15','0','pending',24),
(25,3,3,'Manjali Building','kochi','2023-01-21','15:46:45','421.0','pending',25),
(26,1,2,'Manjali Building','cherthala','2023-01-28','14:22:27','1299.0','pending',26),
(27,2,2,'Manjali Building','aroor','2023-01-28','14:24:22','493.0','pending',27),
(28,2,3,'Manjali Building','kollam','2023-01-28','14:28:40','6435.0','pending',28),
(29,1,3,'Manjali Building','thrissur','2023-01-28','14:29:44','3240.0','pending',29),
(30,1,2,'Manjali Building','cherthala','2023-01-28','14:33:51','1039.2','pending',30),
(31,2,2,'Manjali Building','aroor','2023-01-28','14:34:57','315.52','pending',31);

/*Table structure for table `tbl_complaint` */

DROP TABLE IF EXISTS `tbl_complaint`;

CREATE TABLE `tbl_complaint` (
  `complaint_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `complaint` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_complaint` */

insert  into `tbl_complaint`(`complaint_id`,`user_id`,`complaint`,`reply`,`date`) values 
(1,1,'too late','ok','12-21-2019'),
(2,1,'fvvvg','2022-12-29','pending'),
(3,1,'hi','2023-01-21','pending'),
(4,1,'hi','afdfasdfsadf','2023-01-21');

/*Table structure for table `tbl_feedback` */

DROP TABLE IF EXISTS `tbl_feedback`;

CREATE TABLE `tbl_feedback` (
  `feedback_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `feedback` varchar(100) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_feedback` */

insert  into `tbl_feedback`(`feedback_id`,`user_id`,`feedback`,`date_time`) values 
(1,1,'good','12-21-2019'),
(2,1,'hiisgbtabta','2022-12-29 09:52:20'),
(3,1,'hh','2023-01-21 14:11:08');

/*Table structure for table `tbl_location` */

DROP TABLE IF EXISTS `tbl_location`;

CREATE TABLE `tbl_location` (
  `loc_id` int(10) NOT NULL AUTO_INCREMENT,
  `renter_id` int(10) DEFAULT NULL,
  `lattitude` varchar(50) DEFAULT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  `dates` varchar(50) DEFAULT NULL,
  `times` varchar(50) DEFAULT NULL,
  `availability` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`loc_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_location` */

insert  into `tbl_location`(`loc_id`,`renter_id`,`lattitude`,`longitude`,`dates`,`times`,`availability`) values 
(1,1,'9.9763299','76.2862097','2023-01-28','14:23:32','ok');

/*Table structure for table `tbl_login` */

DROP TABLE IF EXISTS `tbl_login`;

CREATE TABLE `tbl_login` (
  `loginid` int(10) NOT NULL AUTO_INCREMENT,
  `uname` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `login_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`loginid`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_login` */

insert  into `tbl_login`(`loginid`,`uname`,`password`,`login_type`) values 
(1,'admin','admin','admin'),
(2,'ar','ar','renter'),
(3,'ll','ll','user'),
(4,'sasa','sasa','owner'),
(5,'jobin','jobi','user'),
(10,'jhone','jhome','owner'),
(9,'steve','1234','rider'),
(11,'amal','amal','driver'),
(12,'amal','amal','driver'),
(13,'amal','amal','driver'),
(14,'ananthu','ananthu','rider');

/*Table structure for table `tbl_renter` */

DROP TABLE IF EXISTS `tbl_renter`;

CREATE TABLE `tbl_renter` (
  `renter_id` int(10) NOT NULL AUTO_INCREMENT,
  `loginid` int(10) DEFAULT NULL,
  `driver_fname` varchar(50) DEFAULT NULL,
  `driver_lname` varchar(50) DEFAULT NULL,
  `driver_hname` varchar(50) DEFAULT NULL,
  `driver_city` varchar(50) DEFAULT NULL,
  `driver_pin` varchar(50) DEFAULT NULL,
  `driver_gender` varchar(50) DEFAULT NULL,
  `driver_email` varchar(50) DEFAULT NULL,
  `driver_dob` varchar(50) DEFAULT NULL,
  `driver_phn` varchar(50) DEFAULT NULL,
  `driver_lisence` varchar(50) DEFAULT NULL,
  `driver_photo` varchar(150) DEFAULT NULL,
  `driver_exp` varchar(50) DEFAULT NULL,
  `driver_doj` varchar(50) DEFAULT NULL,
  `r_status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`renter_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_renter` */

insert  into `tbl_renter`(`renter_id`,`loginid`,`driver_fname`,`driver_lname`,`driver_hname`,`driver_city`,`driver_pin`,`driver_gender`,`driver_email`,`driver_dob`,`driver_phn`,`driver_lisence`,`driver_photo`,`driver_exp`,`driver_doj`,`r_status`) values 
(1,2,'Arjun','S.K','Rosevilla','Kochi','687676','Male','arjun@gmail.com','12-04-1985','9054564636','FCC76567',NULL,'3','23-04-2010','Active'),
(2,11,'nsnnsndj','bjxjxjj','jjjjj','jjjjj','666666','male','nnnjh@gmail.com','28-1-2024','9142449216','kl828236$828','static/uploads/a2821ad1-cb1e-4422-8970-928ea1162414abc.jpg','1 year','28-1-2023','active'),
(3,12,'nsnnsndj','bjxjxjj','jjjjj','jjjjj','666666','male','nnnjh@gmail.com','28-1-2024','9142449216','kl828236$828','static/uploads/d1db42f7-7849-4dea-a09b-93a7f93c20e2abc.jpg','1 year','28-1-2023','active'),
(4,13,'nsnnsndj','bjxjxjj','jjjjj','jjjjj','666666','male','nnnjh@gmail.com','28-1-2024','9142449216','kl828236$828','static/uploads/622cf39d-d1a6-4234-bfa7-ddf781e92001abc.jpg','1 year','28-1-2023','active');

/*Table structure for table `tbl_vehicle` */

DROP TABLE IF EXISTS `tbl_vehicle`;

CREATE TABLE `tbl_vehicle` (
  `vehicle_id` int(10) NOT NULL AUTO_INCREMENT,
  `renter_id` int(10) DEFAULT NULL,
  `vehicle_type` varchar(50) DEFAULT NULL,
  `no_of_seats` varchar(50) DEFAULT NULL,
  `vehicle_no` varchar(50) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `availability` varchar(50) DEFAULT NULL,
  `tbl_vehicle_status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_vehicle` */

insert  into `tbl_vehicle`(`vehicle_id`,`renter_id`,`vehicle_type`,`no_of_seats`,`vehicle_no`,`amount`,`availability`,`tbl_vehicle_status`) values 
(1,1,'Taxi','5','KL 17 3323','20','7',NULL),
(2,1,'Inova taxi','9','kl32m1567','35','0','available'),
(3,1,'Rent','4','kl32r1653','50','7','available'),
(4,NULL,'Rent','4','6231233','50','available','available'),
(5,NULL,'Rent','4','6231233','50','available','available'),
(6,NULL,'Rent','4','6231233','50','available','available'),
(7,NULL,'Rent','4','6231233','50','available','available'),
(8,NULL,'Rent','4','6231233','50','available','available'),
(9,NULL,'Rent','4','6231233','50','available','available'),
(10,NULL,'Rent','4','6231233','50','available','available'),
(11,NULL,'Rent','4','6231233','50','available','available'),
(12,NULL,'Rent','4','6231233','50','available','available'),
(13,NULL,'Rent','4','6231233','50','available','available'),
(14,1,'Rent','4','6231233','50','7','not available'),
(15,1,'bolero',NULL,'6231233','50','7','not available');

/*Table structure for table `transaction` */

DROP TABLE IF EXISTS `transaction`;

CREATE TABLE `transaction` (
  `transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `uer_id` int(11) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `details` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `transaction` */

/*Table structure for table `travel_history` */

DROP TABLE IF EXISTS `travel_history`;

CREATE TABLE `travel_history` (
  `travel_id` int(11) NOT NULL AUTO_INCREMENT,
  `apr_id` int(11) DEFAULT NULL,
  `amount_charged` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  `payment_status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`travel_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `travel_history` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `loginid` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`loginid`,`first_name`,`last_name`,`gender`,`phone`,`email`) values 
(1,3,'hari','krishnan','male','7894561230','hari@gmail.com'),
(2,5,'jobin','jobi','male','7894561230','ok@gmail.com'),
(3,9,'Steve','Rodrigues','male','6235233153','steverodrigues369@gmail.com'),
(4,14,'Ananthu','Anil','male','9142449216','pthalika8@gmail.com');

/*Table structure for table `vehicle_request` */

DROP TABLE IF EXISTS `vehicle_request`;

CREATE TABLE `vehicle_request` (
  `v_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `renter_id` int(11) DEFAULT NULL,
  `vehicle_id` int(11) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `requested_date` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`v_request_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `vehicle_request` */

insert  into `vehicle_request`(`v_request_id`,`renter_id`,`vehicle_id`,`date`,`requested_date`,`status`) values 
(1,1,1,'1131313','31/12/2022','Returned'),
(2,1,1,'2022-12-28','28-12-2023','Returned'),
(7,1,1,'2023-01-21','21-1-2023','Accept'),
(6,1,1,'2023-01-05','7-2-2024','Returned'),
(8,1,3,'2023-01-21','21-2-2023','Returned'),
(9,1,3,'2023-01-21','21-1-2023','Returned'),
(10,1,3,'2023-01-28','28-12-2022','Returned');

/*Table structure for table `wallet` */

DROP TABLE IF EXISTS `wallet`;

CREATE TABLE `wallet` (
  `wallet_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`wallet_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `wallet` */

insert  into `wallet`(`wallet_id`,`user_id`,`amount`,`date`,`status`) values 
(1,1,'1355.2','2023-01-04','active'),
(2,3,'12.629999999999999','2023-01-21','active'),
(3,2,'0','2023-01-21','active');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
