package org.mendora.router;

import org.mendora.kernel.KernelLauncher;

import java.net.URL;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class ApplicationMain {
    public static void main(String[] args) {
        URL location = ApplicationMain.class.getProtectionDomain().getCodeSource().getLocation();
        KernelLauncher.launch(location, ApplicationMain.class.getClassLoader());
    }
}
