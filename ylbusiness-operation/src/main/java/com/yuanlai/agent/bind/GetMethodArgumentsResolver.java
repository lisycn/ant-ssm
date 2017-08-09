package com.yuanlai.agent.bind;

import com.tangdi.def.utils.common.TdCommUtil;
import com.yuanlai.agent.model.EntityDto;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


@Component
public class GetMethodArgumentsResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Get.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        EntityDto entityDto = new EntityDto();
        Map<String, Object> param = new HashMap<>();
        String[] values = webRequest.getParameterValues("p");
        if("".equals(values[0])){
            entityDto.setParams(param);
            return entityDto;
        }
        String result = TdCommUtil.base64Decode(values[0], "GB2312");
        String decoded = URLDecoder.decode(result);
        String[] keyValues = decoded.split("&");
        for (String keyValue : keyValues) {
            String[] keyAndValue = keyValue.split("=");
            if(keyAndValue.length == 1){
            	param.put(keyAndValue[0], "");
            }else{
              param.put(keyAndValue[0], keyAndValue[1]);
            }
        }

        entityDto.setParams(param);
        return entityDto;
    }
}
