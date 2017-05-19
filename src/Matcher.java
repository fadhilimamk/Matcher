import java.io.File;

public class Matcher {

  private static final File dir = new File("./res");

  public static void main(String args[]) {

    Image pattern = new Image("./res/pattern3.png");
    Image resource = new Image("./res/res3.png");

    int[][] pixels = resource.getPixels();

    int patternValue = pattern.getFullValue();

    int i = 0, j = 0;
    int maxi = resource.getHeight() - pattern.getHeight() ;
    int maxj = resource.getWidth() - pattern.getWidth();
    int cValue = resource.getValue(0, 0, pattern.getHeight()-1,pattern.getWidth()-1);
    boolean found = false, right = true, first = true;

    while (i <= maxi && !found) {
      if (right) j = 0; else j = maxj;
      while (j <= maxj && j >= 0 && !found) {
        System.out.print(i+ " " + j + " " + cValue);
        cValue = resource.getValue(i, j, i+pattern.getHeight()-1,
                j+pattern.getWidth()-1);
//        if (first) {
//          first = false;
//          cValue = resource.getValueRight(cValue, i, j, i+pattern.getHeight()-1, j+pattern.getWidth()-1);
//        if ((j == 0 || j == maxj))
//          if (first) {
//            first = false;
//            cValue = resource.getValueRight(cValue, i, j, i+pattern.getHeight()-1, j+pattern.getWidth()-1);
//          } else
//          cValue = resource.getValueBottom(cValue, i, j, i+pattern.getHeight()-1, j+pattern.getWidth()-1);
//        else if (right)
//          cValue = resource.getValueRight(cValue, i, j, i+pattern.getHeight()-1, j+pattern.getWidth()-1);
//        else
//          cValue = resource.getValueLeft(cValue, i, j, i+pattern.getHeight()-1,
//                  j+pattern.getWidth()-1);
//
        System.out.println(" " + cValue);

        if (cValue == patternValue) {
          System.out.println("Mungkin menemukan");
          found = resource.isSame(i,j,pattern.getPixels());
          System.out.println(found);
        }

        if (!found) if (right) j++; else j--;
      }
      if (!found) i++;
      right = !right;

    }

    if (found) {
      System.out.println("Menemukan pola pada :" + i + " " + j);

      for (int x = i; x < i + pattern.getHeight(); x++) {
        for (int y = j; y < j + pattern.getWidth(); y++)
          System.out.print(Integer.toHexString(pixels[x][y]) + " ");
        System.out.println();
      }

    } else {
      System.out.println("Tidak ditemukan!");
    }


    System.out.println(Integer.toHexString(patternValue));


  }

}
