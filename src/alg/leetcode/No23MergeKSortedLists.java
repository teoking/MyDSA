package alg.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class No23MergeKSortedLists {

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    static class Solution {

        // Recursive merge
        // time: O(n^2)
        // space:
        // runtime: 289 ms
        public ListNode mergeKLists(ListNode[] lists) {
            ListNode head = new ListNode(0);
            mergeKListsInner(head, lists);
            return head.next;
        }

        private void mergeKListsInner(ListNode mergedNode, ListNode[] lists) {
            ListNode minNode = null;
            ListNode currentNode = null;
            int minNodeIndex = -1;

            // find minimum node in the lists
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] == null) {
                    continue;
                }
                currentNode = lists[i];
                if (minNode == null) {
                    minNode = currentNode;
                    minNodeIndex = i;
                }
                if (minNode.val > currentNode.val) {
                    minNode = currentNode;
                    minNodeIndex = i;
                }
            }

            // recursive base
            if (currentNode == null) {
                return;
            }
            lists[minNodeIndex] = minNode.next;
            mergedNode.next = new ListNode(minNode.val);
            mergedNode = mergedNode.next;

            mergeKListsInner(mergedNode, lists);
        }

    }

    static class Solution2 {

        // time: log(n) -- depends on the time complexity of PriorityQueue.add() (see the document).
        // space: k
        // runtime: 13 ms
        public ListNode mergeKLists(ListNode[] lists) {
            ListNode head = null;
            ListNode current = null;
            PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.val));

            for (ListNode node : lists) {
                if (node != null) {
                    pq.add(node);
                }
            }

            while (!pq.isEmpty()) {
                ListNode node = pq.poll();
                if (head == null) {
                    head = new ListNode(node.val);
                    current = head;
                } else {
                    current.next = new ListNode(node.val);
                    current = current.next;
                }
                if (node.next != null) {
                    pq.add(node.next);
                }
            }
            return head;
        }

    }

    public static void main(String[] args) {
        ListNode[] lists = {
                createList(1, 4, 5),
                createList(1, 3, 4),
                createList(2, 6)
        };

        ListNode[] lists1 = {
                createList(1),
                createList(0),
        };

        Solution2 s = new Solution2();
        ListNode node = s.mergeKLists(lists1);
        while (node != null) {
            System.out.print(node.val + "->");
            node = node.next;
        }
    }

    static ListNode createList(int... val) {
        ListNode head = new ListNode(val[0]);
        ListNode node = head;
        for (int i = 1; i < val.length; i++) {
            node.next = new ListNode(val[i]);
            node = node.next;
        }
        return head;
    }
}
