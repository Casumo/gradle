/*
 * Copyright 2015 the original author or authors.
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

package org.gradle.performance.fixture

import org.gradle.internal.jvm.Jvm
import org.gradle.internal.os.OperatingSystem
import org.gradle.util.GradleVersion

class BuildReceiptPerformanceTestRunner extends CrossBuildPerformanceTestRunner {
    String incomingDir

    public BuildReceiptPerformanceTestRunner(BuildExperimentRunner experimentRunner, DataReporter<CrossBuildPerformanceResults> dataReporter) {
        super(experimentRunner, dataReporter)
    }

    @Override
    public CrossBuildPerformanceResults run() {
        assert !specs.empty
        assert testId
        def results = new CrossBuildPerformanceResults(
            testId: testId,
            testGroup: testGroup,
            jvm: Jvm.current().toString(),
            operatingSystem: OperatingSystem.current().toString(),
            versionUnderTest: GradleVersion.current().getVersion(),
            vcsBranch: Git.current().branchName,
            vcsCommits: [Git.current().commitId, Incoming.get(incomingDir).commitSha()],
            testTime: System.currentTimeMillis()
        )
        runAllSpecifications(results)

        results.assertEveryBuildSucceeds()
        reporter.report(results)

        return results
    }
}
