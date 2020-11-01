/**
 * OWNER_FIRST:Will succeed, because the x and y fields declared in the Test class shadow the variables in the delegate
 */
//class Test {
//    def x = 30
//    def y = 40
//
//    def run() {
//        def data = [ x: 10, y: 20]
//        def cl = { y = x + y }
//        cl.delegate = data
//        cl()
//        assert x == 30
//        assert y == 70
//        assert data == [x: 10, y: 20]
//    }
//
//    public static void main(String[] args) {
//        new Test().run()
//    }
//}

/**
 * DELEGATE_FIRST:This will succeed, because the x and y variables declared in the delegate shadow the fields in the owner class
 */
//class Test {
//    def x = 30
//    def y = 40
//
//    def run() {
//        def data = [ x: 10, y: 20 ]
//        def cl = { y = x + y }
//        cl.delegate = data
//        cl.resolveStrategy = Closure.DELEGATE_FIRST
//        cl()
//        assert x == 30
//        assert y == 40
//        assert data == [x:10, y:30]
//    }
//
//    public static void main(String[] args) {
//        new Test().run()
//    }
//}

