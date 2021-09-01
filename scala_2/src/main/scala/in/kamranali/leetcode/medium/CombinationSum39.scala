package in.kamranali.leetcode.medium

import scala.collection.immutable.{List, Queue}

/*
    public List<List<Integer>> combinationSum(int[] nums, int target) {
      List<List<Integer>> list = new ArrayList<>();
      Arrays.sort(nums);
      backtrack(list, new ArrayList<>(), nums, target, 0);
      return list;
    }

    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, int remain, int start){
        if(remain < 0) return;
        else if(remain == 0) list.add(new ArrayList<>(tempList));
        else{
            for(int i = start; i < nums.length; i++){
                tempList.add(nums[i]);
                backtrack(list, tempList, nums, remain - nums[i], i); // not i + 1 because we can reuse same elements
                tempList.remove(tempList.size() - 1);
            }
        }
    }
 */
object CombinationSum39 {
  def combinationSum1(candidates: Array[Int], target: Int): List[List[Int]] = {

    val len = candidates.length
    var res: List[List[Int]] = List()

    def makeChoices(choice: Int, sum: Int, list: Queue[Int]): Unit = {

      if (choice == len) ()
      else {
        val elem = candidates(choice)
        // println(s"[makeChoice] choice: $choice, elem: $elem, sum: $sum, list: $list")
        solve(choice, sum + elem, list :+ elem)

        // println(s"[makeChoice] choice: $choice, sum: $sum, list: $list")
        makeChoices(choice + 1, sum, list)
      }
    }

    // Start with a seed i.e. first element
    def solve(seed: Int, sum: Int, list: Queue[Int]): Unit = {
      if (sum > target) {
        // println(s"[Solve] seed: $seed, sum: $sum, list: $list => sum > target")
        ()
      }
      else if ( sum == target) {
        res = list.toList :: res
        // println(s"[Solve] seed: $seed, sum: $sum, list: $list  res: $res => sum == target")
      }
      else {
        // println(s"[Solve] seed: $seed, sum: $sum, list: $list")
        makeChoices(seed, sum, list)
      }
    }

    solve(0, 0, Queue())
    res
  }

  // https://www.youtube.com/watch?v=OyZFFqQtu98
  def combinationSum(candidates: Array[Int], target: Int): List[List[Int]] = {
    val len = candidates.length

    def util(idx: Int, sum: Int, temp: Queue[Int]): List[List[Int]] = {
      if (idx == len) List()
      else if (sum > target) List()
      else if (sum == target) List(temp.toList)
      else {

        val elem = candidates(idx)

        val chooseElem = util(idx, sum + elem, temp :+ elem)
        val notChooseElem = util(idx + 1, sum, temp)

        chooseElem ++ notChooseElem
      }
    }

    util(0, 0, Queue())
  }

  def main(args: Array[String]): Unit = {
    println(combinationSum1(Array(2, 3), 5))
  }
}