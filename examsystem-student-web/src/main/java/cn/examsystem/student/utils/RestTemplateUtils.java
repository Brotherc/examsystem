package cn.examsystem.student.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/1.
 */
public class RestTemplateUtils {

    /**
     * 执行请求
     *
     * @param url          请求地址
     * @param method       请求方式
     * @param responseType 返回的数据类型
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     */
    public static <T> T exchange(String url, HttpMethod method, Class<T> responseType, Object... uriVariables) throws Exception {
        return exchange(url, method, null, null, responseType, uriVariables);
    }

    /**
     * 执行请求
     *
     * @param url          请求地址
     * @param method       请求方式
     * @param headers      设置的头信息
     * @param responseType 返回的数据类型
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     */
    public static <T> T exchange(String url, HttpMethod method, HttpHeaders headers, Class<T> responseType, Object... uriVariables) throws Exception {
        return exchange(url, method, headers, null, responseType, uriVariables);
    }

    /**
     * 执行请求
     *
     * @param url          请求地址
     * @param method       请求方式
     * @param body         要提交的数据
     * @param responseType 返回数据类型
     *                     返回bean时指定Class
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     */
    public static <T> T exchange(String url, HttpMethod method, Object body, Class<T> responseType, Object... uriVariables) throws Exception {
        return exchange(url, method, null, body, responseType, uriVariables);
    }

