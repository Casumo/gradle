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

package org.gradle.model.internal.inspect;

import com.google.common.collect.ImmutableList;
import org.gradle.model.InvalidModelRuleDeclarationException;
import org.gradle.model.internal.core.ModelPath;
import org.gradle.model.internal.core.ModelRegistration;
import org.gradle.model.internal.core.rule.describe.ModelRuleDescriptor;
import org.gradle.model.internal.registry.ModelRegistry;

import java.util.List;

public class ExtractedModelRegistration implements ExtractedModelRule {

    private final ModelRegistration registration;

    public ExtractedModelRegistration(ModelRegistration registration) {
        this.registration = registration;
    }

    @Override
    public void apply(ModelRegistry modelRegistry, ModelPath scope) {
        if (!scope.equals(ModelPath.ROOT)) {
            throw new InvalidModelRuleDeclarationException(String.format("Rule %s cannot be applied at the scope of model element %s as creation rules cannot be used when applying rule sources to particular elements", registration.getDescriptor(), scope));
        }
        modelRegistry.register(registration);
    }

    @Override
    public ModelRuleDescriptor getDescriptor() {
        return registration.getDescriptor();
    }

    @Override
    public List<Class<?>> getRuleDependencies() {
        return ImmutableList.of();
    }
}
