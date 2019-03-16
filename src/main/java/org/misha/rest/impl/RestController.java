package org.misha.rest.impl;

import org.misha.LoggingService;
import org.misha.loggers.NotImplementedYetThrowable;
import org.misha.rest.RestApi;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.misha.rest.ViewType.CACHED;
import static org.misha.rest.ViewType.DB;
import static org.misha.rest.ViewType.FILE;

/**
 * author: misha
 * date: 1/5/18
 * time: 1:51 PM
 */
@Named
class RestController implements RestApi {
    private final LoggingService holder;
    private static final String DETAILS_KEY = "details";

    @Inject
    RestController(@Named("loggingService") final LoggingService service) {
        holder = service;
    }

    public String run(final @PathVariable String s, final ModelMap model) throws Throwable {
        holder.logEvents(model);
        model.put("anArgument", s);
        switch (s) {
            case "ConsoleEventLogger":
                throw new NotImplementedYetThrowable("console details are unavailable");
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

    @Override
    public String fileDetails(final ModelMap model) throws Throwable {
        model.clear();
        model.put(DETAILS_KEY, holder.getDetails(FILE));
        return "file";
    }

    @Override
    public String cacheDetails(final ModelMap model) throws Throwable {
        model.clear();
        model.put(DETAILS_KEY, holder.getDetails(CACHED));
        return "cached";
    }

    @Override
    public String dbDetails(final ModelMap model) throws Throwable {
        model.clear();
        model.put(DETAILS_KEY, holder.getDetails(DB));
        return "data-base";
    }

    @Override
    public String errorDetails(final ModelMap model, final Throwable e) {
        model.clear();
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            e.printStackTrace(pw);
            pw.flush();
            model.put(DETAILS_KEY, prettifyToHtml(sw.toString()));
        } catch (Exception t) {
            model.put(DETAILS_KEY, t.getMessage());
        }
        return "error";
    }

    private String prettifyToHtml(String stackTrace) {
        return stackTrace.replaceAll("[\t\n\r ]+at ", "<br/>&nbsp;&nbsp;at ")
                         .replaceAll("Caused by", "<br/>&nbsp;&nbsp;Caused by");
    }
}

