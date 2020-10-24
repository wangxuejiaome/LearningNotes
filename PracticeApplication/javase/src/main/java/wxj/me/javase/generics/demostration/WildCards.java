package wxj.me.javase.generics.demostration;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: WildCards
 * Author: wangxuejiao
 * Date: 2019/12/20 14:34
 * Description:
 * Version: 1.0.0
 */
public class WildCards {
    // Raw argument:
    static void rawArgs(Holder holder,Object arg){
        // Warning: Unchecked call to set(T) as a member of the raw type Holder
        holder.set(arg);
        holder.set(new WildCards());
        // Can't do this: don't have any 'T'
        // T t = holder.get();
        // OK,but type information has been lost:
        Object object = holder.get();
    }
}
