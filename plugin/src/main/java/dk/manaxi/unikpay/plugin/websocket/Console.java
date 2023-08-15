package dk.manaxi.unikpay.plugin.websocket;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Plugin(name = "Log4JAppender", category = "Core", elementType = "appender", printObject = true)
public class Console extends AbstractAppender {
    SimpleDateFormat formatter;
    public Console() {
        super("Log4JAppender", null, null, false, null);
        formatter = new SimpleDateFormat("HH:mm:ss");
    }
    @Override
    public boolean isStarted() {
        return true;
    }
    @Override
    public void append(LogEvent event) {
        if(!(IoSocket.getSocket() != null && IoSocket.getSocket().connected())) return;
        String message = event.getMessage().getFormattedMessage();
        String formattedMessage = "[" + formatter.format(new Date()) + " " + event.getLevel().toString() + "] " + message;

        IoSocket.getSocket().emit("console", formattedMessage);

        Throwable throwable = event.getThrown();
        if (throwable != null) {
            // get the throwable as a string[]
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            String[] lines = sw.toString().split(System.lineSeparator());
            for (String line : lines) {
                // send each line to the client
                IoSocket.getSocket().emit("console", line);
            }
        }
    }
}