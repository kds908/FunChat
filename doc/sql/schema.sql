-- 用户信息
create table im_user(
    `uid` varchar(13) primary key not null comment '用户id',
    `username` varchar(20) not null comment '用户登录名',
    `nickname` varchar(10) not null comment '用户昵称',
    `email` varchar(30) default null comment '邮箱',
    `gender` tinyint(1) default null comment '性别 1-男 2-女',
    `password` varchar(255) not null comment '密码',
    `join_type` tinyint(1) default 1 comment '添加方式 0-直接添加，1-同意后添加',
    `personal_signature` varchar(255) default null comment '个性签名',
    `avatar` varchar(255) null comment '头像',
    `status` tinyint(1) default 1 comment '账号状态 0-封禁 1-正常',
    `create_time` datetime default null comment '创建时间',
    `last_login_time` datetime default null comment '最后登录时间',
    `last_off_time` bigint default null comment '最后离线时间',
    `area_name` varchar(32) default null comment '所属地区名称',
    `area_code` varchar(10) default null comment '所属地区编号',
    unique key `un_idx_key_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment='用户信息';

-- 用户靓号
create table `im_user_beauty` (
    `id` bigint not null auto_increment primary key comment '自增ID',
    `uid` varchar(12) not null comment '用户id',
    `username` varchar(20) not null comment '用户名',
    `status` tinyint(1) default 0 comment '0-未使用 1-已使用',
    unique key `un_idx_user_beauty_uid`(`uid`),
    unique key `un_idx_user_beauty_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment='用户靓号';

create table `im_group` (
    `group_id` varchar(12) not null comment '群组ID',
    `group_name` varchar(20) not null comment '群组名称',
    `group_owner_id` varchar(13) not null comment '群主ID',
    `create_time` datetime comment '创建时间',
    `group_notice` varchar(500) comment '群公告',
    `join_type` tinyint(1) comment '0-直接加入 1-管理员同意后加入',
    `status` tinyint default 1 comment '状态 0-解散 1-正常',
    primary key (`group_id`)
) engine = InnoDB character set = utf8mb4 row_format = dynamic;

create table `im_contact` (
    `user_id`          varchar(13) not null comment '用户ID',
    `contact_id`       varchar(13) not null comment '联系人/群组 ID',
    `contact_type`     tinyint(1)  null comment '联系人类型 0-好友 1-群组',
    `create_time`      datetime    null comment '创建时间',
    `status`           tinyint(1)  null comment '账号状态0-陌生人 1-好友 2-已删除 3-被删除 4-已拉黑 5-被拉黑',
    `last_update_time` timestamp   null comment '最后更新时间',
    primary key (`user_id`, `contact_id`),
    key idx_contact_id (`contact_id`)
) engine = InnoDB character set = utf8mb4 comment '联系人';

create table `im_contact_apply` (
    `apply_id`          bigint primary key auto_increment not null comment '申请ID',
    `apply_user_id`       varchar(13) not null comment '申请人 ID',
    `receive_user_id`       varchar(13) not null comment '接收人 ID',
    `contact_type`     tinyint(1) not null comment '联系人类型 0-好友 1-群组',
    `contact_id` varchar(13) comment '联系人/群组ID',
    `last_apply_time`      bigint    null comment '最后申请时间',
    `status`           tinyint(1)  null comment '状态0-待处理 1-已同意 2-已拒绝 3-已拉黑',
    `apply_info` varchar(100)   null comment '申请信息'
) engine = InnoDB character set = utf8mb4 comment '联系人申请';