/*******************************************************************************
* Copyright (c) 2007 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the terms of
* the Eclipse Public License v1.0, which accompanies this distribution and is available at
* http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
******************************************************************************/
package org.eclipse.persistence.tools.workbench.mappingsmodel.project.relational;

import java.util.List;

import org.eclipse.persistence.tools.workbench.mappingsmodel.MWModel;
import org.eclipse.persistence.tools.workbench.mappingsmodel.ProblemConstants;
import org.eclipse.persistence.tools.workbench.mappingsmodel.descriptor.MWMappingDescriptor;
import org.eclipse.persistence.tools.workbench.mappingsmodel.descriptor.relational.MWRelationalClassDescriptor;
import org.eclipse.persistence.tools.workbench.mappingsmodel.descriptor.relational.MWTableDescriptor;
import org.eclipse.persistence.tools.workbench.mappingsmodel.project.MWTransactionalProjectCachingPolicy;
import org.eclipse.persistence.tools.workbench.mappingsmodel.project.MWTransactionalProjectDefaultsPolicy;
import org.eclipse.persistence.tools.workbench.utility.node.Node;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.descriptors.DescriptorEvent;
import org.eclipse.persistence.mappings.converters.ObjectTypeConverter;
import org.eclipse.persistence.oxm.XMLDescriptor;
import deprecated.sdk.SDKAggregateObjectMapping;
import deprecated.sdk.SDKDirectCollectionMapping;
import org.eclipse.persistence.sessions.Project;

/**
 * This class describes the project defaults for a relational project.  
 * 
 * @version 10.1.3
 */
public final class MWRelationalProjectDefaultsPolicy extends MWTransactionalProjectDefaultsPolicy
{
	public static final String MULTI_TABLE_INFO_POLICY = "Multi-Table Info";
	public static final String INTERFACE_ALIAS_POLICY = "Interface Alias";
	
	private volatile boolean queriesCacheAllStatements;
	public final static String QUERIES_CACHE_ALL_STATEMENTS_PROPERTY = "queriesCacheAllStatements";
	
	private volatile boolean queriesBindAllParameters;
	public final static String QUERIES_BIND_ALL_PARAMETERS_PROPERTY = "queriesBindAllParameters";

	
	public static XMLDescriptor buildDescriptor()
	{
		XMLDescriptor descriptor = new XMLDescriptor();
		descriptor.setJavaClass(MWRelationalProjectDefaultsPolicy.class);
		descriptor.getInheritancePolicy().setParentClass(MWTransactionalProjectDefaultsPolicy.class);
		
		descriptor.addDirectMapping("queriesCacheAllStatements", "cache-all-statements/text()");
		descriptor.addDirectMapping("queriesBindAllParameters", "bind-all-parameters/text()");
		
		return descriptor;
	}
	
