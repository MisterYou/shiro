package com.youjin.annotation;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public abstract class Validator{

    protected abstract boolean validate(HttpServletRequest request);

    protected abstract Map<String,Object> handleError();
}
