package github.qe7.detect.event.listeners;

import github.qe7.detect.event.Event;

public class EventRender3D extends Event {

    public float partialTicks;

    public EventRender3D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

}