	public static ClassDescriptor legacy50BuildDescriptor() 
	{
		ClassDescriptor descriptor = MWModel.legacy50BuildStandardDescriptor();
		descriptor.descriptorIsAggregate();

		descriptor.setJavaClass(MWRelationalProjectDefaultsPolicy.class);
		descriptor.setTableName("project-defaults-policy");

		descriptor.addDirectMapping("methodAccessing", "use-method-accessing");
		descriptor.addDirectMapping("queriesBindAllParameters", "bind-all-parameters");
		descriptor.addDirectMapping("queriesCacheAllStatements", "cache-all-statements");

		SDKAggregateObjectMapping projectCachingPolicyMapping = new SDKAggregateObjectMapping();
		projectCachingPolicyMapping.setAttributeName("cachingPolicy");
		projectCachingPolicyMapping.setReferenceClass(MWTransactionalProjectCachingPolicy.class);
		projectCachingPolicyMapping.setFieldName("project-identity-policy");
		descriptor.addMapping(projectCachingPolicyMapping);

		SDKDirectCollectionMapping defaultDescriptorPolicyClassesMapping = new SDKDirectCollectionMapping();
		defaultDescriptorPolicyClassesMapping.setAttributeName("defaultPolicies");
        defaultDescriptorPolicyClassesMapping.setGetMethodName("legacyGetDefaultPolicyClasses");
        defaultDescriptorPolicyClassesMapping.setSetMethodName("legacySetDefaultPolicyClasses");
		ObjectTypeConverter defaultDescriptorClassesConverter = new ObjectTypeConverter();
		defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.MWCopyPolicy", COPY_POLICY);
		defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.MWInheritancePolicy", INHERITANCE_POLICY);
		defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.MWInstantiationPolicy", INSTANTIATION_POLICY);
		defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.MWInterfaceAliasPolicy", INTERFACE_ALIAS_POLICY);
		defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.MWDescriptorAfterLoadingPolicy", AFTER_LOAD_POLICY);
		defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.MWMultiTableInfoPolicy", MULTI_TABLE_INFO_POLICY);
		defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.MWDescriptorEventsPolicy", EVENTS_POLICY);
        defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.MWDescriptorLockingPolicy", "legacy-locking-policy");
        defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.MWIdentityPolicy", "legacy-identity-policy");
		defaultDescriptorPolicyClassesMapping.setValueConverter(defaultDescriptorClassesConverter);
		defaultDescriptorPolicyClassesMapping.setFieldName("default-descriptor-policy-classes");
		defaultDescriptorPolicyClassesMapping.setElementDataTypeName("policy-class");
		descriptor.addMapping(defaultDescriptorPolicyClassesMapping);

		return descriptor;
	}

	public static ClassDescriptor legacy45BuildDescriptor() 
	{
		ClassDescriptor descriptor = MWModel.legacy45BuildStandardDescriptor();
		descriptor.setJavaClass(MWRelationalProjectDefaultsPolicy.class);
		descriptor.setTableName("MWRProjectDefaultsPolicy");
		descriptor.descriptorIsAggregate();

		// DTFs
		descriptor.addDirectMapping("methodAccessing", "defaults_methodAccess");

		// aggregate - projectIdentityPolicy
		SDKAggregateObjectMapping projectIdentityPolicyMapping = new SDKAggregateObjectMapping();
		projectIdentityPolicyMapping.setAttributeName("cachingPolicy");
		projectIdentityPolicyMapping.setReferenceClass(MWTransactionalProjectCachingPolicy.class);
		projectIdentityPolicyMapping.setFieldName("projectIdentityPolicy");
		descriptor.addMapping(projectIdentityPolicyMapping);

		// direct collection - defaultDescriptorPolicyClasses
		SDKDirectCollectionMapping defaultDescriptorPolicyClassesMapping = new SDKDirectCollectionMapping();
		defaultDescriptorPolicyClassesMapping.setAttributeName("defaultPolicies");
        defaultDescriptorPolicyClassesMapping.setGetMethodName("legacyGetDefaultPolicyClasses");
        defaultDescriptorPolicyClassesMapping.setSetMethodName("legacySetDefaultPolicyClasses");
		ObjectTypeConverter defaultDescriptorClassesConverter = new ObjectTypeConverter();
		defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.BldrCopyPolicy", COPY_POLICY);
		defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.BldrInheritancePolicy", INHERITANCE_POLICY);
		defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.BldrInstantiationPolicy", INSTANTIATION_POLICY);
		defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.BldrInterfaceAliasPolicy", INTERFACE_ALIAS_POLICY);
		defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.BldrDescriptorAfterLoadingPolicy", AFTER_LOAD_POLICY);
		defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.BldrMultiTableInfoPolicy", MULTI_TABLE_INFO_POLICY);
        defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.BldrDescriptorEventsPolicy", EVENTS_POLICY);
        defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.BldrDescriptorLockingPolicy", "legacy-locking-policy");
        defaultDescriptorClassesConverter.addConversionValue("org.eclipse.persistence.workbench.model.desc.BldrIdentityPolicy", "legacy-identity-policy");
		defaultDescriptorPolicyClassesMapping.setValueConverter(defaultDescriptorClassesConverter);
		defaultDescriptorPolicyClassesMapping.setFieldName("defautlDescriptorPolicyClasses");
		defaultDescriptorPolicyClassesMapping.setElementDataTypeName("policyClass");
		descriptor.addMapping(defaultDescriptorPolicyClassesMapping);	
		
		return descriptor;
	}
	
