import java.io.*;

class MapIO{
	StageViewer sv;
	public MapIO(StageViewer sv){
		this.sv = sv;
	}
	
	public boolean input(int fileNo){
		BufferedReader br = null;
		String name;
		int gravity;
		int[][] data;
		int[][] notPass;
		int row;
		int col;
		try{
			String tmp;
			String[] s;
			br = new BufferedReader(new FileReader(new File("./Map"+fileNo+".dat")));
			name = br.readLine();
			br.readLine();
			gravity = Integer.parseInt(br.readLine());
			br.readLine();
			tmp = br.readLine();
			s = tmp.split(" ");
			col = Integer.parseInt(s[0]);
			row = Integer.parseInt(s[1]);
			data = new int[row][col];
			notPass = new int[row][col];
			br.readLine();
			for(int i = 0; i < row; i++){
				tmp = br.readLine();
				s = tmp.split(" ");
				for(int j = 0; j < col; j++){
					data[i][j] = Integer.parseInt(s[j]);
				}
			}
			br.readLine();
			for(int i = 0; i < row; i++){
				tmp = br.readLine();
				s = tmp.split(" ");
				for(int j = 0; j < col; j++){
					notPass[i][j] = Integer.parseInt(s[j]);
				}
			}
			br.close();
		}catch(IOException e){
			return false;
		}catch(NumberFormatException e){
			try{
				br.close();
			}catch(IOException ioe){}
			return false;
		}
		sv.backGroundName = name;
		sv.gravity = gravity;
		sv.row = row;
		sv.col = col;
		sv.data = new int[row][col];
		sv.notPass = new boolean[row][col];
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				sv.data[i][j] = data[i][j];
				sv.notPass[i][j] = (notPass[i][j] == 1);
			}
		}
		sv.readEnd();
		return true;
	}
	
	public boolean output(int fileNo){
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("./Map"+fileNo+".dat"))));
			pw.println(sv.backGroundName);
			pw.println();
			pw.println(sv.gravity);
			pw.println();
			pw.println(sv.col + " " + sv.row);
			pw.println();
			for(int i = 0; i < sv.row; i++){
				for(int j = 0; j < sv.col; j++){
					if(j>0) pw.print(" ");
					pw.print(sv.data[i][j]);
				}
				pw.println();
			}
			pw.println();
			for(int i = 0; i < sv.row; i++){
				for(int j = 0; j < sv.col; j++){
					if(j>0) pw.print(" ");
					if(sv.notPass[i][j]) pw.print("1");
					else pw.print("0");
				}
				pw.println();
			}
			pw.println();
		}catch(IOException e){
			if(pw != null) pw.close();
			return false;
		}
		pw.close();
		sv.updated = false;
		return true;
	}
}
