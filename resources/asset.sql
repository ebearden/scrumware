-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 04, 2014 at 12:58 PM
-- Server version: 5.5.25
-- PHP Version: 5.4.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `scrumwarestaging`
--

-- --------------------------------------------------------

--
-- Table structure for table `Asset`
--

CREATE TABLE `Asset` (
  `asset_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `created` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `created_by` int(10) unsigned NOT NULL,
  `updated` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_by` int(10) unsigned NOT NULL,
  `asset_name` char(40) DEFAULT NULL,
  `description` text,
  `location` longtext,
  `project_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`asset_id`),
  KEY `project_id` (`project_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Constraints for table `Asset`
--
ALTER TABLE `Asset`
  ADD CONSTRAINT `Asset_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `Project` (`project_id`);
