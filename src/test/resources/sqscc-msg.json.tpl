{
  "Type" : "Notification",
  "MessageId" : "${MessageId}",
  "TopicArn" : "arn:aws:sns:us-west-2:239062223385:testjenkins-topic",
  "Subject" : "UPDATE: AWS CodeCommit us-west-2 push: testjenkins",
  "Message" : "{\"Records\":[{\"awsRegion\":\"us-west-2\",\"codecommit\":{\"references\":[{\"commit\":\"a6abadd78aa29cab902fd5f9ea6ec3ce063f47a9\",\"ref\":\"${Ref}\"}]},\"eventId\":\"${EventId}\",\"eventName\":\"ReferenceChanges\",\"eventPartNumber\":1,\"eventSource\":\"aws:codecommit\",\"eventSourceARN\":\"arn:aws:codecommit:us-west-2:239062223385:testjenkins\",\"eventTime\":\"2017-06-15T06:46:42.325+0000\",\"eventTotalParts\":1,\"eventTriggerConfigId\":\"7e38a940-5496-425b-b0f8-756c02e22b6a\",\"eventTriggerName\":\"AllEvents\",\"eventVersion\":\"1.0\",\"userIdentityARN\":\"arn:aws:iam::239062223385:user/ext-phuong-huynh\"}]}",
  "Timestamp" : "2017-06-15T06:46:42.385Z",
  "SignatureVersion" : "1",
  "Signature" : "ICte8ngwYXDRMO0rYcJjaFSDRV4Lf+38S5aktP5kUAtIYqy6E6Pw0CX9cgkiSchsDQw8spvJjAqAiu/nbQrRU/3etE4jeYYGY1ZodMXLhBRs2YzuyC6molDE660KHJ5HF3g7+PbhGLbMqr4H3GuxH3EJ5nTbSSntqS7EOmrIjWR7nMLddNiqJ24qc05QgQS86bdWORbp8OvvnwpVolYWEJMUTGNEb0UR68/v3H40q4QaD8xtxdF0WbYG9SyqQhtZ/1y4DmliB2PN5d5elrvCCXdJOs3jHboR6OlmyGNrKIgIRM3dEyEzs10RjLiJquaeaNNifTuTkRQazotxlpyiZg==",
  "SigningCertURL" : "https://sns.us-west-2.amazonaws.com/SimpleNotificationService-b95095beb82e8f6a046b3aafc7f4149a.pem",
  "UnsubscribeURL" : "https://sns.us-west-2.amazonaws.com/?Action=Unsubscribe&SubscriptionArn=arn:aws:sns:us-west-2:239062223385:testjenkins-topic:8fa0d552-bbaf-4a19-8555-ac4ba3417345"
}
