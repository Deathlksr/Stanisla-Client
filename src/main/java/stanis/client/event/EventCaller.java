package stanis.client.event;

import lombok.experimental.UtilityClass;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@UtilityClass
public class EventCaller {
    private final List<EventListener> listeners = new CopyOnWriteArrayList<>();

    public void register(EventListener listener) {
        listeners.add(listener);
    }

    public void call(Event event) {
        for (EventListener listener : listeners) {
            if (!listener.listen()) continue;
            listener.listen(event);
        }
    }
}
