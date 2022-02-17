package leetcode.linked;

/**
 * author : wangxuejiao@leoao.com
 * date   : 2022/2/5
 * desc   :
 */


public class No206 {

    public static void main(String[] args) {
        ListNode listNode3 = new ListNode(3,null);
        ListNode listNode2 = new ListNode(2,null);
        ListNode listNode1 = new ListNode(1,null);

        listNode1.next = listNode2;
        listNode2.next = listNode3;


        reverseList(listNode1);

    }

    public static ListNode reverseList(ListNode head) {
        ListNode currentNode = head;
        ListNode preNode = null;
        ListNode tempNode = null;
        while (currentNode != null) {
            tempNode = currentNode.next;
            currentNode.next = preNode;
            preNode = currentNode;
            currentNode = tempNode;
        }
        return  preNode;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

}
