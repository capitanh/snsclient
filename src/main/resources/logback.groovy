import static ch.qos.logback.classic.Level.*
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.status.OnConsoleStatusListener

statusListener(OnConsoleStatusListener)
context.getStatusManager().getCopyOfStatusListenerList().each {
	it.context = context
	it.start()
}

appender("CONSOLE", ConsoleAppender) {
	encoder(PatternLayoutEncoder) { pattern = "%boldYellow([%d{dd-MM-yyyy HH:mm:ss}]) %highlight([%level]) %boldGreen([%logger]) - %msg%n" }
}

logger 'org.eclipse.jetty', INFO
root INFO, ['CONSOLE']
