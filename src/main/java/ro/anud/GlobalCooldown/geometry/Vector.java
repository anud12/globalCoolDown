package ro.anud.GlobalCooldown.geometry;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Vector {
	private Double x;
	private Double y;

	public static Vector normalized(Point point) {
		long x = point.getX();
		long y = point.getY();

		double length = Math.sqrt(x * x + y * y);
		return Vector.builder()
				.x(x / length)
				.y(y / length)
				.build();
	}

	@Builder
	private Vector(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	public Vector streamSetX(Double x) {
		this.x = x;
		return this;
	}

	public Vector streamSetY(Double y) {
		this.y = y;
		return this;
	}
}
