/*
 * Copyright 2012 the original author or authors.
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

package org.gradle.api.buildcomparison.outcome.internal

import org.gradle.plugins.buildcomparison.outcome.string.StringBuildOutcome

import spock.lang.Specification
import org.gradle.plugins.buildcomparison.outcome.internal.ByTypeAndNameBuildOutcomeAssociator
import org.gradle.plugins.buildcomparison.outcome.internal.BuildOutcome

class ByTypeAndNameBuildOutcomeAssociatorTest extends Specification {

    def associator = new ByTypeAndNameBuildOutcomeAssociator(StringBuildOutcome)

    def "associates"(BuildOutcome from, BuildOutcome to, boolean match) {
        expect:
        associator.findAssociationType(from, to) == (match ? StringBuildOutcome : null)

        where:
        from     | to                               | match
        str("a") | str("a")                         | true
        str("b") | str("a")                         | false
        str("a") | new OtherBuildOutcome(name: "a") | false
        str("b") | new OtherBuildOutcome(name: "a") | false
    }

    StringBuildOutcome str(String str) {
        new StringBuildOutcome(str, str)
    }

    static class OtherBuildOutcome implements BuildOutcome {
        String name
        String description
    }
}
