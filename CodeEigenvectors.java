/*
I made this project for Math475: combinatorics for my reading report
*/


public class CodeEigenvectors {
  static int eigen1;
  static int eigen2;


  public static void main(String[] args) {
    int[][] array = {{6, -1}, {2, 3}}; // can change these values

    int firstElement = array[0][0] + array[1][1];
    System.out.println(firstElement);
    int determinant = Math.abs(array[0][0] * array[1][1]) - (array[0][1] * array[1][0]);
    System.out.println(determinant);
    // solve for lambda
    double lambda = Math.pow(firstElement, 2) - 4 * determinant;
    lambda = Math.sqrt(lambda);

    CodeEigenvectors.eigen1 = (int) (firstElement + lambda) / 2;
    CodeEigenvectors.eigen2 = (int) (firstElement - lambda) / 2;


    System.out.println(CodeEigenvectors.eigen1);
    System.out.println(CodeEigenvectors.eigen2);
  } // end of main

}
