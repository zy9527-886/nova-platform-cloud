-- sys_cald definition

-- Drop table

-- DROP TABLE sys_cald;

CREATE TABLE sys_cald (
                                 id varchar(32) DEFAULT nextval('sys_calendar_id_seq'::regclass) NOT NULL, -- 主键
                                 cald_dt timestamp NOT NULL, -- 日期
                                 is_work int2 DEFAULT 1 NOT NULL, -- 是否工作日
                                 rmk varchar(255) DEFAULT NULL::character varying NULL, -- 备注
                                 cre_per varchar(64) DEFAULT NULL::character varying NULL, -- 创建人
                                 cre_tm timestamp NOT NULL, -- 创建时间
                                 CONSTRAINT sys_cald_pkey PRIMARY KEY (id)
);
COMMENT ON TABLE sys_cald IS '日历信息';

-- Column comments

COMMENT ON COLUMN sys_cald.id IS '主键';
COMMENT ON COLUMN sys_cald.cald_dt IS '日期';
COMMENT ON COLUMN sys_cald.is_work IS '是否工作日';
COMMENT ON COLUMN sys_cald.rmk IS '备注';
COMMENT ON COLUMN sys_cald.cre_per IS '创建人';
COMMENT ON COLUMN sys_cald.cre_tm IS '创建时间';


-- sys_cfg definition

-- Drop table

-- DROP TABLE sys_cfg;

CREATE TABLE sys_cfg (
                                id varchar(32) DEFAULT nextval('sys_config_id_seq'::regclass) NOT NULL, -- id
                                cfg_cd varchar(64) NOT NULL, -- 配置项代码
                                cfg_nm varchar(128) NOT NULL, -- 配置项名称
                                cfg_val varchar(255) NOT NULL, -- 配置项值
                                is_dsp int2 DEFAULT 1 NOT NULL, -- 是否显示
                                rmk varchar(255) DEFAULT NULL::character varying NULL, -- 备注字段
                                cre_per varchar(64) DEFAULT NULL::character varying NULL, -- 创建人
                                cre_tm timestamp NOT NULL, -- 创建时间
                                updt_per varchar(64) DEFAULT NULL::character varying NULL, -- 更新人
                                updt_tm timestamp NOT NULL, -- 更新时间
                                CONSTRAINT sys_config_pkey PRIMARY KEY (id)
);
COMMENT ON TABLE sys_cfg IS '系统配置信息';

-- Column comments

COMMENT ON COLUMN sys_cfg.id IS 'id';
COMMENT ON COLUMN sys_cfg.cfg_cd IS '配置项代码';
COMMENT ON COLUMN sys_cfg.cfg_nm IS '配置项名称';
COMMENT ON COLUMN sys_cfg.cfg_val IS '配置项值';
COMMENT ON COLUMN sys_cfg.is_dsp IS '是否显示';
COMMENT ON COLUMN sys_cfg.rmk IS '备注字段';
COMMENT ON COLUMN sys_cfg.cre_per IS '创建人';
COMMENT ON COLUMN sys_cfg.cre_tm IS '创建时间';
COMMENT ON COLUMN sys_cfg.updt_per IS '更新人';
COMMENT ON COLUMN sys_cfg.updt_tm IS '更新时间';


-- sys_dict definition

-- Drop table

-- DROP TABLE sys_dict;

CREATE TABLE sys_dict (
                                 id varchar(32) DEFAULT nextval('sys_dict_id_seq'::regclass) NOT NULL, -- 主键
                                 dict_typ varchar(32) NOT NULL, -- 字典类型代码（唯一）
                                 dict_nm varchar(64) NOT NULL, -- 字典名称
                                 dict_rmk varchar(255) DEFAULT NULL::character varying NULL, -- 条目备注
                                 prent_id int8 NULL, -- 父级类型id
                                 cre_per varchar(64) DEFAULT NULL::character varying NULL, -- 创建人
                                 cre_tm timestamp NOT NULL, -- 创建时间
                                 updt_per varchar(64) DEFAULT NULL::character varying NULL, -- 更新人
                                 updt_tm timestamp NOT NULL, -- 更新时间
                                 CONSTRAINT sys_dict_pkey PRIMARY KEY (id)
);
COMMENT ON TABLE sys_dict IS '数据字典';

-- Column comments

