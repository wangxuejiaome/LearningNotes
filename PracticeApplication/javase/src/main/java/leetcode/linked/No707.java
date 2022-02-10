package leetcode.linked;

import java.util.Locale;

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
        myLinkedList.addAtIndex(0,0);
//        System.out.println("get 0: " + myLinkedList.get(0));

        myLinkedList.printLinkedList(myLinkedList);
    }
}

class MyLinkedList {

    final int nullValue = -1;
    int val = nullValue;
    MyLinkedList next;

    public MyLinkedList() {

    }

    public MyLinkedList(int val, MyLinkedList next) {
        this.val = val;
        this.next = next;
    }

    public int get(int index) {
        if(this.val == nullValue || index < 0) {
            return -1;
        }

        if(index == 0) {
            return this.val;
        } else {
            int position = 0;
            MyLinkedList pNode = this;
            while(position < index) {
                if(pNode.next == null) {
                    break;
                } else {
                    pNode = pNode.next;
                    position++;
                }
            }
            return (position == index) ?  pNode.val : nullValue;
        }
    }

    public void addAtHead(int val) {
        if(this.val == nullValue) {
            this.next = null;
        } else {
            this.next = new MyLinkedList(this.val,this.next);
        }
        this.val = val;
    }

    public void addAtTail(int val) {
        if(this.val == -1) {
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
        if(index <= 0) {
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
        if(this.val == nullValue || index < 0) {
            return;
        }
        if(index == 0) {
            if(this.next == null) {
                this.val = nullValue;
                this.next = null;
            } else {
                this.val = this.next.val;
                this.next = this.next.next;
            }
        } else {
            int position = 0;
            MyLinkedList pNode = this;
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

    public void printLinkedList(MyLinkedList linkedList) {
        String linkedStr = "";
        MyLinkedList pNode = new MyLinkedList(0,linkedList);
        while (pNode.next != null) {
            pNode = pNode.next;
            linkedStr = linkedStr + (pNode.val + "->");
        }
        System.out.println(linkedStr);
    }
}


/**
 *     public void addAtHead(int val) {
 *         if(this.val == nullValue) {
 *             this.next = null;
 *         } else {
 *             this.next = new MyLinkedList(this.val,this.next);
 *         }
 *         this.val = val;
 *     }
 *     可以优化成：this.next = new MyLinkedList (this.val,this.next); this.val = val;
 */
