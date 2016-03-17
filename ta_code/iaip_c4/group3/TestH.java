public class TestH{
	public static void main(String[] args) {
	int[][] board = new int[7][6];
	board[0][0] = 1;
	board[0][1] = 2;
	board[0][2] = 1;
	board[0][3] = 2;
	board[1][0] = 2;
	board[1][1] = 1;
	board[1][2] = 2;
	board[1][3] = 1;
	board[2][0] = 1;
	board[2][1] = 2;
	board[2][2] = 1;
	board[2][3] = 2;
	board[3][0] = 1;
	board[3][1] = 1;
	board[3][2] = 2;
	board[3][3] = 2;
	double d = Heuristic.GetHeuristic(board, 1);
	System.out.println(d);
	board = new int[7][6];
	board[6][0] = 1;
	board[6][1] = 2;
	board[6][2] = 1;
	board[6][3] = 2;
	board[5][0] = 2;
	board[5][1] = 1;
	board[5][2] = 2;
	board[5][3] = 1;
	board[4][0] = 1;
	board[4][1] = 2;
	board[4][2] = 1;
	board[4][3] = 2;
	board[3][0] = 1;
	board[3][1] = 1;
	board[3][2] = 2;
	board[3][3] = 2;
	d = Heuristic.GetHeuristic(board, 1);
	System.out.println(d);
	}
}