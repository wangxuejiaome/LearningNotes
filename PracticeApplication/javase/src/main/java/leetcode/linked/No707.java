package leetcode.linked;

/**
 * author : wangxuejiao@leoao.com
 * date   : 2022/1/27
 * desc   : https://leetcode-cn.com/problems/design-linked-list/
 * Your MyLinkedList object will
 * MyLinkedList obj = new MyLinke
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
public class No707 {

    public static void main(String[] args) {
        MyLinkedList myLinkedList = new MyLinkedList();
//        System.out.println("get 0: " + myLinkedList.get(0));
        myLinkedList.addAtHead(1);
//        System.out.println("get 0: " + myLinkedList.get(0));
        myLinkedList.addAtTail(3);
//        System.out.println("get 1: " + myLinkedList.get(1));
        myLinkedList.addAtIndex(1,2);
//        System.out.println("get 1: " + myLinkedList.get(1));
        myLinkedList.deleteAtIndex(1);
        System.out.println("get 1: " + myLinkedList.get(1));
    }
}

class MyLinkedList {

    int val;
    MyLinkedList next;

    public MyLinkedList() {

    }

    public MyLinkedList(int val, MyLinkedList next) {
        this.val = val;
        this.next = next;
    }

    public int get(int index) {
        if(index < 0) {
            return -1;
        } else {
            int position = -1;
            MyLinkedList pNode = new MyLinkedList(0,this);
            while(position < index) {
                if(pNode.next == null) {
                    break;
                } else {
                    pNode = pNode.next;
                    position++;
                }
            }
            return (position == index && pNode.val != 0) ?  pNode.val : -1;
        }
    }

    public void addAtHead(int val) {
        if(this.val == 0) {
            this.next = null;
        } else {
            this.next = new MyLinkedList(this.val,this.next);
        }
        this.val = val;
    }

    public void addAtTail(int val) {
        if(this.val == 0) {
            addAtHead(val);
        } else {
            MyLinkedList pNode = this;
            while(pNode.next != null) {
                pNode = pNode.next;
            }
            MyLinkedList tailNode = new MyLinkedList();
            tailNode.val = val;
            pNode.next = tailNode;
        }
    }

    public void addAtIndex(int index, int val) {
        if(index < 0) {
            addAtHead(val);
        } else {
            int position = 0;
            MyLinkedList pNode = this;
            while(position < index -1 ) {
                if(pNode.next == null) {
                    break;
                } else {
                    pNode = pNode.next;
                    position++;
                }
            }
            if(position == index - 1) {
                pNode.next = new MyLinkedList(val,pNode.next);
            }
        }
    }

    public void deleteAtIndex(int index) {
        if(this.val != 0 && index >= 0) {
            int position = -1;
            MyLinkedList pNode = new MyLinkedList(0,this);
            while(position < index - 1) {
                if(pNode.next == null) {
                    break;
                } else {
                    pNode = pNode.next;
                    position++;
                }
            }
            if(position == index - 1 && pNode.next != null) {
                pNode.next = pNode.next.next;
            }
        }
    }
}
