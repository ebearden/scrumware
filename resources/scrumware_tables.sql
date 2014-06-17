use scrumwarestaging;

#----------------------------------
# Rebuild blank tables on localhost.
#----------------------------------
drop table if exists Task_Dependencies;
drop table if exists Task;
drop table if exists Story;
drop table if exists Sprint;
drop table if exists Project;
drop table if exists Sys_User;
drop table if exists Status;
drop table if exists Role;
#----------------------------------

create table if not exists Role (
    sys_id int unsigned not null auto_increment,
    created datetime not null default NOW(),
    updated datetime not null default NOW(),
        
    active boolean DEFAULT true,
    name varchar(40),
    description text(1000),
    
    PRIMARY KEY (sys_id)
);

create table if not exists Status (
    sys_id int unsigned not null auto_increment,
    created datetime not null default NOW(),
    updated datetime not null default NOW(),
    
    name varchar(40),
    
    PRIMARY KEY (sys_id)
);

create table if not exists Sys_User (
    sys_id int unsigned not null auto_increment,
    created datetime not null default NOW(),
    updated datetime not null default NOW(),
    
    username varchar(40) not null,
    password varchar(40) not null,
    first_name varchar(40),
    last_name varchar(40),
    email_address varchar(60),
    role int unsigned,
    active boolean DEFAULT true,
    
    PRIMARY KEY (sys_id),
    FOREIGN KEY (role) REFERENCES Role(sys_id)
);

create table if not exists Project (
    sys_id int unsigned not null auto_increment,
    created datetime not null default NOW(),
    created_by int unsigned not null,
    updated datetime not null default NOW(),
    updated_by int unsigned not null,
    
    name varchar(40),
    description text(1000),
    project_manager int unsigned,
    planned_start_date datetime,
    planned_end_date datetime,
    status varchar(40),
    
    PRIMARY KEY (sys_id),
    FOREIGN KEY (created_by) REFERENCES Sys_User(sys_id),
    FOREIGN KEY (updated_by) REFERENCES Sys_User(sys_id),
    FOREIGN KEY (project_manager) REFERENCES Sys_User(sys_id)
);

create table if not exists Sprint (
    sys_id int unsigned not null auto_increment,
    created datetime not null default NOW(),
    created_by int unsigned not null,
    updated datetime not null default NOW(),
    updated_by int unsigned not null,
    
    name varchar(40),
    description text(1000),
    start_date datetime,
    end_date datetime,
    status varchar(40),
    project_id int unsigned,
    
    PRIMARY KEY (sys_id),
    FOREIGN KEY (created_by) REFERENCES Sys_User(sys_id),
    FOREIGN KEY (updated_by) REFERENCES Sys_User(sys_id),
    FOREIGN KEY (project_id) REFERENCES Project(sys_id)
);

create table if not exists Story (
    sys_id int unsigned not null auto_increment,
    created datetime not null default NOW(),
    created_by int unsigned not null,
    updated datetime not null default NOW(),
    updated_by int unsigned not null,
    
    name varchar(40),
    description text(1000),
    acceptance_criteria text(1000),
    status varchar(40),
    project_id int unsigned,
    sprint_id int unsigned,
    task_count int DEFAULT 0,
    
    PRIMARY KEY (sys_id),
    FOREIGN KEY (created_by) REFERENCES Sys_User(sys_id),
    FOREIGN KEY (updated_by) REFERENCES Sys_User(sys_id),
    FOREIGN KEY (project_id) REFERENCES Project(sys_id),
    FOREIGN KEY (sprint_id) REFERENCES Sprint(sys_id)
);

create table if not exists Task (
    sys_id int unsigned not null auto_increment,
    created datetime not null default NOW(),
    created_by int unsigned not null,
    updated datetime not null default NOW(),
    updated_by int unsigned not null,
    
    name char(40),
    description text(1000),
    assigned_to int unsigned, 
    status varchar(40),
    work_notes text(1000),
    story_id int unsigned,
    
    PRIMARY KEY (sys_id),
    FOREIGN KEY (created_by) REFERENCES Sys_User(sys_id),
    FOREIGN KEY (updated_by) REFERENCES Sys_User(sys_id),
    FOREIGN KEY (story_id) REFERENCES Story(sys_id),
    FOREIGN KEY (assigned_to) REFERENCES Sys_User(sys_id)
);

create table if not exists Task_Dependencies (
    sys_id int unsigned not null auto_increment,
    created datetime not null default NOW(),
    updated datetime not null default NOW(),
    task_id int unsigned,
    depends_on int unsigned,
    
    PRIMARY KEY (sys_id),
    FOREIGN KEY (task_id) REFERENCES Task(sys_id),
    FOREIGN KEY (depends_on) REFERENCES Task(sys_id)
);

#----------------------------------
# Populate Test data
#----------------------------------

