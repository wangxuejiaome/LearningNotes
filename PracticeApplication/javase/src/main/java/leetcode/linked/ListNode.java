package leetcode.linked;

/**
 * Author: wangxuejiao
 * Date: 2022/2/10 21:51
 * Description:
 **/
public class ListNode {
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

    public static void printLinkedList(ListNode linkedList) {
        String linkedStr = "";
        ListNode pNode = new ListNode(0,linkedList);
        while (pNode.next != null) {
            pNode = pNode.next;
            linkedStr = linkedStr + (pNode.val + "->");
        }
        System.out.println(linkedStr);
    }
}
