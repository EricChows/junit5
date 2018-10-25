/*
 * Copyright 2015-2018 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.platform.testkit;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

import java.util.Collections;
import java.util.List;

import org.apiguardian.api.API;
import org.junit.platform.commons.util.Preconditions;
import org.junit.platform.engine.TestDescriptor;

/**
 * Represents the entirety of multiple test or container execution runs.
 *
 * @since 1.4
 */
@API(status = EXPERIMENTAL, since = "1.4")
public class ExecutionResults {

	private static final String CATEGORY = "All";

	private final List<ExecutionEvent> executionEvents;
	private final FilteredResults containerResults;
	private final FilteredResults testResults;

	/**
	 * Construct an {@link ExecutionResults} given a {@link List} of recorded {@link ExecutionEvent}s.
	 *
	 * @param events the {@link List} of {@link ExecutionEvent}s to use when creating the execution graph, cannot be null
	 */
	ExecutionResults(List<ExecutionEvent> events) {
		Preconditions.notNull(events, "ExecutionEvent list must not be null");
		Preconditions.containsNoNullElements(events, "ExecutionEvent list must not contain null elements");

		this.executionEvents = Collections.unmodifiableList(events);
		this.testResults = new FilteredResults(events, TestDescriptor::isTest, "Test");
		this.containerResults = new FilteredResults(events, TestDescriptor::isContainer, "Container");
	}

	// --- Fluent API ----------------------------------------------------------

	/**
	 * Get all recorded events.
	 */
	public Events events() {
		return new Events(this.executionEvents, CATEGORY);
	}

	/**
	 * Get the filtered results for all containers.
	 *
	 * <p>In this context, the word "container" applies to {@link TestDescriptor
	 * TestDescriptors} that return {@code true} from
	 * {@link TestDescriptor#isContainer()}.
	 */
	public FilteredResults containers() {
		return this.containerResults;
	}

	/**
	 * Get the filtered results for all tests.
	 *
	 * <p>In this context, the word "test" applies to {@link TestDescriptor
	 * TestDescriptors} that return {@code true} from
	 * {@link TestDescriptor#isTest()}.
	 */
	public FilteredResults tests() {
		return this.testResults;
	}

	/**
	 * Get all recorded executions.
	 */
	public Executions executions() {
		return new Executions(this.executionEvents, CATEGORY);
	}

	// --- ALL Execution Events ------------------------------------------------

	public List<ExecutionEvent> getExecutionEvents() {
		return this.executionEvents;
	}

	// --- Reporting Entry Publication Execution Events ------------------------

	public long getReportingEntryPublicationCount() {
		return events().reportingEntryPublished().count();
	}

	// --- Dynamic Test Execution Events ---------------------------------------

	public long getDynamicTestRegistrationCount() {
		return events().dynamicTestRegistered().count();
	}

	// --- Container Execution Events ------------------------------------------

	public long getContainersSkippedCount() {
		return containers().events().skipped().count();
	}

	public long getContainersStartedCount() {
		return containers().events().started().count();
	}

	public long getContainersFinishedCount() {
		return containers().events().finished().count();
	}

	public long getContainersFailedCount() {
		return containers().events().failed().count();
	}

	public long getContainersAbortedCount() {
		return containers().events().aborted().count();
	}

	// --- Test Execution Events -----------------------------------------------

	public List<ExecutionEvent> getTestsSuccessfulEvents() {
		return tests().events().succeeded().list();
	}

	public List<ExecutionEvent> getTestsFailedEvents() {
		return tests().events().failed().list();
	}

	public long getTestsSkippedCount() {
		return tests().events().skipped().count();
	}

	public long getTestsStartedCount() {
		return tests().events().started().count();
	}

	public long getTestsFinishedCount() {
		return tests().events().finished().count();
	}

	public long getTestsSuccessfulCount() {
		return tests().events().succeeded().count();
	}

	public long getTestsFailedCount() {
		return tests().events().failed().count();
	}

	public long getTestsAbortedCount() {
		return tests().events().aborted().count();
	}

}