package analyzer;

import java.util.ArrayList;
import java.util.List;

public class Token implements Cloneable {

  // list of IDNs
  static List<String> id_list;

  String name;
  String value = null;
  String source; // format of the token when in the source code 

  public static List<String> getId_list() {
    return id_list;
  }

  public static void freeId_list() {
    id_list = new ArrayList<String>();
  }

  public String getName() {
    return this.name;
  }

  public String getValue() {
    return this.value;
  }

  public String getSource() {
    return this.source;
  }

  public Token() {
  }

  // TODO
  public Token(String name, String source) {
    this.name = name;
    this.source = source;

    if (source == null) {
      return;
    }

    if (name.equals("int") || name.equals("unsigned") || name.equals("long")
        || name.equals("long_unsigned")) {

      long k; // k represent the whether it's binary, octal, or hexical
      int begin; // 从source的begin号字符开始算数

      if (source.charAt(0) == '0') {
        if (source.length() == 1 || source.equals("0l") || source.equals("0L")
            || source.equals("0U") || source.equals("0u")
            || source.equals("0LU")
            || source.equals("0Lu") || source.equals("0lU")
            || source.equals("0lu")) {
          this.value = "0";
          return;
        }

        if (source.charAt(1) == 'x' || source.charAt(1) == 'X') {
          k = 16;
          begin = 2;
        } else if (source.charAt(1) >= '0' && source.charAt(1) <= '7') {
          k = 8;
          begin = 1;
        } else {
          throw new IllegalArgumentException(
              "second char of integer invalid - \'" + source + "\'");
        }

      } else if (source.charAt(0) >= '1' && source.charAt(0) <= '9') {

        k = 10;
        begin = 0;

      } else {
        throw new IllegalArgumentException(
            "the first char of integer must be a digit - \'" + source + "\'");
      }

      long ret = 0;
      for (int i = begin; i < source.length(); i++) {
        int temp = char_to_int(source.charAt(i));
        if (temp < 0) {
          break;
        }

        ret *= k;
        ret += temp;
      }

      this.value = "" + ret;
    } else if (name.equals("float") || name.equals("double")
        || name.equals("long_double")) {

      // System.out.println("source = "+source);

      int p1 = source.indexOf('.');
      int p2 = source.indexOf('e') > source.indexOf('E') ? source.indexOf('e')
          : source.indexOf('E');
      int p3 = p2;

      if (p2 < 0) {
        p2 = source.length();
      } else if (source.charAt(p2 + 1) == '-' || source.charAt(p2 + 1) == '+') {
        p3++;
      }

      long zhengbu = 0;// 正部

      // System.out.println("p1 = "+p1);

      for (int i = 0; i < p1; i++) {

        // System.out.println(i+"\t"+(long)char_to_int(source.charAt(i)));

        zhengbu *= 10;
        zhengbu += char_to_int(source.charAt(i));
      }

      double xiaoshubu = 0.0;// 小数部
      for (int i = p2 - 1; i > p1; i--) {
        xiaoshubu += char_to_int(source.charAt(i));
        xiaoshubu /= 10.0;
      }

      long zhishu = 0;// 指数
      for (int i = p3 + 1; i < source.length(); i++) {
        long temp = char_to_int(source.charAt(i));
        if (temp < 0) {
          break;
        }

        zhishu *= 10;
        zhishu += temp;
      }

      if (p3 >= 0 && source.charAt(p3) == '-') {
        zhishu = -zhishu;
      }

      if (p3 < 0) {
        zhishu = 0;
      }

      this.value = ((zhengbu + xiaoshubu) * Math.pow(10.0, zhishu)) + "";// value计算
    } else if (name.equals("char")) {
      // 如果name是字符
      if (source.charAt(1) == '\\') {

        int ret = 0;

        char temp = source.charAt(2);

        switch (temp) {
          case 'x':
            ret += char_to_int(source.charAt(3));
            if (source.charAt(4) != '\'') {
              ret *= 16;
              ret += char_to_int(source.charAt(4));
            }
            break;
          case 'n':
            ret = '\n';
            break;
          case 'r':
            ret = '\r';
            break;
          case 't':
            ret = '\t';
            break;
          case 'v':
            ret = 11;
            break;
          case 'b':
            ret = '\b';
            break;
          case 'f':
            ret = '\f';
            break;
          case 'a':
            ret = 7;
            break;
          default:
            if (source.charAt(2) >= '0' && source.charAt(2) <= '7') {
              ret += source.charAt(2) - '0';

              if (source.charAt(3) != '\'') {
                ret *= 8;
                ret += source.charAt(3) - '0';

                if (source.charAt(4) != '\'') {
                  ret *= 8;
                  ret += source.charAt(4) - '0';
                }
              }
            } else {
              ret = source.charAt(2);
            }
        }

        this.value = ret + "";

      } else {
        this.value = (int) source.charAt(1) + "";
      }
    } else if (name.equals("string")) {
      // 如果name是string
      this.value = source.substring(1, source.length() - 1);
    } else if (name.equals("id")) {
      int temp = search_id_list(source);

      if (temp >= 0) {
        this.value = temp + "";
      } else {
        id_list.add(source);
        this.value = (id_list.size() - 1) + "";
      }
    }
  }

  /**
   * 返回这个Token的副本
   */
  @Override
  public Token clone() {
    try {
      return (Token) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 将字符转换为编码
   */
  private static int char_to_int(char c) {
    if (c >= '0' && c <= '9') {
      return c - '0';
    } else if (c >= 'a' && c <= 'f') {
      return c - 'a' + 10;
    } else if (c >= 'A' && c <= 'F') {
      return c - 'A' + 10;
    } else {
      return -1;
    }
  }

  /**
   * @param id
   * @return id列表中的下标
   */
  private static int search_id_list(String id) {
    for (int i = 0; i < id_list.size(); i++) {
      if (id.equals(id_list.get(i))) {
        return i;
      }
    }
    return -1;
  }
}
