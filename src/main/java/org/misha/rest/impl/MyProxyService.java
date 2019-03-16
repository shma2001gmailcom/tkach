package org.misha.rest.impl;

import javax.inject.Named;
import java.lang.annotation.Annotation;

import static java.lang.reflect.Proxy.newProxyInstance;

@Named
final class MyProxyService {

    Object createProxy(final Object proxied,
                       final Class<?> someInterface,
                       final Class<? extends Annotation> annotation
    ) {
        return newProxyInstance(MyInvocationHandler.class.getClassLoader(), new Class[]{someInterface},
                                new MyInvocationHandler(proxied, annotation));
    }
}

