package org.mule.extension.mule.azure.sb.internal;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class (as it's name implies) provides connection instances and the funcionality to disconnect and validate those
 * connections.
 * <p>
 * All connection related parameters (values required in order to create a connection) must be
 * declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares that connections resolved by this provider
 * will be pooled and reused. There are other implementations like {@link CachedConnectionProvider} which lazily creates and
 * caches connections or simply {@link ConnectionProvider} if you want a new connection each time something requires one.
 */
public class MuleAzureSBConnectionProvider implements PoolingConnectionProvider<MuleAzureSBConnection> {

  private final Logger LOGGER = LoggerFactory.getLogger(MuleAzureSBConnectionProvider.class);

  @DisplayName("Configuration Detail")
  @Parameter
  MuleAzureSBConfiguration conf;

  @Override
  public MuleAzureSBConnection connect() throws ConnectionException {
    return new MuleAzureSBConnection(conf);
  }

  @Override
  public void disconnect(MuleAzureSBConnection connection) {
    try {
      connection.invalidate();
    } catch (Exception e) {
      LOGGER.error("Error while disconnecting [" + connection.getConfig().getConnString() + "]: " + e.getMessage(), e);
    }
  }

  @Override
  public ConnectionValidationResult validate(MuleAzureSBConnection connection) {
    return ConnectionValidationResult.success();
  }
}
