//package com.hr.manage.web.interceptor;
//import hr.manage.component.common.model.LoggerEntity;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.hr.manage.web.controllers.PersonalController;
//import com.hr.manage.web.util.LoggerUtil;
//
//import net.paoding.rose.web.ControllerInterceptorAdapter;
//import net.paoding.rose.web.Invocation;
//import net.paoding.rose.web.InvocationChain;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class LogInterceptor extends ControllerInterceptorAdapter 
//{
//    //请求开始时间标识
//    private static final String LOGGER_SEND_TIME = "send_time";
//    //请求日志实体标识
//    private static final String LOGGER_ENTITY = "logger_entity";
//    private final Log logger = LogFactory.getLog("log_main");
//    private final Log loggerTime = LogFactory.getLog("log_time");
//	
//    /**
//     * 进入SpringMVC的Controller之前开始记录日志实体
//     * @param request 请求对象
//     * @param response 响应对象
//     * @param o
//     * @return
//     * @throws Exception
//     */
//    @Override
//    protected Object before(Invocation inv) throws Exception {
//        //创建日志实体
//        LoggerEntity logger = new LoggerEntity();
//        //获取请求sessionId
//        String sessionId = inv.getRequest().getRequestedSessionId();
//        //请求路径
//        String url = inv.getRequest().getRequestURI();
//        //获取请求参数信息
//        String paramData = JSON.toJSONString(inv.getRequest().getParameterMap(),
//                SerializerFeature.DisableCircularReferenceDetect,
//                SerializerFeature.WriteMapNullValue);
//        //设置客户端ip
//        logger.setClientIp(LoggerUtil.getCliectIp(inv.getRequest()));
//        //设置请求方法
//        logger.setMethod(inv.getRequest().getMethod());
//        //设置请求类型（json|普通请求）
//        logger.setType(LoggerUtil.getRequestType(inv.getRequest()));
//        //设置请求参数内容json字符串
//        logger.setParamData(paramData);
//        //设置请求地址
//        logger.setUri(url);
//        //设置sessionId
//        logger.setSessionId(sessionId);
//        //设置请求开始时间
//        inv.getRequest().setAttribute(LOGGER_SEND_TIME,System.currentTimeMillis());
//        //设置请求实体到request内，方面afterCompletion方法调用
//        inv.getRequest().setAttribute(LOGGER_ENTITY,logger);
//        return true;
//    }
//
//    @Override
//    protected Object round(Invocation inv, InvocationChain chain) throws Exception {
//        try {
//            long beginTime = System.currentTimeMillis();
//            StringBuilder reqBuilder = new StringBuilder();
//          //获取请求参数信息
//            String paramData = JSON.toJSONString(inv.getRequest().getParameterMap(),
//                    SerializerFeature.DisableCircularReferenceDetect,
//                    SerializerFeature.WriteMapNullValue);
//            paramData = "paramData:" + paramData + "...";
//            reqBuilder.append(paramData + "frontCall：MethodName——" + inv.getMethod().getName() + ",CallTime——" + beginTime + ",InParam" + JSON.toJSONString(inv.getMethodParameters()));
//            reqBuilder.append(" ... ");
//            Object o = chain.doNext();
//            long endTime = System.currentTimeMillis();
//            reqBuilder.append(paramData + "frontCall：MethodName——" + inv.getMethod().getName() + ",CallTime——" + beginTime + ",ReturnTime" + endTime + ",totalTime——" + (endTime - beginTime) + "ms,OutParam" + JSON.toJSONString(o));
//            logger.info(reqBuilder.toString());
//            loggerTime.info("frontCall：MethodName——" + inv.getMethod().getName() + ",CallTime——" + beginTime + ",ReturnTime" + endTime + "，totalTime——" + (endTime - beginTime) + "ms");
//            return o;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//
//    }
//
//
//    @Override
//    protected Object after(Invocation inv, Object instruction) throws Exception {
//        //获取请求错误码
//        int status = inv.getResponse().getStatus();
//        //当前时间
//        long currentTime = System.currentTimeMillis();
//        //请求开始时间
//        long time = Long.valueOf(inv.getRequest().getAttribute(LOGGER_SEND_TIME).toString());
//        //获取本次请求日志实体
//        LoggerEntity loggerEntity = (LoggerEntity) inv.getRequest().getAttribute(LOGGER_ENTITY);
//        //设置请求时间差
//        loggerEntity.setTimeConsuming(Integer.valueOf((currentTime - time)+""));
//        //设置返回时间
//        loggerEntity.setReturnTime(currentTime + "");
//        //设置返回错误码
//        loggerEntity.setHttpStatusCode(status+"");
//        //设置返回值
//        loggerEntity.setReturnData(JSON.toJSONString(inv.getRequest().getAttribute(LoggerUtil.LOGGER_RETURN),
//                SerializerFeature.DisableCircularReferenceDetect,
//                SerializerFeature.WriteMapNullValue));
//        //执行将日志写入数据库
//        
//        return super.after(inv, instruction);
//    }
//   
//}
