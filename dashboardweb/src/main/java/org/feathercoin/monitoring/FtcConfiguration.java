package org.feathercoin.monitoring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FtcConfiguration {
    @Value("${ftc.address:@null}")
    private String ftcAddress;

    public String getFtcAddress() {
        return ftcAddress;
    }
}
