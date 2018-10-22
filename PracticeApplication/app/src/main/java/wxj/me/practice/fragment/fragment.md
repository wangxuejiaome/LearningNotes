Fragment 与 Activity 生命周期关系



* 打开Activity

wxj.me.practice D/FragmentA: onAttach                                                                                                           wxj.me.practice D/FragmentA: onCreate
wxj.me.practice D/FragmentA: onCreateView
wxj.me.practice D/FragmentA: onViewCreated
**wxj.me.practice D/ContainerActivity: onCreate**
wxj.me.practice D/FragmentA: onActivityCreated
**wxj.me.practice D/ContainerActivity: onStart**
wxj.me.practice D/FragmentA: onStart
**wxj.me.practice D/ContainerActivity: onResume**
wxj.me.practice D/FragmentA: onResume



* 返回主屏幕 / 锁屏

wxj.me.practice D/FragmentA: onPause
wxj.me.practice D/ContainerActivity: onPause
wxj.me.practice D/FragmentA: onStop
wxj.me.practice D/ContainerActivity: onStop



* 再次返回 Activity

wxj.me.practice D/ContainerActivity: onRestart                                                                                    wxj.me.practice D/ContainerActivity: onStart
wxj.me.practice D/FragmentA: onStart
wxj.me.practice D/ContainerActivity: onResume
wxj.me.practice D/FragmentA: onResume



* 关闭 Activity

wxj.me.practice D/FragmentA: onPause
wxj.me.practice D/ContainerActivity: onPause
wxj.me.practice D/FragmentA: onStop
wxj.me.practice D/ContainerActivity: onStop
wxj.me.practice D/FragmentA: onDestroyView
wxj.me.practice D/FragmentA: onDestroy
wxj.me.practice D/FragmentA:  onDetach
wxj.me.practice D/ContainerActivity: onDestroy



关于Fragment和activity执行顺序，只有在暂停和销毁时Fragment生命周期是先行的，其他时候都是activity先走，这样才符合包含于被包含的生活常