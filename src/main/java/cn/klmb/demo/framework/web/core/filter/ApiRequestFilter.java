package cn.klmb.demo.framework.web.core.filter;

import cn.hutool.core.util.StrUtil;
import cn.klmb.demo.framework.web.config.WebProperties;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 过滤 /admin-api、/app-api 等 API 请求的过滤器
 *
 * @author 快乐萌宝
 */
@RequiredArgsConstructor
public abstract class ApiRequestFilter extends OncePerRequestFilter {

    protected final WebProperties webProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 只过滤 API 请求的地址
        return !StrUtil.startWithAny(request.getRequestURI(),
                webProperties.getAdminApi().getPrefix(),
                webProperties.getAppApi().getPrefix());
    }

}
