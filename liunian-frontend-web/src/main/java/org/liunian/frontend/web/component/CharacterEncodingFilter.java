package org.liunian.frontend.web.component;

import lombok.extern.slf4j.Slf4j;
import org.liunian.common.ComUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CharacterEncodingFilter implements Filter {

    private static String encoding;
    private static final List<String> RESOURCE_SUFFIX = Arrays.asList(".css", ".js", ".png", ".jpg", ".jpeg", ".gif", ".svg", "ico");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("charset").trim();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 强制转换ServletRequest为HttpServletRequest
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String requestUrl = req.getRequestURL().toString();
        for (String suffix : RESOURCE_SUFFIX) {
            if (ComUtils.endWith(requestUrl, suffix)) {
                filterChain.doFilter(req, resp);
                return;
            }
        }

        // 获得请求方式
        String method = req.getMethod();
        if ("get".equalsIgnoreCase(method)) {
            log.info("GET {}", requestUrl);
            // 创建装饰器类
            req = encoding == null || "".equals(encoding.trim())
                    ? new CharacterEncodingRequestWrapper(req)
                    : new CharacterEncodingRequestWrapper(req, encoding);
        } else if ("post".equalsIgnoreCase(method)) {
            log.info("POST {}", requestUrl);
            // 处理Post请求时乱码问题
            req.setCharacterEncoding(encoding);
            // Post方式响应页面乱码处理
            resp.setCharacterEncoding(encoding);
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

    class CharacterEncodingRequestWrapper extends HttpServletRequestWrapper {

        private final static String DEFAULT_ENCODING = "UTF-8";
        private String encoder;
        private HttpServletRequest request;

        CharacterEncodingRequestWrapper(HttpServletRequest request) {
            this(request, DEFAULT_ENCODING);
        }

        CharacterEncodingRequestWrapper(HttpServletRequest request, String encoder) {
            super(request);
            this.request = request;
            this.encoder = encoder;
        }

        @Override
        public String getParameter(String name) {
            String[] values = getParameterValues(name);
            if (values == null) {
                return null;
            }
            if (values.length > 0)
                return values[0];
            return null;
        }

        @Override
        public String[] getParameterValues(String name) {
            Map<String, String[]> map = getParameterMap();
            return map.get(name);
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            Map<String, String[]> map = new HashMap<>();
            try {
                map = decodingParameter();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return map;
        }

        private Map<String, String[]> decodingParameter() throws UnsupportedEncodingException {
            Map<String, String[]> resultMap = new HashMap<>();
            Map<String, String[]> map = request.getParameterMap();
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                String name = entry.getKey();
                String[] values = entry.getValue();
                for (int i = 0; i < values.length; i++) {
                    // 判断是否UTF-8编码，不是就转为UTF-8编码
                    if (!(Charset.forName(encoder).newEncoder().canEncode(values[i]))) {
                        values[i] = new String(values[i].getBytes(Charset.forName("ISO-8859-1")), encoder);
                    }
                }
                resultMap.put(name, values);
            }
            return resultMap;
        }
    }
}
