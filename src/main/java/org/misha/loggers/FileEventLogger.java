package org.misha.loggers;

import org.apache.log4j.Logger;
import org.misha.EventLogger;
import org.misha.event.Event;
import org.misha.event.EventType;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.misha.event.EventType.ERROR;

/**
 * author: misha
 * date: 1/3/18
 * time: 4:19 AM
 */
@Named
public class FileEventLogger implements EventLogger {
    private final String fileName;
    private final Logger log;
    
    @Inject
    FileEventLogger(@Named final String fileName, final Logger log) {
        this.fileName = fileName;
        this.log = log;
    }
    
    public void logEvent(final Event event) {
        try {
            writeStringToFile(new File(fileName), event.toString() + "\n", true);
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    
    public String getDetails() throws IOException {
        return readFileToString(new File(fileName))
                .replaceAll("\norg\\.misha\\.event\\.Event@", "<p>org.misha.event.Event@");
    }
    
    @Override
    public List<String> getEvents() {
        return Collections.emptyList();
    }
    
    @Override
    public boolean suitableFor(final EventType type) {
        return type == ERROR;
    }
    
    @PostConstruct
    void init() throws IOException {
        log.info("post construct on " + this.getClass().getSimpleName());
        final File file = new File(fileName);
        if (!file.canWrite()) throw new IOException("can't write to " + file.getAbsolutePath());
    }
}
