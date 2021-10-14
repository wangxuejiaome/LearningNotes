import 'package:flutter/material.dart';
import 'package:love_space/res/color.dart';

class LoginPage extends StatefulWidget {
  _LoginState createState() => _LoginState();
}

class _LoginState extends State<LoginPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Padding(
        padding: EdgeInsets.fromLTRB(50, 42, 50, 74),
        child: Column(
          children: [
            SizedBox(height: 120),
            Text(
              '欢迎登录爱吧',
              style: TextStyle(color: Colours.text_dark,fontSize: 28),
            ),
            Text(
              '手机验证码直接登录，无需注册',
              style: TextStyle(color: Colours.text_sub,fontSize: 12),
            ),
            SizedBox(height: 72),
            TextField(
              decoration: InputDecoration(
                hintText: '手机号码',
                hintStyle: TextStyle(color: Color(0xFFCCCCCC),fontSize: 16),
                suffix: Text(
                  '获取验证码',
                  style: TextStyle(color: Color(0xFFCC0044),fontSize: 16),
                ),
              ),
            ),
            TextField(
              decoration: InputDecoration(
                hintText: '验证码',
                hintStyle: TextStyle(color: Color(0xFFCCCCCC),fontSize: 16),
                suffix: Text(
                  '获取验证码',
                  style: TextStyle(color: Color(0xFFCC0044),fontSize: 16),
                ),
              ),
            ),
          ],
        ),
      ),


    );
  }
  
}