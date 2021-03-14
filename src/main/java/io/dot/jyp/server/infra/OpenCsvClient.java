package io.dot.jyp.server.infra;

import com.opencsv.bean.CsvToBeanBuilder;
import io.dot.jyp.server.domain.FileIoClient;
import io.dot.jyp.server.domain.RandomValueGenerator;
import io.dot.jyp.server.infra.dto.NicknameCsvObject;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier("OpenCsvClient")
public class OpenCsvClient implements FileIoClient {

  @Override
  public RandomValueGenerator readCsvFile(String path) {
    RandomValueGenerator nicknameList = null;
    try (Reader reader = Files.newBufferedReader(Paths.get(path));) {
      List<NicknameCsvObject> nicknameObjects = new CsvToBeanBuilder(reader)
          .withType(NicknameCsvObject.class)
          .withIgnoreLeadingWhiteSpace(true)
          .build().parse();
      reader.close();
      nicknameList = RandomValueGenerator.of(
          nicknameObjects.stream().map(NicknameCsvObject::getFirst).collect(Collectors.toList()),
          nicknameObjects.stream().map(NicknameCsvObject::getSecond).collect(Collectors.toList())
      );
      return nicknameList;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return nicknameList;
  }

  @Override
  public Map<String, String> write(String path) throws IOException {
    return null;
  }

}
