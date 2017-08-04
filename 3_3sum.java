/*
Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
Note: The solution set must not contain duplicate triplets.

For example, given array S = [-1, 0, 1, 2, -1, -4],
A solution set is: [[-1, 0, 1], [-1, -1, 2]]
*/
/*
Note:
1. There are dupplicates in given array. So we must sort the given array frist to avoid duplicates.

1. Examples:
input			result
null			null
S.length < 3	[]
[-1, 2, -1]		[-1, 2, -1]	

2. Solutions
2.1 Brute force
find all the triplets starts with each element
3sum(S):
	Iter int1 in S:
		Iter int2 in the following integers after int:
			Inter int3 in the following intergers after int2:
				if int1 + int2 + int3 = 0:
					results.add(int1, int2, int3); 

[-1, 0, 1, 2, -1, -4]
[ 0, 1, 2, 3,  4,  5]
time O(N^3)

2.2 Optimize
decompose this problem into: for each element a, find the following two elements that can sum to -a.
3sum(S):
	iter int1 in S:
		iter int2 in the following part of S:
			using hashtable to help find the int3 such that int2 + int3 = -int1

optimize2:
Since we need to sort the given array to avoid duplicates, we can use two pointers to do two sum instead of using additional data strcutre.

3. Implementation and Test
See the following unannotated code.
*/
public class Solution{
	public ArrayList<ArrayList<Integer>> threeSum(int[] nums) {
		ArrayList<ArrayList<Integer>> allTriplets = new ArrayList<ArrayList<Integer>>();
		if (nums == null || nums.length < 3) return allTriplets;

		/*Sort array to avoid duplicates*/
		Arrays.sort(nums);
		for (int i = 0; i < nums.length - 2; i++) {
			if (i > 0 && nums[i - 1] == nums[i]) {
				continue;
			}
			
			twoSum(allTriplets, nums, -nums[i], i + 1, nums.length - 1);
		}

		return allTriplets;
	}	

	public void twoSum(ArrayList<ArrayList<Integer>> results, int[] nums, int target, int start, int end) {		
		/*array is sorted, so we can use two pointers to iter from each side instead of using hashmap*/
		while (start < end) {
			if (nums[start] + nums[end] == target) {
				ArrayList<Integer> triplet = new ArrayList<>();
				triplet.add(-target);
				triplet.add(nums[start]);
				triplet.add(nums[end]);
				results.add(triplet);

				start++;
				end--;

				/*Avoid duplicates*/
				while (start < end && nums[start - 1] == nums[start]) {
					start++;
				}
				while (start < end && nums[end + 1] == nums[end]) {
					end--;
				}

			}else if (nums[start] + nums[end] < target) {
				start++;
				
			}else {
				end--;
				
			}
			

		}

	}

	@Test
	public void testThreeSum() {
		ArrayList<ArrayList<Integer>> results = new ArrayList<ArrayList<Integer>>();
		assertEquals(results, threeSum(null)); //null input
		assertEquals(results, threeSum(new int[]{})); //empty input
		assertEquals(results, threeSum(new int[]{1,2})); //contains less than 3 elements
		assertEquals(results, threeSum(new int[]{1,2,3}));//no triplet can sum to 0
		
		ArrayList<Integer> triplet1 = new ArrayList<>();
		ArrayList<Integer> triplet2 = new ArrayList<>();
		triplet1.add(-1);
		triplet1.add(-1);
		triplet1.add(2);
		triplet2.add(-1);
		triplet2.add(0);
		triplet2.add(1);
		results.add(triplet1);
		results.add(triplet2);
		assertEquals(results,threeSum(new int[]{-1, 0, 1, 2, -1, -4}));//normal cases
	}

}


















