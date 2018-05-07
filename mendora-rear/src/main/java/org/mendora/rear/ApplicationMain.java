package org.mendora.rear;

import org.mendora.kernel.KernelLauncher;
import org.mendora.kernel.config.KernelConfig;
import org.mendora.kernel.config.MicroService;

import java.net.URL;

/**
 * created by:xmf
 * date:2018/5/7
 * description:
 */
public class ApplicationMain {
    public static void main(String[] args) {
        URL location = ApplicationMain.class.getProtectionDomain().getCodeSource().getLocation();
        KernelConfig config = new KernelConfig();
        config.setClassLoader(ApplicationMain.class.getClassLoader());
        config.setRootUrl(location);
        config.setMicroService(MicroService.REAR);
        config.setScanFacade(false);
        config.setScanVerticle(false);
        KernelLauncher.launch(config);
    }
}
