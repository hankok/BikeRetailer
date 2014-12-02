
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class OrderQueueManager {
	
	AWSCredentials credentials = null;
	AmazonSQS sqs = null;
	DBHelper dbHelper = null;
	Timer timer;
	
	public OrderQueueManager(DBHelper dbHelper){
		this.dbHelper = dbHelper;
		Connect();
		 timer = new Timer();
	     timer.schedule(new BackgroundTask(),0, 2*1000);
	}
	
	public void Connect(){		
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (~/.aws/credentials), and is in valid format.",e);
        }

        sqs = new AmazonSQSClient(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        sqs.setRegion(usWest2);
	}
	
	public List<String> processOrderQueue(){
		List<String> valuesList= new ArrayList<>();
		String orderQueueURL = null;
		for (String queueUrl : sqs.listQueues().getQueueUrls()) {
			if(queueUrl.contains("RetailOrderQueue"))
				orderQueueURL = queueUrl;
        }
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(orderQueueURL);
		receiveMessageRequest.setWaitTimeSeconds(20);
		receiveMessageRequest.setMaxNumberOfMessages(10);
		List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
		for(Message message: messages){
			valuesList.add(message.getBody());
			String messageRecieptHandle = message.getReceiptHandle();
            sqs.deleteMessage(new DeleteMessageRequest(orderQueueURL, messageRecieptHandle));
		}
		return valuesList;
	}
	
	public void postOrderResult(List<String> valuesList) throws SQLException{
		String orderQueueResultURL = null;
		for (String queueUrl : sqs.listQueues().getQueueUrls()) {
			if(queueUrl.contains("RetailOrderResponseQueue"))
				orderQueueResultURL = queueUrl;
        }
		for(String value: valuesList){
			System.out.println(value);
			String[] content = value.split("#");
			int orderId = Integer.parseInt(content[0]);
			int modelNo = Integer.parseInt(content[1]);
			int quantity = Integer.parseInt(content[2]);
			boolean success = dbHelper.updateInventory(modelNo+"", quantity);
			int newQuantity = dbHelper.getQuantity(modelNo);
			sqs.sendMessage(new SendMessageRequest(orderQueueResultURL, orderId+"#"+success+"#"+newQuantity));
		}
	}

	class BackgroundTask extends TimerTask{

		@Override
		public void run() {
			System.out.println("Started");
			List<String> values= processOrderQueue();
			try {
				postOrderResult(values);
				System.out.println("Ended");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
