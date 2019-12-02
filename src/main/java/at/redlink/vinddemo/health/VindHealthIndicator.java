package at.redlink.vinddemo.health;

import com.rbmhtechnology.vind.api.SearchServer;
import com.rbmhtechnology.vind.api.result.StatusResult;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class VindHealthIndicator extends AbstractHealthIndicator {

    private final SearchServer searchServer;

    public VindHealthIndicator(SearchServer searchServer) {
        this.searchServer = searchServer;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        final StatusResult ping = this.searchServer.getBackendStatus();
        switch (ping.getStatus()) {
            case DOWN:
                builder.down();
                break;
            case UP:
                builder.up();
                break;
            default:
                builder.unknown()
                        .withDetail("_status", ping.getStatus());
        }

        builder.withDetail("details", ping.getDetails());
    }
}
