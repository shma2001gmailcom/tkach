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
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
    protected final Logger log;
    private final ReadWriteLock lock;
    
    @Inject
    FileEventLogger(@Named final String fileName, final Logger log) {
        this.fileName = fileName;
        this.log = log;
        this.lock = new ReentrantReadWriteLock();
    }
    
    public void logEvent(final Event event) {
        lock.writeLock().lock();
        try {
            writeStringToFile(new File(fileName), event.toString() + "\n", true);
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    public String getDetails() throws IOException {
        lock.readLock().lock();
        try {
            return readFileToString(new File(fileName))
                    .replaceAll("\norg\\.misha\\.event\\.Event@", "<p>org.misha.event.Event@");
        } finally {
            lock.readLock().unlock();
        }
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
