package analyzer;

import java.util.ArrayList;
import java.util.List;

public class Id {
  private String name;
  private String type;
  private int offset;
  private int width;
  // if this is an array, store the number of its different dimensions
  public List<Integer> arr_list = new ArrayList<>();

  public Id(String name, String type, int offset, int width) {
    super();
    this.name = name;
    this.type = type;
    this.offset = offset;
    this.width = width;
  }

  public String getName() {
    return this.name;
  }

  public String getType() {
    return this.type;
  }

  public int getOffset() {
    return this.offset;
  }

  public int getWidth() {
    return this.width;
  }

}
