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
package es.caib.zkib.jxpath.ri.axes;

import es.caib.zkib.jxpath.ri.EvalContext;
import es.caib.zkib.jxpath.ri.QName;
import es.caib.zkib.jxpath.ri.compiler.NodeNameTest;
import es.caib.zkib.jxpath.ri.compiler.NodeTest;
import es.caib.zkib.jxpath.ri.model.NodeIterator;
import es.caib.zkib.jxpath.ri.model.NodePointer;

/**
 * EvalContext that walks the "namespace::" axis.
 *
 * @author Dmitri Plotnikov
 * @version $Revision: 1.1 $ $Date: 2009-04-03 08:13:14 $
 */
public class NamespaceContext extends EvalContext {
    private NodeTest nodeTest;
    private boolean setStarted = false;
    private NodeIterator iterator;
    private NodePointer currentNodePointer;

    /**
     * @param parentContext represents the previous step on the path
     * @param nodeTest is the name of the namespace we are looking for
     */
    public NamespaceContext(EvalContext parentContext, NodeTest nodeTest) {
        super(parentContext);
        this.nodeTest = nodeTest;
    }

    public NodePointer getCurrentNodePointer() {
        return currentNodePointer;
    }

    public void reset() {
        setStarted = false;
        iterator = null;
        super.reset();
    }

    public boolean setPosition(int position) {
        if (position < getCurrentPosition()) {
            reset();
        }

        while (getCurrentPosition() < position) {
            if (!nextNode()) {
                return false;
            }
        }
        return true;
    }

    public boolean nextNode() {
        super.setPosition(getCurrentPosition() + 1);
        if (!setStarted) {
            setStarted = true;
            if (!(nodeTest instanceof NodeNameTest)) {
                return false;
            }

            NodeNameTest nodeNameTest = (NodeNameTest) nodeTest;
            QName testName = nodeNameTest.getNodeName();
            if (testName.getPrefix() != null) {
                return false;
            }
            if (nodeNameTest.isWildcard()) {
                iterator =
                    parentContext.getCurrentNodePointer().namespaceIterator();
            }
            else {
                currentNodePointer =
                    parentContext.getCurrentNodePointer().namespacePointer(
                            testName.getName());
                return currentNodePointer != null;
            }
        }

        if (iterator == null) {
            return false;
        }
        if (!iterator.setPosition(iterator.getPosition() + 1)) {
            return false;
        }
        currentNodePointer = iterator.getNodePointer();
        return true;
    }
}