COMMENT ON COLUMN sys_dict.id IS '主键';
COMMENT ON COLUMN sys_dict.dict_typ IS '字典类型代码（唯一）';
COMMENT ON COLUMN sys_dict.dict_nm IS '字典名称';
COMMENT ON COLUMN sys_dict.dict_rmk IS '条目备注';
COMMENT ON COLUMN sys_dict.prent_id IS '父级类型id';
COMMENT ON COLUMN sys_dict.cre_per IS '创建人';
COMMENT ON COLUMN sys_dict.cre_tm IS '创建时间';
COMMENT ON COLUMN sys_dict.updt_per IS '更新人';
COMMENT ON COLUMN sys_dict.updt_tm IS '更新时间';


-- sys_file definition

-- Drop table

-- DROP TABLE sys_file;

CREATE TABLE sys_file (
                                 id varchar(32) DEFAULT nextval('sys_file_id_seq'::regclass) NOT NULL, -- id
                                 file_nm varchar(128) NOT NULL, -- 文件名称
                                 file_size int4 NOT NULL, -- 文件大小
                                 file_typ varchar(8) NOT NULL, -- 文件类型
                                 file_path varchar(255) NOT NULL, -- 文件路径
                                 triple_no varchar(50) DEFAULT NULL::character varying NULL, -- 影像平台编号
                                 bucket varchar(64) NOT NULL, -- 桶名
                                 obj_nm varchar(128) NOT NULL, -- 对象名
                                 strg_typ varchar(2) DEFAULT NULL::character varying NULL, -- 存储类型$$$1:MinIO,2:NAS
                                 is_biz int2 DEFAULT 1 NOT NULL, -- 是否关联业务
                                 cre_per varchar(64) DEFAULT NULL::character varying NULL, -- 创建人
                                 cre_tm timestamp NOT NULL, -- 创建时间
                                 updt_per varchar(64) DEFAULT NULL::character varying NULL, -- 更新人
                                 updt_tm timestamp NOT NULL, -- 更新时间
                                 CONSTRAINT sys_file_pkey PRIMARY KEY (id)
);
COMMENT ON TABLE sys_file IS '文件信息';

-- Column comments

COMMENT ON COLUMN sys_file.id IS 'id';
COMMENT ON COLUMN sys_file.file_nm IS '文件名称';
COMMENT ON COLUMN sys_file.file_size IS '文件大小';
COMMENT ON COLUMN sys_file.file_typ IS '文件类型';
COMMENT ON COLUMN sys_file.file_path IS '文件路径';
COMMENT ON COLUMN sys_file.triple_no IS '影像平台编号';
COMMENT ON COLUMN sys_file.bucket IS '桶名';
COMMENT ON COLUMN sys_file.obj_nm IS '对象名';
COMMENT ON COLUMN sys_file.strg_typ IS '存储类型$$$1:MinIO,2:NAS';
COMMENT ON COLUMN sys_file.is_biz IS '是否关联业务';
COMMENT ON COLUMN sys_file.cre_per IS '创建人';
COMMENT ON COLUMN sys_file.cre_tm IS '创建时间';
COMMENT ON COLUMN sys_file.updt_per IS '更新人';
COMMENT ON COLUMN sys_file.updt_tm IS '更新时间';


-- sys_menu definition

-- Drop table

-- DROP TABLE sys_menu;

CREATE TABLE sys_menu (
                                 menu_id varchar(32) NOT NULL, -- 菜单ID
                                 menu_nm varchar(64) NOT NULL, -- 菜单名称
                                 perm_cd varchar(32) DEFAULT NULL::character varying NULL, -- 权限码
                                 "path" varchar(128) DEFAULT NULL::character varying NULL, -- 路径
                                 prent_id varchar(36) NOT NULL, -- 父菜单ID
                                 sort int4 NOT NULL, -- 排序
                                 icon varchar(64) DEFAULT NULL::character varying NULL, -- 图标
                                 typ varchar(2) NOT NULL, -- 类型;按钮、菜单
                                 is_dsp int2 DEFAULT 1 NOT NULL, -- 是否显示
                                 cre_per varchar(64) DEFAULT NULL::character varying NULL, -- 创建人
                                 cre_tm timestamp NOT NULL, -- 创建时间
                                 updt_per varchar(64) DEFAULT NULL::character varying NULL, -- 更新人
                                 updt_tm timestamp NOT NULL, -- 更新时间
                                 menu_source varchar(30) DEFAULT NULL::character varying NULL, -- 菜单来源
                                 CONSTRAINT user_menu_pkey PRIMARY KEY (menu_id)
);
COMMENT ON TABLE sys_menu IS '银行用户菜单权限';

-- Column comments

