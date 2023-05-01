DROP DATABASE IF EXISTS  wyyhr ;
CREATE DATABASE wyyhr CHARACTER SET UTF8 ;
USE wyyhr ;

CREATE TABLE member (
   mid                  VARCHAR(100) NOT NULL,
   name                 VARCHAR(100),
   password             VARCHAR(32),
   flag                 INT,
   CONSTRAINT pk_mid PRIMARY KEY (mid)
) engine=INNODB;

CREATE TABLE role (
   rid                  VARCHAR(100) NOT NULL,
   name                 VARCHAR(100),
   note                 TEXT,
   CONSTRAINT pk_rid PRIMARY KEY (rid)
) engine=INNODB;

CREATE TABLE action (
   aid                  VARCHAR(100) not null,
   rid                  VARCHAR(100),
   name                 VARCHAR(100),
   note                 TEXT,
   CONSTRAINT pk_aid PRIMARY KEY (aid)
) engine=INNODB;

CREATE TABLE member_role (
   mid                  VARCHAR(100),
   rid                  VARCHAR(100)
) engine=INNODB;

CREATE TABLE record (
   recid                BIGINT AUTO_INCREMENT,
   udate                DATETIME,
   mid                  VARCHAR(100),
   name                 VARCHAR(100),
   operate              VARCHAR(100),
   data                 JSON,
   tab                  VARCHAR(100),
   CONSTRAINT pk_recid PRIMARY KEY (recid)
) engine=INNODB;

CREATE TABLE rating (
   rtid                 VARCHAR(100),
   name                 VARCHAR(100),
   low                  DOUBLE,
   high                 DOUBLE,
   note                 TEXT,
   CONSTRAINT pk_rtid PRIMARY KEY (rtid)
) engine=INNODB;

CREATE TABLE dept (
   deptno               BIGINT,
   dname                VARCHAR(100),
   bound                INT,
   current              INT,
   empno                BIGINT,
   mname                VARCHAR(100),
   CONSTRAINT pk_deptno PRIMARY KEY (deptno)
) engine=INNODB;

CREATE TABLE emp (
   empno                BIGINT,
   deptno               BIGINT,
   rtid                 VARCHAR(100),
   ename                VARCHAR(100),
   hiredate             DATE,
   sal                  DOUBLE,
   job                  VARCHAR(100),
   CONSTRAINT pk_empno PRIMARY KEY (empno)
) engine=INNODB;


-- 用户信息， 密码统一为“hello”
INSERT INTO member(mid, name, password, flag) VALUES ('admin', '超级管理员', 'A64507E6F52C887721248EEE2CC42506', 1);
INSERT INTO member(mid, name, password, flag) VALUES ('zs', '张三', 'A64507E6F52C887721248EEE2CC42506', 0);
INSERT INTO member(mid, name, password, flag) VALUES ('ls', '李四', 'A64507E6F52C887721248EEE2CC42506', 0);
INSERT INTO member(mid, name, password, flag) VALUES ('ww', '王五', 'A64507E6F52C887721248EEE2CC42506', 0);
INSERT INTO member(mid, name, password, flag) VALUES ('zl', '赵六', 'A64507E6F52C887721248EEE2CC42506', 0);
INSERT INTO member(mid, name, password, flag) VALUES ('xy', '小杨', 'A64507E6F52C887721248EEE2CC42506', 0);
-- 角色信息
INSERT INTO role(rid, name, note) VALUES ('emp', '雇员管理', '实现雇员信息查看与更新操作');
INSERT INTO role(rid, name, note) VALUES ('dept', '部门管理', '实现部门信息查看与更新操作');
INSERT INTO role(rid, name, note) VALUES ('rating', '级别管理', '实现雇员职位级别管理');
INSERT INTO role(rid, name, note) VALUES ('member', '用户管理', '实现管理用户的信息管理');
INSERT INTO role(rid, name, note) VALUES ('record', '记录管理', '实现数据更新记录的跟踪');
-- 权限信息
INSERT INTO action(aid, rid, name, note) VALUES ('emp:add', 'emp', '新增雇员', '创建新的雇员信息');
INSERT INTO action(aid, rid, name, note) VALUES ('emp:edit', 'emp', '雇员编辑', '编辑已有雇员信息');
INSERT INTO action(aid, rid, name, note) VALUES ('emp:delete', 'emp', '雇员删除', '删除雇员信息');
INSERT INTO action(aid, rid, name, note) VALUES ('emp:list', 'emp', '雇员列表', '雇员信息列表显示');

