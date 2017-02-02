
/* Drop Tables */

DROP TABLE GJ_BAG CASCADE CONSTRAINTS;
DROP TABLE GJ_LOG_C CASCADE CONSTRAINTS;
DROP TABLE GJ_C_FILE CASCADE CONSTRAINTS;
DROP TABLE GJ_JIWON CASCADE CONSTRAINTS;
DROP TABLE GJ_MEM_RECRUIT CASCADE CONSTRAINTS;
DROP TABLE GJ_MEM_C CASCADE CONSTRAINTS;
DROP TABLE GJ_BOARD_FREE CASCADE CONSTRAINTS;
DROP TABLE GJ_LOG_P CASCADE CONSTRAINTS;
DROP TABLE GJ_EXPINFO CASCADE CONSTRAINTS;
DROP TABLE GJ_HAK CASCADE CONSTRAINTS;
DROP TABLE GJ_LAN CASCADE CONSTRAINTS;
DROP TABLE GJ_LAN_CER CASCADE CONSTRAINTS;
DROP TABLE GJ_LICENSE CASCADE CONSTRAINTS;
DROP TABLE GJ_P_FILE CASCADE CONSTRAINTS;
DROP TABLE GJ_MEM_RESUME CASCADE CONSTRAINTS;
DROP TABLE GJ_MEM_P CASCADE CONSTRAINTS;
DROP TABLE GJ_ADDR CASCADE CONSTRAINTS;
DROP TABLE GJ_ADMIN CASCADE CONSTRAINTS;
DROP TABLE GJ_BOARD_NOTICE CASCADE CONSTRAINTS;
DROP TABLE GJ_JIK2 CASCADE CONSTRAINTS;
DROP TABLE GJ_JIK1 CASCADE CONSTRAINTS;
DROP TABLE GJ_UP CASCADE CONSTRAINTS;




/* Create Tables */

CREATE TABLE GJ_ADDR
(
	addr_code number NOT NULL,
	addr_city1 varchar2(20),
	addr_post number,
	addr_city2 varchar2(20),
	addr_city3 varchar2(20),
	addr_detail varchar2(100),
	PRIMARY KEY (addr_code)
);


CREATE TABLE GJ_ADMIN
(
	admin_id varchar2(20) NOT NULL,
	admin_pwd varchar2(20),
	PRIMARY KEY (admin_id)
);


CREATE TABLE GJ_BAG
(
	bag_num number NOT NULL,
	bag_comment varchar2(100),
	mem_c_id varchar2(20) NOT NULL,
	mem_pr_num number NOT NULL,
	PRIMARY KEY (bag_num)
);


CREATE TABLE GJ_BOARD_FREE
(
	board_free_num number NOT NULL,
	board_free_title varchar2(300),
	board_free_contents varchar2(3000),
	board_free_date date,
	board_free_ref number,
	board_free_lev number,
	board_free_step number,
	mem_p_id varchar2(20) NOT NULL,
	PRIMARY KEY (board_free_num)
);


CREATE TABLE GJ_BOARD_NOTICE
(
	board_notice_num number NOT NULL,
	board_notice_title varchar2(300),
	board_notice_contents varchar2(3000),
	board_notice_date date,
	PRIMARY KEY (board_notice_num)
);


CREATE TABLE GJ_C_FILE
(
	file_c_code number NOT NULL,
	file_c_name varchar2(60),
	file_c_sname varchar2(60),
	file_c_date date,
	mem_cr_num number NOT NULL,
	PRIMARY KEY (file_c_code)
);


CREATE TABLE GJ_EXPINFO
(
	expinfo_code number NOT NULL,
	expinfo_date varchar2(20),
	expinfo_cname varchar2(40),
	expinfo_grade varchar2(20),
	expinfo_detail varchar2(1000),
	mem_pr_num number NOT NULL,
	PRIMARY KEY (expinfo_code)
);


