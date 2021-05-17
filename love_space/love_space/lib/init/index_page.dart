import 'package:flutter/material.dart';
import 'package:love_space/home/home_page.dart';
import 'package:love_space/learn/learn_page.dart';
import 'package:love_space/mine/mine_page.dart';
import 'package:love_space/util/utils.dart';

class IndexPage extends StatefulWidget {
  _IndexPageState createState() => _IndexPageState();
}

class _IndexPageState extends State<IndexPage> {
  List<BottomNavigationBarItem> bottomTabs;
  final List tabBodies = [HomePage(), LearnPage(), LearnPage(),MinePage()];

  int currentIndex = 0;
  var currentPage;

  @override
  void initState() {
    // 底部按钮
    bottomTabs = [
      BottomNavigationBarItem(
          icon: _createBottomNavigationBarItemIcon(
              'ic_bottom_home_normal',false),
          activeIcon: _createBottomNavigationBarItemIcon(
              'ic_bottom_home_active',true),
          label: '首页'),
      BottomNavigationBarItem(
          icon: _createBottomNavigationBarItemIcon(
              'ic_bottom_activity_normal',false),
          activeIcon: _createBottomNavigationBarItemIcon(
              'ic_bottom_activity_active',true),
          label: '活动'),
      BottomNavigationBarItem(
          icon: _createBottomNavigationBarItemIcon(
              'ic_bottom_learn_normal',false),
          activeIcon: _createBottomNavigationBarItemIcon(
              'ic_bottom_learn_active',true),
          label: '学习'),
      BottomNavigationBarItem(
          icon: _createBottomNavigationBarItemIcon(
              'ic_bottom_mine_normal',false),
          activeIcon: _createBottomNavigationBarItemIcon(
              'ic_bottom_mine_active',true),
          label: '我的'),
    ];
    currentPage = tabBodies[currentIndex];
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color.fromRGBO(244, 244, 2, 1.0),
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        currentIndex: currentIndex,
        items: bottomTabs,
        onTap: (index) {
          setState(() {
            currentIndex = index;
            currentPage = tabBodies[currentIndex];
          });
        },
      ),
      body: currentPage,
    );
  }

  /// 创建底部导航栏图标
  Image _createBottomNavigationBarItemIcon(String imageName,bool isActive) {
    return Image(
      image: AssetImage(Utils.getImgPath(imageName)),
      width: isActive ? 28 : 24,
      height: isActive ? 28: 24,
    );
  }
}
