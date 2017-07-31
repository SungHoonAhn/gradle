/*
 * Copyright 2016 the original author or authors.
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

package org.gradle.plugins.ide.tooling.r30

import org.gradle.integtests.tooling.fixture.TargetGradleVersion
import org.gradle.integtests.tooling.fixture.ToolingApiSpecification
import org.gradle.integtests.tooling.fixture.ToolingApiVersion
import org.gradle.tooling.model.UnsupportedMethodException
import org.gradle.tooling.model.eclipse.EclipseProject
import org.gradle.tooling.model.eclipse.EclipseSourceDirectory

@ToolingApiVersion('>=3.0')
@TargetGradleVersion(">=3.0")
class ToolingApiEclipseModelSourceDirectoryOutputCrossVersionSpec extends ToolingApiSpecification {

    @TargetGradleVersion(">=1.2 <3.0")
    def "Old versions throw runtime exception when querying source directory output"() {
        setup:
        settingsFile << 'rootProject.name = "root"'
        buildFile << "apply plugin: 'java'"
        file('src/main/java').mkdirs()
        EclipseProject project = loadToolingModel(EclipseProject)
        EclipseSourceDirectory sourceDirectory = project.sourceDirectories.find { it.path == 'src/main/java' }

        when:
        sourceDirectory.getOutput()

        then:
        thrown UnsupportedMethodException
    }

    def "Source directory has no output specified"() {
        setup:
        settingsFile << 'rootProject.name = "root"'
        buildFile << "apply plugin: 'java'"
        file('src/main/java').mkdirs()

        when:
        EclipseProject project = loadToolingModel(EclipseProject)

        then:
        project.sourceDirectories.size() == 1
        project.sourceDirectories[0].getOutput() == null
    }

    def "Source directory has output specified"() {
        setup:
        settingsFile << 'rootProject.name = "root"'
        buildFile <<
        """apply plugin: 'java'
           apply plugin: 'eclipse'
           eclipse {
               classpath {
                   file {
                       whenMerged { classpath ->
                           classpath.entries.find { it.kind == 'src' && it.path == 'src/main/java' }.output = 'customOutput'
                       }
                   }
               }
           }
        """
        file('src/main/java').mkdirs()

        when:
        EclipseProject project = loadToolingModel(EclipseProject)

        then:
        project.sourceDirectories.size() == 1
        project.sourceDirectories[0].output == 'customOutput'
    }
}