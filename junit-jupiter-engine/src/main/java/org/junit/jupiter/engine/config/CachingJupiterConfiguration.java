/*
 * Copyright 2015-2018 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.jupiter.engine.config;

import static org.apiguardian.api.API.Status.INTERNAL;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.apiguardian.api.API;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.ExecutionMode;

@API(status = INTERNAL, since = "5.4")
public class CachingJupiterConfiguration implements JupiterConfiguration {

	private final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();
	private final JupiterConfiguration delegate;

	public CachingJupiterConfiguration(JupiterConfiguration delegate) {
		this.delegate = delegate;
	}

	@Override
	public Optional<String> getRawConfigurationParameter(String key) {
		return delegate.getRawConfigurationParameter(key);
	}

	@Override
	public boolean isParallelExecutionEnabled() {
		return (boolean) cache.computeIfAbsent(PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME,
			key -> delegate.isParallelExecutionEnabled());
	}

	@Override
	public boolean isExtensionAutoDetectionEnabled() {
		return (boolean) cache.computeIfAbsent(EXTENSIONS_AUTODETECTION_ENABLED_PROPERTY_NAME,
			key -> delegate.isExtensionAutoDetectionEnabled());
	}

	@Override
	public ExecutionMode getDefaultExecutionMode() {
		return (ExecutionMode) cache.computeIfAbsent(DEFAULT_EXECUTION_MODE_PROPERTY_NAME,
			key -> delegate.getDefaultExecutionMode());
	}

	@Override
	public TestInstance.Lifecycle getDefaultTestInstanceLifecycle() {
		return (TestInstance.Lifecycle) cache.computeIfAbsent(DEFAULT_TEST_INSTANCE_LIFECYCLE_PROPERTY_NAME,
			key -> delegate.getDefaultTestInstanceLifecycle());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<String> getDeactivateExecutionConditionsPattern() {
		return (Optional<String>) cache.computeIfAbsent(DEACTIVATE_CONDITIONS_PATTERN_PROPERTY_NAME,
			key -> delegate.getDeactivateExecutionConditionsPattern());
	}

}