/*******************************************************************************
* Copyright (c) 2007 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the terms of
* the Eclipse Public License v1.0, which accompanies this distribution and is available at
* http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
******************************************************************************/
package org.eclipse.persistence.tools.workbench.mappingsmodel.schema;

import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.xs.XSTypeDefinition;
import org.eclipse.persistence.tools.workbench.mappingsmodel.MWModel;

import org.eclipse.persistence.oxm.XMLDescriptor;

public abstract class ExplicitSchemaTypeDefinition
	extends AbstractNamedSchemaComponent
	implements MWSchemaTypeDefinition
{
	/** Flag that stores whether this type is a built-in datatype */
	protected boolean builtIn;
	
	
	// **************** Static methods ****************************************
	
	static ExplicitSchemaTypeDefinition reloadedExplicitType(ExplicitElementDeclaration element,
																MWSchemaTypeDefinition oldType,
																XSElementDecl elementDecl) {
		ExplicitSchemaTypeDefinition newExplicitType;
		
		try {
			newExplicitType = (ExplicitSchemaTypeDefinition) oldType;
		}
		catch (ClassCastException cce) {
			newExplicitType = null;
		}
		
		String typeName = elementDecl.getName();
		
		if (elementDecl.getTypeDefinition().getTypeCategory() == XSTypeDefinition.COMPLEX_TYPE) {
			if (! (newExplicitType instanceof ExplicitComplexTypeDefinition)) {
				newExplicitType = new ExplicitComplexTypeDefinition(element, typeName);
			}
		}
		else {
			// (elementNode.getType().getNodeType() == XSDElement.DATATYPE)
			if (! (newExplicitType instanceof ExplicitSimpleTypeDefinition)) {
				newExplicitType = new ExplicitSimpleTypeDefinition(element, typeName);
			}
		}
		
		newExplicitType.reload(elementDecl.getTypeDefinition());
		return newExplicitType;
	}
	
			
	public static XMLDescriptor buildDescriptor() {
		XMLDescriptor descriptor = new XMLDescriptor();
		descriptor.setJavaClass(ExplicitSchemaTypeDefinition.class);
		descriptor.getInheritancePolicy().setParentClass(AbstractNamedSchemaComponent.class);
		
		return descriptor;
	}
	
	
	// **************** Constructors ******************************************
	
	/** Toplink use only */
	protected ExplicitSchemaTypeDefinition() {
		super();
	}
	
	protected ExplicitSchemaTypeDefinition(MWModel parent, String typeName) {
		super(parent, typeName);
	}
	
	protected ExplicitSchemaTypeDefinition(MWModel parent, String typeName, String namespace) {
		super(parent, typeName, namespace);
	}
	
	protected ExplicitSchemaTypeDefinition(MWModel parent, String name, String namespace, boolean builtIn) {
		super(parent, name, namespace);
		this.builtIn = builtIn;
	}
	
	
	// **************** MWSchemaContextComponent contract *********************
	
	public boolean hasType() {
		return true;
	}
	
	public String contextTypeQname() {
		if (this.getName() == null) {
			return (this.getBaseType() == null) ? null : this.getBaseType().qName();
		}
		else {
			return this.qName();
		}
	}
}
