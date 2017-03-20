<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
	<Appenders>
		<Console name="Console">
			<PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %logger{36} - %msg%n" />
		</Console>
	</Appenders>
	<Loggers>
		<Logger name="nl.naturalis" level="INFO" additivity="false">
			<AppenderRef ref="Console" />
		</Logger>
		<Root level="ERROR">
			<AppenderRef ref="Console" />
		</Root>
	</Loggers>
</Configuration>