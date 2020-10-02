package net.xiaoyu233.mitemod.miteite.util;

import java.util.function.Function;

public class BiLazyValue<T,R> {
    private Function<T,R> getter;
    private R delegate;
    public BiLazyValue(Function<T,R> getter){
        this.getter = getter;
    }

    public  R get(T arg){
        if (getter != null){
            this.delegate = this.getter.apply(arg);
            this.getter = null;
        }
        return delegate;
    }
}
