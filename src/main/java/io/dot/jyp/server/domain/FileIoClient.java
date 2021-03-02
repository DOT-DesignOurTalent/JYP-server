package io.dot.jyp.server.domain;

import java.io.IOException;
import java.util.Map;

public interface FileIoClient {
    RandomValueGenerator readCsvFile(String path);

    Map<String, String> write(String path) throws IOException;
}
