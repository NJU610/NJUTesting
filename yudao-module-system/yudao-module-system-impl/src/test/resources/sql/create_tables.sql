CREATE TABLE IF NOT EXISTS "system_dept" (
    "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "name" varchar(30) NOT NULL DEFAULT '',
    "parent_id" bigint NOT NULL DEFAULT '0',
    "sort" int NOT NULL DEFAULT '0',
    "leader_user_id" bigint DEFAULT NULL,
    "phone" varchar(11) DEFAULT NULL,
    "email" varchar(50) DEFAULT NULL,
    "status" tinyint NOT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    "tenant_id" bigint not null default  '0',
    PRIMARY KEY ("id")
) COMMENT '部门表';

CREATE TABLE IF NOT EXISTS "system_dict_data" (
    "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "sort" int NOT NULL DEFAULT '0',
    "label" varchar(100) NOT NULL DEFAULT '',
    "value" varchar(100) NOT NULL DEFAULT '',
    "dict_type" varchar(100) NOT NULL DEFAULT '',
    "status" tinyint NOT NULL DEFAULT '0',
    "color_type" varchar(100) NOT NULL DEFAULT '',
    "css_class" varchar(100) NOT NULL DEFAULT '',
    "remark" varchar(500) DEFAULT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
) COMMENT '字典数据表';

CREATE TABLE IF NOT EXISTS "system_role" (
    "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "name" varchar(30) NOT NULL,
    "code" varchar(100) NOT NULL,
    "sort" int NOT NULL,
    "data_scope" tinyint NOT NULL DEFAULT '1',
    "data_scope_dept_ids" varchar(500) NOT NULL DEFAULT '',
    "status" tinyint NOT NULL,
    "type" tinyint NOT NULL,
    "remark" varchar(500) DEFAULT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    "tenant_id" bigint not null default  '0',
    PRIMARY KEY ("id")
) COMMENT '角色信息表';

CREATE TABLE IF NOT EXISTS "system_role_menu" (
    "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "role_id" bigint NOT NULL,
    "menu_id" bigint NOT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    "tenant_id" bigint not null default  '0',
    PRIMARY KEY ("id")
) COMMENT '角色和菜单关联表';

CREATE TABLE IF NOT EXISTS "system_menu" (
    "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "name" varchar(50) NOT NULL,
    "permission" varchar(100) NOT NULL DEFAULT '',
    "menu_type" tinyint NOT NULL,
    "sort" int NOT NULL DEFAULT '0',
    "parent_id" bigint NOT NULL DEFAULT '0',
    "path" varchar(200) DEFAULT '',
    "icon" varchar(100) DEFAULT '#',
    "component" varchar(255) DEFAULT NULL,
    "status" tinyint NOT NULL DEFAULT '0',
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
) COMMENT '菜单权限表';

CREATE TABLE IF NOT EXISTS "system_user_role" (
     "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
     "user_id" bigint NOT NULL,
     "role_id" bigint NOT NULL,
     "creator" varchar(64) DEFAULT '',
     "create_time" timestamp DEFAULT NULL,
     "updater" varchar(64) DEFAULT '',
     "update_time" timestamp DEFAULT NULL,
     "deleted" bit DEFAULT FALSE,
    "tenant_id" bigint not null default  '0',
    PRIMARY KEY ("id")
) COMMENT '用户和角色关联表';

CREATE TABLE IF NOT EXISTS "system_dict_type" (
    "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "name" varchar(100) NOT NULL DEFAULT '',
    "type" varchar(100) NOT NULL DEFAULT '',
    "status" tinyint NOT NULL DEFAULT '0',
    "remark" varchar(500) DEFAULT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
) COMMENT '字典类型表';

