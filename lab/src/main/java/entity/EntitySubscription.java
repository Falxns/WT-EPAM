package entity;

public class EntitySubscription extends Entity{
    int userId;
    int publicationId;
    int paymentId;
    boolean isPaid;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public boolean isPaid() { return isPaid; }

    public void setPaid(boolean paid) { isPaid = paid; }
}