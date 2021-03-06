/*
 * Copyright (c) 2011, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

// Contributors:
//     Oracle - initial API and implementation
package org.eclipse.persistence.internal.nosql.adapters.mongo;

import jakarta.resource.cci.*;
import org.eclipse.persistence.exceptions.ValidationException;

/**
 * Record factory for Mongo JCA adapter.
 *
 * @author James
 * @since EclipseLink 2.4
 */
public class MongoRecordFactory implements RecordFactory {

    /**
     * Default constructor.
     */
    public MongoRecordFactory() {
    }

    @Override
    public IndexedRecord createIndexedRecord(String recordName) {
        throw ValidationException.operationNotSupported("createIndexedRecord");
    }

    @Override
    public MappedRecord createMappedRecord(String recordName) {
        return new MongoRecord();
    }
}
