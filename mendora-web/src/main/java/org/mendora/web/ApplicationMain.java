package org.mendora.web;

import org.mendora.facade.annotation.aop.Monitor;
import org.mendora.web.aop.MonitorMethodInterceptor;
import org.mendora.kernel.KernelLauncher;
import org.mendora.kernel.config.AopEntry;
import org.mendora.kernel.config.KernelConfig;
import org.mendora.kernel.config.MicroService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class ApplicationMain {
    public static void main(String[] args) {
        URL location = ApplicationMain.class.getProtectionDomain().getCodeSource().getLocation();
        KernelConfig config = new KernelConfig();
        config.setClassLoader(ApplicationMain.class.getClassLoader());
        config.setRootUrl(location);
        config.setMicroService(MicroService.WEB);
        config.setScanVerticle(false);
        config.setScanFacade(true);
        List<AopEntry> aopEntries = new ArrayList<>();
        aopEntries.add(new AopEntry(Monitor.class, new MonitorMethodInterceptor()));
        config.setAopEntries(aopEntries);
        KernelLauncher.launch(config);
    }
}
