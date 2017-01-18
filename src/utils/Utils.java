package utils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Utils {
    public static void openerURL(String URL){
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
    }
}