# Roles
insert into Role (name, description) values ('admin', 'Admin');
insert into Role (name, description) values ('project_manager', 'Project Manager');
insert into Role (name, description) values ('developer', 'Developer');
# Statuses
insert into Status (name) values ('Todo');
insert into Status (name) values ('In Process');
insert into Status (name) values ('To Verify');
insert into Status (name) values ('Done');
# Users
insert into Sys_User (username, password, first_name, last_name, role) values ('admin', 'goodpass', 'Super', 'User', 1);
insert into Sys_User (username, password, first_name, last_name, email_address, role) values ('ebearden', 'pw', 'Elvin', 'Bearden', 'elvin@scw.com', 3);
insert into Sys_User (username, password, first_name, last_name, email_address, role) values ('ekubic', 'pw', 'Emily', 'Kubic', 'emily@scw.com', 3);
insert into Sys_User (username, password, first_name, last_name, email_address, role) values ('jthao', 'pw', 'Joshua', 'Thao', 'joshua@scw.com', 3);
insert into Sys_User (username, password, first_name, last_name, email_address, role) values ('nzitzer', 'pw', 'Nick', 'Zitzer', 'nick@scw.com', 2);
insert into Sys_User (username, password, first_name, last_name, email_address, role) values ('jzorgdrager', 'pw', 'John', 'Zorgdrager', 'john@scw.com', 3);
# Projects
insert into Project (name, description, project_manager, planned_start_date, planned_end_date, status, created_by, updated_by) 
values ('Do Things', 'Things to be done', 5, NOW(), DATE_ADD(NOW(),INTERVAL 45 DAY), 'Active', 1, 1);
insert into Project (name, description, project_manager, planned_start_date, planned_end_date, status, created_by, updated_by) 
values ('Do Other Things', 'Such things done', 5, NOW(), DATE_ADD(NOW(),INTERVAL 10 DAY), 'Planned', 5, 1);
insert into Project (name, description, project_manager, planned_start_date, planned_end_date, status, created_by, updated_by) 
values ('Such Big Good Plans', 'Things to be done way later.', 5, NOW(), DATE_ADD(NOW(),INTERVAL 60 DAY), 'Planned', 1, 5);
insert into Project (name, description, project_manager, planned_start_date, planned_end_date, status, created_by, updated_by) 
values ('Create Skynet', 'Things we do in 5 days', 5, NOW(), DATE_ADD(NOW(),INTERVAL 5 DAY), 'Active', 5, 1);
# Sprints
insert into Sprint (name, description, start_date, end_date, status, project_id, created_by, updated_by)
values ('Sprint1', 'Sprint Description', NOW(), DATE_ADD(NOW(),INTERVAL 10 DAY), 'Active', 1, 5, 1);
insert into Sprint (name, description, start_date, end_date, status, project_id, created_by, updated_by)
values ('Sprint2', 'Sprint Description', NOW(), DATE_ADD(NOW(),INTERVAL 15 DAY), 'Backlog', 2, 5, 5);
insert into Sprint (name, description, start_date, end_date, status, project_id, created_by, updated_by)
values ('Sprint3', 'Sprint Description', NOW(), DATE_ADD(NOW(),INTERVAL 20 DAY), 'Backlogs', 3, 1, 1);
insert into Sprint (name, description, start_date, end_date, status, project_id, created_by, updated_by)
values ('Sprint4', 'Sprint Description', NOW(), DATE_ADD(NOW(),INTERVAL 50 DAY), 'Complete', 2, 1, 5);
insert into Sprint (name, description, start_date, end_date, status, project_id, created_by, updated_by)
values ('Sprint5', 'Sprint Description', NOW(), DATE_ADD(NOW(),INTERVAL 60 DAY), 'Active', 4, 5, 1);
# Stories
insert into Story (name, description, acceptance_criteria, status, project_id, sprint_id, task_count, created_by, updated_by)
values ("Story1", "Story Description", "Things should work", "In Progress", 1, 1, 0, 1, 1);
insert into Story (name, description, acceptance_criteria, status, project_id, sprint_id, task_count, created_by, updated_by)
values ("Story2", "Story Description", "Things should work", "Backlog", 1, 1, 0, 1, 1);
insert into Story (name, description, acceptance_criteria, status, project_id, sprint_id, task_count, created_by, updated_by)
values ("Story3", "Story Description", "Things should work", "In Progress", 1, 1, 0, 1, 1);
insert into Story (name, description, acceptance_criteria, status, project_id, sprint_id, task_count, created_by, updated_by)
values ("Story4", "Story Description", "Things should work", "In QA", 2, 2, 0, 1, 1);
insert into Story (name, description, acceptance_criteria, status, project_id, sprint_id, task_count, created_by, updated_by)
values ("Story5", "Story Description", "Things should work", "Completed", 2, 4, 0, 1, 1);
# Tasks
insert into Task (name, description, status, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Task1", "Task1 depends on task 2", "In Progress", "Some work was done", 1, 5, 5, 2);
insert into Task (name, description, status, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Task2", "Task1 depends on me", "In Progress", "Some work was done", 1, 5, 5, 3);
insert into Task (name, description, status, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Task3", "Task Description", "Active", "Some work was done", 1, 5, 5, 3);
insert into Task (name, description, status, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Task4", "Task Description", "Completed", "Some work was done", 1, 5, 5, 4);
insert into Task (name, description, status, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Task5", "Task Description", "Backlog", "Some work was done", 1, 5, 5, 6);
insert into Task (name, description, status, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Task6", "Task Description", "In Progress", "Some work was done", 2, 5, 5, 6);
insert into Task (name, description, status, work_notes, story_id, created_by, updated_by, assigned_to)
values ("Task7", "Task Description", "In Progress", "Some work was done", 2, 5, 5, 1);
# Task Dependencies
insert into Task_Dependencies (task_id, depends_on) values (2, 1);
insert into Task_Dependencies (task_id, depends_on) values (3, 1);
insert into Task_Dependencies (task_id, depends_on) values (1, 4);
