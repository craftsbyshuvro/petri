package com.wixpress.petri.laboratory;


import com.wixpress.petri.experiments.domain.Experiment;
import com.wixpress.petri.laboratory.dsl.ExperimentMakers;
import com.wixpress.petri.util.RepeatRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import static com.natpryce.makeiteasy.MakeItEasy.*;
import static com.wixpress.petri.laboratory.dsl.ExperimentMakers.*;
import static com.wixpress.petri.laboratory.dsl.TestGroupMakers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author: talyag
 * @since: 12/31/13
 */
public class GuidTestGroupAssignmentStrategyTest {

    public static final Experiment experiment = an(ExperimentMakers.Experiment,
            with(id, 1),
            with(key, "someKey"),
            with(scope, "someScope"),
            with(testGroups, listOf(
                    a(TestGroup,
                            with(probability, 80),
                            with(value, "OLD")),
                    a(TestGroup,
                            with(probability, 20),
                            with(value, "NEW"))
            ))
    ).make();
    public static final Experiment experimentWithOriginalId = an(ExperimentMakers.Experiment,
            with(id, 2),
            with(originalId, experiment.getId()),
            with(key, "someKey"),
            with(scope, "someScope"),
            with(testGroups, listOf(
                    a(TestGroup,
                            with(probability, 20),
                            with(value, "OLD")),
                    a(TestGroup,
                            with(probability, 80),
                            with(value, "NEW"))
            ))
    ).make();
    public static final Experiment linkedExperiment = an(ExperimentMakers.Experiment,
            with(id, 13),
            with(originalId, 11),
            with(linkedId, 1),
            with(key, "someKey"),
            with(scope, "someScope"),
            with(testGroups, listOf(
                    a(TestGroup,
                            with(probability, 20),
                            with(value, "OLD")),
                    a(TestGroup,
                            with(probability, 80),
                            with(value, "NEW"))
            ))
    ).make();

    @Rule
    public RepeatRule repeatRule = new RepeatRule();

    @RepeatRule.Repeat(times = 1000)
    @Test
    public void expandedExperimentCanOnlyReturnHigherTestGroup() throws Exception {
        // a 'magic number' - an example of a uid that causes the test to fail if original_id is ignored
        // (specifically for the testgroups with 20-80)
        // UserGuid.of("73699180-bcf1-4bf3-8d04-676b8444b691")

        String guid = randomGuid();
        GuidTestGroupAssignmentStrategy strategy = new GuidTestGroupAssignmentStrategy();

        String toss = strategy.getAssignment(experiment, guid).getValue();
        String expandedToss = strategy.getAssignment(experimentWithOriginalId, guid).getValue();

        if (toss.equals("NEW")) {
            assertThat(expandedToss, is("NEW"));
        }
    }

    @RepeatRule.Repeat(times = 1000)
    @Test
    public void linkedExperimentCanOnlyReturnHigherTestGroup() throws Exception {
        // a 'magic number' - an example of a uid that causes the test to fail if original_id is ignored
        // (specifically for the testgroups with 20-80)
        // UserGuid.of("73699180-bcf1-4bf3-8d04-676b8444b691")

        String guid = randomGuid();
        GuidTestGroupAssignmentStrategy strategy = new GuidTestGroupAssignmentStrategy();

        String toss = strategy.getAssignment(experiment, guid).getValue();
        String expandedToss = strategy.getAssignment(linkedExperiment, guid).getValue();

        if (toss.equals("NEW")) {
            assertThat(expandedToss, is("NEW"));
        }
    }

    private String randomGuid() {
        return UUID.randomUUID().toString();
    }
}
