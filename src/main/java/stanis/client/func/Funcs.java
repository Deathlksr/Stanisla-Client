package stanis.client.func;

import lombok.experimental.UtilityClass;
import stanis.client.func.impl.player.*;
//import stanis.client.func.impl.pvp.*;
//import stanis.client.func.impl.render.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@UtilityClass
public class Funcs {
    private final List<Func> list = new CopyOnWriteArrayList<>();

    private final Map<Class<? extends Func>, Boolean> stanislaClassesCache = new HashMap<>();

    public void init() {
        new AutoSprint();

        list.forEach(Funcs::onStanislaToggle);
    }

    public void register(Func func) {
        list.add(func);
    }

    public boolean isToggled(Class<? extends Func> stanisla) {
        return stanislaClassesCache.get(stanisla);
    }

    public void onStanislaToggle(Func func) {
        stanislaClassesCache.put(func.getClass(), func.isToggled());
    }
}
