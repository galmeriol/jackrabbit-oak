/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.jackrabbit.oak.jcr.query;

import org.apache.jackrabbit.commons.iterator.RowIteratorAdapter;
import org.apache.jackrabbit.oak.api.Result;
import org.apache.jackrabbit.oak.api.ResultRow;
import org.apache.jackrabbit.oak.jcr.value.ValueFactoryImpl;

import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.query.QueryResult;
import javax.jcr.query.RowIterator;
import java.util.Iterator;

/**
 * The implementation of the corresponding JCR interface.
 */
public class QueryResultImpl implements QueryResult {

    private final Result result;
    private final ValueFactoryImpl valueFactory;

    public QueryResultImpl(Result result, ValueFactoryImpl valueFactory) {
        this.result = result;
        this.valueFactory = valueFactory;
    }

    @Override
    public String[] getColumnNames() throws RepositoryException {
        return result.getColumnNames();
    }

    @Override
    public String[] getSelectorNames() throws RepositoryException {
        return result.getSelectorNames();
    }

    @Override
    public RowIterator getRows() throws RepositoryException {
        Iterator<RowImpl> it = new Iterator<RowImpl>() {

            private final Iterator<? extends ResultRow> it = result.getRows().iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public RowImpl next() {
                return new RowImpl(it.next(), valueFactory);
            }

            @Override
            public void remove() {
                it.remove();
            }

        };
        return new RowIteratorAdapter(it);
    }

    @Override
    public NodeIterator getNodes() throws RepositoryException {
        return null;
    }

}
