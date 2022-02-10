package leetcode.linked;

/**
 * Author: wangxuejiao
 * Date: 2022/2/10 21:53
 * Description:
 **/
public class No24 {

    public static void main(String[] args) {
        No24 no24 = new No24();
        ListNode listNode1 = new ListNode(1,null);
        ListNode listNode2 = new ListNode(2,null);
        ListNode listNode3 = new ListNode(3,null);
        ListNode listNode4 = new ListNode(4,null);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        ListNode.printLinkedList(no24.swapPairs(listNode1));
    }

    public ListNode swapPairs(ListNode head) {
        ListNode preNode = null;
        ListNode pair1Node = head;
        ListNode pair2Node = null;
        while(pair1Node != null && pair1Node.next != null) {
            pair2Node = pair1Node.next;
            ListNode nextNode = pair2Node.next;
            pair2Node.next = pair1Node;
            pair1Node.next = nextNode;
            if(preNode == null) {
                head = pair2Node;
            } else {
                preNode.next = pair2Node;
            }

            preNode = pair1Node;
            pair1Node = nextNode;
        }
        return head;
    }
}
