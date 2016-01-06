package homework.models;

import homework.Main;
import homework.Utils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;

/**
 * Represent each of the Ships.
 */
public class Ship {

  private ImageIcon image;
  private String code;
  private String denomination;
  private String description;
  private int numInteriorDouble;
  private int numExteriorDouble;
  private int numInteriorFamily;
  private int numExteriorFamily;
  private int priceInteriorDouble;
  private int priceExteriorDouble;
  private int priceInteriorFamily;
  private int priceExteriorFamily;

  public Ship(String code, String denomination, String description, int numInteriorDouble, int numExteriorDouble,
              int numInteriorFamily, int numExteriorFamily, int priceInteriorDouble, int priceExteriorDouble,
              int priceInteriorFamily, int priceExteriorFamily) {
    this.code = code;
    this.denomination = denomination;
    this.description = description;
    this.numInteriorDouble = numInteriorDouble;
    this.numExteriorDouble = numExteriorDouble;
    this.numInteriorFamily = numInteriorFamily;
    this.numExteriorFamily = numExteriorFamily;
    this.priceInteriorDouble = priceInteriorDouble;
    this.priceExteriorDouble = priceExteriorDouble;
    this.priceInteriorFamily = priceInteriorFamily;
    this.priceExteriorFamily = priceExteriorFamily;

    try {
      image = Utils.proportionalScaleWidth(new ImageIcon(ImageIO.read(new File("images/" + code + ".jpg"))), 300);
    } catch (IOException e) {
      Main.log.e(e);
    }
  }

  @Override
  public int hashCode() {
    return code.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Ship && code.equals(((Ship) obj).code);
  }

  public int getNumInteriorDouble() {
    return numInteriorDouble;
  }

  public int getNumExteriorDouble() {
    return numExteriorDouble;
  }

  public int getNumInteriorFamily() {
    return numInteriorFamily;
  }

  public int getNumExteriorFamily() {
    return numExteriorFamily;
  }

  public int getPriceInteriorDouble() {
    return priceInteriorDouble;
  }

  public int getPriceExteriorDouble() {
    return priceExteriorDouble;
  }

  public int getPriceInteriorFamily() {
    return priceInteriorFamily;
  }

  public int getPriceExteriorFamily() {
    return priceExteriorFamily;
  }

  public String getCode() {
    return code;
  }

  public ImageIcon getImage() {
    return image;
  }
}
