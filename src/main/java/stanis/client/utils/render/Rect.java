package stanis.client.utils.render;

public record Rect(float x, float y, float x2, float y2) {

    public float width() {
        return x2 - x;
    }

    public float height() {
        return y2 - y;
    }

    public static Rect absPos(float x, float y, float x2, float y2) {
        return new Rect(x, y, x2, y2);
    }

    public static Rect widthHeight(float x, float y, float width, float height) {
        return new Rect(x, y,x + width,y + height);
    }
}
