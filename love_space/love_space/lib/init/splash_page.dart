import 'package:flutter/material.dart';

import '../util/utils.dart';

class SplashPage extends StatelessWidget {
  void _goGuidePage(BuildContext context) {
    Future.delayed(Duration(seconds: 2), () {
      Navigator.pushReplacementNamed(context, '/init/guide');
    });
  }

  @override
  Widget build(BuildContext context) {
    _goGuidePage(context);
    return Material(
      color: Colors.white,
      child: Center(
        child: Image(
          image: AssetImage(Utils.getImgPath('bg_splash')),
          width: 270,
          height: 225,
        ),
      ),
    );
  }
}
