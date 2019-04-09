package ro.anud.globalCooldown.validation.validationChain;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ValidationChainResult {
    private String field;
    private String errorCode;
}
