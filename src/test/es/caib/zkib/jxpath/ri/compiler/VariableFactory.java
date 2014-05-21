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
package es.caib.zkib.jxpath.ri.compiler;


import es.caib.zkib.jxpath.AbstractFactory;
import es.caib.zkib.jxpath.JXPathContext;
import es.caib.zkib.jxpath.Pointer;
import es.caib.zkib.jxpath.TestBean;

/**
 * Test AbstractFactory.
 *
 * @author Dmitri Plotnikov
 * @version $Revision: 1.1 $ $Date: 2009-04-03 08:13:14 $
 */
public class VariableFactory extends AbstractFactory {

    /**
     */
    public boolean createObject(
        JXPathContext context,
        Pointer pointer,
        Object parent,
        String name,
        int index) 
    {
        if (name.equals("testArray")) {
            ((TestBean[]) parent)[index] = new TestBean();
            return true;
        }
        else if (name.equals("stringArray")) {
            ((String[]) parent)[index] = "";
            return true;
        }
        else if (name.equals("array")) {
            ((String[]) parent)[index] = "";
            return true;
        }
        return false;
    }

    /**
     * Create a new object and set it on the specified variable
     */
    public boolean declareVariable(JXPathContext context, String name) {
        if (name.equals("test")) {
            context.getVariables().declareVariable(name, new TestBean());
            return true;
        }
        else if (name.equals("testArray")) {
            context.getVariables().declareVariable(name, new TestBean[0]);
            return true;
        }
        else if (name.equals("stringArray")) {
            context.getVariables().declareVariable(
                name,
                new String[] { "Value1" });
            return true;
        }
        context.getVariables().declareVariable(name, null);
        return true;
    }
}
