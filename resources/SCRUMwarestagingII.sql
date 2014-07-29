use scrumwarestaging;

#----------------------------------
# Rebuild blank tables on localhost.
#----------------------------------
drop table if exists Project_Users;
drop table if exists Task_Dependencies;
drop table if exists Task;
drop table if exists Story;
drop table if exists Sprint;
drop table if exists Project;
drop table if exists Sys_User;
drop table if exists Status;
drop table if exists User_Role;
drop table if exists Asset;
#----------------------------------

create table if not exists User_Role (
    role_id int unsigned not null auto_increment,
    created datetime not null default '0000-00-00 00:00:01',
    updated datetime not null default '0000-00-00 00:00:01',
        
    active boolean DEFAULT true,
    role_name varchar(40),
    description text(1000),
    
    PRIMARY KEY (role_id)
) auto_increment=1;

create table if not exists Status (
    status_id int unsigned not null auto_increment,
    created datetime not null default '0000-00-00 00:00:01',
    updated datetime not null default '0000-00-00 00:00:01',
    
    status_name varchar(40),
    
    PRIMARY KEY (status_id)
) auto_increment=1;

CREATE TABLE Sys_User (
  user_id int(10) unsigned NOT NULL AUTO_INCREMENT,
  created datetime NOT NULL DEFAULT '0000-00-00 00:00:01',
  updated datetime NOT NULL DEFAULT '0000-00-00 00:00:01',
  username varchar(40) NOT NULL,
  password varchar(40) NOT NULL,
  first_name varchar(40) DEFAULT NULL,
  last_name varchar(40) DEFAULT NULL,
  email_address varchar(60) DEFAULT NULL,
  user_role int(10) unsigned NOT NULL,
  active tinyint(1) DEFAULT '1',
  PRIMARY KEY (user_id),
  KEY user_role (user_role)
) AUTO_INCREMENT=1 ;

create table if not exists Project (
    project_id int unsigned not null auto_increment,
    created datetime not null default '0000-00-00 00:00:01',
    created_by int unsigned not null,
    updated datetime not null default '0000-00-00 00:00:01',
    updated_by int unsigned not null,
    
    project_name varchar(40),
    description text(1000),
    project_manager int unsigned,
    planned_start_date datetime,
    planned_end_date datetime,
    status_id int unsigned,
    
    PRIMARY KEY (project_id),
    FOREIGN KEY (created_by) REFERENCES Sys_User(user_id),
    FOREIGN KEY (updated_by) REFERENCES Sys_User(user_id),
    FOREIGN KEY (project_manager) REFERENCES Sys_User(user_id),
    FOREIGN KEY (status_id) REFERENCES Status(status_id)
) auto_increment=1;

create table if not exists Sprint (
    sprint_id int unsigned not null auto_increment,
    created datetime not null default '0000-00-00 00:00:01',
    created_by int unsigned not null,
    updated datetime not null default '0000-00-00 00:00:01',
    updated_by int unsigned not null,
    
    sprint_name varchar(40),
    description text(1000),
    start_date datetime,
    end_date datetime,
    status_id int unsigned,
    project_id int unsigned,
    
    PRIMARY KEY (sprint_id),
    FOREIGN KEY (created_by) REFERENCES Sys_User(user_id),
    FOREIGN KEY (updated_by) REFERENCES Sys_User(user_id),
    FOREIGN KEY (status_id) REFERENCES Status(status_id),
    FOREIGN KEY (project_id) REFERENCES Project(project_id)
) auto_increment=1;

create table if not exists Story (
    story_id int unsigned not null auto_increment,
    created datetime not null default '0000-00-00 00:00:01',
    created_by int unsigned not null,
    updated datetime not null default '0000-00-00 00:00:01',
    updated_by int unsigned not null,
    
    story_name varchar(40),
    description text(1000),
    acceptance_criteria text(1000),
    status_id int unsigned,
    project_id int unsigned,
    sprint_id int unsigned,
    task_count int DEFAULT 0,
    
    PRIMARY KEY (story_id),
    FOREIGN KEY (created_by) REFERENCES Sys_User(user_id),
    FOREIGN KEY (updated_by) REFERENCES Sys_User(user_id),
    FOREIGN KEY (status_id) REFERENCES Status(status_id),
    FOREIGN KEY (project_id) REFERENCES Project(project_id),
    FOREIGN KEY (sprint_id) REFERENCES Sprint(sprint_id)
) auto_increment=1;

