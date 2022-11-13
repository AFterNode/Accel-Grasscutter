package cn.afternode.accel.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EventWrappers {
    private final List<EventWrapper> wrappers;
    private final Logger logger;

    public EventWrappers() {
        wrappers = new ArrayList<>();
        logger = LoggerFactory.getLogger("AccelEventWrappers");
    }

    public void init() {
        logger.info("Loading event wrappers");

        EventWrapper[] prepare = {

        };

        for (EventWrapper wrapper : prepare) {

        }
    }
}
