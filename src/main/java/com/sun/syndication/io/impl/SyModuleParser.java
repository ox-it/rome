/*
 * Copyright 2004 Sun Microsystems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
 *
 */
package com.sun.syndication.io.impl;

import java.util.Locale;

import org.jdom2.Element;
import org.jdom2.Namespace;

import com.sun.syndication.feed.module.Module;
import com.sun.syndication.feed.module.SyModule;
import com.sun.syndication.feed.module.SyModuleImpl;
import com.sun.syndication.io.ModuleParser;

/**
 */
public class SyModuleParser implements ModuleParser {
    @Override
    public String getNamespaceUri() {
        return SyModule.URI;
    }

    private Namespace getDCNamespace() {
        return Namespace.getNamespace(SyModule.URI);
    }

    @Override
    public Module parse(final Element syndRoot, final Locale locale) {
        boolean foundSomething = false;
        final SyModule sm = new SyModuleImpl();

        Element e = syndRoot.getChild("updatePeriod", getDCNamespace());
        if (e != null) {
            foundSomething = true;
            sm.setUpdatePeriod(e.getText());
        }
        e = syndRoot.getChild("updateFrequency", getDCNamespace());
        if (e != null) {
            foundSomething = true;
            sm.setUpdateFrequency(Integer.parseInt(e.getText().trim()));
        }
        e = syndRoot.getChild("updateBase", getDCNamespace());
        if (e != null) {
            foundSomething = true;
            sm.setUpdateBase(DateParser.parseDate(e.getText(), locale));
        }
        if (foundSomething) {
            return sm;
        } else {
            return null;
        }
    }

}
