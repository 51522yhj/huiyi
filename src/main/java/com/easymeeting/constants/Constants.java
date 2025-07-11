package com.easymeeting.constants;

public class Constants {
    public static final String ZERO_STR ="0";
    public static final Integer ZERO =0;
    public static final Integer ONE =1;
    public static final Integer LENGTH_10 = 10;
    public static final Integer LENGTH_20 = 20;
    public static final Integer LENGTH_12 = 12;
    public static final Integer LENGTH_30 = 30;
    public static final String FILE_FOLDER_FILE = "file/";
    public static final String FILE_FOLDER_TEMP = "/temp/";
    public static final String FILE_FOLDER_AVATAR_NAME = "avatar/";
    public static final String IMAGE_SUFFIX = ".jpg";
    public static final String VIDEO_SUFFIX = ".mp4";
    public static final String DEFAULT_AVATAR = "/user.png";
    public static final String PING = "ping";
    /**
     * redis key相关
     */
    public static final Integer REDIS_KEY_EXPIRES_ONE_MIN = 60;
    public static final Integer REDIS_KEY_EXPIRES_ONE_DAY = 60*60*24;
    private static final String REDIS_KEY_PREFIX = "easymeeting:";
    public static final String REDIS_KEY_CHECK_CODE = REDIS_KEY_PREFIX + "checkcode:";
    public static final String REDIS_KEY_WS_TOKEN = REDIS_KEY_PREFIX + "ws:token:";
    public static final String REDIS_KEY_WS_TOEKN_USERID = REDIS_KEY_PREFIX + "ws:token:userid";
    public static final String REDIS_KEY_WS_USER_HEART_BEAT = REDIS_KEY_PREFIX + "ws:user:heartbeat";
    public static final String REDIS_KEY_MEETING_ROOM = REDIS_KEY_PREFIX + "meeting:room:";
    public static final String REDIS_KEY_INVITE_MEMBER = REDIS_KEY_PREFIX + "meeting:invite:member:";
    public static final String REDIS_KEY_SYS_SETTING = REDIS_KEY_PREFIX + "sysSetting:";
    public static final String MEETING_NO_PRIFIX = "M";
    public static final String IMAGE_THUMBNAIL_SUFFIX = "_thumbnail";
    public static final String VIDEO_CODE_HEVC = "hevc";
    public static final String APP_UPDATE_FOLDER = "/app/";
    public static final String APP_NAME = "EasyMeetingSetup";
    public static final String APP_EXE_SUFFIX = ".exe";
    public static final String MESSAGEING_HANDLE_CHANNEL_KEY = "messaging.handle.channel";
    public static final String MESSAGEING_HANDLE_CHANNEL_REDIS = "redis";
    public static final String MESSAGEING_HANDLE_CHANNEL_RABBITMQ = "rabbitmq";
}
