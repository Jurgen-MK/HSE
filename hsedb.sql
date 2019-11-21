-- --------------------------------------------------------
-- Хост:                         10.64.1.72
-- Версия сервера:               Microsoft SQL Server 2014 - 12.0.2000.8
-- Операционная система:         Windows NT 6.1 <X64> (Build 7601: Service Pack 1)
-- HeidiSQL Версия:              9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Дамп структуры базы данных HSEKTZH
CREATE DATABASE IF NOT EXISTS "HSEKTZH";
USE "HSEKTZH";

-- Дамп структуры для таблица HSEKTZH.authorities
CREATE TABLE IF NOT EXISTS "authorities" (
	"username" VARCHAR(256) NOT NULL,
	"authority" VARCHAR(256) NOT NULL,
	PRIMARY KEY ("username","authority")
);

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица HSEKTZH.incidents
CREATE TABLE IF NOT EXISTS "incidents" (
	"inc_id" INT(10,0) NOT NULL,
	"user_id" INT(10,0) NOT NULL,
	"dated" DATETIME(3) NOT NULL,
	"discription" TEXT(2147483647) NULL DEFAULT NULL,
	"content_type" TINYINT(3,0) NULL DEFAULT NULL,
	"content_path" NVARCHAR(200) NULL DEFAULT NULL,
	PRIMARY KEY ("inc_id","user_id","dated")
);

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица HSEKTZH.oauth_access_token
CREATE TABLE IF NOT EXISTS "oauth_access_token" (
	"token_id" VARCHAR(256) NULL DEFAULT NULL,
	"token" VARBINARY(max) NULL DEFAULT NULL,
	"authentication_id" VARCHAR(256) NULL DEFAULT NULL,
	"user_name" VARCHAR(256) NULL DEFAULT NULL,
	"client_id" VARCHAR(256) NULL DEFAULT NULL,
	"authentication" VARBINARY(max) NULL DEFAULT NULL,
	"refresh_token" VARCHAR(256) NULL DEFAULT NULL
);

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица HSEKTZH.oauth_client_details
CREATE TABLE IF NOT EXISTS "oauth_client_details" (
	"client_id" VARCHAR(256) NOT NULL,
	"resource_ids" VARCHAR(256) NULL DEFAULT NULL,
	"client_secret" VARCHAR(256) NOT NULL,
	"scope" VARCHAR(256) NULL DEFAULT NULL,
	"authorized_grant_types" VARCHAR(256) NULL DEFAULT NULL,
	"web_server_redirect_uri" VARCHAR(256) NULL DEFAULT NULL,
	"authorities" VARCHAR(256) NULL DEFAULT NULL,
	"access_token_validity" INT(10,0) NULL DEFAULT NULL,
	"refresh_token_validity" INT(10,0) NULL DEFAULT NULL,
	"additional_information" VARCHAR(4000) NULL DEFAULT NULL,
	"autoapprove" VARCHAR(256) NULL DEFAULT NULL,
	PRIMARY KEY ("client_id")
);


-- Дамп данных таблицы HSEKTZH.oauth_client_details: 1 rows
DELETE FROM "oauth_client_details";
/*!40000 ALTER TABLE "oauth_client_details" DISABLE KEYS */;
INSERT INTO "oauth_client_details" ("client_id", "resource_ids", "client_secret", "scope", "authorized_grant_types", "web_server_redirect_uri", "authorities", "access_token_validity", "refresh_token_validity", "additional_information", "autoapprove") VALUES
	('clientId', NULL, '{bcrypt}$2a$10$vCXMWCn7fDZWOcLnIEhmK.74dvK1Eh8ae2WrWlhr2ETPLoxQctN4.', 'read,write', 'password,refresh_token,client_credentials', NULL, 'ROLE_CLIENT', 28800, NULL, NULL, NULL);
/*!40000 ALTER TABLE "oauth_client_details" ENABLE KEYS */;

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица HSEKTZH.oauth_client_token
CREATE TABLE IF NOT EXISTS "oauth_client_token" (
	"token_id" VARCHAR(256) NULL DEFAULT NULL,
	"token" VARBINARY(max) NULL DEFAULT NULL,
	"authentication_id" VARCHAR(256) NOT NULL,
	"user_name" VARCHAR(256) NULL DEFAULT NULL,
	"client_id" VARCHAR(256) NULL DEFAULT NULL,
	PRIMARY KEY ("authentication_id")
);

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица HSEKTZH.oauth_code
CREATE TABLE IF NOT EXISTS "oauth_code" (
	"code" VARCHAR(256) NULL DEFAULT NULL,
	"authentication" VARBINARY(max) NULL DEFAULT NULL
);

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица HSEKTZH.oauth_refresh_token
CREATE TABLE IF NOT EXISTS "oauth_refresh_token" (
	"token_id" VARCHAR(256) NULL DEFAULT NULL,
	"token" VARBINARY(max) NULL DEFAULT NULL,
	"authentication" VARBINARY(max) NULL DEFAULT NULL
);

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица HSEKTZH.users
CREATE TABLE IF NOT EXISTS "users" (
	"id" INT(10,0) NOT NULL,
	"username" VARCHAR(256) NOT NULL,
	"password" VARCHAR(256) NOT NULL,
	"enabled" TINYINT(3,0) NULL DEFAULT NULL,
	UNIQUE KEY ("username"),
	PRIMARY KEY ("id")
);

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица HSEKTZH.user_info
CREATE TABLE IF NOT EXISTS "user_info" (
	"id" INT(10,0) NULL DEFAULT NULL,
	"username" VARCHAR(256) NULL DEFAULT NULL,
	"fullname" VARCHAR(256) NULL DEFAULT NULL,
	"phone" VARCHAR(11) NULL DEFAULT NULL,
	"email" VARCHAR(50) NULL DEFAULT NULL,
	"position" VARCHAR(50) NULL DEFAULT NULL
);

-- Экспортируемые данные не выделены.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
