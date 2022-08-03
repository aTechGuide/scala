package in.kamranali.company.team.ratelimiting;

/**
 * Created by Siddharth on 2/19/18.
 * This class is implemented based on Token bucket algorithm
 *
 * - https://www.linkedin.com/pulse/api-rate-limiting-using-token-bucket-algorithm-siddharth-patnaik/
 * - https://www.youtube.com/watch?v=FU4WlwfS3G0
 */
public class RateLimiter {

    private final long maxBucketSize;
    private final long refillCountPerSecond;

    long availableTokens;
    long lastRefillTimeStamp;


    public RateLimiter(long capacity, long refillRate){
        this.maxBucketSize = capacity;
        this.refillCountPerSecond = refillRate;

        this.lastRefillTimeStamp = System.currentTimeMillis();
        this.availableTokens = capacity;
    }

    public synchronized boolean allowRequest(int customerId){
        refill(); // first refill the bucket with tokens accumulated since the last call
        
        if(availableTokens > 0) {
            --availableTokens;
            return true;
        }
        else return false;
    }

    private void refill(){
        final long now = System.currentTimeMillis();
        final long elapsedTime = now - lastRefillTimeStamp;

        // refill tokens for this durationlong
        final long tokensToBeAdded = (elapsedTime/1000) * refillCountPerSecond;
        if(tokensToBeAdded > 0) {
            availableTokens = Math.min(maxBucketSize, availableTokens + tokensToBeAdded);
            lastRefillTimeStamp = now;
        }
    }

    public static void main(String[] args) {
        long refillCountPerSecond = 10;

        System.out.println((1000/1000) * refillCountPerSecond);
        System.out.println(((1100 - 1000)/1000) * refillCountPerSecond);
        System.out.println(((1800 - 1000)/1000) * refillCountPerSecond);

    }
}