INSERT INTO action(aid, rid, name, note) VALUES ('dept:add', 'dept', '新增部门', '创建新的部门信息');
INSERT INTO action(aid, rid, name, note) VALUES ('dept:edit', 'dept', '部门编辑', '编辑已有部门信息');
INSERT INTO action(aid, rid, name, note) VALUES ('dept:delete', 'dept', '部门删除', '删除部门信息');
INSERT INTO action(aid, rid, name, note) VALUES ('dept:list', 'dept', '部门列表', '部门信息列表显示');

INSERT INTO action(aid, rid, name, note) VALUES ('rating:add', 'rating', '新增等级', '创建新的等级信息');
INSERT INTO action(aid, rid, name, note) VALUES ('rating:edit', 'rating', '等级编辑', '编辑已有等级信息');
INSERT INTO action(aid, rid, name, note) VALUES ('rating:delete', 'rating', '等级删除', '删除等级信息');
INSERT INTO action(aid, rid, name, note) VALUES ('rating:list', 'rating', '等级列表', '等级信息列表显示');

INSERT INTO action(aid, rid, name, note) VALUES ('member:add', 'member', '新增用户', '创建新的用户信息');
INSERT INTO action(aid, rid, name, note) VALUES ('member:edit', 'member', '用户编辑', '编辑已有用户信息');
INSERT INTO action(aid, rid, name, note) VALUES ('member:delete', 'member', '用户删除', '删除用户信息');
INSERT INTO action(aid, rid, name, note) VALUES ('member:list', 'member', '用户列表', '用户信息列表显示');

INSERT INTO action(aid, rid, name, note) VALUES ('record:list', 'record', '记录列表', '更新记录列表显示');

-- 用户与角色信息
INSERT INTO member_role(mid, rid) VALUES ('admin', 'emp');
INSERT INTO member_role(mid, rid) VALUES ('admin', 'dept');
INSERT INTO member_role(mid, rid) VALUES ('admin', 'rating');
INSERT INTO member_role(mid, rid) VALUES ('admin', 'member');
INSERT INTO member_role(mid, rid) VALUES ('admin', 'record');

INSERT INTO member_role(mid, rid) VALUES ('zs', 'emp');
INSERT INTO member_role(mid, rid) VALUES ('zs', 'dept');
INSERT INTO member_role(mid, rid) VALUES ('zs', 'rating');
INSERT INTO member_role(mid, rid) VALUES ('zs', 'record');

INSERT INTO member_role(mid, rid) VALUES ('ls', 'emp');
INSERT INTO member_role(mid, rid) VALUES ('ls', 'dept');
INSERT INTO member_role(mid, rid) VALUES ('ls', 'rating');
INSERT INTO member_role(mid, rid) VALUES ('ls', 'member');
INSERT INTO member_role(mid, rid) VALUES ('ls', 'record');

INSERT INTO member_role(mid, rid) VALUES ('ww', 'dept');
INSERT INTO member_role(mid, rid) VALUES ('ww', 'rating');

INSERT INTO member_role(mid, rid) VALUES ('zl', 'dept');
INSERT INTO member_role(mid, rid) VALUES ('zl', 'rating');

INSERT INTO member_role(mid, rid) VALUES ('xy', 'dept');
INSERT INTO member_role(mid, rid) VALUES ('xy', 'rating');

