package com.mytooltest.retrofit2_rxjava2_mvp.http.result;


import com.mytooltest.retrofit2_rxjava2_mvp.http.exception.ApiException;
import com.mytooltest.retrofit2_rxjava2_mvp.http.exception.CustomException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;


public class ResultTransformer {

    public static <T> ObservableTransformer<BaseResult<T>, T> handleResult() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends BaseResult<T>>> {

        @Override
        public ObservableSource<? extends BaseResult<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务器返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<BaseResult<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(BaseResult<T> tResponse) throws Exception {
            int code = tResponse.getCode();
            String message = tResponse.getMsg();
            if (code == 200) {
                return Observable.just(tResponse.getData());
            } else {
                return Observable.error(new ApiException(code, message));
            }
        }
    }


}
