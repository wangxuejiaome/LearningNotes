package wxj.aidlservicetest.binderpool;

interface IBinderPool{

    /**
    * @param bindCode, the unique token of specific Binder<br/>
    * @return specific Binder who's token is binderCode.
    */
    IBinder queryBinder(int binderCode);
}