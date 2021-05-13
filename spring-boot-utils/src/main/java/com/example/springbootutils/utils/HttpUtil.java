package com.example.springbootutils.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * post、delete、put请求，json、form-data格式
 */
public class HttpUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String PATCH = "PATCH";
    public static final String CODE = "code";
    public static final String MSG = "msg";
    public static final String DATA = "data";
    private static final String UTF8 = "UTF-8";
    private static final String GBK = "GBK";
    public static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DEFAULT_METHOD = "GET";
    private static final String DEFAULT_MEDIA_TYPE;
    private static final boolean DEFAULT_LOG = false;
    private static final OkHttpClient CLIENT;

    public HttpUtil() {
    }

    public static String get(String url) {
        return execute(OkHttp.builder().url(url).build());
    }

    public static String get(String url, String charset) {
        return execute(OkHttp.builder().url(url).responseCharset(charset).build());
    }

    public static String get(String url, Map<String, String> queryMap) {
        return execute(OkHttp.builder().url(url).queryMap(queryMap).build());
    }

    public static String get(String url, Map<String, String> headerMap, Map<String, String> queryMap) {
        return execute(OkHttp.builder().url(url).queryMap(queryMap).headerMap(headerMap).build());
    }

    public static String get(String url, Map<String, String> queryMap, String charset) {
        return execute(OkHttp.builder().url(url).queryMap(queryMap).responseCharset(charset).build());
    }

    public static String postJson(String url, Object obj) {
        return execute(OkHttp.builder().url(url).method("POST").data(JSON.toJSONString(obj)).mediaType(ContentType.APPLICATION_JSON.getMimeType()).build());
    }

    public static String postForm(String url, Map<String, String> formMap) {
        String data = "";
        if (!CollectionUtils.isEmpty(formMap)) {
            data = (String)formMap.entrySet().stream().map((entry) -> {
                return String.format("%s=%s", entry.getKey(), entry.getValue());
            }).collect(Collectors.joining("&"));
        }

        return execute(OkHttp.builder().url(url).method("POST").data(data).mediaType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType()).build());
    }

    public static String postForm(String url, Map<String, String> headerMap, Map<String, Object> formMap) {
        String data = "";
        if (!CollectionUtils.isEmpty(formMap)) {
            data = (String)formMap.entrySet().stream().map((entry) -> {
                return String.format("%s=%s", entry.getKey(), entry.getValue());
            }).collect(Collectors.joining("&"));
        }

        return execute(OkHttp.builder().url(url).method("POST").data(data).mediaType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType()).headerMap(headerMap).build());
    }

    public static String postForm(String url, Map<String, String> headerMap, Map<String, Object> formMap, String contentType) {
        String data = "";
        if (!CollectionUtils.isEmpty(formMap)) {
            data = (String)formMap.entrySet().stream().map((entry) -> {
                return String.format("%s=%s", entry.getKey(), entry.getValue());
            }).collect(Collectors.joining("&"));
        }

        return execute(OkHttp.builder().url(url).method("POST").data(data).mediaType(contentType).headerMap(headerMap).build());
    }

    public static String postJson(String url, Map<String, String> headerMap, String data, String mediaType, String charset) {
        return execute(OkHttp.builder().url(url).method("POST").data(data).mediaType(mediaType).responseCharset(charset).headerMap(headerMap).build());
    }

    public static String putJson(String url, Map<String, String> headerMap, String data, String mediaType, String charset) {
        return execute(OkHttp.builder().url(url).method("PUT").data(data).mediaType(mediaType).responseCharset(charset).headerMap(headerMap).build());
    }

    public static String delete(String url, Map<String, String> headerMap, String data, String mediaType, String charset) {
        return execute(OkHttp.builder().url(url).method("DELETE").data(data).mediaType(mediaType).responseCharset(charset).headerMap(headerMap).build());
    }

    private static String post(String url, String data, String mediaType, String charset) {
        return execute(OkHttp.builder().url(url).method("POST").data(data).mediaType(mediaType).responseCharset(charset).build());
    }

    private static String execute(OkHttp okHttp) {
        if (StringUtils.isBlank(okHttp.requestCharset)) {
            okHttp.requestCharset = "UTF-8";
        }

        if (StringUtils.isBlank(okHttp.responseCharset)) {
            okHttp.responseCharset = "UTF-8";
        }

        if (StringUtils.isBlank(okHttp.method)) {
            okHttp.method = "GET";
        }

        if (StringUtils.isBlank(okHttp.mediaType)) {
            okHttp.mediaType = DEFAULT_MEDIA_TYPE;
        }

        if (okHttp.requestLog) {
            log.info(okHttp.toString());
        }

        String url = okHttp.url;
        Request.Builder builder = new Request.Builder();
        String method;
        if (!CollectionUtils.isEmpty(okHttp.queryMap)) {
            method = (String)okHttp.queryMap.entrySet().stream().map((entry) -> {
                return String.format("%s=%s", entry.getKey(), URLEncoder.encode((String)entry.getValue()));
            }).collect(Collectors.joining("&"));
            url = String.format("%s%s%s", url, url.contains("?") ? "&" : "?", method);
            System.out.println(url);
        }

        builder.url(url);
        if (!CollectionUtils.isEmpty(okHttp.headerMap)) {
            okHttp.headerMap.forEach(builder::addHeader);
        }

        method = okHttp.method.toUpperCase();
        String mediaType = String.format("%s;charset=%s", okHttp.mediaType, okHttp.requestCharset);
        if (StringUtils.equals(method, "GET")) {
            builder.get();
        } else if (ArrayUtils.contains(new String[]{"POST", "PUT", "DELETE", "PATCH"}, method)) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType), okHttp.data);
            builder.method(method, requestBody);
        } else {
            try {
                throw new Exception(String.format("http method:%s not support!", method));
            } catch (Exception var9) {
                var9.printStackTrace();
            }
        }

        String result = "";

        try {
            Response response = CLIENT.newCall(builder.build()).execute();
            byte[] bytes = response.body().bytes();
            result = new String(bytes, okHttp.responseCharset);
            if (okHttp.responseLog) {
                log.info(String.format("Got response->%s", result));
            }
        } catch (Exception var8) {
            log.error(okHttp.toString(), var8);
        }

        return result;
    }

    public static String getWithCookie(String url, Map<String, String> queryMap, String cookie) {
        Map<String, String> header = new HashMap(16);
        header.put("cookie", cookie);
        return execute(OkHttp.builder().url(url).headerMap(header).queryMap(queryMap).build());
    }

    public static String postJsonWithCookie(String url, Object obj, String cookie) {
        Map<String, String> header = new HashMap(16);
        header.put("cookie", cookie);
        return execute(OkHttp.builder().url(url).headerMap(header).method("POST").data(JSON.toJSONString(obj)).mediaType(ContentType.APPLICATION_JSON.getMimeType()).build());
    }

    public static String postFormDataWithCookie(String url, Map<String, Object> param, Map<String, File> files, String cookie) {
        Map<String, String> header = new HashMap(16);
        header.put("cookie", cookie);
        return executeFormData(OkHttp.builder().url(url).method("POST").headerMap(header).files(files).formMap(param).mediaType(ContentType.MULTIPART_FORM_DATA.getMimeType()).build());
    }

    public static String postFormDataWithCookieNew(String url, Map<String, Object> param, Map<String, List<File>> fileList, String cookie) {
        Map<String, String> header = new HashMap(16);
        header.put("cookie", cookie);
        return executeFormData(OkHttp.builder().url(url).method("POST").headerMap(header).fileList(fileList).formMap(param).mediaType(ContentType.MULTIPART_FORM_DATA.getMimeType()).build());
    }

    public static String putJsonWithCookie(String url, Object obj, String cookie) {
        Map<String, String> header = new HashMap(16);
        header.put("cookie", cookie);
        return execute(OkHttp.builder().url(url).headerMap(header).method("PUT").data(JSON.toJSONString(obj)).mediaType(ContentType.APPLICATION_JSON.getMimeType()).build());
    }

    public static String putFormDataWithCookie(String url, Map<String, Object> param, Map<String, File> files, String cookie) {
        Map<String, String> header = new HashMap(16);
        header.put("cookie", cookie);
        return executeFormData(OkHttp.builder().url(url).method("PUT").headerMap(header).files(files).formMap(param).mediaType(ContentType.MULTIPART_FORM_DATA.getMimeType()).build());
    }

    public static String putFormDataWithCookieNew(String url, Map<String, Object> param, Map<String, List<File>> fileList, String cookie) {
        Map<String, String> header = new HashMap(16);
        header.put("cookie", cookie);
        return executeFormData(OkHttp.builder().url(url).method("PUT").headerMap(header).fileList(fileList).formMap(param).mediaType(ContentType.MULTIPART_FORM_DATA.getMimeType()).build());
    }

    public static String deleteJsonWithCookie(String url, Object obj, String cookie) {
        Map<String, String> header = new HashMap(16);
        header.put("cookie", cookie);
        return execute(OkHttp.builder().url(url).headerMap(header).method("DELETE").data(JSON.toJSONString(obj)).mediaType(ContentType.APPLICATION_JSON.getMimeType()).build());
    }

    public static String getWithToken(String url, Map<String, String> queryMap, String token) {
        Map<String, String> header = new HashMap(16);
        header.put("Authorization", token);
        return execute(OkHttp.builder().url(url).headerMap(header).queryMap(queryMap).build());
    }

    public static String postJsonWithToken(String url, Object obj, String token) {
        Map<String, String> header = new HashMap(16);
        header.put("Authorization", token);
        return execute(OkHttp.builder().url(url).headerMap(header).method("POST").data(JSON.toJSONString(obj)).mediaType(ContentType.APPLICATION_JSON.getMimeType()).build());
    }

    public static String putJsonWithToken(String url, Object obj, String token) {
        Map<String, String> header = new HashMap(16);
        header.put("Authorization", token);
        return execute(OkHttp.builder().url(url).headerMap(header).method("PUT").data(JSON.toJSONString(obj)).mediaType(ContentType.APPLICATION_JSON.getMimeType()).build());
    }

    public static String deleteWithToken(String url, Object obj, String token) {
        Map<String, String> header = new HashMap(16);
        header.put("Authorization", token);
        return execute(OkHttp.builder().url(url).headerMap(header).method("DELETE").data(JSON.toJSONString(obj)).build());
    }

    private static String executeFormData(OkHttp okHttp) {
        if (StringUtils.isBlank(okHttp.requestCharset)) {
            okHttp.requestCharset = "UTF-8";
        }

        if (StringUtils.isBlank(okHttp.responseCharset)) {
            okHttp.responseCharset = "UTF-8";
        }

        if (StringUtils.isBlank(okHttp.method)) {
            okHttp.method = "GET";
        }

        if (StringUtils.isBlank(okHttp.mediaType)) {
            okHttp.mediaType = DEFAULT_MEDIA_TYPE;
        }

        if (okHttp.requestLog) {
            log.info(okHttp.toString());
        }

        String url = okHttp.url;
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (!CollectionUtils.isEmpty(okHttp.headerMap)) {
            okHttp.headerMap.forEach(builder::addHeader);
        }

        okhttp3.MultipartBody.Builder requestBody = (new okhttp3.MultipartBody.Builder()).setType(MultipartBody.FORM);
        Iterator var4;
        String result;
        if (!CollectionUtils.isEmpty(okHttp.formMap)) {
            var4 = okHttp.formMap.keySet().iterator();

            while(var4.hasNext()) {
                result = (String)var4.next();
                if(okHttp.formMap.get(result) != null){
                    String str = okHttp.formMap.get(result).toString();
                    requestBody.addFormDataPart(result, str);
                }
            }
        }

        if (!CollectionUtils.isEmpty(okHttp.files)) {
            var4 = okHttp.files.keySet().iterator();

            while(var4.hasNext()) {
                result = (String)var4.next();
                File file = (File)okHttp.files.get(result);
                long fileSize = file.length();
                requestBody.addPart(Headers.of(new String[]{"Content-Disposition", "form-data; name=\"" + result + "\";filename=\"" + file.getName() + "\";filesize=" + fileSize}), RequestBody.create(MediaType.parse("image/*"), file));
            }
        }

        if (!CollectionUtils.isEmpty(okHttp.fileList)) {
            var4 = okHttp.fileList.keySet().iterator();

            while(var4.hasNext()){
                result = (String) var4.next();
                List<File> fileList = okHttp.fileList.get(result);
                for (File file : fileList) {
                    long fileSize = file.length();
                    requestBody.addPart(Headers.of(new String[]{"Content-Disposition", "form-data; name=\"" + result + "\";filename=\"" + file.getName() + "\";filesize=" + fileSize}), RequestBody.create(MediaType.parse("image/*"), file));
                }
            }
        }

        String method = okHttp.method.toUpperCase();
        builder.method(method, requestBody.build());
        result = "";

        try {
            Response response = CLIENT.newCall(builder.build()).execute();
            byte[] bytes = response.body().bytes();
            result = new String(bytes, okHttp.responseCharset);
            if (okHttp.responseLog) {
                log.info(String.format("Got response->%s", result));
            }
        } catch (Exception var9) {
            log.error(okHttp.toString(), var9);
        }

        return result;
    }

    public static Request getFilesRequest(String url, List<File> files, Map<String, String> maps) {
        okhttp3.MultipartBody.Builder builder = (new okhttp3.MultipartBody.Builder()).setType(MultipartBody.FORM);
        int j;
        if (maps == null) {
            for(j = 0; j < files.size(); ++j) {
                builder.addPart(Headers.of(new String[]{"Content-Disposition", "form-data; name=\"file\";filename=\"file.jpg\""}), RequestBody.create(MediaType.parse("image/*"), (File)files.get(j))).build();
            }
        } else {
            Iterator var7 = maps.keySet().iterator();

            while(var7.hasNext()) {
                String key = (String)var7.next();
                String str = (String)maps.get(key);
                builder.addFormDataPart(key, str);
            }

            for(j = 0; j < files.size(); ++j) {
                long fileSize = ((File)files.get(j)).length();
                builder.addPart(Headers.of(new String[]{"Content-Disposition", "form-data; name=\"file\";filename=\"file.jpg\";filesize=" + fileSize}), RequestBody.create(MediaType.parse("image/*"), (File)files.get(j)));
            }
        }

        RequestBody body = builder.build();
        return (new Request.Builder()).url(url).post(body).build();
    }

    static {
        DEFAULT_MEDIA_TYPE = ContentType.APPLICATION_JSON.getMimeType();
        CLIENT = (new okhttp3.OkHttpClient.Builder()).connectionPool(new ConnectionPool(20, 5L, TimeUnit.MINUTES)).readTimeout(60L, TimeUnit.SECONDS).connectTimeout(20L, TimeUnit.SECONDS).build();
    }

    static class OkHttp {
        private String url;
        private String method = "GET";
        private String data;
        private String mediaType;
        private Map<String, String> queryMap;
        private Map<String, String> headerMap;
        private Map<String, File> files;
        private Map<String, List<File>> fileList;
        private Map<String, Object> formMap;
        private String requestCharset;
        private boolean requestLog;
        private String responseCharset;
        private boolean responseLog;
        private static final MediaType CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded");

        OkHttp(final String url, final String method, final String data, final String mediaType, final Map<String, String> queryMap, final Map<String, String> headerMap, final Map<String, File> files, final Map<String, List<File>> fileList, final Map<String, Object> formMap, final String requestCharset, final boolean requestLog, final String responseCharset, final boolean responseLog) {
            this.mediaType = HttpUtil.DEFAULT_MEDIA_TYPE;
            this.requestCharset = "UTF-8";
            this.requestLog = false;
            this.responseCharset = "UTF-8";
            this.responseLog = false;
            this.url = url;
            this.method = method;
            this.data = data;
            this.mediaType = mediaType;
            this.queryMap = queryMap;
            this.headerMap = headerMap;
            this.files = files;
            this.fileList = fileList;
            this.formMap = formMap;
            this.requestCharset = requestCharset;
            this.requestLog = requestLog;
            this.responseCharset = responseCharset;
            this.responseLog = responseLog;
        }

        public static OkHttpBuilder builder() {
            return new OkHttpBuilder();
        }

        public String toString() {
            return "HttpUtil.OkHttp(url=" + this.url + ", method=" + this.method + ", data=" + this.data + ", mediaType=" + this.mediaType + ", queryMap=" + this.queryMap + ", headerMap=" + this.headerMap + ", files=" + this.files + ", formMap=" + this.formMap + ")";
        }

        public static class OkHttpBuilder {
            private String url;
            private String method;
            private String data;
            private String mediaType;
            private Map<String, String> queryMap;
            private Map<String, String> headerMap;
            private Map<String, File> files;
            private Map<String, List<File>> fileList;
            private Map<String, Object> formMap;
            private String requestCharset;
            private boolean requestLog;
            private String responseCharset;
            private boolean responseLog;

            OkHttpBuilder() {
            }

            public OkHttpBuilder url(final String url) {
                this.url = url;
                return this;
            }

            public OkHttpBuilder method(final String method) {
                this.method = method;
                return this;
            }

            public OkHttpBuilder data(final String data) {
                this.data = data;
                return this;
            }

            public OkHttpBuilder mediaType(final String mediaType) {
                this.mediaType = mediaType;
                return this;
            }

            public OkHttpBuilder queryMap(final Map<String, String> queryMap) {
                this.queryMap = queryMap;
                return this;
            }

            public OkHttpBuilder headerMap(final Map<String, String> headerMap) {
                this.headerMap = headerMap;
                return this;
            }

            public OkHttpBuilder files(final Map<String, File> files) {
                this.files = files;
                return this;
            }

            public OkHttpBuilder fileList(final Map<String, List<File>> fileList) {
                this.fileList = fileList;
                return this;
            }

            public OkHttpBuilder formMap(final Map<String, Object> formMap) {
                this.formMap = formMap;
                return this;
            }

            public OkHttpBuilder requestCharset(final String requestCharset) {
                this.requestCharset = requestCharset;
                return this;
            }

            public OkHttpBuilder requestLog(final boolean requestLog) {
                this.requestLog = requestLog;
                return this;
            }

            public OkHttpBuilder responseCharset(final String responseCharset) {
                this.responseCharset = responseCharset;
                return this;
            }

            public OkHttpBuilder responseLog(final boolean responseLog) {
                this.responseLog = responseLog;
                return this;
            }

            public OkHttp build() {
                return new OkHttp(this.url, this.method, this.data, this.mediaType, this.queryMap, this.headerMap, this.files, this.fileList, this.formMap, this.requestCharset, this.requestLog, this.responseCharset, this.responseLog);
            }

            public String toString() {
                return "HttpUtil.OkHttp.OkHttpBuilder(url=" + this.url + ", method=" + this.method + ", data=" + this.data + ", mediaType=" + this.mediaType + ", queryMap=" + this.queryMap + ", headerMap=" + this.headerMap + ", files=" + this.files + ", formMap=" + this.formMap + ", requestCharset=" + this.requestCharset + ", requestLog=" + this.requestLog + ", responseCharset=" + this.responseCharset + ", responseLog=" + this.responseLog + ")";
            }
        }
    }

    public static void main(String[] args) {
        String cookie = "aaa";
        String url = "http://localhost:8080/";
        Map<String, Object> param = new HashMap<>();
        param.put("name","aaa");
        Map<String, File> fileMap = new HashMap<>();
        fileMap.put("file",new File(""));
        try {
            String resp = HttpUtil.putFormDataWithCookie(url,param,fileMap,cookie);
            log.info("result:" + resp);
        } catch (Exception e) {
            log.error("modifyVisitor Exception", e.getMessage());
            throw e;
        }
    }
}
