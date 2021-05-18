import 'package:flutter/material.dart';
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
                        // todo 调转到下一页
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