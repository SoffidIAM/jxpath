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
package es.caib.zkib.jxpath.ri.model.dom;

import java.util.Locale;

import org.w3c.dom.Node;

import es.caib.zkib.jxpath.ri.QName;
import es.caib.zkib.jxpath.ri.model.NodePointer;
import es.caib.zkib.jxpath.ri.model.NodePointerFactory;

/**
 * Implements NodePointerFactory for DOM elements.
 *
 * @author Dmitri Plotnikov
 * @version $Revision: 1.1 $ $Date: 2009-04-03 08:13:14 $
 */
public class DOMPointerFactory implements NodePointerFactory {

    /** factory order */
    public static final int DOM_POINTER_FACTORY_ORDER = 100;

    public int getOrder() {
        return DOM_POINTER_FACTORY_ORDER;
    }

    public NodePointer createNodePointer(
        QName name,
        Object bean,
        Locale locale) {
        return bean instanceof Node ? new DOMNodePointer((Node) bean, locale) : null;
    }

    public NodePointer createNodePointer(
        NodePointer parent,
        QName name,
        Object bean) {
        return bean instanceof Node ? new DOMNodePointer(parent, (Node) bean) : null;
    }
}
