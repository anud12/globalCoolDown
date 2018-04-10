package ro.anud.globalcooldown.entity;

import lombok.*;
import ro.anud.globalcooldown.geometry.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@ToString
public class Pawn {
	@Id
	private Long id;
	private String name;
	@Column(name = "val")
	private int value;
	private Long version;
	private Long speed;
	private Long x;
	private Long y;
	private Long characterCode;
	private Long userId;

	public Pawn() {

	}

	public Point getPoint() {
		return new Point(x, y);
	}

	public void setPoint(Point point) {
		this.x = point.getX();
		this.y = point.getY();
	}

	public Pawn duplicate() {
		return Pawn.builder()
				.id(this.id)
				.name(this.name)
				.value(this.value)
				.build();
	}

	public Pawn streamSetId(Long id) {
		this.id = id;
		return this;
	}

	public Pawn streamSetName(String name) {
		this.name = name;
		return this;
	}

	public Pawn streamSetValue(int value) {
		this.value = value;
		return this;
	}

	public Pawn streamSetPoint(Point point) {
		this.x = point.getX();
		this.y = point.getY();
		return this;
	}

	public Pawn streamSetSpeed(Long speed) {
		this.speed = speed;
		return this;
	}


}
