/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.caib.zkib.jxpath.ri.model.beans;

import java.util.Locale;

import es.caib.zkib.jxpath.JXPathContext;
import es.caib.zkib.jxpath.JXPathIntrospector;
import es.caib.zkib.jxpath.ri.Compiler;
import es.caib.zkib.jxpath.ri.QName;
import es.caib.zkib.jxpath.ri.compiler.NodeNameTest;
import es.caib.zkib.jxpath.ri.compiler.NodeTest;
import es.caib.zkib.jxpath.ri.compiler.NodeTypeTest;
import es.caib.zkib.jxpath.ri.model.NodeIterator;
import es.caib.zkib.jxpath.ri.model.NodePointer;
import es.caib.zkib.jxpath.util.ValueUtils;

/**
 * Transparent pointer to a collection (array or Collection).
 *
 * @author Dmitri Plotnikov
 * @version $Revision: 1.2 $ $Date: 2009-04-03 12:19:35 $
 */
public class CollectionPointer extends NodePointer {
    private Object collection;
    private NodePointer valuePointer;

    private static final long serialVersionUID = 8620254915563256588L;

    /**
     * Create a new CollectionPointer.
     * @param collection value
     * @param locale Locale
     */
    public CollectionPointer(Object collection, Locale locale) {
        super(null, locale);
        this.collection = collection;
    }

    /**
     * Create a new CollectionPointer.
     * @param parent parent NodePointer
     * @param collection value
     */
    public CollectionPointer(NodePointer parent, Object collection) {
        super(parent);
        this.collection = collection;
    }

    public QName getName() {
        return null;
    }

    public Object getBaseValue() {
        return collection;
    }

    public boolean isCollection() {
        return true;
    }

    public int getLength() {
        return ValueUtils.getLength(getBaseValue());
    }

    public boolean isLeaf() {
        Object value = getNode();
        return value == null || JXPathIntrospector.getBeanInfo(value.getClass()).isAtomic();
    }

    public boolean isContainer() {
        return index != WHOLE_COLLECTION;
    }

    public Object getImmediateNode() {
        return index == WHOLE_COLLECTION ? ValueUtils.getValue(collection)
                : ValueUtils.getValue(collection, index);
    }

    public void setValue(Object value) {
        if (index == WHOLE_COLLECTION) {
            parent.setValue(value);
        }
        else {
            ValueUtils.setValue(collection, index, value);
        }
    }

    public void setIndex(int index) {
        super.setIndex(index);
        valuePointer = null;
    }

    public NodePointer getValuePointer() {
        if (valuePointer == null) {
            if (index == WHOLE_COLLECTION) {
                valuePointer = this;
            }
            else {
                Object value = getImmediateNode();
                valuePointer =
                    NodePointer.newChildNodePointer(this, getName(), value);
            }
        }
        return valuePointer;
    }

    public NodePointer createPath(JXPathContext context) {
        if (ValueUtils.getLength(getBaseValue()) <= index) {
            collection = ValueUtils.expandCollection(getNode(), index + 1);
        }
        return this;
    }

    public NodePointer createPath(JXPathContext context, Object value) {
        NodePointer ptr = createPath(context);
        ptr.setValue(value);
        return ptr;
    }

    public NodePointer createChild(
        JXPathContext context,
        QName name,
        int index,
        Object value) {
        NodePointer ptr = (NodePointer) clone();
        ptr.setIndex(index);
        return ptr.createPath(context, value);
    }

    public NodePointer createChild(
        JXPathContext context,
        QName name,
        int index) {
        NodePointer ptr = (NodePointer) clone();
        ptr.setIndex(index);
        return ptr.createPath(context);
    }

    public int hashCode() {
        return System.identityHashCode(collection) + index;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof CollectionPointer)) {
            return false;
        }

        CollectionPointer other = (CollectionPointer) object;
        return collection == other.collection && index == other.index;
    }

    public NodeIterator childIterator(NodeTest test,
                boolean reverse, NodePointer startWith, boolean ignoreExceptions) {
        if (index == WHOLE_COLLECTION) {
            return new CollectionChildNodeIterator(
                this,
                test,
                reverse,
                startWith,
                ignoreExceptions);
        }
        return getValuePointer().childIterator(test, reverse, startWith, ignoreExceptions);
    }

    public NodeIterator attributeIterator(QName name, boolean ignoreExceptions) {
        return index == WHOLE_COLLECTION ? new CollectionAttributeNodeIterator(this, name, ignoreExceptions)
                : getValuePointer().attributeIterator(name, ignoreExceptions);
    }

    public NodeIterator namespaceIterator() {
        return index == WHOLE_COLLECTION ? null : getValuePointer().namespaceIterator();
    }

    public NodePointer namespacePointer(String namespace) {
        return index == WHOLE_COLLECTION ? null : getValuePointer().namespacePointer(namespace);
    }

    public boolean testNode(NodeTest test) {
        if (index == WHOLE_COLLECTION) {
            if (test == null) {
                return true;
            }
            if (test instanceof NodeNameTest) {
                return false;
            }
            return test instanceof NodeTypeTest && ((NodeTypeTest) test).getNodeType() == Compiler.NODE_TYPE_NODE;
        }
        return getValuePointer().testNode(test);
    }

    public int compareChildNodePointers(
                NodePointer pointer1, NodePointer pointer2) {
        return pointer1.getIndex() - pointer2.getIndex();
    }

    public String asPath() {
        StringBuffer buffer = new StringBuffer();
        NodePointer parent = getImmediateParentPointer();
        if (parent != null) {
            buffer.append(parent.asPath());
            if (index != WHOLE_COLLECTION) {
                // Address the list[1][2] case
                if (parent.getIndex() != WHOLE_COLLECTION) {
                    buffer.append("/.");
                }
                buffer.append("[").append(index + 1).append(']');
            }
        }
        else {
            if (index != WHOLE_COLLECTION) {
                buffer.append("/.[").append(index + 1).append(']');
            }
            else {
                buffer.append("/");
            }
        }
        return buffer.toString();
    }
}
