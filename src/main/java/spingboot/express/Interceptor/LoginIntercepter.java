
package spingboot.express.Interceptor;
/**
 * 判断用户是否登录拦截器
 */
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginIntercepter implements HandlerInterceptor {

    /**
     *@描述
     *@参数 [arg0, arg1, arg2, arg3]
     *@返回值 void
     *@创建人 wanghu
     *@创建时间 2020/11/22 4:03 上午
     *@修改人和其它信息
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1,Object arg2,Exception arg3) throws Exception{


    }

    /**
     *@描述
     *@参数 [arg0, arg1, arg2, arg3]
     *@返回值 void
     *@创建人 wanghu
     *@创建时间 2020/11/22 4:03 上午
     *@修改人和其它信息
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception{

    }


    /**
     *@描述
     *@参数 [request, response, arg2]
     *@返回值 boolean
     *@创建人 wanghu
     *@创建时间 2020/11/22 4:00 上午
     *@修改人和其它信息
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2)throws Exception{
        HttpSession session = request.getSession();
        if (session.getAttribute("user")!=null){
          return true;
        }else{
            String uri = request.getRequestURI();
//            拿到上一个页面的地址
            String path = uri.substring(request.getContextPath().length());
            String query = request.getQueryString();
            if (query == null){
                query="";
            }else{
                query = "?"+query;
            }
            String beforepath = path+query;
            session.setAttribute("beforepath",beforepath);
            response.sendRedirect(request.getContextPath()+"/static/html/kuaidi.html");
            return false;
        }
    }

}

