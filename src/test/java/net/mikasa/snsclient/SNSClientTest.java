package net.mikasa.snsclient;

import org.junit.Test;

public class SNSClientTest {
	
	private String serverAPIKey = "AIzaSyAbyaAs_mFLOF2YyA7gOtmZQvJGA3tORvc";
	private String applicationName = "default-demo-app-";
	private String token = "fQxnlOpF1Bk:APA91bEwRIZJZfqGG1UppFOAVTGO6ZQHSd_YUDthTyvTK1r_qG3Klcyhx7SJIZtcLVwKal9sa6wYUrgUoIyVqeUXoPbf8sHzpsfPSI4SBEW1CEPIwItVpJKltC9ADQ-qgBoswpZi34Ia";
	
	@Test
	public void testPublish()
	{
		SNSClient snsClient = new SNSClient(serverAPIKey,applicationName,token);
		snsClient.publish("This is the test message text");
	}
}
