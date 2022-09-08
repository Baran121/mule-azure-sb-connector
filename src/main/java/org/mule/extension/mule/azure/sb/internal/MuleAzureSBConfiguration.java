package org.mule.extension.mule.azure.sb.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * This class represents an extension configuration, values set in this class
 * are commonly used across multiple operations since they represent something
 * core from the extension.
 */
@Operations(MuleAzureSBOperations.class)
@ConnectionProviders(MuleAzureSBConnectionProvider.class)
public class MuleAzureSBConfiguration {

	/**
	 * A parameter that is always required to be configured.
	 */
	@Parameter
	private String connString;

	public String getConnString() {
		return connString;
	}

	/**
	 * A parameter that is not required to be configured by the user.
	 */
	@Parameter
	private String queueName;

	public String getQueueName() {
		return queueName;
	}
}
