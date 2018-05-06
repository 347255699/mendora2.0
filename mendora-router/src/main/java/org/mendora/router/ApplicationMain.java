package org.mendora.router;

import org.mendora.kernel.KernelLauncher;
import org.mendora.kernel.config.KernelConfig;
import org.mendora.kernel.config.MicroService;

import java.net.URL;

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
        config.setSanVerticle(false);
        KernelLauncher.launch(config);
    }
}
