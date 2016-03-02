public class TestH{
	public static void main(String[] args) {
	int[][] board = new int[7][6];
	board[3][0] = 1;
	board[3][1] = 1;
	board[3][2] = 1;
	double d = Heuristic.GetHeuristic(board, 1);
	System.out.println(d);
	}
}