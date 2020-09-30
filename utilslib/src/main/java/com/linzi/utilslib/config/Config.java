package com.linzi.utilslib.config;

/**
 * @author linzi
 * @date 2020/4/13
 */
public interface Config {
    public String DB_NAME="LINZI.db";
    public long FRESH_NET_TIME=10;//服务器连接状态监听时间（单位s）
    public int NOTICE_TIME=30;//提示用户的时间（单位s）
    public String HOST_CONFIG="host_config";
    public String TOKEN="token";
    public String USERNAME="username";
    public String UID="uid";
}