CREATE TABLE GJ_HAK
(
	hak_code number NOT NULL,
	hak_type varchar2(20),
	hak_name varchar2(50),
	hak_major varchar2(50),
	hak_date varchar2(20),
	mem_pr_num number NOT NULL,
	PRIMARY KEY (hak_code)
);


CREATE TABLE GJ_JIK1
(
	jik1_code number NOT NULL,
	jik1_name varchar2(100),
	PRIMARY KEY (jik1_code)
);


CREATE TABLE GJ_JIK2
(
	jik2_code number NOT NULL,
	jik2_name varchar2(100),
	jik1_code number NOT NULL,
	PRIMARY KEY (jik2_code)
);


CREATE TABLE GJ_JIWON
(
	jiwon_num number NOT NULL,
	mem_cr_num number NOT NULL,
	mem_pr_num number NOT NULL,
	jiwon_date date,
	jiwon_check char(1),
	PRIMARY KEY (jiwon_num)
);


CREATE TABLE GJ_LAN
(
	lan_code number NOT NULL,
	lan_name varchar2(50),
	lan_grade varchar2(10),
	mem_pr_num number NOT NULL,
	PRIMARY KEY (lan_code)
);


CREATE TABLE GJ_LAN_CER
(
	lan_cer_code number NOT NULL,
	lan_cer_name varchar2(50),
	lan_cer_level varchar2(10),
	lan_cer_date varchar2(10),
	mem_pr_num number NOT NULL,
	PRIMARY KEY (lan_cer_code)
);


CREATE TABLE GJ_LICENSE
(
	license_code number NOT NULL,
	license_name varchar2(50),
	license_date varchar2(10),
	mem_pr_num number NOT NULL,
	PRIMARY KEY (license_code)
);


CREATE TABLE GJ_LOG_C
(
	log_c_code number NOT NULL,
	log_c_type varchar2(20),
	log_c_ipaddr varchar2(20),
	log_c_time varchar2(20),
	mem_c_id varchar2(20) NOT NULL,
	PRIMARY KEY (log_c_code)
);


CREATE TABLE GJ_LOG_P
(
	log_p_code number NOT NULL,
	log_p_type varchar2(20),
	log_p_ipaddr varchar2(20),
	log_p_time varchar2(20),
	mem_p_id varchar2(20) NOT NULL,
	PRIMARY KEY (log_p_code)
);


CREATE TABLE GJ_MEM_C
(
	mem_c_id varchar2(20) NOT NULL,
	mem_c_pwd varchar2(20),
	mem_c_name varchar2(40),
	mem_c_comnum varchar2(20),
	mem_c_bossname varchar2(20),
	mem_c_homepage varchar2(30),
	mem_c_intro varchar2(3000),
	mem_c_year varchar2(10),
	mem_c_sawon number,
	mem_c_jabon number,
	mem_c_machul number,
	mem_c_date date,
	mem_c_img varchar2(60),
	mem_c_simg varchar2(60),
	mem_c_addr varchar2(100),
	addr_code number NOT NULL,
	up_code number NOT NULL,
	PRIMARY KEY (mem_c_id)
);


CREATE TABLE GJ_MEM_P
(
	mem_p_id varchar2(20) NOT NULL,
	mem_p_pwd varchar2(20),
	mem_p_name varchar2(20),
	mem_p_phone varchar2(20),
	mem_p_email varchar2(20),
	mem_p_gender varchar2(10),
	mem_p_date date,
	mem_p_addr varchar2(100),
	addr_code number NOT NULL,
	PRIMARY KEY (mem_p_id)
);


