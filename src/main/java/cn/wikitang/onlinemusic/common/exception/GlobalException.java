package cn.wikitang.onlinemusic.common.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package: cn.wikitang.onlinemusic.common.exception
 * @ClassName: GlobalException
 * @Author: WikiTang
 * @Date: 2022/3/13 17:27
 * @Description: 全局异常处理
 */
@ControllerAdvice
@Order(value= Ordered.HIGHEST_PRECEDENCE)
public class GlobalException {

    private Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(value = Exception.class)
    public ModelAndView resolveException(HttpServletRequest request, Exception e) {
        Map<String,Object> map=new HashMap<>(3);
        map.put("code","99999");
        map.put("valid","99999");
        String message = "系统异常";
        if (StringUtils.isNotBlank(e.getMessage())){
            message = e.getMessage();
        }
        map.put("message",message);
        map.put("success",false);
        return returnModelView(map,e);
    }


    @ExceptionHandler(value = ApiException.class)
    public ModelAndView resolveHyHRException(ApiException e) {
        Map<String,Object> map=new HashMap<>(3);
        map.put("code",e.getCode());
        map.put("msg",e.getMsg());
        return returnModelView(map,e);
    }


    private ModelAndView returnModelView(Map<String,Object> map, Exception e){
        ModelAndView mv=new ModelAndView();
        /*  使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常   */
        FastJsonJsonView view = new FastJsonJsonView();
        view.setAttributesMap(map);
        mv.setView(view);
        String mapStr = map.toString();
        logger.error(mapStr,e);
        return mv;
    }
}
