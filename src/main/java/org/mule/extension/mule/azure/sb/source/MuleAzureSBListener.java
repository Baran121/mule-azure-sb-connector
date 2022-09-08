package org.mule.extension.mule.azure.sb.source;

import static org.mule.runtime.api.metadata.MediaType.APPLICATION_JAVA;
import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.source.EmitsResponse;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.source.PollContext;
import org.mule.runtime.extension.api.runtime.source.PollingSource;
import org.mule.runtime.extension.api.runtime.source.SourceCallbackContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
	
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.extension.mule.azure.sb.internal.MuleAzureSBConnection;
import org.mule.extension.mule.azure.sb.internal.MuleAzureSBConnectionProvider;
import org.mule.extension.mule.azure.sb.internal.MuleAzureSBConfiguration;
import org.mule.runtime.api.connection.ConnectionProvider;
@Alias("Listener")
@EmitsResponse
@MediaType(value = ANY, strict = false)
public class MuleAzureSBListener extends PollingSource<String, Void> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MuleAzureSBListener.class);
	private static final String ID_FIELD = "ID";
	@Connection
	private ConnectionProvider<MuleAzureSBConnection> connProvider;
	MuleAzureSBConnection conn;

	@Override
	protected void doStart() throws MuleException {
 	
	}

	@Override
	protected void doStop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void poll(PollContext<String, Void> pollContext) {
		try
		{
			conn = connProvider.connect();
			ServiceBusReceivedMessage message;
			
			ServiceBusReceiverClient receiver = new ServiceBusClientBuilder()
			        .connectionString(conn.getConfig().getConnString())
			        .receiver()				   			        
			        .queueName(conn.getConfig().getQueueName())
			        .buildClient();
		    
		    message = receiver.peekMessage();
		    		    			   	
		    pollContext.accept(item -> {
	            Result<String, Void> result = Result.<String, Void>builder()
	                    .output(message.getBody().toString())
	                    .mediaType(APPLICATION_JAVA)
	                    .build();
	            item.setResult(result);
	            item.setId(ID_FIELD.toString());	            
	            receiver.close();
	        });
		} catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}

	@Override
	public void onRejectedItem(Result<String, Void> result, SourceCallbackContext callbackContext) {
		// TODO Auto-generated method stub
		
	}

}