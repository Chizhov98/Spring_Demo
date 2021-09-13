package com.chizhov.spring.service;

import com.chizhov.spring.dao.HorseDao;
import com.chizhov.spring.dao.RaceDao;
import com.chizhov.spring.dao.RaceListDao;
import com.chizhov.spring.entity.Horse;
import com.chizhov.spring.entity.Race;
import com.chizhov.spring.entity.RaceList;
import com.chizhov.spring.tdo.ProfileInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RaceService {
    @Autowired
    private static HorseDao horseDao;
    @Autowired
    private static RaceDao raceDao;
    @Autowired
    private static RaceListDao raceListDao;
    private static int horseCount;
    private static final Random random = new Random();

    private static final int DISTANCE = 1000;
    private static final int MIN_STEP = 100;
    private static final int STEP_RANGE = 100;
    private static final int MIN_SLEEP_TIME = 400;
    private static final int SLEEP_RANGE = 100;

    public static boolean startNewRace(int chosenHorseId) throws InterruptedException {
        Horse chosenHorse = horseDao.findById(chosenHorseId).orElse(new Horse());
        Race race = new Race();
        race.setDate(LocalDate.now());
        List<Horse> horseList = (List<Horse>) horseDao.findAll();
        horseCount = horseList.size();
        List<Horse> results = new ArrayList<>();
        results = startHorseRun(horseList, results);
        raceDao.save(race);
        saveRunData(race, results, chosenHorse);
        return true;
    }

    private static void saveRunData(Race race, List<Horse> results, Horse chosen) {
        int horsePos = 0;
        RaceList list;
        for (Horse horse : results) {
            list = new RaceList();
            list.setHorse(horse);
            list.setChosen(horse.getId() == chosen.getId());
            list.setPosition(++horsePos);
            list.setRace(race);
            raceListDao.save(list);
        }
    }

    private static List<Horse> startHorseRun(List<Horse> horses, List<Horse> results) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(horses.size());
        Runnable r = () -> {
            Horse horse = horses.get(--horseCount);
            boolean isFinished = false;
            int distanceLeft = DISTANCE;
            while (!isFinished) {
                distanceLeft -= random.nextInt(STEP_RANGE) + MIN_STEP;
                if (distanceLeft > 0) {
                    try {
                        Thread.sleep(random.nextInt(SLEEP_RANGE) + MIN_SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    results.add(horse);
                    isFinished = true;
                }
            }
            latch.countDown();
        };
        for (int i = 0; i < horses.size(); i++) {
            executor.execute(r);
        }
        while (results.size() != horses.size()) {
            Thread.sleep(SLEEP_RANGE);
        }
        return results;
    }

    public static List<RaceList> getRaceInfo(int id) {
        return raceListDao.getAllByRaceId(id);
    }

    public static Race getRaceById(int id) {
        return raceDao.findById(id).get();
    }

    public static List<Horse> getAllHorses() {
        return (List<Horse>) horseDao.findAll();
    }

    public static ProfileInfo getProfileInfo() {
        return new ProfileInfo(raceDao.countAllById(), raceListDao.countAllByIdAndChosenIsTrue());
    }

}