CREATE TABLE IF NOT EXISTS `system_user_session` (
    `id` varchar(32) NOT NULL,
    `user_id` bigint DEFAULT NULL,
    "user_type" tinyint NOT NULL,
    `username` varchar(50) NOT NULL DEFAULT '',
    `user_ip` varchar(50) DEFAULT NULL,
    `user_agent` varchar(512) DEFAULT NULL,
    `session_timeout` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updater` varchar(64) DEFAULT '' ,
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    "tenant_id" bigint not null default  '0',
    PRIMARY KEY (`id`)
) COMMENT '用户在线 Session';

CREATE TABLE IF NOT EXISTS "system_post" (
    "id"          bigint      NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "code"        varchar(64) NOT NULL,
    "name"        varchar(50) NOT NULL,
    "sort"        integer     NOT NULL,
    "status"      tinyint     NOT NULL,
    "remark"      varchar(500)         DEFAULT NULL,
    "creator"     varchar(64)          DEFAULT '',
    "create_time" timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater"     varchar(64)          DEFAULT '',
    "update_time" timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted"     bit         NOT NULL DEFAULT FALSE,
    "tenant_id" bigint not null default  '0',
    PRIMARY KEY ("id")
) COMMENT '岗位信息表';

CREATE TABLE IF NOT EXISTS "system_notice" (
	"id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"title" varchar(50) NOT NULL COMMENT '公告标题',
	"content" text NOT NULL COMMENT '公告内容',
	"notice_type" tinyint NOT NULL COMMENT '公告类型（1通知 2公告）',
	"status" tinyint NOT NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
	"creator" varchar(64) DEFAULT '' COMMENT '创建者',
	"create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	"updater" varchar(64) DEFAULT '' COMMENT '更新者',
	"update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	"deleted" bit NOT NULL DEFAULT 0 COMMENT '是否删除',
    "tenant_id" bigint not null default  '0',
    PRIMARY KEY("id")
) COMMENT '通知公告表';

CREATE TABLE IF NOT EXISTS `system_login_log` (
    `id`          bigint(20)   NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    `log_type`    bigint(4)    NOT NULL,
    "user_id" bigint not null default '0',
    "user_type" tinyint NOT NULL,
    `trace_id`    varchar(64)  NOT NULL DEFAULT '',
    `username`    varchar(50)  NOT NULL DEFAULT '',
    `result`      tinyint(4)   NOT NULL,
    `user_ip`     varchar(50)  NOT NULL,
    `user_agent`  varchar(512) NOT NULL,
    `creator`   varchar(64)           DEFAULT '',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updater`   varchar(64)           DEFAULT '',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted`     bit(1)       NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) COMMENT ='系统访问记录';

CREATE TABLE IF NOT EXISTS `system_operate_log` (
    `id`               bigint(20)    NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    `trace_id`         varchar(64)   NOT NULL DEFAULT '',
    `user_id`          bigint(20)    NOT NULL,
    "user_type" tinyint not null default '0',
    `module`           varchar(50)   NOT NULL,
    `name`             varchar(50)   NOT NULL,
    `operate_type`     bigint(4)     NOT NULL DEFAULT '0',
    `content`          varchar(2000) NOT NULL DEFAULT '',
    `exts`             varchar(512)  NOT NULL DEFAULT '',
    `request_method`   varchar(16)            DEFAULT '',
    `request_url`      varchar(255)           DEFAULT '',
    `user_ip`          varchar(50)            DEFAULT NULL,
    `user_agent`       varchar(200)           DEFAULT NULL,
    `java_method`      varchar(512)  NOT NULL DEFAULT '',
    `java_method_args` varchar(8000)          DEFAULT '',
    `start_time`       datetime      NOT NULL,
    `duration`         int(11)       NOT NULL,
    `result_code`      int(11)       NOT NULL DEFAULT '0',
    `result_msg`       varchar(512)           DEFAULT '',
    `result_data`      varchar(4000)          DEFAULT '',
    `creator`        varchar(64)            DEFAULT '',
    `create_time`      datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updater`        varchar(64)            DEFAULT '',
    `update_time`      datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted`          bit(1)        NOT NULL DEFAULT '0',
    "tenant_id"         bigint not null default  '0',
    PRIMARY KEY (`id`)
) COMMENT ='操作日志记录';

CREATE TABLE IF NOT EXISTS "system_user" (
    "id" bigint not null GENERATED BY DEFAULT AS IDENTITY,
    "username" varchar(30) not null,
    "password" varchar(100) not null default '',
    "nickname" varchar(30) not null,
    "remark" varchar(500) default null,
    "dept_id" bigint default null,
    "post_ids" varchar(255) default null,
    "email" varchar(50) default '',
    "mobile" varchar(11) default '',
    "sex" tinyint default '0',
    "avatar" varchar(100) default '',
    "status" tinyint not null default '0',
    "login_ip" varchar(50) default '',
    "login_date" timestamp default null,
    "creator" varchar(64) default '',
    "create_time" timestamp not null default current_timestamp,
    "updater" varchar(64) default '',
    "update_time" timestamp not null default current_timestamp,
    "deleted" bit not null default false,
    "tenant_id" bigint not null default  '0',
    primary key ("id")
) comment '用户信息表';

CREATE TABLE IF NOT EXISTS "inf_api_access_log" (
  "id" bigint not null GENERATED BY DEFAULT AS IDENTITY,
  "trace_id" varchar(64) not null default '',
  "user_id" bigint not null default '0',
  "user_type" tinyint not null default '0',
  "application_name" varchar(50) not null,
  "request_method" varchar(16) not null default '',
  "request_url" varchar(255) not null default '',
  "request_params" varchar(8000) not null default '',
  "user_ip" varchar(50) not null,
  "user_agent" varchar(512) not null,
  "begin_time" timestamp not null,
  "end_time" timestamp not null,
  "duration" integer not null,
  "result_code" integer not null default '0',
  "result_msg" varchar(512) default '',
  "creator" varchar(64) default '',
  "create_time" timestamp not null default current_timestamp,
  "updater" varchar(64) default '',
  "update_time" timestamp not null default current_timestamp,
  "deleted" bit not null default false,
  "tenant_id" bigint not null default  '0',
  primary key ("id")
) COMMENT 'API 访问日志表';

CREATE TABLE IF NOT EXISTS "inf_api_error_log" (
 "id" bigint not null GENERATED BY DEFAULT AS IDENTITY,
 "trace_id" varchar(64) not null,
 "user_id" bigint not null default '0',
 "user_type" tinyint not null default '0',
 "application_name" varchar(50) not null,
 "request_method" varchar(16) not null,
 "request_url" varchar(255) not null,
 "request_params" varchar(8000) not null,
 "user_ip" varchar(50) not null,
 "user_agent" varchar(512) not null,
 "exception_time" timestamp not null,
 "exception_name" varchar(128) not null default '',
 "exception_message" clob not null,
 "exception_root_cause_message" clob not null,
 "exception_stack_trace" clob not null,
 "exception_class_name" varchar(512) not null,
 "exception_file_name" varchar(512) not null,
 "exception_method_name" varchar(512) not null,
 "exception_line_number" integer not null,
 "process_status" tinyint not null,
 "process_time" timestamp default null,
 "process_user_id" bigint default '0',
 "creator" varchar(64) default '',
 "create_time" timestamp not null default current_timestamp,
 "updater" varchar(64) default '',
 "update_time" timestamp not null default current_timestamp,
 "deleted" bit not null default false,
 "tenant_id" bigint not null default  '0',
 primary key ("id")
) COMMENT '系统异常日志';

CREATE TABLE IF NOT EXISTS "system_sms_channel" (
   "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
   "signature" varchar(10) NOT NULL,
   "code" varchar(63) NOT NULL,
   "status" tinyint NOT NULL,
   "remark" varchar(255) DEFAULT NULL,
   "api_key" varchar(63) NOT NULL,
   "api_secret" varchar(63) DEFAULT NULL,
   "callback_url" varchar(255) DEFAULT NULL,
   "creator" varchar(64) DEFAULT '',
   "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   "updater" varchar(64) DEFAULT '',
   "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   "deleted" bit NOT NULL DEFAULT FALSE,
   PRIMARY KEY ("id")
) COMMENT '短信渠道';

CREATE TABLE IF NOT EXISTS "system_sms_template" (
    "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "type" tinyint NOT NULL,
    "status" tinyint NOT NULL,
    "code" varchar(63) NOT NULL,
    "name" varchar(63) NOT NULL,
    "content" varchar(255) NOT NULL,
    "params" varchar(255) NOT NULL,
    "remark" varchar(255) DEFAULT NULL,
    "api_template_id" varchar(63) NOT NULL,
    "channel_id" bigint NOT NULL,
    "channel_code" varchar(63) NOT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
) COMMENT '短信模板';

CREATE TABLE IF NOT EXISTS "system_sms_log" (
   "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
   "channel_id" bigint NOT NULL,
   "channel_code" varchar(63) NOT NULL,
   "template_id" bigint NOT NULL,
   "template_code" varchar(63) NOT NULL,
   "template_type" tinyint NOT NULL,
   "template_content" varchar(255) NOT NULL,
   "template_params" varchar(255) NOT NULL,
   "api_template_id" varchar(63) NOT NULL,
   "mobile" varchar(11) NOT NULL,
   "user_id" bigint DEFAULT '0',
   "user_type" tinyint DEFAULT '0',
   "send_status" tinyint NOT NULL DEFAULT '0',
   "send_time" timestamp DEFAULT NULL,
   "send_code" int DEFAULT NULL,
   "send_msg" varchar(255) DEFAULT NULL,
   "api_send_code" varchar(63) DEFAULT NULL,
   "api_send_msg" varchar(255) DEFAULT NULL,
   "api_request_id" varchar(255) DEFAULT NULL,
   "api_serial_no" varchar(255) DEFAULT NULL,
   "receive_status" tinyint NOT NULL DEFAULT '0',
   "receive_time" timestamp DEFAULT NULL,
   "api_receive_code" varchar(63) DEFAULT NULL,
   "api_receive_msg" varchar(255) DEFAULT NULL,
   "creator" varchar(64) DEFAULT '',
   "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   "updater" varchar(64) DEFAULT '',
   "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   "deleted" bit NOT NULL DEFAULT FALSE,
   PRIMARY KEY ("id")
) COMMENT '短信日志';

CREATE TABLE IF NOT EXISTS "system_error_code" (
  "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  "type" tinyint NOT NULL DEFAULT '0',
  "application_name" varchar(50) NOT NULL,
  "code" int NOT NULL DEFAULT '0',
  "message" varchar(512) NOT NULL DEFAULT '',
  "memo" varchar(512) DEFAULT '',
  "creator" varchar(64) DEFAULT '',
  "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "updater" varchar(64) DEFAULT '',
  "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "deleted" bit NOT NULL DEFAULT FALSE,
  PRIMARY KEY ("id")
) COMMENT '错误码表';

CREATE TABLE IF NOT EXISTS "system_social_user" (
   "id" number NOT NULL GENERATED BY DEFAULT AS IDENTITY,
   "user_id" bigint NOT NULL,
   "user_type" tinyint NOT NULL DEFAULT '0',
   "type" tinyint NOT NULL,
   "openid" varchar(32) NOT NULL,
   "token" varchar(256) DEFAULT NULL,
   "union_id" varchar(32) NOT NULL,
   "raw_token_info" varchar(1024) NOT NULL,
   "nickname" varchar(32) NOT NULL,
   "avatar" varchar(255) DEFAULT NULL,
   "raw_user_info" varchar(1024) NOT NULL,
   "creator" varchar(64) DEFAULT '',
   "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   "updater" varchar(64) DEFAULT '',
   "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   "deleted" bit NOT NULL DEFAULT FALSE,
   PRIMARY KEY ("id")
) COMMENT '社交用户';

CREATE TABLE IF NOT EXISTS "system_tenant" (
    "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "name" varchar(63) NOT NULL,
    "contact_user_id" bigint NOT NULL DEFAULT '0',
    "contact_name" varchar(255) NOT NULL,
    "contact_mobile" varchar(255),
    "status" tinyint NOT NULL,
    "domain" varchar(63) DEFAULT '',
    "package_id"  bigint NOT NULL,
    "expire_time" timestamp NOT NULL,
    "account_count" int NOT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
) COMMENT '租户';

CREATE TABLE IF NOT EXISTS "system_tenant_package" (
    "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "name" varchar(30) NOT NULL,
    "status" tinyint NOT NULL,
    "remark" varchar(256),
    "menu_ids" varchar(2048) NOT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
) COMMENT '租户套餐表';

CREATE TABLE IF NOT EXISTS "system_sensitive_word" (
    "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "name" varchar(255) NOT NULL,
    "tags" varchar(1024) NOT NULL,
    "status" bit NOT NULL DEFAULT FALSE,
    "description" varchar(512),
    "creator" varchar(64) DEFAULT '',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
) COMMENT '系统敏感词';

CREATE TABLE IF NOT EXISTS "system_delegation" (
    "id" bigint(20) NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "creator_id" bigint(20),
    "launch_time" datetime NOT NULL,
    "name" varchar(255) NOT NULL,
    "table2_id" varchar(255),
    "table3_id" varchar(255),
    "url" varchar(255),
    "market_dept_staff_id" bigint(20),
    "testing_dept_staff_id" bigint(20),
    "market_remark" varchar(1024),
    "testing_remark" varchar(1024),
    "table14_id" varchar(1024),
--     "table12_id" varchar(1024),
    "offer_id" varchar(255),
    "offer_remark" varchar(1024),
    "contract_id" bigint(20),
    "sample_id" bigint(20),
    "solution_id" bigint(20),
    "report_id" bigint(20),
    "state" int(11) NOT NULL,
    "cancel_remark" varchar(1024),
    "creator" varchar(64) DEFAULT '',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    "table12_id" varchar(255),
    "project_id" varchar(255),
    PRIMARY KEY ("id")
    ) COMMENT '委托';

CREATE TABLE IF NOT EXISTS "system_contract" (
    "id" bigint(20) NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "table4_id" varchar(255),
    "table5_id" varchar(255),
    "client_remark" varchar(1024),
    "staff_remark" varchar(1024),
    "url" varchar(1024),
    "creator" varchar(64) DEFAULT '',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
    ) COMMENT '合同';

CREATE TABLE IF NOT EXISTS "system_sample" (
    "id" bigint(20) NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "type" varchar(255),
    "process_type" varchar(255),
    "url" varchar(1024),
    "information" varchar(1024),
    "auditor_id" bigint(20),
    "remark" varchar(1024),
    "modify_remark" varchar(1024),
    "state" int(11),
    "creator" varchar(64) DEFAULT '',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
    ) COMMENT '样品';

CREATE TABLE IF NOT EXISTS "system_solution" (
    "id" bigint(20) NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "table6_id" varchar(255),
    "auditor_id" bigint(20),
    "table13_id" varchar(255),
    "creator" varchar(64) DEFAULT '',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
    ) COMMENT '测试方案';

CREATE TABLE IF NOT EXISTS "system_report" (
    "id" bigint(20) NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "table7_id" varchar(255),
    "table8_id" varchar(255),
    "table9_id" varchar(255),
    "table11_id" varchar(255),
    "testing_dept_manager_id" bigint(20),
    "manager_remark" varchar(1024),
    "table10_id" varchar(255),
    "signatory_remark" varchar(1024),
    "signatory_id" bigint(20),
    "client_remark" varchar(1024),
    "creator" varchar(64) DEFAULT '',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
    ) COMMENT '测试报告';

CREATE TABLE IF NOT EXISTS "system_user_company" (
    "id" bigint(20) NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "user_id" bigint(20) NOT NULL,
    "company_id" bigint(20) NOT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
    ) COMMENT '用户和公司关联表';

CREATE TABLE IF NOT EXISTS "system_company" (
    "id" bigint(20) NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "name" varchar(200) NOT NULL,
    "address" varchar(200) NOT NULL,
    "phone" varchar(200) NOT NULL,
    "code" varchar(8) NOT NULL,
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    "creator" varchar(64) DEFAULT '',
    PRIMARY KEY ("id")
    ) COMMENT '公司';

CREATE TABLE IF NOT EXISTS "system_flow_log" (
    "id" bigint(20) NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "delegation_id" bigint(20) NOT NULL,
    "from_state" int(11),
    "to_state" int(11) NOT NULL,
    "operator_id" bigint(20) NOT NULL,
    "remark" varchar(1023) NOT NULL,
    "operate_time" datetime NOT NULL,
    "updater" varchar(64) DEFAULT '',
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    "creator" varchar(64) DEFAULT '',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "map_value" varchar(1024),
    PRIMARY KEY ("id")
    ) COMMENT '';

CREATE TABLE IF NOT EXISTS "system_front_menu" (
    "id" bigint(20) NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "name" varchar(255),
    "path" varchar(1024),
    "parent_keys" varchar(1024),
    "hide_in_menu" tinyint(4),
    "status" tinyint(4) NOT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
    ) COMMENT '前台菜单表';

CREATE TABLE IF NOT EXISTS "system_role_front_menu" (
    "id" bigint(20) NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "role_id" bigint(20),
    "front_menu_id" bigint(20),
    "creator" varchar(64) DEFAULT '',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
    ) COMMENT '角色前台菜单表';

CREATE TABLE IF NOT EXISTS "system_delegation_table12" (
    "id" bigint(20) NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "delegation_id" bigint(20) NOT NULL,
    "table12_id" varchar(255) NOT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
    ) COMMENT '委托-软件项目委托测试工作检查表';