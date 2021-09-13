package com.chizhov.spring.tdo;

import lombok.Getter;

@Getter
public class ProfileInfo {
    private int raceCount;
    private int winRate;

    public ProfileInfo(int raceCount, int winRate) {
        this.raceCount = raceCount;
        this.winRate = winRate;
    }

}
