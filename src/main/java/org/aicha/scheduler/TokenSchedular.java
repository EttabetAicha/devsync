package org.aicha.scheduler;

import org.aicha.model.Request;
import org.aicha.model.enums.RequestStatus;
import org.aicha.model.enums.RequestType;
import org.aicha.model.Token;
import org.aicha.service.RequestService;
import org.aicha.service.TokenService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TokenSchedular {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final TokenService tokenService = new TokenService();
    private final RequestService requestService = new RequestService();

    public void startTokenScheduler() {
        scheduler.scheduleAtFixedRate(this::resetTokens, 0, 1, TimeUnit.DAYS);
    }

    private void resetTokens() {
        List<Token> tokens = tokenService.getAllTokens();
        tokens.forEach(
                token -> {

                    if (token.getLastResetDate().isBefore(LocalDateTime.now().minusDays(1))) {
                        token.setModificationTokens(2);
                    }
                    if(token.getLastResetDate().isBefore(LocalDateTime.now().minusMonths(1))) {
                        token.setDeletionTokens(1);
                    }
                    this.updateToken(token);
                    token.setLastResetDate(LocalDateTime.now());
                    tokenService.updateToken(token);
                }
        );
    }

    private void updateToken(Token token) {
        List<Request> requests = token.getUser().getRequests();
        requests.forEach(
                request -> {
                    if (
                        request.getStatus().equals(RequestStatus.PENDING)
                        && request.getType().equals(RequestType.MODIFICATION)
                        && request.getRequestedAt().isBefore(LocalDateTime.now().minusHours(12))
                        ) {
                        request.setStatus(RequestStatus.DENIED);
                        requestService.updateRequest(request);
                        token.duplicateModificationTokens();
                    }
                }
        );
    }

    public void stopTokenScheduler() {
        scheduler.shutdown();
    }
}
