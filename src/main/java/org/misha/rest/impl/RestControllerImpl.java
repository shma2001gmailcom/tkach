package org.misha.rest.impl;

import org.misha.LoggingService;
import org.misha.rest.RestApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

@Controller
@RequestMapping(value = "/rest")
final class RestControllerImpl {
    private final MyProxyService proxyService;
    private RestApi restApi;
    private final LoggingService service;

    @Autowired
    public RestControllerImpl(final MyProxyService proxyService, final LoggingService service) {
        this.proxyService = proxyService;
        this.service = service;
    }

    @GetMapping(value = "/{s}")
    @RestApiMethod
    public String run(@PathVariable String s, final ModelMap model) throws Throwable {
        try {
            return restApi.run(s, model);
        } catch (Throwable e) {
            return restApi.errorDetails(model, e);
        }
    }

    @GetMapping(value = "/file")
    @RestApiMethod
    public String fileDetails(final ModelMap model) throws Throwable {
        return restApi.fileDetails(model);
    }

    @GetMapping(value = "/cached")
    @RestApiMethod
    public String cacheDetails(final ModelMap model) throws Throwable {
        return restApi.cacheDetails(model);
    }

    @GetMapping(value = "/data-base")
    @RestApiMethod
    public String dbDetails(final ModelMap model) throws Throwable {
        return restApi.dbDetails(model);
    }

    @PostConstruct
    public void createProxy() {
        restApi = (RestApi) proxyService.createProxy(new RestController(service), RestApi.class, RestApiMethod.class);
    }
}
