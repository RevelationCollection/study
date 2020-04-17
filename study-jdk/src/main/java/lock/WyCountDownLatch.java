package lock;

public class WyCountDownLatch {
    WyAqs wyAqs = new WyAqs(){
        @Override
        public int tryAcquireShare() {
            return getState().get()==0?1:-1;
        }

        @Override
        public boolean tryReleaseShare() {
            return getState().decrementAndGet()==0;
        }

    };

    public  WyCountDownLatch(int count){
        wyAqs.getState().set(count);
    }

    public void await(){
        wyAqs.acquireShare();
    }

    public void countDown(){
        wyAqs.releaseShare();;
    }
}
