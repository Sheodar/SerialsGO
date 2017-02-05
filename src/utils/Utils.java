package utils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class Utils {
    public static void openerURL(String URL,String path) throws IOException {
        if (Objects.equals(path, "1")){
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            try {
                URI url = new URI(URL);
                if (desktop != null) {
                    desktop.browse(url);
                }
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }else {
            String request = String.format("%s %s", path, URL);
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(request);
        }


    }
}