COMMENT ON COLUMN sys_menu.menu_id IS '菜单ID';
COMMENT ON COLUMN sys_menu.menu_nm IS '菜单名称';
COMMENT ON COLUMN sys_menu.perm_cd IS '权限码';
COMMENT ON COLUMN sys_menu."path" IS '路径';
COMMENT ON COLUMN sys_menu.prent_id IS '父菜单ID';
COMMENT ON COLUMN sys_menu.sort IS '排序';
COMMENT ON COLUMN sys_menu.icon IS '图标';
COMMENT ON COLUMN sys_menu.typ IS '类型;按钮、菜单';
COMMENT ON COLUMN sys_menu.is_dsp IS '是否显示';
COMMENT ON COLUMN sys_menu.cre_per IS '创建人';
COMMENT ON COLUMN sys_menu.cre_tm IS '创建时间';
COMMENT ON COLUMN sys_menu.updt_per IS '更新人';
COMMENT ON COLUMN sys_menu.updt_tm IS '更新时间';
COMMENT ON COLUMN sys_menu.menu_source IS '菜单来源';


-- sys_org definition

-- Drop table

-- DROP TABLE sys_org;

CREATE TABLE sys_org (
                                org_id varchar(32) NOT NULL, -- 机构编号
                                org_cd varchar(32) DEFAULT NULL::character varying NULL, -- 机构代码,后台自动生成，四位数字代表一个层级，每个层级0001开始，层级内容依次累加，同一层级数值累加，例：000100010001三级网点，以便于根据上级机构查询下级
                                org_nm varchar(64) NOT NULL, -- 机构名称
                                org_prent_id varchar(32) NOT NULL, -- 上级机构编码
                                org_lv_cd varchar(8) NOT NULL, -- 机构级别代码
                                org_addr varchar(512) DEFAULT NULL::character varying NULL, -- 机构地址
                                tel_no varchar(16) DEFAULT NULL::character varying NULL, -- 联系电话;固定电话格式
                                ctct_per varchar(64) DEFAULT NULL::character varying NULL, -- 联系人
                                org_stus varchar(8) NOT NULL, -- 机构状态
                                org_desc varchar(255) DEFAULT NULL::character varying NULL, -- 机构描述
                                org_flg int2 NULL, -- 机构标志
                                corp_id varchar(32) DEFAULT NULL::character varying NULL, -- 法人编码
                                clrg_org_id varchar(32) DEFAULT NULL::character varying NULL, -- 清算机构编号;对应行内清算机构
                                safe_sign_org_id varchar(64) DEFAULT NULL::character varying NULL, -- 安心签机构号
                                rmk varchar(255) DEFAULT NULL::character varying NULL, -- 备注字段
                                cre_per varchar(64) DEFAULT NULL::character varying NULL, -- 创建人
                                cre_tm timestamp NOT NULL, -- 创建时间
                                updt_per varchar(64) DEFAULT NULL::character varying NULL, -- 更新人
                                updt_tm timestamp NOT NULL, -- 更新时间
                                clrg_line_cd varchar(64) DEFAULT NULL::character varying NULL, -- 清算行号
                                fee_org_id varchar(64) DEFAULT NULL::character varying NULL, -- 手续费账户机构
                                fund_org_id varchar(64) DEFAULT NULL::character varying NULL, -- 资金账户机构
                                extras varchar(255) DEFAULT NULL::character varying NULL, -- 扩展信息
                                CONSTRAINT sys_org_pkey PRIMARY KEY (org_id)
);
COMMENT ON TABLE sys_org IS '机构信息表';

-- Column comments

COMMENT ON COLUMN sys_org.org_id IS '机构编号';
COMMENT ON COLUMN sys_org.org_cd IS '机构代码,后台自动生成，四位数字代表一个层级，每个层级0001开始，层级内容依次累加，同一层级数值累加，例：000100010001三级网点，以便于根据上级机构查询下级';
COMMENT ON COLUMN sys_org.org_nm IS '机构名称';
COMMENT ON COLUMN sys_org.org_prent_id IS '上级机构编码';
COMMENT ON COLUMN sys_org.org_lv_cd IS '机构级别代码';
COMMENT ON COLUMN sys_org.org_addr IS '机构地址';
COMMENT ON COLUMN sys_org.tel_no IS '联系电话;固定电话格式';
COMMENT ON COLUMN sys_org.ctct_per IS '联系人';
COMMENT ON COLUMN sys_org.org_stus IS '机构状态';
COMMENT ON COLUMN sys_org.org_desc IS '机构描述';
COMMENT ON COLUMN sys_org.org_flg IS '机构标志';
COMMENT ON COLUMN sys_org.corp_id IS '法人编码';
COMMENT ON COLUMN sys_org.clrg_org_id IS '清算机构编号;对应行内清算机构';
COMMENT ON COLUMN sys_org.safe_sign_org_id IS '安心签机构号';
COMMENT ON COLUMN sys_org.rmk IS '备注字段';
COMMENT ON COLUMN sys_org.cre_per IS '创建人';
COMMENT ON COLUMN sys_org.cre_tm IS '创建时间';
COMMENT ON COLUMN sys_org.updt_per IS '更新人';
COMMENT ON COLUMN sys_org.updt_tm IS '更新时间';
COMMENT ON COLUMN sys_org.clrg_line_cd IS '清算行号';
COMMENT ON COLUMN sys_org.fee_org_id IS '手续费账户机构';
COMMENT ON COLUMN sys_org.fund_org_id IS '资金账户机构';
COMMENT ON COLUMN sys_org.extras IS '扩展信息';


