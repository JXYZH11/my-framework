package com.jxyzh11.myframework.toolkit;

import org.apache.commons.lang3.StringUtils;

/**
 * 版本工具类
 */
public class VersionUtil {

    private static final String APP_VERSION_PATTERN = "^\\d+(\\.\\d+)*$";

    /**
     * 比较当前app版本相对于目标版本是否是老版本
     *
     * @param currentVersion 当前版本
     * @param targetVersion  目标版本
     * @return 当前版本为空，默认老版本，true老版本
     * @author sunny
     * @date 2018/10/8
     */
    public static boolean isAppNewVersion(String currentVersion, String targetVersion) {
        if (currentVersion == null) {
            return true;
        }
        return currentVersion.compareTo(targetVersion) > 0;
    }

    /**
     * 比较app的版本号大小
     *
     * @param currentVersion 当前版本
     * @param targetVersion  目标版本
     * @return current>=target:true,反之false
     */
    public static boolean isCurrentNewVersion(String currentVersion, String targetVersion) {
        if (StringUtils.isBlank(currentVersion)) {
            return false;
        }
        if (StringUtils.isBlank(targetVersion)) {
            return true;
        }

        String[] current = currentVersion.split("\\.");
        String[] target = targetVersion.split("\\.");
        int idx = 0;

        //取最小长度值
        int minLength = Math.min(current.length, target.length);
        int diff = 0;
        //先比较长度再比较字符
        while (idx < minLength
                && (diff = current[idx].length() - target[idx].length()) == 0
                && (diff = current[idx].compareTo(target[idx])) == 0) {
            ++idx;
        }

        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : current.length - target.length;
        return diff >= 0;
    }

    /**
     * 校验app版本号是否符合格式
     *
     * @param version
     * @return
     */
    public static boolean isAppVersionLegal(String version) {
        if (version == null) {
            return false;
        }
        return version.matches(APP_VERSION_PATTERN);
    }
}
