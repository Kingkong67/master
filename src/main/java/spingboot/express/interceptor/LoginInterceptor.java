package spingboot.express.interceptor;
/**
 * 判断用户是否登录拦截器
 */

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * @描述
     * @参数 [request, response, arg2]
     * @返回值 boolean
     * @创建人 wanghu
     * @创建时间 2020/11/22 4:00 上午
     * @修改人和其它信息
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        return true;
    }

    /**
     * @描述
     * @参数 [arg0, arg1, arg2, arg3]
     * @返回值 void
     * @创建人 wanghu
     * @创建时间 2020/11/22 4:03 上午
     * @修改人和其它信息
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {


    }

    /**
     * @描述
     * @参数 [arg0, arg1, arg2, arg3]
     * @返回值 void
     * @创建人 wanghu
     * @创建时间 2020/11/22 4:03 上午
     * @修改人和其它信息
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {

    }


}