CREATE TABLE GJ_MEM_RECRUIT
(
	mem_cr_num number NOT NULL,
	mem_cr_gyoung varchar2(10),
	mem_cr_sal number,
	mem_cr_jobtype varchar2(20),
	mem_cr_jobtime number,
	mem_cr_rtime varchar2(20),
	mem_cr_chadate varchar2(20),
	mem_cr_workout varchar2(3000),
	mem_cr_recruitnumber number,
	mem_cr_age number,
	mem_cr_gender varchar2(10),
	mem_cr_document varchar2(300),
	mem_cr_howto varchar2(3000),
	mem_cr_qna varchar2(300),
	mem_cr_qnaname varchar2(20),
	mem_cr_hak varchar2(20),
	mem_cr_license varchar2(100),
	mem_cr_lan_cer varchar2(100),
	mem_cr_lan varchar2(100),
	mem_cr_date date,
	mem_cr_check char(1),
	mem_cr_hit number,
	mem_cr_addr varchar2(100),
	addr_code number NOT NULL,
	mem_c_id varchar2(20) NOT NULL,
	jik1_code number NOT NULL,
	PRIMARY KEY (mem_cr_num)
);


CREATE TABLE GJ_MEM_RESUME
(
	mem_pr_num number NOT NULL,
	mem_pr_title varchar2(100),
	mem_pr_marry varchar2(10),
	mem_pr_byoung varchar2(10),
	mem_pr_bohoon varchar2(10),
	mem_pr_boho varchar2(10),
	mem_pr_handy varchar2(10),
	mem_pr_gyoung varchar2(10),
	mem_pr_exp varchar2(3000),
	mem_pr_prize varchar2(3000),
	mem_pr_wishsal varchar2(20),
	mem_pr_resume varchar2(10000),
	mem_pr_date date,
	mem_pr_img varchar2(60),
	mem_pr_simg varchar2(60),
	mem_pr_check char(1),
	mem_p_id varchar2(20) NOT NULL,
	jik1_code number NOT NULL,
	PRIMARY KEY (mem_pr_num)
);


CREATE TABLE GJ_P_FILE
(
	file_p_code number NOT NULL,
	file_p_name varchar2(60),
	file_p_sname varchar2(60),
	file_p_date date,
	mem_pr_num number NOT NULL,
	PRIMARY KEY (file_p_code)
);


CREATE TABLE GJ_UP
(
	up_code number NOT NULL,
	up_name varchar2(100),
	PRIMARY KEY (up_code)
);



/* Create Foreign Keys */

ALTER TABLE GJ_MEM_C
	ADD FOREIGN KEY (addr_code)
	REFERENCES GJ_ADDR (addr_code)
;


ALTER TABLE GJ_MEM_P
	ADD FOREIGN KEY (addr_code)
	REFERENCES GJ_ADDR (addr_code)
;


ALTER TABLE GJ_MEM_RECRUIT
	ADD FOREIGN KEY (addr_code)
	REFERENCES GJ_ADDR (addr_code)
;


ALTER TABLE GJ_JIK2
	ADD FOREIGN KEY (jik1_code)
	REFERENCES GJ_JIK1 (jik1_code)
;


ALTER TABLE GJ_MEM_RECRUIT
	ADD FOREIGN KEY (jik1_code)
	REFERENCES GJ_JIK1 (jik1_code)
;


ALTER TABLE GJ_MEM_RESUME
	ADD FOREIGN KEY (jik1_code)
	REFERENCES GJ_JIK1 (jik1_code)
;


ALTER TABLE GJ_BAG
	ADD FOREIGN KEY (mem_c_id)
	REFERENCES GJ_MEM_C (mem_c_id)
;


ALTER TABLE GJ_LOG_C
	ADD FOREIGN KEY (mem_c_id)
	REFERENCES GJ_MEM_C (mem_c_id)
;


ALTER TABLE GJ_MEM_RECRUIT
	ADD FOREIGN KEY (mem_c_id)
	REFERENCES GJ_MEM_C (mem_c_id)
;


ALTER TABLE GJ_BOARD_FREE
	ADD FOREIGN KEY (mem_p_id)
	REFERENCES GJ_MEM_P (mem_p_id)
;


ALTER TABLE GJ_LOG_P
	ADD FOREIGN KEY (mem_p_id)
	REFERENCES GJ_MEM_P (mem_p_id)
;


ALTER TABLE GJ_MEM_RESUME
	ADD FOREIGN KEY (mem_p_id)
	REFERENCES GJ_MEM_P (mem_p_id)
;


