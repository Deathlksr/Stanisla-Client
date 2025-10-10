package stanis.client.ui.alt;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import stanis.client.Client;
import stanis.client.alt.Account;
import stanis.client.alt.microsoft.Auth;
import stanis.client.alt.microsoft.MicrosoftAuthCallback;
import stanis.client.ui.buttons.Button;
import stanis.client.ui.buttons.TextButton;
import stanis.client.utils.animation.Easing;
import stanis.client.utils.animation.EasingAnimation;
import stanis.client.utils.color.ColorUtils;
import stanis.client.utils.generate.NameGenerator;
import stanis.client.utils.gui.GuiUtils;
import stanis.client.utils.gui.Scroll;
import stanis.client.utils.render.Rect;
import stanis.client.utils.render.RenderUtils;
import stanis.client.utils.render.font.ClientFontRenderer;
import stanis.client.utils.render.font.Fonts;
import stanis.client.utils.render.shader.QuadRadius;
import stanis.client.utils.render.shader.impl.BackgroundUtils;
import stanis.client.utils.render.shader.impl.RoundedUtils;
import stanis.client.utils.render.stencil.StencilUtils;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class AltManagerScreen extends GuiScreen {

    TextButton textButton;

    List<Account> accounts = new CopyOnWriteArrayList<>();

    @Override
    public void initGui() {
        super.initGui();
        ScaledResolution sc = new ScaledResolution(mc);
        textButton = new TextButton(0, 50, sc.getScaledHeight() - 10 - 75, 100, 20);
        buttonList.add(new Button(1,"Login", 50, sc.getScaledHeight() - 10 - 50, 100, 20));
        buttonList.add(new Button(2,"Microsoft", 50, sc.getScaledHeight() - 10 - 25, 100, 20));

        textButton.setFocused(true);
    }

    Scroll scroll = new Scroll(30);

    EasingAnimation scrollAnim = new EasingAnimation();
    int scrollTotalHeight;

    Account selectedAccount, lastClickedAccount;
    long lastClickedTime;

    String statusText = "";

    ResourceLocation removeLogo = Client.of("textures/logo/exit.png");

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int currentScroll = Mouse.getDWheel();

        ScaledResolution sc = new ScaledResolution(mc);
        ClientFontRenderer fontRenderer = Fonts.fonts.get("JetBrains");

        BackgroundUtils.run();

        Rect rect = Rect.widthHeight(
            (sc.getScaledWidth() - 200 - 10),
            10,
            200,
            (sc.getScaledHeight() - 20)
        );

        Rect buttonsRect = Rect.widthHeight(
            45,
            sc.getScaledHeight() - 5 - 75 - 10,
            110,
            80
        );

        Color rectsColor = new Color(0,0,0,0.5f);

        scroll.update(scrollTotalHeight, sc.getScaledHeight() - 25);
        scroll.handleScrollInput(currentScroll, GuiUtils.isHoveredRect(mouseX, mouseY, rect));

        scrollAnim.update(3, Easing.OUT_CUBIC);
        scrollAnim.setEnd(scroll.getScroll());

        RoundedUtils.drawRect(rect, 10, rectsColor);
        RoundedUtils.drawRect(buttonsRect, 15, rectsColor);

        updateDeleting();

        String currentSession = "Current Session: " + mc.getSession().getUsername();
        String currentType = "Type: " + mc.getSession().getSessionType();
        fontRenderer.drawString(currentSession, 5,5, Color.WHITE);
        fontRenderer.drawString(currentType, 5,5 + 10 + 1, Color.WHITE);

        float statusX = 45 + buttonsRect.width() / 2f;
        float statusY = sc.getScaledHeight() - 105;

        fontRenderer.drawCenteredString(statusText, statusX, statusY, Color.WHITE);

        StencilUtils.setUpTexture(rect, 10);
        StencilUtils.writeTexture();

        scrollTotalHeight = 0;
        float offset = scrollAnim.getValue();
        for (Account account : accounts) {
            EasingAnimation hover = account.getHoverAnim();

            updateAccountAnimations(account);

            Rect accountRect = Rect.widthHeight(
                (sc.getScaledWidth() - 200 - 10) + 5 - hover.getValue(),
                offset + 15 - hover.getValue(),
                190 + hover.getValue() * 2,
                25 + hover.getValue() * 2
            );

            Rect deleteRect = Rect.widthHeight(
                (sc.getScaledWidth() - 200 - 10 + 175) + 5 - hover.getValue(),
                offset + 15 + 5f - hover.getValue(),
                15 + hover.getValue() * 2,
                15 + hover.getValue() * 2
            );

            QuadRadius radius = getAccountRectRadius(account == accounts.getFirst(), account == accounts.getLast());

            boolean hoveredAccount = GuiUtils.isHovered(mouseX, mouseY, accountRect.x(), accountRect.y(), accountRect.width(), accountRect.height());
            boolean hoveredRemove = GuiUtils.isHoveredRect(mouseX, mouseY, deleteRect);

            account.getHoverAnim().setEnd(hoveredAccount || isSelected(account));
            account.getSelectAnim().setEnd(isSelected(account) || isSession(account));
            if (!account.isDeleting()) account.getDeleteAnim().setEnd(1);

            Color accountRectColor = ColorUtils.interpolateColor(new Color(0,0,0,0.5f * account.getDeleteAnim().getValue()), new Color(0,0,0,0.6f * account.getDeleteAnim().getValue()), hover.getValue());
            Color textColor = ColorUtils.interpolateColor(new Color(1f,1f,1f, account.getDeleteAnim().getValue()), new Color(0f,1f,0f, account.getDeleteAnim().getValue()), account.getSelectAnim().getValue());
            Color removeColor = hoveredRemove ? new Color(1f,0,0,account.getDeleteAnim().getValue()) : new Color(1f,1f,1f,account.getDeleteAnim().getValue());

            RoundedUtils.drawRect(accountRect, radius, accountRectColor);
            fontRenderer.drawString(account.getName() + " " + account.getType(), accountRect.x() + 5, accountRect.y() + accountRect.height() / 2f - 2.5f, textColor);

            ColorUtils.glColor(removeColor);
            RenderUtils.drawImage(removeLogo, deleteRect.x(), deleteRect.y(), deleteRect.width(), deleteRect.height(), true);

            scrollTotalHeight += 30;
            offset += 30 * account.getDeleteAnim().getValue();
        }

        StencilUtils.endWriteTexture();

        super.drawScreen(mouseX, mouseY, partialTicks);

        textButton.drawTextBox();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        textButton.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        ScaledResolution sc = new ScaledResolution(mc);

        float offset = scrollAnim.getValue();
        for (Account account : accounts) {
            EasingAnimation hover = account.getHoverAnim();

            Rect accountRect = Rect.widthHeight(
                (sc.getScaledWidth() - 200 - 10) + 5 - hover.getValue(),
                offset + 15 - hover.getValue(),
                190 + hover.getValue() * 2,
                25 + hover.getValue() * 2
            );

            if (GuiUtils.isHoveredRect(mouseX, mouseY, accountRect)) {
                long currentTime = System.currentTimeMillis();

                Rect deleteRect = Rect.widthHeight(
                    (sc.getScaledWidth() - 200 - 10 + 175) + 5 - hover.getValue(),
                    offset + 15 + 5f - hover.getValue(),
                    15 + hover.getValue() * 2,
                    15 + hover.getValue() * 2
                );

                if (GuiUtils.isHoveredRect(mouseX, mouseY, deleteRect)) {
                    removeAccount(account);
                    return;
                }

                if (getLoginCondition(account, currentTime)) {
                    if (account.getUuid() != null) {
                        loginMicrosoft(account);
                    } else {
                        loginOffline(account);
                    }
                } else {
                    toggleAccount(account);
                }

                lastClickedTime = currentTime;
                lastClickedAccount = account;
            }

            offset += 30;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 1 -> {
                if (!textButton.getText().isEmpty()) {
                    String accountName = textButton.getText();
                    boolean accountExists = accounts.stream().anyMatch(acc -> acc.getName().equals(accountName));

                    if (!accountExists) {
                        Account account = new Account(accountName);
                        addAccount(account);
                        loginOffline(account);
                        selectedAccount = account;
                    }
                    textButton.setText("");
                } else if (selectedAccount != null) {
                    if (selectedAccount.getUuid() != null) {
                        loginMicrosoft(selectedAccount);
                    } else {
                        loginOffline(selectedAccount);
                    }
                } else {
                    Account account = new Account(NameGenerator.generateName(16));
                    loginOffline(account);
                }
            }

            case 2 -> {
                final MicrosoftAuthCallback callback = new MicrosoftAuthCallback();

                CompletableFuture<Account> future = callback.start((s, _) -> updateStatus(s));

                Sys.openURL(MicrosoftAuthCallback.url);

                future.whenCompleteAsync((account, error) -> {
                    if (error != null) {
                        updateStatus("Failed added account: " + error + ".");
                    } else {
                        addAccount(new Account(account.getName(), account.getRefreshToken(), account.getUuid()));
                        selectedAccount = account;
                    }
                });
            }
        }
    }

    private boolean isSession(Account account) {
        return mc.getSession().getUsername().equals(account.getName());
    }

    private void updateStatus(String status) {
        statusText = status;
    }

    private void updateDeleting() {
        accounts.removeIf(this::getDeleteCondition);
    }

    private QuadRadius getAccountRectRadius(boolean first, boolean last) {
        return new QuadRadius(
            first ? 7.5f : 2,
            last ? 7.5f : 2,
            first ? 7.5f : 2,
            last ? 7.5f : 2
        );
    }

    private boolean isSelected(Account account) {
        return selectedAccount != null && selectedAccount.equals(account);
    }

    private boolean getLoginCondition(Account account, long currentTime) {
        return lastClickedAccount == account && currentTime - lastClickedTime < 300;
    }

    private boolean getDeleteCondition(Account account) {
        return account.isDeleting() && !account.getDeleteAnim().isAnimating();
    }

    private void addAccount(Account account) {
        accounts.add(account);
        account.getDeleteAnim().setEnd(1);
        selectedAccount = account;
        updateStatus("Successful added account: " + account.getName() + ".");
    }

    private void removeAccount(Account account) {
        account.getDeleteAnim().setEnd(0);
        account.setDeleting(true);
        selectedAccount = null;
        updateStatus("Successful removed account: " + account.getName() + ".");
    }

    private void loginOffline(Account account) {
        mc.getSession().setUsername(account.getName());
        updateStatus("Successful login account: " + account.getName() + ".");
    }

    private void loginMicrosoft(Account account) {
        new Thread(() -> {
            try {
                Map.Entry<String, String> authRefreshTokens = Auth.refreshToken(account.getRefreshToken());
                String xblToken = Auth.authXBL(authRefreshTokens.getKey());
                Map.Entry<String, String> xstsTokenUserhash = Auth.authXSTS(xblToken);
                String accessToken = Auth.authMinecraft(xstsTokenUserhash.getValue(), xstsTokenUserhash.getKey());

                mc.setSession(new Session(account.getName(),
                    account.getUuid(),
                    accessToken, "msa"));
                selectedAccount = account;
                updateStatus("Successful login account: " + account.getName() + ".");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                updateStatus("Failed login account: " + e.getMessage() + ".");
            }
        }).start();
    }

    private void toggleAccount(Account account) {
        selectedAccount = selectedAccount == account ? null : account;
    }

    private void updateAccountAnimations(Account account) {
        account.getDeleteAnim().update(4f, Easing.OUT_CUBIC);
        account.getHoverAnim().update(0.7f, Easing.OUT_ELASTIC);
        account.getSelectAnim().update(4f,Easing.OUT_CUBIC);
    }
}
