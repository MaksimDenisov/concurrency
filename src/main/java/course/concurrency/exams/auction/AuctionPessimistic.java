package course.concurrency.exams.auction;

public class AuctionPessimistic implements Auction {

    private Notifier notifier;

    public AuctionPessimistic(Notifier notifier) {
        this.notifier = notifier;
    }

    private Bid latestBid = new Bid(0L, 0L, 0L);

    public boolean propose(Bid bid) {
        synchronized (this) {
            if (bid.getPrice() > latestBid.getPrice()) {
                notifier.sendOutdatedMessage(latestBid);
                latestBid = bid;
                return true;
            }
        }
        return false;
    }

    public synchronized Bid getLatestBid() {
        return latestBid;
    }
}
