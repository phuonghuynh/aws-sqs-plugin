package io.relution.jenkins.awssqs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.relution.jenkins.awssqs.model.entities.codecommit.MessageBody;
import io.relution.jenkins.awssqs.model.entities.codecommit.Record;
import io.relution.jenkins.awssqs.model.entities.codecommit.Records;
import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GsonParserTest {

    private final Gson gson =  new GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create();

    @Test
    public void testParseSampleSqsResponse() throws IOException {
        String sqsResponse = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("sample-sqs-codecommit-response.json"), StandardCharsets.UTF_8);
        Assertions.assertThat(sqsResponse).isNotNull().isNotEmpty();

        MessageBody messageBody = gson.fromJson(sqsResponse, MessageBody.class);
        Assertions.assertThat(messageBody).isNotNull()
            .extracting("MessageId", "Message").isNotNull().isNotEmpty();

        Records records = gson.fromJson(messageBody.getMessage(), Records.class);
        Assertions.assertThat(records).isNotNull()
            .hasAtLeastOneElementOfType(Record.class);
    }
}
