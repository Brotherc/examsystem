package cn.examsystem.common.utils;

import org.json.JSONObject;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/2/2.
 */
public class UrlUtils {
    public static String expandURL(String url, JSONObject obj) {
        final Pattern QUERY_PARAM_PATTERN = Pattern.compile("([^&=]+)(=?)([^&]+)?");
        Matcher mc = QUERY_PARAM_PATTERN.matcher(url);
        StringBuilder sb = new StringBuilder(url);
        if (mc.find()) {
            sb.append("&");
        } else {
            sb.append("?");
        }

        Set<String> keys = obj.keySet();
        for (Object key : keys) {
            sb.append(key).append("=").append(obj.get((String)key).toString()).append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
