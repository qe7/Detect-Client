package github.qe7.detect.event.impl;

import github.qe7.detect.event.Event;

public class EventChat extends Event {

    public String message;

    public EventChat(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
