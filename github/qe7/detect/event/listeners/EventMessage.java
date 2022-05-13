package github.qe7.detect.event.listeners;

import github.qe7.detect.event.Event;

public class EventMessage extends Event {

    String message;

    public EventMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
