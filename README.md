# PaymentService :
    initatePayment API :  Provide payment link of user choosen gateway amongs razorPay or stripe to user.
    HandleEvent API : Handles the event send by Gateways.

# Flow
    User Place the Order, choose the paymentGateway and initiate Payment <- (in return receive PaymentLink)
    Through paymentLink user redirected to chosen payment gateway -> Enters the card details -> Click Pay button
    Event is triggered from the gateway. 
    Event is authenticated
      -> On Authenticated, request is Processed
        -> On successful Payment 
              Payment.Captured Event is recieved
              Payment record staus updated to Success
        -> Else 
              Payment.Failed is recieved
              Payment record staus updated to Failed
      -> Else
              Request Denied

# Pre Requisites:
  	JDK 12+, Spring and spring boot Framework, Maven dependency, for Windows is required.

# Supported PlatForms:
  	Windows

# References:
    StripeGateway : https://stripe.com/docs/payments
    RazorPayGateway : https://razorpay.com/docs/api/
  	Others : StackOverFlow, Spring Docs, etc
  
