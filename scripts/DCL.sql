-- Squema creation
CREATE SCHEMA `crewmanager` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

-- Create crewmanageruser for application
CREATE USER IF NOT EXISTS 
'crewmanageruser'@'%'
IDENTIFIED BY 'oompaloompa';

-- Assign privileges to crewmanager database
GRANT SELECT, INSERT, DELETE, UPDATE ON crewmanager.* TO 'crewmanageruser'@'%';

--Create index by name to improve search by name
ADD INDEX `IDX_BY_NAME` USING BTREE (`name`) VISIBLE;