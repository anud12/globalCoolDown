package ro.anud.globalCooldown.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class RGBA {
    private Double red;
    private Double green;
    private Double blue;
    private Double alpha;
}
