package org.mule.extension.mule.azure.sb.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.extension.api.annotation.Alias;

import com.azure.core.util.IterableStream;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusException;
import com.azure.messaging.servicebus.ServiceBusFailureReason;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;

import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class MuleAzureSBOperations {

	private final Logger LOGGER = LoggerFactory.getLogger(MuleAzureSBOperations.class);

	/**
	 * Example of an operation that uses the configuration and a connection instance
	 * to perform some action.
	 */
	@MediaType(value = ANY, strict = false)
	public String send(@Connection MuleAzureSBConnection conn, @Alias("payload") String message) {
		try {
			ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
					.connectionString(conn.getConfig().getConnString()).sender()
					.queueName(conn.getConfig().getQueueName()).buildClient();
			LOGGER.info("Sender Client has been created successfully");
			senderClient.sendMessage(new ServiceBusMessage(message));
			LOGGER.info("Sent successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.info(ex.getMessage().toString());
		}
		return "";
	}

	@MediaType(value = ANY, strict = false)
	public InputStream receive(@Connection MuleAzureSBConnection conn) {
		try {
			ServiceBusReceiverClient receiver = new ServiceBusClientBuilder()
					.connectionString(conn.getConfig().getConnString()).receiver().disableAutoComplete()
					.receiveMode(ServiceBusReceiveMode.PEEK_LOCK).queueName(conn.getConfig().getQueueName())
					.buildClient();

			IterableStream<ServiceBusReceivedMessage> messages = receiver.receiveMessages(1);
			ServiceBusReceivedMessage message = messages.iterator().next();
		    receiver.close();	
		    return message.getBody().toStream();
 
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.info(ex.getMessage().toString());
		}
		return null;
	}

}
