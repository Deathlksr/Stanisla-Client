package stanis.client.utils.render.shader;

public record QuadRadius(float leftUp, float leftDown, float rightDown, float rightUp) {

    public static QuadRadius one(float radius) {
        return new QuadRadius(radius, radius, radius, radius);
    }

}