create table if not exists Task (
    task_id int unsigned not null auto_increment,
    created datetime not null default '0000-00-00 00:00:01',
    created_by int unsigned not null,
    updated datetime not null default '0000-00-00 00:00:01',
    updated_by int unsigned not null,
    
    task_name char(40),
    description text(1000),
    assigned_to int unsigned,
    status_id int unsigned,
    work_notes text(1000),
    story_id int unsigned,
    dependent_count int not null default '0',
    
    PRIMARY KEY (task_id),
    FOREIGN KEY (created_by) REFERENCES Sys_User(user_id),
    FOREIGN KEY (updated_by) REFERENCES Sys_User(user_id),
    FOREIGN KEY (status_id) REFERENCES Status(status_id),
    FOREIGN KEY (story_id) REFERENCES Story(story_id),
    FOREIGN KEY (assigned_to) REFERENCES Sys_User(user_id)
) auto_increment=1;

create table if not exists Asset (
    asset_id int unsigned not null auto_increment,
    created datetime not null default '0000-00-00 00:00:00',
    created_by int unsigned not null,
    updated datetime not null default '0000-00-00 00:00:00',
    updated_by int unsigned not null,
    
    asset_name char(40),
    description text(1000),
    location char(80),
    
    PRIMARY KEY (asset_id)
) auto_increment=1;

create table if not exists Task_Dependencies (
    dependency_id int unsigned not null auto_increment,
    created datetime not null default '0000-00-00 00:00:01',
    updated datetime not null default '0000-00-00 00:00:01',
    task_id int unsigned,
    depends_on int unsigned,
    active boolean default true, 
    
    PRIMARY KEY (dependency_id),
    FOREIGN KEY (task_id) REFERENCES Task(task_id),
    FOREIGN KEY (depends_on) REFERENCES Task(task_id)
) auto_increment=1;

create table if not exists Project_Users (
    proj_users_id int unsigned not null auto_increment,
    created datetime not null default '0000-00-00 00:00:01',
    updated datetime not null default '0000-00-00 00:00:01',
    project_id int unsigned,
    user_id int unsigned,
    
    PRIMARY KEY (proj_users_id),
    FOREIGN KEY (project_id) REFERENCES Project(project_id),
    FOREIGN KEY (user_id) REFERENCES Sys_User(user_id)
) auto_increment=1;

#----------------------------------
# Populate Test data
#----------------------------------

