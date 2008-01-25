/*******************************************************************************
* Copyright (c) 2007 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the terms of
* the Eclipse Public License v1.0, which accompanies this distribution and is available at
* http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
******************************************************************************/
package org.eclipse.persistence.tools.workbench.utility.node;

import org.eclipse.persistence.tools.workbench.utility.SynchronizedBoolean;
import org.eclipse.persistence.tools.workbench.utility.string.StringTools;


/**
 * This implementation of the PluggableValidator.Delegate interface
 * simply sets a shared "validate" flag to true. This should trigger a
 * separate "validation" thread to begin validating the appropriate
 * branch of nodes.
 */
public class AsynchronousValidator
	implements PluggableValidator.Delegate
{
	private SynchronizedBoolean validateFlag;

	/**
	 * Construct a validator delegate with the specified shared
	 * "validate" flag. This flag should be shared with
	 * another thread that will perform the actual validation.
	 */
	public AsynchronousValidator(SynchronizedBoolean validateFlag) {
		super();
		this.validateFlag = validateFlag;
	}

	/**
	 * Set the shared "validate" flag to true, triggering
	 * an asynchronous validation of the appropriate
	 * branch of nodes.
	 * @see PluggableValidator.Delegate#validate()
	 */
	public void validate() {
		this.validateFlag.setTrue();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return StringTools.buildToStringFor(this, this.validateFlag);
	}

}
