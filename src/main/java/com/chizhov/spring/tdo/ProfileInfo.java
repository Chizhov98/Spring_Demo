package com.chizhov.spring.tdo;

import lombok.Getter;

@Getter
public class ProfileInfo {
    private long raceCount;
    private long winRate;

    public ProfileInfo(long raceCount, long winRate) {
        this.raceCount = raceCount;
        this.winRate = winRate;
    }
}
