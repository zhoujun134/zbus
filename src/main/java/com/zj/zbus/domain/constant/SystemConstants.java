package com.zj.zbus.domain.constant;

public class SystemConstants {
    /**
     * 文章是正常发布状态
     */
    public static final String ARTICLE_STATUS_NORMAL = "0";

    /**
     * 文章是草稿状态
     */
    public static final String ARTICLE_STATUS_Draft = "1";

    /**
     * 目录是正常发布状态
     */
    public static final String Category_STATUS_NORMAL = "0";

    /**
     * 友链是审核通过状态
     */
    public static final String LINK_STATUS_PASS = "0";

    /**
     * 根节点评论
     */
    public static final Long COMMENT_ROOT = -1L;

    /**
     * Redis 用户 id 的前奏
     */
    public static final String REDIS_USER_ID_PREFIX = "zBusLogin:";

    /**
     * Redis 用户 登录的 token key 前缀
     */
    public static final String REDIS_USER_LOGIN_TOKEN_PREFIX = "zBus-token";

    /**
     * 用户类型为管理员
     */
    public static final String ADMIN_USER = "1";
}
