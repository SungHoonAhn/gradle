/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.tooling.internal;

import org.gradle.tooling.model.internal.Exceptions;

import java.io.File;
import java.util.List;

/**
 * by Szczepan Faber, created at: 12/22/11
 */
public class VersionOnlyBuildEnvironment extends DefaultBuildEnvironment {

    public VersionOnlyBuildEnvironment(String gradleVersion) {
        super(gradleVersion, null, null);
    }

    public File getJavaHome() {
        throw Exceptions.incompatibleMethod("getJavaHome");
    }

    public List<String> getJvmArguments() {
        throw Exceptions.incompatibleMethod("getJvmArguments");
    }
}
