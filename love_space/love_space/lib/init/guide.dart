import 'package:flutter/material.dart';
import 'package:love_space/init/login.dart';
import 'package:love_space/util/utils.dart';

class GuidePage extends StatefulWidget {
  _GuidePageState createState() => _GuidePageState();
}

class _GuidePageState extends State<GuidePage> {
  final List<String> guideImageNames = [
    "guide_page1",
    "guide_page2",
    "guide_page3"
  ];
  int currentIndex = 0;

  // void _goHomePage(BuildContext context) {
  //   if (SpUtil.getString(PreferencesKey.token).isEmpty) {
  //     NavigatorUtils.push(context, EntranceRouter.loginPage, replace: true);
  //   } else {
  //     NavigatorUtils.push(context, HomeRouter.mainPage, replace: true);
  //   }
  // }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: PageView(
        children: <Widget>[
          Image(image: AssetImage(Utils.getImgPath(guideImageNames[0]))),
          Image(image: AssetImage(Utils.getImgPath(guideImageNames[1]))),
          Stack(
            alignment: Alignment.topCenter,
            children: [
              Image(image: AssetImage(Utils.getImgPath(guideImageNames[2]))),
              Positioned(
                  bottom: MediaQuery.of(context).size.height * 0.2,
                  child: OutlinedButton(
                      onPressed: () {
                        Navigator.of(context).push(MaterialPageRoute(builder: (context){
                          return LoginPage();
                        }));
                      },
                      child: Text("立即体验")))
            ],
          )
        ],
      ),
      backgroundColor: Colors.white,
    );
  }
}
