package stanis.client.func;

import lombok.Getter;
import stanis.client.event.Event;
import stanis.client.event.EventListener;
import stanis.client.utils.access.Access;

import java.lang.annotation.AnnotationFormatError;

@Getter
public class Func implements EventListener, Access {
    private final String name, desc;
    private final Category category;

    private boolean toggled = true;

    public Func() {
        Info info = getClass().getAnnotation(Info.class);

        if (info == null) throw new AnnotationFormatError("Not found \"Info\" annotation in class: " + getClass().getSimpleName());

        name = info.name();
        desc = info.desc();
        category = info.category();

        if (info.useEvents()) registerToEvents();

        Funcs.register(this);
    }

    public void toggle() {
        if (toggled) onDisable();
        if (!toggled) onEnable();
        toggled = !toggled;
        Funcs.onStanislaToggle(this);
    }

    public void onEnable() {}

    public void onDisable() {}

    @Override
    public void listen(Event event) {}

    @Override
    public boolean listen() {
        return mc.thePlayer != null && mc.theWorld != null;
    }
}
