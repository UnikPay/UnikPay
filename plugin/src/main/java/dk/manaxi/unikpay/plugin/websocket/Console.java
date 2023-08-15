package dk.manaxi.unikpay.plugin.websocket;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.bukkit.Bukkit;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Plugin(name = "Log4JAppender", category = "Core", elementType = "appender", printObject = true)
public class Console extends AbstractAppender {
    SimpleDateFormat formatter;
    public Console() {
        super("Log4JAppender", null, null);
        formatter = new SimpleDateFormat("HH:mm:ss");
    }
    @Override
    public boolean isStarted() {
        return true;
    }
    @Override
    public void append(LogEvent event) {
        String message = event.getMessage().getFormattedMessage();
        String formattedMessage = "[" + formatter.format(new Date()) + " " + event.getLevel().toString() + "] " + message;
        Throwable throwable = event.getThrown();
        if (throwable != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            String exceptionDetails = sw.toString();
            formattedMessage += "\n" + exceptionDetails;
        }

        if (IoSocket.getSocket() != null && IoSocket.getSocket().connected()) {
            IoSocket.getSocket().emit("console", formattedMessage);
        }
    }
}