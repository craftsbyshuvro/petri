package com.wixpress.petri.experiments.domain;

import com.wixpress.petri.laboratory.UserInfo;

/**
 * @author: talyag
 * @since: 11/26/13
 */
public class RegisteredUsersFilter implements Filter {


    public RegisteredUsersFilter() {
    }

    @Override
    public String toString() {
        return "RegisteredUsersFilter";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public boolean isEligible(UserInfo user, Experiment experiment) {
        return !user.isAnonymous();
    }
}
