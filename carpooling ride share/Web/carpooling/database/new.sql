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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `emergency` */

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
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `o_vehicle` */

insert  into `o_vehicle`(`vehicle_id`,`owner_id`,`vehicle`,`image`,`details`,`reg_no`,`status`) values 
(1,1,'traveler','static/1c48c95f-6924-4aa9-abcb-38a6aed3bbe2baby-groot-listening-to-music_2560x1440_xtrafondos.com.jpg','fortuner desel','kl32r1653','active');

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
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `owner` */

insert  into `owner`(`owner_id`,`loginid`,`fname`,`lname`,`place`,`phone`,`email`) values 
(1,4,'asdf','asdf','asd','7894561230','pthalika8@gmail.com');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `apr_id` int(11) DEFAULT NULL,
  `amount` varchar(500) DEFAULT NULL,
  `date_time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `rateid` int(10) NOT NULL AUTO_INCREMENT,
  `travel_id` int(10) DEFAULT NULL,
  `rating` varchar(10) DEFAULT NULL,
  `review` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rateid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `rating` */

/*Table structure for table `req_aprvl` */

DROP TABLE IF EXISTS `req_aprvl`;

CREATE TABLE `req_aprvl` (
  `apr_id` int(10) NOT NULL AUTO_INCREMENT,
  `req_id` int(10) DEFAULT NULL,
  `renter_id` int(10) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`apr_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `req_aprvl` */

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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `request` */

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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `tbl_advancepay` */

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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `tbl_booking` */

/*Table structure for table `tbl_complaint` */

DROP TABLE IF EXISTS `tbl_complaint`;

CREATE TABLE `tbl_complaint` (
  `complaint_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `complaint` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_complaint` */

insert  into `tbl_complaint`(`complaint_id`,`user_id`,`complaint`,`reply`,`date`) values 
(1,1,'too late','ok','12-21-2019');

/*Table structure for table `tbl_feedback` */

DROP TABLE IF EXISTS `tbl_feedback`;

CREATE TABLE `tbl_feedback` (
  `feedback_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `feedback` varchar(100) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_feedback` */

insert  into `tbl_feedback`(`feedback_id`,`user_id`,`feedback`,`date_time`) values 
(1,1,'good','12-21-2019');

/*Table structure for table `tbl_location` */

DROP TABLE IF EXISTS `tbl_location`;

CREATE TABLE `tbl_location` (
  `loc_id` int(10) NOT NULL AUTO_INCREMENT,
  `renter_id` int(10) DEFAULT NULL,
  `lattitude` varchar(50) DEFAULT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  `availability` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`loc_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `tbl_location` */

/*Table structure for table `tbl_login` */

DROP TABLE IF EXISTS `tbl_login`;

CREATE TABLE `tbl_login` (
  `loginid` int(10) NOT NULL AUTO_INCREMENT,
  `uname` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `login_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`loginid`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_login` */

insert  into `tbl_login`(`loginid`,`uname`,`password`,`login_type`) values 
(1,'admin','admin','admin'),
(2,'ar','ar','renter'),
(3,'ll','ll','user'),
(4,'sasa','sasa','owner');

/*Table structure for table `tbl_renter` */

DROP TABLE IF EXISTS `tbl_renter`;

CREATE TABLE `tbl_renter` (
  `renter_id` int(10) NOT NULL AUTO_INCREMENT,
  `loginid` int(10) DEFAULT NULL,
  `driver_fname` varchar(50) DEFAULT NULL,
  `driver_lname` varchar(50) DEFAULT NULL,
  `driver_hname` varchar(50) DEFAULT NULL,
  `driver_city` varchar(50) DEFAULT NULL,
  `driver_street` varchar(50) DEFAULT NULL,
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
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_renter` */

insert  into `tbl_renter`(`renter_id`,`loginid`,`driver_fname`,`driver_lname`,`driver_hname`,`driver_city`,`driver_street`,`driver_gender`,`driver_email`,`driver_dob`,`driver_phn`,`driver_lisence`,`driver_photo`,`driver_exp`,`driver_doj`,`r_status`) values 
(1,2,'Arjun','S.K','Rosevilla','Kochi','687676','Male','arjun@gmail.com','12-04-1985','9054564636','FCC76567',NULL,'3','23-04-2010','Active');

/*Table structure for table `tbl_vehicle` */

DROP TABLE IF EXISTS `tbl_vehicle`;

CREATE TABLE `tbl_vehicle` (
  `vehicle_id` int(10) NOT NULL AUTO_INCREMENT,
  `renter_id` int(10) DEFAULT NULL,
  `vehicle_type` varchar(50) DEFAULT NULL,
  `no_of_seats` varchar(50) DEFAULT NULL,
  `vehicle_no` varchar(50) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_vehicle` */

insert  into `tbl_vehicle`(`vehicle_id`,`renter_id`,`vehicle_type`,`no_of_seats`,`vehicle_no`,`amount`) values 
(1,1,'Taxi','5','KL 17 3323','20');

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
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`loginid`,`first_name`,`last_name`,`gender`,`phone`,`email`) values 
(1,3,'ll','ll','ll','ll','ll');

/*Table structure for table `vehicle_request` */

DROP TABLE IF EXISTS `vehicle_request`;

CREATE TABLE `vehicle_request` (
  `v_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `vehicle_id` int(11) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `requested_date` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`v_request_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `vehicle_request` */

insert  into `vehicle_request`(`v_request_id`,`user_id`,`vehicle_id`,`date`,`requested_date`,`status`) values 
(1,1,1,'1131313','31/12/2022','REject');

/*Table structure for table `wallet` */

DROP TABLE IF EXISTS `wallet`;

CREATE TABLE `wallet` (
  `wallet_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `dae` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`wallet_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `wallet` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
