package github.qe7.detect.event.listeners;

import github.qe7.detect.event.Event;

public class EventRender2D extends Event {

    public double width, height;

    public EventRender2D(double width, double height) {
        this.width = width / 2;
        this.height = height / 2;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

}
