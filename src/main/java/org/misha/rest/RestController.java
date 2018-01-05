package org.misha.rest;

import org.misha.LoggingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * author: misha
 * date: 1/5/18
 * time: 1:51 PM
 */
@Controller
@RequestMapping(value = "/rest")
public class RestController {
    private final LoggingService holder;

    @Inject
    RestController(final LoggingService service) {
        holder = service;
    }

    @RequestMapping(value = "/{s}", method = RequestMethod.GET)
    public String run(@PathVariable String s, final ModelMap model) {
        holder.logEvents(model);
        model.put("anArgument", s);
        return "list";
    }
}

