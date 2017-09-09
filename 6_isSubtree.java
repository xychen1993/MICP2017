/*
MICP Summer 2017
Author: Cindy Chen
Language: Java
*/
/*
Subtree of Another Tree
Given two non-empty binary trees s and t, check whether tree t has exactly the same structure and node values with a subtree of s. A subtree of s is a tree that consists of a node in s and all of that node's descendants.
*/
/*
1. Talk
Will the given two trees be null? Y
Is this a BST? N
Check if the values are the same or the nodes are the same? Values

2. Example
See in the following pics

3. Brute Force
	1. Serialize given two trees, get two strings s and t.
	2. Check if s contains t as a substring? if yes, return true; else return false;
Time Complexity: O(M + N + M*N) (iter M and N to get strings, check if contains substring takes M*N)
Space Complexity: O(max(M,N)) (length of strings)

4. Optimization
	Iter node in s:
		if substree starts with s == t:
			return true;
		else:
			go to s.left and s.right to find substrees
Time Complexity: O(MN) (need to check if same on each node in s)
Space Complexity: O(N) (recursion depth)
*/
class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null && t == null) {
            return true;
        }
        if (s == null || t == null) {
            return false;
        }
        if (isSame(s, t)) {
            return true;
        }
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }
    private boolean isSame(TreeNode s, TreeNode t) {
        if (s == null && t == null) {
            return true;
        }
        if (s == null || t == null) {
            return false;
        }
        if (s.val != t.val) {
            return false;
        }
        return isSame(s.left, t.left) && isSame(s.right, t.right);
    }
}