package com.joanfuentes.xingsprojectrepositories.data.memcache;

import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SparseArrayCompat;

import com.joanfuentes.xingsprojectrepositories.data.Cacheable;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RepoMemcache implements Cacheable {
    private static RepoMemcache instance = new RepoMemcache();
    private final static long VALID_PERIOD_IN_MILLIS = 5 * 60 * 1000;
    private final static long VALID_TIME_WHEN_NO_CACHE = 0;
    private final SparseArrayCompat<List<Repo>> reposByPage;
    private final LongSparseArray<Long> validTimesByReposPage;

    public RepoMemcache() {
        reposByPage = new SparseArrayCompat<>();
        validTimesByReposPage = new LongSparseArray<>();
    }

    public static RepoMemcache getInstance() {
        return instance;
    }

    public void saveRepos(List<Repo> repos, int page) {
        long now = Calendar.getInstance().getTimeInMillis();
        long bestBeforeTimeInMillis = now + VALID_PERIOD_IN_MILLIS;
        synchronized (validTimesByReposPage) {
            reposByPage.put(page, repos);
            validTimesByReposPage.put(page, bestBeforeTimeInMillis);
        }
    }

    public List<Repo> getRepos(int page) {
        List<Repo> repos = new ArrayList<>();
        synchronized (validTimesByReposPage) {
            long validTime = validTimesByReposPage.get(page, VALID_TIME_WHEN_NO_CACHE);
            long now = Calendar.getInstance().getTimeInMillis();
            if (validTime >= now) {
                repos = reposByPage.get(page, new ArrayList<Repo>());
            }
        }
        return repos;
    }

    @Override
    public void invalidate() {
        synchronized (validTimesByReposPage) {
            reposByPage.clear();
            validTimesByReposPage.clear();
        }
    }
}
