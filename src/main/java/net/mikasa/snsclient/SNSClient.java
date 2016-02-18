package net.mikasa.snsclient;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.sns.model.MessageAttributeValue;

import net.mikasa.snsclient.AmazonSNSClientWrapper;
import net.mikasa.snsclient.MessageGenerator.Platform;

public class SNSClient {

	private Logger log = LoggerFactory.getLogger(SNSClient.class.getName());
	private AmazonSNSClientWrapper snsClientWrapper;
	private String serverAPIKey;
	private String applicationName;
	private String token;
	private String awsEndPoint;
	
	public SNSClient(String serverAPIKey,String applicationName,String token){
		
		try{

			this.serverAPIKey = serverAPIKey;
			this.applicationName = applicationName;
			this.token = token;
			
			Configuration config = new PropertiesConfiguration("AwsCredentials.properties");
			awsEndPoint = config.getString("awsEndPoint");
			
		} catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	
	
	public static final Map<Platform, Map<String, MessageAttributeValue>> attributesMap = new HashMap<Platform, Map<String, MessageAttributeValue>>();
	static {
		attributesMap.put(Platform.GCM, null);
		attributesMap.put(Platform.APNS, null);
		attributesMap.put(Platform.APNS_SANDBOX, null);
	}
	public void publish(String payload)
	{
		try
		{
			AmazonSNS sns = new AmazonSNSClient(new ClasspathPropertiesFileCredentialsProvider());
			snsClientWrapper = new AmazonSNSClientWrapper(sns);
			sns.setEndpoint(awsEndPoint);
			snsClientWrapper.demoNotification(Platform.GCM, "", serverAPIKey, token, applicationName, attributesMap, payload);
		}
		
		catch (AmazonServiceException ase){
			log.error ("Caught an AmazonServiceException, which means your request made it to Amazon SNS, but was rejected with an error response for some reason.");
			log.error("Error Message:    " + ase.getMessage());
			log.error("HTTP Status Code: " + ase.getStatusCode());
			log.error("AWS Error Code:   " + ase.getErrorCode());
			log.error("Error Type:       " + ase.getErrorType());
			log.error("Request ID:       " + ase.getRequestId());			
		}
		
		catch (AmazonClientException ace) {
			log.error("Caught an AmazonClientException, which means the client encountered a serious internal problem while trying to communicate with SNS, such as not being able to access the network.");
			log.error("Error Message: " + ace.getMessage());
		}
	}		
}	


