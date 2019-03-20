package com.napptilus.crewmanager.aspects;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LogingAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private long startTime;

	@Before("execution (* com.napptilus.crewmanager.api.*.*(..))")
	public void startLog(JoinPoint joinPoint) {
		startTime = System.currentTimeMillis();

		StringBuffer data = new StringBuffer(250);
		data.append("- >> Input - ");
		data.append("[" + Thread.currentThread().getId() + "] - ");
		data.append("(" + Arrays.toString(joinPoint.getArgs()) + ")");

		logger.info(data.toString());
	}

	@AfterReturning(value = "execution (* com.napptilus.crewmanager.api.*.*(..))", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {

		StringBuffer output = new StringBuffer(250);

		output.append("- << Output - ");
		output.append("[" + Thread.currentThread().getId() + "] - ");
		output.append(joinPoint.getSignature().getName());

		if (result != null) {
			if (result.toString().length() > 50)
				output.append("(" + result.toString().substring(0, 50) + "...)");
			else
				output.append("(" + result.toString() + ")");
		} else {
			output.append("(null)");
		}

		output.append(" - " + getElapsedTime(startTime));

		logger.info(output.toString());
	}

	@AfterThrowing(pointcut = "execution (* com.napptilus.crewmanager.api.*.*(..))", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {

		StringBuffer output = new StringBuffer(250);
		
		output.append("- << Output - ");
		output.append("[" + Thread.currentThread().getId() + "] - ");
		output.append(joinPoint.getSignature().getName());
		output.append(" - Exception: " + e.getMessage());
		output.append(" - " + getElapsedTime(startTime));
		
		logger.error(output.toString());

	}

	private String getElapsedTime(Long startTime) {
		String elapsedTime = null;
		Long time = (System.currentTimeMillis() - startTime);
		Long hour = TimeUnit.MILLISECONDS.toHours(time);
		Long hourMillis = time - TimeUnit.HOURS.toMillis(hour);
		Long minutes = TimeUnit.MILLISECONDS.toMinutes(hourMillis);
		Long minutesMillis = hourMillis - TimeUnit.MINUTES.toMillis(minutes);
		Long seconds = TimeUnit.MILLISECONDS.toSeconds(minutesMillis);
		Long secondMillis = minutesMillis - TimeUnit.SECONDS.toMillis(seconds);
		Long millis = TimeUnit.MILLISECONDS.toMillis(secondMillis);

		if (hour > 0)
			elapsedTime = String.format("%d hour, %d min, %d sec, %d millis", hour, minutes, seconds, millis);
		else {
			if (minutes > 0)
				elapsedTime = String.format("%d min, %d sec, %d millis", minutes, seconds, millis);
			else {
				if (seconds > 0)
					elapsedTime = String.format("%d sec, %d millis", seconds, millis);
				else
					elapsedTime = String.format("%d millis", millis);
			}
		}
		return elapsedTime;
	}
}