# Roles
insert into User_Role (role_name, description) values ('admin', 'Admin');
insert into User_Role (role_name, description) values ('project_manager', 'Project Manager');
insert into User_Role (role_name, description) values ('developer', 'Developer');
# Statuses
insert into Status (status_name) values ('Todo');
insert into Status (status_name) values ('In Process');
insert into Status (status_name) values ('To Verify');
insert into Status (status_name) values ('Done');
# Users
INSERT INTO Sys_User (created, updated, username, password, first_name, last_name, email_address, user_role, active) VALUES
('0000-00-00 00:00:01', '2014-07-27 20:08:36', 'admin', '0e1ddb984e31997e343e3c2bf879c6cdbec255ae', 'Super', 'User', 'admin@scw.com', 1, 1),
('0000-00-00 00:00:01', '0000-00-00 00:00:01', 'ebearden', 'bdab0b7dfea8906e8fb840c1b0a71e355875c721', 'Elvin', 'Bearden', 'elvin@scw.com', 3, 1),
('0000-00-00 00:00:01', '0000-00-00 00:00:01', 'ekubic', 'bdab0b7dfea8906e8fb840c1b0a71e355875c721', 'Emily', 'Kubic', 'emily@scw.com', 1, 1),
('0000-00-00 00:00:01', '0000-00-00 00:00:01', 'jthao', 'bdab0b7dfea8906e8fb840c1b0a71e355875c721', 'Joshua', 'Thao', 'joshua@scw.com', 3, 1),
('0000-00-00 00:00:01', '0000-00-00 00:00:01', 'nzitzer', 'bdab0b7dfea8906e8fb840c1b0a71e355875c721', 'Nick', 'Zitzer', 'nick@scw.com', 2, 1),
('0000-00-00 00:00:01', '0000-00-00 00:00:01', 'jzorgdrager', 'bdab0b7dfea8906e8fb840c1b0a71e355875c721', 'John', 'Zorgdrager', 'john@scw.com', 3, 1),
('2014-07-27 18:43:56', '2014-07-27 18:43:56', 'testinactive', 'b7aa244bfc1b880e486e42d0735ee16bf1dd1c09', 'test', 'inactive', 'inactive@scw.com', 3, 0);
# Projects
insert into Project (project_name, description, project_manager, planned_start_date, planned_end_date, status_id, created_by, updated_by)
values ('Do Things', 'Things to be done', 5, NOW(), DATE_ADD(NOW(),INTERVAL 45 DAY), 1, 1, 1);
insert into Project (project_name, description, project_manager, planned_start_date, planned_end_date, status_id, created_by, updated_by)
values ('Do Other Things', 'Such things done', 5, NOW(), DATE_ADD(NOW(),INTERVAL 10 DAY), 2, 5, 1);
insert into Project (project_name, description, project_manager, planned_start_date, planned_end_date, status_id, created_by, updated_by)
values ('Such Big Good Plans', 'Things to be done way later.', 5, NOW(), DATE_ADD(NOW(),INTERVAL 60 DAY), 1, 1, 5);
insert into Project (project_name, description, project_manager, planned_start_date, planned_end_date, status_id, created_by, updated_by)
values ('Create Skynet', 'Things we do in 5 days', 5, NOW(), DATE_ADD(NOW(),INTERVAL 5 DAY), 2, 5, 1);
# Sprints
insert into Sprint (sprint_name, description, start_date, end_date, status_id, project_id, created_by, updated_by)
values ('Sprint1', 'Sprint Description', NOW(), DATE_ADD(NOW(),INTERVAL 10 DAY), 2, 1, 5, 1);
insert into Sprint (sprint_name, description, start_date, end_date, status_id, project_id, created_by, updated_by)
values ('Sprint2', 'Sprint Description', NOW(), DATE_ADD(NOW(),INTERVAL 15 DAY), 1, 2, 5, 5);
insert into Sprint (sprint_name, description, start_date, end_date, status_id, project_id, created_by, updated_by)
values ('Sprint3', 'Sprint Description', NOW(), DATE_ADD(NOW(),INTERVAL 20 DAY), 1, 3, 1, 1);
insert into Sprint (sprint_name, description, start_date, end_date, status_id, project_id, created_by, updated_by)
values ('Sprint4', 'Sprint Description', NOW(), DATE_ADD(NOW(),INTERVAL 50 DAY), 4, 2, 1, 5);
insert into Sprint (sprint_name, description, start_date, end_date, status_id, project_id, created_by, updated_by)
values ('Sprint5', 'Sprint Description', NOW(), DATE_ADD(NOW(),INTERVAL 60 DAY), 2, 4, 5, 1);
# Stories
insert into Story (story_name, description, acceptance_criteria, status_id, project_id, sprint_id, task_count, created_by, updated_by)
values ("Story1", "Story Description", "Things should work", 2, 1, 1, 0, 1, 1);
insert into Story (story_name, description, acceptance_criteria, status_id, project_id, sprint_id, task_count, created_by, updated_by)
values ("Story2", "Story Description", "Things should work", 1, 1, 1, 0, 1, 1);
insert into Story (story_name, description, acceptance_criteria, status_id, project_id, sprint_id, task_count, created_by, updated_by)
values ("Story3", "Story Description", "Things should work", 2, 1, 1, 0, 1, 1);
insert into Story (story_name, description, acceptance_criteria, status_id, project_id, sprint_id, task_count, created_by, updated_by)
values ("Story4", "Story Description", "Things should work", 3, 2, 2, 0, 1, 1);
insert into Story (story_name, description, acceptance_criteria, status_id, project_id, sprint_id, task_count, created_by, updated_by)
values ("Story5", "Story Description", "Things should work", 4, 2, 4, 0, 1, 1);
# Tasks
insert into Task (task_name, description, status_id, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Implement Task CRUD", "Need to be able to create, retrieve, update and delete tasks.", 2, "Some notes", 1, 5, 5, 3);
insert into Task (task_name, description, status_id, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Implement Project CRUD", "Need to be able to create, retrieve, update and delete projects.", 2, "Some notes", 1, 5, 5, 3);
insert into Task (task_name, description, status_id, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Implement Story CRUD", "Need to be able to create, retrieve, update and delete stories.", 1, "Some notes", 1, 5, 5, 3);
insert into Task (task_name, description, status_id, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Implement Sprint CRUD", "Need to be able to create, retrieve, update and delete sprint.", 3, "Some notes", 1, 5, 5, 3);
insert into Task (task_name, description, status_id, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Implement Task Servlet", "Need to be able to create, retrieve, update and delete tasks using servlets.", 2, "Some notes", 1, 5, 5, 3);
insert into Task (task_name, description, status_id, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Implement Project Servlet", "Need to be able to create, retrieve, update and delete projects using servlets.", 4, "Some notes", 1, 5, 5, 3);
insert into Task (task_name, description, status_id, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Implement Story Servlet", "Need to be able to create, retrieve, update and delete stories using servlets.", 2, "Some notes", 1, 5, 5, 3);
insert into Task (task_name, description, status_id, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Implement Sprint Servlet", "Need to be able to create, retrieve, update and delete sprint using servlets.", 4, "Some notes", 1, 5, 5, 3);
insert into Task (task_name, description, status_id, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Task5", "Task Description", 1, "Some work was done", 1, 5, 5, 6);
insert into Task (task_name, description, status_id, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Task6", "Task Description", 2, "Some work was done", 2, 5, 5, 6);
insert into Task (task_name, description, status_id, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Task7", "Task Description", 3, "Some work was done", 2, 5, 5, 1);
# Task Dependencies
insert into Task_Dependencies (task_id, depends_on) values (2, 1);
insert into Task_Dependencies (task_id, depends_on) values (3, 1);
insert into Task_Dependencies (task_id, depends_on) values (4, 3);
insert into Task_Dependencies (task_id, depends_on) values (4, 2);
insert into Task_Dependencies (task_id, depends_on) values (5, 4);
# Project Users
insert into Project_Users (project_id, user_id) values (1, 1);
insert into Project_Users (project_id, user_id) values (1, 2);
insert into Project_Users (project_id, user_id) values (1, 3);
insert into Project_Users (project_id, user_id) values (1, 4);
insert into Project_Users (project_id, user_id) values (1, 5);
insert into Project_Users (project_id, user_id) values (1, 6);
insert into Project_Users (project_id, user_id) values (2, 1);
insert into Project_Users (project_id, user_id) values (2, 2);
insert into Project_Users (project_id, user_id) values (2, 3);
insert into Project_Users (project_id, user_id) values (2, 4);
insert into Project_Users (project_id, user_id) values (2, 5);
insert into Project_Users (project_id, user_id) values (2, 6);
insert into Project_Users (project_id, user_id) values (3, 1);
insert into Project_Users (project_id, user_id) values (3, 2);
insert into Project_Users (project_id, user_id) values (3, 3);
insert into Project_Users (project_id, user_id) values (3, 4);
insert into Project_Users (project_id, user_id) values (3, 5);
insert into Project_Users (project_id, user_id) values (3, 6);
insert into Project_Users (project_id, user_id) values (4, 1);
insert into Project_Users (project_id, user_id) values (4, 2);
insert into Project_Users (project_id, user_id) values (4, 3);
insert into Project_Users (project_id, user_id) values (4, 4);
insert into Project_Users (project_id, user_id) values (4, 5);
insert into Project_Users (project_id, user_id) values (4, 6);