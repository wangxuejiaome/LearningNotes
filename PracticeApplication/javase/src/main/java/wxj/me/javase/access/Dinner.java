package wxj.me.javase.access;

import wxj.me.javase.access.dessert.Cookie;

public class Dinner {

    public static void main(String[] args) {
        Cookie cookie = new Cookie();
        //! cookie.bite(); // Can't access
    }
}
