package org.mule.extension.mule.azure.sb.internal;


/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class MuleAzureSBConnection  {
  
  private final MuleAzureSBConfiguration config;

  public MuleAzureSBConnection(MuleAzureSBConfiguration config) {
    this.config = config;
  }

 
  public MuleAzureSBConfiguration getConfig() {
    return config;
  }

  public void invalidate() {
    // do something to invalidate this connection!
  }
}
