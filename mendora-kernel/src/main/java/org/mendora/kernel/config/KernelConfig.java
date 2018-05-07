package org.mendora.kernel.config;

import lombok.Data;

import java.net.URL;
import java.util.List;

/**
 * Created by kam on 2018/5/6.
 */
@Data
public class KernelConfig {
    private URL rootUrl;
    private ClassLoader classLoader;
    private MicroService microService;
    private boolean scanVerticle;
    private boolean scanFacade;
    private List<AopEntry> aopEntries;
}
