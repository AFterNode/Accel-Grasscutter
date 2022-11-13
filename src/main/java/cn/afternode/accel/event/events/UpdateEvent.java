package cn.afternode.accel.event.events;

import com.darkmagician6.eventapi.events.Event;

import java.time.Instant;

public class UpdateEvent implements Event {
    private final Instant start;
    private final Instant end;

    public UpdateEvent(Instant start, Instant end) {
        this.start = start;
        this.end = end;
    }

    public Instant getTickStart() {
        return this.start;
    }

    public Instant getTickEnd() {
        return this.end;
    }
}
