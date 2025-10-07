package stanis.client.event;

public interface EventListener {
    boolean listen();
    void listen(Event event);

    default void registerToEvents() {
        EventCaller.register(this);
    }
}
