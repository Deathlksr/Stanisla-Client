package stanis.client.event;

import lombok.Getter;

@Getter
public class Event {
    private boolean canceled;

    public void cancel() {
        canceled = true;
    }

    public void call() {
        EventCaller.call(this);
    }
}
