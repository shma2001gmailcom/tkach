package org.misha.rest;

import org.misha.LoggingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

import static org.misha.rest.ViewType.*;

/**
 * author: misha
 * date: 1/5/18
 * time: 1:51 PM
 */
@Controller
@RequestMapping(value = "/rest")
public class RestController {
    private static final String DETAILS_KEY = "details";
    private final LoggingService holder;
    
    @Inject
    RestController(final LoggingService service) {
        holder = service;
    }
    
    @RequestMapping(value = "/{s}", method = RequestMethod.GET)
    public String run(@PathVariable String s, final ModelMap model) throws Exception {
        holder.logEvents(model);
        model.put("anArgument", s);
        switch (s) {
            case "ConsoleEventLogger":
                return "console";
            case "FileEventLogger":
                return fileDetails(model);
            case "CachedEventLogger":
                return cacheDetails(model);
            case "DbLogger":
                return dbDetails(model);
            default:
                return "list";
        }
    }
    
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public String fileDetails(final ModelMap model) throws Exception {
        model.clear();
        model.put(DETAILS_KEY, holder.getDetails(FILE));
        return "file";
    }
    
    @RequestMapping(value = "/cached", method = RequestMethod.GET)
    public String cacheDetails(final ModelMap model) throws Exception {
        model.clear();
        model.put(DETAILS_KEY, holder.getDetails(CACHED));
        return "cached";
    }
    
    @RequestMapping(value = "/data-base", method = RequestMethod.GET)
    public String dbDetails(final ModelMap model) throws Exception {
        model.clear();
        model.put(DETAILS_KEY, holder.getDetails(DB));
        return "data-base";
    }
}

