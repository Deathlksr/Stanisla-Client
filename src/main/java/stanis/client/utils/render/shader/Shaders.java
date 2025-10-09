package stanis.client.utils.render.shader;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.minecraft.util.ResourceLocation;
import stanis.client.Client;

@UtilityClass
public class Shaders {

    @Getter private Shader rounded, background, blur;

	public void init() {
		rounded = new Shader(getShaderSource("rounded.glsl"), getShaderSource("vertex.txt"));
		background = new Shader(getShaderSource("background.glsl"), getShaderSource("vertex.txt"));
		blur = new Shader(getShaderSource("blur.glsl"), getShaderSource("vertex.txt"));
	}

	private ResourceLocation getShaderSource(String name) {
		return Client.of("shaders/" + name);
	}
}