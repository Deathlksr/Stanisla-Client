package stanis.client.utils.animation;

import lombok.Getter;
import lombok.Setter;
import stanis.client.utils.time.DeltaTracker;

@Getter
@Setter
public class EasingAnimation {
    private float value = 0;
    private float start = 0;
    private float end = 0;
    private float progress = 1;

    private Easing function = Easing.OUT_CUBIC;

    public EasingAnimation(Easing function) {
        this.function = function;
    }

    public EasingAnimation() {}

    public void reset() {
        this.value = end;
        this.start = end;
        this.progress = 1f;
    }
    
    public void update(float speed) {
        if (progress < 1f) {
            progress += speed * DeltaTracker.getMilli() / 1000;
            progress = Math.min(progress, 1f);
            
            float easedProgress = (float) function.get(progress);
            value = start + (end - start) * easedProgress;
        } else {
            value = end;
        }
    }
    
    public void setEnd(float endValue) {
        if (this.end != endValue) {
            this.start = this.value;
            this.end = endValue;
            this.progress = 0f;
        }
    }

    public boolean isAnimating() {
        return progress < 1f;
    }
    
    public void setValue(float value) {
        this.value = value;
        this.start = value;
        this.end = value;
        this.progress = 1f;
    }
}