package com.wixpress.petri.experiments.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wixpress.petri.laboratory.UserInfo;

/**
 * @author: talyag
 * @since: 5/15/14
 */
public class ConstantFilter implements Filter {

    private final boolean value;

    @JsonCreator
    public ConstantFilter(@JsonProperty(value = "value") boolean value) {
        this.value = value;
    }

    @Override
    public boolean isEligible(UserInfo user, Experiment experiment) {
        return value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantFilter that = (ConstantFilter) o;

        if (value != that.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (value ? 1 : 0);
    }
}
