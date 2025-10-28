package com.trader.shared.dto.identity.trader;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UsernameChangeStatus {
    private final boolean canChange;
    private final long secondsUntilChangeAllowed;

    @JsonCreator
    public UsernameChangeStatus(
            @JsonProperty("canChange") boolean canChange,
            @JsonProperty("secondsUntilChangeAllowed") long secondsUntilChangeAllowed) {
        this.canChange = canChange;
        this.secondsUntilChangeAllowed = secondsUntilChangeAllowed;
    }

    public boolean isCanChange() {
        return canChange;
    }

    public long getSecondsUntilChangeAllowed() {
        return secondsUntilChangeAllowed;
    }
}