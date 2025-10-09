package stanis.client.event.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.ScaledResolution;
import stanis.client.event.Event;

@Getter
@Setter
@AllArgsConstructor
public class Render2DEvent extends Event {

    ScaledResolution scaledResolution;
    int mouseX, mouseY;

}
