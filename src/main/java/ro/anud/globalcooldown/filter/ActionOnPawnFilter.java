package ro.anud.globalcooldown.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ro.anud.globalcooldown.model.action.ActionOnPawn;
import ro.anud.globalcooldown.repository.PawnRepository;
import ro.anud.globalcooldown.security.model.SpringSecurityUser;

import java.util.Objects;
import java.util.Optional;

@Component
public class ActionOnPawnFilter {

	private final PawnRepository pawnRepository;

	public ActionOnPawnFilter(final PawnRepository pawnRepository) {
		this.pawnRepository = Objects.requireNonNull(pawnRepository, "pawnRepository must not be null");
	}

	public Optional filter(ActionOnPawn actionOnPawn) {
		SpringSecurityUser springSecurityUser = (SpringSecurityUser) SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal();
		return pawnRepository.findOneById(actionOnPawn.toEntity().getPawnId())
				.map(pawn -> {
					if (springSecurityUser.getId().equals(pawn.getUserId())) {
						return Optional.of(true);
					}
					return Optional.empty();
				})
				.orElse(Optional.empty());

	}
}
