package org.mendora.kernel.config;

import lombok.Data;

import java.net.URL;
import java.util.List;

/**
 * Created by kam on 2018/5/6.
 */
@Data
public class KernelConfig {
    // app root path.
    private URL rootUrl;
    // default class loader.
    private ClassLoader classLoader;
    // micro service type.
    private MicroService microService;
    // want to scan "verticle" group?
    private boolean scanVerticle;
    // want to scan "facade" group?
    private boolean scanFacade;
    // add aop entry group.
    private List<AopEntry> aopEntries;
}
