/*
MICP Summer 2017
Author: Cindy Chen
Language: Java
*/

/*
Implement a function to check if a linked list is a palindrome
*/
/*
TEBOW IT

1. Talk
Will the input be null or only one node? Yes.
Singlye linked list or doubly linked list? Singly.

2. Examples
Input				Output
null				false
sinlge node			true
1->2->3				false
1->2->1				true

3. Brute Force
	1. Iterate throught given linked list, Use and additional array to store all nodes' value;
	2. Use two pointers q and p to iterate array from both sides, compare a[p] and a[q], if they are always the same, then given linked list is a palindrome.
Time complexity: O(N)
Space complexity: O(N)

4. Optimization
	1. Use two pointers to find the middle of given linked list;
	2. We reverse the first half part of given linked list;
	2. Start from both head and middle to iterate given linked list, compare the value of head and middle, if they are always the same, then given linked list is a palindrome.
	3. Attached is an image to illustrate this idea.

5. Walk Through
During the walk throught process, I found out that odd/even number of nodes have different middle nodes, we should take care of it.

6. Implementation and Test
See the following unannotated code;
*/
class Solution {
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }
        /*Get the middle node of given linked list*/
        ListNode middle = getMiddle(head);
        /*Reverse the first half of linked list, and get new head*/
        head = reverseFirstHalf(head, middle);
        /*Iter from head and middle, keep comparing their value*/
        return isPalindrome(head, middle);
    }
    private ListNode getMiddle(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        /*Handle odd number of nodes*/
        if (fast != null) {
            slow = slow.next;
        }
        return slow;
    }
    private ListNode reverseFirstHalf(ListNode head, ListNode middle) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode curt = head;
        ListNode pre = dummy;
        while (middle != null) {
            middle = middle.next;
            ListNode next = curt.next;
            curt.next = pre;
            pre = curt;
            curt = next;
        }
        head.next = null;
        return pre;
    }
    private boolean isPalindrome(ListNode head, ListNode middle) {
        if (head == null || middle == null) {
            return true;
        }
        while (middle != null) {
            if (head.val != middle.val) {
                return false;
            }
            head = head.next;
            middle = middle.next;
        }
        return true;
    }
}






