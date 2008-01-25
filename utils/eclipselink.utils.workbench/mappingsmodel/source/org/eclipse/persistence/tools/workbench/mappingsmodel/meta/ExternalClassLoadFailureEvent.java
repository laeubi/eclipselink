/*******************************************************************************
* Copyright (c) 2007 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the terms of
* the Eclipse Public License v1.0, which accompanies this distribution and is available at
* http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
******************************************************************************/
package org.eclipse.persistence.tools.workbench.mappingsmodel.meta;

import java.util.EventObject;

/**
 * An "ExternalClassLoadFailure" event gets delivered whenever an "external"
 * class repository has problems loading the metadata needed to populate
 * an "external" class.
 */

public class ExternalClassLoadFailureEvent 
	extends EventObject 
{
	/** the name of the class that failed to load */
	private volatile String className;

	/** the cause of the load failure */
	private volatile Throwable cause;


	public ExternalClassLoadFailureEvent(Object source, String className, Throwable cause) {
		super(source);
		this.className = className;
		this.cause = cause;
	}

	public String getClassName() {
		return this.className;
	}

	public Throwable getCause() {
		return this.cause;
	}

}