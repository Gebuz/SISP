public class Heuristic{
	public Heuristic(){

	}

	public double GetHeuristic(int[][] board, int player){
		int x = board.length;
		int y = board[0].length;
		double count = 0;
		for(int c = 0; c < x; c++){
			for(int r = 0; r < y; r++){
				if(board[c][r] == player){
                	//check vertical
					if(r+2<y){
						double tcount = 0;
						for(int a = 1; a<4; a++){
							if(player != board[c][r+a]){
								break;
							}
							else{
								tcount+=1;
							}
						}
						if(tcount > count){
							count = tcount;
						}
					}
	                //check horizontal
					if(c+2<x){
						double tcount = 0;
						for(int a = 1; a<4; a++){
							if(player != board[c+a][r]){
								break;
							}
							else{
								tcount+=1;
							}
						}
						if(tcount > count){
							count = tcount;
						}
					}
	                //check diagonalup
					if(r+2<y && c+2<x){
						double tcount = 0;
						for(int a = 1; 0<4; a++){
							if(player != board[c+a][r+a]){
								break;
							}
							else{
								tcount+=1;
							}
						}
						if(tcount > count){
							count = tcount;
						}
					}
	                //check diagonaldown
					if(r-2>0 && c+2<x){
						double tcount = 0;
						for(int a = 1; 0<4; a++){
							if(player != board[c+a][r-a]){
								break;
							}
							else{
								tcount+=1;
							}
						}
						if(tcount > count){
							count = tcount;
						}
					}
				}
				
			}
		}
		return (count+1)/4;
	}
}