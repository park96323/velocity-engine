package org.apache.velocity.test.provider;

/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 *  simple class to test boolean property
 *  introspection - can't use TestProvider
 *  as there is a get( String )
 *  and that comes before isProperty
 *  in the search pattern
 *
 *  @author <a href="mailto:geirm@apache.org">Geir Magnusson Jr.</a>
 */
public class BoolObj
{
    public boolean isBoolean()
    {
        return true;
    }

    /*
     *  not isProperty as it's not
     *  boolean return valued...
     */
    public String  isNotboolean()
    {
        return "hello";
    }
}
