package github.qe7.detect.event.impl;

import github.qe7.detect.event.Event;

public class EventRender3D extends Event {

    private float partialTicks;

    public EventRender3D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

}
