package org.mule.extension.mule.azure.sb.internal;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.Sources;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import  org.mule.extension.mule.azure.sb.source.MuleAzureSBListener;
/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "mule-azure-sb")
@Extension(name = "Mule Azure SB")
@Sources(MuleAzureSBListener.class)
@Operations({MuleAzureSBOperations.class})
@ConnectionProviders(MuleAzureSBConnectionProvider.class)
public class MuleAzureSBExtension {

}

