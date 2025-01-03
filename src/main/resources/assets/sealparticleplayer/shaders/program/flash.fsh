#version 150

#moj_import <lodestone:common_math.glsl>
// The game's render output
uniform sampler2D DiffuseSampler;
// Info from the instance buffer
uniform samplerBuffer DataBuffer;
uniform int InstanceCount;
// The texture coordinate represented as a 2D vector (x,y)
in vec2 texCoord;
// The output color of each pixel represented as a 4D vector (r,g,b,a)
out vec4 fragColor;

void main() {
		// Extract the original color of the pixel from the DiffuseSampler
		vec4 original = texture(DiffuseSampler, texCoord);

		// Assuming only one instance is needed
		int index = 0; // First instance
		// 0-2: color, 3: intensity
		vec3 color = fetch3(DataBuffer, index);
		float intensity = fetch(DataBuffer, index + 3);

		if (intensity <= 0.0) {
			// If the intensity is zero, just output the original color
			fragColor = original;
			return;
		}

		// Calculate the distance from the center of the screen
		vec2 center = vec2(0.5, 0.5);
		float distance = length(texCoord - center);

		// Adjust the intensity based on the distance
		float adjustedIntensity = intensity * (1.0 - distance);

		// Combine the original image with the adjusted color
		fragColor = mix(original, vec4(color, 1.0), adjustedIntensity);
}