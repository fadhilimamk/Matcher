import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class Image {
  private int[][] pixels;
  private File file;
  private BufferedImage image;
  private int width;
  private int height;

  public Image(String url) {
    file = new File(url);

    try {
      image = ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
    }

    width = image.getWidth();
    height = image.getHeight();

    setImagePixels();
  }


  private void setImagePixels() {
    int pixelLength = image.getAlphaRaster() == null ? 3 : 4;
    pixels = new int[height][width];
    byte[] data = ((DataBufferByte) image.getRaster().
            getDataBuffer()).
            getData();

    for (int pixel = 0, row = 0, col = 0; pixel < data.length; pixel += pixelLength) {
      int argb = 0;
      argb += (pixelLength == 3) ? -16777216 : (((int) data[pixel] & 0xff) << 24);  // alpha
      argb += ((int) data[pixel] & 0xff);                                           // blue
      argb += (((int) data[pixel + 1] & 0xff) << 8);                                // green
      argb += (((int) data[pixel + 2] & 0xff) << 16);                               // red
      pixels[row][col] = argb;
      col++;
      if (col == width) {
        col = 0;
        row++;
      }
    }

  }

  public int getValue(int i1, int j1, int i2, int j2) {
    int result = 0;

    for(int i = i1; i <= i2; i++)
      for(int j = j1; j <= j2; j++)
        result += pixels[i][j];

    return result % 1000;
  }

  // input merupakan indeks baru
  public int getValueRight(int oldValue,int i1, int j1, int i2, int j2) {
    int result = oldValue;
    for (int i = i1; i <= i2; i++)
      result -= pixels[i][j1];
    for (int i = i1; i <= i2; i++)
      result += pixels[i][j2];
    return result % 1000;
  }

  // input merupakan indeks baru
  public int getValueLeft(int oldValue,int i1, int j1, int i2, int j2) {
    int result = oldValue;
    for (int i = i1; i <= i2; i++)
      result -= pixels[i][j2];
    for (int i = i1; i <= i2; i++)
      result += pixels[i][j1];
    return result % 1000;
  }

  // input merupakan indeks baru
  public int getValueBottom(int oldValue,int i1, int j1, int i2, int j2) {
    int result = oldValue;
    for (int j = j1; j <= j2; j++)
      result -= pixels[i1][j];
    for (int j = j1; j <= j2; j++)
      result += pixels[i2][j];
    return result % 1000;
  }

  public int getFullValue() {
    return getValue(0,0,height-1,width-1);
  }

  public int[][] getPixels() {
    return pixels;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public boolean isSame(int i, int j, int[][] pattern) {
    boolean same = true;

    for (int x = 0; x < pattern.length; x++) {
      for (int y = 0; y < pattern[x].length; y++) {
        if (pixels[i+x][j+y] != pattern[x][y]) {
          same = false;
          break;
        }
      }
      if (!same) break;
    }

    return same;
  }
}