	@Override
	protected void legacy45PostBuild(DescriptorEvent event) {
		super.legacy45PostBuild(event);
		this.queriesBindAllParameters = true;
	}
	
	private MWRelationalProjectDefaultsPolicy()
	{
		super();
	}

	MWRelationalProjectDefaultsPolicy(MWModel parent)
	{
		super(parent);
	}
	
	@Override
	protected void initialize(Node parent) {
		super.initialize(parent);
		this.queriesBindAllParameters = true;
	}
	
	protected void initializePolicyDescriptors()
	{
		super.initializePolicyDescriptors();
		addPolicyDescriptor(MULTI_TABLE_INFO_POLICY, new MultiTableInfoPolicyDescriptor());
		addPolicyDescriptor(INTERFACE_ALIAS_POLICY, new InterfaceAliasPolicyDescriptor());
	}
	
	public boolean shouldQueriesBindAllParameters()
	{
		return this.queriesBindAllParameters;
	}

	public void setQueriesBindAllParameters(boolean queriesBindAllParameters)
	{
		boolean oldValue = queriesBindAllParameters;
		this.queriesBindAllParameters = queriesBindAllParameters;
		firePropertyChanged(QUERIES_BIND_ALL_PARAMETERS_PROPERTY, oldValue, this.queriesBindAllParameters);
	}

	public boolean shouldQueriesCacheAllStatements()
	{
		return this.queriesCacheAllStatements;
	}
	
	public void setQueriesCacheAllStatements(boolean queriesCacheAllStatements)
	{
		boolean oldValue = queriesCacheAllStatements;
		this.queriesCacheAllStatements = queriesCacheAllStatements;
		firePropertyChanged(QUERIES_CACHE_ALL_STATEMENTS_PROPERTY, oldValue, this.queriesCacheAllStatements);
	}
	
	protected void adjustRuntimeProject(Project project)
	{
		project.getLogin().setShouldBindAllParameters(
						shouldQueriesBindAllParameters());
		project.getLogin().setShouldCacheAllStatements(
				shouldQueriesCacheAllStatements());	
	}
	
	private class MultiTableInfoPolicyDescriptor implements PolicyDescriptor
	{
		public void applyPolicyToDescriptor(MWMappingDescriptor descriptor) {
			if (((MWRelationalClassDescriptor) descriptor).supportsMultitablePolicy()) {
				((MWTableDescriptor) descriptor).addMultiTableInfoPolicy();
			}
		}	
	}
	
	private class InterfaceAliasPolicyDescriptor implements PolicyDescriptor
	{
		public void applyPolicyToDescriptor(MWMappingDescriptor descriptor) {
			if (((MWRelationalClassDescriptor) descriptor).supportsInterfaceAliasPolicy()) {
				((MWTableDescriptor) descriptor).addInterfaceAliasPolicy();
			}
		}
	}
	
	protected void addProblemsTo(List currentProblems) {
		super.addProblemsTo(currentProblems);
		this.checkCacheAllStatementsAndBindAllParameters(currentProblems);
	}
	
	private void checkCacheAllStatementsAndBindAllParameters(List newProblems) {
		if (this.queriesCacheAllStatements && ! this.queriesBindAllParameters)
			newProblems.add(this.buildProblem(ProblemConstants.PROJECT_CACHES_QUERY_STATEMENTS_WITHOUT_BINDING_PARAMETERS));
	}

}
