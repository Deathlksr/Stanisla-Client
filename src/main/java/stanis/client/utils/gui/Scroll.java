package stanis.client.utils.gui;

import lombok.Getter;
import lombok.Setter;
import stanis.client.utils.animation.EasingAnimation;

@Getter
@Setter
public class Scroll {
    private float scroll;
    private float scrollTotalHeight;
    private float visibleHeight;
    private final float scrollStep;

    public Scroll(float scrollStep) {
        this.scrollStep = scrollStep;
    }

    public void update(float scrollTotalHeight, float visibleHeight) {
        this.scrollTotalHeight = scrollTotalHeight;
        this.visibleHeight = visibleHeight;
        clampScroll();
    }

    public void handleScrollInput(double delta, boolean isHovered) {
        if (isHovered) {
            scroll += (float) (delta / 120 * scrollStep);
            clampScroll();
        }
    }

    private void clampScroll() {
        float maxScroll = Math.max(0, scrollTotalHeight - visibleHeight);
        if (scroll < -maxScroll) scroll = (int) -maxScroll;
        if (scroll > 0) scroll = 0;
    }

    public void setScroll(float scroll) {
        this.scroll = scroll;
        clampScroll();
    }

    public void reset() {
        scroll = 0;
    }

    public boolean canScroll() {
        return scrollTotalHeight > visibleHeight;
    }

    public float getScrollPercentage() {
        float maxScroll = Math.max(scrollTotalHeight - visibleHeight, 0);
        return maxScroll > 0 ? -scroll / maxScroll : 0;
    }
}