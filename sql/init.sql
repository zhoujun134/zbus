use zbus;
create table user_info
(
    id          int auto_increment comment 'id 主键'
        primary key,
    email     varchar(256) default ''               not null comment '邮箱',
    name        varchar(64) collate utf8mb4_unicode_ci null comment '菜单名称',
    user_name   varchar(128) default ''               not null comment '用户名',
    password      varchar(256) default ''               not null comment '密码',

    create_id   varchar(128)          default -1                null comment '创建人',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_id   varchar(128)          default -1                null comment '更新人',
    update_time datetime     default CURRENT_TIMESTAMP not null comment '更新时间',
    is_deleted  tinyint      default 0                 null comment '是否删除(0 未删除 1 已删除)'
);
