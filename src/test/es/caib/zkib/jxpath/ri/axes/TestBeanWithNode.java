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

import org.w3c.dom.Document;

import es.caib.zkib.jxpath.JXPathTestCase;
import es.caib.zkib.jxpath.TestBean;
import es.caib.zkib.jxpath.xml.DocumentContainer;

/**
 * Test bean for mixed model JUnit tests.
 *
 * @author Dmitri Plotnikov
 * @version $Revision: 1.1 $ $Date: 2009-04-03 08:13:15 $
 */
public class TestBeanWithNode extends TestBean {
    private Object node;
    private Object object;

    public Object getVendor() {
        return node;
    }

    public Object[] getVendors() {
        return new Object[] { node };
    }

    public void setVendor(Object node) {
        this.node = node;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static TestBeanWithNode createTestBeanWithDOM() {
        DocumentContainer docCtr =
            new DocumentContainer(
                JXPathTestCase.class.getResource("Vendor.xml"));
        Document doc = (Document) docCtr.getValue();
        TestBeanWithNode tbwdom = new TestBeanWithNode();
        tbwdom.setVendor(doc.getDocumentElement());
        tbwdom.setObject(docCtr);
        return tbwdom;
    }

}
