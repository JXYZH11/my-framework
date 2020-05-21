package com.jxyzh11.myframework.toolkit.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author sunny
 * @date 2018/9/27
 */
public class PageUtil {

    public static <T> List<T> getPageDataOfList(List<T> data, int pageNo, int pageSize) {
        if (CollectionUtils.isEmpty(data)) {
            return Lists.newArrayList();
        }
        int startPos = (pageNo - 1) * pageSize;
        int endPos = startPos + pageSize;
        int length = data.size();
        if (startPos > length) {
            return Lists.newArrayList();
        }
        if (endPos > length) {
            endPos = length;
        }
        return data.subList(startPos, endPos);
    }

    public static int getTotalPage(int totalCount, int pageSize) {
        if (totalCount == 0) {
            return 0;
        }
        return totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
    }
}
