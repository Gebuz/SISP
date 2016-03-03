public final class Heuristic{
	private Heuristic() {

	}

	private static int opponent(int player) {
		if(player == 1)
			return 2;
		else return 1;
	}

	public static double GetHeuristic(int[][] board, int player) {
		int x = board.length;
		int y = board[0].length;
		double count = 0;
		for(int c = 0; c < x; c++) {
			for(int r = 0; r < y; r++) {
				int startp = board[c][r];
                	//check vertical
				if(r+3<y) {
					double tcount = 0;
					for(int a = 0; a<4; a++) {
						int currentp = board[c][r+a];
						if(startp == player && currentp == player) {
							tcount+=1;
						} else if(startp == player && currentp == 0) {
							tcount+=0.1;
						} else if(startp == opponent(player) && currentp == 0) {
							tcount+=0.1;
						} else if(startp == opponent(player) && currentp == opponent(player)) {
							tcount-=1;
						} else if(startp == 0 && currentp == player) {
							tcount+=1;
							startp = currentp;
						} else if(startp == 0 && currentp == opponent(player)) {
							tcount = -tcount;
							tcount-=1;
							startp = currentp;
						} else if(startp == 0 && currentp == 0) {
							tcount+=0.1;
						} else{
							tcount = 0;
							break;
						}
					}
					count += tcount;
				}
	                //check horizontal
				if(c+3<x) {
					double tcount = 0;
					for(int a = 0; a<4; a++) {
						int currentp = board[c+a][r];
						if(currentp == player && startp == player) {
							tcount+=1;
						} else if(currentp == startp) {

						} else if(currentp == opponent(player) && startp == opponent(player)) {
							tcount-=1;
						}
						else{
							tcount = 0;
							break;
						}
					}
					count += tcount;
				}
	                //check diagonalup
				if(r+3<y && c+3<x) {
					double tcount = 0;
					for(int a = 0; a<4; a++) {
						int currentp = board[c+a][r+a];
						if(currentp == player && startp == player) {
							tcount+=1;
						} else if(currentp == startp) {

						} else if(currentp == opponent(player) && startp == opponent(player)) {
							tcount-=1;
						}
						else{
							tcount = 0;
							break;
						}
					}
					count += tcount;
				}
	                //check diagonaldown
				if(r-3>0 && c+3<x) {
					double tcount = 0;
					for(int a = 0; a<4; a++) {
						int currentp = board[c+a][r-a];
						if(currentp == player && startp == player) {
							tcount+=1;
						} else if(currentp == startp) {

						} else if(currentp == opponent(player) && startp == opponent(player)) {
							tcount-=1;
						}
						else{
							tcount = 0;
							break;
						}
					}
					count += tcount;
				}
			}
		}
		return count;
	}
}
