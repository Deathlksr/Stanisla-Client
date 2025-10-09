package stanis.client.alt;

import lombok.Getter;
import lombok.Setter;
import stanis.client.utils.animation.EasingAnimation;

@Getter
@Setter
public class Account {

    String name;
    String refreshToken, uuid;

    EasingAnimation anim = new EasingAnimation();

    AccountType type;

    boolean deleting;

    public Account(String name) {
        this.type = AccountType.OFFLINE;
        this.name = name;
    }

    public Account(String name, String refreshToken, String uuid) {
        this.type = AccountType.MICROSOFT;
        this.name = name;
        this.refreshToken = refreshToken;
        this.uuid = uuid;
    }

    public enum AccountType {
        OFFLINE, MICROSOFT
    }
}
