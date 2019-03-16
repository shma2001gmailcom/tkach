package org.misha.rest.impl;

import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

final class MyInvocationHandler implements InvocationHandler {
    private static final Logger log = Logger.getLogger(MyInvocationHandler.class);
    private final Object proxied;
    private final Class<? extends Annotation> annotation;

    MyInvocationHandler(final Object proxied, final Class<? extends Annotation> someClass) {
        this.proxied = proxied;
        this.annotation = someClass;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        Method m = proxied.getClass().getMethod(method.getName(), method.getParameterTypes());
        if (m.isAnnotationPresent(annotation)) {
            try {
                log.info("\n\n try call real method");
                Object returnValue = method.invoke(proxied, args);
                log.info("\n\n try real method has been called");
                return returnValue;
            } catch (Exception e) {
                log.error("\n\n try return stack trace for " + e);

            }
        }
        return method.invoke(proxied, args);
    }
}