-- sys_rol definition

-- Drop table

-- DROP TABLE sys_rol;

CREATE TABLE sys_rol (
                                rol_id varchar(32) NOT NULL, -- 角色id
                                rol_nm varchar(64) NOT NULL, -- 角色名
                                rol_cd varchar(8) DEFAULT NULL::character varying NULL, -- 角色代码;定义后用作特殊识别使用
                                rol_lv varchar(8) DEFAULT NULL::character varying NULL, -- 角色级别
                                rol_desc varchar(255) DEFAULT NULL::character varying NULL, -- 角色描述
                                org_id varchar(32) NOT NULL, -- 机构编号;为ALL时为通用角色，不可被修改
                                is_pub int2 DEFAULT 0 NOT NULL, -- 是否公共
                                cre_per varchar(64) DEFAULT NULL::character varying NULL, -- 创建人
                                cre_tm timestamp NOT NULL, -- 创建时间
                                updt_per varchar(64) DEFAULT NULL::character varying NULL, -- 更新人
                                updt_tm timestamp NOT NULL, -- 更新时间
                                CONSTRAINT user_rol_pkey PRIMARY KEY (rol_id)
);
COMMENT ON TABLE sys_rol IS '银行用户角色表';

-- Column comments

COMMENT ON COLUMN sys_rol.rol_id IS '角色id';
COMMENT ON COLUMN sys_rol.rol_nm IS '角色名';
COMMENT ON COLUMN sys_rol.rol_cd IS '角色代码;定义后用作特殊识别使用';
COMMENT ON COLUMN sys_rol.rol_lv IS '角色级别';
COMMENT ON COLUMN sys_rol.rol_desc IS '角色描述';
COMMENT ON COLUMN sys_rol.org_id IS '机构编号;为ALL时为通用角色，不可被修改';
COMMENT ON COLUMN sys_rol.is_pub IS '是否公共';
COMMENT ON COLUMN sys_rol.cre_per IS '创建人';
COMMENT ON COLUMN sys_rol.cre_tm IS '创建时间';
COMMENT ON COLUMN sys_rol.updt_per IS '更新人';
COMMENT ON COLUMN sys_rol.updt_tm IS '更新时间';


-- sys_rol_menu definition

-- Drop table

-- DROP TABLE sys_rol_menu;

CREATE TABLE sys_rol_menu (
                                     rol_id varchar(32) NULL, -- 角色id
                                     menu_id varchar(32) NULL, -- 菜单id
                                     CONSTRAINT sys_rol_menu_unique UNIQUE (rol_id, menu_id)
);
COMMENT ON TABLE sys_rol_menu IS '角色菜单中间表';

-- Column comments

COMMENT ON COLUMN sys_rol_menu.rol_id IS '角色id';
COMMENT ON COLUMN sys_rol_menu.menu_id IS '菜单id';


-- sys_tsk definition

-- Drop table

-- DROP TABLE sys_tsk;

CREATE TABLE sys_tsk (
                                tsk_id varchar(32) NOT NULL, -- id
                                tsk_typ varchar(50) NOT NULL, -- 任务类型
                                tsk_nm varchar(64) NOT NULL, -- 任务名
                                tsk_tbl varchar(10) NOT NULL, -- 任务表名
                                tsk_dt varchar(10) NOT NULL, -- 任务日期
                                stus varchar(2) NOT NULL, -- 状态
                                cre_per varchar(64) DEFAULT NULL::character varying NULL, -- 创建人
                                cre_tm timestamp NOT NULL, -- 创建时间
                                updt_per varchar(64) DEFAULT NULL::character varying NULL, -- 更新人
                                updt_tm timestamp NOT NULL, -- 更新时间
                                CONSTRAINT sys_tsk_pkey PRIMARY KEY (tsk_id)
);
COMMENT ON TABLE sys_tsk IS '系统任务';

-- Column comments

