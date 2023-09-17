import java.util.Scanner;

public class Exer1b {

	public static void main(String[] args) {
		
		UserInput ui = new UserInput();
		int rows = ui.inputRows();
		int columns = ui.inputColumns();
		
		String[][] arr = new String[rows][columns];
		
		int length = 3;
		StringStorage ss = new StringStorage();
		arr = ss.storeString(rows, columns, length);
		
		ArrayPrinter ap;
		ap = new ArrayPrinter(arr, rows, columns);
		
		String option;

		while(true) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("\nEnter the small letter representing the action you like:"
					+ "\nSearch(s), Edit(d), Print(p), Reset(r), Exit(x)"
					+ "\nYour choice: ");
			option = scanner.nextLine();
		
			switch (option) {
			
			case "s":
				Searcher s = new Searcher(arr, rows, columns);
				break;
				
			case "d":
				int row_index = ui.inputRowIndex(rows);
				int column_index = ui.inputColumnIndex(columns);
				
				Editor e = new Editor();
				arr = e.editString(arr, row_index, column_index);
				ap = new ArrayPrinter(arr, rows, columns);
				break;
				
			case "p":
				ap = new ArrayPrinter(arr, rows, columns);
				break;
				
			case "r":
				System.out.println("\nHere you may specify the new dimensions for the table and generate new contents.");
				rows = ui.inputRows();
				columns = ui.inputColumns();
				
				String[][] temp = new String[rows][columns];
				arr = temp;
				
				length = ui.inputLength();
				
				arr = ss.storeString(rows, columns, length);
				ap = new ArrayPrinter(arr, rows, columns);
				break;
				
			case "x":
				System.out.println("Exiting the program...");
				scanner.close();
				System.exit(0);
				break;
				
			default:
				System.out.println("This is not a valid menu option. Please choose again.");
				break;
			}
		}
	}	
}

class UserInput {
	Scanner scanner = new Scanner(System.in);
	public int inputRows() {
		int rows = 0;
		System.out.print("Input number of rows greater than 0: ");
		
		do {
			if (!scanner.hasNextInt()) {
                System.out.print("You have entered an invalid string, input a number greater than 0 for the rows: ");
            } else {
                rows = scanner.nextInt();
                if (rows <= 0) {
                    System.out.print("Please enter a number greater than 0 only: ");
                } 
            }
			scanner.nextLine();
		} while (rows <= 0);
		return rows;
	}
	
	public int inputColumns() {
		int columns = 0;
		System.out.print("Input number of columns greater than 0: ");
		
		do {
			if (!scanner.hasNextInt()) {
                System.out.print("You have entered an invalid string, input a number greater than 0 for the columns: ");
            } else {
                columns = scanner.nextInt();
                if (columns <= 0) {
                    System.out.print("Please enter a number greater than 0 only: ");
                }
            }
            scanner.nextLine();
		} while (columns <= 0);
		return columns;
	}
	
	public int inputRowIndex(int rows) {
		int row_index = -1;
		System.out.print("\nSpecify the row index of the cell to edit (0-"+(rows-1)+"): ");
		do {
			if (!scanner.hasNextInt()) {
                System.out.print("You have entered an invalid string, input a number from 0 to "+(rows-1)+" only: ");
            }
			else {
				row_index = scanner.nextInt();
                if (row_index < 0 || row_index > (rows-1)) {
                    System.out.print("Please enter a number from 0 to "+(rows-1)+" only: ");
                }
            }
            scanner.nextLine();
		} while (row_index < 0 || row_index > (rows-1));
		return row_index;
	}
	
	public int inputColumnIndex(int columns) {
		int column_index = -1;
		System.out.print("Specify the column index of the cell to edit (0-"+(columns-1)+"): ");
		do {
			if (!scanner.hasNextInt()) {
                System.out.print("You have entered an invalid string, input a number from 0 to "+(columns-1)+" only: ");
            }
			else {
				column_index = scanner.nextInt();
                if (column_index < 0 || column_index > (columns-1)) {
                    System.out.print("Please enter a number from 0 to "+(columns-1)+" only: ");
                }
            }
            scanner.nextLine();
		} while (column_index < 0 || column_index > (columns-1));
		return column_index;
	}
	
	public int inputLength() {
		int length = 0;
		System.out.print("Enter desired length of each string in the cells (1-10): ");
		do {
			if (!scanner.hasNextInt()) {
                System.out.print("You have entered an invalid string, input a number between 1 to 10: ");
            }
			else {
                length = scanner.nextInt();
                if ((length <= 0) || (length > 10)) {
                    System.out.print("Please enter a number between 1 to 10 only: ");
                }
            }
            scanner.nextLine();
		} while ((length <= 0) || (length > 10));
		return length;
	}
}

class StringStorage {
	public String[][] storeString(int rows, int columns, int length) {
		String[][] arr = new String[rows][columns];
		StringGenerator sg = new StringGenerator();
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				arr[i][j] = sg.getString(length);
			}
		}
		return arr;
	}
}

class StringGenerator {
	public String getString(int n) {
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int asciiCode;
			do {
				asciiCode = (int) (127*Math.random());
			} while (asciiCode < 32);
			sb.append((char) asciiCode);
		}
		return sb.toString();
	}	
}

class Searcher {
	Scanner scanner = new Scanner(System.in);
	
	public Searcher(String[][] arr, int rows, int columns) {
		System.out.print("\nEnter the string to be searched: ");
		String find = scanner.nextLine();
		System.out.println("Search String: "+find);
		boolean match = false;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				String source = arr[i][j];
				int index = 0;
				int occur = 0;
				while ((index = source.indexOf(find, index)) != -1) {
					index++;
					occur++;
				}
				if (occur == 1 ) {
					System.out.println("Found "+find+" on ("+i+", "+j+") with "+occur+" instance.");
					match = true;
				}
				else if (occur > 1) {
					System.out.println("Found "+find+" on ("+i+", "+j+") with "+occur+" instances.");
					match = true;
				}
			}	
		}
		if (match == false) {
			System.out.println("No match found!");
		}
	}
}

class Editor {
	Scanner scanner = new Scanner(System.in);
	
	public String[][] editString(String[][] arr, int row_index, int column_index) {
		System.out.println("\nCell value at cell ["+row_index+"]["+column_index+"]: "+arr[row_index][column_index]);
		System.out.print("Enter the desired new cell value: ");
		arr[row_index][column_index] = scanner.nextLine();
		return arr;
	}
}

class ArrayPrinter {
	public ArrayPrinter(String[][] arr, int rows, int columns) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.print(arr[i][j]+"\t");
			}
			System.out.println();
		}
	}
}
