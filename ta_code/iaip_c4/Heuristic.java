public final class Heuristic{
	private Heuristic() {

	}

  private static final double EMPTY_SPACE_VALUE = 0.5;

	private static int opponent(int player) {
		if(player == 1)
			return 2;
		else return 1;
	}

	public static double GetHeuristic(int[][] board, int player) {
		int x = board.length;
		int y = board[0].length;
		double sum = 0;
		for(int c = 0; c < x; c++) {
			for(int r = 0; r < y; r++) {
				int startp = board[c][r];
        //check vertical
				if (r+3<y) {
					double count = 0;
					for(int a = 0; a<4; a++) {
						int currentp = board[c][r+a];
						if(startp == player && currentp == player) {
							count+=1;
						} else if(startp == player && currentp == 0) {
							count+=EMPTY_SPACE_VALUE;
						} else if(startp == opponent(player) && currentp == 0) {
							count-=EMPTY_SPACE_VALUE;
						} else if(startp == opponent(player) && currentp == opponent(player)) {
							count-=1;
						} else if(startp == 0 && currentp == player) {
							count+=1;
							startp = currentp;
						} else if(startp == 0 && currentp == opponent(player)) {
							count = -count;
							count-=1;
							startp = currentp;
						} else if(startp == 0 && currentp == 0) {
							count+=EMPTY_SPACE_VALUE;
						} else{
							count = 0;
							break;
						}
					}
          if (count >= 1 || count <= -1) {
            sum += count;
          }
				}
        //check horizontal
				if(c+3<x) {
					double count = 0;
					for(int a = 0; a<4; a++) {
						int currentp = board[c+a][r];
						if(startp == player && currentp == player) {
							count+=1;
						} else if(startp == player && currentp == 0) {
							count+=EMPTY_SPACE_VALUE;
						} else if(startp == opponent(player) && currentp == 0) {
							count-=EMPTY_SPACE_VALUE;
						} else if(startp == opponent(player) && currentp == opponent(player)) {
							count-=1;
						} else if(startp == 0 && currentp == player) {
							count+=1;
							startp = currentp;
						} else if(startp == 0 && currentp == opponent(player)) {
							count = -count;
							count-=1;
							startp = currentp;
						} else if(startp == 0 && currentp == 0) {
							count+=EMPTY_SPACE_VALUE;
						} else{
							count = 0;
							break;
						}
					}
          if (count >= 1 || count <= -1) {
            sum += count;
          }
				}
	                //check diagonalup
				if(r+3<y && c+3<x) {
					double count = 0;
					for(int a = 0; a<4; a++) {
						int currentp = board[c+a][r+a];
						if(startp == player && currentp == player) {
							count+=1;
						} else if(startp == player && currentp == 0) {
							count+=EMPTY_SPACE_VALUE;
						} else if(startp == opponent(player) && currentp == 0) {
							count-=EMPTY_SPACE_VALUE;
						} else if(startp == opponent(player) && currentp == opponent(player)) {
							count-=1;
						} else if(startp == 0 && currentp == player) {
							count+=1;
							startp = currentp;
						} else if(startp == 0 && currentp == opponent(player)) {
							count = -count;
							count-=1;
							startp = currentp;
						} else if(startp == 0 && currentp == 0) {
							count+=EMPTY_SPACE_VALUE;
						} else{
							count = 0;
							break;
						}
					}
          if (count >= 1 || count <= -1) {
            sum += count;
          }
				}
        //check diagonaldown
				if(r-3>=0 && c+3<x) {
					double count = 0;
					for(int a = 0; a<4; a++) {
						int currentp = board[c+a][r-a];
						if(startp == player && currentp == player) {
							count+=1;
						} else if(startp == player && currentp == 0) {
							count+=EMPTY_SPACE_VALUE;
						} else if(startp == opponent(player) && currentp == 0) {
							count-=EMPTY_SPACE_VALUE;
						} else if(startp == opponent(player) && currentp == opponent(player)) {
							count-=1;
						} else if(startp == 0 && currentp == player) {
							count+=1;
							startp = currentp;
						} else if(startp == 0 && currentp == opponent(player)) {
							count = -count;
							count-=1;
							startp = currentp;
						} else if(startp == 0 && currentp == 0) {
							count+=EMPTY_SPACE_VALUE;
						} else{
							count = 0;
							break;
						}
					}
          if (count >= 1 || count <= -1) {
            sum += count;
          }
				}
			}
		}
		return sum;
	}
}
