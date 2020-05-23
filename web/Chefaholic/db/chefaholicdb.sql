# MySQL-Front 3.2  (Build 8.0)

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES latin1 */;

# Host: localhost    Database: chefaholicdb
# ------------------------------------------------------
# Server version 5.0.15-nt

DROP DATABASE IF EXISTS `chefaholicdb`;
CREATE DATABASE `chefaholicdb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `chefaholicdb`;

#
# Table structure for table comments_info
#

CREATE TABLE `comments_info` (
  `username` varchar(25) NOT NULL,
  `recipie_id` bigint(11) NOT NULL default '0',
  `comments` blob NOT NULL,
  `date` date NOT NULL,
  KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Dumping data for table comments_info
#

INSERT INTO `comments_info` VALUES ('anoop',3808,'very good','2015-03-27');
INSERT INTO `comments_info` VALUES ('anoop',3808,'podappa','2015-03-27');
INSERT INTO `comments_info` VALUES ('anoop',3808,'adipoli','2015-03-27');
INSERT INTO `comments_info` VALUES ('anoop',3808,'thengakloa','2015-03-27');
INSERT INTO `comments_info` VALUES ('anoop',3808,'then','2015-03-27');
INSERT INTO `comments_info` VALUES ('anoop',3808,'vvhvhjvhvjvhjb','2015-03-27');
INSERT INTO `comments_info` VALUES ('anoop',3808,'vvhvhjvhvjvhjbhjgjhhbnjk','2015-03-27');


#
# Table structure for table favourite_info
#

CREATE TABLE `favourite_info` (
  `username` varchar(25) NOT NULL,
  `recipie_id` bigint(11) NOT NULL default '0',
  KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Dumping data for table favourite_info
#

INSERT INTO `favourite_info` VALUES ('anoop',570);
INSERT INTO `favourite_info` VALUES ('anoop',3808);
INSERT INTO `favourite_info` VALUES ('anoop',3808);


#
# Table structure for table following_info
#

CREATE TABLE `following_info` (
  `username` varchar(25) NOT NULL,
  `follower_username` varchar(25) NOT NULL,
  KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Dumping data for table following_info
#

INSERT INTO `following_info` VALUES ('kiran','anoop');
INSERT INTO `following_info` VALUES ('mary','anoop');
INSERT INTO `following_info` VALUES ('rahul','anoop');


#
# Table structure for table login_info
#

CREATE TABLE `login_info` (
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `type` varchar(25) NOT NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Dumping data for table login_info
#

INSERT INTO `login_info` VALUES ('admin','admin','admin');
INSERT INTO `login_info` VALUES ('anoop','anoop','user');
INSERT INTO `login_info` VALUES ('kiran','kiran','user');
INSERT INTO `login_info` VALUES ('mary','mary','user');
INSERT INTO `login_info` VALUES ('prem','prem','user');
INSERT INTO `login_info` VALUES ('rahul','rahul','user');


#
# Table structure for table recipie_info
#

CREATE TABLE `recipie_info` (
  `recipie_id` bigint(11) default NULL,
  `username` varchar(25) NOT NULL,
  `food_name` blob NOT NULL,
  `ingredients` blob NOT NULL,
  `making` blob NOT NULL,
  `categories` varchar(100) NOT NULL,
  `pic` blob,
  `date` date NOT NULL,
  KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Dumping data for table recipie_info
#

INSERT INTO `recipie_info` VALUES (580,'admin','asd','beef pork ','asdzfgsdfgnskjdfhgkisjdfvbgkjdxbvksgfvuib kfsdgvhsgiofhlksjcvkgzsdfbgdfbgxdfnvlsjr dzghsldhgloxdhflkgxhdg','veg','','2015-03-14');
INSERT INTO `recipie_info` VALUES (560,'admin','asdasdasdasd','BEEF pork chicken','asdasdasdasdasdndslcgldkfvlgdf glskdhlnzksdlzskblgzfbgvkorfgv lzsglhzkfgvlzhglzfnbvglhgolzfgv lszhglzfsolghvgflnvlozoigzvgf lzsgolzhsfvihzolghlovglzglsfjlfbgzlfbhlzd','non-veg','','2015-03-16');
INSERT INTO `recipie_info` VALUES (570,'kiran','Qweqwe','beef chicken','Qweqwedzbfvgdfgmjskedrgtsfdghndfkjlhszfdljlfngldhsalgknsdbflkshglknshgnxlgnbolshgpinb hdzghlshdghoislkhblkxvhboishglnfgkblnsfnlksdfgpijsdfgljklj','veg','','2015-03-19');
INSERT INTO `recipie_info` VALUES (590,'rahul','chicken fry','beef ','sdfsdfsdfsdfsdfsdf','non-veg',NULL,'2015-03-19');
INSERT INTO `recipie_info` VALUES (3808,'kiran','fries','chicken beef pork','asdasdfnhsodf loidsfhlsdnflsdf ldsahflsdfh','non-veg','F:\\Akshay\\Chefaholic\\build\\web\\anoop\\fries.png','2015-03-21');
INSERT INTO `recipie_info` VALUES (540,'admin','chillie gopi','potato','asfsdfgsd dfggfxsgfdfg sfdgdfgsfgsf ','veg',NULL,'2015-03-22');
INSERT INTO `recipie_info` VALUES (4951,'anoop','payasam','adffh','add','non-veg','F:\\Akshay\\Chefaholic\\build\\web\\anoop\\payasam.png','2015-03-28');
INSERT INTO `recipie_info` VALUES (3378,'anoop','paneer butter masala','paneer, butter, masala','mix the whole thing...','non-veg','F:\\Akshay\\Chefaholic\\build\\web\\anoop\\paneer butter masala.png','2015-03-31');


#
# Table structure for table store_info
#

CREATE TABLE `store_info` (
  `store_name` blob NOT NULL,
  `latitude` varchar(100) NOT NULL,
  `longitude` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Dumping data for table store_info
#

INSERT INTO `store_info` VALUES ('Temple Road','9.968261175787168','76.30016081035137');


#
# Table structure for table user_info
#

CREATE TABLE `user_info` (
  `username` varchar(25) NOT NULL,
  `name` varchar(25) NOT NULL,
  `mobile` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `address` blob NOT NULL,
  `pic` blob,
  `follower_count` int(11) default NULL,
  KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Dumping data for table user_info
#

INSERT INTO `user_info` VALUES ('rahul','rahul','9633220362','okvaishakh21@gmail.com','hjgfsgfsdhgfsdhgfsdhg','F:\\Akshay\\Chefaholic\\build\\web\\profile_pic\\rahul.png',4);
INSERT INTO `user_info` VALUES ('anoop','anoop','8891355607','akshayraj666@gmail.com','sdasdasdasdasdasdasdasdads','F:\\Akshay\\Chefaholic\\build\\web\\profile_pic\\anoop.png',1);
INSERT INTO `user_info` VALUES ('prem','prem','9638527410','prem@gmail.com','sdfsdfdsfsdfsdcsdfsdfcscvsdfsdf sdfsdfsdfsdf','F:\\Akshay\\Chefaholic\\build\\web\\profile_pic\\rahul.png',5);
INSERT INTO `user_info` VALUES ('kiran','kiran','852963741','kiran@gmail.com','asdasdasdasdasdasdacxasdasdaxca asdasdaxasdasdaxasx','F:\\Akshay\\Chefaholic\\build\\web\\profile_pic\\rahul.png',6);
INSERT INTO `user_info` VALUES ('mary','mary','9637418520','mary@gmail.com','asdasdasscvsdfsdfscvsdfsdf sdfsdfsdsdfsfd','F:\\Akshay\\Chefaholic\\build\\web\\profile_pic\\rahul.png',2);


#
#  Foreign keys for table comments_info
#

ALTER TABLE `comments_info`
  ADD FOREIGN KEY (`username`) REFERENCES `login_info` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

#
#  Foreign keys for table favourite_info
#

ALTER TABLE `favourite_info`
  ADD FOREIGN KEY (`username`) REFERENCES `login_info` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

#
#  Foreign keys for table following_info
#

ALTER TABLE `following_info`
  ADD FOREIGN KEY (`username`) REFERENCES `login_info` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

#
#  Foreign keys for table recipie_info
#

ALTER TABLE `recipie_info`
  ADD FOREIGN KEY (`username`) REFERENCES `login_info` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

#
#  Foreign keys for table user_info
#

ALTER TABLE `user_info`
  ADD FOREIGN KEY (`username`) REFERENCES `login_info` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
