package org.misha.rest;

import org.springframework.ui.ModelMap;

import java.io.Serializable;

public interface RestApi extends Serializable {

    String run(String s, final ModelMap model) throws Throwable;

    String fileDetails(ModelMap model) throws Throwable;

    String cacheDetails(ModelMap model) throws Throwable;

    String dbDetails(ModelMap model) throws Throwable;

    String errorDetails(ModelMap model, Throwable e);
}
