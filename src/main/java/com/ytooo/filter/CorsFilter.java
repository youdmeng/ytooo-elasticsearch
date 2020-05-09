package com.ytooo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);
    public CorsFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
// 域白名单注释 换成*
    /*    List<String> list = FilterConstant.list;
        if (list.size()>0){
            String origin = ((HttpServletRequest) request).getHeader("Origin");
            logger.info("访问者的域名为："+origin);
            for (String ori:list){
                if (ori.equals(origin)){
                    httpServletResponse.setHeader("Access-Control-Allow-Origin",origin);
                    break;

                }
            }
        }*/

         httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "sid, Origin,orgId, Accept, x-auth-token, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
