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
package es.caib.zkib.jxpath.ri.model;


import es.caib.zkib.jxpath.JXPathContext;
import es.caib.zkib.jxpath.JXPathTestCase;
import es.caib.zkib.jxpath.xml.DocumentContainer;

/**
 * Test for text trimming from JXPATH-83.
 *
 * @author Matt Benson
 * @version $Revision: 1.1 $ $Date: 2009-04-03 08:13:14 $
 */
public class XMLPreserveSpaceTest extends JXPathTestCase {
    protected JXPathContext context;

    protected DocumentContainer createDocumentContainer(String model) {
        return new DocumentContainer(JXPathTestCase.class
                .getResource("XmlPreserveSpace.xml"), model);
    }

    protected JXPathContext createContext(String model) {
        JXPathContext context = JXPathContext
                .newContext(createDocumentContainer(model));
        return context;
    }

    protected void doTest(String id, String model, String expectedValue) {
        JXPathContext context = JXPathContext
                .newContext(createDocumentContainer(model));
        assertEquals(context.getValue("test/text[@id='" + id + "']"), expectedValue);
    }

    public void testUnspecifiedDOM() {
        doTest("unspecified", DocumentContainer.MODEL_DOM, " foo ");
    }

    public void testDefaultDOM() {
        doTest("default", DocumentContainer.MODEL_DOM, "foo");
    }

    public void testPreserveDOM() {
        doTest("preserve", DocumentContainer.MODEL_DOM, " foo ");
    }

    public void testNestedDOM() {
        doTest("nested", DocumentContainer.MODEL_DOM, " foo ;bar; baz ");
    }

    public void testNestedWithCommentsDOM() {
        doTest("nested-with-comments", DocumentContainer.MODEL_DOM, " foo ;bar; baz ");
    }

    public void testUnspecifiedJDOM() {
        doTest("unspecified", DocumentContainer.MODEL_JDOM, " foo ");
    }

    public void testDefaultJDOM() {
        doTest("default", DocumentContainer.MODEL_JDOM, "foo");
    }

    public void testPreserveJDOM() {
        doTest("preserve", DocumentContainer.MODEL_JDOM, " foo ");
    }

    public void testNestedJDOM() {
        doTest("nested", DocumentContainer.MODEL_JDOM, " foo ;bar; baz ");
    }

    public void testNestedWithCommentsJDOM() {
        doTest("nested-with-comments", DocumentContainer.MODEL_JDOM, " foo ;bar; baz ");
    }
}