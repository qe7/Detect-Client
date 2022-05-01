package github.qe7.detect.event.listeners;

import github.qe7.detect.event.Event;

public class EventKey extends Event {

    public int code;

    public EventKey(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
