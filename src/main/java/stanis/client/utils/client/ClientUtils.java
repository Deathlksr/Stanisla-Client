package stanis.client.utils.client;

import net.minecraft.util.ChatComponentText;
import stanis.client.Client;
import stanis.client.utils.access.Access;

public class ClientUtils implements Access {

    static String prefixLog = "§2Stanislas §f→ ";

    /**
     * @param message Объект, который будет выведен в чат игры.
     */
    public static void chatLog(Object message) {
        if (mc.thePlayer == null) {
            System.out.println(Client.NAME + " → " + message);
        } else {
            mc.thePlayer.addChatMessage(new ChatComponentText(prefixLog + message));
        }
    }
}
