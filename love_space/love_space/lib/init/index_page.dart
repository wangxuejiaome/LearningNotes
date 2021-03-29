import 'package:flutter/material.dart';
import 'package:love_space/home/home_page.dart';
import 'package:love_space/learn/learn_page.dart';
import 'package:love_space/mine/mine_page.dart';

class IndexPage extends StatefulWidget {
  _IndexPageState createState() => _IndexPageState();
}

class _IndexPageState extends State<IndexPage> {
  final List<BottomNavigationBarItem> bottomTabs = [
    BottomNavigationBarItem(icon: Icon(Icons.add_location), label: '首页'),
    BottomNavigationBarItem(icon: Icon(Icons.book), label: '学习'),
    BottomNavigationBarItem(icon: Icon(Icons.person), label: '我的')
  ];
  final List tabBodies = [HomePage(),LearnPage(),MinePage()];

  int currentIndex = 0;
  var currentPage;

  @override
  void initState() {
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
        onTap: (index){
          setState(() {
            currentIndex = index;
            currentPage = tabBodies[currentIndex];
          });
        },
      ),
      body: currentPage,
    );
  }
}
