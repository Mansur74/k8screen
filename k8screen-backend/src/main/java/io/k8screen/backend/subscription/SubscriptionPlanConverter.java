package io.k8screen.backend.subscription;

import io.k8screen.backend.subscription.dto.SubscriptionPlanInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionPlanConverter {
  SubscriptionPlanInfo toSubscriptionPlanInfo(SubscriptionPlan subscriptionPlan);
}
