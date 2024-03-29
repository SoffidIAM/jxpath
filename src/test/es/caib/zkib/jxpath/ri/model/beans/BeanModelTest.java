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


import es.caib.zkib.jxpath.AbstractFactory;
import es.caib.zkib.jxpath.JXPathContext;
import es.caib.zkib.jxpath.TestBean;
import es.caib.zkib.jxpath.ri.model.BeanModelTestCase;

/**
 * Tests JXPath with JavaBeans
*
 * @author Dmitri Plotnikov
 * @version $Revision: 1.1 $ $Date: 2009-04-03 08:13:15 $
 */
public class BeanModelTest extends BeanModelTestCase {

    protected Object createContextBean() {
        return new TestBean();
    }

    protected AbstractFactory getAbstractFactory() {
        return new TestBeanFactory();
    }
    
    public void testIndexedProperty() {
        JXPathContext context =
            JXPathContext.newContext(null, new TestIndexedPropertyBean());
            
        assertXPathValueAndPointer(
            context,
            "indexed[1]",
            new Integer(0),
            "/indexed[1]");
    }


}