package org.berendeev.roma.smarttodo.domain.interactor;


public abstract class Interactor<T, P> {

    private InteractorExecutor requestExecutor;
    private InteractorExecutor responseExecutor;

    public Interactor(InteractorExecutor requestExecutor, InteractorExecutor responseExecutor) {
        this.requestExecutor = requestExecutor;
        this.responseExecutor = responseExecutor;
    }

    public void executeInteractor(final T requestValue, final Callback<P> callback) {
        requestExecutor.execute(new Runnable() {
            @Override public void run() {
                operation(requestValue, new InteractorCallback<P>(callback, responseExecutor));
            }
        });
    }

    protected abstract void operation(T requestValue, Callback<P> callback);

    public interface Callback<P> {
        void onSuccess(P responseValue);

        void onError(Throwable t);
    }

    private static class InteractorCallback<P> implements Callback<P> {
        private Callback<P> callback;
        private InteractorExecutor executor;

        public InteractorCallback(Callback<P> callback, InteractorExecutor executor) {
            this.callback = callback;
            this.executor = executor;
        }

        public void onSuccess(final P response) {
            executor.execute(new Runnable() {
                @Override public void run() {
                    callback.onSuccess(response);
                }
            });
        }

            @Override public void onError (final Throwable t){
                executor.execute(new Runnable() {
                    @Override public void run() {
                        callback.onError(t);
                    }
                });
            }
        }
    }
