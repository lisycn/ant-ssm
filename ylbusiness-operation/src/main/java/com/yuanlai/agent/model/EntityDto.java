package com.yuanlai.agent.model;

import java.util.Map;

/**
 * Created by johnny on 2016/11/24.
 */
public class EntityDto {
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = getParams();
        for (Map.Entry entry : params.entrySet()) {
            stringBuilder.append(" " + entry.getKey().toString() + " : " + entry.getValue().toString() + " ");
        }
        return stringBuilder.toString();
    }
}