-- 工资等级信息
INSERT INTO rating (rtid, name, low, high, note) VALUES ('H-1', '实习生', 800.0, 1500.0, '在校未取得学历证书');
INSERT INTO rating (rtid, name, low, high, note) VALUES ('H-2', '一级职员', 2500.0, 5000.0, '新入职员工');
INSERT INTO rating (rtid, name, low, high, note) VALUES ('H-3', '二级职员', 5001.0, 8000.0, '工作半年以上的职员');
INSERT INTO rating (rtid, name, low, high, note) VALUES ('H-4', '三级职员', 8001.0, 12000.0, '工作两年以上的职员');
INSERT INTO rating (rtid, name, low, high, note) VALUES ('H-5', '部门主管', 12001.0, 15000.0, '部门业务主干');
INSERT INTO rating (rtid, name, low, high, note) VALUES ('H-6', '区域经理', 15001.0, 30000.0, '负责区域相关事务');
INSERT INTO rating (rtid, name, low, high, note) VALUES ('H-7', '公司总监', 30001.0, 50000.0, '负责全公司项目运营');
INSERT INTO rating (rtid, name, low, high, note) VALUES ('H-8', '副总裁', 50001.0, 80000.0, '联合创始人');
INSERT INTO rating (rtid, name, low, high, note) VALUES ('H-9', '总裁', 80001.0, 300000.0, 'CEO');

-- 部门信息
INSERT INTO dept(deptno, dname, bound, current, empno, mname) VALUES (10, '财务部', 5, 3, 10123, '程欢亭');
INSERT INTO dept(deptno, dname, bound, current, empno, mname) VALUES (20, '人事部', 3, 2, 20215, '沈锵良');
INSERT INTO dept(deptno, dname, bound, current, empno, mname) VALUES (30, '技术部', 10, 6, 30318, '刘中舟');
INSERT INTO dept(deptno, dname, bound, current, empno, mname) VALUES (40, '销售部', 20, 8, 40412, '钟召云');
INSERT INTO dept(deptno, dname, bound, current, empno, mname) VALUES (50, '市场部', 10, 5, 50531, '张广房');

-- 雇员信息
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (10123, 10, 'H-6', '程欢亭', '2016-10-10', 23500, '财务主管');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (10157, 10, 'H-3', '郭山彤', '2019-10-10', 6500, '财务');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (10158, 10, 'H-2', '梁敦厦', '2021-05-10', 4500, '财务');

INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (20215, 20, 'H-5', '沈锵良', '2014-05-10', 14900, '人事主管');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (20217, 20, 'H-4', '霍负浪', '2018-05-10', 11000, '职员');

INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (30318, 30, 'H-7', '刘中舟', '2015-07-17', 41000, '技术经理');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (30317, 30, 'H-5', '虞信品', '2017-05-10', 13000, '技术主管');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (30319, 30, 'H-4', '马仁毅', '2019-08-13', 11000, '技术员');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (30320, 30, 'H-4', '冯州龙', '2020-09-17', 9100, '技术员');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (30321, 30, 'H-4', '吴资龙', '2020-09-17', 9200, '技术员');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (30322, 30, 'H-4', '刘枝迟', '2020-09-17', 9300, '技术员');

INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (40412, 40, 'H-5', '钟召云', '2018-09-19', 13000, '销售主管');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (40413, 40, 'H-4', '李锐汉', '2019-09-20', 9500, '销售员');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (40415, 40, 'H-4', '严锋滕', '2019-09-21', 9500, '销售员');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (40417, 40, 'H-4', '李恩妙', '2019-09-22', 9500, '销售员');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (40419, 40, 'H-4', '薛敬文', '2019-09-23', 9500, '销售员');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (40431, 40, 'H-3', '文子隐', '2020-03-21', 6000, '销售员');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (40432, 40, 'H-3', '俞贡延', '2020-04-22', 5900, '销售员');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (40433, 40, 'H-2', '韦烽凌', '2020-09-22', 3900, '销售员');

INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (50531, 50, 'H-8', '张广房', '2012-03-14', 79900, '市场部副总裁');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (50532, 50, 'H-5', '袁修纯', '2014-08-27', 13000, '市场部副主管');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (50533, 50, 'H-5', '柯河舍', '2015-09-19', 15000, '市场部副主管');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (50535, 50, 'H-4', '郭俊立', '2019-09-18', 10900, '市场专员');
INSERT INTO emp(empno, deptno, rtid, ename, hiredate, sal, job) VALUES (50537, 50, 'H-3', '岑希伙', '2020-09-22', 7000, '市场专员');
