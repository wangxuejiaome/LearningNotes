package wxj.me.practice

import wxj.me.practice.groovy.Person
class Main {
    def cc = {
        name = "hanmeimei"
        age = 26
        eat("油条")
        eat "油条"
    }

    static void main(String... args) {
        Main main = new Main();
        Person person = new Person(name:"lilei",age: 14)
        println person.toString()

        main.cc.delegate = person
        main.cc.call()
        println person.toString()
    }
}