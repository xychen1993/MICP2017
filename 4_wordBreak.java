/*
Word Break
Given an input string and a dictionary of words, find out if the input string can be segmented into a space-separated sequence of dictionary words.
For example, consider the following dictionary: { pear, salmon, foot, prints, footprints, leave, you, sun, girl, enjoy },
Examples:
Given the string “youenjoy”, 
Output: True (The string can be segmented as “you enjoy”)

Input: “youleavefootprints”,
Output: True (The string can be segmented as “you leave footprints” or “you leave foot prints”)

Input:salmonenjoyapples
Output: False
*/
/*
TEBOW IT

1. Talk:
Is is case sensitive? (Suppose that inputs and dic words are all in lowercase)
Can we use duplicates? (Yeah, so "youyouenjoy" can be segmented as "you you enjoy")
Mutiple ways to do segment? (Yeah, “youleavefootprints” ->  “you leave footprints” or “you leave foot prints”)

2. Examples
Input 					dictionary words					output
null					no matter what						false
no matter what			null								false
""						not null							true
youyouenjoy				{ you, sun, enjoy }					true

3. Brute Force
Iterate through input string, for each substring, check if it is contained in dic and see if we can segment the whole string.
Time complexity: O(N^2), cause there are totally O(N^2) substrings

4. Optimize
1. DP
if a substring can be segmented into dic words, it must be like: dicwords + a new dicword
So we can iterate trough this string from 0 to n, at each postion i check if str[0, i - len(any dicwords)] is true, if it is true, it means the current substring can be represneted as dicwords + a new dicword, so position i is true.
Time complexity: O(N * L) = O(N) (L is the len of dic)

2. DP II
Iterate through input string, for each substring, check if it is contained in dic and use a boolean[] to stroe the results.
Time complexity: O(N^2)


5. Walk Through
booleanp[] resutls = all false;
set reuslt[0] = true;
Iter i from 0 to len(str):
	Iter j from i to len(str):
		if dic.contains(substring[i, j]):
			reuslt[j] = result[i];
return result[len(str)];

6. Implementation
Following unannotated code;

7. Test
Input 					dictionary words					Output 				dp[]
null					/									false
/						null								false
""						{a, b}								true 				[t]
"ab"					{a, b}								true 				[t,t,t]
"aa"					{a, b}								true 				[t,t,t]
*/	
public class Solution {
	public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null || wordDict == null) {
			return false;
		}
		boolean[] dp = new boolean[s.length() + 1];
		dp[0] = true;
        /*尾循环在外面是因为其实是在检测每一位当做尾巴的时候是不是true*/
        for (int end = 1; end <= s.length(); end++) {
            for (int start = 0; start < end; start++) {
                String substr = s.substring(start, end);
                if (dp[start] && wordDict.contains(substr)) {
                    dp[end] = true; 
                    break;
                }
            }
        }
		
		return dp[s.length()];
    }
	@Test
		public static void testWordBreak() {
			assertEquals(false,wordBreak(null, new HashSet<String>(Arrays.asList("pear", "salmon", "foot", "prints", "footprints", "leave", "you", "sun", "girl", "enjoy" ))));
			assertEquals(false,wordBreak("youleavefootprints", null));
			assertEquals(true,wordBreak("", new HashSet<String>(Arrays.asList("pear", "salmon", "foot", "prints", "footprints", "leave", "you", "sun", "girl", "enjoy" ))));
			assertEquals(true,wordBreak("pearpear", new HashSet<String>(Arrays.asList("pear", "salmon", "foot", "prints", "footprints", "leave", "you", "sun", "girl", "enjoy" ))));
			assertEquals(true,wordBreak("youleavefootprints", new HashSet<String>(Arrays.asList("pear", "salmon", "foot", "prints", "footprints", "leave", "you", "sun", "girl", "enjoy" ))));
			assertEquals(true,wordBreak("youenjoy", new HashSet<String>(Arrays.asList("pear", "salmon", "foot", "prints", "footprints", "leave", "you", "sun", "girl", "enjoy" ))));

		}
}
















