package org.feathercoin.monitoring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class FtcConfiguration implements Serializable{
    @Value("${ftc.address:@null}")
    private String ftcAddress;

    public String getFtcAddress() {
        return ftcAddress;
    }
}
