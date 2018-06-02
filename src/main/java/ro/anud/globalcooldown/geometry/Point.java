package ro.anud.globalcooldown.geometry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Point {

	private long x;
	private long y;

	public Point streamSetX(long x) {
		this.x = x;
		return this;
	}

	public Point streamSetY(long y) {
		this.y = y;
		return this;
	}

	public double distance(Point point) {
		double dx = x - point.x;
		double dy = y - point.y;

		return Math.sqrt(dx * dx + dy * dy);
	}

	public Point streamSubstract(Point point) {
		x = x - point.getX();
		y = y - point.getY();
		return this;
	}

	public Point duplicate() {
		return new Point(x, y);
	}

	public Point streamTranspose(Vector direction, Long distance) {
		return this
				.streamSetX((long) (direction.getX() * distance + x))
				.streamSetY((long) (direction.getY() * distance + y));
	}

}