    /**
     * 执行请求
     *
     * @param url          请求地址
     * @param method       请求方式
     * @param httpHeaders  请求头
     * @param body         要提交的数据
     * @param responseType 返回数据类型
     *                     返回bean时指定Class
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws Exception RestClient异常，包含状态码和非200的返回内容
     */
    public static <T> T exchange(String url, HttpMethod method, HttpHeaders httpHeaders, Object body, Class<T> responseType, Object... uriVariables) throws Exception {
            HttpEntity<?> requestEntity = new HttpEntity(body, httpHeaders);
            requestEntity = convert(requestEntity);

        RestTemplate restTemplate = RestTemplateUtils.getInstance();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory() {
            @Override
            protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
                if (HttpMethod.DELETE == httpMethod) {
                    return new HttpEntityEnclosingDeleteRequest(uri);
                }
                return super.createHttpUriRequest(httpMethod, uri);
            }
        });

            if (uriVariables.length == 1 && uriVariables[0] instanceof Map) {
                Map<String, ?> _uriVariables = (Map<String, ?>) uriVariables[0];
                return restTemplate.exchange(url, method, requestEntity, responseType, _uriVariables).getBody();
            }

            return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables).getBody();
    }
    public static <T> T exchangeForFile(String url, HttpMethod method,MultipartFile resource, Class<T> responseType, Object... uriVariables) throws Exception {

        MultiValueMap<String,Object> param=new LinkedMultiValueMap<>();
        param.add("uploadFile",resource);

        HttpEntity<MultiValueMap<String,Object>> httpEntity=new HttpEntity(param);

        RestTemplate restTemplate = RestTemplateUtils.getInstance();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory() {
            @Override
            protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
                if (HttpMethod.DELETE == httpMethod) {
                    return new HttpEntityEnclosingDeleteRequest(uri);
                }
                return super.createHttpUriRequest(httpMethod, uri);
            }
        });

        if (uriVariables.length == 1 && uriVariables[0] instanceof Map) {
            Map<String, ?> _uriVariables = (Map<String, ?>) uriVariables[0];
            return restTemplate.exchange(url, method, httpEntity, responseType, _uriVariables).getBody();
        }

        return restTemplate.exchange(url, method, httpEntity, responseType, uriVariables).getBody();
    }

    /**
     * 执行请求
     *
     * @param url          请求地址
     * @param method       请求方式
     * @param responseType 返回的数据类型，例：new ParameterizedTypeReference<List<Bean>>(){}
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     */
    public static <T> T exchange(String url, HttpMethod method, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws Exception {
        return exchange(url, method, null, null, responseType, uriVariables);
    }

    /**
     * 执行请求
     *
     * @param url          请求地址
     * @param method       请求方式
     * @param headers      设置的头信息
     * @param responseType 返回的数据类型，例：new ParameterizedTypeReference<List<Bean>>(){}
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     */
    public static <T> T exchange(String url, HttpMethod method, HttpHeaders headers, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws Exception {
        return exchange(url, method, headers, null, responseType, uriVariables);
    }

    /**
     * 执行请求
     *
     * @param url          请求地址
     * @param method       请求方式
     * @param body         要提交的数据
     * @param responseType 返回数据类型，例：new ParameterizedTypeReference<List<Bean>>(){}
     *                     返回bean时指定Class
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     */
    public static <T> T exchange(String url, HttpMethod method, Object body, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws Exception {
        return exchange(url, method, null, body, responseType, uriVariables);
    }

    /**
     * 执行请求
     *
     * @param url          请求地址
     * @param method       请求方式
     * @param httpHeaders  请求头
     * @param body         要提交的数据
     * @param responseType 返回数据类型，例：new ParameterizedTypeReference<List<Bean>>(){}
     *                     返回bean时指定Class
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     */
    public static <T> T exchange(String url, HttpMethod method, HttpHeaders httpHeaders, Object body, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws Exception {
            HttpEntity<?> requestEntity = new HttpEntity(body, httpHeaders);
            requestEntity = convert(requestEntity);

        RestTemplate restTemplate = RestTemplateUtils.getInstance();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory() {
            @Override
            protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
                if (HttpMethod.DELETE == httpMethod) {
                    return new HttpEntityEnclosingDeleteRequest(uri);
                }
                return super.createHttpUriRequest(httpMethod, uri);
            }
        });

        if (uriVariables.length == 1 && uriVariables[0] instanceof Map) {
                Map<String, ?> _uriVariables = (Map<String, ?>) uriVariables[0];
                return restTemplate.exchange(url, method, requestEntity, responseType, _uriVariables).getBody();
            }

            return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables).getBody();
    }

    /**
     * 获得一个RestTemplate客户端
     *
     * @return
     */
    private static class SingletonRestTemplate {
        /**
         * 单例对象实例
         */
        static final RestTemplate INSTANCE = new RestTemplate();
    }

    private RestTemplateUtils() {

    }



    /**
     * 单例实例
     */
    public static RestTemplate getInstance() {
        return SingletonRestTemplate.INSTANCE;
    }


    public static class HttpEntityEnclosingDeleteRequest extends HttpEntityEnclosingRequestBase {

        public HttpEntityEnclosingDeleteRequest(final URI uri) {
            super();
            setURI(uri);
        }

        @Override
        public String getMethod() {
            return "DELETE";
        }
    }
    /**
     * 获取一个application/x-www-form-urlencoded头
     *
     * @return
     */
    public static HttpHeaders buildBasicFORMHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    /**
     * 获取一个application/json头
     *
     * @return
     */
    public static HttpHeaders buildBasicJSONHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * 获取一个text/html头
     *
     * @return
     */
    public static HttpHeaders buildBasicHTMLHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return headers;
    }


    /**
     * 对bean对象转表单模型做处理
     *
     * @param requestEntity
     * @return
     */
    private static HttpEntity<?> convert(HttpEntity<?> requestEntity) {
        Object body = requestEntity.getBody();
        HttpHeaders headers = requestEntity.getHeaders();

        if (body == null) {
            return requestEntity;
        }

        if (body instanceof Map) {
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            Map<String, ?> _body = (Map<String, ?>) body;
            for (String key : _body.keySet()) {
                multiValueMap.add(key, (String)_body.get(key));
            }

            requestEntity = new HttpEntity<>(multiValueMap, headers);
        }

        if (headers == null || !MediaType.APPLICATION_FORM_URLENCODED.equals(headers.getContentType())) {
            return requestEntity;
        }

        if (body instanceof String) {
            return requestEntity;
        }

        if (body instanceof Collection) {
            return requestEntity;
        }

        if (body instanceof Map) {
            return requestEntity;
        }

        MultiValueMap<String, Object> formEntity = new LinkedMultiValueMap<>();

        Field[] fields = body.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            String value = null;

            try {
                value = BeanUtils.getProperty(body, name);
            } catch (Exception e) {
                e.printStackTrace();
            }

            formEntity.add(name, value);
        }

        return new HttpEntity<>(formEntity, headers);
    }

    public final static Object[] EMPTY_URI_VARIABLES = new Object[]{};

    public final static HttpHeaders EMPTY_HEADERS = new HttpHeaders();

    public final static Map<String, ?> EMPTY_BODY = new HashMap<>(1);

    public final static HttpEntity EMPTY_ENTITY = new HttpEntity(EMPTY_HEADERS);

}
