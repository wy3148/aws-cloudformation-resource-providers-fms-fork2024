package software.amazon.fms.notificationchannel;

import software.amazon.awssdk.services.fms.model.GetNotificationChannelResponse;
import software.amazon.awssdk.services.fms.model.PutNotificationChannelRequest;
import software.amazon.awssdk.services.fms.model.PutNotificationChannelResponse;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;

public class CreateHandler extends NotificationChannelHandler {

    @Override
    protected boolean throwAlreadyExistsException() {
        return true;
    }

    @Override
    protected PutNotificationChannelResponse makeRequest(
            final AmazonWebServicesClientProxy proxy,
            final ResourceModel desiredResourceState,
            final GetNotificationChannelResponse getNotificationChannelResponse) {

        // send the create request based on the desired resource state
        final PutNotificationChannelRequest putNotificationChannelRequest = PutNotificationChannelRequest.builder()
                .snsTopicArn(desiredResourceState.getSnsTopicArn())
                .snsRoleName(desiredResourceState.getSnsRoleName())
                .build();
        return proxy.injectCredentialsAndInvokeV2(putNotificationChannelRequest, client::putNotificationChannel);
    }

    @Override
    protected ResourceModel constructSuccessResourceState(
            ResourceModel desiredResourceState,
            final GetNotificationChannelResponse getNotificationChannelResponse) {

        // use the desired resource state as the post-create resource state
        return desiredResourceState;
    }
}