ALTER TABLE GJ_C_FILE
	ADD FOREIGN KEY (mem_cr_num)
	REFERENCES GJ_MEM_RECRUIT (mem_cr_num)
;


ALTER TABLE GJ_JIWON
	ADD FOREIGN KEY (mem_cr_num)
	REFERENCES GJ_MEM_RECRUIT (mem_cr_num)
;


ALTER TABLE GJ_BAG
	ADD FOREIGN KEY (mem_pr_num)
	REFERENCES GJ_MEM_RESUME (mem_pr_num)
;


ALTER TABLE GJ_EXPINFO
	ADD FOREIGN KEY (mem_pr_num)
	REFERENCES GJ_MEM_RESUME (mem_pr_num)
;


ALTER TABLE GJ_HAK
	ADD FOREIGN KEY (mem_pr_num)
	REFERENCES GJ_MEM_RESUME (mem_pr_num)
;


ALTER TABLE GJ_JIWON
	ADD FOREIGN KEY (mem_pr_num)
	REFERENCES GJ_MEM_RESUME (mem_pr_num)
;


ALTER TABLE GJ_LAN
	ADD FOREIGN KEY (mem_pr_num)
	REFERENCES GJ_MEM_RESUME (mem_pr_num)
;


ALTER TABLE GJ_LAN_CER
	ADD FOREIGN KEY (mem_pr_num)
	REFERENCES GJ_MEM_RESUME (mem_pr_num)
;


ALTER TABLE GJ_LICENSE
	ADD FOREIGN KEY (mem_pr_num)
	REFERENCES GJ_MEM_RESUME (mem_pr_num)
;


ALTER TABLE GJ_P_FILE
	ADD FOREIGN KEY (mem_pr_num)
	REFERENCES GJ_MEM_RESUME (mem_pr_num)
;


ALTER TABLE GJ_MEM_C
	ADD FOREIGN KEY (up_code)
	REFERENCES GJ_UP (up_code)
;

DROP SEQUENCE MEM_RESUME_SEQ;
DROP SEQUENCE EXPINFO_SEQ;
DROP SEQUENCE LAN_CER_SEQ;
DROP SEQUENCE HAK_SEQ;
DROP SEQUENCE JIWON_SEQ;
DROP SEQUENCE JIK1_SEQ;
DROP SEQUENCE JIK2_SEQ;
DROP SEQUENCE LAN_SEQ;
DROP SEQUENCE BOARD_NOTICE_SEQ;
DROP SEQUENCE BOARD_FREE_SEQ;
DROP SEQUENCE LICENSE_SEQ;
DROP SEQUENCE P_FILE_SEQ;
DROP SEQUENCE C_FILE_SEQ;
DROP SEQUENCE LOG_P_SEQ;
DROP SEQUENCE LOG_C_SEQ;
DROP SEQUENCE UP_SEQ;
DROP SEQUENCE BAG_SEQ;
DROP SEQUENCE MEM_RECRUIT_SEQ;


CREATE SEQUENCE MEM_RESUME_SEQ;
CREATE SEQUENCE	EXPINFO_SEQ;
CREATE SEQUENCE LAN_CER_SEQ;
CREATE SEQUENCE HAK_SEQ;
CREATE SEQUENCE JIWON_SEQ;
CREATE SEQUENCE JIK1_SEQ;
CREATE SEQUENCE JIK2_SEQ;
CREATE SEQUENCE LAN_SEQ;
CREATE SEQUENCE BOARD_NOTICE_SEQ;
CREATE SEQUENCE BOARD_FREE_SEQ;
CREATE SEQUENCE LICENSE_SEQ;
CREATE SEQUENCE P_FILE_SEQ;
CREATE SEQUENCE C_FILE_SEQ;
CREATE SEQUENCE LOG_P_SEQ;
CREATE SEQUENCE LOG_C_SEQ;
CREATE SEQUENCE UP_SEQ;
CREATE SEQUENCE BAG_SEQ;
CREATE SEQUENCE MEM_RECRUIT_SEQ;