COMMENT ON COLUMN sys_tsk.tsk_id IS 'id';
COMMENT ON COLUMN sys_tsk.tsk_typ IS '任务类型';
COMMENT ON COLUMN sys_tsk.tsk_nm IS '任务名';
COMMENT ON COLUMN sys_tsk.tsk_tbl IS '任务表名';
COMMENT ON COLUMN sys_tsk.tsk_dt IS '任务日期';
COMMENT ON COLUMN sys_tsk.stus IS '状态';
COMMENT ON COLUMN sys_tsk.cre_per IS '创建人';
COMMENT ON COLUMN sys_tsk.cre_tm IS '创建时间';
COMMENT ON COLUMN sys_tsk.updt_per IS '更新人';
COMMENT ON COLUMN sys_tsk.updt_tm IS '更新时间';


-- sys_user definition

-- Drop table

-- DROP TABLE sys_user;

CREATE TABLE sys_user (
                                 user_id varchar(32) NOT NULL, -- 用户id
                                 user_nm varchar(64) NOT NULL, -- 用户名
                                 rmk varchar(255) DEFAULT NULL::character varying NULL, -- 备注
                                 pwd varchar(64) NULL, -- 用户密码
                                 id_typ varchar(2) DEFAULT '1'::character varying NULL, -- 证件类型
                                 id_no varchar(32) DEFAULT NULL::character varying NULL, -- 证件号
                                 real_nm varchar(64) DEFAULT NULL::character varying NULL, -- 真实姓名
                                 tel varchar(16) DEFAULT NULL::character varying NULL, -- 联系电话
                                 org_cd varchar(32) NULL, -- 所属机构编码
                                 stus varchar(2) DEFAULT '1'::character varying NOT NULL, -- 用户状态
                                 pwd_err_tms int2 DEFAULT 0 NOT NULL, -- 密码错误次数
                                 pwd_chg_tm timestamp NULL, -- 密码修改时间
                                 lst_lgn_tm timestamp NULL, -- 最近登录时间
                                 lock_tm timestamp NULL, -- 锁定时间
                                 icon varchar NULL, -- 头像图标
                                 cre_per varchar(64) DEFAULT NULL::character varying NULL, -- 创建人
                                 cre_tm timestamp NOT NULL, -- 创建时间
                                 updt_per varchar(64) DEFAULT NULL::character varying NULL, -- 更新人
                                 updt_tm timestamp NOT NULL, -- 更新时间
                                 CONSTRAINT sys_user_pkey PRIMARY KEY (user_id)
);
COMMENT ON TABLE sys_user IS '银行用户表';

-- Column comments

COMMENT ON COLUMN sys_user.user_id IS '用户id';
COMMENT ON COLUMN sys_user.user_nm IS '用户名';
COMMENT ON COLUMN sys_user.rmk IS '备注';
COMMENT ON COLUMN sys_user.pwd IS '用户密码';
COMMENT ON COLUMN sys_user.id_typ IS '证件类型';
COMMENT ON COLUMN sys_user.id_no IS '证件号';
COMMENT ON COLUMN sys_user.real_nm IS '真实姓名';
COMMENT ON COLUMN sys_user.tel IS '联系电话';
COMMENT ON COLUMN sys_user.org_cd IS '所属机构编码';
COMMENT ON COLUMN sys_user.stus IS '用户状态';
COMMENT ON COLUMN sys_user.pwd_err_tms IS '密码错误次数';
COMMENT ON COLUMN sys_user.pwd_chg_tm IS '密码修改时间';
COMMENT ON COLUMN sys_user.lst_lgn_tm IS '最近登录时间';
COMMENT ON COLUMN sys_user.lock_tm IS '锁定时间';
COMMENT ON COLUMN sys_user.icon IS '头像图标';
COMMENT ON COLUMN sys_user.cre_per IS '创建人';
COMMENT ON COLUMN sys_user.cre_tm IS '创建时间';
COMMENT ON COLUMN sys_user.updt_per IS '更新人';
COMMENT ON COLUMN sys_user.updt_tm IS '更新时间';


-- sys_user_rol definition

-- Drop table

-- DROP TABLE sys_user_rol;

CREATE TABLE sys_user_rol (
                                     user_id varchar(32) NULL, -- 用户id
                                     rol_id varchar(32) NULL, -- 角色id
                                     CONSTRAINT sys_user_rol_unique UNIQUE (user_id, rol_id)
);
COMMENT ON TABLE sys_user_rol IS '用户角色表';

-- Column comments

COMMENT ON COLUMN sys_user_rol.user_id IS '用户id';
COMMENT ON COLUMN sys_user_rol.rol_id IS '角色id';