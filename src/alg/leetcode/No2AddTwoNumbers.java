package alg.leetcode;

public class No2AddTwoNumbers {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode node = null;
        ListNode head = null;
        ListNode l1Node = l1, l2Node = l2;
        while (true) {
            int val = 0;
            int nullCount = 0;
            if (l1Node != null) {
                val += l1Node.val;
                l1Node = l1Node.next;
            } else {
                nullCount++;
            }

            if (l2Node != null) {
                val += l2Node.val;
                l2Node = l2Node.next;
            } else {
                nullCount++;
            }

            if (nullCount == 2) {
                break;
            }

            if (head == null) {
                if (val >= 10) {
                    head = new ListNode(val % 10);
                    node = head;
                    head.next = new ListNode(1);
                } else {
                    head = new ListNode(val);
                    node = head;
                }
            } else {
                if (node.next != null) {
                    val++;
                }
                if (val >= 10) {
                    node.next = new ListNode(val % 10);
                    node = node.next;
                    node.next = new ListNode(1);
                } else {
                    node.next = new ListNode(val);
                    node = node.next;
                }
            }
        }
        return head;
    }

    static ListNode createList(int[] data) {
        ListNode head = null;
        ListNode node = null;
        for (int i : data) {
            if (head == null) {
                head = new ListNode(i);
                node = head;
            } else {
                node.next = new ListNode(i);
                node = node.next;
            }
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode l1 = createList(new int[]{2, 4, 3});
        ListNode l2 = createList(new int[]{5, 6, 4});

        ListNode node = addTwoNumbers(l1, l2);
        ListNode n = node;
    }


}
