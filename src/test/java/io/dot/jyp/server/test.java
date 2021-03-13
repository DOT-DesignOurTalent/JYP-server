package io.dot.jyp.server;

import com.opencsv.bean.CsvToBeanBuilder;
import io.dot.jyp.server.infra.dto.NicknameCsvObject;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class test {

  private static final String PATH = "src/test/resources/nickname.csv";

  public static void main(String[] args) throws FileNotFoundException {

    List<NicknameCsvObject> beans = new CsvToBeanBuilder(new FileReader(PATH))
        .withType(NicknameCsvObject.class).build().parse();
    System.out.println(beans.get(2).getFirst());
    System.out.println(beans.get(1).getSecond());
    System.out.println(beans.size());

  }
}
