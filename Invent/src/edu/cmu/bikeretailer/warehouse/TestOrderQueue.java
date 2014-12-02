package edu.cmu.bikeretailer.warehouse;

import java.util.Random;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class TestOrderQueue {
	
	public static void main(String[] args){
		
		AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        AmazonSQS sqs = new AmazonSQSClient(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        sqs.setRegion(usWest2);
        
        String myQueueUrl = null;
        // List queues
        System.out.println("Listing all queues in your account.\n");
        for (String queueUrl : sqs.listQueues().getQueueUrls()) {
        	if(queueUrl.contains("RetailOrderQueue"))
        		myQueueUrl = queueUrl;
        }
        System.out.println();

        // Send a message
        System.out.println("Sending a message to MyQueue.\n");
        Random rd = new Random();
        for(int i = 1; i<=1000; i++){
        	sqs.sendMessage(new SendMessageRequest(myQueueUrl, i+"||"+rd.nextInt(10000-1+1)+1+"||"+1));
        }
	}

}
