package com.joanfuentes.xingsprojectrepositories.domain.usecase;

import com.joanfuentes.xingsprojectrepositories.data.ReposRepository;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;
import com.joanfuentes.xingsprojectrepositories.threading.PostExecutionThread;
import com.joanfuentes.xingsprojectrepositories.threading.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

public class GetReposUseCase implements Runnable {
    private final ThreadExecutor executor;
    private final PostExecutionThread postExecution;
    private final ReposRepository repository;
    private Callback callback;
    private int page;

    @Inject
    public GetReposUseCase(ThreadExecutor executor,
                            PostExecutionThread postExecution,
                            ReposRepository repository) {
        this.executor = executor;
        this.postExecution = postExecution;
        this.repository = repository;
    }

    @Override
    public void run() {
        repository.getRepos(page, new ReposRepository.Callback() {
            @Override
            public void onSuccess(final List<Repo> repos) {
                notifyOnSuccess(repos);
            }

            @Override
            public void onError() {
                notifyOnError();
            }
        });
    }

    public void execute(final int page, final Callback callback) {
        this.page = page;
        this.callback = callback;
        this.executor.execute(this);
    }

    public interface Callback {
        void onReposReady(List<Repo> repos);
        void onError();
    }

    private void notifyOnSuccess(final List<Repo> repos) {
        postExecution.post(new Runnable() {
            @Override
            public void run() {
                callback.onReposReady(repos);
            }
        });
    }

    protected void notifyOnError() {
        postExecution.post(new Runnable() {
            @Override
            public void run() {
                callback.onError();
            }
        });
    }
}